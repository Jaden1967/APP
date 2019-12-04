package entities.strategy;

import java.util.Comparator;
import java.util.Vector;

import entities.Country;
import entities.GamePlay;
import entities.Player;

public abstract class Strategy{
	public GamePlay game;
	public Player player;
	public Vector <Country> ownedCountries;
	protected String type;
	
	/**
	 * AI's action, including reinforce, attack, and fortify
	 */
	public void action() {
		reinforce();
		attack();
		fortify();
	}
	
	/**
	 * Empty superclass reinforce function, will be overwrite by subclass
	 */
	public void reinforce() {}
	
	/**
	 * Empty superclass attack function, will be overwrite by subclass
	 */
	public void attack() {}
	
	/**
	 * Empty superclass fortify function, will be overwrite by subclass
	 */
	public void fortify() {}
	
	/**
	 * Comparator for Country object, based on its army number
	 * @return result after comparison
	 */
	class CountryComparator implements Comparator<Country>{
		@Override
		public int compare(Country o1, Country o2) {
			return o1.getArmyNum() - o2.getArmyNum();
		}
		
	}
	
	/**
	 * Setter for countries which this AI owns
	 * @param countries
	 */
	public void setOwnedCountries(Vector<Country> countries) {
		this.ownedCountries = countries;
	}
	
	/**
	 * get type of strategy
	 * a, b, c , r
	 * @return
	 */
	public String getType() {
		return this.type;
	}
}
