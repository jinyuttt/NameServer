  
/**    
* �ļ�����RecviceClientRequest.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��12��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import ProcessMessage.InnerMessage;
import Tools.MsgPackTool;
import nameServerInterface.NetData;


/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�RecviceClientRequest    
* ��������    ���ܿͻ�������Ĵ���
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��12�� ����12:45:30    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��12�� ����12:45:30    
* �޸ı�ע��    
* @version     
*     
*/
public class RecviceClientRequest implements IRecMsg {
	IDDS_Protocol  protocol=null;
	
	
/**
 *  ���մ�����
 */
	@Override
	public void RecData(String address, byte[] data) {
		 StringBuilder error=new StringBuilder();
		ProcessRequest  proce=new ProcessRequest();
		System.out.println("���տͻ�������");
		MsgPackTool tool=new MsgPackTool();
		InnerMessage.getInstance().PostMessage(this, "AddUILog", "���տͻ�������!");
		NetData netd=tool.Deserialize(data, NetData.class, error);
	   byte[] param=proce.RespondClient(address, netd.data);
	if(param!=null)
	{
		
		if(protocol!=null)
		{
			protocol.ServerSocketSend(param);
			System.out.println("���͸��ͻ���");
			protocol.ClientSocketClose();  
		}
	}
	}

	  
	/**
	 * ����ͨѶʵ��
	 */
	@Override
	public void SaveInstance(Object call) {
		protocol=(IDDS_Protocol)call;
		
	}

}

