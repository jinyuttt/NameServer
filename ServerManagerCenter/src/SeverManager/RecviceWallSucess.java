  
/**    
* �ļ�����RecviceUDP.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��8��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SeverManager;

import java.util.concurrent.TimeUnit;

import DDS_Transfer.IRecMsg;

/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�RecviceUDP    
* ��������   ������ǽ�ɹ�����Ϣ
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��8�� ����12:04:41    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��8�� ����12:04:41    
* �޸ı�ע��    
* @version     
*     
*/
public class RecviceWallSucess implements IRecMsg
{

	/*
	 * 
	* Title: RecData
	*Description: ��������
	* @param address
	* @param data
	 */
	@Override
	public void RecData(String address, byte[] data) {
	String server=new String(data);
	//��ǽ׼���õķ�����Ϣ
	try {
		//3�뻹û�д��������������
		//�Ѵ�ǽ�ɹ�����Ϣ�������
	 boolean r=	FireWallPack.sucess.offer(server, 3, TimeUnit.SECONDS);
	 if(!r)
	 {
		 //�̰߳�ȫ���У���СҪ����ʵ�����
		 FireWallPack.sucess.clear();
	 }
	} catch (InterruptedException e) {
	
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
		// TODO Auto-generated method stub
		
	}

}