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

/*
 * ͨ��������ٴη�������,��CreateClient�����ʹ��
 * ʵ��ʱ�ر�Socket
 */
public void ClientSocketSend(byte[]data);

/*
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
 * �����ͻ������ݽ���
 */
public byte[] RecClientSocket();



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

}
