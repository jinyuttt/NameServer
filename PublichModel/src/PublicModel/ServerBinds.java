package PublicModel;

/**
 * ����ע����Ϣ,�м���Ϣ
 * ���Sever_BindsInfo
 * ��ͻ��˷��͵���Ϣ
 * û��sid,communicationType
 * @author jinyu
 *
 */
public class ServerBinds {
	
  /**
 * �����ַ
 */
public String address="127.0.0.1";

  /**
 * ����˿�
 */
public int port=4444;

  /**
 * ��������
 */
public String name="��ʼ����֤";

  /**
 * ���õ�ͨ��Э�飨TCP,UDP��
 */
public String communicationType="TCP";

  /**
 * ����Ψһֵ�����ڿͻ��˱��Ѿ����ܹ�����Ϣ
 */
public String sid;

/**
 * ȷ���Ƿ���������
 * 0����1��
 */
public String master="0";

/**
 * �Ƿ�����
 * 1����0��
 * ֻ������ӷ���ʱ��������
 */
public String Is_Using="1";

/**
 * 
 */
public String slave="0";
}
