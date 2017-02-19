package ProxyExchange;

//import org.zeromq.ZMQ;
//import org.zeromq.ZMQ.Context;
//import org.zeromq.ZMQ.Socket;

import DDS_Transfer.IDDS_Protocol;
import ProtocolsManager.ProtocolManager;


/**
 * 接收客户端调用
 * 
 * @author jinyu
 *
 */
public class  ClientToServer {

	 //Context context = ZMQ.context(1);
	 //IDataCall thecall=null;
	 public String ip;
	 public int port;
	 public String type_Name;
	 IDDS_Protocol curObj=null;
	 DataRecvice rec=new DataRecvice();
	 /**
	  * 
	 * @Name: InitServer 
	 * @Description: 启动服务通讯
	 * @param call 服务回传
	 * @param error  参数说明 
	 * @return void    返回类型 
	 * @throws
	  */
	  public void InitServer(IDataCall call, StringBuilder error) {
		 // thecall=call;
		  rec.calldata=call;
		  try {
			  Object obj=ProtocolManager.getInstance().CreateObject(type_Name);
			  if(obj!=null)
			  {
				   curObj = (IDDS_Protocol)obj;
				  
				  curObj.RecData(ip+":"+port, rec);
				
			  }	
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		  
//		Thread rec1=new Thread(new Runnable()
//		{
//	public void run() {
//	
//		      Socket frontend = context.socket(ZMQ.ROUTER);
//			  Socket backend=context.socket(ZMQ.DEALER);
//			  String tcpCon="tcp://"+ip+":"+port;
//			  frontend.bind(tcpCon);
//			  backend.bind("inproc://rec");
//			  
//			  InitWork();
//	          ZMQ.proxy(frontend, backend, null);
//	        //
//	         
//		
//	}
//
//
//		});
//rec1.setDaemon(true);
//rec1.setName("recNetData");
//rec1.start();
	}
      
	  /**
	   * 
	  * @Name: ProxySendNatPackage 
	  * @Description: 服务绑定地址发送NAT包
	  * @param addr  管理器地址
	  * @param port  管理器地址端口 
	  * @return void    返回类型 
	  * @throws
	   */
	  public void ProxySendNatPackage(String addr,int port)
	  {
		  byte[]data="NatBeat".getBytes();
		  if(curObj!=null)
		  {
			  try
			  {
			  curObj.Connect(addr, port);
			  }
			  catch(Exception ex)
			  {
				  System.out.println(ex.getMessage());
				  
			  }
			  try
			  {
				 
				  curObj.ClientSocketSendData(data);
			  }
			  catch(Exception ex)
			  {
				  System.out.println(ex.getMessage());
			  }
			  try
			  {
				 
				  curObj.ServerSocketSendData(data);
			  }
			  catch(NullPointerException e)
			  {
				  
			  }
			  catch(Exception ex)
			  {
				  System.out.println(ex.getMessage());
			  }
		  }
	  }
	  
}
