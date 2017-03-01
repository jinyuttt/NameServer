  
/**    
* 文件名：PackData.java    
*    
* 版本信息：    
* 日期：2017年2月26日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SubPackage;

import java.nio.ByteBuffer;
import java.util.HashMap;

import IPackData.DataModel;
import IPackData.IPackData;

/**    
*     
* 项目名称：SubPackage    
* 类名称：PackData    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月26日 下午11:59:19    
* 修改人：jinyu    
* 修改时间：2017年2月26日 下午11:59:19    
* 修改备注：    
* @version     
*     
*/
public class PackData {
	private byte[] leftdata=null;
	IPackData  callObj=null;
	private String addr="";
	private HashMap<Integer,PackageModel> packdata=new HashMap<Integer,PackageModel>();
	private long lastTime=0;//完成组包最后时间
	
	/**
	 * 
	* @Name: PackageData 
	* @Description: 组织数据
	* @param data  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public synchronized  void PackageData(byte[]data)
	{
		//按照顺序解析
		int len=data.length;
	   if(leftdata!=null&&leftdata.length>0)
	   {
		   len=len+leftdata.length;
	   }
		ByteBuffer databuffer=ByteBuffer.allocate(len);
		databuffer.put(leftdata);
		databuffer.put(data);
		//
		databuffer.flip();
		ReSet(databuffer);
	}
	/*
	 * 处理数据
	 */
	private void ReSet(ByteBuffer buffer)
	{
		int offset=buffer.position();
		//数据检查
		if(offset<buffer.limit())
		{
			//有部分数据可能
			if(buffer.limit()<offset+PackageConfig.headLen)
			{
				//头不够,直接保留
				int len=buffer.limit()-offset;
				leftdata=new byte[len];
				buffer.get(leftdata);
				return;
			}
		}

		 int flageid = buffer.getInt(PackageConfig.flageidIndex+offset);
		 int packagenum = buffer.getInt(PackageConfig.packageNumIndex+offset);
		

		 int datalen=buffer.getInt(PackageConfig.dataLenIndex+offset);
		 int rulelen=buffer.getInt(PackageConfig.ruleLenIndex+offset);

		 int curPackageLen=PackageConfig.headLen+datalen+rulelen;
		 if(offset+curPackageLen>buffer.limit())
		 {
			    //不够数据了，直接保存补充
			    buffer.position(offset);//回到当前头位置
			    int len=buffer.limit()-offset;
				leftdata=new byte[len];
				buffer.get(leftdata, buffer.position(), len);
				return;
		 }
		 //有数据解析
		 PackageModel model= packdata.getOrDefault(flageid, null);

		 if(model==null)
		 {
			 model=new PackageModel(packagenum);
			 byte[]bytes=new byte[curPackageLen];
			 buffer.get(bytes);
			 model.Package(bytes);
		 }
		 else
		 {
			 byte[]bytes=new byte[curPackageLen];
			 buffer.get(bytes);
			 model.Package(bytes);
		 }
		 //buffer位置已经增加
		 if(buffer.limit()>buffer.position())
		 {
		     //还有数据
			 ReSet(buffer);
		 }
	}
	/**
	 * 
	* @Name: CallData 
	* @Description: 组包完成
	* @param data  返回数据
	* @return void    返回类型 
	* @throws
	 */
	public void CallData(int id,DataModel data)
	{
		if(callObj!=null)
		{
			callObj.RecData(addr, data);
			Remove(id);
			lastTime=System.currentTimeMillis();
		}
	}
	/**
	 * 
	* @Name: NoteAddrObj 
	* @Description: 传递回调接口
	* @param addr   数据来源地址
	* @param callObj  回调实例
	* @return void    返回类型 
	* @throws
	 */
	public void  NoteAddrObj(String addr,IPackData  callObj)
	{
		this.addr=addr;
		this.callObj=callObj;
	}
	/**
	 * 
	* @Name: Remove 
	* @Description: 删除该数据包
	* @param flageid  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	private void Remove(int flageid)
	{
		PackageModel tmp=packdata.remove(flageid);
		tmp=null;
		
	}
	/**
	 * 
	* @Name: CheckAll 
	* @Description: 已经完成所有组包,可以清楚
	* @return  参数说明 
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean CheckAll()
	{
		if(System.currentTimeMillis()-this.lastTime>60000&&packdata.isEmpty())
		{
			//保留1分钟，以免频繁创建对象
			return true;
		}
		else
		{
			return false;
		}
	}
}
