  
/**    
* 文件名：UDPClient.java    
*    
* 版本信息：    
* 日期：2017年2月8日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package UDPPeerToPeer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**    
*     
* 项目名称：PeerToPeerClient    
* 类名称：UDPClient    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月8日 上午1:00:42    
* 修改人：jinyu    
* 修改时间：2017年2月8日 上午1:00:42    
* 修改备注：    
* @version     
*     
*/
public class UDPClient {
public void Start(int port)
{
	 try {
         // 向server发起请求
         SocketAddress target = new InetSocketAddress("10.1.11.137", 2008);
         DatagramSocket client = new DatagramSocket();
         String message = "I am UPDClinetA 192.168.85.132";
         byte[] sendbuf = message.getBytes();
         DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
         client.send(pack);
         // 接收请求的回复,可能不是server回复的，有可能来自UPDClientB的请求内
         receive(client);
     } catch (Exception e) {
         e.printStackTrace();
     }
}

/** 
* @Name: receive 
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @param client  参数说明 
* @return void    返回类型 
* @throws 
*/
private void receive(DatagramSocket client) {
	
	 try {
         for (;;) {
             byte[] buf = new byte[1024];
             DatagramPacket packet = new DatagramPacket(buf, buf.length);
             client.receive(packet);
             String receiveMessage = new String(packet.getData(), 0, packet.getLength());
             System.out.println(receiveMessage);
             int port = packet.getPort();
             InetAddress address = packet.getAddress();
             String reportMessage = "tks";
             //获取接收到请问内容后并取到地址与端口,然后用获取到地址与端口回复内容
             sendMessaage(reportMessage, port, address, client);
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
}

/** 
* @Name: sendMessaage 
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @param reportMessage
* @param port
* @param address
* @param client  参数说明 
* @return void    返回类型 
* @throws 
*/
private void sendMessaage(String reportMessage, int port, InetAddress address, DatagramSocket client) {
	 try {
         byte[] sendBuf = reportMessage.getBytes();
         DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
         client.send(sendPacket);
         System.out.println("消息发送成功!");
     } catch (Exception e) {
         e.printStackTrace();
     }
	
}
}
