package entities.strategy;

import java.util.Collections;
import java.util.HashSet;

import entities.Country;
import entities.GamePlay;
import entities.Player;
import entities.strategy.Strategy.CountryComparator;

public class StrategyRandom extends Strategy{

	
	public StrategyRandom(GamePlay game, Player p) {
		this.game = game;
		this.player = p;		
		this.type = "r";

	}
	
	/**
	 * Reinforces a random country in the computer player's owned countries
	 */
	@Override
	public void reinforce() {
		Collections.shuffle(ownedCountries);
		game.reinforceArmy(ownedCountries.get((int)(Math.random() * ownedCountries.size())), player.getArmyToPlace());
	}
	
	/**
	 * Attacks a random number of times a random country
	 */
	@Override
	public void attack() {
		if (game.checkIfCanAttack(player)) {
			Collections.shuffle(ownedCountries);
			int i = ownedCountries.size() - 1;
			while(!ownedCountries.get(i).hasEnemyNeighbour()) {
				i--;
			}
			// Choose a random owned country to attack
			Country attacker = ownedCountries.get(i);

			f: for(Country defender: attacker.getNeighbors()) {
				if(defender.getOwner().getID() == attacker.getOwner().getID()) continue f;
				if (attacker.getArmyNum() > 1){
					if(defender.getArmyNum() == 0){
						if(attacker.getArmyNum()-game.getAttackDice()>=1) {
							game.attackMove(game.getAttackDice());
						}
						else {
							game.attackMove(attacker.getArmyNum()-1);
						}
					}
					boolean randomCondition = true;
					while(randomCondition) {
						if (attacker.getArmyNum() > 1 && defender.getArmyNum() > 1) {
							randomCondition = Math.random() < 0.7;
						}
						else {
							randomCondition = false;
						}
						game.setAttack(attacker, defender, 1);
					}
				}else {
					break;
				}
			}
		}
	}
	
	/**
	 * Then fortifies a random country
	 */
	@Override
	public void fortify() {
		if(game.checkIfCanFortify(player)) {
			Collections.shuffle(ownedCountries);
			for(Country c: ownedCountries) {
				HashSet<String> visited = new HashSet<>();
				if (c.hasPathTo(ownedCountries.get(ownedCountries.size() - 1).getName(), player.getID(), visited)) {
					//fortify the player owned country with all - 1 of the "Strongest": enemy-less country
					game.fortify(ownedCountries.get(ownedCountries.size()-1), c, (int)(Math.random() * (ownedCountries.get(ownedCountries.size()-1).getArmyNum()-1)));
					break;
				}
			}
		}else {
			game.nextPlayer();
		}
	}
}