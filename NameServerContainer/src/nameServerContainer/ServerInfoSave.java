package nameServerContainer;

import java.util.ArrayList;
import java.util.Iterator;

import ProcessMessage.InnerMessage;
import PublicModel.ServerBinds;


/**
 * ���ӷ���
 * @author jinyu
 *
 */
public class ServerInfoSave {
	
  /**
 * @param server
 * ���ӷ��񱣳�
 */
public void Add(ServerBinds server)
  {
	if(server.master=="1"||ServersContains.slaveServers.containsKey(server.name))
	{
		ProcessMaster(server);
		return;
	}
	if(ServersContains.servers.containsKey(server.name))
	{
		ArrayList<ServerBinds> listObj=ServersContains.servers.get(server.name);
		Iterator<ServerBinds>	 it=listObj.iterator();
		while(it.hasNext())
		{
			if(it.next().sid.equals(server.sid))
			{
				return ;
			}
		}
		//
		listObj.add(server);
		InnerMessage.getInstance().PostMessage(this, "AddServer", server);
	}
	else
	{
		//����
		ArrayList<ServerBinds> listObj=new ArrayList<ServerBinds>();
		listObj.add(server);
		ServersContains.servers.put(server.name, listObj);
		
		InnerMessage.getInstance().PostMessage(this, "AddServer", server);
	}
	  
  }


/**
 * �Ƴ�����
 * @param name  ����
 * @param sid 
 */
public void Remove(String name, String  sid)
{
	if(ServersContains.servers.containsKey(name))
	{
		ArrayList<ServerBinds> listObj=ServersContains.servers.get(name);
		Iterator<ServerBinds>it=listObj.iterator();
		while(it.hasNext())
		{
			if(it.next().sid==sid)
			{
				it.remove();
				break;
			}
		}
		//
		
	}
}
/**
 * ��ȡ��ǰ������Ϣ
 * @param name
 * @return
 */
public ArrayList<ServerBinds> GetCur(String name)
{
	if(ServersContains.servers.containsKey(name))
	{
		ArrayList<ServerBinds> listObj=ServersContains.servers.get(name);
		return listObj;
		//
	}
	else
	{
		return null;
	}
}

/**
 * 
* @Name: ProcessMaster 
* @Description: �������ӷ��� 
* @param server  ����˵�� 
* @return void    �������� 
* @throws
 */
private void ProcessMaster(ServerBinds server)
{
	server.master="1";//ֻҪ����÷����Ķ������ӷ���
	if(ServersContains.slaveServers.containsKey(server.name))
	{
		ArrayList<ServerBinds> listObj=ServersContains.servers.get(server.name);
		Iterator<ServerBinds>	 it=listObj.iterator();
		while(it.hasNext())
		{
			if(it.next().sid.equals(server.sid))
			{
				return ;
			}
		}
		//
		listObj.add(server);
		InnerMessage.getInstance().PostMessage(this, "AddServer", server);
	}
	else
	{
		//����
		ArrayList<ServerBinds> listObj=new ArrayList<ServerBinds>();
		listObj.add(server);
		ServersContains.slaveServers.put(server.name, listObj);
		InnerMessage.getInstance().PostMessage(this, "AddServer", server);
		
	}
	//����������
	if(server.is_Using.equals("1"))
	{
		ServerBinds tmp=ServersContains.masterServers.getOrDefault(server.name, null);
		if(tmp==null)
		{
			//�����û����������ʱ���Ӵӷ�������ʹ��
			ServersContains.masterServers.put(server.name, server);
		}
		else
		{
			if(tmp.is_Using.equals("1"))
			{
				//���ô���ʱ��������
				if(server.sid.compareTo(tmp.sid)>0)
				{
					ServersContains.masterServers.replace(server.name, server);
					tmp.is_Using="0";//��ǰ�ķ����޸�
				}
				else
				{
					server.is_Using="0";//�޸ģ���������ȫ���޸�
				}
			}
			else
			{
				ServersContains.masterServers.replace(server.name, server);
			}
		}
		
	}
	else
	{
		ServerBinds tmp=ServersContains.masterServers.getOrDefault(server.name, null);
		if(tmp==null)
		{
			//�����û����������ʱ���Ӵӷ�������ʹ��
			ServersContains.masterServers.put(server.name, server);
		}
	}
	//�����ǰ�ķ����Ƿ�û���������ӷ���
	//ͬ���Ƶķ���һ����һ���������ӷ�����ȫ������
	ArrayList<ServerBinds> listObj=ServersContains.servers.getOrDefault(server.name,null);
	if(listObj!=null)
	{
		ArrayList<ServerBinds> listTmp=ServersContains.slaveServers.getOrDefault(server.name,null);
		Iterator<ServerBinds>	 it=listObj.iterator();
		while(it.hasNext())
		{
			
			 ServerBinds tmp=it.next();
			 tmp.master="1";
			 listTmp.add(tmp);
			 InnerMessage.getInstance().PostMessage(this, "AddServer", tmp);
		}
		
		listObj.clear();
		ServersContains.servers.remove(server.name);
	}
	
}
}