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
	
	public void reinforce() {}
	
	public void attack() {}
	
	public void fortify() {}
	
	
	class CountryComparator implements Comparator<Country>{
		@Override
		public int compare(Country o1, Country o2) {
			return o1.getArmyNum() - o2.getArmyNum();
		}
		
	}
}
