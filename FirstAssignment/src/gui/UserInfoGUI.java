package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;

public class UserInfoGUI {
	private JTable table;
	/**
	 * @wbp.parser.entryPoint
	 */
	public UserInfoGUI(){
		 JFrame frame=new JFrame("User Info");
		 frame.setBounds(700, 300, 589, 510);
		 frame.getContentPane().setLayout(null);
		 
		 JButton btnBack = new JButton("Back");
		 btnBack.setBounds(12, 425, 97, 25);
		 frame.getContentPane().add(btnBack);
		 
		 table = new JTable();
		 table.setBounds(168, 73, 323, 25);
		 frame.getContentPane().add(table);
		 
		 JLabel lblUserDetails = new JLabel("User details:");
		 lblUserDetails.setFont(new Font("Tahoma", Font.PLAIN, 17));
		 lblUserDetails.setBounds(49, 73, 107, 16);
		 frame.getContentPane().add(lblUserDetails);
}
}