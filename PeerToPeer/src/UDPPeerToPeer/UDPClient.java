  
/**    
* �ļ�����UDPClient.java    
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
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**    
*     
* ��Ŀ���ƣ�PeerToPeerClient    
* �����ƣ�UDPClient    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��8�� ����1:00:42    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��8�� ����1:00:42    
* �޸ı�ע��    
* @version     
*     
*/
public class UDPClient {
public void Start(int port)
{
	 try {
         // ��server��������
         SocketAddress target = new InetSocketAddress("10.1.11.137", 2008);
         DatagramSocket client = new DatagramSocket();
         String message = "I am UPDClinetA 192.168.85.132";
         byte[] sendbuf = message.getBytes();
         DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
         client.send(pack);
         // ��������Ļظ�,���ܲ���server�ظ��ģ��п�������UPDClientB��������
         receive(client);
     } catch (Exception e) {
         e.printStackTrace();
     }
}

/** 
* @Name: receive 
* @Description: TODO(������һ�仰�����������������) 
* @param client  ����˵�� 
* @return void    �������� 
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
             //��ȡ���յ��������ݺ�ȡ����ַ��˿�,Ȼ���û�ȡ����ַ��˿ڻظ�����
             sendMessaage(reportMessage, port, address, client);
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
}

/** 
* @Name: sendMessaage 
* @Description: TODO(������һ�仰�����������������) 
* @param reportMessage
* @param port
* @param address
* @param client  ����˵�� 
* @return void    �������� 
* @throws 
*/
private void sendMessaage(String reportMessage, int port, InetAddress address, DatagramSocket client) {
	 try {
         byte[] sendBuf = reportMessage.getBytes();
         DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
         client.send(sendPacket);
         System.out.println("��Ϣ���ͳɹ�!");
     } catch (Exception e) {
         e.printStackTrace();
     }
	
}
}
