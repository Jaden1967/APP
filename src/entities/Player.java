package entities;

import java.awt.Color;
import java.util.Vector;

import controller.Controller;
import entities.strategy.*;

/**
 * Player object for the game play
 * Contains attributes for a single player, such as id, owned country number, color, armies to place
 * as well as methods to alter these attributes
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class Player {
	private String id;
	private int total_country_number;
	private Vector<Continent> ownedContinent = new Vector<Continent>();
	private Vector<Card> ownedCard = new Vector<Card>(); 
	private Vector<Country> ownedCountries = new Vector<>();
	private Color color;
	private int army_to_place;
	private int trade_times;
	private GamePlay g;
	private boolean is_ai = false;
	private Strategy strategy;
	
	/**
	 * Empty constructor for Player
	 * Creates a player with the id: "DNE"
	 */
	public Player () {
		this.id = "DNE";
	}
	
	
	/**
	 * Constructor for human Player
	 * Initializes player with input id and Color
	 * @param id name of Player
	 * @param color Color of player
	 */
	public Player(String id, Color color, GamePlay g) {
		this.id = id;
		this.color = color;
		this.army_to_place = 0;
		this.trade_times = 0;
		this.g = g;
	}
	
	/**
	 * Constructor for AI Player
	 * Initializes ai with input id, Color and Strategy
	 * @param id name of Player
	 * @param color Color of player
	 */
	public Player(String id, Color color, GamePlay g,String strat) {
		
		this.id = id;
		this.color = color;
		this.army_to_place = 0;
		this.trade_times = 0;
		this.g = g;
		this.is_ai = true;
		switch (strat) {
			case "a": this.strategy = new StrategyAggresive(g,this); break;
			case "b": this.strategy = new StrategyBenevolent(g,this); break;
			case "c": this.strategy = new StrategyCheater(g,this); break;
			case "r": this.strategy = new StrategyRandom(g,this); break;
		}
		//System.out.println("AI player "+id+" is created");
	}
	
	
	/**
	 * If the player is an AI, use the defined specific Strategy object to automatically complete player actions
	 */
	public void doStrategy() {
		strategy.action();
	}
	
	/**
	 * Calculate the default amount of army rewarded to Player at the start of the recruitment phase
	 * Based on total number of Countries owned by the player
	 * @return raw number of armies to place as int
	 */
	public int calculateArmy() {
        int raw = total_country_number/3;
        if(raw<3) {
            raw=3;
        }
        return raw;
    }
	
	/**
	 * Reward a specified amount of army to place during recruitment phase
	 * @param i number of army to be rewarded
	 */
	public void rewardArmy(int i) {
		this.army_to_place += i;
	}
	
   /**
	* At the start of startup phase, this method is called to give players an initial amount of army
	* To place one at a time in round-robin fashion
	* @param playersize number of players
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
		rewardArmy(reward);
	}
	
	/**
	 * Reward army to place during reinforcement phase
	 */
	public void rewardInitialArmy() {
		this.army_to_place += calculateArmy() + extraArmyToAdd();
	}
	
	public void reSetArmy() {
		army_to_place = 0;
	}
	
	
	/**
	 * Decreases army to place by a certain amount after deployment or mobilization
	 * @param i amount of army to be deployed 
	 */
	public void deployArmy(int i) {
		this.army_to_place -= i;
	}
	

	
	/**
	 * Increments the number of countries that the player owns
	 * @param i number of countries to increase
	 */
	public void increaseCountry(int i) {
		total_country_number+=i;
	}
	
	/**
	 * Add a country to the player's owned country vecter
	 * @param c Country object to be added
	 */
	public void addCountry(Country c) {
		this.ownedCountries.add(c);
		total_country_number++;
	}
	
	/**
	 * When a player loses ownership of a country, the totalCountryNum is decreased. 
	 * When the player no longer have at least 1 country, the player loses, this function returns true
	 * @param c Country object to be removed
	 * @return true if player lost, false if player still has at least 1 country remaining
	 */
	public boolean removeCountry(Country c) {
		int index = -1;
		for (int i=0;i<ownedCountries.size();i++) {
			if(ownedCountries.get(i).getID()==(c.getID())) {
				index = i;
				break;
			}
		}
		if(index >= 0) {
			ownedCountries.remove(index);
			total_country_number--;
		}
		if (total_country_number>0) return false;
		
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
	 * @return Color of player
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Verifies if the player has won the game by owning all continents available in the game
	 * @param totalContinents the number of the continent in total
	 * @return true if player won
	 */
	public boolean checkWin (int totalContinents) {
		return this.ownedContinent.size() == totalContinents;
	}
	
	/**
	 * Getter for player's id
	 * @return playerID as String
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Getter for player's color String
	 * @return color as String
	 */
	public String getColorStr() {
		return String.valueOf(Integer.toString(this.color.getRGB()));
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
	
	/**
	 * Getter of total countries number
	 * @return number
	 */
	public int getTotalCountriesNumber() {
		return total_country_number;
	}
	
	/**
	 * Getter of owned card
	 * @return vector of card
	 */
	public Vector<Card> getOwnCard(){
		return this.ownedCard;
	}
	
	/**
	 * When the player is AI and has 5 cards to trade in, automatically trade in 3 of the same or 3 different cards
	 */
	public void autoTradeCards() {
		int i = 0, c = 0, a = 0;
		for (Card card: ownedCard) {
			if (card.getType().equals("Infantry")) i++;
			else if(card.getType().equals("Cavalry")) c++;
			else a++;
		}
		Vector<Integer> indexes_to_remove = new Vector<>();
		//add indexes to remove three-of-a-kind cards
		if(i==3 || c == 3 || a ==3) {
			if (i==3) {
				for(int ind= 0;ind<ownedCard.size();ind++) {
					if(ownedCard.get(ind).getType().equals("Infantry")) {
						indexes_to_remove.add(ind);
					}
				}
			}else if(c==3) {
				for(int ind= 0;ind<ownedCard.size();ind++) {
					if(ownedCard.get(ind).getType().equals("Cavalry")) {
						indexes_to_remove.add(ind);
					}
				}
			}else if(a==3) {
				for(int ind= 0;ind<ownedCard.size();ind++) {
					if(ownedCard.get(ind).getType().equals("Artillery")) {
						indexes_to_remove.add(ind);
					}
				}
			}
		}
		//add indexes to remove three all different cards
		else if(i>0 && c >0 && a >0) {
			i=1;c=1;a=1;
			for(int ind= 0;ind<ownedCard.size();ind++) {
				if(i==1 && ownedCard.get(ind).getType().equals("Infantry")) {
					indexes_to_remove.add(ind);
					i--;
				}
				if(c ==1 && ownedCard.get(ind).getType().equals("Cavalry")) {
					indexes_to_remove.add(ind);
					c--;
				}
				if(a ==1 && ownedCard.get(ind).getType().equals("Artillery")) {
					indexes_to_remove.add(ind);
					a--;
				}
			}
		}
		while(!indexes_to_remove.isEmpty()) {
			ownedCard.remove(indexes_to_remove.remove(indexes_to_remove.size()-1));
		}
		this.addTradeTimes();
		this.rewardArmy(this.trade_times*5);
	}
	
	/**
	 * Adds a card to the player's arsenal
	 * @param type Type of the card (Infantry, Calvary, Artillery)
	 */
	public void addCard(String type) {
		ownedCard.add(new Card(type));
	}
	
	/**
	 * Getter for vector of owned continent
	 * @return owned continent
	 */
	public Vector<Continent> getOwnContinent(){
		return this.ownedContinent;
	}
	
	/**
	 * Getter for vector of owned countries
	 * @return owned country vector
	 */
	public Vector<Country> getOwnCountries(){
		return this.ownedCountries;
	}
	
	/**
	 * Allows intake of own country vector for the strategy to use if player is AI
	 */
	public void setOwnCountriesInStrategy() {
		// System.out.println("Player "+this.id+" set owncountries in strategy "+" with size "+ownedCountries.size());
		this.strategy.setOwnedCountries(this.ownedCountries);
	}
	
	/**
	 * Setter for army to place
	 * @param i Value to set
	 */
	public void setArmyToPlace(int i) {
		this.army_to_place = i;
	}
	
	
	/**
	 * Getter for army to place
	 * @return armyToPlace as int
	 */
	public int getArmyToPlace() {
		return this.army_to_place;
	}
	
	/**
	 * Increments the amount of times the player has traded in cards
	 */
	public void addTradeTimes() {
		trade_times++;
	}
	
	/**
	 * Getter for trade_times, 
	 * @return amount of times the player has traded in cards
	 */
	public int getTradeTimes() {
		return trade_times;
	}
	
	/**
	 * Setter for trade_times 
	 * @param t amount of times the player has traded in cards
	 */
	public void setTradeTimes(int t) {
		trade_times = t;
	}
	
	

	/**
	 * Return whether or not this player object is an AI player
	 * @return is_ai boolean that determines if the player is AI
	 */
	public boolean isAI() {
		return this.is_ai;
	}
	
	public String getAiType() {
		return (this.is_ai)?this.strategy.getType():"h";
	}
	
	
}
