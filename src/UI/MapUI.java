package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Entities.*;
import UI.labels.CountryObsLabel;
import UI.labels.InfoObsLabel;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MapUI extends JFrame {
	
	private JPanel contentPane;
	private JTextField textField;
	private String isCommandPattern = "(placearmy \\w*|placeall|reinforce \\w* [1-9][0-9]*|fortify (\\w*\\ \\w*\\ [1-9][0-9]*|none))";
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	class mapPanel extends JPanel{
		private Vector <String> filesLoad;
		
		mapPanel(Vector<String> filesLoad){
			this.filesLoad = filesLoad;
		}
		@Override
		public void paintComponent(Graphics g) {
			String tmp[] = filesLoad.get(0).split(" ");
			File file = new File("image\\"+tmp[1]);
			if(file.exists()) {
				Image i = new ImageIcon("Image\\"+tmp[1]).getImage();
				g.drawImage(i,0,0,940,585,this);
			}
		}
	}

	private mapPanel visualizeAndPair(mapPanel map,Vector<Country> countriesList) {
		for(Country c:countriesList) {
			CountryObsLabel oL = c.getLabel();
			c.addObserver(oL);
			map.add(oL);
		}
		return map;
	}
	
	/**
	 * Create the frame.
	 */
	public MapUI() {
		setTitle("Risk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 760);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		mapPanel map = new mapPanel(null);
		map.setBounds(20, 20, 940, 585);
		map.setLayout(null);
		
		contentPane.add(map);
		
		textField = new JTextField();
		textField.setBounds(20, 639, 804, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		InfoObsLabel infoLabel = new InfoObsLabel ("Phase");
		infoLabel.setBounds(24, 670, 800, 35);
		contentPane.add(infoLabel);
		
		JButton runBtn = new JButton("Run");
		runBtn.setBounds(847, 638, 115, 27);
		contentPane.add(runBtn);
	}

	
	public MapUI(Vector<Continent> continentsList, Vector<Country> countriesList, Vector<Player> playerList, Vector <String> filesLoad, int x, int y) {
		setTitle("Risk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 760);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		mapPanel map = new mapPanel(filesLoad);
		map.setBounds(20, 20, 940, 585);
		map.setLayout(null);
		
		contentPane.add(map);
		
		textField = new JTextField();
		textField.setBounds(20, 639, 804, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		InfoObsLabel infoLabel = new InfoObsLabel ("Phase");
		infoLabel.setBounds(24, 670, 800, 35);
		contentPane.add(infoLabel);
		
		map = visualizeAndPair(map,countriesList);
		
		GamePlay game = new GamePlay(continentsList, countriesList, playerList,infoLabel);
		
		JButton runBtn = new JButton("Run");
		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Pattern.matches(isCommandPattern, textField.getText())) {
					if(textField.getText().equals("placeall")) {
						//place all armies randomly for current player
						game.randomAssignArmy();
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Command invalid!", "Warning", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		runBtn.setBounds(847, 638, 115, 27);
		contentPane.add(runBtn);
	}
}
