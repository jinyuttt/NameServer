  
/**    
* 文件名：RecPackData.java    
*    
* 版本信息：    
* 日期：2017年2月27日    
* Copyright 足下 Corporation 2017     
* 版权所有    
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
* 项目名称：SubPackage    
* 类名称：RecPackData    
* 类描述：    接受网络数据
* 创建人：jinyu    
* 创建时间：2017年2月27日 上午12:20:45    
* 修改人：jinyu    
* 修改时间：2017年2月27日 上午12:20:45    
* 修改备注：    
* @version     
*     
*/
public class RecPackData {
private 	ConcurrentHashMap<String, PackData> hashMap=new ConcurrentHashMap<String ,PackData>();
private  long lastTime=0;
/**
 * 
* @Name: AddDataPack 
* @Description:  接受网络数据进行组包
* @param call  注入的接受类实例；返回传送时的数据及结构
* @param addr   来源地址
* @param data  接收的数据
* @return void    返回类型 
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
	//每3分钟检查一次
	if(System.currentTimeMillis()-lastTime>3*6000)
	{
		UnAction();
		lastTime=System.currentTimeMillis();
	}
}
/**
 * 
* @Name: UnAction 
* @Description: 去掉一些不使用的解析
* @return void    返回类型 
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
