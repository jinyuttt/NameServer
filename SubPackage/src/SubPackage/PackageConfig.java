  
/**    
* 文件名：PackageConfig.java    
*    
* 版本信息：    
* 日期：2017年2月21日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SubPackage;


/**    
*     
* 项目名称：SubPackage    
* 类名称：PackageConfig    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月21日 上午1:30:40    
* 修改人：jinyu    
* 修改时间：2017年2月21日 上午1:30:40    
* 修改备注：    
* @version     
*     
*/
public class PackageConfig {
public static int pazkagesize=100;
public static volatile int curflageid=0;
////总长，flageid,packagenum,类型,packageid,数据长度，规则长度，数据区，规则区
// 4,    4     ,4        , 1   ,4        ,  4    ,   4   ,    :25
//吐出来的：类型：数据长度：规则长度：
public static final int flageidIndex=4;
public static final int packageNumIndex=8;
public static final int dataTypeIndex=9;
public static final int packageidIndex=13;
public static final int dataLenIndex=17;
public static final int ruleLenIndex=21;
public static final int dataIndex=25;
public static final int headLen=25;

}
