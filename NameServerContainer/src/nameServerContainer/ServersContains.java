package nameServerContainer;



import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import PublicModel.ServerBinds;


/**
 * ������Ϣ�洢
 * �����������������ø��ౣ����Ϣ
 * @author jinyu
 *
 */
public class ServersContains {
	/**
	 * ����һ�������Ϣ
	 */
public static  ConcurrentHashMap<String,ArrayList<ServerBinds>> servers=new ConcurrentHashMap<String, ArrayList<ServerBinds >>();

/**
 * �������ӷ���
 */
public static  ConcurrentHashMap<String,ServerBinds> masterServers=new ConcurrentHashMap<String,ServerBinds>();

/**
 * ������
 */
public static  ConcurrentHashMap<String,ArrayList<ServerBinds>> slaveServers=new ConcurrentHashMap<String, ArrayList<ServerBinds >>();

/**
 * ʧ��ķ���
 */
public static  ConcurrentHashMap<String,ArrayList<ServerBinds>> unActionServers=new ConcurrentHashMap<String, ArrayList<ServerBinds >>();


}
