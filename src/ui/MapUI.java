package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.*;
import ui.labels.CountryObsLabel;
import ui.labels.InfoObsLabel;
import ui.labels.OutcomeObsLabel;
import ui.labels.PlayerTurnObsLabel;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;

import javax.swing.SwingConstants;

public class MapUI extends JFrame {
	
	private JPanel contentPane;
	private JTextField textField;
	private String isCommandPattern = "(placearmy \\w*(\\-\\w+)*|placeall|reinforce \\w*(\\-\\w+)* [1-9][0-9]*|fortify (\\w*(\\-\\w+)*\\ \\w*(\\-\\w+)*\\ [1-9][0-9]*|none))";
	private Vector<Country> countriesList = null;
	
	/**
	 * Used for showing map
	 */
	class mapPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private Vector <String> filesLoad;
		
		mapPanel(Vector<String> filesLoad){
			this.filesLoad = filesLoad;
		}
		/**
		 * Default painting the JPanel (map)
		 */
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2=(Graphics2D)g;
			String tmp[] = filesLoad.get(0).split(" ");
			File file = new File("image\\"+tmp[1]);
			if(file.exists()) {
				Image i = new ImageIcon("Image\\"+tmp[1]).getImage();
				g.drawImage(i,0,0,940,585,this);
			}
			Stroke stroke=new BasicStroke(3.0f);//…Ë÷√œﬂøÌŒ™3.0
			g2.setStroke(stroke);
			for(Country c:countriesList) {
				int[] from = c.getXY();
				for(Country linkc:c.getLinkCountries()) {
					int[] to = linkc.getXY();
					g2.drawLine(from[0],from[1],to[0],to[1]);
				}
			}
		}
	}
	
	/**
	 * Add every countries as a label on the map
	 * @param map a JPanel
	 * @param countriesList containing all the countries
	 * @return update the map
	 */
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
		setBounds(100, 100, 1000, 800);
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
		infoLabel.setText("Phase 1 army 1");
		infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		infoLabel.setBounds(100, 670, 800, 35);
		contentPane.add(infoLabel);
		
		OutcomeObsLabel outcomeLabel = new OutcomeObsLabel();
		outcomeLabel.setText("Hi");
		outcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		outcomeLabel.setBounds(100,700,500, 35);
		contentPane.add(outcomeLabel);
		
		PlayerTurnObsLabel turnLabel = new PlayerTurnObsLabel();
		turnLabel.setBounds(30, 675, 40, 20);
		contentPane.add(turnLabel);
		
		JButton runBtn = new JButton("Run");
		runBtn.setBounds(847, 638, 115, 27);
		contentPane.add(runBtn);
	}
	
	/**
	 * Used as the controller, containing all the possible commands and responding based on the command
	 * @param game game model
	 * @param countriesList vector of all the countries
	 */
	public void run(GamePlay game,Vector<Country> countriesList) {
		if(Pattern.matches(isCommandPattern, textField.getText())) {
			String [] splitted = textField.getText().split("\\s+");
			if(splitted[0].equals("placeall")) {
                if(game.inStartUpPhase()) {
                    //place all armies randomly for current player
                    game.randomAssignArmy();
                }else {
                    JOptionPane.showMessageDialog(null, "Not in correct phase!", "Warning", JOptionPane.ERROR_MESSAGE); 
                }
            }
			else if(splitted[0].equals("placearmy")){
				if(game.inStartUpPhase()) {
					String countryId = splitted [1];
					boolean c_exists = false;
					Country temp = new Country();
					for (Country c: countriesList) {
						if (c.getName().equals(countryId)) {
							c_exists = true;
						temp = c;
						break;
						}
					}
					if(c_exists) {
						if(temp.getOwner().getID().equals(game.getPlayerID())) {
							game.placeArmy(temp);
						}
						else {
							JOptionPane.showMessageDialog(null, "Country not owned by Player!", "Warning", JOptionPane.ERROR_MESSAGE);
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "Country Does not Exist!", "Warning", JOptionPane.ERROR_MESSAGE);
					}
				}
				else{
                    JOptionPane.showMessageDialog(null, "Not in correct phase!", "Warning", JOptionPane.ERROR_MESSAGE);
                }
			}
			else if(splitted[0].equals("reinforce")) {
				if(game.getPhase().equals("Reinforcement Phase")) {
				
					String countryId = splitted[1];
					int num = Integer.parseInt(splitted[2]);
					boolean c_exists = false;
					Country temp = new Country();
					for (Country c: countriesList) {
						if (c.getName().equals(countryId)) {
							c_exists = true;
							temp = c;
							break;
						}
					}
					if(c_exists) {
						if(temp.getOwner().getID().equals(game.getPlayerID())) {
							if(num<= game.getPlayer().getArmyToPlace()) {
								game.assignArmy(countryId, num);
							}
							else {
								JOptionPane.showMessageDialog(null, "Number exceeds assigning limit!", "Warning", JOptionPane.ERROR_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Country not owned by Player!", "Warning", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Country Does not Exist!", "Warning", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
                    JOptionPane.showMessageDialog(null, "Not in correct phase!", "Warning", JOptionPane.ERROR_MESSAGE);						}
			}
			else if(splitted[0].equals("fortify")) {
				if (game.getPhase().equals("Fortification Phase")) {
					if(splitted[1].equals("none")){
						game.nextPlayer();
					}
					else {
						String fromCountry = splitted[1];
						String toCountry = splitted[2];
						int number = Integer.parseInt(splitted[3]);
						boolean f_exists = false;
						boolean t_exists = false;
						Country tempFrom = new Country();
						Country tempTo = new Country();
						for (Country c: countriesList) {
							if (c.getName().equals(fromCountry)) {
								f_exists = true;
								tempFrom = c;
								break;
							}
						}
						for (Country c: countriesList) {
							if (c.getName().equals(toCountry)) {
								t_exists = true;
								tempTo = c;
								break;
							}
						}
						if(f_exists && t_exists) {
							if(tempFrom.getOwner().getID().equals(tempTo.getOwner().getID())) {
								HashSet<String> visited = new HashSet<>();
								if(tempFrom.hasPathTo(toCountry,tempFrom.getOwner().getID(),visited)) {
									if(tempFrom.getArmyNum() > number) {
										game.fortify(tempFrom,tempTo,number);
									}
									else {
										JOptionPane.showMessageDialog(null, "Fortifying army exceeds limit!", "Warning", JOptionPane.ERROR_MESSAGE);
									}
								}
								else {
									JOptionPane.showMessageDialog(null, "No linked path between countries "+fromCountry+" and "+toCountry+"!", "Warning", JOptionPane.ERROR_MESSAGE);
								}
							}
							else {
								JOptionPane.showMessageDialog(null, "Countries not owned by the same player!", "Warning", JOptionPane.ERROR_MESSAGE);
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "Country(ies)"+ (f_exists?"":tempFrom)+
									(t_exists?"":toCountry)+" do(es)'nt exist!", "Warning", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				else {
						JOptionPane.showMessageDialog(null, "Not in Correct Phase!", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Command invalid!", "Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public MapUI(Vector<Continent> continentsList, Vector<Country> countriesList, Vector<Player> playerList, Vector <String> filesLoad, int x, int y) {
		this.countriesList = countriesList;
		setTitle("Risk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
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
		infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		infoLabel.setBounds(100, 670, 800, 35);
		contentPane.add(infoLabel);
		
		OutcomeObsLabel outcomeLabel = new OutcomeObsLabel();
		outcomeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		outcomeLabel.setBounds(100,700,500, 35);
		contentPane.add(outcomeLabel);
		
		PlayerTurnObsLabel turnLabel = new PlayerTurnObsLabel();
		turnLabel.setBounds(30, 675, 40, 20);
		contentPane.add(turnLabel);
		
		map = visualizeAndPair(map,countriesList);
		
		GamePlay game = new GamePlay(continentsList, countriesList, playerList,infoLabel,outcomeLabel,turnLabel);
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					run(game,countriesList);
				}
			}
		});
		
		JButton runBtn = new JButton("Run");
		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				run(game,countriesList);
			}
		});
		runBtn.setBounds(847, 638, 115, 27);
		contentPane.add(runBtn);
	}
}
