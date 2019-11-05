package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import entities.Continent;
import entities.Country;
import entities.Player;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This JFrame will appear when the player insert view in the main game UI
 * It shows the information of all the players' name, how much countries they owned (in percentage), which continent they have controlled
 * It also shows the total army numbers of all the players, if one player is already lose, it will not been shown
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 *
 */
public class DominationView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 * @param args set user as arg
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DominationView frame = new DominationView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * This default constructor is only for test
	 */
	public DominationView() {
		setTitle("Domination View");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 762, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textArea.setEditable(false);
		textArea.setBounds(14, 13, 716, 262);
		contentPane.add(textArea);
		
		textArea.append(String.format("%-20s%-18s%-30s%-10s","Player","Percentage","Continent control","Armies")+"\n");
		textArea.append(String.format("%-20s%-18s%-30s%-10s","yechao","20%","AF,SE","56")+"\n");
		textArea.append(String.format("%-20s%-18s%-40s%-10s","yechao","20%","AF,SE","56")+"\n");
		textArea.append(String.format("%-20s%-18s%-40s%-10s","yechao","20%","AF,SE","56")+"\n");
		textArea.append(String.format("%-20s%-18s%-40s%-10s","yechao","20%","AF,SE","56")+"\n");
		textArea.append(String.format("%-20s%-18s%-40s%-10s","yechao","20%","AF,SE","56")+"\n");
		textArea.append(String.format("%-20s%-18s%-40s%-10s","yechao","20%","AF,SE","56")+"\n");
		textArea.append(String.format("%-20s%-18s%-40s%-10s","yechao","20%","AF,SE","56")+"\n");
		textArea.append(String.format("%-20s%-18s%-40s%-10s","yechao","20%","AF,SE","56")+"\n");
		textArea.append(String.format("%-20s%-18s%-40s%-10s","yechao","20%","AF,SE","56")+"\n");
		textArea.append(String.format("%-20s%-18s%-40s%-10s","yechao","20%","AF,SE","56")+"\n");
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnOk.setBounds(310, 298, 113, 27);
		contentPane.add(btnOk);
	}
	
	/**
	 * Create the frame.
	 * @param control object of controller
	 */
	public DominationView(Controller control) {
		setTitle("Domination View");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 762, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
		textArea.setEditable(false);
		textArea.setBounds(14, 13, 716, 262);
		contentPane.add(textArea);
		
		textArea.append(String.format("%-20s%-18s%-30s%-10s","Player","Percentage","Continent control","Armies")+"\n");
		for(Player p:control.getGame().getPlayers()) {
			String name = p.getID();
			String percentage = String.valueOf(p.getTotalCountriesNumber()*100/control.getGame().getCountries().size())+"%";
			String continent_control = "";
			for(int i = 0;i<p.getOwnContinent().size();i++) {
				if(i==0) {
					continent_control+=p.getOwnContinent().get(i).getName();
				}
				else {
					continent_control+=","+p.getOwnContinent().get(i).getName();
				}
			}
			int armies = 0;
			for(Country c:control.getGame().getCountries()) {
				if(c.getOwner().getID().equals(p.getID())) {
					armies+=c.getArmyNum();
				}
			}
			if(continent_control.length()>30) {
				String tmp = continent_control.substring(27);
				continent_control = continent_control.substring(0,27)+"-";
				textArea.append(String.format("%-20s%-18s%-30s%-10s",name,percentage,continent_control,String.valueOf(armies))+"\n");
				while(tmp.length()>30) {
					String t = tmp.substring(0,27)+"-";
					textArea.append(String.format("%-20s%-18s%-30s%-10s","","",t,"")+"\n");
					tmp = tmp.substring(27);
				}
				textArea.append(String.format("%-20s%-18s%-30s%-10s","","",tmp,"")+"\n");
			}
			else {
				textArea.append(String.format("%-20s%-18s%-30s%-10s",name,percentage,continent_control,String.valueOf(armies))+"\n");
			}
		}
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(310, 298, 113, 27);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(btnOk);
	}
}

