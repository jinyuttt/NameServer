  
/**    
* �ļ�����ManagerConfig.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��26��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package nameServerContainer;

import java.util.HashMap;

/**    
*     
* ��Ŀ���ƣ�NameServerContainer    
* �����ƣ�ManagerConfig    
* ��������    �����Ƿ����ù�������
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��26�� ����1:46:27    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��26�� ����1:46:27    
* �޸ı�ע��    
* @version     
*     
*/
public class ManagerConfig {
/**
 * ������������ͨѶ��ʽ
 * ��������ù�������������Ч
 */
public static String Communication_Type="TCP";
/**
 * ȫ�ֲ��� �÷����Ƿ񴫵ݸ���������
 */
public static  boolean  IsManager=true;
/**
 * ��Ը������������Ƿ񴫵ݸ�����
 */
public static HashMap<String,Boolean> hashConfig=new HashMap<String,Boolean>();

/**
 * ��������IP
 */
public static String ManagerAddr="127.0.0.1";
/**
 * �������Ķ˿�
 */
public static int ManagerPort=3333;
}
