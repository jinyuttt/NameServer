  
/**    
* 文件名：ManagerInfo.java    
*    
* 版本信息：    
* 日期：2017年2月18日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package nameServerContainer;

import java.util.HashMap;

/**    
*     
* 项目名称：NameServerContainer    
* 类名称：ManagerInfo    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月18日 上午12:25:52    
* 修改人：jinyu    
* 修改时间：2017年2月18日 上午12:25:52    
* 修改备注：    
* @version     
*     
*/
public class ManagerInfo {
	/**
	 * 管理器传送的信息
	 */
	public static HashMap<String,String> hashConfig=new HashMap<String,String>();
	/**
	 * 只接收一次服务
	 */
	public  static volatile boolean isInit=false;

}
