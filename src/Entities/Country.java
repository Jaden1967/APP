package Entities;

import java.awt.Color;
import java.awt.Font;

import java.util.Observable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import UI.labels.CountryObsLabel;

public class Country extends Observable{
		private String countryName;
		private Player owner;
		private int countryId;
		private Vector <Country> linkCountries = new Vector<Country>();
		private int armyNum = 0;
		private Continent belongTo;
		private int x;
		private int y;

		private CountryObsLabel l;
		private Border b; //Continent's border, constant throughout the game

		public Country() {
			this.countryName = "DNE";
			this.owner = new Player();
		}
	
		/**
		 * 
		 * @param id
		 * @param name
		 * @param horizontal
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

			l = new CountryObsLabel(String.valueOf(armyNum));


			l.setBounds((int)((float)plotX/imageX*x-10), (int)((float)plotY/imageY*y-10), 20, 20);
			l.setFont(new Font("SimSun", Font.BOLD, 15));
			l.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		public void modifyLabel() {
			
		}
		
		public Player getOwner() {
			return this.owner;
		}
		
		public void setOwner(Player p) {
			owner = p;

			if (armyNum==0) armyNum++;
			l.setBackground(owner.getColor());
			alertObservers();

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
		
		public void alertObservers() {
			setChanged();
			notifyObservers(this);
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
		

		public int getArmyNum() {
			return this.armyNum;
		}
		

		public String getName() {
			return this.countryName;
		}
		
		/**
		 * Getter of the Continent this Country belongs to
		 * @return Continent object
		 */
		public Continent getContinent() {
			return this.belongTo;
		}
		
		public void setContinent(Continent c) {
			belongTo = c;
			b = BorderFactory.createLineBorder(c.getColor(), 3);
			l.setBorder(b);
		}
		

		public CountryObsLabel getLabel() {
			return l;
		}
		
		public void printLinkedCountries() {
			for(Country c:linkCountries) {
				System.out.println(c.getName());
			}
		}
		
		public void printLinkedCountriesNum() {
			System.out.println(linkCountries.size());
		}
}
