package nameServerContainer;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import PublicModel.ServerBinds;
import RequestServerInfo.ReqInfoSend;

import ServerRecInfo.ServerInfo;
import hashingAlgorithm.cirALL;
/*
 * 
*     
* ��Ŀ���ƣ�NameServerContainer    
* �����ƣ�ProxyPlugin    
* ��������    ����
* ��ȡ������Ϣ���������շ�����Ϣ�����ͻ�ȡ���з��������
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��21�� ����12:33:08    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��21�� ����12:33:08    
* �޸ı�ע��    
* @version     
*
 */
public class ProxyPlugin  {

	ServerInfo serverAdd=new ServerInfo(); 
	private final static ProxyPlugin sigle=new ProxyPlugin();
	public static ProxyPlugin GetInstance()
	{
		return  sigle;
	}
   private ProxyPlugin()
   {
	   
   }
	
 /**
  * ��ȡ������Ϣ
 * @param name ��������
 * @return ���������Ϣ
 */
public ServerBinds GetCur(String name) {
		
	ServerInfoSave  temp=new ServerInfoSave();
    ArrayList<ServerBinds> listObj=temp.GetCur(name);
	if(listObj==null||listObj.size()==0)
	{
		return null;
	}
	else if(listObj.size()==1)
	{
		return listObj.get(0);
	}
	else
	{
		//���ø��ؾ���
		cirALL cir=new cirALL();
		Object[] objarry=listObj.toArray();
		CopyOnWriteArrayList<Object> cal = new CopyOnWriteArrayList<Object>(objarry);
	    Object obj=	cir.GetCurNodeInfo(cal);
	    ServerBinds info=(ServerBinds)obj; 
	    return info;
	}
		
	
	}
	
	/**
	 * ��ʼ�����շ�����Ϣ
	 * 
	 */
	public void InitRecSeverInfo()
	{
		serverAdd.AddServer();
	}
	
	
	/**
	 * ����˰������ӵķ����ͳ�ȥ
	 * @param info
	 */
	public void NoticeServerInfo(ServerBinds info)
	{
		
		serverAdd.SendInfo(info);
	}
	
/**
 * �ͻ�������ʱ��������
 */
public void ReqServerInfo()
{
	ReqInfoSend send=new ReqInfoSend();
 	send.send();
}
}