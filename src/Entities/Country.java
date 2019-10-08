package Entities;

import java.util.Vector;

public class Country {
		private String countryName;
		private Player owner;
		private int countryId;
		private Vector <Country> linkCountries;
		private int armyNum = 0;
		private Continent belongTo;
		private int x;
		private int y;
		
		
		public Country() {}
		
		
		/**
		 * Constructor for Country
		 * @param id serialized String for country
		 * @param name proper name for Country
		 * @param horizontal location on x axis on map
		 * @param vertical location on y axis on map
		 */
		public Country(int id, String name, int horizontal, int vertical) {
			this.countryId = id;
			this.countryName = name;
			this.x = horizontal;
			this.y = vertical;
			this.linkCountries = new Vector<>();
		}
		
		public void AssignOwner (Player p) {
			this.owner = p;
		}
		
		public Player getOwner() {
			return this.owner;
		}
		
		/**
		 * Adds Country object in argument to linkedCountries Vector as a neighbor
		 * does't need to add this to neighbour's linkedCountries the same function will be called for them too
		 * @param nbour
		 */
		public void addNeighbour(Country nbour) {
			for (Country c: this.linkCountries) {
				if (c.getID() == nbour.getID())
					return;
			}
			this.linkCountries.add(nbour);
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
		 * First verifies if country is connected and the army number to be moved is valid
		 * if verification is passed, adjusts army number of both countries accordingly
		 * @param toID id of connected country
		 * @param armyNum amount of army to move to destination
		 * @return whether or not moving is successful
		 */
		public boolean moveArmy(int toID, int armyNum) {
			if (this.armyNum < armyNum+1) return false;
			boolean neighBourExists = false;
			for(Country c: this.linkCountries) {
				if (c.getID() == toID) {
					neighBourExists = true;
				}
			}
			if(!neighBourExists) return false;
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
