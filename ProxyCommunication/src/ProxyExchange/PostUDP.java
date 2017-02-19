  
/**    
* �ļ�����PostUDP.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��9��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package ProxyExchange;

import DDS_Transfer.IDDS_Protocol;
import ISearchInfo.ISearchPrxoy;
import ProtocolsManager.ProtocolManager;

/**    
*     
* ��Ŀ���ƣ�ProxyCommunication    
* �����ƣ�PostUDP    
* ��������    ���������Լ�UDP��ǽ
* ͨ������UDP��ǽ��
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��9�� ����1:56:06    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��9�� ����1:56:06    
* �޸ı�ע��    
* @version     
*     
*/
public class PostUDP {
private	IDDS_Protocol curObj=null;
private	IDDS_Protocol recUDP=null;
private	String walladdr="";//���������մ�ǽ׼���ɹ��ĵ�ַ
private  String beat="";//����������ַ
public   ISearchPrxoy search=null;
	public PostUDP(String addr,String beataddr)
	{
		walladdr=addr;
		beat=beataddr;
	}
	/**
	 * 
	* @Name: SendBeat 
	* @Description: ������������UDP���ͣ�
	* @param xml  ����˵�� 
	* @return void    �������� 
	* @throws
	 */
public void  SendBeat(String xml)
{
	if(curObj==null)
	{
		Object obj = null;
	
			try {
				obj = ProtocolManager.getInstance().CreateObject("UDP");
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			}
		
		  if(obj!=null)
		  {
			    curObj = (IDDS_Protocol)obj;
			    curObj.CreateClient();
			    String[]beataddr=beat.split(":");
			    curObj.Connect(beataddr[0], Integer.valueOf(beataddr[1]));
		  }
	}
	//��������
	byte[]data=xml.getBytes();
	curObj.ClientSocketSendData(data);
}

/**
 * 
* @Name: StartRec 
* @Description: ��ʼ���չ����������Ĵ�ǽ��Ϣ,���ҷ���׼���õ���Ϣ
* @return void    �������� 
* @throws
 */
public void StartRec()
{
	if(curObj==null)
	{
		Object obj = null;
	
			try {
				obj = ProtocolManager.getInstance().CreateObject("UDP");
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			}
		
		  if(obj!=null)
		  {
			    curObj = (IDDS_Protocol)obj;
			    curObj.CreateClient();
			    String[]beataddr=beat.split(":");
			    curObj.Connect(beataddr[0], Integer.valueOf(beataddr[1]));
		  }
	}
	if(curObj!=null)
	{
		Thread rec=new Thread(new Runnable()
				{

					@Override
					public void run() {
					while(true)
					{
						//�����˿ڽ��մ�ǽ��Ϣ
					 byte[]data=curObj.RecClientSocketData();
					 //���չ������������Ĵ�ǽ��ַ��������ǽ�ĵ�ַ
					 String wallInfo=new String(data);
					 String[]info=wallInfo.split("#");
					 //��ǽ�ڶ������ʹ�ǽ��Ϣ
					 if(search!=null)
					 {
						 String[]servername=info[1].split("@");
						 search.SendData(servername[0], info[0], data);
						
					 }
//					 if(recUDP==null)
//					 {
//						 Object obj=null;
//						 try {
//							 obj = ProtocolManager.getInstance().CreateObject("UDP");
//						} catch (InstantiationException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IllegalAccessException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						 if(obj!=null)
//						  {
//							 //ͨ����Ӧ�ķ����ʹ�ǽ
//							 recUDP = (IDDS_Protocol)obj;
//							 recUDP.CreateClient();
//							 String[]addr=info[0].split(":");
//							 recUDP.Connect(addr[0],Integer.valueOf(addr[1]));
//							 //
//							 byte[] test="test".getBytes();
//							 recUDP.ClientSocketSendData(test);
//						  }
//					 }
					 //
					 String localhost=recUDP.GetClientAddress();
					 String tmpaddr=info[1]+"#"+localhost;
					 //data=info[1].getBytes();//��׼���ķ���תһȦ
					 data=tmpaddr.getBytes();
					 recUDP.ClientSocketSendData(data);
					 //���߹�����׼�����ˣ�������Ϣ��������
					 try {
						 if(walladdr!=null)
						 {
						Object obj = ProtocolManager.getInstance().CreateObject("UDP");
						IDDS_Protocol fire=(IDDS_Protocol)obj;
						
						fire.SendData(walladdr, data);
						 }
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					}
				});
		rec.setDaemon(true);
		rec.setName("serverwallinfo");
		rec.start();
	}
}
///**
// * 
//* @Name: RecFireWallInf 
//* @Description: ���տͻ���ͨ����ǽ����������
//* @return void    �������� 
//* @throws
// */
//public void RecFireWallInf()
//{
//	if(recUDP==null)
//	{
//		Thread rec=new Thread(new Runnable()
//				{
//
//					@Override
//					public void run() {
//					while(true)
//					{
//					//��ǽ����
//					//Ҫ��������ϢЯ��������Ϣ
//					 byte[]data=recUDP.RecClientSocketData();
//					 System.out.println("����˽��յ��ͻ��˴���������");
//					 //
//					}
//					}
//				});
//		rec.setDaemon(true);
//		rec.setName("clientRequest");
//		rec.start();
//		//���տͻ��˴�ǽ��������Ϣ
//	}
//}
}
