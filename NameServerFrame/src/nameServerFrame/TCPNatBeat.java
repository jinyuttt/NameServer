  
/**    
* 文件名：TCPNatBeat.java    
*    
* 版本信息：    
* 日期：2017年2月14日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package nameServerFrame;

import java.util.Map.Entry;


/**    
*     
* 项目名称：NameServerFrame    
* 类名称：TCPNatBeat    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月14日 下午8:55:43    
* 修改人：jinyu    
* 修改时间：2017年2月14日 下午8:55:43    
* 修改备注：    
* @version     
*     
*/
public class TCPNatBeat {
private	String ManagerAddr="";
private	int ManagerPort=0;
public void Start(String addr,int port)
{
	ManagerAddr=addr;
	ManagerPort=port;
	Thread send=new Thread(new Runnable()
			{
		       @Override
				public void run() {
		    	   while(true)
		    	   {
		    		   try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		   try
		    		   {
				for(Entry<String, ServerInfo> entry:ServerInstances.servers.entrySet())
				{
					CheckPack(entry.getValue().porxy);
					//entry.getValue().porxy.SendTCPNatPackage(ManagerAddr, 	ManagerPort);
				}
		    		   }
		    		   catch(Exception ex)
		    		   {
		    			   System.out.println(ex.getMessage());
		    		   }
		    		   try
		    		   {
			    for(Entry<String, ServerBox> entry:ServerBoxSet.serverBox.entrySet())
			    {
			    	CheckPack(entry.getValue().serverProxy);
			    	//entry.getValue().serverProxy.SendTCPNatPackage(ManagerAddr, ManagerPort);
			    }
		    		   }
		    		   catch(Exception ex)
		    		   {
		    			   System.out.println(ex.getMessage());
		    		   }
				}
		       }
		
			});
	send.setDaemon(true);
	send.setName("TCPNatPackage");
	send.setPriority(3);//设置低级别
	send.start();
}
/**
 * 
* @Name: CheckPack 
* @Description: 当前需要发送心跳的服务
* @param porxy  参数说明 
* @return void    返回类型 
* @throws
 */
private void CheckPack(ServerPorxy porxy)
{
	if(porxy.type_Name.equals("TCP"))
	{
		porxy.SendTCPNatPackage(ManagerAddr, ManagerPort);
	}
}


}
