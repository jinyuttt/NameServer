  
/**    
* 文件名：IInerMessage.java    
*    
* 版本信息：    
* 日期：2017年1月25日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package ProcessMessage;

  
/**    
*     
* 项目名称：ProcessMessage    
* 类名称：IInerMessage    
* 类描述：   消息返回接口
* 创建人：jinyu    
* 创建时间：2017年1月25日 下午7:19:46    
* 修改人：jinyu    
* 修改时间：2017年1月25日 下午7:19:46    
* 修改备注：    
* @version     
*     
*/
public interface IInerMessage {
public void notify(Object sender,String name,Object objStruct,String message);
}
