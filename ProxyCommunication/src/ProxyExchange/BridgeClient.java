package ProxyExchange;

import org.zeromq.ZMQ;

import DDS_Transfer.IDDS_Protocol;
import ProtocolsManager.ProtocolManager;



/**
 * 发起服务请求
 * 通过特定的名称创建与服务端的通讯连接
 * 
 * @author jinyu
 *
 */
public class BridgeClient {
public String IP="";
public int port=0;
public String type_name;
String url="";
ZMQ.Context context;
ZMQ.Socket socket;
IDDS_Protocol curObj=null;

public void Create()
{
	try
	{
//     context=ZMQ.context(1);
//     socket=context.socket(ZMQ.REQ);
//	 url="tcp://"+IP+":"+port;
//	 socket.connect(url);
		 Object obj=ProtocolManager.getInstance().CreateObject(type_name);
		  if(obj!=null)
		  {
			    curObj = (IDDS_Protocol)obj;
			    curObj.CreateClient();
			   // curObj.SendData(IP+":"+port, data);
		  }
	        
	}
	catch(Exception ex)
	{
    	
	}
}
/**
 * 连接服务端
 * @return
 */
public boolean Connect()
{
   if(curObj!=null)
   {
	 return  curObj.Connect(IP, port);
   }
return false;
}
public boolean Connect(String remoteAddr,int remotePort)
{
   if(curObj!=null)
   {
	   return  curObj.Connect(remoteAddr, remotePort);
   }
return false;
}
public void Close()
{
	if(curObj!=null)
	   {
		curObj.ClientSocketClose();
	   }
}
public byte[]  RecData(byte[]para)
{
	if(curObj!=null)
	{
		 curObj.ClientSocketSendData(para);
		byte[] bytes= curObj.RecClientSocket();
		System.out.println("接收服务端信息");
		return bytes;
	}
	return null;
//	if(socket!=null)
//	{
//		socket.send(para);
//	    return	socket.recv();
//	}
//	else
//	{
//		return null;
//	}
	
}
public void SendData(byte[]data)
{
//	if(socket!=null)
//	{
//	  	socket.send(data,ZMQ.NOBLOCK);
//	}
	
	if(curObj!=null)
	{
		 curObj.SendData(IP+":"+port, data);
	}
}
}
