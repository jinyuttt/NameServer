package nameServerClient;


import AppConfig.ClientGlobalConfig;
import PublicModel.ServerBinds;
import RequestServerInfo.AnalysisParam;
import RequestServerInfo.ServerManagerType;
import Tools.MsgPackTool;
import UDPPeerToPeer.ServerXml;
import UDPPeerToPeer.ServerXmlInfo;
import nameServerInterface.IPoxyObj;


/**
 * �ͻ��˴���
 * �����ڳ�������Manager����
 * ͨ�����þ���ʹ�÷�ʽ��
 * ��manager�и��������ͨѶ
 * �ڳ����и�����������managerͨѶ
 * @author jinyu
 *
 */
public class ProxyClient {
	
/**
 * ��ȡ����˴���
 * @param name ��������
 * @param error ������Ϣ
 * @return �������
 */
public static IPoxyObj CastObj(String name,StringBuilder error)

{
	error=new StringBuilder();
	if(name==null)
	{
		error.append("���Ʋ���Ϊ��");
		return null;
		
	}
	//
	ServerManagerType  serverType= ClientGlobalConfig.mapConfig.getOrDefault(name, null);
	if(serverType==null)
	{
		serverType=ClientGlobalConfig.ServerProType;
	}
	if(serverType==ServerManagerType.ClientDirectMode)
	{
	//�ͻ����и÷�����Ϣ��ֱ�����ӷ���
	ServerBinds serverinfo=LocalServer.GetServerInfo().GetCur(name);
	if(serverinfo!=null)
	{
		//ServerConnectPorxy tempPorxy=new ServerConnectPorxy(serverinfo.address,serverinfo.port,serverinfo.communicationType);
		ClientToServer client=ProcessClient(name,serverinfo.address,serverinfo.port,serverinfo.communicationType);
		//client.proxy=tempPorxy;
		//client.ServerName=name;
	    return client;
	}
	else
	{
		error.append("û�и÷���");
		return null;
	}
	}
	else
	{
		//����Manager
		//���ص�ַ
		//ServerConnectPorxy tempPorxy=new ServerConnectPorxy(GlobalConfig.ManagerAddress,GlobalConfig.ManagerPort,GlobalConfig.protol_Type);
		//ClientToServer client=new ClientToServer();
		//client.proxy=tempPorxy;
		//client.ServerName=name;
 		ClientToServer client=ProcessClient(name,ClientGlobalConfig.ManagerAddress,ClientGlobalConfig.ManagerPort,ClientGlobalConfig.protol_Type);
	    byte[] bytes=AnalysisParam.PackParam(name, ClientGlobalConfig.ServerProType, null);
	    byte[] result=	client.GetData(bytes);
	    if(result!=null)
	    {
	    
	    	//�з�����
	    	 client.Close();//�ر���Manager������
	    	 MsgPackTool tool=new MsgPackTool();
	    	 ServerBinds serverinfo= tool.Deserialize(result, ServerBinds.class, error);
	    	 //tempPorxy=new ServerConnectPorxy(serverinfo.address,serverinfo.port,serverinfo.communicationType);
	    	 if(serverinfo.natAddr!=null&&serverinfo.natAddr.length()>0)
	    	 {
	    		 //��ǽ����
	    		 client=ProcessClient(name,serverinfo.address,serverinfo.port,serverinfo.communicationType);
	    		 byte[] data= client.GetData(result);
	    		 String info=new String(data);
	    		 ServerXml server=ServerXmlInfo.Analysis(info);
	    		 client.DisConnect();
	    		 String[] serveraddr=server.address.split(":");
	    		 //�������ӷ����
	    		 ReSetProcessClient(client,serveraddr[0],Integer.valueOf(serveraddr[1]));
	    	 }
	    	 else
	    	 {
	    		 //����Ҫ��ǽ
	    		 client=ProcessClient(name,serverinfo.address,serverinfo.port,serverinfo.communicationType);
	    	 }
	 		
	 	    
	    }
	    return client;
	    
	   //������ַ
	}
}
private static   ClientToServer ProcessClient(String name,String addr,int port,String protol_Type)
{
 	ServerConnectPorxy tempPorxy=new ServerConnectPorxy(addr,port,protol_Type);
	ClientToServer client=new ClientToServer(tempPorxy);
	
	client.ServerName=name;
	return client;
}
private static   void  ReSetProcessClient(ClientToServer client,String addr,int port)
{
	client.Reset(addr, port);
}
}
