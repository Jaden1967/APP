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
	private Vector<String> colorList = new Vector<String>();
	private String isCommandPattern = "(gameplayer -(add|remove) \\w*|loadmap \\w*\\.map|populatecountries)";
	GamePlay game;
	Controller control;
	
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
				if(game.getCountries().size()==0||game.getContinents().size()==0||game.getPlayers().size()<=1||game.getPlayers().size()>game.getCountries().size()) {
					JOptionPane.showMessageDialog(null, "Please check the arguments!\nCountry numbers: "+game.getCountries().size()+"\nContinent numbers: "+game.getContinents().size()+"\nPlayer numbers: "+game.getPlayers().size(), "Warning", JOptionPane.ERROR_MESSAGE);
					output_text.append("Fail to start game!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Game start!", "Good luck!", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					MapUI g = new MapUI(game.getCountries(), control);
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
					if(game.getPlayers().size()>=8) {
						JOptionPane.showMessageDialog(null, "Too many players!", "Warning", JOptionPane.ERROR_MESSAGE);
						output_text.append("Fail to add player "+name+"\n");
					}
					else {
						for(int i = 0;i<game.getPlayers().size();i++) {
							if(name.equals(game.getPlayers().get(i).getID())) {
								JOptionPane.showMessageDialog(null, "Same player name!", "Warning", JOptionPane.ERROR_MESSAGE);
								output_text.append("Fail to add player "+name+"\n");
								flag = 1;
								break;
							}
						}
						if(flag==0) {
							Color c = getColor(getRandColor(colorList));
							Player p = new Player(name, c);
							game.getPlayers().add(p);
							input_text.setText("");
							output_text.append("Successfully add player "+name+"\n");
						}
					}
				}
				else if(type.equals("-remove")) {
					if(game.getPlayers().size()==0) {
						JOptionPane.showMessageDialog(null, "Invalid removal!", "Warning", JOptionPane.ERROR_MESSAGE);
						output_text.append("Fail to remove player "+name+"\n");
					}
					else {
						for(int i = 0;i<game.getPlayers().size();i++) {
							if(name.equals(game.getPlayers().get(i).getID())) {
								colorList.add(game.getPlayers().get(i).getColorStr());
								game.getPlayers().remove(game.getPlayers().get(i));
								input_text.setText("");
								output_text.append("Successfully remove player "+name+"\n");
								break;
							}
							if(i==game.getPlayers().size()-1) {
								JOptionPane.showMessageDialog(null, "No such username!", "Warning", JOptionPane.ERROR_MESSAGE);
								output_text.append("Fail to remove player "+name+"\n");
							}

						}
					}
					
				}
				else if(type.equals("loadmap")) {
					if(control.readFile(name)) {
						output_text.append("Loading map "+name+" success!\n");
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
						output_text.append("Loading map "+name+" fail!\n");
					}
				}
				player_text.setText("");
				for(Player p:control.getFilesLoad()) {
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
	 * Create the frame for Startup Phase
	 */
	public Initial() {
		game = new GamePlay();
		control = new Controller(game);
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
