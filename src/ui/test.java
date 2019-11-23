package ui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;

import entities.Country;
import entities.GamePlay;

import java.text.SimpleDateFormat;
/**
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class test extends Observable{
	
	
	public static void main (String args[] ) {
		Vector<Country> countries = new Vector<>();
		for (int i=0;i<9;i++) {
			countries.add(new Country());
		}
		Random rand = new Random();
		for (int i=0;i<9;i++) {
			countries.get(i).setArmy(rand.nextInt(50));
			System.out.print(countries.get(i).getArmyNum()+ " ");
		}
		System.out.println();
		
		//need to create public Country comparator to test this 
		
		// Collections.sort(countries,new CountryComparator());
		
		for (Country c: countries) {
			System.out.print(c.getArmyNum()+ " ");

		}
		
	}
}