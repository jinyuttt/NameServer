package ProtocolsManager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import ClassUtil.JarUtil;
import DDS_Transfer.ProtocolType;


/**    
*     
* ��Ŀ���ƣ�ProtocolsManager    
* �����ƣ�ProtocolManager    
* ��������    �������ƴ���ͨѶ
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��12�� ����12:13:02    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��12�� ����12:13:02    
* �޸ı�ע��    
* @version     
*     
*/
public class ProtocolManager {
	private static ProtocolManager instance=new ProtocolManager();
	private ProtocolManager(){};
	public static ProtocolManager getInstance()
	{
		return instance;
	}
		ConcurrentHashMap<String,Class<?>> hashmap=new ConcurrentHashMap<String, Class<?>>();
		public  Object CreateObject(String name) throws InstantiationException, IllegalAccessException
		{
			if(hashmap.size()==0)
			{
				Init("transfer");
			}
			Class<?> cl=hashmap.getOrDefault(name.toLowerCase(), null);
			if(cl!=null)
			{
			 return	cl.newInstance();
			}
			else
			{
				return null;
			}
			
		}
	public void Init(String protocolfile)
	{
	     	JarUtil  loader=new JarUtil();
			List<Class<?>> all=loader.getPathClass(protocolfile,new Class<?>[]{ DDS_Transfer.IDDS_Protocol.class});
			if(all!=null&&all.size()>0)
			{
				for(int i = 0;i<all.size();i++)
				{
					Class<?> tmp=(Class<?>)all.get(i);
					if(tmp.isAnnotationPresent(ProtocolType.class))
					{
						ProtocolType annotation=  tmp.getAnnotation(ProtocolType.class);
						hashmap.put(annotation.Name().toLowerCase(), tmp);
						
				    }
						
						
				}
			}
	}

}
