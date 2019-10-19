package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu {

	private JFrame frmRisk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frmRisk.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		frmRisk = new JFrame();
		frmRisk.setTitle("Risk");
		frmRisk.setBounds(100, 100, 800, 600);
		frmRisk.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRisk.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Risk");
		lblNewLabel.setBounds(250, 50, 250, 100);
		lblNewLabel.setFont(new Font("SimSun", Font.BOLD, 79));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmRisk.getContentPane().add(lblNewLabel);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmRisk.setVisible(false);
				Initial i = new Initial();
				i.setVisible(true);
			}
		});
		btnPlay.setFont(new Font("SimSun", Font.BOLD, 30));
		btnPlay.setBounds(250, 223, 250, 45);
		frmRisk.getContentPane().add(btnPlay);
		
		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.setFont(new Font("SimSun", Font.BOLD, 30));
		btnLoadGame.setBounds(250, 319, 250, 45);
		frmRisk.getContentPane().add(btnLoadGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmRisk.dispose();
			}
		});
		btnExit.setFont(new Font("SimSun", Font.BOLD, 30));
		btnExit.setBounds(250, 415, 250, 45);
		frmRisk.getContentPane().add(btnExit);	
	}

}
