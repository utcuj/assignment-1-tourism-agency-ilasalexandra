package gui;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Agent;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Font;

public class CreateAccountGUI {
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private BeginGUI mainGUI;
	private static int flagAdmin=0;
	private JTextField txtName;
	private JTextField txtAdress;
	private JTextField txtPnc;
	private static Agent agentAccount;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public CreateAccountGUI(){

		 JFrame frame=new JFrame("Create new account");
		 frame.getContentPane().setBackground(Color.WHITE);
		 frame.getContentPane().setLayout(null);
		 frame.setVisible(true);
		 frame.setBounds(700, 300, 589, 510);
		 txtName = new JTextField();
		 txtName.setText("name");
		 txtName.setBounds(213, 88, 116, 22);
		 frame.getContentPane().add(txtName);
		 txtName.setColumns(10);
		 
		 txtAdress = new JTextField();
		 txtAdress.setText("adress");
		 txtAdress.setColumns(10);
		 txtAdress.setBounds(213, 112, 116, 22);
		 frame.getContentPane().add(txtAdress);
		 
		 txtPnc = new JTextField();
		 txtPnc.setText("PNC");
		 txtPnc.setColumns(10);
		 txtPnc.setBounds(213, 135, 116, 22);
		 frame.getContentPane().add(txtPnc);
		 
		 txtUsername = new JTextField();
		 txtUsername.setText("choose username");
		 txtUsername.setBounds(213, 196, 116, 22);
		 frame.getContentPane().add(txtUsername);
		 txtUsername.setColumns(10);
		 
		 txtPassword = new JPasswordField(8);
		 txtPassword.setText("password");
		 txtPassword.setBounds(213, 221, 116, 22);
		 txtPassword.setEchoChar('*');
		 frame.getContentPane().add(txtPassword);
		 txtPassword.setColumns(10);
		 
		 JRadioButton btnRadiobutton = new JRadioButton("Administrator");
		 btnRadiobutton.setBackground(Color.WHITE);
		 btnRadiobutton.setBounds(213, 252, 130, 25);
		 frame.getContentPane().add(btnRadiobutton);
		 btnRadiobutton.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 	flagAdmin=1;
			 	}
			 });
		 
		 JLabel lblNewLabel = new JLabel("New account created succesfully!");
		 lblNewLabel.setForeground(Color.BLUE);
		 lblNewLabel.setFont(new Font("Nirmala UI Semilight", Font.PLAIN, 17));
		 lblNewLabel.setBounds(147, 339, 257, 22);
		 frame.getContentPane().add(lblNewLabel);
		 lblNewLabel.setVisible(false);
		 
		 JLabel lblusernameAlreadyIn = new JLabel("!username already in use");
		 lblusernameAlreadyIn.setForeground(Color.RED);
		 lblusernameAlreadyIn.setBounds(333, 199, 170, 16);
		 frame.getContentPane().add(lblusernameAlreadyIn);
		 lblusernameAlreadyIn.setVisible(false);
		 
		 JButton btnDone = new JButton("Done");
		 btnDone.setBounds(220, 301, 97, 25);
		 frame.getContentPane().add(btnDone);
		 btnDone.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		agentAccount= new Agent(txtName.getText(),txtAdress.getText(),Integer.parseInt(txtPnc.getText()),txtUsername.getText().toString(),txtPassword.getText().toString(),flagAdmin);
			 		if(agentAccount.findbyUserName(agentAccount.getUserName()).getName()!=null)
			 				{
			 				
			 					lblusernameAlreadyIn.setVisible(true);
			 					
			 				}
			 		else
			 			{
			 				agentAccount.insertAgent();	
			 				lblusernameAlreadyIn.setVisible(false);
			 				lblNewLabel.setVisible(true);
			 			}
			 	}
			 });
	
		 
		 JButton btnBack = new JButton("Back");
		 btnBack.setBounds(12, 425, 97, 25);
		 btnBack.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 	frame.setVisible(false);
			 	mainGUI=new BeginGUI();
			 	}
			 });
	
		 frame.getContentPane().add(btnBack);
		 
	
	
		 
		 
		
		 
}
}
