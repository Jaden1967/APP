package ui;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import entities.Continent;
import entities.Country;
import entities.Player;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.util.regex.*;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Creating the JFrame
 */

public class Initial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Vector<Continent> continentsList = new Vector<Continent>();
	private Vector<Country> countriesList = new Vector<Country>();
	private Vector<Player> playerList = new Vector<Player>();
	private Vector <String> filesLoad = new Vector <String>();
	private int x,y;
	private Vector<String> colorList = new Vector<String>();
	private String isCommandPattern = "(gameplayer -(add|remove) \\w*|loadmap \\w*\\.map|populatecountries)";
	
	/**
	 * Used as the controller, containing all the possible commands and responding based on the command
	 * @param picture_label
	 * @param input_text
	 * @param player_text
	 * @param output_text
	 */
	private void run(JLabel picture_label, JTextField input_text, JTextArea player_text, JTextArea output_text) {
		if(Pattern.matches(isCommandPattern, input_text.getText())) {
			if(input_text.getText().equals("populatecountries")) {
				if(countriesList.size()==0||continentsList.size()==0||playerList.size()<=1||playerList.size()>countriesList.size()) {
					JOptionPane.showMessageDialog(null, "Please check the arguments!\nCountry numbers: "+countriesList.size()+"\nContinent numbers: "+continentsList.size()+"\nPlayer numbers: "+playerList.size(), "Warning", JOptionPane.ERROR_MESSAGE);
					output_text.append("Fail to start game!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Game start!", "Good luck!", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					MapUI g = new MapUI(continentsList, countriesList, playerList, filesLoad, x, y);
					g.setVisible(true);
				}
			}
			else {
				if(input_text.getText().substring(0, input_text.getText().indexOf(" ")).equals("gameplayer")) {
					input_text.setText(input_text.getText().substring(input_text.getText().indexOf(" ")+1));
				}
				String type = input_text.getText().substring(0, input_text.getText().indexOf(" "));
				String name = input_text.getText().substring(input_text.getText().indexOf(" ")+1);
				int flag = 0;
				if(type.equals("-add")) {
					if(playerList.size()>=8) {
						JOptionPane.showMessageDialog(null, "Too many players!", "Warning", JOptionPane.ERROR_MESSAGE);
						output_text.append("Fail to add player "+name+"\n");
					}
					else {
						for(int i = 0;i<playerList.size();i++) {
							if(name.equals(playerList.get(i).getID())) {
								JOptionPane.showMessageDialog(null, "Same player name!", "Warning", JOptionPane.ERROR_MESSAGE);
								output_text.append("Fail to add player "+name+"\n");
								flag = 1;
								break;
							}
						}
						if(flag==0) {
							String c = getRandColor(colorList);
							Player p = new Player(name, c);
							playerList.add(p);
							input_text.setText("");
							output_text.append("Successfully add player "+name+"\n");
						}
					}
				}
				else if(type.equals("-remove")) {
					if(playerList.size()==0) {
						JOptionPane.showMessageDialog(null, "Invalid removal!", "Warning", JOptionPane.ERROR_MESSAGE);
						output_text.append("Fail to remove player "+name+"\n");
					}
					else {
						for(int i = 0;i<playerList.size();i++) {
							if(name.equals(playerList.get(i).getID())) {
								colorList.add(playerList.get(i).getColorStr());
								playerList.remove(playerList.get(i));
								input_text.setText("");
								output_text.append("Successfully remove player "+name+"\n");
								break;
							}
							if(i==playerList.size()-1) {
								JOptionPane.showMessageDialog(null, "No such username!", "Warning", JOptionPane.ERROR_MESSAGE);
								output_text.append("Fail to remove player "+name+"\n");
							}

						}
					}
					
				}
				else if(type.equals("loadmap")) {
					countriesList.clear();
					continentsList.clear();
					if(readFile(name)) {
						output_text.append("Loading map "+name+" success!\n");
						input_text.setText("");
						if(filesLoad.size()>=3) {
							String tmp[] = filesLoad.get(3).split(" ");
							File file = new File("image\\"+tmp[1]);
							if(file.exists()) {
								picture_label.setText("");
								ImageIcon icon = new ImageIcon("Image\\"+tmp[1]);
								picture_label.setIcon(icon);
							}
						}
					}
					else {
						output_text.append("Loading map "+name+" fail!\n");
					}
				}
				player_text.setText("");
				for(Player p:playerList) {
					player_text.append("Name: "+p.getID()+"    Color: "+p.getColorStr()+"\n");
				}

			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Command invalid!", "Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Initial list of colors (string)
	 */
	private void initialList() {
		colorList.add("pink");
		colorList.add("cyan");
		colorList.add("DeepPink");
		colorList.add("skyblue");
		colorList.add("lightyellow");
		colorList.add("grey");
		colorList.add("white");
		colorList.add("purple");
	}
	/**
	 * Returns corresponding Color object to the parsed String
	 * @param color
	 * @return default color white
	 */
	private Color getColor(String color) {
        switch (color) {
            case "red": return Color.red;
            case "yellow": return Color.yellow;
            case "blue": return Color.blue;
            case "green": return Color.green;
            case "lightyellow": return new Color(107,142,35);
            case "grey": return Color.gray;
            case "magenta": return Color.magenta;
            case "orange": return Color.orange;
            case "pink": return Color.pink;
            case "cyan": return Color.cyan;
            case "DeepPink": return new Color(255,20,147);
            case "skyblue": return new Color(176, 196, 222);
            case "white": return Color.white;
            case "purple": return new Color(128, 0, 128);
            default: return Color.white;
        }
    }
	
	/**
	 * Reads map data file
	 * Parses lines in file into relevant data to be used in game
	 * Generate Game Object entities and their respective connections to each other
	 * @param address address of map file
	 * @return Validation of map data file
	 */
	public boolean readFile(String address) {
		try {
			String pathname = "map\\"+address;
			File filename = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			do {
				line = br.readLine();
				if(line.contains("Dimensions")) {
					String[] tmp = line.split("\\s+");
					x = Integer.parseInt(tmp[2]);
					y = Integer.parseInt(tmp[4]);
				}
			}while(!line.trim().equals("[files]"));
			line = br.readLine();
			while(!line.trim().isEmpty()) {
				filesLoad.add(line);
				line = br.readLine();
			}
			do {
				line = br.readLine();
			}while(!line.trim().equals("[continents]"));
			line = br.readLine();
			int counter = 0;
			while(!line.trim().isEmpty()) {
				counter++;
				String[] tmp = line.split("\\s+");
				Continent c = new Continent(counter, tmp[0], Integer.parseInt(tmp[1]), getColor(tmp[2]));
				continentsList.add(c);
				line = br.readLine();
			}
			do {
				line = br.readLine();
			}while(!line.trim().equals("[countries]"));
			line = br.readLine();
			while(!line.trim().isEmpty()) {
				int flag = 0;
				String[] tmp = line.split("\\s+");
				Country c = new Country(Integer.parseInt(tmp[0]), tmp[1], Integer.parseInt(tmp[3]), Integer.parseInt(tmp[4]), x, y, 940, 585);
				for(Continent con:continentsList) {
					if(con.getID()==Integer.parseInt(tmp[2])) {
						con.addToCountriesList(c);
						c.setContinent(con);
						flag = 1;
						break;
					}
				}
				if(flag == 0) {
					return false;
				}
				countriesList.add(c);
				line = br.readLine();
			}
			do {
				line = br.readLine();
			}while(!line.trim().equals("[borders]"));
			line = br.readLine();
			while(line!=null) {
				String[] tmp = line.split("\\s+");
				int flag = 0;
				for(Country c:countriesList) {
					if(c.getID()==Integer.parseInt(tmp[0])) {
						flag = 1;
						for(int i = 1;i<tmp.length;i++) {
							Country tc = new Country();
							for(int j = 0;j<countriesList.size();j++) {
								if(countriesList.get(j).getID()==Integer.parseInt(tmp[i])) {
									tc = countriesList.get(j);
									break;
								}
							}
							if(tc.getLabel().equals("")) {
								return false;
							}
							c.addNeighbour(tc);
						}
					}
				}
				if(flag==0) {
					return false;
				}
				line = br.readLine();
			}
			return true;
		}
		 catch (Exception e) {
				e.printStackTrace();
				return false;
		}
	}
	
	/**
	 * Get random color from String List colorList
	 * @param colorList
	 * @return Color as String
	 */
	private String getRandColor(Vector<String> colorList) {
        int c = new Random().nextInt(colorList.size());
        String color = colorList.get(c);
        colorList.remove(c);
        return color;
    }
	

	/**
	 * Create the frame for Startup Phase
	 */
	public Initial() {
		initialList();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setTitle("Setting");
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel picture_label = new JLabel("no picture at this time");
		picture_label.setOpaque(true);
		picture_label.setBackground(Color.PINK);
		picture_label.setHorizontalAlignment(SwingConstants.CENTER);
		picture_label.setBounds(14, 13, 370, 254);
		getContentPane().add(picture_label);
		
		JTextArea player_text = new JTextArea();
		player_text.setForeground(Color.BLACK);
		player_text.setFont(new Font("Monospaced", Font.PLAIN, 15));
		player_text.setEditable(false);
		player_text.setBounds(412, 13, 356, 254);
		getContentPane().add(player_text);
		
		JTextField input_text = new JTextField();
		input_text.setBounds(14, 303, 627, 24);
		contentPane.add(input_text);
		input_text.setColumns(10);
		
		
		
		JTextArea output_text = new JTextArea();
		output_text.setEditable(false);
		output_text.setBounds(14, 380, 754, 160);
		output_text.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(output_text);
		scroll.setBounds(14,380,754,160);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scroll);
		
		JLabel lblOutputLog = new JLabel("Output Log");
		lblOutputLog.setBounds(14, 349, 92, 18);
		contentPane.add(lblOutputLog);
		
		JButton btnRun = new JButton("Run");
		
		input_text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					run(picture_label, input_text, player_text, output_text);
				}
			}
		});
		
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				run(picture_label, input_text, player_text, output_text);
			}
		});
		btnRun.setBounds(655, 302, 113, 27);
		contentPane.add(btnRun);
		
	}
}
