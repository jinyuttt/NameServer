package nameServerClient;



import Tools.MsgPackTool;
import nameServerInterface.IPoxyObj;
import nameServerInterface.NetData;


 /**
 * @author jinyu
 * 服务通信客户端代理实例类
 * 与服务端通讯
 */
class ClientToServer implements IPoxyObj{

	private ServerConnectPorxy proxy;
	public String ServerName="";
	public ClientToServer(ServerConnectPorxy initproxy)
	{
		proxy=initproxy;
	}
	@Override
	public byte[] GetData(byte[] data) {
	
	    byte[]dataStream=NetStream("GetData",data);
		return proxy.GetData(dataStream);
	}

	@Override
	public void SetData(byte[] data) {
	
		byte[]dataStream=NetStream("SetData",data);
		proxy.SetData(dataStream);
	}

	@Override
	public void CallData(byte[] data) {
		
		byte[]dataStream=NetStream("CallData",data);
		proxy.SetData(dataStream);
	}
	/**
	 * 
	* @Name: NetStream 
	* @Description: 将数据传化成功能信息
	* @param name
	* @param data
	* @return  参数说明 
	* @return byte[]    返回类型 
	* @throws
	 */
   private byte[] NetStream(String name,byte[]data)
   {
	    StringBuilder error=new StringBuilder();
	    NetData curData=new NetData();
	    curData.fun_Name=name;
	    curData.serverName=ServerName;
		curData.data=data;
		MsgPackTool tool=new MsgPackTool();
	    byte[] curBytes=	tool.Serialize(curData, error);
	    return curBytes;
   }

	/** 
	* @Name: Close 
	* @Description: 关闭网络 
	* @return void    返回类型 
	* @throws 
	*/
	public void Close() {
		proxy.Close();
		
	}
	
	public void  DisConnect()
	{
		proxy.DisConnect();
	}
	
	public void Reset(String address,int port )
	{
		if(proxy!=null)
		{
			proxy.ResetConnectPorxy(address, port);
		}
	}
}
