package DataProcess;


import java.util.HashMap;



import DataModel.TreansferModel;

import nameServerInterface.IServer;

public class RecData implements IServer{

	@Override
	public void GetData(byte[] data) {
	
		
	}

	@Override
	public void SetData(byte[] data) {
		
		System.out.println("接收到客户端功能数据");
	//	StringBuilder error=null;
//		MsgPackTool tool=new MsgPackTool();
//		TreansferModel model=tool.Deserialize(data, TreansferModel.class, error);
		TreansferModel model=new TreansferModel();
		model.key=String.valueOf(System.currentTimeMillis());
		model.data=new String(data);
		DataCenter.AddData(model);
	}

	@Override
	public void CallData(byte[] data) {
		
		
	}

	@Override
	public void Start(String name, HashMap<String, String> hamp) {
		
		
	}

}
