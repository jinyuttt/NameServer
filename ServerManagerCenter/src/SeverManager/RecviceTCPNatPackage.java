  
/**    
* 文件名：RecviceTCPNatPackage.java    
*    
* 版本信息：    
* 日期：2017年2月14日    
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

/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：RecviceTCPNatPackage    
* 类描述：    TCP接收穿墙的地址
* 创建人：jinyu    
* 创建时间：2017年2月14日 上午2:05:06    
* 修改人：jinyu    
* 修改时间：2017年2月14日 上午2:05:06    
* 修改备注：    
* @version     
*     
*/
public class RecviceTCPNatPackage implements IRecMsg{

	private IDDS_Protocol protocol;

	/*
	* Title: RecData
	*Description: 接收服务端TCP接收保存通讯状态
	*准备穿墙
	* @param address
	* @param data 
	 
	*/
	@Override
	public void RecData(String address, byte[] data) {
		String xmlStr =new String(data);
		ServerXml info=ServerXmlInfo.Analysis(xmlStr);
		if(info==null)
		{
			InnerMessage.getInstance().PostMessage(this, "AddUILog", "心跳格式不正确:"+xmlStr);
			return;
		}
		String key=info.name+":"+info.address;
	    HeartBeatSocket.tcpNatBeat.put(key, protocol);
		
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
