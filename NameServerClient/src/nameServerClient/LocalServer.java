package nameServerClient;


import AppConfig.ClientGlobalConfig;

import RequestServerInfo.ServerManagerType;
import nameServerContainer.ProxyPlugin;

/**
 * 
*     
* 项目名称：NameServerClient    
* 类名称：LocalServer    
* 类描述：    接收的服务信息,返回接收消息的组件代理
* 创建人：jinyu    
* 创建时间：2017年1月20日 下午11:35:58    
* 修改人：jinyu    
* 修改时间：2017年1月20日 下午11:35:58    
* 修改备注：    
* @version     
*
 */
public class LocalServer {
 static	ProxyPlugin plugin=null;

/**
 * 封装一层为了初始化
* @Name: GetServerInfo 
* @Description: 返回服务信息并且初始化开启接收服务注册
* @return  参数说明 
* @return InitPlugin    返回类型 
* @throws
 */
public static ProxyPlugin GetServerInfo()
   {
	   if(plugin==null)
	   {
		   plugin=ProxyPlugin.GetInstance();
		   ClientGlobalConfig.ServerProType=ServerManagerType.ServerIndirectMode;
		   if(ClientGlobalConfig.ServerProType==ServerManagerType.ClientDirectMode)
		   {
			   //如果客户端有直接调用服务的方式，则必须接收服务信息
			   //否则只需要从ServerManger中获取
		      plugin.InitRecSeverInfo();
		      plugin.ReqServerInfo();
		   }
		   try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	   }
	   return plugin;
   }
	
}
