  
/**    
* �ļ�����PackageModel.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��22��    
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
* �����ƣ�PackageModel    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��22�� ����9:43:02    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��22�� ����9:43:02    
* �޸ı�ע��    
* @version     
*     
*/
public class PackageModel {
	public PackageModel(int num)
	{
		data=new byte[num][];
		packagenum=num;
	}
	//�ܳ���flageid,packagenum,����,packageid,���ݳ��ȣ����򳤶ȣ���������������
	// 4,    4     ,4        , 1 ,4        ,  4    ,   4   ,    :25
	//�³����ģ����ͣ����ݳ��ȣ����򳤶ȣ�
	private byte[][] data=null;//����
	private  int num=0;//����������
	private  int packagenum=0;
	private byte type=0;//����
	private int flageid=0;//���ID
	private int packageid=0;//С��ID
	private boolean isinit=false;//��ʼ����ȡ
	private int curposition=0;//��ǰ��ȡλ��
	private int curnum=0;
	private PackData thisObj=null;

	/**
	 * 
	* @Name: Add 
	* @Description: �ְ�
	* @param bytedata  ����˵�� 
	* @return void    �������� 
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
		//�������
		//���
	}
}
/**
 * 
* @Name: Package 
* @Description: ���
* @param bytedata  ����˵�� 
* @return void    �������� 
* @throws
 */
public void Package(byte[] bytedata)
{
	//���ζ�ȡ����
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
		//�������
		//���
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
    	System.out.println("��ʧ");
    }
	}
}

private boolean Check(DataModel outData)
{
	
	//��������ܳ�������С
	//��֯���ݣ�ÿ������ȥ����ͷ21�ֽ�
	
	// һ����ȡ����
	//boolean isInit=true;
	//byte[] datainfo=new byte[num];
	 ByteBuffer bytedata=ByteBuffer.allocate(num);//��������ռ��Ҫ
	byte[] listRule=null;//ռ��Ҫ
	for(int i=0;i<data.length;i++)
	{
		byte[] tmp=data[i];
		//bytedata.put(tmp, 25, tmp.length-25);
		//13λ��ȡ���ݳ��ȣ����򳤶�
		ByteBuffer bufferhead=ByteBuffer.allocate(8);
		//���ݳ�����ȡ
		bufferhead.put(tmp, PackageConfig.dataLenIndex, 4);
		//���򳤶���ȡ
		bufferhead.put(tmp, PackageConfig.ruleLenIndex, 4);
//		if(isInit)
//		{
//			//��ȡ��������
//			outData.dataType=tmp[PackageConfig.dataTypeIndex];
//			
//			isInit=false;
//		}
		//����
		bufferhead.flip();
		int curLen=bufferhead.getInt();
		int ruleLen=bufferhead.getInt();
		if(ruleLen==0)
		{
			//˵����ǰ������������
			bytedata.put(tmp, PackageConfig.headLen, tmp.length-PackageConfig.headLen);
			//byte[] dataByte=new byte[tmp.length-PackageConfig.headLen];
			//System.arraycopy(tmp,  PackageConfig.headLen, dataByte, 0, dataByte.length);
			//datainfo=byteMerge(datainfo,dataByte);
		}
		else
		{
			//˵�����������
			if(curLen>0)
			{
			  bytedata.put(tmp, PackageConfig.headLen,curLen);
				//byte[] dataByte=new byte[curLen];
				//System.arraycopy(tmp, PackageConfig.headLen, dataByte, 0, dataByte.length);
				//datainfo=byteMerge(datainfo,dataByte);
			}
			//��������
			byte[] ruleByte=new byte[ruleLen];
			System.arraycopy(tmp, PackageConfig.headLen+curLen, ruleByte, 0, ruleLen);
			listRule=byteMerge(listRule,ruleByte);
		}
		
	}
	//�������
	if(outData==null)
	{
		outData=new DataModel();//û����
	}
	outData.dataType=type;
	//������
	bytedata.flip();
	outData.dataInfo=new byte[bytedata.limit()];//��ʵ���ݴ�С
	bytedata.get(outData.dataInfo);
	//outData.dataInfo=datainfo;
	//��������
	outData.dataRule=listRule;
	//�����ܳ�
	//���ճ��ȼ�� 
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
* @Description: 2������ϲ�
* @param data ��һ������
* @param arg �ڶ�������
* @return  ����˵�� 
* @return byte[]   ������
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
	 //�����һ��
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
