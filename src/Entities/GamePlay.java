package Entities;

import java.util.Vector;

import UI.labels.InfoObsLabel;

import java.util.HashSet;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

/**
 * Model for Risk Game
 * 
 *
 */
public class GamePlay{
	private Vector <Continent> continentsList;
	private Vector<Country> countriesList;	
	private Vector<Player> playerList;
	private GameInfoTab gameInfo;
	int playerIndex; //index deciding whose turn to play
	String phase;
	Player player;
	String armyToPlace;
	
	
	public GamePlay (Vector<Continent> continentsList, Vector<Country> countriesList, Vector<Player> playerList,InfoObsLabel iol) {
		this.continentsList = continentsList;
		this.countriesList = countriesList;
		this.playerList = playerList;
		populateRandomly();
		this.playerIndex = 0;
		this.player = playerList.get(playerIndex);
		this.armyToPlace = String.valueOf(player.getArmyToPlace());
		this.gameInfo = new GameInfoTab();
		this.gameInfo.addObserver(iol);
		phaseOne();
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
	}
	
	private void phaseOne() {
		phase = "Reinforcement Phase";
		
		gameInfo.fetchInfo(phase, player.getID(), armyToPlace);
		System.out.println("Currently in "+phase);
		
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

		nextPlayer();
	}
	
	private void nextPlayer() {
		playerIndex++;
		if(playerIndex % playerList.size()==0) {
			playerIndex = 0;
		}
		player = playerList.get(playerIndex);
		armyToPlace = String.valueOf(player.getArmyToPlace());
		phaseOne();
	}
	
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
		gameInfo.fetchInfo(phase, player.getID(), armyToPlace);
	}
	
	public GameInfoTab getGameInfo() {
		return this.gameInfo;
	}
	
	
}
