package DDS_Transfer;


/*
 * �ýӿڽ�һ�������ͨ�ţ������������ͻ���
 * ����ͨ�ſ���
 */
public interface IDDS_Protocol {
/**
 * ��������
 * @param adress ����˵�ַ
 * @param data ���͵�����
 * @return
 */
public boolean SendData(String adress,byte[]data);

public  void DisConnect();


/**
 * ��������

 * @param Address ���յ�ַ
 * @param rec �ط��˿�
 */
public void RecData(String Address,IRecMsg rec);

/**
 * �ر������ķ����Socket
 */
public void Close();

/*
 * �ر����ӵĿͻ���
 */
public void ClientSocketClose();

/*
 * �������ӿͻ��˲�������
 */
public  void CreateClient(String ip,int port);

/*
 * ����Socket�������ʹ�������������Socket����
 */
public  void CreateClient();

/*
 * �󶨱���IP,�˿�
 * һ��÷���ֻ����CreateClient�������
 */
public  boolean BindLocal(String locahost,int port);

/*
 * ��������
 * һ��÷���ֻ����CreateClient�������
 */
public boolean Connect(String remoteaddr,int port);

/**
 * 
* @Name: ClientSocketSend 
* @Description:  ͨ��������ٴη�������,��CreateClient�����ʹ��,ʵ��ʱ�ر�Socket
* @param data  ����˵�� 
* @return void    �������� 
* @throws
 */
public void ClientSocketSend(byte[]data);

/**
 * ͨ��������ٴη�������,��CreateClient�����ʹ��
 * �ýӿڲ��ܹر�Socket��ֻ����
 */
public void ClientSocketSendData(byte[]data);

/*
 * ����˻ش�����
 */
public void ServerSocketSend(byte[]data);

/*
 * ����˻ش�����
 * ֻ�������ݲ��ر�
 */
public void ServerSocketSendData(byte[]data);

/*
 * �����ͻ������ݽ��գ�������ɺ�ر�
 */
public byte[] RecClientSocket();

/**
 * 
* @Name: RecClientSocketData 
* @Description: �ͻ��˳�����������
* @return  ����˵�� 
* @return byte[]    �������� 
* @throws
 */
public byte[] RecClientSocketData();

/**
 * ��ȡ�����ַ
 * @return
 */
public String GetBindAddress();

/**
 * ���ð󶨵�ַ (����)
 * @param addr
 */
public void SetBindAddress(String addr);

//����ʹ�õĵ�ַ
public  String GetLocalAddress();

/*
 * ����Socket�����С
 */
public void SetSocketBufferSize(int size);

/**
 * 
* @Name: SetRecBufferSize 
* @Description: ���ý��������С
* @param siez  ����˵�� 
* @return void    �������� 
* @throws
 */
public void SetRecBufferSize(int size);

/**
 * 
* @Name: GetClientAddress 
* @Description: ��ȡ����socket��ַ������Ѿ���
* @return  ����˵�� 
* @return String    �������� 
* @throws
 */
public String GetClientAddress();

}
