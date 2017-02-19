  
/**    
* �ļ�����RecviceTCPNatPackage.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��14��    
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

/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�RecviceTCPNatPackage    
* ��������    TCP���մ�ǽ�ĵ�ַ
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��14�� ����2:05:06    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��14�� ����2:05:06    
* �޸ı�ע��    
* @version     
*     
*/
public class RecviceTCPNatPackage implements IRecMsg{

	private IDDS_Protocol protocol;

	/*
	* Title: RecData
	*Description: ���շ����TCP���ձ���ͨѶ״̬
	*׼����ǽ
	* @param address
	* @param data 
	 
	*/
	@Override
	public void RecData(String address, byte[] data) {
		String xmlStr =new String(data);
		ServerXml info=ServerXmlInfo.Analysis(xmlStr);
		if(info==null)
		{
			InnerMessage.getInstance().PostMessage(this, "AddUILog", "������ʽ����ȷ:"+xmlStr);
			return;
		}
		String key=info.name+":"+info.address;
	    HeartBeatSocket.tcpNatBeat.put(key, protocol);
		
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
