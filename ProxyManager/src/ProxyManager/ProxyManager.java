  
/**    
* �ļ�����ProxyManager.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��22��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package ProxyManager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import IFrameClient.PluginUI;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;

/**    
*     
* ��Ŀ���ƣ�ProxyManager    
* �����ƣ�ProxyManager    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��22�� ����10:21:56    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��22�� ����10:21:56    
* �޸ı�ע��    
* @version     
*     
*/
@PluginUI(Name="ProxyManager")
public class ProxyManager extends JFrame {

	/**   
	* @Title: ProxyManager.java 
	* @Package ProxyManager 
	* @Description: TODO(��һ�仰�������ļ���ʲô) 
	* @author fsjohnhuang
	* @date 2017��1��23�� ����1:46:29 
	* @version V1.0   
	*/
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tb_server;
	private JTable tb_log;
	private JScrollPane scrollPane;
	private JScrollPane sol_server;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProxyManager frame = new ProxyManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    public void AddDataLog()
    {
    	
    }
    
	/**
	 * Create the frame.
	 */
	public ProxyManager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 528, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		//
		String[] headers = { "����", "IP", "�˿�" };
		Object[][] cellData = null;
		DefaultTableModel model = new DefaultTableModel(cellData, headers);
		//
		String[] logheaders = { "��־ʱ��", "��־����", "��־����" };
		Object[][] logData = null;
		DefaultTableModel logmodel = new DefaultTableModel(logData, logheaders);
		
		JPanel panel_Server = new JPanel();
		tabbedPane.addTab("��ǰ������Ϣ", null, panel_Server, null);
		
		sol_server = new JScrollPane();
		
		tb_server = new JTable(model);
		sol_server.setViewportView(tb_server);
		panel_Server.add(sol_server,BorderLayout.CENTER);
		
		JPanel panel_log = new JPanel();
		tabbedPane.addTab("��־��Ϣ", null, panel_log, null);
		
		scrollPane = new JScrollPane();
		panel_log.add(scrollPane);
		
		tb_log = new JTable(logmodel);
		scrollPane.setViewportView(tb_log);
		contentPane.setLayout(gl_contentPane);
	}
}