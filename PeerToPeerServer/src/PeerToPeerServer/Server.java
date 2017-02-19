  
/**    
* 文件名：Server.java    
*    
* 版本信息：    
* 日期：2017年2月11日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package PeerToPeerServer;

import java.io.IOException;

/**    
*     
* 项目名称：PeerToPeerServer    
* 类名称：Server    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月11日 下午3:20:36    
* 修改人：jinyu    
* 修改时间：2017年2月11日 下午3:20:36    
* 修改备注：    
* @version     
*     
*/
public class Server {

	/** 
	* @Name: main 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param args  参数说明 
	* @return void    返回类型 
	* @throws 
	*/
	public static void main(String[] args) {
		UDPServer ss=new UDPServer();
		ss.StartServer(6666);
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
