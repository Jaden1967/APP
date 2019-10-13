package Entities;

import java.util.Observable;

/**
 * Observable class that fetches information during game play
 * Such as phase, player in play, and player's army to place
 * 
 */
public class GameInfoTab extends Observable{
	private String ph; //phase
	private String pl; //player name
	private String army; //army to place
	
	public GameInfoTab() {
		ph = "";
		pl = "";
		army = "";
	}
	
	void fetchInfo (String phase, String player, String armyToPlace) {
		this.ph = phase;
		this.pl = player;
		this.army = armyToPlace;
		alertObservers();
	}
	
	public void alertObservers() {
		setChanged();
		notifyObservers(this);
	}
	
	public String getPhaseIn() {
		return ph;
	}
	
	public String getPlayerIn() {
		return pl;
	}
	
	public String getArmyIn() {
		return army;
	}
}
