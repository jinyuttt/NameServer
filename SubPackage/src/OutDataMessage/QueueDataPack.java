  
/**    
* �ļ�����QueueDataPack.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��27��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package OutDataMessage;

import java.util.concurrent.LinkedBlockingQueue;

import IPackData.IPackData;
import SubPackage.SubPackageData;

/**    
*     
* ��Ŀ���ƣ�SubPackage    
* �����ƣ�QueueDataPack    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��27�� ����12:50:31    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��27�� ����12:50:31    
* �޸ı�ע��    
* @version     
*     
*/
public class QueueDataPack {
private 	 LinkedBlockingQueue<SubPackageData> queue=new LinkedBlockingQueue<SubPackageData>(3000);
private  volatile boolean isInit=false;//�������
private  Object lock_obj=new Object();//ͬ������
private  IPackData  call=null;
public QueueDataPack(IPackData rec )
{
	 call=rec;
}

/**
 * 
* @Name: AddQueueDataPack 
* @Description: �������ݻ���
* @param addr ���յ�ַ
* @param data  ����
* @return void    �������� 
* @throws
 */
	public  void AddQueueDataPack(String addr,byte[]data)
	{
		SubPackageData pdata=new SubPackageData();
		pdata.addr=addr;
		pdata.data=data;
		try {
			queue.put(pdata);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!isInit)
		{
			synchronized(lock_obj)
			{
				if(!isInit)
				{
			       isInit=true;
			       Start();
				}
			}
		}
	}
	
	/**
	 * 
	* @Name: Start 
	* @Description: �����̴߳������ݣ����
	* @return void    �������� 
	* @throws
	 */
	private   void Start()
	{
		RecPackData qpack=new RecPackData();
		Thread rec=new Thread(new Runnable()
				{

					@Override
					public void run() {
						try {
							SubPackageData tmp=queue.take();
							qpack.AddDataPack(call, tmp.addr, tmp.data);
							tmp=null;
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
						
						
					}
			  
				});
		rec.setDaemon(true);
		rec.setName("PackData");
		rec.start();
	}
}
