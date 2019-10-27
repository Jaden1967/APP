package ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Menu extends JFrame {


	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		Menu m = this;
		setTitle("Risk");
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Risk");
		lblNewLabel.setBounds(250, 50, 250, 100);
		lblNewLabel.setFont(new Font("SimSun", Font.BOLD, 79));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblNewLabel);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Initial i = new Initial();
				i.setVisible(true);
				dispose();
			}
		});
		btnPlay.setFont(new Font("SimSun", Font.BOLD, 30));
		btnPlay.setBounds(250, 223, 250, 45);
		getContentPane().add(btnPlay);
		
		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				LoadPrompt lp = new LoadPrompt(m);
				lp.setVisible(true);
			}
		});
		btnLoadGame.setFont(new Font("SimSun", Font.BOLD, 30));
		btnLoadGame.setBounds(250, 319, 250, 45);
		getContentPane().add(btnLoadGame);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setFont(new Font("SimSun", Font.BOLD, 30));
		btnExit.setBounds(250, 415, 250, 45);
		getContentPane().add(btnExit);	
	}

}
