package Entities;

import java.util.Vector;

public class Country {
		private String countryName;
		private Player owner;
		private int countryId;
		private Vector <Country> linkCountries;
		private int armyNum = 0;
		private Continent belongTo;
		
		public Country() {
			
		}
		
		public Player getOwner() {
			return this.owner;
		}
		
		/**
		 * Only used in phase 1 and phase 3
		 * @param playerID
		 */
		public void addArmy(int amount) {
			armyNum += amount;
		}
		
		/**
		 * Whenever there is a subtraction of army number
		 * @param amount
		 */
		public void removeArmy(int amount) {
			armyNum -= amount;
		}
		
		/**
		 * During end of attack in phases 2 and fortification in phase 3
		 * @param toID id of connected country
		 * @param armyNum amount of army to move to destination
		 * @return whether or not moving is successful
		 */
		public boolean moveArmy(int toID, int armyNum) {
			if (this.armyNum < armyNum+1) return false;
			
			Country temp = new Country();
			for (Country c: linkCountries) {
				temp = c;
				if (c.getID() == toID) break;
			}
			if (temp.getID()!=toID) return false;
			
			temp.addArmy(armyNum);
			removeArmy(armyNum);
			return true;
		}
		
		/**
		 * Getter of country's id 
		 * @return ID integer
		 */
		public int getID() {
			return this.countryId;
		}
		
		/**
		 * Getter of the Continent this Country belongs to
		 * @return Continent object
		 */
		public Continent getContinent() {
			return this.belongTo;
		}
}
