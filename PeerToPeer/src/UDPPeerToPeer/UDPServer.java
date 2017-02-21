  
/**    
* 文件名：UDPServer.java    
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

/**    
*     
* 项目名称：PeerToPeerServer    
* 类名称：UDPServer    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月8日 上午12:55:12    
* 修改人：jinyu    
* 修改时间：2017年2月8日 上午12:55:12    
* 修改备注：    
* @version     
*     
*/
public class UDPServer {
public void StartServer(int port)
{
	try {
        DatagramSocket server = new DatagramSocket(2008);
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
         
        String sendMessage132 = "";
        String sendMessage129 = "";
        int port132 = 0;
        int port129 = 0;
        InetAddress address132 = null;
        InetAddress address129 = null;
        for (;;) {
            server.receive(packet);
             
            String receiveMessage = new String(packet.getData(), 0, packet.getLength());
            System.out.println(receiveMessage);
            //接收到clientA
            if (receiveMessage.contains("132")) {
                port132 = packet.getPort();
                address132 = packet.getAddress();
                sendMessage132 = "host:" + address132.getHostAddress() + ",port:" + port132;
            }
            //接收到clientB
            if (receiveMessage.contains("129")) {
                port129 = packet.getPort();
                address129 = packet.getAddress();
                sendMessage129 = "host:" + address129.getHostAddress() + ",port:" + port129;
            }
            //两个都接收到后分别A、B址地交换互发
            if (!sendMessage132.equals("") && !sendMessage129.equals("")) {
                send132(sendMessage129, port132, address132, server);
                send129(sendMessage132, port129, address129, server);
                sendMessage132 = "";
                sendMessage129 = "";
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

/** 
* @Name: send129 
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @param sendMessage132
* @param port129
* @param address129
* @param server  参数说明 
* @return void    返回类型 
* @throws 
*/
private void send129(String sendMessage132, int port132, InetAddress address132, DatagramSocket server) {
	 try {
         byte[] sendBuf = sendMessage132.getBytes();
         DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address132, port132);
         server.send(sendPacket);
         System.out.println("消息发送成功!");
     } catch (Exception e) {
         e.printStackTrace();
     }
	
}

/** 
* @Name: send132 
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @param sendMessage129
* @param port132
* @param address132
* @param server  参数说明 
* @return void    返回类型 
* @throws 
*/
private void send132(String sendMessage129, int port129, InetAddress address129, DatagramSocket server) {
	  try {
          byte[] sendBuf = sendMessage129.getBytes();
          DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address129, port129);
          server.send(sendPacket);
          System.out.println("消息发送成功!");
      } catch (Exception e) {
          e.printStackTrace();
      }
	
}
}
