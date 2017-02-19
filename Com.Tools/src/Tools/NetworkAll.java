  
/**    
* �ļ�����NetworkAll.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��18��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package Tools;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

/**    
*     
* ��Ŀ���ƣ�Com.Tools    
* �����ƣ�NetworkAll    
* ��������    ��ȡ����IP
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��18�� ����12:22:31    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��18�� ����12:22:31    
* �޸ı�ע��    
* @version     
*     
*/
public class NetworkAll {
private  static ArrayList<String> listAll=new ArrayList<String>();

/**
 * 
* @Name: GetLocalAllIP 
* @Description: ��ȡ����IP
* @return  ����˵�� 
* @return ArrayList<String>    �������� 
* @throws
 */
public static ArrayList<String> GetLocalAllIP()
{
	if(listAll.size()==0)
	{
	ArrayList<String> list=new ArrayList<String>();
	Enumeration<?> allNetInterfaces = null;
	try {
		allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	InetAddress ip = null;
	while (allNetInterfaces.hasMoreElements())
	{
	NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
	System.out.println(netInterface.getName());
	Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
	while (addresses.hasMoreElements())
	{
	ip = (InetAddress) addresses.nextElement();
	if (ip != null && ip instanceof Inet4Address)
	{
		String ipaddr=ip.getHostAddress();
		list.add(ipaddr);
	  
	} 
	}
	}
	if(!list.contains("127.0.0.1"))
	{
		list.add("127.0.0.1");
	}
	listAll=list;
}
	return listAll;
 }

/**
 * 
* @Name: IsLocalIP 
* @Description: �ж��Ƿ������IP
* @param ip
* @return  ����˵�� 
* @return boolean    �������� 
* @throws
 */
public static boolean IsLocalIP(String ip)
{
	if(listAll.size()==0)
	{
		GetLocalAllIP();
	}
	if(listAll.contains(ip))
	{
		return true;
	}
	else
	{
		return false;
	}
}
/**
 * 
* @Name: internalIp 
* @Description: �ж��Ƿ�������IP
* @param ip
* @return  ����˵�� 
* @return boolean    �������� 
* @throws
 */
public static boolean internalIp(String ip) {
    byte[] addr = null;
	try {
		addr = InetAddress.getByName(ip).getAddress();
	} catch (UnknownHostException e) {
		
		e.printStackTrace();
	}
    return internalIp(addr);
}


public static boolean internalIp(byte[] addr) {
    final byte b0 = addr[0];
    final byte b1 = addr[1];
    //10.x.x.x/8
    final byte SECTION_1 = 0x0A;
    //172.16.x.x/12
    final byte SECTION_2 = (byte) 0xAC;
    final byte SECTION_3 = (byte) 0x10;
    final byte SECTION_4 = (byte) 0x1F;
    //192.168.x.x/16
    final byte SECTION_5 = (byte) 0xC0;
    final byte SECTION_6 = (byte) 0xA8;
    switch (b0) {
        case SECTION_1:
            return true;
        case SECTION_2:
            if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                return true;
            }
        case SECTION_5:
            switch (b1) {
                case SECTION_6:
                    return true;
            }
        default:
            return false;

    }
}
}