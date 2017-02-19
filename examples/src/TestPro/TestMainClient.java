package TestPro;


import java.util.Date;




import DataModel.TreansferModel;
import Tools.MsgPackTool;
import nameServerClient.ProxyClient;
import nameServerInterface.IPoxyObj;

public class TestMainClient {

	public static void main(String[] args) {
//		  BerkeleyDB  berkeleydb=new BerkeleyDB();
//			berkeleydb.SetFileName("BerkeleyDBData");
//			  berkeleydb.SetDBName("DBCache");
//			  berkeleydb.openDatabase();
//	  while(true)
//	  {
//		  Map<byte[],byte[]> data=  berkeleydb.getItem(100);
//		  List<byte[]> keys=new LinkedList<byte[]>();
//		  for (byte[] key : data.keySet()) {  
//				 
//				  keys.add(key);
//		  }
//		  berkeleydb.deleteFromDatabase(keys, 10);
//		  try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	  }
		while(true)
		{
		StringBuilder error=new StringBuilder();
	    IPoxyObj proxy=	ProxyClient.CastObj("Test", error);
	    if(proxy!=null)
	    {
	     	MsgPackTool tool=new MsgPackTool();
			TreansferModel model=new TreansferModel();
			model.key=String.valueOf(System.currentTimeMillis());
			model.data=new Date().toString();
		    byte[]bytes=new Date().toString().getBytes();
		    bytes= tool.Serialize(model, error);
			
	        proxy.SetData(bytes);
	    }
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	}
	

}