package Entities;

import java.awt.Color;
import java.util.Vector;;

public class Player {
	private String playerID;
	private int totalCountryNum;
	private Vector <Continent> ownedContinent = new Vector<Continent>();
	private String color;
	private int armyToPlace;
	
	public Player(String id, String color) {
		this.playerID = id;
		this.color = color;
		this.ownedContinent = new Vector<>();
		this.armyToPlace = 0;
	}
	
	public void rewardArmy(int i) {
		this.armyToPlace += i;
	}
	
	public int getArmyToPlace() {
		return (this.armyToPlace + extraArmyToAdd());
	}
	
	public void deployArmy(int i) {
		this.armyToPlace -= i;
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
	

	public Color getColor() {
		return this.getColor(getColorStr());
	}
	
	private Color getColor(String color) {
        switch (color) {
            case "red": return Color.red;
            case "yellow": return Color.yellow;
            case "blue": return Color.blue;
            case "green": return Color.green;
            case "lightyellow": return new Color(255,255,224);
            case "grey": return Color.gray;
            case "magenta": return Color.magenta;
            case "orange": return Color.orange;
            case "pink": return Color.pink;
            case "cyan": return Color.cyan;
            case "OliveDrab": return new Color(85, 107, 47);
            case "skyblue": return new Color(135,206,250);
            case "white": return Color.white;
            case "purple": return new Color(128, 0, 128);
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
