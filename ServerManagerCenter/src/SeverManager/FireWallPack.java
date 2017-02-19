  
/**    
* 文件名：FireWallPack.java    
*    
* 版本信息：    
* 日期：2017年2月10日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SeverManager;

import java.util.concurrent.LinkedBlockingQueue;

/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：FireWallPack    
* 类描述：    接收穿墙准备成功的消息
* 创建人：jinyu    
* 创建时间：2017年2月10日 上午1:11:30    
* 修改人：jinyu    
* 修改时间：2017年2月10日 上午1:11:30    
* 修改备注：    
* @version     
*     
*/
public class FireWallPack {
	//SynchronousQueue，准备优化研究
public static	LinkedBlockingQueue<String>  sucess=new LinkedBlockingQueue<String>(40);
}
