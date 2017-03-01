  
/**    
* �ļ�����UnpackNet.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��21��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SubPackage;

import java.nio.ByteBuffer;

import IPackData.DataModel;

/**    
*     
* ��Ŀ���ƣ�SubPackage    
* �����ƣ�UnpackNet    
* ��������    ����ְ�
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��21�� ����1:29:30    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��21�� ����1:29:30    
* �޸ı�ע��    
* @version     
*     
*/
public class UnpackNet {


public PackageModel SendByte(byte[]data)
{
	////�ܳ���4
	//���ͣ�1
	//����id:4
	//packagenum:4
	//packageid:4
	//���ݳ���:4
	//���򳤶ȣ�4
	int packageid=0;
	int num=data.length/PackageConfig.pazkagesize;
	int left=data.length%PackageConfig.pazkagesize;
	int sum=num+left;//�ְ�����
	int packagesize=PackageConfig.pazkagesize;
	//
	ByteBuffer buffer=ByteBuffer.allocate(256);
	buffer.putInt(25+data.length);//�ܳ�����
	buffer.put((byte) -1);//����
	buffer.putInt(PackageConfig.curflageid);//����ID
	
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
			tmp.putInt(packagesize);//���ݳ���
		}
		else
		{
			//���һ��
			tmp.putInt(left);//���ݳ���
			packagesize=left;
		}
		tmp.putInt(0);//���򳤶�
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
	int sum=num+left;//�ְ�����
	int packagesize=PackageConfig.pazkagesize;
	//
	ByteBuffer buffer=ByteBuffer.allocate(256);
	buffer.putInt(21+curLen);//�ܳ�����
	buffer.put((byte) -1);//����
	buffer.putInt(PackageConfig.curflageid);//����ID
	//
	ByteBuffer tmp=ByteBuffer.allocate(21+curLen);
	byte[] newdata=new byte[curLen];
	System.arraycopy(data, 0, newdata, 0, data.length);//��������
	System.arraycopy(rule, 0, newdata, data.length, rule.length);//��������
	data=newdata;//��������
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
		  buffer.putInt(packagesize);//���ݳ���
		}
		else
		{
			//���һ��
			tmp.putInt(left);//���ݳ���
			 packagesize=left;
		}
		tmp.putInt(rule.length);//���򳤶�
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
	int sum=num+left;//�ְ�����
	int packagesize=PackageConfig.pazkagesize;
	//
	ByteBuffer buffer=ByteBuffer.allocate(256);
	buffer.putInt(21+curLen);//�ܳ�����
	buffer.put(datatype);//����
	buffer.putInt(PackageConfig.curflageid);//����ID
	//
	ByteBuffer tmp=ByteBuffer.allocate(21+curLen);
	byte[] newdata=new byte[curLen];
	System.arraycopy(data, 0, newdata, 0, data.length);//��������
	System.arraycopy(rule, 0, newdata, data.length, rule.length);//��������
	data=newdata;//��������
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
		  buffer.putInt(packagesize);//���ݳ���
		}
		else
		{
			//���һ��
			 tmp.putInt(left);//���ݳ���
			 packagesize=left;
		}
		tmp.putInt(rule.length);//���򳤶�
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
		int sum=num+left;//�ְ�����
		int packagesize=PackageConfig.pazkagesize;
		//
		 byte[]head=FixHead(sumLen+PackageConfig.headLen,PackageConfig.curflageid,sum,data.dataType);
		//packageid,���ݳ��ȣ����򳤶ȣ���������������
		//��������
		  model=new PackageModel(sum);
		 int offset=0;
		
		 ByteBuffer tmp=ByteBuffer.allocate(packagesize+21);
		for(int i=0;i<sum;i++)
		{
		  offset = i*packagesize;
		  if(offset+packagesize<data.dataInfo.length)
		  {
			  //������������㹻
			  tmp.put(head);
			  tmp.putInt(packageid);
			  tmp.putInt(packagesize);
			  tmp.putInt(0);
			  tmp.put(data.dataInfo, offset, packagesize);
		  }
		  else
		  {
			  //����������
			  int ruleoffset=0;
			  int dataoffset=0;
			  tmp.put(head);
			  tmp.putInt(packageid);
			  int dataLen=data.dataInfo.length-offset;
			  int ruleLen=packagesize-dataLen;
			  dataoffset=offset;
			  if(dataLen<0)
			  {
				  //˵��������ȫ������
				  ruleoffset=Math.abs(dataLen);
				  dataLen=0;
				  dataoffset=data.dataInfo.length-1;
				 if(ruleoffset+packagesize<data.dataRule.length)
				 {
					 ruleLen=packagesize;
				 }
				 else
				 {
					 //���һ������������
					 ruleLen=data.dataRule.length-ruleoffset;
				 }
				  
			  }
			  tmp.putInt(dataLen);
			  tmp.putInt(ruleLen);
			  tmp.put(data.dataInfo, dataoffset, dataLen);
			  tmp.put(data.dataRule, ruleoffset, ruleLen);
		  }
		  //�������
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
* @Description: �����̶�ͷ
* @param len
* @param flageid
* @param num
* @param type
* @return  ����˵�� 
* @return byte[]    �������� 
* @throws
 */
private byte[] FixHead(int len,int flageid,int num,byte type )
{
	//�ܳ���flageid,packagenum,����,
	ByteBuffer head=ByteBuffer.allocate(13);
	head.putInt(len);
	head.putInt(flageid);
	head.putInt(num);
	head.put(type);
	return head.array();
}
}
