package nameServerFrame;

import java.util.concurrent.ConcurrentHashMap;

import nameServerInterface.IServer;



/**
 * �������
 * һ������һ���м������һ���м��������һ������
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
   * ����ͨѶ����
   */
  public	ServerPorxy serverProxy=null;
  
  /**
 * ����˴�����Ϣ��IP,Port,���䣩
 */
    public  Sever_BindsInfo ProxyInfo=null;
    
    /**
     * 
    * @Name: AddServer 
    * @Description: ���ӷ��񵽷������
    * @param serverName ��������
    * @param server  ����
    * @return void    �������� 
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
	* @Description: ��ȡ����
	* @param serverName ��������
	* @return  ����ʵ��
	* @return IServer     ����ʵ��
	* @throws
	 */
	public IServer GetServer(String serverName)
	{
		return serverBox.get(serverName);
	}
	
}