package testing;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Ignore;
import org.junit.Test;

import entities.Continent;
import entities.Country;

class ContinentTest {
	
	Continent con = new Continent(1, "Asian", 5, null);
	Vector<Country> countries = new Vector<Country>();
	
	

	@Ignore 
	void testContinent() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckIfConquered() {
		
		assertFalse(con.checkIfConquered()) ;
		
	}

	@Test
	void testGetValue() {
		assertEquals(5,con.getValue());
	}

	@Test
	void testGetName() {
		assertEquals("Asian",con.getName());
	}

	@Test
	void testGetID() {
		assertEquals(1,con.getID());
	}

	@Test
	void testAddToCountriesList() {
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