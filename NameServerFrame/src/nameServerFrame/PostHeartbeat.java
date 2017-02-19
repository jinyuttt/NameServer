  
/**    
* 文件名：PostHeartbeat.java    
*    
* 版本信息：    
* 日期：2017年2月8日    
* Copyright 足下 Corporation 2017     
* 版权所有    
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
* 项目名称：NameServerFrame    
* 类名称：PostHeartbeat    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月8日 下午8:10:26    
* 修改人：jinyu    
* 修改时间：2017年2月8日 下午8:10:26    
* 修改备注：    
* @version     
*     
*/
public class PostHeartbeat {
String addr=null;
String beat=null;
/**
 * 
* @Name: Start 
* @Description: 构造函数 
* @param walladdr  防火墙回复准备成功地址
* @param beataddr  心跳发送地址
* @return void    返回类型 
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
	post.setPriority(3);//设置低级别
	post.start();
}
}
