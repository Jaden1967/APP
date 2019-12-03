package testing;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Ignore;
import org.junit.Test;

import entities.Continent;
import entities.Country;

/**
 * One-to-one test class for Continent object
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class ContinentTest {
	
	Continent con = new Continent(1, "Asian", 5, null);
	Vector<Country> countries = con.getContinentsCountries();
	
	
	@Test
	public void testCheckIfConquered() {
		
		assertFalse(con.checkIfConquered()) ;
		
	}

	@Test
	public void testGetValue() {
		assertEquals(5,con.getValue());
	}

	@Test
	public void testGetName() {
		assertEquals("Asian",con.getName());
	}

	@Test
	public void testGetID() {
		assertEquals(1,con.getID());
	}

	@Test
	public void testAddToCountriesList() {
		Country c = new Country();
		int i = countries.size();
		con.addToCountriesList(c);
		assertEquals(i+1,countries.size());
	}


}