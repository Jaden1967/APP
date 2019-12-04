package entities.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

import entities.Continent;
import entities.Country;
import entities.GamePlay;
import entities.Player;
import entities.strategy.Strategy.CountryComparator;
import ui.MapUI;

/**
 * An aggressive computer player strategy that focuses on attack Contains 3
 * major functions: 1. reinforces the computer player's strongest country 2.
 * then always attack with this strongest country until it cannot attack anymore
 * 3. then fortifies in order to maximize aggregation of forces in one country
 * 
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class StrategyAggresive extends Strategy {

	/**
	 * Default constructor containing the GamePlay entity
	 * 
	 * @param game GamePlay
	 */
	public StrategyAggresive(GamePlay g, Player p) {
		this.game = g;
		this.player = p;
		this.type = "a";
	}

	/**
	 * Reinforces the computer player's strongest country
	 */
	@Override
	public void reinforce() {
		// System.out.printf("Player %s is in reinforcement.\n", player.getID());
		Collections.sort(ownedCountries, new CountryComparator());
		System.out.println(player.getID()+" reinforce "+ownedCountries.get(ownedCountries.size() - 1).getName()+" "+player.getArmyToPlace()+" armies");
		game.reinforceArmy(ownedCountries.get(ownedCountries.size() - 1), player.getArmyToPlace());
	}

	/**
	 * Always attack with this strongest country until it cannot attack anymore
	 */
	@Override
	public void attack() {
		while (game.checkIfCanAttack(player)) {
			Collections.sort(ownedCountries, new CountryComparator());
			int i = ownedCountries.size()-1;
			while(!ownedCountries.get(i).hasEnemyNeighbour()||ownedCountries.get(i).getArmyNum()==1) {
				i--;
				if(i<0) {
					return;
				}
			}
			System.out.println("chose index: "+i+" country: "+ownedCountries.get(i).getName());
			//Choose owned country with highest army count to attack
			Country attacker = ownedCountries.get(i);
			//All out attack on all its neighbors until army count on that attacking country reduces to 1 or no more enemy neighbors
			f: for(Country defender: attacker.getNeighbors()) {
				if(defender.getOwner().getID() == attacker.getOwner().getID()) continue f;
				if(game.allOutAttack(attacker, defender)){
					System.out.println("From "+attacker.getName()+" attack "+defender.getName()+" win");
					System.out.println("Move "+Integer.toString(attacker.getArmyNum()-1)+" armies");
					game.attackMove(attacker.getArmyNum()-1);
				}
			}
		}
	}
	
	/**
	 * Then fortifies in order to maximize aggregation of forces in one country
	 */
	@Override
	public void fortify() {

		if(game.checkIfCanFortify(player)) {
			Collections.sort(ownedCountries, new CountryComparator());
			//if the country with the highest number of army count doesn't have any more enemy neighbors to attack
			if (!ownedCountries.get(ownedCountries.size()-1).hasEnemyNeighbour()) {
				Country tmp = null;
				for(Country c: ownedCountries) {
					int max = 0;
					HashSet<String> visited = new HashSet<>();
					//if the player owned country has path to the "Strongest" enemy-less country
					if (c.hasEnemyNeighbour() && c.hasPathTo(ownedCountries.get(ownedCountries.size()-1).getName(), player.getID(), visited)) {
						if(c.getEnemyNeighbour()>max) {
							max = c.getEnemyNeighbour();
							tmp = c;
						}
						//fortify the player owned country with all - 1 of the "Strongest": enemy-less country
					}
				}
				if(tmp!=null) {
					System.out.println("Fority from "+ownedCountries.get(ownedCountries.size()-1).getName()+" to "+tmp.getName()+" with "+Integer.toString(ownedCountries.get(ownedCountries.size()-1).getArmyNum()-1));
					game.fortify(ownedCountries.get(ownedCountries.size()-1), tmp, ownedCountries.get(ownedCountries.size()-1).getArmyNum()-1);
					return;
				}
			}
			game.nextPlayer();
		}else {
			game.nextPlayer();
		}
	}
}
