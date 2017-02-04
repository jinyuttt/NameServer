package ProtocolsManager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import ClassUtil.JarUtil;
import DDS_Transfer.ProtocolType;


/**    
*     
* 项目名称：ProtocolsManager    
* 类名称：ProtocolManager    
* 类描述：    根据名称创建通讯
* 创建人：jinyu    
* 创建时间：2017年1月12日 上午12:13:02    
* 修改人：jinyu    
* 修改时间：2017年1月12日 上午12:13:02    
* 修改备注：    
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
