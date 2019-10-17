package entities;

import java.awt.Color;
import java.util.Vector;;

public class Player {
	private String playerID;
	private int totalCountryNum;
	private Vector <Continent> ownedContinent = new Vector<Continent>();
	private String color;
	private int armyToPlace;
	
	/**
	 * Empty constructor for Player
	 * Creates a player with the id: "DNE"
	 */
	public Player () {
		this.playerID = "DNE";
	}
	
	/**
	 * Proper constructor for Player
	 * Initializes player with input id and Color
	 * @param id name of Player
	 * @param color Color of player
	 */
	public Player(String id, String color) {
		this.playerID = id;
		this.color = color;
		this.ownedContinent = new Vector<>();
		this.armyToPlace = 0;
	}
	
	/**
	 * Calculate the default amount of army rewarded to Player at the start of the recruitment phase
	 * Based on total number of Countries owned by the player
	 * @return rew number of armies to place as int
	 */
	public int calculateArmy() {
        int rew = totalCountryNum/3;
        if(rew<3) {
            rew=3;
        }
        return rew;
    }
	
	/**
	 * Reward a specified amount of army to place during recruitment phase
	 * @param i number of army to be rewarded
	 */
	public void rewardArmy(int i) {
		this.armyToPlace += i;
	}
	
	/**
	* At the start of startup phase, this method is called to give players an initial amount of army
	* To place one at a time in round-robin fashion
	*/
	public void initializeStartupArmy(int playersize) {
		int reward;
		switch (playersize) {
		case 8: reward = 10; break;
		case 7: reward = 15; break;
		case 6: reward = 20; break;
		case 5: reward = 25; break;
		case 4: reward = 30; break;
		case 3: reward = 35; break;
		case 2: reward = 40; break;
		default: reward = 0; 
		}
		this.armyToPlace += reward;
	}
	
	/**
	 * Reward army to place during recruitment phase
	 */
	public void rewardInitialArmy() {
		this.armyToPlace += calculateArmy() + extraArmyToAdd();
	}
	
	/**
	 * Getter for army to place
	 * @return armyToPlace as int
	 */
	public int getArmyToPlace() {
		return this.armyToPlace;
	}
	
	/**
	 * Decreases army to place by a certain amount after deployment or mobilization
	 * @param i amount of army to be deployed 
	 */
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
	
	/**
	 * Getter for Player's assigned Color
	 * @return
	 */
	public Color getColor() {
		return this.getColor(getColorStr());
	}
	
	/**
	 * Getter for corresponding color of input String
	 * @param color color as String
	 * @return Color color as Object
	 */
	private Color getColor(String color) {
        switch (color) {
            case "red": return Color.red;
            case "yellow": return Color.yellow;
            case "blue": return Color.blue;
            case "green": return Color.green;
            case "lightyellow": return new Color(107,142,35);
            case "grey": return Color.gray;
            case "magenta": return Color.magenta;
            case "orange": return Color.orange;
            case "pink": return Color.pink;
            case "cyan": return Color.cyan;
            case "DeepPink": return new Color(255,20,147);
            case "skyblue": return new Color(176, 196, 222);
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
	
	/**
	 * Getter for player's id
	 * @return playerID as String
	 */
	public String getID() {
		return this.playerID;
	}
	
	/**
	 * Getter for player's color String
	 * @return color as String
	 */
	public String getColorStr() {
		return this.color;
	}
	
	/**
	 * Adds continent under Player's ownedContinent list when all countries in that continent is owned by player
	 * @param con Continent currently fully conquered by Player
	 */
	public void addContinent (Continent con) {
		ownedContinent.add(con);
	}
	
	/**
	 * Removes continent under Player's ownedContinent list when player has lost control of full ownership of the Continent
	 * @param con Continent object to be removed from ownedContinent
	 * @return if continent is successfully removed
	 */
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
