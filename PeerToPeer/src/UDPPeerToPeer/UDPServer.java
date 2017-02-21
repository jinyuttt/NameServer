  
/**    
* �ļ�����UDPServer.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��8��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package UDPPeerToPeer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**    
*     
* ��Ŀ���ƣ�PeerToPeerServer    
* �����ƣ�UDPServer    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��8�� ����12:55:12    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��8�� ����12:55:12    
* �޸ı�ע��    
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
            //���յ�clientA
            if (receiveMessage.contains("132")) {
                port132 = packet.getPort();
                address132 = packet.getAddress();
                sendMessage132 = "host:" + address132.getHostAddress() + ",port:" + port132;
            }
            //���յ�clientB
            if (receiveMessage.contains("129")) {
                port129 = packet.getPort();
                address129 = packet.getAddress();
                sendMessage129 = "host:" + address129.getHostAddress() + ",port:" + port129;
            }
            //���������յ���ֱ�A��Bַ�ؽ�������
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
* @Description: TODO(������һ�仰�����������������) 
* @param sendMessage132
* @param port129
* @param address129
* @param server  ����˵�� 
* @return void    �������� 
* @throws 
*/
private void send129(String sendMessage132, int port132, InetAddress address132, DatagramSocket server) {
	 try {
         byte[] sendBuf = sendMessage132.getBytes();
         DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address132, port132);
         server.send(sendPacket);
         System.out.println("��Ϣ���ͳɹ�!");
     } catch (Exception e) {
         e.printStackTrace();
     }
	
}

/** 
* @Name: send132 
* @Description: TODO(������һ�仰�����������������) 
* @param sendMessage129
* @param port132
* @param address132
* @param server  ����˵�� 
* @return void    �������� 
* @throws 
*/
private void send132(String sendMessage129, int port129, InetAddress address129, DatagramSocket server) {
	  try {
          byte[] sendBuf = sendMessage129.getBytes();
          DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address129, port129);
          server.send(sendPacket);
          System.out.println("��Ϣ���ͳɹ�!");
      } catch (Exception e) {
          e.printStackTrace();
      }
	
}
}
