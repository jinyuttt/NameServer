  
/**    
* 文件名：HeartBeatSocket.java    
*    
* 版本信息：    
* 日期：2017年2月9日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SeverManager;


import java.util.concurrent.ConcurrentHashMap;

import DDS_Transfer.IDDS_Protocol;


/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：HeartBeatSocket    
* 类描述：    保存心跳的通讯socket,用于回发数据穿墙
* 创建人：jinyu    
* 创建时间：2017年2月9日 下午10:14:46    
* 修改人：jinyu    
* 修改时间：2017年2月9日 下午10:14:46    
* 修改备注：    
* @version     
*     
*/
public class HeartBeatSocket {
	/**
	 *保存通讯端
	 *通过服务名称+IP+端口
	 */
public static  ConcurrentHashMap<String,IDDS_Protocol> fireBeat=new ConcurrentHashMap<String, IDDS_Protocol>();
public static  ConcurrentHashMap<String,IDDS_Protocol>  tcpNatBeat=new ConcurrentHashMap<String, IDDS_Protocol>();
}
