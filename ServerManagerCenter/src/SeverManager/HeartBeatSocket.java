  
/**    
* �ļ�����HeartBeatSocket.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��9��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SeverManager;


import java.util.concurrent.ConcurrentHashMap;

import DDS_Transfer.IDDS_Protocol;


/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�HeartBeatSocket    
* ��������    ����������ͨѶsocket,���ڻط����ݴ�ǽ
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��9�� ����10:14:46    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��9�� ����10:14:46    
* �޸ı�ע��    
* @version     
*     
*/
public class HeartBeatSocket {
	/**
	 *����ͨѶ��
	 *ͨ����������+IP+�˿�
	 */
public static  ConcurrentHashMap<String,IDDS_Protocol> fireBeat=new ConcurrentHashMap<String, IDDS_Protocol>();
public static  ConcurrentHashMap<String,IDDS_Protocol>  tcpNatBeat=new ConcurrentHashMap<String, IDDS_Protocol>();
}
