package nameServerContainer;

import java.util.ArrayList;
import java.util.Iterator;

import ProcessMessage.InnerMessage;
import PublicModel.ServerBinds;


/**
 * 添加服务
 * @author jinyu
 *
 */
public class ServerInfoSave {
	
  /**
 * @param server
 * 添加服务保持
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
		//保存
		ArrayList<ServerBinds> listObj=new ArrayList<ServerBinds>();
		listObj.add(server);
		ServersContains.servers.put(server.name, listObj);
		
		InnerMessage.getInstance().PostMessage(this, "AddServer", server);
	}
	  
  }


/**
 * 移出服务
 * @param name  名称
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
 * 获取当前服务信息
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
* @Description: 处理主从服务 
* @param server  参数说明 
* @return void    返回类型 
* @throws
 */
private void ProcessMaster(ServerBinds server)
{
	server.master="1";//只要进入该方法的都是主从服务
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
		//保存
		ArrayList<ServerBinds> listObj=new ArrayList<ServerBinds>();
		listObj.add(server);
		ServersContains.slaveServers.put(server.name, listObj);
		InnerMessage.getInstance().PostMessage(this, "AddServer", server);
		
	}
	//处理主服务
	if(server.is_Using.equals("1"))
	{
		ServerBinds tmp=ServersContains.masterServers.getOrDefault(server.name, null);
		if(tmp==null)
		{
			//如果还没有主服务，临时添加从服务启动使用
			ServersContains.masterServers.put(server.name, server);
		}
		else
		{
			if(tmp.is_Using.equals("1"))
			{
				//配置错误时处理错误
				if(server.sid.compareTo(tmp.sid)>0)
				{
					ServersContains.masterServers.replace(server.name, server);
					tmp.is_Using="0";//以前的服务修改
				}
				else
				{
					server.is_Using="0";//修改，基于引用全部修改
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
			//如果还没有主服务，临时添加从服务启动使用
			ServersContains.masterServers.put(server.name, server);
		}
	}
	//检查以前的服务是否没有配置主从服务
	//同名称的服务，一旦有一个配置主从服务，则全部都是
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
