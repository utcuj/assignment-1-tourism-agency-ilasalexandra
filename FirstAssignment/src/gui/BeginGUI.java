package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Agent;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

public class BeginGUI {

	private LogInGUI logIn;
	private CreateAccountGUI createAcc;
	/**
	 * @wbp.parser.entryPoint
	 */
	
	public BeginGUI(){
		JFrame frame=new JFrame("Tourism agency application");
		frame.getContentPane().setBackground(Color.WHITE);
		 frame.getContentPane().setLayout(null);
		 frame.setVisible(true);
		 frame.setBounds(700, 300, 589, 510);
		 JLabel lblWelcome = new JLabel("Welcome!");
		 lblWelcome.setFont(new Font("Nirmala UI Semilight", Font.PLAIN, 33));
		 lblWelcome.setBounds(197, 142, 161, 65);
		 frame.getContentPane().add(lblWelcome);
		 
		 JButton btnLogIn = new JButton("Log in");
		 btnLogIn.setBounds(113, 251, 130, 25);
		 frame.getContentPane().add(btnLogIn);
		 btnLogIn.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 	frame.setVisible(false);
			 	logIn=new LogInGUI();
			 	}
			 });
		 JButton btnCreateAccount = new JButton("Create account");
		 btnCreateAccount.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		frame.setVisible(false);
		 		createAcc=new CreateAccountGUI();
		 	}
		 });
		 btnCreateAccount.setBounds(305, 251, 130, 25);
		 frame.getContentPane().add(btnCreateAccount);
	}
	public static void main(String[] args){
		BeginGUI mainGUI= new BeginGUI();
	}
}
