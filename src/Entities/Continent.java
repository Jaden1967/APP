package Entities;

import java.util.Vector;

public class Continent {
	String name;
	Vector<Country> countries;
	boolean isConquered;
	private int value;
	
	/**
	 * If one player fully occupied the continent, function will add its name in the player occupied list
	 * @return whether or not this continent is fully owned by a player
	 */
	public boolean checkIfConquered () {
		if (countries.size() == 0) return false;
		String owner = countries.get(0).getOwner().getID();
		if(countries.size() == 1 && !owner.contentEquals("")) return true;
		for (int i=1;i<countries.size();i++){
			if (!owner.equals(countries.get(i).getOwner())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * During phase one, when a player owns all countries in this Continent, player will be rewarded
	 * an additional army equivalent to the continent's value
	 * @return Continent's value
	 */
	public int getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public void updateOwner (int countryID, String playerID) {
		
	}
	
	
}
