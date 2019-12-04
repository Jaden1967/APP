package entities.strategy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;

import entities.Country;
import entities.GamePlay;
import entities.Player;

/**
 * An benevolent computer player strategy that focuses on protecting its weak countries (reinforces its weakest countries,
 * never attacks, then fortifies in order to move armies to weaker countries) 
 * Contains 3 major functions:
 * 1. reinforces the computer player's weakest countries
 * 2. skips attack phase
 * 3. then fortifies in order to maximize distribution of forces in all countries
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class StrategyBenevolent extends Strategy{
	
	/**
	 * Default constructor containing the GamePlay entity 
	 * @param game GamePlay 
	 */
	public StrategyBenevolent (GamePlay g, Player p) {
		this.game = g;
		this.player = p;
		this.type = "b";
	}
	
	/**
	 * Reinforce its weakest country
	 */
	@Override
	public void reinforce() {
		Collections.sort(ownedCountries,new CountryComparator());
		int ind = 0;
		int avg = player.getArmyToPlace();
		for(Country c: ownedCountries) {
			avg += c.getArmyNum();
		}
		avg /= ownedCountries.size();
		//reinforce small amounts of army to each country to balance out
		while (player.getArmyToPlace() > 0) {
			Country curr = ownedCountries.get(ind);
			if (curr.getArmyNum() < avg+1) {
				System.out.println(player.getID()+" reinforce "+ownedCountries.get(ownedCountries.size() - 1).getName()+" 1 armies");
				game.reinforceArmy(curr, 1);
			}
			ind = (ind+1) % ownedCountries.size();
		}
		//enhancement
		for(Country c:player.getOwnCountries()) {
			c.addArmy(1);
		}
		
	}
	
	/**
	 * No attack all the time
	 */
	@Override
	public void attack() {
		//Do nothing
	}
	
	/**
	 * Fortify to its weakest country
	 */
	@Override
	public void fortify() {
		if(game.checkIfCanFortify(player)) {
			Collections.sort(ownedCountries,new CountryComparator());
			int curr = 0;
			while(curr<ownedCountries.size()) {
			HashSet<Country> visited = new HashSet<Country>();
			visited = ownedCountries.get(curr).getLinkCountries(ownedCountries.get(0).getOwner().getID(), visited);
			curr++;
				Vector<Country> tmpC = new Vector<Country>();
				for(Country c:visited) {
					tmpC.add(c);
				}
				Collections.sort(tmpC,new CountryComparator());
				int avg = (tmpC.get(tmpC.size()-1).getArmyNum()+tmpC.get(0).getArmyNum())/2;
				if(tmpC.get(tmpC.size()-1).getArmyNum()-avg<=0) {
					continue;
				}
				else {
					System.out.println("Fority from "+tmpC.get(tmpC.size()-1).getName()+" to "+tmpC.get(0).getName()+" with "+Integer.toString(tmpC.get(tmpC.size()-1).getArmyNum()-avg));
					game.fortify(tmpC.get(tmpC.size()-1), tmpC.get(0), tmpC.get(tmpC.size()-1).getArmyNum()-avg);
					return;
				}
			}
			game.nextPlayer();
		}
		else {
			game.nextPlayer(); 
		}
	}
}
