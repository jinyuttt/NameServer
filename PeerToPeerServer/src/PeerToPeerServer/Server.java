  
/**    
* �ļ�����Server.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��11��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package PeerToPeerServer;

import java.io.IOException;

/**    
*     
* ��Ŀ���ƣ�PeerToPeerServer    
* �����ƣ�Server    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��11�� ����3:20:36    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��11�� ����3:20:36    
* �޸ı�ע��    
* @version     
*     
*/
public class Server {

	/** 
	* @Name: main 
	* @Description: TODO(������һ�仰�����������������) 
	* @param args  ����˵�� 
	* @return void    �������� 
	* @throws 
	*/
	public static void main(String[] args) {
		UDPServer ss=new UDPServer();
		ss.StartServer(6666);
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
