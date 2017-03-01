  
/**    
* �ļ�����PackNetData.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��3��1��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package OutDataMessage;

import DDS_Transfer.IDDS_Protocol;
import IPackData.DataModel;

import SubPackage.PackageModel;
import SubPackage.UnpackNet;

/**    
*     
* ��Ŀ���ƣ�SubPackage    
* �����ƣ�PackNetData    
* ��������    ��������ʱʹ��
* �����ˣ�jinyu    
* ����ʱ�䣺2017��3��1�� ����11:30:06    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��3��1�� ����11:30:06    
* �޸ı�ע��    
* @version     
*     
*/
public class PackNetData{

	/*
	* Title: SendData
	*Description: ���ø÷����������ݣ����зְ�
	* @param procotol
	* @param data 
	 
	*/
	
	public void SendData(IDDS_Protocol procotol, DataModel data) {
		//
		UnpackNet unpack=new UnpackNet();
		PackageModel  model=unpack.SendBytes(data);
		while(true)
		{
		 byte[]bytes= model.get();
		 if(bytes==null)
		 {
			 
			 break;
		 }
		 else
		 {
			 procotol.ClientSocketSendData(bytes);
		 }
		}
		
	}

	

		
	

}
