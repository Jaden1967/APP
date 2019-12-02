package testing;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Ignore;
import org.junit.Test;

import entities.Continent;
import entities.Country;

/**
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class ContinentTest {
	
	Continent con = new Continent(1, "Asian", 5, null);
	Vector<Country> countries = new Vector<Country>();
	
	

	@Ignore 
	void testContinent() {
		fail("Not yet implemented");
	}

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

	@Ignore
	public void testAddToCountriesList() {
		Country c = new Country();
		con.addToCountriesList(c);
		
		assertEquals(c,countries.add(c));
	}

	@Ignore 
	void testUpdateOwner() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testGetColor() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testPrintSelfCountries() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testObject() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testGetClass() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testHashCode() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testEquals() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testClone() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testToString() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testNotify() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testWait() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testWaitLong() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Ignore 
	void testFinalize() {
		fail("Not yet implemented");
	}

}