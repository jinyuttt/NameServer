  
/**    
* 文件名：ServerModel.java    
*    
* 版本信息：    
* 日期：2017年1月24日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package ProxyManager;

  
/**    
*     
* 项目名称：ProxyManager    
* 类名称：ServerModel    
* 类描述：   在界面显示服务信息
* 创建人：jinyu    
* 创建时间：2017年1月24日 上午12:45:34    
* 修改人：jinyu    
* 修改时间：2017年1月24日 上午12:45:34    
* 修改备注：    
* @version     
*     
*/
public class ServerModel {

	/**
	 * 标记
	 */
	public String sid="";
	
	/**
	 * 服务名称
	 */
public String name;
/**
 * IP
 */
public String IP;
/**
 * 端口
 */
public int port;
/**
 * 服务状态：是否正常运行
 * 需要启用心跳
 */
public String status="活动";
/**
 * 是否是主从服务
 */
public String  master;

/**
 * 是否正在使用
 * 只针对主从服务
 * 负载均衡则全部是启用
 */
public Boolean  isUsing;


}
