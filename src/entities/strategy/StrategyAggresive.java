package entities.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

import entities.Continent;
import entities.Country;
import entities.GamePlay;
import entities.Player;
import ui.MapUI;

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
public class StrategyAggresive extends Strategy {
	
	/**
	 * Default constructor containing the GamePlay entity 
	 * @param game GamePlay 
	 */
	public StrategyAggresive (GamePlay g) {
		this.game = g;
		this.current_player = game.getPlayer();
	}
	
	/**
	 * Reinforces the computer player's strongest country 
	 */
	public void doReinforcement(Player player) {
		 System.out.printf("Player %s is in reinforcement.\n", player.getID());
		 aggressivelyDeployArmy(player); 
	}

	private void aggressivelyDeployArmy(Player player) {
		 StrategyAggresive Aggresive = new StrategyAggresive(game);
		 Aggresive.game.phaseRecruit();
		 Vector<Country> countries = playerOwnedCountries(player);
 	     MapUI mapUi = new MapUI();
 	     String a = mapUi.textField.getText(); 
 	     String [] splitted = a.split("\\s+");
 	     int num = Integer.parseInt(splitted[2]);
		 Aggresive.game.reinforceArmy(aggressivelyPickCountryFrom(countries), num);
	}
	
	/**
	 * then always attack with this strongest country until it cannot attack anymore 
	 */
	public void doAttack(Player player) {
        System.out.printf("Player %s is in attack.\n", player.getID());
        aggressivelyAttack(player);
    }
	
	 public void aggressivelyAttack(Player player) {
		 Country ownedStrongestCountry = getOwnedStrongestCountry(player);
		 if(ownedStrongestCountry != null) { 	 
			 Vector<Country> enemyCountryList = getEnemyNeighbour(player, ownedStrongestCountry);
	         Collections.shuffle(enemyCountryList);
	         while(!enemyCountryList.isEmpty()) {        	 
	             Collections.shuffle(enemyCountryList);
	            // Country enemyCountry = enemyCountryList.get(0);
	             int attackArmy = ownedStrongestCountry.getArmyNum();
	             player.deployArmy(attackArmy);
	             if(ownedStrongestCountry.getArmyNum()<=0) {
	            	 break;
	             }
	          }
	      }
	 }
	    	
	/**
	 * then fortifies in order to maximize aggregation of forces in one country 
	 */	 
	 public void doFortification(Player player) {
	        System.out.printf("Player %sin fortification.\n", player.getID());
	        aggressivelyFortify(player);
	    }	 
	 	
	 private void aggressivelyFortify(Player player) {
		 Country ownedStrongestCountry = getOwnedStrongestCountry(player);
		 int i = player.getArmyToPlace();
		 ownedStrongestCountry.addArmy(i);
		 for(int m=0; m<playerOwnedCountries(player).capacity(); m++) {
			 if(!(playerOwnedCountries(player).get(m)==ownedStrongestCountry)) {
			 int n = playerOwnedCountries(player).get(m).getArmyNum();
			 playerOwnedCountries(player).get(m).removeArmy(n);
			 }
		 }
     }

	 private Country aggressivelyPickCountryFrom(Vector<Country> from) {
			int max = 0;
	        Vector<Country> evenCountries = new Vector<>();
	        for (Country country : from) {
	            if (country.getArmyNum() > max) {
	                evenCountries.clear();
	                evenCountries.add(country);
	                max = country.getArmyNum();
	            } else if (country.getArmyNum() == max) {
	                evenCountries.add(country);
	            }
	        }
	        return evenCountries.get(new Random().nextInt(evenCountries.size()));
	    }

	 private Vector<Country> playerOwnedCountries(Player player){
			 Vector<Continent> continents = player.getOwnContinent();
			 Vector<Country> countries = new Vector<>();
			 for(int i=0; i<continents.capacity();i++) {
				 Vector<Country> c = continents.elementAt(i).getContinentsCountries();
				 countries.addAll(c);
			 }
			 return countries;
		}

	 private Country getOwnedStrongestCountry(Player player) {
	     Country result = null;
		 Vector<Country> countries = playerOwnedCountries(player);
		 if (!countries.isEmpty()) {
	            System.out.println("get strongest country, list: " + countries);
	            countries.sort(new Comparator<Country>() {
	                @Override
	                public int compare(Country o1, Country o2) {
	                    return o2.getArmyNum() - o1.getArmyNum();
	                }
	            });
	            result = countries.get(0).getArmyNum() > 1 ? countries.get(0) : null;
	        }
	        return result;
		 }
	 
	 private Vector<Country> getEnemyNeighbour(Player player,Country c){
		 Vector<Country> enemyCountries = new Vector<Country>();
		 for (Country a: c.getLinkCountries()) {
				if (!a.getOwner().getID().equals(c.getOwner().getID())) {
					enemyCountries.add(a);
				}
	     }
		 return enemyCountries;
	 }
	
}
	

