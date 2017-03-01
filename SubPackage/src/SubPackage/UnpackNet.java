  
/**    
* 文件名：UnpackNet.java    
*    
* 版本信息：    
* 日期：2017年2月21日    
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
* 类名称：UnpackNet    
* 类描述：    网络分包
* 创建人：jinyu    
* 创建时间：2017年2月21日 上午1:29:30    
* 修改人：jinyu    
* 修改时间：2017年2月21日 上午1:29:30    
* 修改备注：    
* @version     
*     
*/
public class UnpackNet {


public PackageModel SendByte(byte[]data)
{
	////总长：4
	//类型：1
	//数据id:4
	//packagenum:4
	//packageid:4
	//数据长度:4
	//规则长度：4
	int packageid=0;
	int num=data.length/PackageConfig.pazkagesize;
	int left=data.length%PackageConfig.pazkagesize;
	int sum=num+left;//分包个数
	int packagesize=PackageConfig.pazkagesize;
	//
	ByteBuffer buffer=ByteBuffer.allocate(256);
	buffer.putInt(25+data.length);//总长数据
	buffer.put((byte) -1);//类型
	buffer.putInt(PackageConfig.curflageid);//数据ID
	
	ByteBuffer tmp=ByteBuffer.allocate(256);
	
	//byte[]bytes=new byte[16+PackageConfig.pazkagesize];
	int offset=0;
	PackageModel model=new PackageModel(sum);
	for(int i=0;i<sum;i++)
	{
	    offset=i*PackageConfig.pazkagesize;
		tmp.put(buffer);
		tmp.putInt(sum);
		tmp.putInt(packageid);//packageid
		//
		if(left==0||i<sum-1)
		{
			tmp.putInt(packagesize);//数据长度
		}
		else
		{
			//最后一包
			tmp.putInt(left);//数据长度
			packagesize=left;
		}
		tmp.putInt(0);//规则长度
		//
		tmp.put(data, offset, packagesize);
		packageid++;
		model.Add(tmp.array());
	}
	return model;
	//
	
	
	
	
}
public PackageModel SendByte(byte[]data,byte[]rule)
{
	//
	int packageid=0;
	int curLen=data.length+rule.length;
	int num=curLen/PackageConfig.pazkagesize;
	int left=curLen%PackageConfig.pazkagesize;
	int sum=num+left;//分包个数
	int packagesize=PackageConfig.pazkagesize;
	//
	ByteBuffer buffer=ByteBuffer.allocate(256);
	buffer.putInt(21+curLen);//总长数据
	buffer.put((byte) -1);//类型
	buffer.putInt(PackageConfig.curflageid);//数据ID
	//
	ByteBuffer tmp=ByteBuffer.allocate(21+curLen);
	byte[] newdata=new byte[curLen];
	System.arraycopy(data, 0, newdata, 0, data.length);//拷贝数据
	System.arraycopy(rule, 0, newdata, data.length, rule.length);//拷贝规则
	data=newdata;//重新引用
	//byte[]bytes=new byte[16+PackageConfig.pazkagesize];
	int offset=0;
	PackageModel model=new PackageModel(sum);
	for(int i=0;i<sum;i++)
	{
	    offset=i*PackageConfig.pazkagesize;
		tmp.put(buffer);
		tmp.putInt(packageid);//packageid
		//
		if(left==0||i<sum-1)
		{
		  buffer.putInt(packagesize);//数据长度
		}
		else
		{
			//最后一包
			tmp.putInt(left);//数据长度
			 packagesize=left;
		}
		tmp.putInt(rule.length);//规则长度
		//
		tmp.put(data, offset, packagesize);
		packageid++;
		model.Add(tmp.array());
	}
	return model;
}
public PackageModel SendByte(byte[]data,byte[]rule,byte datatype)
{
	//
	//
	int packageid=0;
	int curLen=data.length+rule.length;
	int num=curLen/PackageConfig.pazkagesize;
	int left=curLen%PackageConfig.pazkagesize;
	int sum=num+left;//分包个数
	int packagesize=PackageConfig.pazkagesize;
	//
	ByteBuffer buffer=ByteBuffer.allocate(256);
	buffer.putInt(21+curLen);//总长数据
	buffer.put(datatype);//类型
	buffer.putInt(PackageConfig.curflageid);//数据ID
	//
	ByteBuffer tmp=ByteBuffer.allocate(21+curLen);
	byte[] newdata=new byte[curLen];
	System.arraycopy(data, 0, newdata, 0, data.length);//拷贝数据
	System.arraycopy(rule, 0, newdata, data.length, rule.length);//拷贝规则
	data=newdata;//重新引用
	//byte[]bytes=new byte[16+PackageConfig.pazkagesize];
	int offset=0;
	PackageModel model=new PackageModel(sum);
	for(int i=0;i<sum;i++)
	{
	    offset=i*PackageConfig.pazkagesize;
		tmp.put(buffer);
		tmp.putInt(packageid);//packageid
		//
		if(left==0||i<sum-1)
		{
		  buffer.putInt(packagesize);//数据长度
		}
		else
		{
			//最后一包
			 tmp.putInt(left);//数据长度
			 packagesize=left;
		}
		tmp.putInt(rule.length);//规则长度
		//
		tmp.put(data, offset, packagesize);
		packageid++;
		model.Add(tmp.array());
	}
	return model;
}
public PackageModel SendBytes(DataModel data)
{
	PackageModel model=null;
	if(data==null)
	{
	   return null;
	}
	else
	{
		int sumLen=0;
		if(data.dataRule!=null)
		{
			sumLen+=data.dataRule.length;
		}
		if(data.dataInfo!=null)
		{
			sumLen+=data.dataInfo.length;
		}
		//
		int packageid=0;
		int curLen=0;
		int num=curLen/PackageConfig.pazkagesize;
		int left=curLen%PackageConfig.pazkagesize;
		int sum=num+left;//分包个数
		int packagesize=PackageConfig.pazkagesize;
		//
		 byte[]head=FixHead(sumLen+PackageConfig.headLen,PackageConfig.curflageid,sum,data.dataType);
		//packageid,数据长度，规则长度，数据区，规则区
		//数据先填
		  model=new PackageModel(sum);
		 int offset=0;
		
		 ByteBuffer tmp=ByteBuffer.allocate(packagesize+21);
		for(int i=0;i<sum;i++)
		{
		  offset = i*packagesize;
		  if(offset+packagesize<data.dataInfo.length)
		  {
			  //如果数据区域足够
			  tmp.put(head);
			  tmp.putInt(packageid);
			  tmp.putInt(packagesize);
			  tmp.putInt(0);
			  tmp.put(data.dataInfo, offset, packagesize);
		  }
		  else
		  {
			  //数据区不够
			  int ruleoffset=0;
			  int dataoffset=0;
			  tmp.put(head);
			  tmp.putInt(packageid);
			  int dataLen=data.dataInfo.length-offset;
			  int ruleLen=packagesize-dataLen;
			  dataoffset=offset;
			  if(dataLen<0)
			  {
				  //说明数据区全部填完
				  ruleoffset=Math.abs(dataLen);
				  dataLen=0;
				  dataoffset=data.dataInfo.length-1;
				 if(ruleoffset+packagesize<data.dataRule.length)
				 {
					 ruleLen=packagesize;
				 }
				 else
				 {
					 //最后一包，规则填完
					 ruleLen=data.dataRule.length-ruleoffset;
				 }
				  
			  }
			  tmp.putInt(dataLen);
			  tmp.putInt(ruleLen);
			  tmp.put(data.dataInfo, dataoffset, dataLen);
			  tmp.put(data.dataRule, ruleoffset, ruleLen);
		  }
		  //数据完成
		  tmp.flip();
		  byte[]curData=new byte[tmp.limit()];
		  tmp.get(curData);
		  model.Add(curData);
		  tmp.clear();
			packageid++;
			
		}
		
	}
	return model;
}

/**
 * 
* @Name: FixHead 
* @Description: 产生固定头
* @param len
* @param flageid
* @param num
* @param type
* @return  参数说明 
* @return byte[]    返回类型 
* @throws
 */
private byte[] FixHead(int len,int flageid,int num,byte type )
{
	//总长，flageid,packagenum,类型,
	ByteBuffer head=ByteBuffer.allocate(13);
	head.putInt(len);
	head.putInt(flageid);
	head.putInt(num);
	head.put(type);
	return head.array();
}
}
