package ui;
import java.awt.Color;
import java.util.Random;
import java.util.regex.*;
import entities.Continent;
import entities.Country;
import entities.Player;

public class test {
	public static String isCommandPattern=("placearmy \\w*|placeall|reinforce \\w* [1-9][0-9]*|fortify (\\w* \\w* [1-9][0-9]*|none)");
	
	public boolean checkIfConquered (Continent c) {
		if (c.getContinentsCountries().size() == 0) return false;
		String owner = c.getContinentsCountries().get(0).getOwner().getID();
		for (int i=1;i<c.getContinentsCountries().size();i++){
			if (!owner.equals(c.getContinentsCountries().get(i).getOwner().getID())) {
				return false;
			}
		}
		return true;
	}
	
	public static void main (String args[] ) {
		Player p1 = new Player("p1",Color.red);
		Player p2 = new Player("p2",Color.green);
		Continent c = new Continent(1, "TC", 5, Color.red);
		Country c1 = new Country(1, "c1", 1, 1,1, 1, 1, 1);
		c1.setOwner(p1);
		Country c2 = new Country(2, "c2", 1, 1,1, 1, 1, 1);
		c2.setOwner(p2);
		c.addToCountriesList(c1);
		c.addToCountriesList(c2);
		System.out.println(c.checkIfConquered());
	}
}