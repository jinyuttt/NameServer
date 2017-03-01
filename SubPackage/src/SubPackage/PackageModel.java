  
/**    
* 文件名：PackageModel.java    
*    
* 版本信息：    
* 日期：2017年2月22日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SubPackage;

import java.nio.ByteBuffer;

import IPackData.DataModel;


/**    
*     
* 项目名称：SubPackage    
* 类名称：PackageModel    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月22日 下午9:43:02    
* 修改人：jinyu    
* 修改时间：2017年2月22日 下午9:43:02    
* 修改备注：    
* @version     
*     
*/
public class PackageModel {
	public PackageModel(int num)
	{
		data=new byte[num][];
		packagenum=num;
	}
	//总长，flageid,packagenum,类型,packageid,数据长度，规则长度，数据区，规则区
	// 4,    4     ,4        , 1 ,4        ,  4    ,   4   ,    :25
	//吐出来的：类型：数据长度：规则长度：
	private byte[][] data=null;//数据
	private  int num=0;//总数，长度
	private  int packagenum=0;
	private byte type=0;//类型
	private int flageid=0;//大包ID
	private int packageid=0;//小包ID
	private boolean isinit=false;//初始化提取
	private int curposition=0;//当前提取位置
	private int curnum=0;
	private PackData thisObj=null;

	/**
	 * 
	* @Name: Add 
	* @Description: 分包
	* @param bytedata  参数说明 
	* @return void    返回类型 
	* @throws
	 */
public void Add(byte[] bytedata)
{
    ByteBuffer tmp=ByteBuffer.allocate(50);
	if(!isinit)
	{
	
	 tmp.put(bytedata, 0, 50);
	 num=tmp.getInt(0);//
	 type=tmp.get(4);
	 //flageid=tmp.getInt(5);
	 isinit=true;
	}
	packageid=tmp.getInt(13);
	data[packageid]=bytedata;
	curnum++;
	if(curnum==packagenum)
	{
		//接收完成
		//检查
	}
}
/**
 * 
* @Name: Package 
* @Description: 组包
* @param bytedata  参数说明 
* @return void    返回类型 
* @throws
 */
public void Package(byte[] bytedata)
{
	//依次读取数据
    ByteBuffer tmp=ByteBuffer.allocate(50);
	if(!isinit)
	{
	 tmp.put(bytedata, 0, 50);
	 num=tmp.getInt(0);//
	 type=tmp.get(4);
	  flageid=tmp.getInt(5);
	 packagenum=tmp.getInt(9);
	 isinit=true;
	 data=new byte[packagenum][];
	}
	packageid=tmp.getInt(13);
	data[packageid]=bytedata;
	curnum++;
	if(curnum==packagenum)
	{
		//接收完成
		//检查
	DataModel tmpdata=new DataModel();
    if(Check(tmpdata))
       {
    	  if(thisObj!=null)
    	  {
    		  thisObj.CallData(flageid, tmpdata);
    	  }
       }
    else
    {
    	System.out.println("丢失");
    }
	}
}

private boolean Check(DataModel outData)
{
	
	//检查数据总长，包大小
	//组织数据，每个数据去掉包头21字节
	
	// 一次提取内容
	//boolean isInit=true;
	//byte[] datainfo=new byte[num];
	 ByteBuffer bytedata=ByteBuffer.allocate(num);//数据区，占主要
	byte[] listRule=null;//占次要
	for(int i=0;i<data.length;i++)
	{
		byte[] tmp=data[i];
		//bytedata.put(tmp, 25, tmp.length-25);
		//13位置取数据长度，规则长度
		ByteBuffer bufferhead=ByteBuffer.allocate(8);
		//数据长度提取
		bufferhead.put(tmp, PackageConfig.dataLenIndex, 4);
		//规则长度提取
		bufferhead.put(tmp, PackageConfig.ruleLenIndex, 4);
//		if(isInit)
//		{
//			//提取数据类型
//			outData.dataType=tmp[PackageConfig.dataTypeIndex];
//			
//			isInit=false;
//		}
		//重置
		bufferhead.flip();
		int curLen=bufferhead.getInt();
		int ruleLen=bufferhead.getInt();
		if(ruleLen==0)
		{
			//说明当前还是数据区域
			bytedata.put(tmp, PackageConfig.headLen, tmp.length-PackageConfig.headLen);
			//byte[] dataByte=new byte[tmp.length-PackageConfig.headLen];
			//System.arraycopy(tmp,  PackageConfig.headLen, dataByte, 0, dataByte.length);
			//datainfo=byteMerge(datainfo,dataByte);
		}
		else
		{
			//说明进入规则区
			if(curLen>0)
			{
			  bytedata.put(tmp, PackageConfig.headLen,curLen);
				//byte[] dataByte=new byte[curLen];
				//System.arraycopy(tmp, PackageConfig.headLen, dataByte, 0, dataByte.length);
				//datainfo=byteMerge(datainfo,dataByte);
			}
			//拷贝数组
			byte[] ruleByte=new byte[ruleLen];
			System.arraycopy(tmp, PackageConfig.headLen+curLen, ruleByte, 0, ruleLen);
			listRule=byteMerge(listRule,ruleByte);
		}
		
	}
	//处理完成
	if(outData==null)
	{
		outData=new DataModel();//没有用
	}
	outData.dataType=type;
	//数据区
	bytedata.flip();
	outData.dataInfo=new byte[bytedata.limit()];//真实数据大小
	bytedata.get(outData.dataInfo);
	//outData.dataInfo=datainfo;
	//规则区域
	outData.dataRule=listRule;
	//计算总长
	//按照长度检查 
	int sumbyte=0;
	sumbyte+=PackageConfig.headLen;
	if(outData.dataInfo!=null)
	{
		sumbyte+=outData.dataInfo.length;
	}
	if(outData.dataRule!=null)
	{
		sumbyte+=outData.dataRule.length;
	}
	if(sumbyte==num)
	{
		return true;
	}
	else
	{
		return false;
	}
}

/**
 * 
* @Name: byteMerge 
* @Description: 2个数组合并
* @param data 第一个数组
* @param arg 第二个数组
* @return  参数说明 
* @return byte[]   新数组
* @throws
 */
private byte[]  byteMerge(byte[]data,byte[]arg)
{
	 int len=0;
	 int index=0;
	 if(data==null&&arg==null)
	 {
		 return null;
	 }
	 if(data!=null)
	 {
		 len+=data.length;
	 }
	 if(arg!=null)
	 {
		 len+=arg.length;
	 }
	 byte[]  dataMerge=new byte[len];
	 //处理第一个
	 if(data!=null)
	 {
		 System.arraycopy(data, 0, dataMerge, 0, data.length);
		 index=data.length;
	 }
	if(arg!=null)
	{
		 System.arraycopy(arg, 0, dataMerge, index, arg.length);
	}
	
	return dataMerge;
	
}
public byte[] get()
{
	if(curposition<packagenum)
	{
		return data[curposition++];
	}
	return null;
	
}
public byte[] get(int id)
{
	if(id<packagenum)
	{
		return data[id];
	}
	return null;
	
}
public void CallObj(PackData pack)
{
	thisObj=pack;
}
}
