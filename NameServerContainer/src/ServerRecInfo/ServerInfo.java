package ServerRecInfo;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import DDS_Transfer.IDDS_Protocol;
import ProtocolsManager.ProtocolManager;
import ProxyExchange.IDataCallBack;
import ProxyExchange.MulticastRec;

import PublicModel.ServerBinds;
import Tools.MsgPackTool;
import nameServerContainer.ManagerConfig;
import nameServerContainer.ManagerInfo;



public class  ServerInfo {
	IDataCallBack  sinfo=new RecServerInfo();

  /**
 *  ���շ������Ϣ����
 */
public void AddServer()
  {
	//��ʼ�����շ���˵ķ�����Ϣ������223�˿�
	MulticastRec  rec=new MulticastRec();
	rec.port=223;
	
	rec.Start(sinfo);
  }
/**
 * 
* @Name: SendInfo 
* @Description: �����ӵķ����ַ���ͳ�ȥע��
* @param info ������Ϣ
* @param addr  ���͵ĵ�ַ
* @param port  ���͵Ķ˿� 
* @return void    �������� 
* @throws
 */
public void SendInfo(ServerBinds info)
  {
	  StringBuilder error=new StringBuilder();
	  byte[]data=null;
	  MsgPackTool tool=new MsgPackTool();
 	  data=tool.Serialize(info, error);
 	   Boolean value=  ManagerConfig.hashConfig.getOrDefault(info.name, null);
		if(value==null)
		{
			value=ManagerConfig.IsManager;
		}
	 if(value)
	 {
		 try {
			 IDDS_Protocol protocols=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject(ManagerConfig.Communication_Type);
			 protocols.CreateClient();
			 protocols.Connect(ManagerConfig.ManagerAddr, ManagerConfig.ManagerPort);
			 protocols.ClientSocketSendData(data);
			 if(!ManagerInfo.isInit)
			 {
			    ManagerInfo.isInit=true;
			    byte[]managerbytes= protocols.RecClientSocket();
			    String managerinfo=new String(managerbytes);
		         if(managerinfo.length()>0)
		          {
			        GetManagerInfo( managerinfo);
		          }
		       //
		         System.out.println("���չ�������Ϣ�ɹ���");
			 }
			
		    protocols.ClientSocketClose();
			
		 }
		 catch(Exception ex)
		 {
			 System.out.println(ex.getMessage());
		 }
		 
	 }
  }
/**
 * 
* @Name: GetManagerInfo 
* @Description: ��ȡManager�ĵ�ַ��Ϣ 
* @param xml  ����˵�� 
* @return void    �������� 
* @throws
 */
private void GetManagerInfo(String xml)
{
	String xmlStr =xml;
	StringReader sr = new StringReader(xmlStr); 
	InputSource is = new InputSource(sr); 
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
	DocumentBuilder builder = null;
	
	try {
		builder = factory.newDocumentBuilder();
	} catch (ParserConfigurationException e1) {
		
		e1.printStackTrace();
	} 
	try {
		
		HashMap<String,String> hash=new HashMap<String,String>();
		Document doc = builder.parse(is);
	    NodeList  serverAddr=	doc.getDocumentElement().getChildNodes();
	    int size=serverAddr.getLength();
	     for(int i=0;i<size;i++)
	     {
	    	 Node childNode=serverAddr.item(i);
	    	
	    	 hash.put(childNode.getNodeName(), childNode.getTextContent());
	     }
	     ManagerInfo.hashConfig=hash;
	    //
	    
	  
	} catch (SAXException e) {
		
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
}
}