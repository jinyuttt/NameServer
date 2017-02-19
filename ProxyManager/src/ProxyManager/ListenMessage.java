  
/**    
* �ļ�����ListenMessage.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��25��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package ProxyManager;

import ProcessMessage.IInerMessage;
import PublicModel.ServerBinds;

/**    
*     
* ��Ŀ���ƣ�ProxyManager    
* �����ƣ�ListenMessage    
* ��������    ��ȡ��Ϣ
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��25�� ����7:54:55    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��25�� ����7:54:55    
* �޸ı�ע��    
* @version     
*     
*/
public class ListenMessage implements IInerMessage {
   public JProxyManager objui=null;
	/*
	* Title: notify
	*Description: 
	* @param sender
	* @param objStruct
	* @param message 
	 
	*/
	@Override
	public void notify(Object sender,String name, Object objStruct, String message) {
		
			
		    ConvertMessage(name, objStruct, message);
		}
	/** 
	* @Name: ConvertMessage 
	* @Description: TODO(������һ�仰�����������������) 
	* @param name
	* @param objStruct
	* @param message  ����˵�� 
	* @return void    �������� 
	* @throws 
	*/
	private void ConvertMessage(String name, Object objStruct, String message) {
		if(objui!=null)
		{
			if(name.equals("AddServer"))
			{
				if(objStruct!=null)
				{
				ServerBinds obj=(ServerBinds) objStruct;
				ServerModel model=new ServerModel();
				model.name=obj.name;
				model.IP=obj.address;
				model.port=obj.port;
			    if(obj.master.equals("0"))
			    {
			    	 model.master="��";
			    }
			    else
			    {
			    	 model.master="��";
			    }
			    if(obj.is_Using.equals("0"))
			    {
			       model.isUsing=false;
			    }
			    else
			    {
			    	model.isUsing=true;
			    }
			    //
			    if(obj.isAction)
			    {
			    	model.status="�";
			    }
			    else
			    {
			    	model.status="�쳣";
			    }
			    model.sid=obj.sid;
			    objui.AddServerInfo(model);
				}
			}
			else if(name.equals("AddUILog"))
			{
				LogInfos log=new LogInfos();
				log.logcontent=message;
				 objui.AddLog(log);
			}
		}
	}

	
    
}
