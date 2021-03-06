  
/**    
* 文件名：JJYTableCell.java    
*    
* 版本信息：    
* 日期：2017年2月2日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package JJYComponent;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**    
*     
* 项目名称：ProxyManager    
* 类名称：JJYTableCell    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月2日 下午4:31:57    
* 修改人：jinyu    
* 修改时间：2017年2月2日 下午4:31:57    
* 修改备注：    
* @version     
*     
*/
public class JJYTableCell implements TableCellRenderer {

	/*
	* Title: getTableCellRendererComponent
	*Description: 
	* @param arg0
	* @param arg1
	* @param arg2
	* @param arg3
	* @param arg4
	* @param arg5
	* @return 
	 
	*/
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelect, boolean hasFocus, int row,
			int column) {
//		Boolean b=(Boolean)value;
		JCheckBox ck=new JCheckBox();
		ck.setSelected(isSelect);
		ck.setHorizontalAlignment((int) 0.5f);
		return ck;
	}

}
