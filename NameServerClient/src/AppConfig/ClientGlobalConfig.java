  
/**    
* �ļ�����GlobalConfig.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��21��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package AppConfig;

import java.util.HashMap;
import java.util.Map;

import RequestServerInfo.ServerManagerType;


/**    
*     
* ��Ŀ���ƣ�NameServerClient    
* �����ƣ�GlobalConfig    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��21�� ����10:16:26    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��21�� ����10:16:26    
* �޸ı�ע��    
* @version     
*     
*/
public class ClientGlobalConfig {
	/*
	 * ��ǰ��������
	 */ 
public static ServerManagerType ServerProType=ServerManagerType.ServerIndirectMode;

/*
 * ÿ������Ĵ�������
 * ���շ������ƣ�����ͬ��ʽ
 */
public static Map<String,ServerManagerType> mapConfig=new HashMap<String,ServerManagerType>();

public static String ManagerAddress="127.0.0.1";
public static  int ManagerPort=3334;
public static  String protol_Type="TCP";

}
