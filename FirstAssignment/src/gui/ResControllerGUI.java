package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Client;
import model.Holiday;
import model.Payment;
import model.Person;
import model.Reservation;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class ResControllerGUI {
	private JTextField txtId;
	private JTable table;
	private JTable table_1;
	private JTextField txtSum;
	private NormalUserGUI normalGUI;
	private JTextField textField;
	private static int row1,row,col;
	private static Reservation reservation;
	public static Reservation addedRes;
	public static Date modificationDate;

	private JTextField textField_1;
	private static List<Person> persons=new ArrayList<Person>();


	/**
	 * @wbp.parser.entryPoint
	 */
	
	public ResControllerGUI(){
		
		DefaultTableModel reservationModel=new DefaultTableModel() ;
		reservationModel.addColumn("ID");
		reservationModel.addColumn("Client ID");
		reservationModel.addColumn("Vacation ID");
		reservationModel.addColumn("Location");
		reservationModel.addColumn("Hotel");
		reservationModel.addColumn("Total fee");
		reservationModel.addColumn("Final pay date");
		
		DefaultTableModel personModel=new DefaultTableModel() ;
		personModel.addColumn("ID");
		personModel.addColumn("Name");
		personModel.addColumn("ReservationID");
		personModel.addColumn("Payed");
		
		 JFrame frame=new JFrame("Reservations data");
		 frame.getContentPane().setLayout(null);
		 frame.setVisible(true);
		 frame.setBounds(700, 300, 771, 525);

		 JLabel lblClientId = new JLabel("Client ID:");
		 lblClientId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 lblClientId.setBounds(68, 37, 75, 20);
		 frame.getContentPane().add(lblClientId);
		
		 JLabel lblselectReservation = new JLabel("!select reservation");
		 lblselectReservation.setForeground(Color.RED);
		 lblselectReservation.setVisible(false);
		 lblselectReservation.setBounds(564, 65, 131, 16);
		
		 frame.getContentPane().add(lblselectReservation);
		 txtId = new JTextField();
		 txtId.setText("id");
		 txtId.setBounds(155, 37, 24, 22);
		 frame.getContentPane().add(txtId);
		 txtId.setColumns(10);
		 JPanel panel_2 = new JPanel();
		 panel_2.setLayout(null);
		 panel_2.setBounds(538, 224, 167, 15);
		 panel_2.setVisible(false);
		 frame.getContentPane().add(panel_2);
		 
		 JLabel label_1 = new JLabel("ID");
		 label_1.setBounds(12, 0, 56, 16);
		 panel_2.add(label_1);
		 
		 JLabel lblName_1 = new JLabel("Name");
		 lblName_1.setBounds(54, 0, 56, 16);
		 panel_2.add(lblName_1);
		 
		 JLabel lblResId_1 = new JLabel("Res ID");
		 lblResId_1.setBounds(111, 0, 56, 16);
		 panel_2.add(lblResId_1);
		 


		 
		 JLabel lblAdded = new JLabel("Added!");
		 lblAdded.setForeground(Color.BLUE);
		 lblAdded.setBounds(154, 303, 56, 16);
		 frame.getContentPane().add(lblAdded);
		 lblAdded.setVisible(false);
		 
		 JLabel lblwrongId = new JLabel("!wrong id/client doesn't have reservations");
		 lblwrongId.setForeground(Color.RED);
		 lblwrongId.setBounds(46, 8, 249, 16);
		 lblwrongId.setVisible(false);
		 frame.getContentPane().add(lblwrongId);
		
		 JLabel lblError = new JLabel("Error");
		 lblError.setFont(new Font("Tahoma", Font.BOLD, 17));
		 lblError.setForeground(new Color(255, 0, 0));
		 lblError.setBounds(327, 11, 75, 16);
		 lblError.setVisible(false);
		 frame.getContentPane().add(lblError);
		 JLabel lblDone = new JLabel("Done!");
		 lblDone.setForeground(Color.BLUE);
		 lblDone.setBounds(685, 299, 56, 16);
		 lblDone.setVisible(false);
		 frame.getContentPane().add(lblDone);
		 JPanel reservationTables = new JPanel();
		 reservationTables.setBounds(68, 59, 458, 231);
		 frame.getContentPane().add(reservationTables);
		 reservationTables.setLayout(null);
		 
		 
		 
		 
		 
		 JLabel lblPeopleOnThe = new JLabel("People on the same reservation");
		 lblPeopleOnThe.setBounds(21, 116, 365, 16);
		 reservationTables.add(lblPeopleOnThe);
		 lblPeopleOnThe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 
		 JLabel lblReservations = new JLabel("Reservations");
		 lblReservations.setBounds(21, 0, 116, 16);
		 reservationTables.add(lblReservations);
		 lblReservations.setFont(new Font("Tahoma", Font.PLAIN, 16));
		 reservationTables.setVisible(false);
		
		 
		
	
		 JButton btnBack = new JButton("Back");
		 btnBack.setBounds(12, 425, 97, 25);
		 btnBack.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		 frame.setVisible(false);
		 		 normalGUI=new NormalUserGUI();
		 	}
		 });
		 frame.getContentPane().add(btnBack);
		 
		 
		 
		 
		 JPanel partialPaymentsPanel = new JPanel();
		 partialPaymentsPanel.setBounds(528, 94, 167, 86);
		 frame.getContentPane().add(partialPaymentsPanel);
		 partialPaymentsPanel.setLayout(null);
		 partialPaymentsPanel.setVisible(false);
		
		 txtSum = new JTextField();
		 txtSum.setBounds(10, 51, 116, 22);
		 partialPaymentsPanel.add(txtSum);
		 txtSum.setText("sum");
		 txtSum.setColumns(10);
		 
		 JButton btnAddPartialPayment = new JButton("Add partial payment");
		 btnAddPartialPayment.setBounds(0, 13, 167, 25);
		 btnAddPartialPayment.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {

		 		 try{
			 		 int sum=Integer.parseInt(txtSum.getText());
		 			 reservation.addPartialPayment(sum);
		 		 }catch(NullPointerException e4){
		 			 lblselectReservation.setVisible(true);
		 		 }catch(NumberFormatException e5){
		 			 
		 		 }
		 		reservationModel.setValueAt(reservation.getPaymentInfo().getTotalPayment(), row, col);
		 		reservationModel.fireTableDataChanged();
		 	}
		 });
		 partialPaymentsPanel.add(btnAddPartialPayment);
	
		 table_1 = new JTable(personModel);
		 table_1.setBounds(21, 161, 427, 70);
		

		 reservationTables.add(table_1);
		 
		 
		 table = new JTable(reservationModel);
		 table.setBounds(21, 41, 427, 70);
		 reservationTables.add(table);
		 
		 JPanel panel = new JPanel();
		 panel.setBounds(21, 23, 446, 15);
		 reservationTables.add(panel);
		 panel.setLayout(null);
		 
		 JLabel lblClientId_1 = new JLabel("C ID");
		 lblClientId_1.setBounds(79, 0, 56, 16);
		 panel.add(lblClientId_1);
		 
		 JLabel lblId = new JLabel("ID");
		 lblId.setBounds(12, 0, 56, 16);
		 panel.add(lblId);
		 
		 JLabel lblVid = new JLabel("VID");
		 lblVid.setBounds(135, 0, 56, 16);
		 panel.add(lblVid);
		 
		 JLabel lblDest = new JLabel("Dest.");
		 lblDest.setBounds(191, 0, 56, 16);
		 panel.add(lblDest);
		 
		 JLabel lblHotel = new JLabel("Hotel");
		 lblHotel.setBounds(258, 0, 56, 16);
		 panel.add(lblHotel);
		 
		 JLabel lblPayment = new JLabel("Payment");
		 lblPayment.setBounds(311, 0, 56, 16);
		 panel.add(lblPayment);
		 
		 JLabel lblFinalPayDate = new JLabel("Final pay ");
		 lblFinalPayDate.setBounds(369, 0, 87, 16);
		 panel.add(lblFinalPayDate);
		 
		 JPanel panel_1 = new JPanel();
		 panel_1.setLayout(null);
		 panel_1.setBounds(12, 145, 446, 15);
		 reservationTables.add(panel_1);
		 
		 JLabel label = new JLabel("ID");
		 label.setBounds(12, 0, 56, 16);
		 panel_1.add(label);
		 
		 JLabel lblResId = new JLabel("Res ID");
		 lblResId.setBounds(113, 0, 56, 16);
		 panel_1.add(lblResId);
		 
		 JLabel lblName = new JLabel("Name");
		 lblName.setBounds(222, 0, 56, 16);
		 panel_1.add(lblName);
		 
		 JLabel lblPayed = new JLabel("Payed?");
		 lblPayed.setBounds(329, 0, 56, 16);
		 panel_1.add(lblPayed);
		 table.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseReleased(MouseEvent e) {
			    		partialPaymentsPanel.setVisible(true);
					    row = table.rowAtPoint(e.getPoint());
					    col=table.columnAtPoint(e.getPoint());  
					    Client client=new Client(Integer.parseInt(txtId.getText()));
					    Holiday holiday=new Holiday(Integer.parseInt(table.getValueAt(row, 2).toString()),table.getValueAt(row, 3).toString(),table.getValueAt(row, 4).toString());
					    Payment pay=new Payment(Integer.parseInt(table.getValueAt(row, 5).toString()));
					    reservation=new Reservation(Integer.parseInt(table.getValueAt(row, 0).toString()),client,holiday,pay);
					    Person person=new Person();
					    for(int i=0;i<personModel.getRowCount();i++){
					    	 personModel.removeRow(i);
					     }

			 				for(Person p:person.getAll(Integer.parseInt(txtId.getText())))
			 				{			 				 	
							    	int flag2=0;
							    	for(int i=0;i<personModel.getRowCount();i++)
							    		if(Integer.parseInt(personModel.getValueAt(i, 0).toString())==p.getId())
							    			flag2=1;
							    	if(flag2==0)
							    	{	
							    		if(Integer.parseInt(table.getValueAt(row, 0).toString())==p.getReservation().getId())
							    		{
							    			String[] aux={Integer.toString(p.getId()),Integer.toString(p.getReservation().getId()),p.getName(),Integer.toString(p.getPayed())};
							    	
							    			personModel.addRow(aux);
							    			//reservationModel.addRow(aux);
							    			//reservationModel.fireTableDataChanged();
							    		}
							    	}

			 				}}
			});

		 //addPanel
		 JPanel addPanel = new JPanel();
		 addPanel.setBounds(121, 325, 507, 125);
		 frame.getContentPane().add(addPanel);
		 addPanel.setLayout(null);
		 
		 textField = new JTextField();
		 textField.setBounds(33, 0, 311, 25);
		 addPanel.add(textField);
		 textField.setColumns(10);
		 
		 textField_1 = new JTextField();
		 textField_1.setColumns(10);
		 textField_1.setBounds(33, 44, 311, 25);
		 addPanel.add(textField_1);
		 
		 JButton btnAdd = new JButton("Add reservation");
		 btnAdd.setBounds(356, 0, 139, 25);
		 btnAdd.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		
			 		StringTokenizer stok = new StringTokenizer( textField.getText(), ",");
					String[]tokens = new String[stok.countTokens()];
					for(int i=0; i<tokens.length; i++)
						{
							tokens[i] = stok.nextToken();
						}
					Client client=new Client(Integer.parseInt(txtId.getText()));
					client=client.findById();
					Holiday holiday=new Holiday(Integer.parseInt(tokens[0]));
					Date date =new Date(Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
					Payment payment=new Payment(Integer.parseInt(tokens[1]),date);
					
					Reservation reservation=new Reservation(client,holiday,payment);
			
						int id=reservation.insertReservation();
						addedRes=reservation;
						modificationDate=Date.valueOf(LocalDate.now());
						if(id==-1)
						{
							lblError.setVisible(true);
						}
						else
						{
							String[] aux={Integer.toString(id),Integer.toString(client.getId()),Integer.toString(holiday.getId()),Integer.toString(payment.getTotalPayment()),Integer.toString(payment.getPaymentDate().getYear())+" "+Integer.toString(payment.getPaymentDate().getMonth())+" "+Integer.toString(payment.getPaymentDate().getDay())};
							reservationModel.addRow(aux);
							lblAdded.setVisible(true);
							lblError.setVisible(false);
							
						}
					}
					
			 	
			 });
		 addPanel.add(btnAdd);
	
		 
		 JLabel lblclientIdvacationId = new JLabel("*vacation ID, total payment, final paymeny date: year,month,day");
		 lblclientIdvacationId.setBounds(33, 24, 427, 16);
		 addPanel.add(lblclientIdvacationId);
		 
		 JLabel lblpersonidname = new JLabel("*reservationID,name,payed");
		 lblpersonidname.setBounds(33, 71, 225, 16);
		 addPanel.add(lblpersonidname);
		 
		 JButton btnAddPerson = new JButton("Add person");
		 btnAddPerson.setBounds(356, 44, 139, 25);
		 btnAddPerson.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		StringTokenizer stok = new StringTokenizer( textField_1.getText(), ",");
					String[]tokens = new String[stok.countTokens()];
					for(int i=0; i<tokens.length; i++)
						{
							tokens[i] = stok.nextToken();
						}
					Reservation r=new Reservation(Integer.parseInt(tokens[0]));
					r=r.findById();
					Person person=new Person(r,tokens[1].toString(),Integer.parseInt(tokens[2]));
					int id=person.insertPerson();
					person.setId(id);
					persons.add(person);
					
			 	}
			 });
		 addPanel.add(btnAddPerson);
		 addPanel.setVisible(false);
		 
		 JButton btnAddReservations = new JButton("Edit Reservations");
		 btnAddReservations.setBounds(253, 299, 149, 25);
		 btnAddReservations.setVisible(false);
		 btnAddReservations.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		addPanel.setVisible(true);
			 	}
			 });
		 frame.getContentPane().add(btnAddReservations);
		 
	
		 
		 DefaultTableModel missedPersons=new DefaultTableModel();
		 missedPersons.addColumn("Person ID");
		 missedPersons.addColumn("Person Name");
		 missedPersons.addColumn("Reservation ID");
		 
		 JTable missedTable = new JTable(missedPersons);
		 missedTable.setBounds(538, 239, 167, 51);
		 missedTable.setVisible(false);
		 frame.getContentPane().add(missedTable);
		 
		 JButton btnNewButton = new JButton("See missed payments");
		 btnNewButton.setBounds(538, 201, 167, 25);
		 btnNewButton.setVisible(false);
		 btnNewButton.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		missedTable.setVisible(true);
				    panel_2.setVisible(true);
			 		for(int i=0;i<missedPersons.getRowCount();i++)
			 			missedPersons.removeRow(i);
			 		List<Person> persons=new ArrayList<Person>();
			 		Person paux=new Person();
			 		for(Person p:paux.getAll(Integer.parseInt(txtId.getText())))
			 		{
			 		//	System.out.println(p.getReservation().getPaymentInfo().getTotalPayment());
			 			if(p.seeMissedPayments()!=null)
			 				persons.add(p);
			 		}
			 		for(Person p:persons)	
			 		{
			 			int flag2=0;
			 			for(int i=0;i<missedPersons.getRowCount();i++)
				    		if(Integer.parseInt(missedPersons.getValueAt(i, 0).toString())==p.getId())
				    			flag2=1;
				    	if(flag2==0)
				    	{	
				    		if(Integer.parseInt(table.getValueAt(row, 0).toString())==p.getReservation().getId())
				    		{
				    			String[] aux={Integer.toString(p.getId()),p.getName(),Integer.toString(p.getReservation().getId())};
				    	
				    			missedPersons.addRow(aux);
				 		
				    		}
				    	}
			 		}
			    
			 	}});
		 frame.getContentPane().add(btnNewButton);
		 table_1.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseReleased(MouseEvent e) {
			    btnNewButton.setVisible(true);
			    
			    }});
		 JButton btnCancel = new JButton(" Cancel?");
		 btnCancel.setBounds(584, 294, 81, 25);
		 btnCancel.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		Person person =new Person(Integer.parseInt(table.getValueAt(row1, 0).toString()));
			    	person.deletePerson();
			    	missedPersons.removeRow(row1);
			    	personModel.fireTableDataChanged();
			    	lblDone.setVisible(true);
			 	}
			 });
		 btnCancel.setVisible(false);
		 frame.getContentPane().add(btnCancel);
		
		 missedTable.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseReleased(MouseEvent e) {
					 btnCancel.setVisible(true);
			    	row1=table.rowAtPoint(e.getPoint());
			    	//id,name,resid
			    }});
		 JButton btnFindReservations = new JButton("Find reservations");
		 btnFindReservations.setBounds(253, 36, 149, 25);
		 btnFindReservations.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		reservationTables.setVisible(true);
				 btnAddReservations.setVisible(true);
				Reservation raux=new Reservation();
		 		 int flag=0;
		
			 		try {
			 			Client c=new Client(Integer.parseInt(txtId.getText()));
			 			
			 			Reservation res=new Reservation(c);
			 			if(res.findByClientId()==-1)
			 			{
			 				lblwrongId.setVisible(true);
			 			}
			 			else{
			 				for(Reservation r:res.findAllRes())
			 				{
			 					if(r!=null)
			 					{
			 						for(int i=0;i<reservationModel.getRowCount();i++)
			 						{
										if(Integer.parseInt(reservationModel.getValueAt(i, 0).toString())==r.getId())
											flag=1;	
									}
										
			 						if(flag==0)
			 						{
			 							Holiday h=new Holiday(r.getHoliday().getId());
			 							h=h.findById();
			 							/*reservationModel.addColumn("ID");
			 							reservationModel.addColumn("Client ID");
			 							reservationModel.addColumn("Vacation ID");
			 							reservationModel.addColumn("Location");
			 							reservationModel.addColumn("Hotel");
			 							reservationModel.addColumn("Total fee");
			 							reservationModel.addColumn("Final pay date");
			 							*/
			 							String[] aux={Integer.toString(r.getId()),Integer.toString(r.getClient().getId()),Integer.toString(h.getId()),h.getDestination(),h.getHotelName(),Integer.toString(r.getPaymentInfo().getTotalPayment()),Integer.toString(r.getPaymentInfo().getPaymentDate().getYear())+" "+Integer.toString(r.getPaymentInfo().getPaymentDate().getMonth())+" "+Integer.toString(r.getPaymentInfo().getPaymentDate().getDay())};										
			 							reservationModel.addRow(aux);
			 						
									}
								
			 					}
			 				}
			 			}
			 		} catch (SQLException e1) {
						e1.printStackTrace();
					}catch(NumberFormatException e3){
						lblwrongId.setVisible(true);
						for(int i=0;i<reservationModel.getRowCount();i++)
							reservationModel.removeRow(i);
					}
		 	}
		 });
		 frame.getContentPane().add(btnFindReservations);
		 
		 JLabel lblTheReservations = new JLabel("* the reservations shown are only for loged in agent's clients");
		 lblTheReservations.setBounds(383, 8, 358, 16);
		 frame.getContentPane().add(lblTheReservations);
		 

		 
		 frame.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseReleased(MouseEvent e) {
			    	
					lblAdded.setVisible(false);
					lblwrongId.setVisible(false);
					partialPaymentsPanel.setVisible(false);
					addPanel.setVisible(false);
					lblError.setVisible(false);
			 		missedTable.setVisible(false);
			 		btnNewButton.setVisible(false);
			 		btnAddReservations.setVisible(false);
			 		lblselectReservation.setVisible(false);
					panel_2.setVisible(false);
					lblDone.setVisible(false);
					 btnCancel.setVisible(false);

					
			    }
			});
		
		
		 
}
}
