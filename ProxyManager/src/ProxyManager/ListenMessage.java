  
/**    
* 文件名：ListenMessage.java    
*    
* 版本信息：    
* 日期：2017年1月25日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package ProxyManager;

import ProcessMessage.IInerMessage;
import PublicModel.ServerBinds;

/**    
*     
* 项目名称：ProxyManager    
* 类名称：ListenMessage    
* 类描述：    获取信息
* 创建人：jinyu    
* 创建时间：2017年1月25日 下午7:54:55    
* 修改人：jinyu    
* 修改时间：2017年1月25日 下午7:54:55    
* 修改备注：    
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
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param name
	* @param objStruct
	* @param message  参数说明 
	* @return void    返回类型 
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
			    	 model.master="否";
			    }
			    else
			    {
			    	 model.master="是";
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
			    	model.status="活动";
			    }
			    else
			    {
			    	model.status="异常";
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
