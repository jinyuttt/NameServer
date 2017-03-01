  
/**    
* �ļ�����RecPackData.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��27��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package OutDataMessage;


import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import IPackData.IPackData;
import SubPackage.PackData;

/**    
*     
* ��Ŀ���ƣ�SubPackage    
* �����ƣ�RecPackData    
* ��������    ������������
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��27�� ����12:20:45    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��27�� ����12:20:45    
* �޸ı�ע��    
* @version     
*     
*/
public class RecPackData {
private 	ConcurrentHashMap<String, PackData> hashMap=new ConcurrentHashMap<String ,PackData>();
private  long lastTime=0;
/**
 * 
* @Name: AddDataPack 
* @Description:  �����������ݽ������
* @param call  ע��Ľ�����ʵ�������ش���ʱ�����ݼ��ṹ
* @param addr   ��Դ��ַ
* @param data  ���յ�����
* @return void    �������� 
* @throws
 */
public synchronized  void AddDataPack(IPackData call, String addr,byte[]data)
{
	PackData pack=hashMap.getOrDefault(addr, null);
	if(pack==null)
	{
		pack=new PackData();
		pack.PackageData(data);
		pack.NoteAddrObj(addr, call);
	}
	else
	{
		pack.PackageData(data);
	}
	//ÿ3���Ӽ��һ��
	if(System.currentTimeMillis()-lastTime>3*6000)
	{
		UnAction();
		lastTime=System.currentTimeMillis();
	}
}
/**
 * 
* @Name: UnAction 
* @Description: ȥ��һЩ��ʹ�õĽ���
* @return void    �������� 
* @throws
 */
private   void UnAction()
{
	ArrayList<String> list=new ArrayList<String>(10);
	for(Entry<String, PackData> entry:hashMap.entrySet())
	{
		if(entry.getValue().CheckAll())
		{
			list.add(entry.getKey());
		}
	}
	//
	String key="";
	for(int i=0;i<list.size();i++)
	{
	    key= 	list.get(i);
	    hashMap.remove(key);
	}
}
}
