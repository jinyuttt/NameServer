package nameServerFrame;


import java.util.concurrent.ConcurrentHashMap;

/**
 * �������ʵ��
 * @author jinyu
 *
 */
public class ServerInstances {
	
	/**
	 * �Է������ƴ洢����ͨѶ
	 */
	public static ConcurrentHashMap<String, ServerInfo> servers = new ConcurrentHashMap<String, ServerInfo>();

}
