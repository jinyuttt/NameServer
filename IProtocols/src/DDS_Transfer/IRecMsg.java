package DDS_Transfer;

public interface IRecMsg {
	/*
	 * ��������
	 */
public  void RecData(String address,byte[]data);

/*
 * �ش�ͨѶʵ��
 */
public  void SaveInstance(Object call);
}
