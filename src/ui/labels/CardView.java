package ui.labels;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import entities.Card;

public class CardView extends JLabel implements Observer{

	private static final long serialVersionUID = 1L;
	private int count = 0;
	private Card represent = new Card();
	
	public CardView(Card c,ImageIcon icon){
		represent = c;
		setIcon(icon);
	}
	
	public void update(Observable arg0, Object arg1) {
		count++;
		if(count%2==1) {
			Border border = BorderFactory.createLineBorder(Color.red, 2);
			setBorder(border);
		}
		else {
			setBorder(null);
		}
	}

	public Card getRepresent() {
		return represent;
	}
	
	public int getCount() {
		return count;
	}
	
}
