package ui;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import controller.Controller;
import entities.Continent;
import entities.Country;
import entities.GamePlay;
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
	Vector<String[]> player_str_list = new Vector<>();
	private Vector<String> colorList = new Vector<String>();
	private String isCommandPattern = "(gameplayer -(add|remove) \\w*|loadmap \\w*\\.map|populatecountries)";
	GamePlay game;
	Controller control;
	
	
	/**
	 * Initial list of colors (string)
	 */
	private void initializeColors() {
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
	 * Get random color from List colorList
	 * @return color as Color object
	 */
	public String getRandColor () {
        int c = new Random().nextInt(colorList.size());
        String color = colorList.get(c);
        colorList.remove(c);
        return color;
    }
	
	/**
	 * Used as the controller, containing all the possible commands and responding based on the command
	 * @param picture_label
	 * @param input_text
	 * @param player_text
	 * @param output_text
	 */
	private void run(JLabel picture_label, JTextField input_text, JTextArea player_text, JTextArea output_text) {
		if(Pattern.matches(isCommandPattern, input_text.getText())) {
			String [] command = input_text.getText().split("\\s+");
			if(command[0].equals("populatecountries")) {
				if(game.getCountries().size()==0||game.getContinents().size()==0||player_str_list.size()<=1||player_str_list.size() >game.getCountries().size()) {
					JOptionPane.showMessageDialog(null, "Please check the arguments!\nCountry numbers: "+game.getCountries().size()+"\nContinent numbers: "+game.getContinents().size()+"\nPlayer numbers: "+player_str_list.size(), "Warning", JOptionPane.ERROR_MESSAGE);
					output_text.append("Fail to start game!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Game start!", "Good luck!", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					control.addPlayers(player_str_list);
					MapUI g = new MapUI(game.getCountries(), control);
					g.setVisible(true);
				}
			}
			else if(command[0].equals("gameplayer")) {
					input_text.setText(command[1]);
				
				String type = command[1];
				String name = command[2];
				if(type.equals("-add")) {
					if(player_str_list.size()>=8) {
						JOptionPane.showMessageDialog(null, "Too many players!", "Warning", JOptionPane.ERROR_MESSAGE);
						output_text.append("Fail to add player "+name+"\n");
					}
					else {
						for(String [] s: player_str_list) {
							if (name.equals(s[0])) {
								JOptionPane.showMessageDialog(null, "Same player name!", "Warning", JOptionPane.ERROR_MESSAGE);
								output_text.append("Fail to add player "+name+"\n");
								return;
							}
						}
	
						String c = getRandColor();
						player_str_list.add(new String[] {name,c});
						input_text.setText("");
						output_text.append("Successfully add player "+name+"\n");
					}
				}
				else if(type.equals("-remove")) {
					if(game.getPlayers().size()==0) {
						JOptionPane.showMessageDialog(null, "Invalid removal!", "Warning", JOptionPane.ERROR_MESSAGE);
						output_text.append("Fail to remove player "+name+"\n");
					}
					else {
						for(int i=0;i<player_str_list.size();i++) {
							if(name.equals(player_str_list.get(i)[0])) {
								colorList.add(player_str_list.get(i)[1]);
								player_str_list.remove(i);
								input_text.setText("");
								output_text.append("Successfully removed player "+name+"\n");
								return;
							}
						}
						JOptionPane.showMessageDialog(null, "No such username!", "Warning", JOptionPane.ERROR_MESSAGE);
						output_text.append("Fail to remove player "+name+"\n");
					}
					
				}
			}
			else if(command[0].equals("loadmap")) {
				if(control.readFile(command[1])) {
					output_text.append("Loading map "+command[1]+" success!\n");
					input_text.setText("");
					if(control.getFilesLoad().size()>=3) {
						String tmp[] = control.getFilesLoad().get(3).split(" ");
						File file = new File("image\\"+tmp[1]);
						if(file.exists()) {
							picture_label.setText("");
							ImageIcon icon = new ImageIcon("Image\\"+tmp[1]);
							picture_label.setIcon(icon);
						}
					}
				}
				else {
					output_text.append("Loading map "+command[1]+" fail!\n");
				}
			}
			player_text.setText("");
			for(String [] s:player_str_list) {
				player_text.append("Name: "+s[0]+"    Color: "+s[1]+"\n");
			}

			
		}
		else {
			JOptionPane.showMessageDialog(null, "Command invalid!", "Warning", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	/**
	 * Create the frame for Startup Phase
	 */
	public Initial() {
		initializeColors();
		game = new GamePlay();
		control = new Controller(game);
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
