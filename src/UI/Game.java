package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Entities.*;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Game extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private Vector<Continent> continentsList = new Vector<Continent>();
	private Vector<Country> countriesList = new Vector<Country>();
	private Vector<Player> playerList = new Vector<Player>();
	private Vector <String> filesLoad = new Vector <String>();
	private int x,y;
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
		public void paintComponent(Graphics g) {
			String tmp[] = filesLoad.get(0).split(" ");
			File file = new File("image\\"+tmp[1]);
			if(file.exists()) {
				Image i = new ImageIcon("Image\\"+tmp[1]).getImage();
				g.drawImage(i,0,0,940,585,this);
			}
		}
	}

	private mapPanel visualization(mapPanel map) {
		for(Country c:countriesList) {
			map.add(c.getLabel());
		}
		return map;
	}
	
	/**
	 * Create the frame.
	 */
	/*public Game() {
		setTitle("Risk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel map = new JPanel();
		map.setBounds(20, 20, 940, 585);
		map.setLayout(null);
		map = visualized(map);
		contentPane.add(map);
		
		textField = new JTextField();
		textField.setBounds(20, 639, 804, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Run");
		btnNewButton.setBounds(847, 638, 115, 27);
		contentPane.add(btnNewButton);
	}*/
	
	public Game(Vector<Continent> continentsList, Vector<Country> countriesList, Vector<Player> playerList, Vector <String> filesLoad, int x, int y) {
		this.continentsList = continentsList;
		this.countriesList = countriesList;
		this.playerList = playerList;
		this.filesLoad = filesLoad;
		this.x = x;
		this.y = y;
		setTitle("Risk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		mapPanel map = new mapPanel();
		map.setBounds(20, 20, 940, 585);
		map.setLayout(null);
		map = visualization(map);
		contentPane.add(map);
		
		textField = new JTextField();
		textField.setBounds(20, 639, 804, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Run");
		btnNewButton.setBounds(847, 638, 115, 27);
		contentPane.add(btnNewButton);
	}
}
