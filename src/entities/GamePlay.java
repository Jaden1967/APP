package entities;

import java.util.Vector;

import ui.labels.InfoObsLabel;
import ui.labels.OutcomeObsLabel;
import ui.labels.PlayerTurnObsLabel;

import java.util.HashSet;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

/**
 * Model for Risk Game
 * 
 *
 */
public class GamePlay extends Observable{
	private Vector <Continent> continents_list;
	private Vector<Country> countries_list;	
	private Vector<Player> player_list;
	private int player_index; //index deciding whose turn to play
	private String phase;
	private Player player;
	private int army_to_place;
	private String outcome;
	private int alert_type;
	
	public GamePlay() {
		phase = "";
		player_index = 0;
		alert_type = 0;
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
			player_list.get(pInd).deployArmy(1);
			pInd++;
			if (pInd % player_list.size() == 0) {
				pInd = 0;
			}
		}
		
		outcome = "Randomly assigned countries to all players";
		alertObservers(2);
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
		
		System.out.println("Currently in "+phase);
		
		alertObservers(1);
	}
	
	/**
	 * From player command: placearmy countryname
     * place one army to target country in parameter, then give the turn to the next player 
	 */
	public void placeArmy(Country c) {
		c.addArmy(1);
		player.deployArmy(1);
		nextPlayerToPlace();
	}
	
	/**
     * From player command: placeall
     * During starup phase, randomly places army on the map for all players until all players have no more units to place
     * Enters Recruitment phase once all troops are deployed
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
        phaseOne();
        alertObservers(2);
        	
    }
    
    
	/**
     * Recruitment Phase
     * Set phase to "Fortification Phase"
     * Alerts InfoObsLabel
     */

	private void phaseOne() {
		phase = "Reinforcement Phase";
		army_to_place = player.getArmyToPlace();
		System.out.println("Currently in "+phase);
		
		alertObservers(1);
		//phaseTwo();
	}
	
	/**
     * From player command: recruit country num
     * During recruitment phase, player assigns the input amount of army to target country 
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
			phaseThree();
		}
	}
	
	
	
    /**
     * Attack Phase
     */
	private void phaseTwo() {
		phase = "Attack Phase";
		System.out.println("Currently in "+phase);
		
		phaseThree();
	}
	
    /**
     * Fortify Phase
     * Set phase to "Fortification Phase"
     * alerts InfoObsLabel
     */
	private void phaseThree() {
		phase = "Fortification Phase";
		
		System.out.println("Currently in "+phase);

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
	 * Initiate recruitment phase for that player
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
		
		phaseOne();
	}
	
	/**
	 * For next player to place army during Startup Phase
	 * Switches GamePlay context to next player's datasets
	 */
	public void nextPlayerToPlace() {
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
			phaseOne();
		}
	}
	
    
	
	
	
	
    /**
     * Getter for phase attribute
     * @return phase as String
     */
 
    public String getPhase() {
        return this.phase;
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
    
    public Vector<Continent> getContinents(){
    	return continents_list;
    }
    
    public void setContinents(Vector<Continent> continentsList) {
		this.continents_list = continentsList;
	}
	
	public Vector<Country> getCountries() {
		return countries_list;
	}
	
	public void setCountries(Vector<Country> countriesList) {
		this.countries_list = countriesList;
	}
	
	public Vector<Player> getPlayers(){
		return player_list;
	}
	
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
    

	
	
	
}