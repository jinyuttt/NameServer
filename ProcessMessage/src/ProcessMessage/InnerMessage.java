  
/**    
* �ļ�����ProcessMessage.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��25��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package ProcessMessage;

import java.util.HashMap;
import java.util.LinkedList;

/**    
*     
* ��Ŀ���ƣ�ProcessMessage    
* �����ƣ�ProcessMessage    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��25�� ����7:17:56    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��25�� ����7:17:56    
* �޸ı�ע��    
* @version     
*     
*/
public class InnerMessage {
	private static InnerMessage instance;  
	      private InnerMessage (){}   
	     /**
	      *  
	     * @Name: getInstance 
	     * @Description: ����
	     * @return  ����˵�� 
	     * @return ProcessMessage    �������� 
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
    * @Description: ��ȡ������Ϣ
    * @param name ��Ϣ����
    * @param inerMessage  �ص���� 
    * @return void    �������� 
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
	* @Description: ������Ϣ
	* @param sender  ���Ͷ���
	* @param name   ��Ϣ����
	* @param msg  ��Ϣ����
	* @return void    �������� 
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
    * @Description: ������Ϣ
    * @param sender ���Ͷ���
    * @param name  ��Ϣ����
    * @param objStruct  ��Ϣ�ṹ 
    * @return void    �������� 
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
