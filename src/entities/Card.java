package entities;

import java.util.Observable;

import javax.swing.ImageIcon;

import ui.labels.CardView;

/**
 * Cards object for the game play
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class Card extends Observable{
	private String type;
	private ImageIcon icon;
	private CardView cv = null;
	
	public Card() {}
	
	/**
	 * Initial for Card object
	 * @param type
	 */
	public Card(String type) {
		this.type = type;
		loadImage(this.type);
		cv = new CardView(this,icon);
		addObserver(cv);
	}
	 
	/**
	 * Based on its type, load an existing image and make connection between them
	 * @param type
	 * @return boolean, successfully load or not
	 */
	public boolean loadImage (String type) {
		switch (type) {
			case "Infantry":
				this.icon = new ImageIcon("source\\Infantry.jpg");
				return true;
			case "Cavalry":
				this.icon = new ImageIcon("source\\Cavalry.jpg");
				return true;
			case "Artillery":
				this.icon = new ImageIcon("source\\Artillery.jpg");
				return true;
		}
		return false;
	}
	
	/**
	 * Getter for card type
	 * @return String card_type
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Getter for card image
	 * @return ImageIcon
	 */
	public ImageIcon getImage() {
		return this.icon;
	}
	
	/**
	 * Getter for card view
	 * @return CardView object
	 */
	public CardView getCardView() {
		return cv;
	}
	
	/**
	 * Update card view
	 */
	public void changeView() {
		setChanged();
		notifyObservers(this);
	}
}
