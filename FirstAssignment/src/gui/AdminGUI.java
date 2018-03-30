package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Agent;
import model.Client;

import javax.swing.JPanel;
import java.awt.Color;

public class AdminGUI {
	private JTable table;
	private JTextField txtId;
	private JTextField txtInsertAgentData;
	private JTextField txtNewAgentData;
	private LogInGUI loginGUI;
	private JTextField txtAgentId;
	private JTextField txtStartDate;
	private JTextField txtEndDate;
	private static int row,col;
	private static Agent agent;
	/**
	 * @wbp.parser.entryPoint
	 */
	public AdminGUI(){	
		//table stuff
		DefaultTableModel agentModel=new DefaultTableModel() ;
		agentModel.addColumn("ID");
		agentModel.addColumn("Name");
		agentModel.addColumn("Adress");
		agentModel.addColumn("CNP");
		agentModel.addColumn("UserName");
		agentModel.addColumn("Password");
		agentModel.addColumn("Agent");
		//frame stuff
	
		 JFrame frame=new JFrame("Agents data");
		 frame.getContentPane().setLayout(null);
		 frame.setVisible(true);
		 frame.setBounds(700, 300, 589, 510);
		 JLabel lblAgentsInfo = new JLabel("Agents info");
		 lblAgentsInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		 lblAgentsInfo.setBounds(31, 13, 114, 25);
		 frame.getContentPane().add(lblAgentsInfo);
		 
		 JButton btnBack = new JButton("Back");
		 btnBack.setBounds(12, 425, 97, 25);
		 btnBack.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		 frame.setVisible(false);
		 		 loginGUI=new LogInGUI();
		 	}
		 });
		 
		 //lables
		 JLabel lblagentAlreadyAdded = new JLabel("!agent already added");
		 lblagentAlreadyAdded.setForeground(Color.RED);
		 lblagentAlreadyAdded.setBounds(311, 43, 174, 16);
		 lblagentAlreadyAdded.setVisible(false);
		 frame.getContentPane().add(lblagentAlreadyAdded);
		 
		 JLabel lblAgentAddedSuccsessfully = new JLabel("Agent added succsessfully!");
		 lblAgentAddedSuccsessfully.setFont(new Font("Tahoma", Font.PLAIN, 15));
		 lblAgentAddedSuccsessfully.setForeground(Color.BLUE);
		 lblAgentAddedSuccsessfully.setBounds(322, 34, 190, 16);
		 frame.getContentPane().add(lblAgentAddedSuccsessfully);
		 lblAgentAddedSuccsessfully.setVisible(false);
		 
		 JLabel lblinvalidId = new JLabel("!invalid id");
		 lblinvalidId.setForeground(Color.RED);
		 lblinvalidId.setBounds(456, 179, 56, 16);
		 frame.getContentPane().add(lblinvalidId);
		 lblinvalidId.setVisible(false);
		 
		 JLabel lblNewReportCreated = new JLabel("New report created succsessfully!");
		 lblNewReportCreated.setFont(new Font("Tahoma", Font.PLAIN, 15));
		 lblNewReportCreated.setForeground(Color.BLUE);
		 lblNewReportCreated.setBounds(340, 273, 221, 16);
		 frame.getContentPane().add(lblNewReportCreated);
		 lblNewReportCreated.setVisible(false);
		 
		 
		 //panels
		 // remove panel
		 JPanel removePanel = new JPanel();
		 removePanel.setBounds(311, 166, 140, 38);
		 frame.getContentPane().add(removePanel);
		 removePanel.setLayout(null);
		 
		 
		 txtId = new JTextField();
		 txtId.setBounds(0, 13, 31, 22);
		 removePanel.add(txtId);
		 txtId.setText("id");
		 txtId.setColumns(10);
		 
		 JButton btnDelete = new JButton("Remove:(");
		 btnDelete.setBounds(43, 12, 89, 25);
		 btnDelete.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		removePanel.setVisible(true);
			 		Agent c=new Agent(Integer.parseInt(txtId.getText()));
			 		
			 		 if(c.findById().getName()==null)
			 			lblinvalidId.setVisible(true);
			 		 else{ 
			 			lblinvalidId.setVisible(false);
			 			 c.deleteAgent();
			 			 for(int i=0;i<agentModel.getRowCount();i++)
			 				 if(c.getId()==Integer.parseInt(agentModel.getValueAt(i, 0).toString()))
			 				 {
			 					 agentModel.removeRow(i);
			 					 break;
			 				 
			 				 }
			 		 }
			 	}
			 });
		 removePanel.add(btnDelete);
		 removePanel.setVisible(false);
		 

		 JButton btnDeleteAgent = new JButton("Remove agent ");
		 btnDeleteAgent.setBounds(311, 141, 174, 25);
		 btnDeleteAgent.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		removePanel.setVisible(true);
			 	}
			 });
		 frame.getContentPane().add(btnDeleteAgent);
		 
		

		 frame.getContentPane().add(btnBack);
		 //Add panel
		 JPanel addPanel = new JPanel();
		 addPanel.setBounds(311, 92, 250, 46);
		 frame.getContentPane().add(addPanel);
		 addPanel.setLayout(null);
		 
		 txtInsertAgentData = new JTextField();
		 txtInsertAgentData.setBounds(0, 0, 107, 22);
		 addPanel.add(txtInsertAgentData);
		 txtInsertAgentData.setText("insert agent data");
		 txtInsertAgentData.setColumns(10);
		 
		 JButton btnNewButton_1 = new JButton("Add");
		 btnNewButton_1.setBounds(110, -1, 65, 25);
		 btnNewButton_1.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		StringTokenizer stok = new StringTokenizer(txtInsertAgentData.getText(), ",");
					String[]tokens = new String[stok.countTokens()];
					for(int i=0; i<tokens.length; i++)
						{
							tokens[i] = stok.nextToken();
						}
					Agent agent=new Agent(tokens[0].toString(),tokens[1].toString(),Integer.parseInt(tokens[2]),tokens[3].toString(),tokens[4].toString());
					int id=agent.insertAgent();
					if(id==-1)
					{
							lblagentAlreadyAdded.setVisible(true);
						}
					else
					{
							String[] aux={Integer.toString(id),agent.getName(),agent.getAdress(),Integer.toString(agent.getCNP()),agent.getUserName().toString(),agent.getUserPassword().toString()};
							agentModel.addRow(aux);
							lblagentAlreadyAdded.setVisible(false);
							addPanel.setVisible(false);
							agentModel.fireTableDataChanged();
							lblAgentAddedSuccsessfully.setVisible(true);
							
					}
						
						
					}
			 	
			 });
		 addPanel.add(btnNewButton_1);
		 
		 JLabel lblnameadress = new JLabel("*name,adress,PNC,username,pass,admin");
		 lblnameadress.setBounds(10, 30, 244, 16);
		 addPanel.add(lblnameadress);
		 addPanel.setVisible(false);
		 
		 JButton btnNewButton = new JButton("Add new agent");
		 btnNewButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		addPanel.setVisible(true);
		 		lblnameadress.setVisible(true);
		 	}
		 });
		 btnNewButton.setBounds(311, 63, 174, 25);
		 frame.getContentPane().add(btnNewButton);
		

		 //Update panel
		 JPanel updatePanel = new JPanel();
		 updatePanel.setBounds(31, 292, 297, 39);
		 frame.getContentPane().add(updatePanel);
		 updatePanel.setLayout(null);
		 
		 txtNewAgentData = new JTextField();
		 txtNewAgentData.setBounds(12, 0, 189, 22);
		 updatePanel.add(txtNewAgentData);
		 txtNewAgentData.setText("new agent data");
		 txtNewAgentData.setColumns(10);
		 
		 JLabel lblnameadressusernamepassword = new JLabel("*name\\adress\\username\\password\\admin");
		 lblnameadressusernamepassword.setBounds(12, 23, 259, 16);
		 updatePanel.add(lblnameadressusernamepassword);
		 
		 JButton btnUpdate = new JButton("Update?");
		 btnUpdate.setBounds(90, 255, 89, 25);
		 btnUpdate.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		updatePanel.setVisible(true);
			 		lblnameadressusernamepassword.setVisible(true);
			 	}
			 });
		 btnUpdate.setVisible(false);
		 frame.getContentPane().add(btnUpdate);
		 
		 JButton btnNewButton_2 = new JButton("Update");
		 btnNewButton_2.setBounds(212, -1, 73, 25);
		 btnNewButton_2.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		 if(col==1)
			         {
			        	agent.updateName(txtNewAgentData.getText().toString());
			        	agentModel.setValueAt(txtNewAgentData.getText(), row, col);
			       		agentModel.fireTableDataChanged();
			       	 }
			 		if(col==2)
		        	{
			 			agent.updateAdress(txtNewAgentData.getText());
			 			agentModel.setValueAt(txtNewAgentData.getText(), row, col);
			 			agentModel.fireTableDataChanged();
		        	}
			 		if(col==4)
			         {
			        	agent.updateUserName(txtNewAgentData.getText().toString());
			        	agentModel.setValueAt(txtNewAgentData.getText(), row, col);
			       		agentModel.fireTableDataChanged();
			       	 }
			 		if(col==5)
		        	{
			 			agent.updatePassword(txtNewAgentData.getText());
			 			agentModel.setValueAt(txtNewAgentData.getText(), row, col);
			 			agentModel.fireTableDataChanged();
		        	}
			 		if(col==6)
		        	{
			 			agent.updateAdmin(Integer.parseInt(txtNewAgentData.getText()));
			 			agentModel.setValueAt(txtNewAgentData.getText(), row, col);
			 			agentModel.fireTableDataChanged();
		        	}
			 		
			 		
			 		updatePanel.setVisible(false);
			 		btnUpdate.setVisible(false);
			 		lblnameadressusernamepassword.setVisible(false);
			 	}
			 });
		 updatePanel.add(btnNewButton_2);
		 updatePanel.setVisible(false);

		 JButton btnShowInfo = new JButton("Show Info");
		 btnShowInfo.setFont(new Font("Tahoma", Font.BOLD, 13));
		 btnShowInfo.setBounds(146, 16, 97, 25);
		 btnShowInfo.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		Agent auxa=new Agent();
			 		 int flag=0;
				 		try {
							for(Agent a:auxa.listAllAgents())
							{
								if(a!=null)
								{
									for(int i=0;i<agentModel.getRowCount();i++)
										{
											if(Integer.parseInt(agentModel.getValueAt(i, 0).toString())==a.getId())
												flag=1;	
										}
											
									if(flag==0)
										{
											String[] aux={Integer.toString(a.getId()),a.getName(),a.getAdress(),Integer.toString(a.getCNP()),a.getUserName(),a.getUserPassword(),Integer.toString(a.getAdmin())};
											agentModel.addRow(aux);
											agentModel.fireTableDataChanged();
										}
									
								}
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
			 	}
			 });
		 frame.getContentPane().add(btnShowInfo);
		 table = new JTable(agentModel);
		 table.setBounds(12, 65, 287, 177);
		 table.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseReleased(MouseEvent e) {
			    	btnUpdate.setVisible(true);
					       row = table.rowAtPoint(e.getPoint());
					        col=table.columnAtPoint(e.getPoint());
					        if(row>=0 && col>=0&& row<table.getRowCount()&& col<table.getColumnCount())
					        {
					        	agent= new Agent(Integer.parseInt(table.getValueAt(row, 0).toString()));
					        }
					    	else
					 			{
					    			btnUpdate.setVisible(false);
					    			updatePanel.setVisible(false);
					 			}
			    }
			});
		 frame.getContentPane().add(table);
		 JPanel createRapPanel = new JPanel();
		 createRapPanel.setBounds(306, 331, 240, 81);
		 frame.getContentPane().add(createRapPanel);
		 createRapPanel.setLayout(null);
		 
		 txtAgentId = new JTextField();
		 txtAgentId.setBounds(12, 0, 65, 22);
		 createRapPanel.add(txtAgentId);
		 txtAgentId.setText("Agent id");
		 txtAgentId.setColumns(10);
		 
		 txtStartDate = new JTextField();
		 txtStartDate.setBounds(12, 25, 116, 22);
		 createRapPanel.add(txtStartDate);
		 txtStartDate.setText("start date");
		 txtStartDate.setColumns(10);
		 
		 txtEndDate = new JTextField();
		 txtEndDate.setBounds(12, 46, 116, 22);
		 createRapPanel.add(txtEndDate);
		 txtEndDate.setText("end date");
		 txtEndDate.setColumns(10);
		 
		 JButton btnCreate = new JButton("Create");
		 btnCreate.setBounds(139, 24, 89, 25);
		 btnCreate.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		Agent agent=new Agent(Integer.parseInt(txtAgentId.getText()));
			 		agent=agent.findById();
			 		agent.createReport(null, null);
			 	}
			 });
		 createRapPanel.add(btnCreate);
		 createRapPanel.setVisible(false);
		 
		 JButton btnNewButton_3 = new JButton("Create report");
		 btnNewButton_3.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		createRapPanel.setVisible(true);
		 		
		 	}
		 });
		 btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		 btnNewButton_3.setBounds(340, 293, 174, 25);
		 frame.getContentPane().add(btnNewButton_3);
		 
		 JLabel lblId = new JLabel("Id");
		 lblId.setBounds(12, 43, 19, 16);
		 frame.getContentPane().add(lblId);
		 
		 JLabel lblName = new JLabel(" Name");
		 lblName.setBounds(49, 43, 42, 16);
		 frame.getContentPane().add(lblName);
		 
		 JLabel lblAdress = new JLabel("Adress");
		 lblAdress.setBounds(90, 43, 42, 16);
		 frame.getContentPane().add(lblAdress);
		 
		 JLabel lblPnc = new JLabel("PNC");
		 lblPnc.setBounds(137, 43, 42, 16);
		 frame.getContentPane().add(lblPnc);
		 
		 JLabel lblUsser = new JLabel("user");
		 lblUsser.setBounds(173, 43, 42, 16);
		 frame.getContentPane().add(lblUsser);
		 
		 JLabel lblPass = new JLabel("pass");
		 lblPass.setBounds(222, 43, 42, 16);
		 frame.getContentPane().add(lblPass);
		 
		 JLabel lblAdmin = new JLabel("admin");
		 lblAdmin.setBounds(257, 43, 42, 16);
		 frame.getContentPane().add(lblAdmin);
		 frame.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseReleased(MouseEvent e) {
			    	lblAgentAddedSuccsessfully.setVisible(false);
			    	btnUpdate.setVisible(false);    
			    	lblagentAlreadyAdded.setVisible(false);
					lblinvalidId.setVisible(false);
					addPanel.setVisible(false);
					updatePanel.setVisible(false);
					removePanel.setVisible(false);
					createRapPanel.setVisible(false);
					
					 
					
			    }
			});
		 
		 
	
}
}