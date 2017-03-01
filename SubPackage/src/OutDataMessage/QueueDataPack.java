  
/**    
* 文件名：QueueDataPack.java    
*    
* 版本信息：    
* 日期：2017年2月27日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package OutDataMessage;

import java.util.concurrent.LinkedBlockingQueue;

import IPackData.IPackData;
import SubPackage.SubPackageData;

/**    
*     
* 项目名称：SubPackage    
* 类名称：QueueDataPack    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月27日 上午12:50:31    
* 修改人：jinyu    
* 修改时间：2017年2月27日 上午12:50:31    
* 修改备注：    
* @version     
*     
*/
public class QueueDataPack {
private 	 LinkedBlockingQueue<SubPackageData> queue=new LinkedBlockingQueue<SubPackageData>(3000);
private  volatile boolean isInit=false;//标记启动
private  Object lock_obj=new Object();//同步变量
private  IPackData  call=null;
public QueueDataPack(IPackData rec )
{
	 call=rec;
}

/**
 * 
* @Name: AddQueueDataPack 
* @Description: 接收数据缓存
* @param addr 接收地址
* @param data  数据
* @return void    返回类型 
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
	* @Description: 开启线程处理数据，组包
	* @return void    返回类型 
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
