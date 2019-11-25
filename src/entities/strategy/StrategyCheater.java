package entities.strategy;
import java.util.Vector;
import entities.Continent;
import entities.Country;
import entities.GamePlay;
import entities.Player;

/**
 * An aggressive computer player strategy that focuses on attack 
 * Contains 3 major functions:
 * 1. reinforces the computer player's strongest country
 * 2. then always attack with this strongest country until it cannot attack anymore
 * 3. then fortifies in order to maximize aggregation of forces in one country
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */


public class StrategyCheater extends Strategy {
	
	public StrategyCheater(GamePlay game, Player p) {
		this.game = game;
		this.player = p;		
		this.type = "c";

	}
	
	@Override
	//doubles the armies on all its countries
	public void reinforce() {
		for(int i = 0; i < ownedCountries.size(); i++) {
			Country c = ownedCountries.get(i);
			int temp = c.getArmyNum();
			c.addArmy(temp);
		}
		
	}
	
	// automatically conquers all the neighbors of all its countries
	@Override
	public void attack() {
		for(int i = 0; i < ownedCountries.size(); i++) {
			Country c = ownedCountries.get(i);
			Vector<Country> neighbour = c.getNeighbors(); 
			for(int j = 0; j<neighbour.size();j++) {
				Country currentCountry = neighbour.elementAt(j);
				if(!currentCountry.getOwner().getID().equals(player)) {
					currentCountry.setOwner(player);
				}
			}
		}
		
	}
	
	//doubles the number of armies on its countries that have neighbors that belong to other players. 
	@Override
	public void fortify() {
		for(int i = 0; i < ownedCountries.size(); i++) {
			Country c = ownedCountries.get(i);
			if(c.hasEnemyNeighbour()) {
				int temp = c.getArmyNum();
				c.addArmy(temp);
			}
			
		}
		
	}
	

}
