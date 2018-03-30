package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class NormalUserGUI {
	private ResControllerGUI reservationsGUI;
	private ClientControllerGUI clientsGUI;
	private LogInGUI loginGUI;
	private UserInfoGUI userInfoGUI;
	/**
	 * @wbp.parser.entryPoint
	 */
	public NormalUserGUI(){
		 JFrame frame=new JFrame("Options");
		 frame.getContentPane().setBackground(Color.WHITE);
		 frame.getContentPane().setLayout(null);
		 frame.setVisible(true);
		 frame.setBounds(700, 300, 589, 510);
		 JLabel lblYouAre = new JLabel("* You are loged in as a normal user");
		 lblYouAre.setBounds(12, 13, 387, 16);
		 frame.getContentPane().add(lblYouAre);
		 
		 JLabel lblChooseWhatYou = new JLabel("Choose what you would like to access");
		 lblChooseWhatYou.setFont(new Font("Tahoma", Font.PLAIN, 26));
		 lblChooseWhatYou.setBounds(50, 42, 438, 75);
		 frame.getContentPane().add(lblChooseWhatYou);
		 
		 JButton btnClientInfo = new JButton("Client Info");
		 btnClientInfo.setFont(new Font("Calibri", Font.BOLD, 18));
		 btnClientInfo.setBounds(65, 171, 186, 75);
		 btnClientInfo.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		frame.setVisible(false);
			 		clientsGUI=new ClientControllerGUI();
			 	}
			 });
		 frame.getContentPane().add(btnClientInfo);
		 
		 JButton btnReservationsInfo = new JButton("Reservations Info");
		 btnReservationsInfo.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 		frame.setVisible(false);
		 		reservationsGUI=new ResControllerGUI();
		 	}
		 });
		 btnReservationsInfo.setFont(new Font("Calibri", Font.BOLD, 18));
		 btnReservationsInfo.setBounds(302, 171, 186, 75);
		 frame.getContentPane().add(btnReservationsInfo);
		 
		 JButton btnBack = new JButton("Back");
		 btnBack.setBounds(12, 425, 97, 25);
		 btnBack.addActionListener(new ActionListener() {
		 	 public void actionPerformed(ActionEvent e) {
		 		 frame.setVisible(false);
		 		 loginGUI=new LogInGUI();
		 	}
		 });
		 frame.getContentPane().add(btnBack);
		 
	}
}