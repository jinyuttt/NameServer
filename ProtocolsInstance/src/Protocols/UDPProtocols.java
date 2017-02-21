package Protocols;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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
		DatagramSocket serverSocket = null;
		DatagramSocket clientSocket=null;
		private boolean isRun=true;
		DatagramPacket servercall=null;
		/**
		 * 
		* @Name: SendMsg 
		* @Description: 向该IP,端口发送数据发送数据并且关闭
		* @param ip
		* @param port
		* @param data
		* @throws IOException
		* @throws Exception  参数说明 
		* @return void    返回类型 
		* @throws
		 */
      private void SendMsg(String ip,int port,byte[]data) throws IOException, Exception
		{
	      
	        DatagramSocket client = new DatagramSocket(0);
		    DatagramPacket dp=new DatagramPacket(data, data.length,InetAddress.getByName(ip),port);
		     client.send(dp);
		     curLocalAddress= client.getLocalAddress().getHostAddress()+":"+client.getLocalPort();
		     client.close();
		}
/**
 *  向该地址发送数据
 */
	@Override
	public boolean SendData(String adress, byte[] data) {
		String[] addr=adress.split(":");
		if(addr.length==2)
		{
			String serverIP=addr[0];
			int port=Integer.valueOf(addr[1]);
			try {
				SendMsg(serverIP,port,data);
				return true;
			} catch (IOException e) {
			  System.out.println("数据通信失败,IP:"+serverIP+",端口："+port);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return false;
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
		while(isRun)
		{
			try {
				serverSocket=listen;
				byte [] buf = new byte[recBuffersize]; 
				byte[] recall=new byte[0];
	            DatagramPacket dp = new DatagramPacket(buf,buf.length); 
	            listen.receive(dp);
	            servercall=new  DatagramPacket(recall, recall.length,dp.getSocketAddress());
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
		curRec.SaveInstance(this);
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
		    isRun=false;
		    serverSocket.close();
			
		}
		
	}
	  

	  

	/**
	 * 关闭socket
	 */
	@Override
	public void ClientSocketClose() {
	if(clientSocket!=null)
	{
		clientSocket.close();
	}
		
	}
	  
	/**
	 * 创建Socket，绑定该地址
	 */
	@Override
	public void CreateClient(String ip, int port) {
		try {
			InetAddress point = null;
			try {
				point = InetAddress.getByName(ip);
			} catch (UnknownHostException e) {
				
				e.printStackTrace();
			} 
			//SocketAddress endpoint=new SocketAddress ();
			clientSocket=new DatagramSocket(port,point);
		} catch (SocketException e) {
			
			e.printStackTrace();
		}
		
	}
	  
	
	/**
	 * 发送数据并且关闭
	 */
	@Override
	public void ClientSocketSend(byte[] data) {
		if(clientSocket!=null)
		{
			DatagramPacket p=new DatagramPacket(data, data.length);
			try {
				clientSocket.send(p);
				clientSocket.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	  
	/**
	 * 服务端反向发送数据，发送给客户端
	 */
	@Override
	public void ServerSocketSend(byte[] data) {
	if(serverSocket!=null)
	{
		if(servercall!=null)
		{
		try {
			DatagramPacket dp=new DatagramPacket(data,data.length,servercall.getSocketAddress());
			serverSocket.send(dp);
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		}
	}
		
	}
	/**
	 * 反向客户端接收数据
	 * 接收到数据后关闭
	 */
	@Override
	public byte[] RecClientSocket() {
		if(clientSocket!=null)
		{
			byte [] buf = new byte[recBuffersize]; 
            DatagramPacket dp = new DatagramPacket(buf,recBuffersize); 
		
			try {
				
				 clientSocket.receive(dp);
				 int r= dp.getLength();
			     byte[]realData=new byte[r];
			     System.arraycopy(dp.getData(), 0, realData, 0, r);
			     clientSocket.close();
			     return realData;
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	/**
	 * 创建客户端
	 */
	@Override
	public void CreateClient() {
		try {
			
			//SocketAddress endpoint=new SocketAddress ();
			clientSocket=new DatagramSocket();
		} catch (SocketException e) {
			
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 客户端绑定该地址
	 */
	@Override
	public boolean BindLocal(String locahost, int port) {
		if(clientSocket!=null)
		{
			
			SocketAddress addr=new InetSocketAddress(locahost,port);
			try {
				clientSocket.bind(addr);
				return true;
			} catch (SocketException e) {
				
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 链接该地址
	 */
	@Override
	public boolean Connect(String remoteaddr, int port) {
		if(clientSocket!=null)
		{
			SocketAddress addr=new InetSocketAddress(remoteaddr,port);
			try {
				clientSocket.connect(addr);
				return true;
			} catch (SocketException e) {
				
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	/**
	 * 客户端发送数据并且不关闭
	 */
	@Override
	public void ClientSocketSendData(byte[] data) {
		
		if(clientSocket!=null)
		{
			DatagramPacket p=new DatagramPacket(data, data.length);
			try {
				clientSocket.send(p);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	/**
	 * 服务端发数据并且不关闭客户端
	 */
	@Override
	public void ServerSocketSendData(byte[] data) {
		if(serverSocket!=null)
		{
			DatagramPacket p=new DatagramPacket(data, data.length,servercall.getSocketAddress());;
			try {
				serverSocket.send(p);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 设置socket缓存大小
	 */
	@Override
	public void SetSocketBufferSize(int size) {
		 socketBuffersize=size;
		
		
	}
	/**
	 * 设置接收缓存
	 */
	@Override
	public void SetRecBufferSize(int size) {
		 recBuffersize=size;
		
	}
	/**
	 * 接收客户端数据,接收后不关闭
	 */
	@Override
	public byte[] RecClientSocketData() {
		
		if(clientSocket!=null)
		{
			byte [] buf = new byte[recBuffersize]; 
            DatagramPacket dp = new DatagramPacket(buf,recBuffersize); 
		
			try {
				
				 clientSocket.receive(dp);
				 int r= dp.getLength();
			     byte[]realData=new byte[r];
			     System.arraycopy(dp.getData(), 0, realData, 0, r);
			  
			     return realData;
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		return null;
	}
	/*
	* Title: GetClientAddress
	*Description: 
	* @return 
	 
	*/
	@Override
	public String GetClientAddress() {
		if(clientSocket!=null)
		{
			String addr=clientSocket.getLocalAddress().getHostAddress()+":"+clientSocket.getLocalPort();
			return addr;
		}
		else
		{
			if(servercall!=null)
			{
				String addr=servercall.getAddress()+":"+servercall.getPort();
				return addr;
			}
		}
		return null;
	}
	/*
	* Title: DisConnect
	*Description:  
	 
	*/
	@Override
	public void DisConnect() {
		// TODO Auto-generated method stub
		
	}

}
