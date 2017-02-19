package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import ProtocolsManager.ProtocolManager;


/**    
* �ļ�����ServerManager.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��12��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/

/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�ServerManager    
* ��������     �������񣬳�ʼ������
*  ����������managerע�ᣬ�ͻ���ͨ��manager��ȡ��Ϣ�����ߴ�������
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��12�� ����12:04:01    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��12�� ����12:04:01    
* �޸ı�ע��    
* @version     
*     
*/
public class ServerManager {
	 IDDS_Protocol curObj =null;
	 IRecMsg recService =null;
	 IRecMsg recClientRequest =null;
	 IRecMsg recHeartBeat=null;
	 IRecMsg recFireInfo=null;//���շ���˴�ǽ�ɹ�
	 IRecMsg recTCPNat=null;
	 IRecMsg recUDPNat=null;//���տͻ��˴�ǽ����,��TCP��ͬ
	 IRecMsg recTCPBeat=null;//Ϊ�˱���TCP����Ķ˿���Ϣ
	 IDDS_Protocol recClientReq=null;
	 IDDS_Protocol recServerRsp=null;
	 IDDS_Protocol recClientBeat=null;
	 IDDS_Protocol recFire=null;
	 IDDS_Protocol recTCPPackage=null;
	 IDDS_Protocol recUDPClientNat=null;//���տͻ��˴�ǽ����
	 IDDS_Protocol recTCPClientNat=null;//���տͻ���TCP��ǽ����
	/**
	 * 
	* @Name: InitServiceRec 
	* @Description: ��ʼ�����ܷ����ע����Ϣ
	* @param ip  ���ڽ��ܵ�IP
	* @param port  ���ڽ��յĶ˿�
	* @param typeName  ͨѶ���� 
	* @return void    �������� 
	* @throws
	 */
public  void InitServiceRec(String ip,int port,String typeName)
{
	//InitPlugin.GetInstance().InitRecSeverInfo();
	 try {
		 recService=new RecviceService();
		 recServerRsp=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject(typeName);
		 recServerRsp.RecData(ip+":"+port, recService);
	} catch (InstantiationException e) {
		
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		
		e.printStackTrace();
	}
	
}

/**
 * 
* @Name: InitClientRequest 
* @Description: ��ʼ�����տͻ�������
* @param ip
* @param port
* @param typeName  ����˵�� 
* @return void    �������� 
* @throws
 */
public void InitClientRequest(String ip,int port,String typeName)
{
	
	
	try {
		 recClientRequest=new RecviceClientRequest();
		 recClientReq=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject(typeName);
		 recClientReq.RecData(ip+":"+port, recClientRequest);
		
	} catch (InstantiationException e) {
		
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		
		e.printStackTrace();
	}

}
/**
 * 
* @Name: InitHeartBeat 
* @Description: ��������
* @param ip
* @param port
* @param typeName  ����˵�� 
* @return void    �������� 
* @throws
 */
public void InitHeartBeat(String ip,int port,String typeName)
{
	try {
		String addr=ip+":"+port;
		ManagerAddrInfo.hashMap.put("ManagerBeat", addr);
		recHeartBeat=new RecviceHeartbeat();
		recClientBeat=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject(typeName);
		recClientBeat.RecData(addr, recHeartBeat);
		System.out.println("�����������գ�"+ip+","+port);
		
	} catch (InstantiationException e) {
		
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		
		e.printStackTrace();
	}
}
/**
 * 
* @Name: InitRecUDPNat 
* @Description: ���տͻ���UDP��ǽ����
* @param ip 
* @param port
* @param typeName  ����˵�� 
* @return void    �������� 
* @throws
 */
public void InitRecUDPNat(String ip,int port,String typeName)
{
	try {
		String addr=ip+":"+port;
		ManagerAddrInfo.hashMap.put("ManagerUDPNAT", addr);
		recUDPNat=new RecviceClientUDPWall();
		recUDPClientNat=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject(typeName);
		recUDPClientNat.RecData(addr, recUDPNat);
		System.out.println("����UDP��ǽ����"+ip+","+port);
	} catch (InstantiationException e) {
		
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		
		e.printStackTrace();
	}
}
/**
 * 
* @Name: InitRecTCPNat 
* @Description: ���տͻ���UDP��ǽ����
* @param ip 
* @param port
* @param typeName  ����˵�� 
* @return void    �������� 
* @throws
 */
public void InitRecTCPNat(String ip,int port,String typeName)
{
	try {
		String addr=ip+":"+port;
		ManagerAddrInfo.hashMap.put("ManagerTCPNAT", addr);
		recTCPNat=new RecviceClientTCPWall();
		recTCPClientNat=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject(typeName);
		recTCPClientNat.RecData(addr, recTCPNat);
		System.out.println("����TCP��ǽ������գ�"+ip+","+port);
	} catch (InstantiationException e) {
		
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		
		e.printStackTrace();
	}
}
/**
 * 
* @Name: InitRecWallSucess 
* @Description: ����UDP��ǽ�ɹ���Ϣ
* @param ip
* @param port
* @param typeName  ����˵�� 
* @return void    �������� 
* @throws
 */
public void InitRecWallSucess(String ip,int port,String typeName)
{
	try {
		String addr=ip+":"+port;
		ManagerAddrInfo.hashMap.put("ManagerWallSucess", addr);
		recFireInfo=new RecviceWallSucess();
		recFire=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject(typeName);
		recFire.RecData(addr, recFireInfo);
		System.out.println("������ǽ�ɹ���Ϣ���գ�"+ip+","+port);
	} catch (InstantiationException e) {
		
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		
		e.printStackTrace();
	}
}

/**
 * 
* @Name: InitRecTCPNat 
* @Description: TCP����ʱ���Ͱ�����������NAT������Ϣ
* @param ip
* @param port
* @param typeName  ����˵�� 
* @return void    �������� 
* @throws
 */
public void InitRecTCPBeat(String ip,int port,String typeName)
{
	try {
		String addr=ip+":"+port;
		ManagerAddrInfo.hashMap.put("ManagerTCPBeat", addr);
		recTCPBeat=new  RecviceTCPNatPackage();
		recTCPPackage=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject(typeName);
		recTCPPackage.RecData(addr, recTCPBeat);
		System.out.println("���������TCP�������գ�"+ip+","+port);
		
	} catch (InstantiationException e) {
		
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		
		e.printStackTrace();
	}
}
}