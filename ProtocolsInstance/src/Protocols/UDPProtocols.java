package Protocols;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import DDS_Transfer.ProtocolType;

@ProtocolType(Name="UDP")
public class UDPProtocols implements IDDS_Protocol{
	//保存接收数据的注册
		ConcurrentHashMap<String,List<IRecMsg>> hmap=new ConcurrentHashMap<String,List<IRecMsg>>();
		private	String curBindAddress="";
		private String curLocalAddress="";
		IRecMsg curRec=null;
		int socketBuffersize=32;
		int recBuffersize=1024;
		ServerSocket serverSocket = null;
		Socket clientSocket=null;
private void SendMsg(String ip,int port,byte[]data) throws IOException, Exception
		{
	System.out.println("发送IP:"+ip);
	System.out.println("发送端口:"+port);
	        DatagramSocket client = new DatagramSocket(0);
		    DatagramPacket dp=new DatagramPacket(data, data.length,InetAddress.getByName(ip),port);
		     client.send(dp);
		     
		       curLocalAddress= client.getLocalAddress().getHostAddress()+":"+client.getLocalPort();
		       client.close();
		}
	@Override
	public boolean SendData(String adress, byte[] data) {
		String[] addr=adress.split(":");
		if(addr.length==2)
		{
			String serverIP=addr[0];
			int port=Integer.valueOf(addr[1]);
			// TCP发送
			try {
				SendMsg(serverIP,port,data);
			} catch (IOException e) {
			  System.out.println("数据通信失败,IP:"+serverIP+",端口："+port);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		return true;
	}

	@Override
	public void RecData(String Address, IRecMsg rec) {
		
		 curRec=rec;
			//避免同一实例启动
			SoeckRec(Address);
		
	}
/**
 * 开启网络接收
 * @param Address
 */
private void SoeckRec(String Address)
{
	Thread rec1=new Thread(new Runnable()
	{
@SuppressWarnings("resource")
public void run() {
	String[] addr=Address.split(":");
	curBindAddress=Address;
	if(addr.length==2)
	{
		String serverIP=addr[0];
		int port=Integer.valueOf(addr[1]);
		DatagramSocket  listen = null;
		if(serverIP.equals("*"))
		{
			   try {
				listen = new DatagramSocket();
				listen.setReuseAddress(true);
			    listen.bind(new InetSocketAddress(port));
				  System.out.println("绑定端口:"+port);
			} 
			   catch (IOException e) 
			   {
				   System.out.println(e.getLocalizedMessage());
				
				e.printStackTrace();
			}
		}
		else
		{
	      
			try {
				 listen = new DatagramSocket (null);
				 listen.setReuseAddress(true);
				 SocketAddress endpoint = new InetSocketAddress(serverIP, port); 
				 listen.bind(endpoint);
				 System.out.println("绑定端口IP:"+port+";"+serverIP);
				 
			} catch (IOException e) {
				 System.out.println(e.getLocalizedMessage());
				 System.out.println(e.getMessage());
				 System.out.println(e);
				e.printStackTrace();
			}
	    }
		while(true)
		{
			try {
				byte [] buf = new byte[1024]; 
	            DatagramPacket dp = new DatagramPacket(buf,1024); 
	            listen.receive(dp);
			    String ip = dp.getAddress().getHostAddress();
		        int remoteport=dp.getPort();
				int r= dp.getLength();
			     byte[]realData=new byte[r];
				 System.arraycopy(dp.getData(), 0, realData, 0, r);
					 //没有考虑分包或接收不全
					 CallData(ip,remoteport,realData);
			} catch (IOException e) {
				 System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
			}
	
           
		}

	}
}
	});
rec1.setDaemon(true);
rec1.setName("recUDPDDSData");
rec1.start();
}
/**
 * 解析数据
 * @param src
 * @param port
 * @param data
 */
private void CallData(String src,int port,byte[]data)
{
	if(curRec!=null)
	{
		String addr=src+":"+port;
		curRec.RecData(addr, data);
	}

}


	@Override
	public String GetBindAddress() {
		
		return curBindAddress;
	}

	@Override
	public void SetBindAddress(String addr) {
		// TODO Auto-generated method stub
		//curBindAddress=addr;
	}
	@Override
	public String GetLocalAddress() {
		
		return curLocalAddress;
	}
	  
	/**  
	* (non-Javadoc)    
	* @see DDS_Transfer.IDDS_Protocol#Close()    
	*/  
	
	@Override
	public void Close() {
		if(serverSocket!=null)
		{
		     try {
		    	// isRun=false;
				 serverSocket.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	  

	  
	/**  
	* (non-Javadoc)    
	* @see DDS_Transfer.IDDS_Protocol#ClientSocketClose()    
	*/  
	
	@Override
	public void ClientSocketClose() {
		// TODO Auto-generated method stub
		
	}
	  
	/**  
	* (non-Javadoc)    
	* @see DDS_Transfer.IDDS_Protocol#CreateClient(java.lang.String, int)    
	*/  
	
	@Override
	public void CreateClient(String ip, int port) {
		// TODO Auto-generated method stub
		
	}
	  
	/**  
	* (non-Javadoc)    
	* @see DDS_Transfer.IDDS_Protocol#ClientSocketSend(byte[])    
	*/  
	
	@Override
	public void ClientSocketSend(byte[] data) {
		// TODO Auto-generated method stub
		
	}
	  
	/**  
	* (non-Javadoc)    
	* @see DDS_Transfer.IDDS_Protocol#ServerSocketSend(byte[])    
	*/  
	
	@Override
	public void ServerSocketSend(byte[] data) {
		// TODO Auto-generated method stub
		
	}
	  
	/**  
	* (non-Javadoc)    
	* @see DDS_Transfer.IDDS_Protocol#RecClientSocket()    
	*/  
	
	@Override
	public byte[] RecClientSocket() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	* Title: CreateClient
	*Description:  
	 
	*/
	@Override
	public void CreateClient() {
		// TODO Auto-generated method stub
		
	}
	/*
	* Title: BindLocal
	*Description: 
	* @param locahost
	* @param port
	* @return 
	 
	*/
	@Override
	public boolean BindLocal(String locahost, int port) {
		// TODO Auto-generated method stub
		return false;
	}
	/*
	* Title: Connect
	*Description: 
	* @param remoteaddr
	* @param port
	* @return 
	 
	*/
	@Override
	public boolean Connect(String remoteaddr, int port) {
		// TODO Auto-generated method stub
		return false;
	}
	/*
	* Title: ClientSocketSendData
	*Description: 
	* @param data 
	 
	*/
	@Override
	public void ClientSocketSendData(byte[] data) {
		// TODO Auto-generated method stub
		
	}
	/*
	* Title: ServerSocketSendData
	*Description: 
	* @param data 
	 
	*/
	@Override
	public void ServerSocketSendData(byte[] data) {
		// TODO Auto-generated method stub
		
	}
	/*
	* Title: SetSocketBufferSize
	*Description: 
	* @param size 
	 
	*/
	@Override
	public void SetSocketBufferSize(int size) {
		 socketBuffersize=size;
		
		
	}
	/*
	* Title: SetRecBufferSize
	*Description: 
	* @param siez 
	 
	*/
	@Override
	public void SetRecBufferSize(int size) {
		 recBuffersize=size;
		
	}

}
