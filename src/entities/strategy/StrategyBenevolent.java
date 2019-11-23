package entities.strategy;

import java.util.Collections;
import java.util.Vector;

import entities.Country;
import entities.GamePlay;

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
	public StrategyBenevolent (GamePlay g) {
		this.game = g;
		this.player =g.getPlayer();
		this.ownedCountries = player.getOwnCountries();
	}
	
	@Override
	public void reinforce() {
		Collections.sort(player.getOwnCountries(),new CountryComparator());
		int curr = 0;
		int next = 1;
		while (next < player.getOwnCountries().size() && player.getArmyToPlace()>0) {
			if(ownedCountries.get(curr).getArmyNum() < ownedCountries.get(next).getArmyNum()) {
				int amt = Math.min(player.getArmyToPlace(), ownedCountries.get(next).getArmyNum()-ownedCountries.get(curr).getArmyNum());
				game.reinforceArmy(ownedCountries.get(curr), amt);
				continue;
			}else {
				curr++;
				next++;
			}
		}
		
		
	}
	
	@Override
	public void attack() {
		
	}
	
	@Override
	public void fortify() {
		
	}
}
