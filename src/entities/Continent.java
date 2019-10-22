package entities;

import java.awt.Color;
import java.util.Vector;

public class Continent {
	private int continentID;
	private String name;
	private Vector<Country> countries = new Vector<Country>();
	private int value;
	private Color color;
	
	/**
	 * Constructor for Continent
	 * @param name of continent
	 * @param value of continent to be rewarded to complete owner
	 * @param color of continent
	 */
	public Continent(int continentID, String name, int value, Color color) {
		this.continentID = continentID;
		this.name = name;
		this.value = value;
		this.color = color;
	}
	
	/**
	 * If one player fully occupied the continent, function will add its name in the player occupied list
	 * @return whether or not this continent is fully owned by a player
	 */
	public boolean checkIfConquered () {
		if (countries.size() == 0) return false;
		String owner = countries.get(0).getOwner().getID();
		for (int i=1;i<countries.size();i++){
			if (!owner.equals(countries.get(i).getOwner().getID())) {
				return false;
			}
		}
		int flag = 0;
		for(int i = 0;i<countries.get(0).getOwner().getOwnContinent().size();i++) {
			if(countries.get(0).getOwner().getOwnContinent().get(i).getName().equals(name)) {
				flag = 1;
			}
		}
		if(flag==0) {
			countries.get(0).getOwner().addContinent(this);
		}
		return true;
	}
	
	/**
	 * During phase one, when a player owns all countries in this Continent, player will be rewarded
	 * an additional army equivalent to the continent's value
	 * @return Continent's value
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Getter for Continent name
	 * @return name as String
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter for Continent id 
	 * @return continent id as int
	 */
	public int getID() {
		return this.continentID;
	}
	
	/**
	 * Getter for list of countries
	 * @return vector of countries
	 */
	public Vector<Country> getContinentsCountries(){
		return this.countries;
	}
	
	/**
	 * Add a country to belong to this Continent
	 * @param c country to be added
	 */
	public void addToCountriesList(Country c) {
		countries.add(c);
	}
	
	/**
	 * Getter for the Continent's color
	 * @return color Color Object
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Print all countries included in this Continent
	 */
	public void printSelfCountries() {
		for(Country c:countries) {
			System.out.println(c.getName());
		}
	}
}
