  
/**    
* �ļ�����Client.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��11��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package PeerToPeerClient;

import java.io.IOException;

/**    
*     
* ��Ŀ���ƣ�PeerToPeerClient    
* �����ƣ�Client    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��11�� ����3:20:13    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��11�� ����3:20:13    
* �޸ı�ע��    
* @version     
*     
*/
public class Client {

	/** 
	* @Name: main 
	* @Description: TODO(������һ�仰�����������������) 
	* @param args  ����˵�� 
	* @return void    �������� 
	* @throws 
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         UDPClient cc=new UDPClient();
         cc.Start(6666);
         try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
