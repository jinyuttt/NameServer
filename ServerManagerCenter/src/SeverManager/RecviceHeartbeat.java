  
/**    
* 文件名：RecviceHeartbeat.java    
*    
* 版本信息：    
* 日期：2017年2月8日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SeverManager;



import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import ProcessMessage.InnerMessage;
import UDPPeerToPeer.ServerXml;
import UDPPeerToPeer.ServerXmlInfo;
import nameServerContainer.CheckServerAction;


/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：RecviceHeartbeat    
* 类描述：    接受心跳(XMl格式)
* 创建人：jinyu    
* 创建时间：2017年2月8日 上午10:57:33    
* 修改人：jinyu    
* 修改时间：2017年2月8日 上午10:57:33    
* 修改备注：    
* @version     
*     
*/
public class RecviceHeartbeat  implements IRecMsg{

	private IDDS_Protocol protocol=null;

	/*
	* Title: RecData
	*Description: 
	* @param address
	* @param data 
	 
	*/
	@Override
	public void RecData(String address, byte[] data) {
		
		String xmlStr =new String(data);
		ServerXml info=ServerXmlInfo.Analysis(xmlStr);

		try {

			if(info==null)
			{
				InnerMessage.getInstance().PostMessage(this, "AddUILog", "心跳格式不正确:"+xmlStr);
				return;
			}
			String key=info.name+":"+info.address;
		    HeartBeatSocket.fireBeat.put(key, protocol);
		    //传递数据
		    //可以并行检查服务活动状态
		    CheckServerAction.CheckAction(info.name, info.address);
		    InnerMessage.getInstance().PostMessage(this, "AddUILog", "接收心跳");
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}

	/*
	* Title: SaveInstance
	*Description: 
	* @param call 
	 
	*/
	@Override
	public void SaveInstance(Object call) {
		protocol=(IDDS_Protocol)call;
		
	}

}
