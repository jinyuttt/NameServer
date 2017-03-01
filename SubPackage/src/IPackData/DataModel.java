  
/**    
* 文件名：DataModel.java    
*    
* 版本信息：    
* 日期：2017年2月28日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package IPackData;

  
/**    
*     
* 项目名称：SubPackage    
* 类名称：DataModel    
* 类描述：    发送接收时的数据信息
* 创建人：jinyu    
* 创建时间：2017年2月28日 下午11:24:33    
* 修改人：jinyu    
* 修改时间：2017年2月28日 下午11:24:33    
* 修改备注：    
* @version     
*     
*/
public class DataModel {
	/**
	 * 数据类型，可以为传送的数据分别定义一个类型进行区分
	 * 默认-1
	 */
  public byte dataType=-1;
  
  /**
   * 传送的数据
   */
  public byte[]dataInfo=null;
  
  /**
   * 该类型数据关联的规则信息
   * 如果该数据需要额外操作的操作规则信息
   */
  public byte[]dataRule=null;
}
