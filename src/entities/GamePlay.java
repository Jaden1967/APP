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
	private Vector <Continent> continentsList;
	private Vector<Country> countriesList;	
	private Vector<Player> playerList;
	int player_index; //index deciding whose turn to play
	String phase;
	Player player;
	String army_to_place;
	String outcome;
	int type;
	int place_flag;
	
	/**
	 * Contructor for a game instance
	 * Sets all instances of continents, countries and players to allow interaction
	 * Initiates Startup Phase at the end of function
	 * @param continentsList
	 * @param countriesList
	 * @param playerList
	 * @param iol
	 * @param ocol
	 */
	public GamePlay (Vector<Continent> continentsList, Vector<Country> countriesList, Vector<Player> playerList,InfoObsLabel iol,OutcomeObsLabel ocol,
			PlayerTurnObsLabel ptol) {
		this.continentsList = continentsList;
		this.countriesList = countriesList;
		this.playerList = playerList;
		for(Player p: playerList) p.initializeStartupArmy(playerList.size());
		populateRandomly();
		this.player_index = 0;
		this.type = 0;
		this.place_flag = 0;
		this.player = playerList.get(player_index);
		this.army_to_place = String.valueOf(player.getArmyToPlace());
		this.addObserver(iol);
		this.addObserver(ocol);
		this.addObserver(ptol);
		phaseZero();
	}
	
	/**
	 * After change of game state, alert concerned Observers
	 * @param x type of Observer to alert: 1 = InfoObsLabel, 2 = OutcomeObsLabel
	 */
	public void alertObservers(int x) {
		type = x;
		setChanged();
		notifyObservers(this);
		type = 0;
	}
	
	/**
	 * Player input: populatecountries
	 * randomly assign ownership of countries on the map until all are filled
	 */
	private void populateRandomly(){
		HashSet <Integer> polledCountries = new HashSet <>();
		Random rand = new Random();
		int cInd; // Country index in list
		int pInd = 0; // Player index in list
		while (polledCountries.size()!=countriesList.size()) {
			do {
				cInd = rand.nextInt(countriesList.size()); //generate random unassigned Country index 
			} while (polledCountries.contains(cInd));
			
			polledCountries.add(cInd);
			
			countriesList.get(cInd).setOwner(playerList.get(pInd)); //set unassigned Country to Player in List in order
			playerList.get(pInd).deployArmy(1);
			pInd++;
			if (pInd % playerList.size() == 0) {
				pInd = 0;
			}
		}
		
		outcome = "Randomly assigned countries to all players";
		alertObservers(2);
	}
	
	/**
	 * From player command: placearmy countryname
	 */
	public void placeArmy(Country c) {
		c.addArmy(1);
		player.deployArmy(1);
		nextPlayerToPlace();
	}
	
	private void phaseZero() {
		phase = "Startup Phase";
		
		army_to_place = String.valueOf(player.getArmyToPlace());
		System.out.println("Currently in "+phase);
		
		alertObservers(1);
	}
	
	private void phaseOne() {
		place_flag = 1;
		phase = "Reinforcement Phase";
		army_to_place = String.valueOf(player.getArmyToPlace());
		System.out.println("Currently in "+phase);
		
		alertObservers(1);
		//phaseTwo();
	}
	
	private void phaseTwo() {
		phase = "Attack Phase";
		System.out.println("Currently in "+phase);
		
		phaseThree();
	}
	
	private void phaseThree() {
		phase = "Fortification Phase";
		
		System.out.println("Currently in "+phase);

		alertObservers(1);
	}
	
	/**
	 * Switches context to next player in chronological order
	 * Initiate recruitment phase for that player
	 */
	public void nextPlayer() {
		player_index++;
		if(player_index % playerList.size()==0) {
			player_index = 0;
		}
		player = playerList.get(player_index);
		player.rewardInitialArmy();
		army_to_place = String.valueOf(player.getArmyToPlace());
		outcome = "Next player's turn";
		alertObservers(2);
		
		phaseOne();
	}
	
	/**
	 * For next player to place army during Startup Phase
	 */
	public void nextPlayerToPlace() {
		player_index++;
		if(player_index % playerList.size()==0) {
			player_index = 0;
		}
		player = playerList.get(player_index);
		army_to_place = String.valueOf(player.getArmyToPlace());
		outcome = "Next player's turn";
		alertObservers(1);
		alertObservers(2);
		if(army_to_place.equals("0")) {
			player.rewardInitialArmy();
			phaseOne();
		}
	}
	
	/**
     * During startup phase, when the player inputs "placeall"
     * Randomly places armies to current player's owned countries
     */
    public void randomAssignArmy() {
        System.out.println("placing all for player "+player.getID());
        for (Player p: playerList) {
            player = p;
            Vector <Country> toAdd = new Vector <>();
            for(Country c: countriesList) {
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
            army_to_place = String.valueOf(player.getArmyToPlace());

        }
        place_flag  = 1;
        player = playerList.get(0);
        player_index = 0;
        player.rewardInitialArmy();
        outcome = "Randomly assigned armies to owned countries";
        phaseOne();
        alertObservers(2);
        	
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
		outcome = "Sucessfull mobilized";
		alertObservers(2);
		nextPlayer();
	}
	
	/**
	 * During Recruitment phase, player chooses to place one army to specific owned country
	 * @param countryID
	 */
	public void assignArmy (String countryID, int num) {
		System.out.println("placing army on "+countryID);
		Country temp = new Country();
		for (Country c:countriesList) {
			if (c.getName().equals(countryID)) {
				temp = c;
			}
		}
		
		if(temp.getName().equals("DNE")) {
			outcome = "Country does not Exist!";
			alertObservers(2);
			return;
		}
		
		if(temp.getOwner().getID().equals(player.getID())) {
			temp.addArmy(num);
			player.deployArmy(num);
			army_to_place = String.valueOf(player.getArmyToPlace());
		}else {
			
		}
		alertObservers(1);
		if(army_to_place.equals("0")) {
			phaseThree();
		}
	}
	
	public String getPhase() {
		return this.phase;
	}
	
	public String getPlayerID() {
		return this.player.getID();
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public String getArmyToPlace() {
		return this.army_to_place;
	}
	
	public int getAlertType() {
		return this.type;
	}
	
	public String getOutcome() {
		return this.outcome;
	}
	
	public boolean inStartUpPhase() {
		return place_flag == 0;
	}
	
	
	
}
