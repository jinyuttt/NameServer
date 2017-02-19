  
/**    
* �ļ�����RecviceHeartbeat.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��8��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SeverManager;



import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import ProcessMessage.InnerMessage;
import UDPPeerToPeer.ServerXml;
import UDPPeerToPeer.ServerXmlInfo;
import nameServerContainer.CheckServerAction;


/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�RecviceHeartbeat    
* ��������    ��������(XMl��ʽ)
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��8�� ����10:57:33    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��8�� ����10:57:33    
* �޸ı�ע��    
* @version     
*     
*/
public class RecviceHeartbeat  implements IRecMsg{

	private IDDS_Protocol protocol=null;

	/*
	* Title: RecData
	*Description: 
	* @param address
	* @param data 
	 
	*/
	@Override
	public void RecData(String address, byte[] data) {
		
		String xmlStr =new String(data);
		ServerXml info=ServerXmlInfo.Analysis(xmlStr);

		try {

			if(info==null)
			{
				InnerMessage.getInstance().PostMessage(this, "AddUILog", "������ʽ����ȷ:"+xmlStr);
				return;
			}
			String key=info.name+":"+info.address;
		    HeartBeatSocket.fireBeat.put(key, protocol);
		    //��������
		    //���Բ��м�����״̬
		    CheckServerAction.CheckAction(info.name, info.address);
		    InnerMessage.getInstance().PostMessage(this, "AddUILog", "��������");
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}

	/*
	* Title: SaveInstance
	*Description: 
	* @param call 
	 
	*/
	@Override
	public void SaveInstance(Object call) {
		protocol=(IDDS_Protocol)call;
		
	}

}
