package gui;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextField;

import model.Agent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import javax.swing.JLabel;

public class LogInGUI {
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private BeginGUI mainGUI;
	private NormalUserGUI normalGUI;
	private AdminGUI adminGUI;
	public static Agent agentAccount;
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public LogInGUI(){
		agentAccount=new Agent();
		 JFrame frame=new JFrame("Log in");
		 frame.getContentPane().setBackground(Color.WHITE);
		 frame.getContentPane().setLayout(null);
		 frame.setVisible(true);
		 frame.setBounds(700, 300, 589, 510);
		 	 
		 txtUsername = new JTextField();
		 txtUsername.setText("user");
		 txtUsername.setBounds(218, 168, 116, 22);
		 frame.getContentPane().add(txtUsername);
		 txtUsername.setColumns(10);
		 
		 txtPassword = new JPasswordField(8);
		 txtPassword.setText("password");
		 txtPassword.setColumns(10);
		 txtPassword.setBounds(218, 203, 116, 22);
		 txtPassword.setEchoChar('*');
		 frame.getContentPane().add(txtPassword);
		 
		 JLabel lblNewLabel = new JLabel("!user does not exist");
		 lblNewLabel.setForeground(Color.RED);
		 lblNewLabel.setBounds(346, 171, 136, 16);
		 frame.getContentPane().add(lblNewLabel);
		 lblNewLabel.setVisible(false);
		 
		 JLabel lblwrongPassword = new JLabel("!wrong password");
		 lblwrongPassword.setForeground(Color.RED);
		 lblwrongPassword.setBounds(346, 206, 109, 16);
		 frame.getContentPane().add(lblwrongPassword);
		 lblwrongPassword.setVisible(false);
		 
		 JButton btnBack = new JButton("Back");
		 btnBack.setBounds(25, 425, 97, 25);
		 frame.getContentPane().add(btnBack);
		 btnBack.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 	frame.setVisible(false);
			 	mainGUI=new BeginGUI();
			 	}
			 });
		 
		 JButton btnLogIn = new JButton("Log in");
		 btnLogIn.setBounds(226, 278, 97, 25);
		 btnLogIn.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		agentAccount=agentAccount.findbyUserName(txtUsername.getText().toString());
			 		if(agentAccount.getName()==null)
			 		{
			 			lblNewLabel.setVisible(true);
			 		}
			 		else{
			 			if(agentAccount.getUserPassword().compareTo(txtPassword.getText().toString())!=0)
			 			{
			 				lblwrongPassword.setVisible(true);
			 			}
			 				
			 			else
			 				{
			 					lblwrongPassword.setVisible(false);
			 					if (agentAccount.getAdmin()==1)
							 		
				 				{
				 					frame.setVisible(false);
				 					adminGUI=new AdminGUI();
				 				}
				 				else
				 				{
				 					frame.setVisible(false);
				 					normalGUI=new NormalUserGUI();
				 				}
			 				}
			 		
			 			System.out.println(); //2016/11/16 12:08:43
			 			LocalDate todayLocalDate = LocalDate.now();
			 			agentAccount.insertLogInDate(Date.valueOf(todayLocalDate));
			 		}
			 	}});
		 frame.getContentPane().add(btnLogIn);
		 
	
}
}