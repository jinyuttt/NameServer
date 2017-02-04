  
/**    
* 文件名：ProcessMessage.java    
*    
* 版本信息：    
* 日期：2017年1月25日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package ProcessMessage;

import java.util.HashMap;
import java.util.LinkedList;

/**    
*     
* 项目名称：ProcessMessage    
* 类名称：ProcessMessage    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年1月25日 下午7:17:56    
* 修改人：jinyu    
* 修改时间：2017年1月25日 下午7:17:56    
* 修改备注：    
* @version     
*     
*/
public class InnerMessage {
	private static InnerMessage instance;  
	      private InnerMessage (){}   
	     /**
	      *  
	     * @Name: getInstance 
	     * @Description: 单例
	     * @return  参数说明 
	     * @return ProcessMessage    返回类型 
	     * @throws
	      */
	     public static InnerMessage getInstance() {  
	      if (instance == null) {  
	         instance = new InnerMessage();  
	    }  
	   return instance;  
	      }  
	private HashMap<String,LinkedList<IInerMessage>> hashMap=new HashMap<String,LinkedList<IInerMessage>>();
    /**
     * 
    * @Name: Listen 
    * @Description: 获取推送信息
    * @param name 消息名称
    * @param inerMessage  回调结果 
    * @return void    返回类型 
    * @throws
     */
	public void  Listen(String name,IInerMessage inerMessage)
    {
	LinkedList<IInerMessage> list=hashMap.getOrDefault(name, null);
   if(list!=null)
   {
	   list.addLast(inerMessage);
   }
   else
   {
	   list=new LinkedList<IInerMessage>();
	   list.addLast(inerMessage);
	   hashMap.put(name, list);
   }
    }
	/**
	 * 
	* @Name: PostMessage 
	* @Description: 传送信息
	* @param sender  传送对象
	* @param name   消息名称
	* @param msg  消息内容
	* @return void    返回类型 
	* @throws
	 */
    public void PostMessage(Object sender,String name,String msg)
    {
    	LinkedList<IInerMessage> list=hashMap.getOrDefault(name, null);
    	   if(list!=null)
    	   {
    		   for(IInerMessage cur:list)
    		   {
    			   cur.notify(sender,name, null, msg);
    		   }
    	   }
    	   
    }
    /**
     * 
    * @Name: PostMessage 
    * @Description: 推送信息
    * @param sender 推送对象
    * @param name  消息名称
    * @param objStruct  消息结构 
    * @return void    返回类型 
    * @throws
     */
    public void PostMessage(Object sender,String name,Object objStruct)
    {
    	LinkedList<IInerMessage> list=hashMap.getOrDefault(name, null);
    	   if(list!=null)
    	   {
    		   for(IInerMessage cur:list)
    		   {
    			   cur.notify(sender,name, objStruct, "");
    		   }
    	   }
    	   
    }
}
