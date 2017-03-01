  
/**    
* 文件名：PackNetData.java    
*    
* 版本信息：    
* 日期：2017年3月1日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package OutDataMessage;

import DDS_Transfer.IDDS_Protocol;
import IPackData.DataModel;

import SubPackage.PackageModel;
import SubPackage.UnpackNet;

/**    
*     
* 项目名称：SubPackage    
* 类名称：PackNetData    
* 类描述：    传送数据时使用
* 创建人：jinyu    
* 创建时间：2017年3月1日 下午11:30:06    
* 修改人：jinyu    
* 修改时间：2017年3月1日 下午11:30:06    
* 修改备注：    
* @version     
*     
*/
public class PackNetData{

	/*
	* Title: SendData
	*Description: 调用该方法传送数据，进行分包
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
