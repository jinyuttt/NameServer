package nameServerClient;

import ProxyExchange.BridgeClient;

/**
 * @author jinyu
 * �������ӷ����
 * �м������
 */
public class ServerConnectPorxy {

private	BridgeClient client=new BridgeClient();
	/**
	 * 
	* ����һ���µ�ʵ�� ServerConnectPorxy.    
	* ����ʵ���������ӷ���
	* @param address
	* @param port
	* @param type_name
	 */
	public ServerConnectPorxy(String address, int port,String type_name) {
		
		client.IP=address;
		client.port=port;
		client.type_name=type_name;
		client.Create();
 		client.Connect();
	}
	/**
	 * 
	* ���½�����������.    
	*    
	* @param address
	* @param port
	 * @return 
	 */
	public boolean ResetConnectPorxy(String address, int port)
	{
		client.IP=address;
		client.port=port;
	return	client.Connect();
	}
	
	/**
	 * 
	* @Name: Close 
	* @Description: �ر�����
	* @return void    �������� 
	* @throws
	 */
  public void Close()
  {
	  if(client!=null)
	  {
		  client.Close();
	  }
  }
  /**
   * 
  * @Name: GetData 
  * @Description: ��������
  * @param para ����
  * @return  ����˵�� 
  * @return byte[]    ��������
  * @throws
   */
	public byte[] GetData(byte[] para) {
	
	byte[]data=	client.RecData(para);
	return data;
	}

	/**
	 * 
	* @Name: SetData 
	* @Description: ��������
	* @param data  ����
	* @return void    �������� 
	* @throws
	 */
	public void SetData(byte[] data) {
	
		client.SendData(data);
		
	}
	
	/**
	 * 
	* @Name: DisConnect 
	* @Description: �˿�����
	* @return void    �������� 
	* @throws
	 */
public void DisConnect()
{
	client.DisConnect();
}
}