package entities;

import java.util.Observable;

import javax.swing.ImageIcon;

import ui.labels.CardView;

public class Card extends Observable{
	private String type;
	private ImageIcon icon;
	private CardView cv = null;
	
	public Card() {}
	
	public Card(String type) {
		this.type = type;
		loadImage(this.type);
		cv = new CardView(this,icon);
		addObserver(cv);
	}
	 
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
	
	public String getType() {
		return this.type;
	}
	
	public ImageIcon getImage() {
		return this.icon;
	}
	
	public CardView getCardView() {
		return cv;
	}
	
	public void changeView() {
		setChanged();
		notifyObservers(this);
	}
}
