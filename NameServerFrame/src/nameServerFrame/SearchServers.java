  
/**    
* 文件名：SearchServers.java    
*    
* 版本信息：    
* 日期：2017年2月18日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package nameServerFrame;

import ISearchInfo.ISearchPrxoy;

/**    
*     
* 项目名称：NameServerFrame    
* 类名称：SearchServers    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月18日 下午2:06:11    
* 修改人：jinyu    
* 修改时间：2017年2月18日 下午2:06:11    
* 修改备注：    
* @version     

*     
*/
public class SearchServers implements  ISearchPrxoy{

	/*
	* Title: SendData
	*Description: 
	* @param infokey
	* @param addr
	* @param data 
	 
	*/
	@Override
	public void SendData(String infokey, String addr, byte[] data) {
		ServerInfo server=ServerInstances.servers.getOrDefault(infokey, null);
		if(server!=null)
		{
			try
			{
				int num=10;
				//发送10次
		    	String[]address=addr.split(":");
		    	while(num>1)
		    	{
		         server.porxy.SendTCPNatPackage(address[0], Integer.valueOf(address[1]));
		         num--;
		    	}
			}
			catch(Exception ex)
			{
				
			}
		}
		
	}

		
	


}
