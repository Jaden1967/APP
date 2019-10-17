package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Observable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import ui.labels.CountryObsLabel;

public class Country extends Observable{
		private String countryName;
		private Player owner;
		private int countryId;
		private Vector <Country> linkCountries = new Vector<Country>();
		private int armyNum = 0;
		private Continent belongTo;
		private int x;
		private int y;
		private boolean owned;
		private CountryObsLabel l;
		private Border b; //Continent's border, constant throughout the game

		/**
		* Default contructor for Country
		* Creates empty Country object with name "DNE"
		*/
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
			this.owned = false;
			l = new CountryObsLabel(String.valueOf(armyNum));
			l.setBounds((int)((float)plotX/imageX*x-10), (int)((float)plotY/imageY*y-10), 20, 20);
			l.setFont(new Font("SimSun", Font.BOLD, 15));
			l.setHorizontalAlignment(SwingConstants.CENTER);
			l.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					JOptionPane.showMessageDialog(null, "Country name: "+countryName+"\nOwner: "+owner.getID()+"\nArmy number"+armyNum, "Country information", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		
		/**
		 * Getter for owner of Country
		 * @return
		 */
		public Player getOwner() {
			return this.owner;
		}
		
		/**
		 * Boolean of whether country's currently owned by a player
		 * @return
		 */
		public boolean hasOwner() {
			return owned;
		}
		
		/**
		 * Setter for owned player
		 * Alerts observer to change color to that of the new player
		 * @param p
		 */
		public void setOwner(Player p) {
			owned = true;
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
		
		/**
		 * Determines if this Country has a neighbour with the ID of the argument String
		 * @param countryId target neighbour's name
		 * @return true if country is connected, false if not
		 */
		public boolean hasNeighbour (String countryId) {
			for (Country c: this.linkCountries) {
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
			for (Country c: this.linkCountries) {
				if(visited.contains(c.getName())) continue;
				if(c.hasPathTo(countryId,ownerId,visited)) return true;
			}
			return false;
			
		}
		
		/**
		 * After change of state, alerts attached CountryObsLabel 
		 */
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
		
		/**
		 * Getter of armyNum
		 * @return
		 */
		public int getArmyNum() {
			return this.armyNum;
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
			return this.belongTo;
		}
		
		/**
		 * Set Continent that this Country belongs to
		 * @param c
		 */
		public void setContinent(Continent c) {
			belongTo = c;
			b = BorderFactory.createLineBorder(c.getColor(), 3);
			l.setBorder(b);
		}
		
		/**
		 * Getter of neigbouring Country Vector
		 * @return
		 */
		public Vector<Country> getLinkCountries() {
			return linkCountries;
		}
		
		/**
		 * Getter of CountryObsLabel l
		 * @return
		 */
		public CountryObsLabel getLabel() {
			return l;
		}
		
		/**
		 * Print all neighbours' name
		 */
		public void printLinkedCountries() {
			for(Country c:linkCountries) {
				System.out.println(c.getName());
			}
		}
		
		/**
		 * Print number of neighbours this Country has 
		 */
		public void printLinkedCountriesNum() {
			System.out.println(linkCountries.size());
		}
}
