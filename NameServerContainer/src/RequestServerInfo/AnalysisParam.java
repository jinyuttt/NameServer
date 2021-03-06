  
/**    
* 文件名：AnalysisParam.java    
*    
* 版本信息：    
* 日期：2017年1月17日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package RequestServerInfo;

import java.util.Arrays;

import Tools.NumberToTool;

/**    
*     
* 项目名称：NameServerContainer    
* 类名称：AnalysisParam    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年1月17日 下午9:02:44    
* 修改人：jinyu    
* 修改时间：2017年1月17日 下午9:02:44    
* 修改备注：    
* @version     
*     
*/
public class AnalysisParam {
 public static RequestModel GetParam(byte[]data)
 {
	 //
	 RequestModel curModel=new RequestModel();
	 int cur=data[0];
	 ServerManagerType  curType=Get(cur);
	 if(curType==null)
	 {
		 return null;
	 }
	 else
	 {
		 curModel.managerType=(ServerManagerType) curType;
	 }
	
	 //名称长度
	 int len=0;
	 byte[] lenbyte= NumberToTool.intToByte(len);
	 int tmplen=lenbyte.length;
	 lenbyte=Arrays.copyOfRange(data, 1, 1+tmplen);
	 len=NumberToTool.byteToInt(lenbyte);
	 //
	 byte[]name=Arrays.copyOfRange(data, 1+lenbyte.length, 1+lenbyte.length+len);
	 String serverName=new String(name);
	 curModel.serverName=serverName;
	 // 获取参数
	 //   
	 int index=1+lenbyte.length+len;
	 curModel.dataParam=Arrays.copyOfRange(data, index, data.length+1);
	 
    return curModel;
	 
 }
 public static  byte[] PackParam(String name,ServerManagerType managetType,byte[]param)
 {
	 int templen=0;
	 if(name.equals("")||name==null)
	 {
		 return  null;
	 }
	 if(param==null)
	 {
		 param=new byte[0];
	 }
	 // 准备数据
	 byte[] curManagerType=new byte[]{(byte) managetType.ordinal()};
	 byte[] serverName=name.getBytes();
	 byte[]nameLen=NumberToTool.intToByte(serverName.length);
	 // 全部数据
	 byte[] data=new byte[curManagerType.length+nameLen.length+serverName.length+param.length];
	 //拷贝数据
	 System.arraycopy(curManagerType, 0, data, 0, curManagerType.length);
	 templen=curManagerType.length;
	 System.arraycopy(nameLen, 0, data, templen, nameLen.length);
	 templen+=nameLen.length;
	 System.arraycopy(serverName, 0, data, templen, serverName.length);
	 templen+=serverName.length;
	 System.arraycopy(param, 0, data, templen, param.length);
	 //
	return data;
	 //
	 
 }
 private static ServerManagerType Get(int index)
 {
	 ServerManagerType[] values= ServerManagerType.values();
	 for(ServerManagerType cur:values)
	 {
		 if(cur.ordinal()==index)
		 {
			 return cur;
		 }
	 }
	 return null;
 }
}
