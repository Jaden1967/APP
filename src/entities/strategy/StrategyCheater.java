package entities.strategy;
import java.util.HashSet;
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
	
	/**
	 * Automatically conquers all the neighbors of all its countries.
	 * for every country in the Player's owned Countries, visit the neighbors that are viable to be conquered (once).
	 * Store the names of the countries into a hash set .
	 * At the end of the f1 for loop, use the HashSet to set all visited Countries to be owned by the Cheater Player and set the army count
	 * to 1.
	 */
	@Override
	public void attack() {
		HashSet<String> visited= new HashSet<>();
		
		f1: for(Country c: ownedCountries){
			f2: for (Country n: c.getNeighbors()) {
				if(visited.contains(n.getName()) || n.getOwner().getID().equals(player.getID()))continue f2;
				//will conquer countries that have not yet been visited and does not belong to player yet
				if(c.getArmyNum()>1) {
					visited.add(n.getName());
					c.removeArmy(1);
				}else {
					continue f1;
				}
			}
		}
		
		for(Country c: game.getCountries()) {
			if(visited.contains(c.getName())) {
				c.getOwner().removeCountry(c);
				c.getOwner().getOwnContinent().remove(c.getContinent());	
				if(c.getOwner().getTotalCountriesNumber()==0) {
					game.removePlayer(c.getOwner());
				}
				c.setOwner(player);
				player.addCountry(c);
				c.setArmy(1);
			}
		}
		game.checkWin();
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
		game.nextPlayer();
	}
	

}
