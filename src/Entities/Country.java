package entities;

import java.awt.Font;
import java.util.HashSet;
import java.util.Observable;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import ui.labels.CountryObsLabel;

public class Country extends Observable{
		private String countryName;
		private Player owner;
		private int countryId;
		private Vector <Country> linked_countries = new Vector<Country>();
		private int armyNum = 0;
		private Continent continent;
		private int x;
		private int y;
		private CountryObsLabel label;
		private Border border; //Continent's border, constant throughout the game

		public Country() {
			this.countryName = "DNE";
			this.owner = new Player();
		}
	
		/**
		 * Initialize Country Object given parameters of id, name, map attributes
		 * Creates a corresponding CountryObsLabel to be fetched later 
		 * @param id serialized Country id number created 
		 * @param name assigned Country name 
		 * @param horizontal TODO
		 * @param vertical
		 * @param imageX
		 * @param imageY
		 * @param plotX
		 * @param plotY
		 */
		public Country(int id, String name, int horizontal, int vertical,int imageX, int imageY, int plotX, int plotY) {
			this.countryId = id;
			this.countryName = name;
			this.x = horizontal;
			this.y = vertical;
			this.owner = new Player();
			label = new CountryObsLabel(String.valueOf(armyNum));
			x = (int)((float)plotX/imageX*x);
			y = (int)((float)plotY/imageY*y);
			label.setBounds(x-15, y-15, 30, 30);
			label.setFont(new Font("SimSun", Font.BOLD, 15));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			this.addObserver(label);
		}
		
		/**
		 * Getter for owner of Country
		 * @return
		 */
		public Player getOwner() {
			return this.owner;
		}
		
		/**
		 * Setter for owned player
		 * Alerts observer to change color to that of the new player
		 * @param p
		 */
		public void setOwner(Player p) {
			owner = p;	
			label.setBackground(owner.getColor());
			alertObservers();

		}
		
		/**
		 * Adds Country object in argument to linkedCountries Vector as a neighbor
		 * does't need to add this to neighbour's linkedCountries the same function will be called for them too
		 * @param nbour
		 */
		public void addNeighbour(Country nbour) {
			for (Country c: this.linked_countries) {
				if (c.getID() == nbour.getID())
					return;
			}
			this.linked_countries.add(nbour);
		}
		
		/**
		 * Determines if this Country has a neighbour with the ID of the argument String
		 * @param countryId target neighbour's name
		 * @return true if country is connected, false if not
		 */
		public boolean hasNeighbour (String countryId) {
			for (Country c: this.linked_countries) {
				if (c.getName().equals(countryId)) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Depth-first recursive search to determine if there's a linked path owned by same player to the destination Country
		 * with countryId
		 * @param countryId destination countryId
		 * @param ownerId playerId who is suppoed to own the path
		 * @param visited HashSet of visited id of Countries during the recursive call
		 * @return false if no such path is found, true if yes
		 */
		public boolean hasPathTo (String countryId, String ownerId, HashSet<String>visited) {
			visited.add(this.getName());
			if(!this.getOwner().getID().equals(ownerId)) return false;
			if(this.getName().equals(countryId)) return true;
			for (Country c: this.linked_countries) {
				if(visited.contains(c.getName())) continue;
				if(c.hasPathTo(countryId,ownerId,visited)) return true;
			}
			return false;
			
		}
		
		/**
		 * Only used in phase 1 and phase 3
		 * @param playerID
		 */
		public void addArmy(int amount) {
			armyNum += amount;
			alertObservers();
		}
		
		/**
		 * Whenever there is a subtraction of army number
		 * @param amount
		 */
		public void removeArmy(int amount) {
			armyNum -= amount;
			alertObservers();
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
			for(Country c: this.linked_countries) {
				if (c.getID() == toID) {
					neighBourExists = true;
				}
			}
			if(!neighBourExists) return false;
			Country temp = new Country();
			for (Country c: linked_countries) {
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
		 * Getter of armyNum
		 * @return
		 */
		public int getArmyNum() {
			return this.armyNum;
		}
		
		public void setArmy(int i) {
			this.armyNum =i;
		}
		
		/**
		 * Getter of countryName
		 * @return
		 */
		public String getName() {
			return this.countryName;
		}
		
		/**
		 * Getter of the Continent this Country belongs to
		 * @return Continent object
		 */
		public Continent getContinent() {
			return this.continent;
		}
		
		/**
		 * Set Continent that this Country belongs to
		 * @param c
		 */
		public void setContinent(Continent c) {
			continent = c;
			border = BorderFactory.createLineBorder(c.getColor(), 3);
			label.setBorder(border);
		}
		
		/**
		 * Getter of surrounding Country Vector
		 * @return
		 */
		public Vector<Country> getLinkCountries() {
			return linked_countries;
		}
		
		/**
		 * Getter of CountryObsLabel l
		 * @return
		 */
		public CountryObsLabel getLabel() {
			return label;
		}
		
		/**
		 * Print all neighbors' name
		 */
		public void printLinkedCountries() {
			for(Country c:linked_countries) {
				System.out.println(c.getName());
			}
		}
		
		/**
		 * Print number of neighbors this Country has 
		 */
		public void printLinkedCountriesNum() {
			System.out.println(linked_countries.size());
		}
		
		/**
		 * 
		 * @return array of coordinate
		 */
		public int[] getXY() {
			int[] tmp = {x,y};
			return tmp;
		}
		
		/**
		 * After change of state, alerts attached CountryObsLabel 
		 */
		public void alertObservers() {
			setChanged();
			notifyObservers(this);
		}
}
