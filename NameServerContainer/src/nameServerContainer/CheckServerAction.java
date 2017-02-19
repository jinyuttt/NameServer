  
/**    
* �ļ�����CheckServerAction.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��8��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package nameServerContainer;

import java.util.ArrayList;

import java.util.Map.Entry;


import ProcessMessage.InnerMessage;
import PublicModel.ServerBinds;

/**    
*     
* ��Ŀ���ƣ�NameServerContainer    
* �����ƣ�CheckServerAction    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��8�� ����4:47:44    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��8�� ����4:47:44    
* �޸ı�ע��    
* @version     
*     
*/
public class CheckServerAction {
	/**
	 * ��֤����ʱʧ��ʱ�䳤��
	 */
public static long actionLen=10*1000;//10��

public static long checkTime=5*1000;

private static  volatile boolean isCheck=true;


/**
 * ���·�����Ϣ
* @Name: CheckAction 
* @Description: ���·�����Ϣ
* @param serverName
* @param address  ����󶨵�ַ 
* @return void    �������� 
* @throws
 */
public static void CheckAction(String serverName,String address)
{
	if(isCheck)
	{
		//����ȷ���������м��
		isCheck=false;
		CheckUnAction();
		
	}
	address=address.replaceFirst(":", "");
	ArrayList<ServerBinds> servers=ServersContains.unActionServers.getOrDefault(serverName, null);
	if(servers!=null)
	{
		for(int i=0;i<servers.size();i++)
		{
			ServerBinds tmp=	servers.get(i);
			String addr=tmp.address+tmp.port;
			if(addr.equals(address))
			{
				if(!tmp.isAction)
				{
					tmp.isAction=true;
					 InnerMessage.getInstance().PostMessage(null, "AddServer", tmp);
				}

				tmp.actionTime=System.currentTimeMillis();
				if(tmp.master.equals("0"))
				{
					ArrayList<ServerBinds> list=ServersContains.servers.getOrDefault(serverName, null);
					list.add(tmp);
				}
				else
				{
					if(tmp.is_Using.equals("1"))
					{
						ServersContains.masterServers.replace(serverName, tmp);
					}
					else
					{
						ArrayList<ServerBinds> slaves=ServersContains.slaveServers.getOrDefault(serverName, null);
						if(slaves!=null)
						{
							slaves.add(tmp);
						}
						else
						{
							slaves=new ArrayList<ServerBinds>();
							slaves.add(tmp);
							ServersContains.slaveServers.put(serverName, slaves);
						}
					}
				}
				
				return;
			}
		}
	}
	else
	{
		//˵��û��
		//������ӷ���
		ServerBinds tmp=	ServersContains.masterServers.getOrDefault(serverName, null);
		if(tmp!=null)
		{
			String addr=tmp.address+tmp.port;
			if(addr.equals(address))
			{
				if(!tmp.isAction)
				{
					tmp.isAction=true;
				InnerMessage.getInstance().PostMessage(null, "AddServer", tmp);
				}
				
				tmp.actionTime=System.currentTimeMillis();
			}
			else
			{
				ArrayList<ServerBinds> slaves=ServersContains.slaveServers.getOrDefault(serverName, null);
				if(slaves!=null)
				{
				 for(int i=0;i<slaves.size();i++)
				 {
					 ServerBinds server= slaves.get(i);
					 addr=server.address+server.port;
					 if(addr.equals(address))
					 {
						 if(!server.isAction)
							{
								server.isAction=true;
							InnerMessage.getInstance().PostMessage(null, "AddServer", server);
							}
						
						 server.actionTime=System.currentTimeMillis();
						 break;
					}
				 }
				}
				else
				{
					slaves=new ArrayList<ServerBinds>();
					slaves.add(tmp);
					ServersContains.slaveServers.put(serverName, slaves);
				}
			}
		}
		else
		{
			//��� ���ؾ������
			ArrayList<ServerBinds> list=ServersContains.servers.getOrDefault(serverName, null);
			if(list!=null)
			{
				String addr="";
				for(int i=0;i<list.size();i++)
				{
					 ServerBinds ckserver=list.get(i);
					 addr=ckserver.address+ckserver.port;
					 if(addr.equals(address))
					 {
						 if(!ckserver.isAction)
							{
							 ckserver.isAction=true;
							InnerMessage.getInstance().PostMessage(null, "AddServer", ckserver);
							}
						 ckserver.actionTime=System.currentTimeMillis();
						 break;
					 }
				}
			}
		}
	}
}

/**
 * 
* @Name: CheckUnAction 
* @Description: ������״̬ 
* @return void    �������� 
* @throws
 */
public static void CheckUnAction()
{
	 Thread ckAction=new Thread(new Runnable()
			 {

				@Override
				public void run() {
					while(true)
					{
						try {
							Thread.sleep(checkTime);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
				// ������ӷ���
				for(Entry<String, ServerBinds> map:ServersContains.masterServers.entrySet())
				{
					ServerBinds tmp=map.getValue();
					String name=map.getKey();
					if(System.currentTimeMillis()-tmp.actionTime>actionLen)
					{
						//ʧ��
						tmp.isAction=false;
						
						 
						
						ArrayList<ServerBinds> list=ServersContains.unActionServers.getOrDefault(name, null);
						list.add(tmp);
						//�����޸ģ�ʹ�ôӷ���
						list=ServersContains.slaveServers.getOrDefault(name, null);
						if(list!=null)
						{
							for(int i=0;i<list.size();i++)
							{
								if(list.get(i).isAction)
								{
									ServersContains.masterServers.replace(name, tmp); 
								}
							}
						}
						InnerMessage.getInstance().PostMessage(null, "AddServer", tmp);
					}
				}
				//���ӷ���
				for(Entry<String, ArrayList<ServerBinds>> map:ServersContains.slaveServers.entrySet())
				{
					ArrayList<ServerBinds> list = map.getValue();
					String name=map.getKey();
					for(int i=0;i<list.size();i++)
					{
					ServerBinds tmp=list.get(i);
					
					if(System.currentTimeMillis()-tmp.actionTime>actionLen)
					{
						//ʧ��
						tmp.isAction=false;
						 
						ArrayList<ServerBinds> unAction=ServersContains.unActionServers.getOrDefault(name, null);
						if(unAction!=null)
						{
							unAction.add(tmp);
						}
						else
						{
							unAction=new ArrayList<ServerBinds>();
							unAction.add(tmp);
							ServersContains.unActionServers.put(name, unAction);
						}
						list.remove(i);
						InnerMessage.getInstance().PostMessage(null, "AddServer", tmp);
					}
					}
				}
				//��鸺�ؾ���
				for(Entry<String, ArrayList<ServerBinds>> map:ServersContains.servers.entrySet())
				{
					ArrayList<ServerBinds> list = map.getValue();
					String name=map.getKey();
					for(int i=0;i<list.size();i++)
					{
					ServerBinds tmp=list.get(i);
					
					if(System.currentTimeMillis()-tmp.actionTime>actionLen)
					{
						//ʧ��
						tmp.isAction=false;
					
						ArrayList<ServerBinds> unAction=ServersContains.unActionServers.getOrDefault(name, null);
						if(unAction!=null)
						{
							unAction.add(tmp);
						}
						else
						{
							unAction=new ArrayList<ServerBinds>();
							unAction.add(tmp);
							ServersContains.unActionServers.put(name, unAction);
						}
						list.remove(i);
						InnerMessage.getInstance().PostMessage(null, "AddServer", tmp);
					}
					}
				}
				
					
				}
				}
		 
			 });
	 ckAction.setDaemon(true);
	 ckAction.setName("CheckAction");
	 ckAction.start();
}
}
