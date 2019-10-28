package ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Card;
import entities.GamePlay;
import ui.labels.CardView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class Trade extends JFrame{

	private static final long serialVersionUID = 1L;
	private Vector<Card> chosen_cards_list = new Vector<Card>();
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public Trade() {
		setTitle("Trade");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 760, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		ImageIcon icon1 = new ImageIcon("source\\Infantry.jpg");
		ImageIcon icon2 = new ImageIcon("source\\Cavalry.jpg");
		ImageIcon icon3 = new ImageIcon("source\\Artillery.jpg");
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(50, 50, 120, 200);
		lblNewLabel.setIcon(icon1);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		label.setBounds(180, 50, 120, 200);
		label.setIcon(icon2);
		contentPane.add(label);
		
		JLabel label1 = new JLabel("");
		label1.setBounds(310, 50, 120, 200);
		label1.setIcon(icon3);
		contentPane.add(label1);
		
		JLabel label2 = new JLabel("");
		label2.setBounds(440, 50, 120, 200);
		label2.setIcon(icon1);
		contentPane.add(label2);
		
		JLabel label3 = new JLabel("");
		label3.setBounds(570, 50, 120, 200);
		label3.setIcon(icon2);
		contentPane.add(label3);
		
		JButton btnTrade = new JButton("trade");
		btnTrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnTrade.setBounds(187, 302, 113, 30);
		contentPane.add(btnTrade);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(440, 302, 113, 30);
		contentPane.add(btnCancel);
	}
	
	public Trade(GamePlay game) {
		setTitle("Trade");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 760, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		for(int i = 0;i<game.getPlayer().getOwnCard().size();i++) {
			CardView l = game.getPlayer().getOwnCard().get(i).getCardView();
			l.reSetCount();
			l.setBounds(50+130*i, 50, 120, 200);
			l.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					l.getRepresent().changeView();
					if(l.getCount()%2==1) {
						chosen_cards_list.add(l.getRepresent());
					}
					else {
						chosen_cards_list.remove(l.getRepresent());
					}
					System.out.println(chosen_cards_list.size());
				}
			});
			contentPane.add(l);
		}
		
		JButton btnTrade = new JButton("trade");
		btnTrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chosen_cards_list.size()!=3) {
					JOptionPane.showMessageDialog(null, "You can only choose three cards!", "Warning", JOptionPane.ERROR_MESSAGE);
				}
				else {
					int flag = 0;
					if((chosen_cards_list.get(0).getType().equals("Infantry")&&chosen_cards_list.get(1).getType().equals("Infantry")&&chosen_cards_list.get(2).getType().equals("Infantry"))||
							(chosen_cards_list.get(0).getType().equals("Cavalry")&&chosen_cards_list.get(1).getType().equals("Cavalry")&&chosen_cards_list.get(2).getType().equals("Cavalry"))||
							(chosen_cards_list.get(0).getType().equals("Artillery")&&chosen_cards_list.get(1).getType().equals("Artillery")&&chosen_cards_list.get(2).getType().equals("Artillery"))||
							(!chosen_cards_list.get(0).getType().equals(chosen_cards_list.get(1).getType())&&
							!chosen_cards_list.get(0).getType().equals(chosen_cards_list.get(2).getType())&&
							!chosen_cards_list.get(1).getType().equals(chosen_cards_list.get(2).getType()))) {
						game.getPlayer().addTradeTimes();
						flag = game.getPlayer().getTradeTimes()*5;
						game.getPlayer().rewardArmy(flag);
					}
					else {
						JOptionPane.showMessageDialog(null, "You cannot trade with these cards!", "Warning", JOptionPane.ERROR_MESSAGE);
					}
					if(flag!=0) {
						for(Card c:chosen_cards_list) {
							game.getPlayer().getOwnCard().remove(c);
						}
						game.alertObservers();
						JOptionPane.showMessageDialog(null, "Trade success! You have "+flag+" extra armies!", "Good luck", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				}
			}
		});
		btnTrade.setBounds(187, 302, 113, 30);
		contentPane.add(btnTrade);
		
		JButton btnCancel = new JButton("cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(game.getPlayer().getOwnCard().size()>=5) {
					JOptionPane.showMessageDialog(null, "You have reached the maximum number of cards, please trade!", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					dispose();
				}
			}
		});
		btnCancel.setBounds(440, 302, 113, 30);
		contentPane.add(btnCancel);
	}
}
