package Entities;

import java.awt.Color;
import java.util.Vector;;

public class Player {
	String playerID;
	private int totalCountryNum;
	Vector <Continent> ownedContinent;
	private String color;
	
	public Player(String id, String color) {
		this.playerID = id;
		this.color = color;
		this.ownedContinent = new Vector<>();
	}
	
	/**
	 * When the player successfully conquers a new country, increment the totalaCountryNum this player has
	 */
	public void increaseCountry () {
		this.totalCountryNum++;
	}
	
	/**
	 * When a player loses ownership of a country, the totalCountryNum is decreased. 
	 * When the player no longer have at least 1 country, the player loses, this function returns true
	 * @return true if player lost, false if player still has at least 1 country remaining
	 */
	public boolean decreaseCountry()  {
		this.totalCountryNum--;
		if (this.totalCountryNum >0 ) return false;
		return true;
	}
	
	/**
	 * During phase one, when a player owns all countries in this Continent, player will be rewarded
	 * an additional army equivalent to the continent's value
	 * @return total army bonus to add due to owned continents
	 */
	public int extraArmyToAdd() {
		int value = 0; 
		for (Continent con: ownedContinent) {
			value += con.getValue();
		}
		return value;
	}
	
	private Color getColor(String color) {
        switch (color) {
            case "red": return Color.red;
            case "yellow": return Color.yellow;
            case "blue": return Color.blue;
            case "green": return Color.green;
            case "black": return Color.black;
            case "grey": return Color.gray;
            case "purple": return Color.magenta;
            case "orange": return Color.orange;
            default: return Color.white;
        }
    }
	
	/**
	 * Verifies if the player has won the game by owning all continents available in the game
	 * @param totalContinents
	 * @return
	 */
	public boolean checkWin (int totalContinents) {
		return this.ownedContinent.size() == totalContinents;
	}
	
	public String getID() {
		return this.playerID;
	}
	
	public String getColorStr() {
		return this.color;
	}
	
	public void addContinent (Continent con) {
		ownedContinent.add(con);
	}
	
	public boolean removeContinent (Continent con) {
		int counter = 0;
		for (Continent c: ownedContinent) {
			if(c.getName().equals(con.getName())) {
				break;
			}
			counter++;
		}
		if(counter >= this.ownedContinent.size()) return false;
		ownedContinent.remove(counter);
		return true;
		
	}
	
	
}
