package Tools;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
*     
* 项目名称：Com.Tools    
* 类名称：IPTotool    
* 类描述：    IP地址转INT
* 创建人：jinyu    
* 创建时间：2017年2月18日 上午11:58:45    
* 修改人：jinyu    
* 修改时间：2017年2月18日 上午11:58:45    
* 修改备注：    
* @version     
*
 */
public class IPTotool {
	
	/**
	 * 
	* @Name: ipStr2int 
	* @Description: IP转换
	* @param ipstr IP地址
	* @return  参数说明 
	* @return int    返回类型 
	* @throws
	 */
	public static int ipStr2int(String ipstr)
	{
	    byte[] bytes = null;
		try {
			bytes = InetAddress.getByName(ipstr).getAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    int addr = bytes[3] & 0xFF;
	    addr |= ((bytes[2] << 8) & 0xFF00);
	    addr |= ((bytes[1] << 16) & 0xFF0000);
	    addr |= ((bytes[0] << 24) & 0xFF000000);
	    return addr;
	}
	 public static String intToIp(int ipInt) {
	        return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.')
	                .append((ipInt >> 16) & 0xff).append('.').append(
	                        (ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff))
	                .toString();
	    }

}
