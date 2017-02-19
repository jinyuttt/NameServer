  
/**    
* 文件名：ProceRequest.java    
*    
* 版本信息：    
* 日期：2017年1月17日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SeverManager;






import DDS_Transfer.IDDS_Protocol;

import PublicModel.ServerBinds;
import RequestServerInfo.AnalysisParam;
import RequestServerInfo.RequestModel;
import RequestServerInfo.ServerManagerType;
import Tools.IpV4Util;
import Tools.MsgPackTool;
import Tools.NetworkAll;
import nameServerClient.LocalServer;
import nameServerClient.ProxyClient;

import nameServerInterface.IPoxyObj;


/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：ProceRequest    
* 类描述：     解析传送的内容
* 创建人：jinyu    
* 创建时间：2017年1月17日 下午8:40:15    
* 修改人：jinyu    
* 修改时间：2017年1月17日 下午8:40:15    
* 修改备注：    
* @version     
*     
*/
public class ProcessRequest {
	
/**
 * 
* @Name: RespondClient 
* @Description: 接收客户端信息，处理请求
* @param address 来源
* @param data  数据（参数）
* @return  参数说明 
* @return byte[]    如果客户端是请求服务地址则返回地址，否则为null
* @throws
 */
public byte[]   RespondClient (String address,byte[]data)
{
	byte[] param=null;
	RequestModel model=AnalysisParam.GetParam(data);
	model.managerType=ServerManagerType.ServerIndirectMode;
	switch(model.managerType)
	{
	case ClientDirectMode:
	   break;
	case ServerDirectMode:
		ServerFun(model.serverName,model.dataParam);
		break;
	case ServerIndirectMode:
 		param=ClientFun(address,model.serverName);
 		
		break;
	default:
		break;
	}
	
	return param;
}

/**
 * 
* @Name: ServerFun 
* @Description: 获取代理传递数据 
* @param data  参数说明 
* void    返回类型 
* @throws
 */
public void ServerFun(String servername,byte[]data)
{
	StringBuilder error=new StringBuilder();
    IPoxyObj proxy=	ProxyClient.CastObj(servername, error);
    if(proxy!=null)
    {
        proxy.SetData(data);
    }
}

/**
 * 
* @Name: ClientFun 
* @Description:  获取服务信息，转出地址
* @param name  传来的参数
* @return  参数说明 
* @return byte[]    服务信息
* @throws
 */
public byte[] ClientFun(String addr,String serverName)
{
	 //String serverName=new String(name);
	 StringBuilder error=new StringBuilder();
	 if(serverName==null||serverName.length()==0||serverName.equals(" "))
	 {
		 serverName="初始化验证";
	 }
	 ServerBinds serverinfo=LocalServer.GetServerInfo().GetCur(serverName);
	 CheckWall(serverinfo);
	// String preRec= UDPwall(addr,serverinfo);
	// serverinfo.natAddr=preRec;
	 MsgPackTool tool=new MsgPackTool();
	 byte[]bytes= tool.Serialize(serverinfo, error);
	
	 return bytes;
	
}
///**
// * 
//* @Name: UDPwall 
//* @Description: 发送穿墙消息并且接收准备成功的消息
//* @param udpaddr
//* @param serverinfo  参数说明 
//* @return String    穿墙挖洞成功准备接受数据的地址 
//* @throws
// */
//private String UDPwall(String udpaddr,ServerBinds serverinfo)
//{
//	//通知穿墙准备
//	String key=serverinfo.name+":"+serverinfo.address+":"+serverinfo.port;
//	IDDS_Protocol protocol=	HeartBeatSocket.fireBeat.getOrDefault(key, null);
//	String recAddr="";
//	if(protocol!=null)
//	{
//		String wallInfo= udpaddr+"#"+key;
//		byte[] data =wallInfo.getBytes();
//		//告诉服务端穿墙
//		protocol.ServerSocketSendData(data);
//		//
//	while(true)
//	 {
//	   try {
//		 //取出接收穿墙成功的服务
//		String info=FireWallPack.sucess.poll(4, TimeUnit.SECONDS);
//		if(info==null)
//		{
//			//再次发给服务端，再次发送穿墙包
//			protocol.ClientSocketSendData(data);
//			InnerMessage.getInstance().PostMessage(this, "AddUILog", "通知已经发送穿墙包!");
//		}
//		String[] tmpaddr=info.split("#");
//		
//		if(tmpaddr[0].equals(key))
//		{
//			recAddr=tmpaddr[1];
//			//已经接收到服务端发来的穿墙通知
//			break;
//		}
//		else
//		{
//			//不是当前服务的包再次存入，供另外的线程继续使用判断
//			FireWallPack.sucess.put(info);
//		}
//		   
//		 } catch (InterruptedException e) {
//		
//		e.printStackTrace();
//	      }
//	 }
//	}
//	return recAddr;
//}

/**
 * 
* @Name: CheckWall 
* @Description:检查是否需要穿墙
* @param serverinfo  服务信息
* @return void    返回类型 
* @throws
 */
private void CheckWall(ServerBinds serverinfo)
{
	String key=serverinfo.name+":"+serverinfo.address+":"+serverinfo.port;
	IDDS_Protocol protocol=	HeartBeatSocket.fireBeat.getOrDefault(key, null);
	String addr=protocol.GetClientAddress();//获取客户端通讯IP
	String[] addrport=addr.split(":");
	//if(!addrport[0].equals(serverinfo.address)&&serverinfo.address.equals("127.0.0.1"))
	//如果不是同一网段，则要穿墙
	String clientaddr=addrport[0];
	clientaddr=clientaddr.replaceAll("/", "").trim();
	if(NetworkAll.IsLocalIP(clientaddr))
	{
		//如果同一台机器；
		serverinfo.natAddr="";
		return;
	}
	if(NetworkAll.internalIp(clientaddr))
	{
		//如果是内网地址，则是同一路由
		serverinfo.natAddr="";
		return;
	}
	if(!IpV4Util.checkSameSegmentByDefault(serverinfo.address,clientaddr))
	{
		//如果客户端的IP与服务IP不同网段，则需要穿墙
		//给客户端赋予管理器连接的穿墙地址
		String addrnat="";
		if(serverinfo.communicationType.equals("TCP"))
		{
			addrnat=ManagerAddrInfo.hashMap.getOrDefault("ManagerTCPNAT", null);
			if(addrnat!=null)
			{
				serverinfo.natAddr=addrnat;
			}
		   
		}
		else
		{
			addrnat=ManagerAddrInfo.hashMap.getOrDefault("ManagerUDPNAT", null);
			if(addrnat!=null)
			{
				serverinfo.natAddr=addrnat;
			}
		}
	}
	else
	{
		serverinfo.natAddr="";
	}
	
}


}
