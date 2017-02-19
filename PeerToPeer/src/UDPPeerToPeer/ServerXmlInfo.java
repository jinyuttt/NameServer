  
/**    
* �ļ�����XmlModel.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��8��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package UDPPeerToPeer;

import java.io.IOException;
import java.io.StringReader;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**    
*     
* ��Ŀ���ƣ�PeerToPeerServer    
* �����ƣ�XmlModel    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��8�� ����1:53:21    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��8�� ����1:53:21    
* �޸ı�ע��    
* @version     
*     
*/
public class ServerXmlInfo {
public static String HeaderXml(String serverName,String address,String xmlInfo)
{
	StringBuilder xml=new StringBuilder();
	xml.append("<NameServerInfo>");
	xml.append("<Name>");
	xml.append(serverName);
	xml.append("</Name>");
	xml.append("<Address>");
	xml.append(address);
	xml.append("</Address>");
	xml.append("<Info>");
	xml.append(xmlInfo);
	xml.append("</Info>");
	xml.append("</NameServerInfo>");
    return xml.toString();
	
}
public static ServerXml Analysis(String xmlString)
{
	String xmlStr =xmlString;
	StringReader sr = new StringReader(xmlStr); 
	InputSource is = new InputSource(sr); 
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
	DocumentBuilder builder = null;
	ServerXml xmlinfo=new ServerXml();
	try {
		builder = factory.newDocumentBuilder();
	} catch (ParserConfigurationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	try {
		String serverName="";
		String address="";
		String info="";
		Document doc = builder.parse(is);
	    NodeList name=	doc.getElementsByTagName("Name");
	    serverName=name.item(0).getTextContent();
	    NodeList  serverAddr=	doc.getElementsByTagName("Address");
	    address=serverAddr.item(0).getTextContent();
	    NodeList  serverInfo=	doc.getElementsByTagName("Info");
	    info=serverInfo.item(0).getTextContent();
	    //
	    xmlinfo.name=serverName;
	    xmlinfo.address=address;
	    xmlinfo.info=info;
	    return xmlinfo;
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	
}
}