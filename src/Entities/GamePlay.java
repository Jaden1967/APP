package Entities;

import java.util.Vector;

import UI.labels.InfoObsLabel;
import UI.labels.OutcomeObsLabel;

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
	int playerIndex; //index deciding whose turn to play
	String phase;
	Player player;
	String armyToPlace;
	String outcome;
	int type;
	
	
	public GamePlay (Vector<Continent> continentsList, Vector<Country> countriesList, Vector<Player> playerList,InfoObsLabel iol,OutcomeObsLabel ocol) {
		this.continentsList = continentsList;
		this.countriesList = countriesList;
		this.playerList = playerList;
		populateRandomly();
		this.playerIndex = 0;
		this.type = 0;
		this.player = playerList.get(playerIndex);
		this.armyToPlace = String.valueOf(player.getArmyToPlace());
		this.addObserver(iol);
		this.addObserver(ocol);
		phaseOne();
	}
	
	public void alertObservers(int x) {
		type = x;
		setChanged();
		notifyObservers(this);
		type = 0;
	}
	
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
			playerList.get(pInd).rewardArmy(1);
			pInd++;
			if (pInd % playerList.size() == 0) {
				pInd = 0;
			}
		}
		outcome = "Randomly assigned countries to all players";
		alertObservers(2);
	}
	
	private void phaseOne() {
		phase = "Reinforcement Phase";
		
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
	
	public void nextPlayer() {
		playerIndex++;
		if(playerIndex % playerList.size()==0) {
			playerIndex = 0;
		}
		player = playerList.get(playerIndex);
		armyToPlace = String.valueOf(player.getArmyToPlace());
		outcome = "Next player's turn";
		alertObservers(2);
		
		phaseOne();
	}
	
	/**
	 * During Recruitment phase, when the player inputs "placeall"
	 * Randomly places armies to current player's owned countries
	 */
	public void randomAssignArmy() {
		System.out.println("placing all for player "+player.getID());
		Vector <Country> toAdd = new Vector <>();
		for(Country c: countriesList) {
			if(c.getOwner().getID().equals(player.getID())) {
				toAdd.add(c);
			}
		}
		int ind = 0;
		Random rand = new Random();
		while (player.getArmyToPlace()!=0) {
			int amt;
			if (player.getArmyToPlace()>= toAdd.size() && player.getArmyToPlace()/toAdd.size()>=2) {
				amt = rand.nextInt(player.getArmyToPlace()/toAdd.size() - 1 ) + 1;
			}else {
				amt = 1;
			}
			toAdd.get(ind).addArmy(amt);
			player.deployArmy(amt);
			ind ++;
			if(ind%toAdd.size()==0) {
				ind = 0;
			}
		}
		armyToPlace = String.valueOf(player.getArmyToPlace());
		outcome = "Randomly assigned armies to owned countries";
		alertObservers(2);
		phaseThree();
	}
	
	/**
	 * During Recruitment phase, player chooses to place one army to specific owned country
	 * @param countryID
	 */
	public void assignArmy (String countryID) {
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
			temp.addArmy(1);
			player.deployArmy(1);
			armyToPlace = String.valueOf(player.getArmyToPlace());
		}else {
			
		}
		alertObservers(1);
		if(armyToPlace.equals("0")) {
			phaseThree();
		}
	}
	
	public String getPhase() {
		return this.phase;
	}
	
	public String getPlayerID() {
		return this.player.getID();
	}
	
	public String getArmyToPlace() {
		return this.armyToPlace;
	}
	
	public int getAlertType() {
		return this.type;
	}
	
	public String getOutcome() {
		return this.outcome;
	}
	
	
	
	
}
