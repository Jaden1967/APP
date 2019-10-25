package entities;

import javax.swing.ImageIcon;

public class Card {
	private String type;
	private ImageIcon icon;
	
	public Card() {}
	
	public Card(String type) {
		this.type = type;
		loadImage(this.type);
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
}
