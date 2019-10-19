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

import controller.Controller;
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
	private Vector<Country> countries_list = null;
	private Controller control;
	
	/**
	 * Used for showing map
	 */
	class mapPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private Vector <String> load_files;
		
		mapPanel(Vector<String> filesLoad){
			this.load_files = filesLoad;
		}
		/**
		 * Default painting the JPanel (map)
		 */
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2=(Graphics2D)g;
			if(load_files.size()>=1) {
				String tmp[] = load_files.get(0).split(" ");
				File file = new File("image\\"+tmp[1]);
				if(file.exists()) {
					Image i = new ImageIcon("Image\\"+tmp[1]).getImage();
					g.drawImage(i,0,0,940,585,this);
				}
			}
			Stroke stroke=new BasicStroke(3.0f);//…Ë÷√œﬂøÌŒ™3.0
			g2.setStroke(stroke);
			for(Country c:countries_list) {
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
	 * Relays user input to the controller to process and act on the game play
	 * Error Dialog will pop up if the user command is invalid
	 */
	public void run() {
		if(Pattern.matches(isCommandPattern, textField.getText())) {
			String [] result = control.processInput(textField.getText());
			if(result[0].equals("F")) {
				JOptionPane.showMessageDialog(null, result[1], "Warning", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else {
			JOptionPane.showMessageDialog(null, "Command invalid!", "Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public MapUI(Vector<Continent> continentsList, Vector<Country> countriesList, Vector<Player> playerList, Vector <String> filesLoad, int x, int y, Controller control) {
		this.countries_list = countriesList;
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
		this.control = control;
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					run();
				}
			}
		});
		
		JButton runBtn = new JButton("Run");
		runBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				run();
			}
		});
		runBtn.setBounds(847, 638, 115, 27);
		contentPane.add(runBtn);
	}
}
