package nameServerFrame;

import java.util.concurrent.ConcurrentHashMap;

import nameServerInterface.IServer;



/**
 * 服务盒子
 * 一个盒子一个中间代理，一个中间代理启动一个接收
 * @author jinyu
 *
 */
public class ServerBox {
	public ServerBox(Sever_BindsInfo proxyInfo)
	{
		if(serverProxy==null)
		{
			serverProxy=new ServerPorxy(proxyInfo.address,proxyInfo.port,proxyInfo.t_type);
			ProxyInfo=proxyInfo;
		}
	}
  private	ConcurrentHashMap<String,IServer> serverBox=new ConcurrentHashMap<String,IServer>();
  /**
   * 服务通讯代理
   */
  public	ServerPorxy serverProxy=null;
  
  /**
 * 服务端代理信息（IP,Port,传输）
 */
    public  Sever_BindsInfo ProxyInfo=null;
    
    /**
     * 
    * @Name: AddServer 
    * @Description: 添加服务到服务盒子
    * @param serverName 服务名称
    * @param server  服务
    * @return void    返回类型 
    * @throws
     */
	public void AddServer(String serverName,IServer server)
	{
		serverBox.put(serverName, server);
		serverProxy.InitServerThread(serverName, server);
	}
	/**
	 * 
	* @Name: GetServer 
	* @Description: 获取服务
	* @param serverName 服务名称
	* @return  服务实例
	* @return IServer     服务实例
	* @throws
	 */
	public IServer GetServer(String serverName)
	{
		return serverBox.get(serverName);
	}
	
}
