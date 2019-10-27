package ui;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controller.Controller;
import entities.*;
import ui.labels.*;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.Font;

public class MapUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private String isCommandPattern = "(placearmy \\w*(\\-\\w+)*|placeall|"
			+ "reinforce \\w*(\\-\\w+)* [1-9][0-9]*|"
			+ "fortify (\\w*(\\-\\w+)* \\w*(\\-\\w+)* [1-9][0-9]*|\\-none))|"
			+ "cheat|trade|"
			+ "attack \\w*(\\-\\w+)* \\w*(\\-\\w+)* ([1-3]|\\-allout)|"
			+ "\\-noattack|defend [1-2]|attackmove [1-9][0-9]*";
	private Controller control;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapUI frame = new MapUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Used for showing map
	 */
	class mapPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		private Vector <String> load_files;
		private 
		mapPanel(Vector<String> filesLoad){
			this.load_files = filesLoad;
		}
		/**
		 * Default painting the JPanel (map)
		 */
		@Override
		public void paintComponent(Graphics g) {
			Vector<Country> countries_list = control.getCountries();
			Graphics2D g2=(Graphics2D)g;
			if(load_files.size()>=1) {
				String tmp[] = load_files.get(0).split(" ");
				File file = new File("image\\"+tmp[1]);
				if(file.exists()) {
					Image i = new ImageIcon("Image\\"+tmp[1]).getImage();
					g.drawImage(i,0,0,940,585,this);
				}
			}
			Stroke stroke=new BasicStroke(3.0f);
			g2.setStroke(stroke);
			for(Country c:countries_list) {
				int[] from = c.getXY();
				for(Country linkc:c.getLinkCountries()) {
					int[] to = linkc.getXY();
					if(Math.abs(from[0]-to[0])>600) {
						int[] tmpXL;
						int[] tmpXR;
						if(from[0]>=to[0]) {
							tmpXL = to;
							tmpXR = from;
						}
						else {
							tmpXL = from;
							tmpXR = to;
						}
						if(tmpXL[1]>=tmpXR[1]) {
							g2.drawLine(tmpXL[0], tmpXL[1], 7, tmpXL[1]-(Math.abs(tmpXL[1]-tmpXR[1])*tmpXL[0]/(tmpXL[0]+940-tmpXR[0])));
							g2.drawLine(tmpXR[0], tmpXR[1], 933, tmpXL[1]-(Math.abs(tmpXL[1]-tmpXR[1])*tmpXL[0]/(tmpXL[0]+940-tmpXR[0])));
						}
						else{
							g2.drawLine(tmpXL[0], tmpXL[1], 7, tmpXL[1]+(Math.abs(tmpXL[1]-tmpXR[1])*tmpXL[0]/(tmpXL[0]+940-tmpXR[0])));
							g2.drawLine(tmpXR[0], tmpXR[1], 933, tmpXL[1]+(Math.abs(tmpXL[1]-tmpXR[1])*tmpXL[0]/(tmpXL[0]+940-tmpXR[0])));
						}
					}
					else {
						g2.drawLine(from[0],from[1],to[0],to[1]);
					}
				}
			}
		}
	}
	
	/**
	 * Add every countries as a label on the map
	 * @param map a JPanel
	 * @param countriesList containing all the countries
	 */
	private void visualizeCountryLabels(mapPanel map,Controller control) {
		for(Country c: control.getCountries()) {
			map.add(c.getLabel());
		}
	}
	
	/**
	 * Create the frame.
	 */
	public MapUI() {
		setTitle("Risk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1262, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel map = new JPanel();
		map.setBounds(20, 50, 940, 585);
		map.setLayout(null);
		
		contentPane.add(map);
		
		OutcomeObsLabel outcome_label = new OutcomeObsLabel();
		outcome_label.setEditable(false);
		outcome_label.setBounds(974, 50, 256, 585);
		contentPane.add(outcome_label);
		
		textField = new JTextField();
		textField.setBounds(20, 665, 1081, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		InfoObsLabel info_label = new InfoObsLabel ("Phase");
		info_label.setHorizontalAlignment(SwingConstants.LEFT);
		info_label.setBounds(100, 700, 800, 35);
		contentPane.add(info_label);
		
		PlayerTurnObsLabel turn_label = new PlayerTurnObsLabel();
		turn_label.setBounds(30, 705, 40, 20);
		contentPane.add(turn_label);
		
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					run();
				}
			}
		});
		
		JButton runBtn = new JButton("Run");

		runBtn.setBounds(1115, 665, 115, 27);
		contentPane.add(runBtn);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1244, 35);
		contentPane.add(menuBar);
		
		JMenu Menu = new JMenu("Menu");
		Menu.setHorizontalAlignment(SwingConstants.CENTER);
		Menu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		menuBar.add(Menu);
		
		JMenuItem mntmSaveGame = new JMenuItem("save game");
		mntmSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SavePrompt s = new SavePrompt(control);
				s.setVisible(true);
			}
		});
		mntmSaveGame.setHorizontalAlignment(SwingConstants.LEFT);
		mntmSaveGame.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		mntmSaveGame.setPreferredSize(new Dimension(150,35));
		Menu.add(mntmSaveGame);
		
		JMenuItem mntmExit = new JMenuItem("exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mntmExit.setHorizontalAlignment(SwingConstants.LEFT);
		mntmExit.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		mntmExit.setPreferredSize(new Dimension(150,35));	
		Menu.add(mntmExit);
	}
	
	/**
	 * Relays user input to the controller to process and act on the game play
	 * Error Dialog will pop up if the user command is invalid
	 */
	public void run() {
		if(Pattern.matches(isCommandPattern, textField.getText())) {
			String [] result = control.processInput(textField.getText());
			if(result.length>1) {
				if(result[0].equals("F")) {
					JOptionPane.showMessageDialog(null, result[1], "Warning", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, result[1], "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Command invalid!", "Warning", JOptionPane.ERROR_MESSAGE);
		}
		textField.setText("");
	}
	
	public MapUI(Controller control) {
		this.control = control;
		MapUI m = this;
		control.getGame().setJFrame(this);
		setTitle("Risk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1262, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		mapPanel map = new mapPanel(control.getFilesLoad());
		map.setBounds(20, 50, 940, 585);
		map.setLayout(null);
		
		contentPane.add(map);
		
		textField = new JTextField();
		textField.setBounds(20, 665, 1081, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		InfoObsLabel info_label = new InfoObsLabel ("Phase");
		info_label.setHorizontalAlignment(SwingConstants.LEFT);
		info_label.setBounds(100, 700, 800, 35);
		contentPane.add(info_label);
		
		OutcomeObsLabel outcome_label = new OutcomeObsLabel();
		outcome_label.setBounds(974, 50, 256, 585);
		outcome_label.setEditable(false);
		outcome_label.setLineWrap(true);
		contentPane.add(outcome_label);
		
		PlayerTurnObsLabel turn_label = new PlayerTurnObsLabel();
		turn_label.setBounds(30, 705, 40, 20);
		contentPane.add(turn_label);
		
		control.attachObserver(info_label);
		control.attachObserver(outcome_label);
		control.attachObserver(turn_label);
		
		visualizeCountryLabels(map,control);
		
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
		runBtn.setBounds(1115, 665, 115, 27);
		contentPane.add(runBtn);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1244, 35);
		contentPane.add(menuBar);
		
		JMenu Menu = new JMenu("Menu");
		Menu.setHorizontalAlignment(SwingConstants.CENTER);
		Menu.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		menuBar.add(Menu);
		
		JMenuItem mntmSaveGame = new JMenuItem("save game");
		mntmSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!control.getGame().getPhase().equals("Attack Phase 2")&&!control.getGame().getPhase().equals("Attack Phase 3")) {
					SavePrompt s = new SavePrompt(control);
					s.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "You cannot save in the middle of the attack!", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mntmSaveGame.setHorizontalAlignment(SwingConstants.LEFT);
		mntmSaveGame.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		mntmSaveGame.setPreferredSize(new Dimension(150,35));
		Menu.add(mntmSaveGame);
		
		JMenuItem mntmLoadGame = new JMenuItem("load game");
		mntmLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadPrompt lp = new LoadPrompt(m);
				lp.setVisible(true);
			}
		});
		mntmLoadGame.setHorizontalAlignment(SwingConstants.LEFT);
		mntmLoadGame.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		mntmLoadGame.setPreferredSize(new Dimension(150,35));
		Menu.add(mntmLoadGame);
		
		JMenuItem mntmExit = new JMenuItem("exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mntmExit.setHorizontalAlignment(SwingConstants.LEFT);
		mntmExit.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		mntmExit.setPreferredSize(new Dimension(150,35));	
		Menu.add(mntmExit);
		if(!control.loadedGame()) {
			control.startGame();
		}else {
			this.setVisible(true);
			control.refresh();
		}
	}
}
