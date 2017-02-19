  
/**    
* 文件名：CheckServerAction.java    
*    
* 版本信息：    
* 日期：2017年2月8日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package nameServerContainer;

import java.util.ArrayList;

import java.util.Map.Entry;


import ProcessMessage.InnerMessage;
import PublicModel.ServerBinds;

/**    
*     
* 项目名称：NameServerContainer    
* 类名称：CheckServerAction    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月8日 下午4:47:44    
* 修改人：jinyu    
* 修改时间：2017年2月8日 下午4:47:44    
* 修改备注：    
* @version     
*     
*/
public class CheckServerAction {
	/**
	 * 验证服务超时失活时间长度
	 */
public static long actionLen=10*1000;//10秒

public static long checkTime=5*1000;

private static  volatile boolean isCheck=true;


/**
 * 更新服务活动信息
* @Name: CheckAction 
* @Description: 更新服务活动信息
* @param serverName
* @param address  服务绑定地址 
* @return void    返回类型 
* @throws
 */
public static void CheckAction(String serverName,String address)
{
	if(isCheck)
	{
		//有正确的心跳才有检查
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
		//说明没有
		//检查主从服务
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
			//检查 负载均衡服务
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
* @Description: 检查服务活动状态 
* @return void    返回类型 
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
				// 检查主从服务
				for(Entry<String, ServerBinds> map:ServersContains.masterServers.entrySet())
				{
					ServerBinds tmp=map.getValue();
					String name=map.getKey();
					if(System.currentTimeMillis()-tmp.actionTime>actionLen)
					{
						//失活
						tmp.isAction=false;
						
						 
						
						ArrayList<ServerBinds> list=ServersContains.unActionServers.getOrDefault(name, null);
						list.add(tmp);
						//立即修改；使用从服务
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
				//检查从服务
				for(Entry<String, ArrayList<ServerBinds>> map:ServersContains.slaveServers.entrySet())
				{
					ArrayList<ServerBinds> list = map.getValue();
					String name=map.getKey();
					for(int i=0;i<list.size();i++)
					{
					ServerBinds tmp=list.get(i);
					
					if(System.currentTimeMillis()-tmp.actionTime>actionLen)
					{
						//失活
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
				//检查负载均衡
				for(Entry<String, ArrayList<ServerBinds>> map:ServersContains.servers.entrySet())
				{
					ArrayList<ServerBinds> list = map.getValue();
					String name=map.getKey();
					for(int i=0;i<list.size();i++)
					{
					ServerBinds tmp=list.get(i);
					
					if(System.currentTimeMillis()-tmp.actionTime>actionLen)
					{
						//失活
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
