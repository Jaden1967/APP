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
		System.out.println("reinforce for attack ai");
		// System.out.printf("Player %s is in reinforcement.\n", player.getID());
		Collections.sort(ownedCountries, new CountryComparator());
		game.reinforceArmy(ownedCountries.get(ownedCountries.size() - 1), player.getArmyToPlace());
	}

	/**
	 * Always attack with this strongest country until it cannot attack anymore
	 */
	@Override
	public void attack() {
		System.out.println("attack for attack ai");

		if (game.checkIfCanAttack(player)) {
			Collections.sort(ownedCountries, new CountryComparator());
			int i = ownedCountries.size();
			while(!ownedCountries.get(i-1).hasEnemyNeighbour()) {
				i--;
			}
			//Choose owned country with highest army count to attack
			Country attacker = ownedCountries.get(i-1);
			//All out attack on all its neighbors until army count on that attacking country reduces to 1 or no more enemy neighbors
			f: for(Country defender: attacker.getNeighbors()) {
				if(defender.getOwner().getID() == attacker.getOwner().getID()) continue f;
				if (attacker.getArmyNum() > 1){
					if(game.allOutAttack(attacker, defender)){
						game.attackMove(game.getAttackDice());
					}else {
						return;
					}
				}else {
					return;
				}
			}
		}
		
	}
	
	/**
	 * Then fortifies in order to maximize aggregation of forces in one country
	 */
	@Override
	public void fortify() {
		System.out.println("fortify for attack ai");

		if(game.checkIfCanFortify(player)) {
			Collections.sort(ownedCountries, new CountryComparator());
			//if the country with the highest number of army count doesn't have any more enemy neighbors to attack
			if (!ownedCountries.get(ownedCountries.size()-1).hasEnemyNeighbour()) {
				for(Country c: ownedCountries) {
					HashSet<String> visited = new HashSet<>();
					//if the player owned country has path to the "Strongest" enemy-less country
					if (c.hasEnemyNeighbour() && c.hasPathTo(ownedCountries.get(ownedCountries.size()-1).getName(), player.getID(), visited)) {
						//fortify the player owned country with all - 1 of the "Strongest": enemy-less country
						game.fortify(ownedCountries.get(ownedCountries.size()-1), c, ownedCountries.get(ownedCountries.size()-1).getArmyNum()-1);
						return;
					}
				}
			}
			game.nextPlayer();
		}else {
			game.nextPlayer();
		}
	}

	/**
	 * then fortifies in order to maximize aggregation of forces in one country
	 *//*
		 * public void doFortification(Player player) {
		 * System.out.printf("Player %sin fortification.\n", player.getID());
		 * aggressivelyFortify(player); }
		 * 
		 * private void aggressivelyFortify(Player player) { Country
		 * ownedStrongestCountry = getOwnedStrongestCountry(player); int i =
		 * player.getArmyToPlace(); ownedStrongestCountry.addArmy(i); for(int m=0;
		 * m<ownedCountries.size(); m++) {
		 * if(!(ownedCountries(player).get(m)==ownedStrongestCountry)) { int n =
		 * ownedCountries(player).get(m).getArmyNum();
		 * ownedCountries(player).get(m).removeArmy(n); } } }
		 * 
		 * 
		 * private Vector<Country> getEnemyNeighbour(Player player,Country c){
		 * Vector<Country> enemyCountries = new Vector<Country>(); for (Country a:
		 * c.getNeighbors()) { if (!a.getOwner().getID().equals(c.getOwner().getID())) {
		 * enemyCountries.add(a); } } return enemyCountries; }
		 */

}
