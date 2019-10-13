package UI;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import Entities.Continent;
import Entities.Country;
import Entities.Player;

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

public class Initial extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Vector<Continent> continentsList = new Vector<Continent>();
	private Vector<Country> countriesList = new Vector<Country>();
	private Vector<Player> playerList = new Vector<Player>();
	private Vector <String> filesLoad = new Vector <String>();
	private int x,y;
	private Vector<String> colorList = new Vector<String>();
	private String isCommandPattern = "(gameplayer -(add|remove) \\w*|loadmap \\w*\\.txt|populatecountries)";
	
	private void initialList() {
		colorList.add("red");
		colorList.add("yellow");
		colorList.add("blue");
		colorList.add("green");
		colorList.add("black");
		colorList.add("gray");
		colorList.add("magenta");
		colorList.add("orange");
	}
	
	private Color getColor(String color) {
        switch (color) {
            case "red": return Color.red;
            case "yellow": return Color.yellow;
            case "blue": return Color.blue;
            case "green": return Color.green;
            case "black": return Color.black;
            case "grey": return Color.gray;
            case "magenta": return Color.magenta;
            case "orange": return Color.orange;
            default: return Color.white;
        }
    }
	
	private boolean readFile(String address) {
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
				String[] tmp = line.split("\\s+");
				Country c = new Country(Integer.parseInt(tmp[0]), tmp[1], Integer.parseInt(tmp[3]), Integer.parseInt(tmp[4]), x, y, 940, 585);
				for(Continent con:continentsList) {
					if(con.getID()==Integer.parseInt(tmp[2])) {
						con.addToCountriesList(c);
						c.setContinent(con);
						break;
					}
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
				for(Country c:countriesList) {
					if(c.getID()==Integer.parseInt(tmp[0])) {
						for(int i = 1;i<tmp.length;i++) {
							Country tc = new Country();
							for(int j = 0;j<countriesList.size();j++) {
								if(countriesList.get(j).getID()==Integer.parseInt(tmp[i])) {
									tc = countriesList.get(j);
									break;
								}
							}
							c.addNeighbour(tc);
						}
					}
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
	
	private String getRandColor(Vector<String> colorList) {
        int c = new Random().nextInt(colorList.size());
        String color = colorList.get(c);
        colorList.remove(c);
        return color;
    }
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Initial frame = new Initial();
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
	public Initial() {
		initialList();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setTitle("Setting");
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("no picture at this time");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(Color.PINK);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(14, 13, 370, 254);
		getContentPane().add(lblNewLabel);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
		textArea.setEditable(false);
		textArea.setBounds(412, 13, 356, 254);
		getContentPane().add(textArea);
		
		JTextField textField = new JTextField();
		textField.setBounds(14, 303, 627, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(14, 380, 754, 160);
		textArea_1.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(textArea_1);
		scroll.setBounds(14,380,754,160);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scroll);
		
		JLabel lblOutputLog = new JLabel("Output Log");
		lblOutputLog.setBounds(14, 349, 92, 18);
		contentPane.add(lblOutputLog);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Pattern.matches(isCommandPattern, textField.getText())) {
					if(textField.getText().equals("populatecountries")) {
						if(countriesList.size()==0||continentsList.size()==0||playerList.size()<=1||playerList.size()>countriesList.size()) {
							JOptionPane.showMessageDialog(null, "Please check the arguments!\nCountry numbers: "+countriesList.size()+"\nContinent numbers: "+continentsList.size()+"\nPlayer numbers: "+playerList.size(), "Warning", JOptionPane.ERROR_MESSAGE);
							textArea_1.append("Fail to start game!");
						}
						else {
							JOptionPane.showMessageDialog(null, "Game start!", "Good luck!", JOptionPane.ERROR_MESSAGE);
							setVisible(false);
							MapUI g = new MapUI(continentsList, countriesList, playerList, filesLoad, x, y);

							g.setVisible(true);
						}
					}
					else {
						if(textField.getText().substring(0, textField.getText().indexOf(" ")).equals("gameplayer")) {
							textField.setText(textField.getText().substring(textField.getText().indexOf(" ")+1));
						}
						String type = textField.getText().substring(0, textField.getText().indexOf(" "));
						String name = textField.getText().substring(textField.getText().indexOf(" ")+1);
						int flag = 0;
						if(type.equals("-add")) {
							if(playerList.size()>=8) {
								JOptionPane.showMessageDialog(null, "Too many players!", "Warning", JOptionPane.ERROR_MESSAGE);
								textArea_1.append("Fail to add player "+name+"\n");
							}
							else {
								for(int i = 0;i<playerList.size();i++) {
									if(name.equals(playerList.get(i).getID())) {
										JOptionPane.showMessageDialog(null, "Same player name!", "Warning", JOptionPane.ERROR_MESSAGE);
										textArea_1.append("Fail to add player "+name+"\n");
										flag = 1;
										break;
									}
								}
								if(flag==0) {
									String c = getRandColor(colorList);
									Player p = new Player(name, c);
									playerList.add(p);
									textField.setText("");
									textArea_1.append("Successfully add player "+name+"\n");
								}
							}
						}
						else if(type.equals("-remove")) {
							if(playerList.size()==0) {
								JOptionPane.showMessageDialog(null, "Invalid removal!", "Warning", JOptionPane.ERROR_MESSAGE);
								textArea_1.append("Fail to remove player "+name+"\n");
							}
							else {
								for(int i = 0;i<playerList.size();i++) {
									if(name.equals(playerList.get(i).getID())) {
										colorList.add(playerList.get(i).getColorStr());
										playerList.remove(playerList.get(i));
										textField.setText("");
										textArea_1.append("Successfully remove player "+name+"\n");
										break;
									}
									if(i==playerList.size()-1) {
										JOptionPane.showMessageDialog(null, "No such username!", "Warning", JOptionPane.ERROR_MESSAGE);
										textArea_1.append("Fail to remove player "+name+"\n");
									}

								}
							}
							
						}
						else if(type.equals("loadmap")) {
							countriesList.clear();
							continentsList.clear();
							if(readFile(name)) {
								textArea_1.append("Loading map "+name+" success!\n");
								textField.setText("");
								String tmp[] = filesLoad.get(3).split(" ");
								File file = new File("image\\"+tmp[1]);
								if(file.exists()) {
									lblNewLabel.setText("");
									ImageIcon icon = new ImageIcon("Image\\"+tmp[1]);
									lblNewLabel.setIcon(icon);
								}
							}
							else {
								textArea_1.append("Loading map "+name+" fail!\n");
							}
						}
						textArea.setText("");
						for(Player p:playerList) {
							textArea.append("Name: "+p.getID()+"    Color: "+p.getColorStr()+"\n");
						}

					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Command invalid!", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnRun.setBounds(655, 302, 113, 27);
		contentPane.add(btnRun);
		
	}
}
