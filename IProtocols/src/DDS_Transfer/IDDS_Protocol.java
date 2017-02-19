package DDS_Transfer;


/*
 * 该接口将一般的数据通信，看作服务端与客户端
 * 进行通信控制
 */
public interface IDDS_Protocol {
/**
 * 发送数据
 * @param adress 服务端地址
 * @param data 发送的数据
 * @return
 */
public boolean SendData(String adress,byte[]data);

public  void DisConnect();


/**
 * 接收数据

 * @param Address 接收地址
 * @param rec 回发端口
 */
public void RecData(String Address,IRecMsg rec);

/**
 * 关闭启动的服务端Socket
 */
public void Close();

/*
 * 关闭连接的客户端
 */
public void ClientSocketClose();

/*
 * 创建连接客户端并且连接
 */
public  void CreateClient(String ip,int port);

/*
 * 创建Socket对象（如果使用组件则是类似Socket对象）
 */
public  void CreateClient();

/*
 * 绑定本地IP,端口
 * 一般该方法只能与CreateClient方法结合
 */
public  boolean BindLocal(String locahost,int port);

/*
 * 单独连接
 * 一般该方法只能与CreateClient方法结合
 */
public boolean Connect(String remoteaddr,int port);

/**
 * 
* @Name: ClientSocketSend 
* @Description:  通过服务端再次发送数据,与CreateClient搭配的使用,实现时关闭Socket
* @param data  参数说明 
* @return void    返回类型 
* @throws
 */
public void ClientSocketSend(byte[]data);

/**
 * 通过服务端再次发送数据,与CreateClient搭配的使用
 * 该接口不能关闭Socket；只发送
 */
public void ClientSocketSendData(byte[]data);

/*
 * 服务端回传数据
 */
public void ServerSocketSend(byte[]data);

/*
 * 服务端回传数据
 * 只发送数据不关闭
 */
public void ServerSocketSendData(byte[]data);

/*
 * 开启客户端数据接收，接收完成后关闭
 */
public byte[] RecClientSocket();

/**
 * 
* @Name: RecClientSocketData 
* @Description: 客户端持续接收数据
* @return  参数说明 
* @return byte[]    返回类型 
* @throws
 */
public byte[] RecClientSocketData();

/**
 * 获取传输地址
 * @return
 */
public String GetBindAddress();

/**
 * 设置绑定地址 (作废)
 * @param addr
 */
public void SetBindAddress(String addr);

//返回使用的地址
public  String GetLocalAddress();

/*
 * 设置Socket缓存大小
 */
public void SetSocketBufferSize(int size);

/**
 * 
* @Name: SetRecBufferSize 
* @Description: 设置接收区域大小
* @param siez  参数说明 
* @return void    返回类型 
* @throws
 */
public void SetRecBufferSize(int size);

/**
 * 
* @Name: GetClientAddress 
* @Description: 获取创建socket地址，如果已经绑定
* @return  参数说明 
* @return String    返回类型 
* @throws
 */
public String GetClientAddress();

}
