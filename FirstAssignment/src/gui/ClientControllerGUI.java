package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Element;
import javax.swing.text.TableView.TableRow;

import model.Agent;
import model.Client;

import java.awt.Color;

public class ClientControllerGUI {
	private JTable table;
	private JTextField txtInsertClientData;
	private JTextField txtNewClientData;
	private NormalUserGUI normalGUI;
	private static Client client;
	private static int row,col;
	private JTextField txtId;
	/**
	 * @wbp.parser.entryPoint
	 */
	
	public ClientControllerGUI(){	
		
	
		 
		DefaultTableModel clientModel=new DefaultTableModel() ;
		clientModel.addColumn("ID");
		clientModel.addColumn("Name");
		clientModel.addColumn("CNP");
		clientModel.addColumn("Adress");
		clientModel.addColumn("Agent");
		
		JFrame frame=new JFrame("Clients data");
		 frame.setBackground(Color.WHITE);
		 frame.getContentPane().setBackground(UIManager.getColor("MenuBar.background"));
		 frame.getContentPane().setLayout(null);
		 frame.setVisible(true);
		 frame.setBounds(700, 300, 589, 510);
		 
		 
		 //labels
		 JLabel lblwrongAgentId = new JLabel("! wrong agent data");
		 lblwrongAgentId.setForeground(Color.RED);
		 lblwrongAgentId.setBounds(331, 69, 133, 16);
		 lblwrongAgentId.setVisible(false);
		 frame.getContentPane().add(lblwrongAgentId);
		
		 JLabel lblclientAlreadyExists = new JLabel("!client already exists");
		 lblclientAlreadyExists.setForeground(Color.RED);
		 lblclientAlreadyExists.setBounds(331, 69, 149, 16);
		 lblclientAlreadyExists.setVisible(false);
		 frame.getContentPane().add(lblclientAlreadyExists);
		 
		 JLabel lblClientData = new JLabel("Clients data");
		 lblClientData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		 lblClientData.setBounds(12, 0, 122, 32);
		 frame.getContentPane().add(lblClientData);
		
		 JLabel lblAddedNewClient = new JLabel("Added new client successfully!");
		 lblAddedNewClient.setFont(new Font("Tahoma", Font.PLAIN, 15));
		 lblAddedNewClient.setForeground(Color.BLUE);
		 lblAddedNewClient.setBounds(294, 58, 211, 16);
		 frame.getContentPane().add(lblAddedNewClient);
		 
		 JLabel lblwrongId = new JLabel("!wrong id");
		 lblwrongId.setForeground(Color.RED);
		 lblwrongId.setBounds(467, 251, 56, 16);
		 lblwrongId.setVisible(false);
		 frame.getContentPane().add(lblwrongId);
		 
		 lblAddedNewClient.setVisible(false);
		//addClient panel
		 
		
		 
		 JPanel addClientPanel = new JPanel();
		 addClientPanel.setBounds(266, 114, 224, 78);
		 frame.getContentPane().add(addClientPanel);
		 addClientPanel.setLayout(null);
		 
		 txtInsertClientData = new JTextField();
		 txtInsertClientData.setBounds(12, 13, 116, 22);
		 addClientPanel.add(txtInsertClientData);
		 txtInsertClientData.setText("insert client data");
		 txtInsertClientData.setColumns(10);
		 
		 JButton btnNewButton = new JButton("Add");
		 btnNewButton.setBounds(140, 12, 66, 25);
		 btnNewButton.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		StringTokenizer stok = new StringTokenizer(txtInsertClientData.getText(), ",");
				String[]tokens = new String[stok.countTokens()];
				for(int i=0; i<tokens.length; i++)
					{
						tokens[i] = stok.nextToken();
					}
				Client client=new Client(tokens[0].toString(),Integer.parseInt(tokens[1]),tokens[2].toString());
				client.setAgent(new Agent(LogInGUI.agentAccount.getId()));
				if(client.getAgent().findById().getName()==null)
				{
					lblwrongAgentId.setVisible(true);
					
				}else
				{
					int id=client.insertClient();
					if(id==-1)
					{
						lblwrongAgentId.setVisible(false);
						 lblclientAlreadyExists.setVisible(true);
					}
					else
					{
						String[] aux={Integer.toString(id),client.getName(),Integer.toString(client.getCNP()),client.getAdress(),Integer.toString(client.getAgent().getId())};
						clientModel.addRow(aux);
						lblclientAlreadyExists.setVisible(false);
						lblwrongAgentId.setVisible(false);
						addClientPanel.setVisible(false);
						clientModel.fireTableDataChanged();
						lblAddedNewClient.setVisible(true);
						
					}
					
					
				}
				}
		 });
		 addClientPanel.add(btnNewButton);
		
		 JLabel lblnamepncadressagentId = new JLabel("*name,PNC,adress");
		 lblnamepncadressagentId.setBounds(22, 34, 170, 16);
		 addClientPanel.add(lblnamepncadressagentId);
		 
		 addClientPanel.setVisible(false);
		 
		 JButton btnAddClient = new JButton("Add client");
		 btnAddClient.setBounds(288, 87, 185, 25);
		 btnAddClient.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		addClientPanel.setVisible(true);
		 		
		 	}
		 });
		 
		 
		 
		 frame.getContentPane().add(btnAddClient);
		 
		 JButton btnNewButton_1 = new JButton("Back");
		 btnNewButton_1.setBounds(12, 425, 97, 25);
		 btnNewButton_1.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		 frame.setVisible(false);
		 		 normalGUI=new NormalUserGUI();
		 	}
		 });
		 frame.getContentPane().add(btnNewButton_1);
		 
		 JButton btnShow = new JButton("Show");
		 btnShow.setFont(new Font("Tahoma", Font.BOLD, 13));
		 btnShow.setBounds(146, 7, 97, 25);
		 btnShow.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		 int flag=0;
		 		try {
		 			Client caux=new Client();
					for(Client c:caux.getAll())
					{
						if(c!=null)
						{
							for(int i=0;i<clientModel.getRowCount();i++)
								{
									if(Integer.parseInt(clientModel.getValueAt(i, 0).toString())==c.getId())
										flag=1;	
								}
									
							if(flag==0)
								{
									String[] aux={Integer.toString(c.getId()),c.getName(),Integer.toString(c.getCNP()),c.getAdress(),Integer.toString(c.getAgent().getId())};
									clientModel.addRow(aux);
									clientModel.fireTableDataChanged();
								}
							
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		 		
		 	}
		 });
		 frame.getContentPane().add(btnShow);
		
		 
		 
		//update Panel
		 JPanel updatePanel = new JPanel();
		 updatePanel.setBounds(12, 296, 297, 47);
		 frame.getContentPane().add(updatePanel);
		 updatePanel.setLayout(null);
		 
		 txtNewClientData = new JTextField();
		 txtNewClientData.setBounds(0, 0, 194, 22);
		 updatePanel.add(txtNewClientData);
		 txtNewClientData.setText("new client data");
		 txtNewClientData.setColumns(10);
		 
		 JLabel label = new JLabel("*name/adress");
		 label.setBounds(10, 18, 170, 16);
		 updatePanel.add(label);
		
		 JButton btnUpdate = new JButton("Update?");
		 btnUpdate.setBounds(74, 259, 97, 25);
		 btnUpdate.setVisible(false);
		 btnUpdate.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		 updatePanel.setVisible(true);
		 	}
		 });
		
		 frame.getContentPane().add(btnUpdate);
		
		 JButton btnUpdate_1 = new JButton("Update");
		 btnUpdate_1.setBounds(200, -1, 85, 25);
		 btnUpdate_1.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		 if(col==1)
		        	{
		        		client.updateName(txtNewClientData.getText().toString());
		        		clientModel.setValueAt(txtNewClientData.getText(), row, col);
		        		clientModel.fireTableDataChanged();
		        	}
		 		if(col==3)
	        	{
	        		client.updateAdress(txtNewClientData.getText());
	        		clientModel.setValueAt(txtNewClientData.getText(), row, col);
	        		clientModel.fireTableDataChanged();
	        	}
		 	
		 		updatePanel.setVisible(false);
		 		btnUpdate.setVisible(false);
		 	}
		 });
		 updatePanel.add(btnUpdate_1);
		
		 updatePanel.setVisible(false);
		
		 //delete Panel
		 JPanel deletePanel = new JPanel();
		 deletePanel.setBounds(289, 251, 175, 47);
		 frame.getContentPane().add(deletePanel);
		 deletePanel.setLayout(null);
		 deletePanel.setVisible(false);
		 
		 JButton btnNewButton_2 = new JButton("Delete client");
		 btnNewButton_2.setBounds(288, 221, 185, 25);
		 btnNewButton_2.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		 deletePanel.setVisible(true);
		 	}
		 });
		 frame.getContentPane().add(btnNewButton_2);
		 
		 
		 txtId = new JTextField();
		 txtId.setBounds(29, 0, 25, 22);
		 deletePanel.add(txtId);
		 txtId.setText("id");
		 txtId.setColumns(10);
		 
		 JButton btnDelete = new JButton(" Delete");
		 btnDelete.setBounds(66, -1, 97, 25);
		 btnDelete.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		 Client c=new Client(Integer.parseInt(txtId.getText()));
		 		
		 		 if(c.findById().getName()==null)
		 			lblwrongId.setVisible(true);
		 		 else{ 
		 			 lblwrongId.setVisible(false);
		 			 c.deleteClient();
		 			 for(int i=0;i<clientModel.getRowCount();i++)
		 				 if(c.getId()==Integer.parseInt(clientModel.getValueAt(i, 0).toString()))
		 				 {
		 					 clientModel.removeRow(i);
		 					
		 					 break;
		 				 
		 				 }
		 		 }
		 	}
		 });
		 deletePanel.add(btnDelete);
		
		 //client table stuff
		 table = new JTable(clientModel);
		 table.setBounds(12, 58, 231, 188);
		 frame.getContentPane().add(table);
		 
		 JLabel lblId = new JLabel("ID");
		 lblId.setBounds(12, 41, 56, 16);
		 frame.getContentPane().add(lblId);
		 
		 JLabel lblName = new JLabel("Name");
		 lblName.setBounds(61, 41, 56, 16);
		 frame.getContentPane().add(lblName);
		 
		 JLabel lblPnc = new JLabel("Adress");
		 lblPnc.setBounds(146, 41, 56, 16);
		 frame.getContentPane().add(lblPnc);
		 
		 JLabel lblAgentid = new JLabel("AgentID");
		 lblAgentid.setBounds(198, 41, 56, 16);
		 frame.getContentPane().add(lblAgentid);
		 
		 JLabel lblPnc_1 = new JLabel("PNC");
		 lblPnc_1.setBounds(105, 41, 56, 16);
		 frame.getContentPane().add(lblPnc_1);
		 table.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseReleased(MouseEvent e) {
			    	btnUpdate.setVisible(true);
			    	lblAddedNewClient.setVisible(false);
					       row = table.rowAtPoint(e.getPoint());
					        col=table.columnAtPoint(e.getPoint());
					        if(row>=0 && col>=0&& row<table.getRowCount()&& col<table.getColumnCount())
					        {
					        	client= new Client(Integer.parseInt(table.getValueAt(row, 0).toString()));
					        }
					    	else
					 			{
					    			btnUpdate.setVisible(false);
					    			updatePanel.setVisible(false);
					 			}
			    }
			});
		 
		 frame.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseReleased(MouseEvent e) {
			    	lblAddedNewClient.setVisible(false);
			    	btnUpdate.setVisible(false);    
			    	lblwrongAgentId.setVisible(false);
					addClientPanel.setVisible(false);
					deletePanel.setVisible(false);
					updatePanel.setVisible(false);
				    lblwrongId.setVisible(false);
					
					 
					
			    }
			});
		 
		 
}
}