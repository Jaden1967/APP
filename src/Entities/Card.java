package Entities;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	private String type;
	private Image image;
	
	public Card(String type) {
		this.type = type;
		loadImage(this.type);
	}
	 
	public boolean loadImage (String type) {
		try {
			switch (type) {
				case "Infantry":
					this.image = ImageIO.read (new File ("Infantry.jpg"));
					return true;
				case "Cavalry":
					this.image = ImageIO.read (new File ("Cavalry.jpg"));
					return true;
				case "Artillery":
					this.image = ImageIO.read (new File ("Artilery.jpg"));
					return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
