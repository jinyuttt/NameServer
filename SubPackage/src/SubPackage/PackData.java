  
/**    
* �ļ�����PackData.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��26��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SubPackage;

import java.nio.ByteBuffer;
import java.util.HashMap;

import IPackData.DataModel;
import IPackData.IPackData;

/**    
*     
* ��Ŀ���ƣ�SubPackage    
* �����ƣ�PackData    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��26�� ����11:59:19    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��26�� ����11:59:19    
* �޸ı�ע��    
* @version     
*     
*/
public class PackData {
	private byte[] leftdata=null;
	IPackData  callObj=null;
	private String addr="";
	private HashMap<Integer,PackageModel> packdata=new HashMap<Integer,PackageModel>();
	private long lastTime=0;//���������ʱ��
	
	/**
	 * 
	* @Name: PackageData 
	* @Description: ��֯����
	* @param data  ����˵�� 
	* @return void    �������� 
	* @throws
	 */
	public synchronized  void PackageData(byte[]data)
	{
		//����˳�����
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
	 * ��������
	 */
	private void ReSet(ByteBuffer buffer)
	{
		int offset=buffer.position();
		//���ݼ��
		if(offset<buffer.limit())
		{
			//�в������ݿ���
			if(buffer.limit()<offset+PackageConfig.headLen)
			{
				//ͷ����,ֱ�ӱ���
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
			    //���������ˣ�ֱ�ӱ��油��
			    buffer.position(offset);//�ص���ǰͷλ��
			    int len=buffer.limit()-offset;
				leftdata=new byte[len];
				buffer.get(leftdata, buffer.position(), len);
				return;
		 }
		 //�����ݽ���
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
		 //bufferλ���Ѿ�����
		 if(buffer.limit()>buffer.position())
		 {
		     //��������
			 ReSet(buffer);
		 }
	}
	/**
	 * 
	* @Name: CallData 
	* @Description: ������
	* @param data  ��������
	* @return void    �������� 
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
	* @Description: ���ݻص��ӿ�
	* @param addr   ������Դ��ַ
	* @param callObj  �ص�ʵ��
	* @return void    �������� 
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
	* @Description: ɾ�������ݰ�
	* @param flageid  ����˵�� 
	* @return void    �������� 
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
	* @Description: �Ѿ�����������,�������
	* @return  ����˵�� 
	* @return boolean    �������� 
	* @throws
	 */
	public boolean CheckAll()
	{
		if(System.currentTimeMillis()-this.lastTime>60000&&packdata.isEmpty())
		{
			//����1���ӣ�����Ƶ����������
			return true;
		}
		else
		{
			return false;
		}
	}
}
