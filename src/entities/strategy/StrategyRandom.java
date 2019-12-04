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
		while(player.getArmyToPlace()!=0) {
			int r = (int)(Math.random()*player.getOwnCountries().size());
			System.out.println(player.getID()+" reinforce "+player.getOwnCountries().get(r).getName()+" 1 armies");
			game.reinforceArmy(player.getOwnCountries().get(r), 1);
		}
		//enhancement
		for(Country c:player.getOwnCountries()) {
			if(Math.random()>=0.66) {
				c.addArmy(1);
			}
		}
	}
	
	/**
	 * Attacks a random number of times a random country
	 */
	@Override
	public void attack() {
		int chances = (int)(Math.random()*11);
		int count = 0;
		while (game.checkIfCanAttack(player)&&count<=chances) {
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
				if(Math.random()>=0.5) {
					if(game.allOutAttack(attacker, defender)){
						System.out.println("From "+attacker.getName()+" attack "+defender.getName()+" win");
						System.out.println("Move "+Integer.toString(attacker.getArmyNum()-1)+" armies");
						game.attackMove(attacker.getArmyNum()-1);
					}
				}
			}
			count++;
		}
	}
	
	/**
	 * Then fortifies a random country
	 */
	@Override
	public void fortify() {
		int r = (int)(Math.random()*player.getOwnCountries().size());
		Country from = player.getOwnCountries().get(r);
		HashSet<Country> visited = new HashSet<Country>();
		visited = from.getLinkCountries(player.getID(), visited);
		int i = (int)(Math.random()*visited.size());
		int tmp = 0;
		Country to = null;
		for(Country c:visited) {
			if(tmp==i) {
				to = c;
				break;
			}
			tmp++;
		}
		int num = (int)(Math.random()*(from.getArmyNum()-1));
		System.out.println("Fority from "+from.getName()+" to "+to.getName()+" with "+num);
		game.fortify(from, to, num);
	}
}