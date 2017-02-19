package nameServerFrame;


import java.util.concurrent.ConcurrentHashMap;

/**
 * 保存服务实例
 * @author jinyu
 *
 */
public class ServerInstances {
	
	/**
	 * 以服务名称存储服务通讯
	 */
	public static ConcurrentHashMap<String, ServerInfo> servers = new ConcurrentHashMap<String, ServerInfo>();

}
