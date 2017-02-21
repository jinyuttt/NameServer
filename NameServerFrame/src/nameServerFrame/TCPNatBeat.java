  
/**    
* �ļ�����TCPNatBeat.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��14��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package nameServerFrame;

import java.util.Map.Entry;


/**    
*     
* ��Ŀ���ƣ�NameServerFrame    
* �����ƣ�TCPNatBeat    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��14�� ����8:55:43    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��14�� ����8:55:43    
* �޸ı�ע��    
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
	send.setPriority(3);//���õͼ���
	send.start();
}
/**
 * 
* @Name: CheckPack 
* @Description: ��ǰ��Ҫ���������ķ���
* @param porxy  ����˵�� 
* @return void    �������� 
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