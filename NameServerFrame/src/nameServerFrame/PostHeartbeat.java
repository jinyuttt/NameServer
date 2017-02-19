  
/**    
* �ļ�����PostHeartbeat.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��8��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package nameServerFrame;

import java.util.ArrayList;
import java.util.Map.Entry;

import ProxyExchange.PostUDP;
import PublicModel.ServerBinds;
import UDPPeerToPeer.ServerXmlInfo;
import nameServerContainer.ServersContains;

/**    
*     
* ��Ŀ���ƣ�NameServerFrame    
* �����ƣ�PostHeartbeat    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��8�� ����8:10:26    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��8�� ����8:10:26    
* �޸ı�ע��    
* @version     
*     
*/
public class PostHeartbeat {
String addr=null;
String beat=null;
/**
 * 
* @Name: Start 
* @Description: ���캯�� 
* @param walladdr  ����ǽ�ظ�׼���ɹ���ַ
* @param beataddr  �������͵�ַ
* @return void    �������� 
* @throws
 */
public void Start(String walladdr,String beataddr)
{
	addr=walladdr;
	beat=beataddr;
	Thread post=new Thread(new Runnable()
			{

				@Override
				public void run() {
					PostUDP postbeat=new PostUDP(addr,beat);
					postbeat.search=new SearchServers();
					postbeat.StartRec();
					while(true)
					{
						//
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
						for(Entry<String, ArrayList<ServerBinds>> entry : ServersContains.servers.entrySet())
						{
							ArrayList<ServerBinds> list=entry.getValue();
							for(int j=0;j<list.size();j++)
							{
								ServerBinds tmp=list.get(j);
								String xml=ServerXmlInfo.HeaderXml(tmp.name, tmp.address+":"+tmp.port, "");
						     	//xml="dffggg";
								postbeat.SendBeat(xml);
							}
						}
						
					}
				}
		
			});
	post.setDaemon(true);
	post.setName("TimerBeat");
	post.setPriority(3);//���õͼ���
	post.start();
}
}
