  
/**    
* �ļ�����PackageConfig.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��21��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SubPackage;


/**    
*     
* ��Ŀ���ƣ�SubPackage    
* �����ƣ�PackageConfig    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��21�� ����1:30:40    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��21�� ����1:30:40    
* �޸ı�ע��    
* @version     
*     
*/
public class PackageConfig {
public static int pazkagesize=100;
public static volatile int curflageid=0;
////�ܳ���flageid,packagenum,����,packageid,���ݳ��ȣ����򳤶ȣ���������������
// 4,    4     ,4        , 1   ,4        ,  4    ,   4   ,    :25
//�³����ģ����ͣ����ݳ��ȣ����򳤶ȣ�
public static final int flageidIndex=4;
public static final int packageNumIndex=8;
public static final int dataTypeIndex=9;
public static final int packageidIndex=13;
public static final int dataLenIndex=17;
public static final int ruleLenIndex=21;
public static final int dataIndex=25;
public static final int headLen=25;

}
