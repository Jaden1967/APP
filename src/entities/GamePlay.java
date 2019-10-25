package entities;

import java.util.Vector;
import javax.swing.JOptionPane;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Observable;
import java.util.Random;

/**
 * Model for Risk Game
 */
public class GamePlay extends Observable{
	private Vector <Continent> continents_list;
	private Vector<Country> countries_list;	
	private Vector<Player> player_list;
	private Player player;
	private int player_index = 0; //index deciding whose turn to play
	private int army_to_place;
	private int alert_type = 0;
	private String outcome;
	private String phase = "";
	private Country attacker = null;
	private Country defender = null;
	private int att_dice=0;
	private int def_dice=0;
	
	public GamePlay() {
	}
	
	public void clearData() {
		countries_list.clear();
		player_list.clear();
		continents_list.clear();
	}
	
	/**
	 * Player input: populatecountries
	 * randomly assign ownership of countries on the map until all are filled
	 */
	public void populateCountries(){
		HashSet <Integer> polledCountries = new HashSet <>();
		Random rand = new Random();
		int cInd; // Country index in list
		int pInd = 0; // Player index in list
		while (polledCountries.size()!=countries_list.size()) {
			do {
				cInd = rand.nextInt(countries_list.size()); //generate random unassigned Country index 
			} while (polledCountries.contains(cInd));
			
			polledCountries.add(cInd);
			
			countries_list.get(cInd).setOwner(player_list.get(pInd)); //set unassigned Country to Player in List in order
			countries_list.get(cInd).addArmy(1);
			player_list.get(pInd).increaseCountry();
			player_list.get(pInd).deployArmy(1);
			pInd++;
			if (pInd % player_list.size() == 0) {
				pInd = 0;
			}
		}
		outcome = "Randomly assigned countries to all players";
		alertObservers(2);
		for(int i = 0;i<continents_list.size();i++) {
			continents_list.get(i).checkIfConquered();
		}
		phaseZero();
	}
	
	
	
	/**
     * Startup Phase
     */
	private void phaseZero() {
		phase = "Startup Phase";
		for(Player p: player_list) p.initializeStartupArmy(player_list.size());

		player = player_list.get(player_index);
		army_to_place = player.getArmyToPlace();
		
		alertObservers(1);
	}
	
	/**
	 * From player command: placearmy countryname
     * place one army to target country in parameter, then give the turn to the next player 
	 */
	public void placeArmy(Country c) {
		c.addArmy(1);
		player.deployArmy(1);
		startUpNext();
	}
	
	/**
     * From player command: placeall
     * During startup phase, randomly places army on the map for all players until all players have no more units to place
     * Enters Reinforcement phase once all troops are deployed
     * Randomly places armies to current player's owned countries
     */

    public void placeAll() {
        System.out.println("placing all for player "+player.getID());
        for (Player p: player_list) {
            player = p;
            Vector <Country> toAdd = new Vector <>();
            for(Country c: countries_list) {
                if(c.getOwner().getID().equals(player.getID())) {
                    toAdd.add(c);
                }
            }
            int ind = 0;
            Random rand = new Random();
            while (player.getArmyToPlace()!=0) {
                ind = rand.nextInt(toAdd.size());
                toAdd.get(ind).addArmy(1);
                player.deployArmy(1);
            }
            army_to_place = player.getArmyToPlace();
        }
        player = player_list.get(0);
        player_index = 0;
        player.rewardInitialArmy();
        outcome = "Randomly assigned armies to owned countries";
        phaseRecruit();
        alertObservers(2);   	
    }
    
	
	
	/**
	 * For next player to place army during Startup Phase
	 * Switches GamePlay context to next player's datasets
	 */
	public void startUpNext() {
		player_index++;
		if(player_index % player_list.size()==0) {
			player_index = 0;
		}
		player = player_list.get(player_index);
		army_to_place = player.getArmyToPlace();
		outcome += "Next player's turn";
		alertObservers(1);
		alertObservers(2);
		if(army_to_place==0) {
			player.rewardInitialArmy();
			phaseRecruit();
		}
	}
	
	
	
