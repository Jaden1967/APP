package UI;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import Entities.Player;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
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
	private JTextField textField;
	private Vector<Player> playerList = new Vector<Player>();
	private Vector<String> colorList = new Vector<String>();
	private String isCommandPattern = "-(add|remove)\\s\\w*";
	
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

		JLabel lblNewLabel = new JLabel("place for map picture");
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
		
		textField = new JTextField();
		textField.setBounds(14, 303, 577, 24);
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
					String type = textField.getText().substring(1, textField.getText().indexOf(" "));
					String name = textField.getText().substring(textField.getText().indexOf(" ")+1);
					int flag = 0;
					if(type.equals("add")) {
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
					else if(type.equals("remove")) {
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
					textArea.setText("");
					for(Player p:playerList) {
						textArea.append("Name: "+p.getID()+"    Color: "+p.getColorStr()+"\n");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Command invalid!", "Warning", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnRun.setBounds(605, 302, 113, 27);
		contentPane.add(btnRun);
		

	}
}
