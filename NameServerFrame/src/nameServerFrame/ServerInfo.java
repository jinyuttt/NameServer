package nameServerFrame;

import nameServerInterface.IServer;

/**
 * @author jinyu
 * 服务代理信息
 */
public class ServerInfo {
	
	/**
	 * 服务实例
	 */
	public IServer server;
	
	/**
	 * 服务代理
	 */
	public ServerPorxy porxy;
	
	/**
	 * 代理通讯类型
	 */
	public String type_Name;

}