	/**
     * Reinforcement Phase
     * Set phase to "Fortification Phase"
     * Alerts InfoObsLabel
     */
	private void phaseRecruit() {
		JOptionPane.showMessageDialog(null, "Reinforcement Phase for player "+player.getID(), "Information", JOptionPane.INFORMATION_MESSAGE);
		if(player.getOwnCard().size()==5) {
			JOptionPane.showMessageDialog(null, "You have reached the maximum number of cards, please trade!", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		phase = "Reinforcement Phase";
		army_to_place = player.getArmyToPlace();
		alertObservers(1);
	}
	
	/**
     * From player command: reinforce country number
     * During reinforcement phase, player assigns the input amount of army to target country 
     * @param c target countryId
     * @param num amount of army to assign
     */
	public void reinforceArmy (Country c, int num) {
		System.out.println("placing army on "+c.getName());
		c.addArmy(num);
		player.deployArmy(num);
		outcome = "Reinforced "+c.getName()+" with "+num+" armies";
		alertObservers(1);
		if(army_to_place==0) {
			phaseAttack();
		}
	}
	
	/**
	 * Check if a player has at least one country able to attack
	 * 1. Country belongs to the player
	 * 2. Country has at least 2 armies
	 * 3. Country has at least 1 enemy neighbor
	 * @param p Player to test if can attack
	 * @return true if can false if not
	 */
	public boolean checkIfCanAttack(Player p) {
		for (Country c:countries_list) {
			if (!c.getOwner().getID().equals(p.getID()) || c.getArmyNum()<2 || !c.hasEnemyNeighbour()) continue;
			return true;
		}
		return false;
	}
	
    /**
     * Attack Phase
     * If player is able to attack with at least one of its countries, continue the attack phase
     * Else go directly into fortify phase
     */
	private void phaseAttack() {
		JOptionPane.showMessageDialog(null, "Attack Phase for player "+player.getID(), "Information", JOptionPane.INFORMATION_MESSAGE);
		if(checkIfCanAttack(player)) {
			phase = "Attack Phase 1";
			alertObservers(1);
		}else {
			phaseFortify();
		}
		
	}
	
	public void setAttack(Country from, Country to, int dicenum) {
		attacker = from;
		defender = to;
		att_dice = dicenum;
		phase = "Attack Phase 2";
		alertObservers(1);
	}
	
	public void commenceAttack(int defenderDice) {
		def_dice = defenderDice;
		attackRound();
	}
	
	private void attackRound() {
		Vector<Integer> adice = new Vector<>();
		Vector<Integer> ddice = new Vector<>();
		Random rand = new Random();
		for (int i=0;i<att_dice;i++) {
			adice.add(rand.nextInt(6)+1);
		}
		for (int i=0;i<att_dice;i++) {
			adice.add(rand.nextInt(6)+1);
		}
		Comparator comparator = Collections.reverseOrder();
		Collections.sort(adice,comparator);
		Collections.sort(ddice,comparator);
		outcome = "Player "+attacker.getOwner().getID()+" rolled "
		+ adice.toString() + " , Player "+defender.getOwner().getID() + " rolled " + ddice.toString()+"\n"; 
		
		int alose = 0, dlose = 0;
		while (adice.size()!=0 && ddice.size()!=0) {
			if(adice.remove(0) > ddice.remove(0)) {
				defender.removeArmy(1);
				dlose++;
			}
			else {
				attacker.removeArmy(1);
				alose++;
			}
		}
		outcome += "Attacker lost "+alose+ " , Defender lose "+dlose;
		
		
		alertObservers(1);
		
		
		
	}
	
	
	
	
	public Country getDefender() {
		return this.defender;
	}
	
    /**
     * Fortify Phase
     * Set phase to "Fortification Phase"
     * alerts InfoObsLabel
     */
	private void phaseFortify() {
		JOptionPane.showMessageDialog(null, "Fortification Phase for player "+player.getID(), "Information", JOptionPane.INFORMATION_MESSAGE);
		phase = "Fortification Phase";
		alertObservers(1);
	}
	
	/**
	 * From player command fortify fromcountry tocountry num
	 * removes army number from from country, add it to to country
	 * @param from Country mobilizing army
	 * @param to Country receiving army
	 * @param num Amount of army mobilized
	 */
	public void fortify(Country from, Country to, int num) {
		from.removeArmy(num);
		to.addArmy(num);
		outcome = "Sucessfully mobilized from "+ from.getName()+" to "+to.getName()+" "+num+" armies";
		nextPlayer();
		
	}
	
	/**
	 * Switches context to next player in chronological order
	 * Initiate reinforcement phase for that player
	 */
	public void nextPlayer() {
		player_index++;
		if(player_index % player_list.size()==0) {
			player_index = 0;
		}
		player = player_list.get(player_index);
		player.rewardInitialArmy();
		army_to_place = player.getArmyToPlace();
		outcome += "\tNext player's turn";
		alertObservers(2);
		
		phaseRecruit();
	}
	
    /**
     * Getter for phase attribute
     * @return phase as String
     */
    public String getPhase() {
        return this.phase;
    }
    
    public void setPhase(String s) {
    	switch (s) {
    		case "0": phase = "Startup Phase";return;
    		case "1": phase = "Reinforcement Phase";return;
    		case "2": phase = "Attack Phase 1";return;
    		case "3": phase = "Attack Phase 2";return;
    		case "4": phase = "Attack Phase 3";return;
    		case "5": phase = "Fortification Phase";return;
    	}
    }
    
    /**
     * Getter for current player ID
     * @return player id as String
     */
    public String getPlayerID() {
        return this.player.getID();
    }
    
    /**
     * Getter for current Player
     * @return player as Player object
     */
    public Player getPlayer() {
        return this.player;
    }
    
    /**
     * Getter for army to place
     * @return armyToPlace as String
     */
    public int getArmyToPlace() {
        return this.army_to_place;
    }
    
    /**
     * Getter for alert type for observers
     * @return type as int
     */
    public int getAlertType() {
        return this.alert_type;
    }
    
    /**
     * Getter for outcome after a player action
     * @return outcome as String
     */
    public String getOutcome() {
        return this.outcome;
    }
    
    /**
     * Getter for vector of continent
     * @return continents_list as vector
     */
    public Vector<Continent> getContinents(){
    	return continents_list;
    }
    
    /**
     * Setter for continents_list
     * @param continentsList
     */
    public void setContinents(Vector<Continent> continentsList) {
		this.continents_list = continentsList;
	}
	
    /**
     * Getter for vector of countries
     * @return countries_list as vector
     */
	public Vector<Country> getCountries() {
		return countries_list;
	}
	
	/**
	 * Setter for countries_list
	 * @param countriesList
	 */
	public void setCountries(Vector<Country> countriesList) {
		this.countries_list = countriesList;
	}
	
    /**
     * Getter for vector of players
     * @return player_list as vector
     */
	public Vector<Player> getPlayers(){
		return player_list;
	}
	
	/**
	 * Setter for player_list
	 * @param playerList
	 */
	public void setPlayers(Vector<Player> playerList) {
		this.player_list = playerList;
		this.player = playerList.get(0);
	}
    
    /**
	 * After change of game state, alert concerned Observers
	 * @param x type of Observer to alert: 1 = InfoObsLabel, 2 = OutcomeObsLabel
	 */
	public void alertObservers(int x) {
		this.player = player_list.get(player_index);
		this.army_to_place = player.getArmyToPlace();

		alert_type = x;
		setChanged();
		notifyObservers(this);
		outcome = "";
		alert_type = 0;
	}
	
	public void cheatAddCard() {
		String tmp[] = {"Infantry","Cavalry","Artillery"};
		int r = (int)(Math.random()*3);
		player.addCard(tmp[r]);
		outcome = "add one random card to current player (cheat)";
		alertObservers(2);
	}
	
	/**
	 * Getter of player index
	 * @return integer of player index
	 */
	public int getPlayerIndex() {
		return this.player_index;
	}
	
	public void setPlayerIndex(int i) {
		this.player_index = i;
	}
}