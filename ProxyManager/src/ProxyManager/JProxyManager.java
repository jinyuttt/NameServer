  
/**    
* �ļ�����JProxyManager.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��23��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package ProxyManager;


import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import AppConfig.ManagerGlobalConfig;
import JJYComponent.JJYTableCell;
import ProcessMessage.InnerMessage;
import SeverManager.ServerManager;


import javax.swing.JScrollPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**    
*     
* ��Ŀ���ƣ�ProxyManager    
* �����ƣ�JProxyManager    
* ��������    ������Ϣ����
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��23�� ����1:41:48    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��23�� ����1:41:48    
* �޸ı�ע��    
* @version     
*     
*/
public class JProxyManager extends JPanel {
	/**   
	* @Title: JProxyManager.java 
	* @Package ProxyManager 
	* @Description: TODO(��һ�仰�������ļ���ʲô) 
	* @author fsjohnhuang
	* @date 2017��1��24�� ����12:37:21 
	* @version V1.0   
	*/
	ServerManager manager=new ServerManager();
	private static final long serialVersionUID = 1L;
	private JTable tb_server;
	private JTable tb_log;
    private DefaultTableModel datamodel=new DefaultTableModel();
    private JTabbedPane tabbedPane;
	/**
	 * Create the panel.
	 */
	public JProxyManager() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				ServerManager manager=new ServerManager();
				manager.InitServiceRec("*", 3333, "TCP");
				manager.InitClientRequest("*", 3334, "TCP");
				//
				InnerMessage.getInstance().PostMessage(this, "AddUILog", "���ӷ���ɹ�!");
			}
		});
		setLayout(new GridLayout(0,1,0,0));
		//setLayout(new  BorderLayout(0, 0));
		String[] headers = {"sid", "����", "IP", "�˿�","״̬","�Ƿ������ӷ���","����"};
		Object[][] cellData = null;
		DefaultTableModel modelserver = new DefaultTableModel(cellData, headers);
		//
		String[] logheadrs = { "��־ʱ��", "��־����", "��־��ע" };
		Object[][] logdata = null;
		DefaultTableModel modellog = new DefaultTableModel(logdata, logheadrs);
		//
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		//tabbedPane.setLayout(new GridLayout(0,1,0,0));
		JPanel pl_server = new JPanel();
		//pl_server.setLayout(new GridLayout(0,1,0,0));
		//new BorderLayout(0, 0)
		pl_server.setLayout( new BorderLayout(0, 0));
		tabbedPane.addTab("������Ϣ", null, pl_server, null);
		
		JScrollPane srol_server = new JScrollPane();
		srol_server.setEnabled(false);
		pl_server.add(srol_server);
		
		tb_server = new JTable(modelserver);
		srol_server.setViewportView(tb_server);
		
		JPanel pl_log = new JPanel();
		pl_log.setLayout( new BorderLayout(0, 0));
		tabbedPane.addTab("��־��Ϣ", null, pl_log, null);
		
		JScrollPane srol_log = new JScrollPane();
		pl_log.add(srol_log);
		
		tb_log = new JTable(modellog);
		srol_log.setViewportView(tb_log);
		//
		ListenMessage listen=new ListenMessage();
		listen.objui=this;
		InnerMessage.getInstance().Listen("AddServer", listen);
		InnerMessage.getInstance().Listen("AddUILog", listen);
		//
		manager.InitServiceRec(ManagerGlobalConfig.ManagerServerAddress, ManagerGlobalConfig.ServerPort, "TCP");
		manager.InitClientRequest(ManagerGlobalConfig.ManagerAddress, ManagerGlobalConfig.ReqPort, "TCP");
		manager.InitHeartBeat(ManagerGlobalConfig.ManagerHeart, ManagerGlobalConfig.beatPort, "UDP");
		manager.InitRecWallSucess(ManagerGlobalConfig.ManagerWall, ManagerGlobalConfig.wallPort, "UDP");
		manager.InitRecTCPBeat(ManagerGlobalConfig.ManagerTCPBeat, ManagerGlobalConfig.tcpbeat, "TCP");
		manager.InitRecUDPNat(ManagerGlobalConfig.ManagerUDPNat, ManagerGlobalConfig.udpNatPort, "UDP");
		manager.InitRecTCPNat(ManagerGlobalConfig.ManagerTCPNat, ManagerGlobalConfig.tcpNatPort, "TCP");
		//
		InnerMessage.getInstance().PostMessage(this, "AddUILog", "���ӷ���ɹ�!");
		tb_server.getColumnModel().getColumn(6).setCellRenderer(new JJYTableCell());
		//tb_server.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JCheckBox()));
	}
	
	/**
	 * 
	* @Name: AddServerInfo 
	* @Description: ���շ�����Ϣ 
	* @param model  ����˵�� 
	* @return void    �������� 
	* @throws
	 */
	public void AddServerInfo(ServerModel model)
	{
		Object[]rowData=new Object[]{model.sid,model.name,model.IP,String.valueOf(model.port),model.status,model.master,model.isUsing};
		datamodel=(DefaultTableModel) tb_server.getModel();
		if(datamodel.getRowCount()>50)
		{
			datamodel.setRowCount(0);
		}
	for(int i=0;i<datamodel.getRowCount();i++)
	{
		if(datamodel.getValueAt(i, 0).equals(model.sid))
		{
			datamodel.removeRow(i);
			datamodel.insertRow(i, rowData);
		}
	}
		
		datamodel.addRow(rowData);
		//tb_server.getModel().
	}
	
	/**
	 * 
	* @Name: AddLog 
	* @Description: ������־ 
	* @param log  ����˵�� 
	* @return void    �������� 
	* @throws
	 */
	public void AddLog(LogInfos log)
	{
		String[]rowData=new String[]{new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log.logtime),log.logcontent,log.level};
		datamodel=(DefaultTableModel) tb_log.getModel();
		if(datamodel.getRowCount()>50)
		{
			datamodel.setRowCount(0);
		}
		datamodel.addRow(rowData);
	}

}