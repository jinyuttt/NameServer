  
/**    
* 文件名：RecviceClientUDPWall.java    
*    
* 版本信息：    
* 日期：2017年2月18日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import UDPPeerToPeer.ServerXml;
import UDPPeerToPeer.ServerXmlInfo;

/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：RecviceClientUDPWall    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月18日 下午7:04:59    
* 修改人：jinyu    
* 修改时间：2017年2月18日 下午7:04:59    
* 修改备注：    
* @version     
*     
*/
public class RecviceClientUDPWall implements IRecMsg {
	private IDDS_Protocol protocol;
	/*
	* Title: RecData
	*Description: 
	* @param address
	* @param data 
	 
	*/
	@Override
	public void RecData(String address, byte[] data) {
		String xml=new String(data);
		ServerXml server=ServerXmlInfo.Analysis(xml);
		//发送给服务端穿墙消息
		PostWallCmd.UDPwall(address, server);
		//
	   String  coninfo=	ServerXmlInfo.HeaderXml(server.name, address,"");
	   data=coninfo.getBytes();
	   //当前连接告诉客户端可以连接服务端了
		protocol.ServerSocketSendData(data);
		
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
