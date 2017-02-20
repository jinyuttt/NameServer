package DDS_Transfer;

public interface IRecMsg {
	/*
	 * 接受数据
	 */
public  void RecData(String address,byte[]data);

/*
 * 回传通讯实例
 */
public  void SaveInstance(Object call);
}
