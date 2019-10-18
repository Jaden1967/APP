package testing;

import static org.junit.Assert.*;

import java.util.Vector;
import org.junit.Ignore;
import org.junit.Test;

import entities.Continent;
import entities.Player;

public class PlayerTest {
	
	Player pla = new Player("Jaden","red");
	int totalCountryNum = 9;
	
	Continent con = new Continent(1, "Asian", 5, null);
	Vector <Continent> ownedContinent = new Vector<Continent>();



	@Ignore
	public void testPlayer() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testPlayerStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateArmy() {
		assertEquals(3,pla.calculateArmy());
	}

	@Test
	public void testRewardArmy() {
		pla.rewardArmy(10);
		
		assertEquals(10,pla.getArmyToPlace());
	}

	@Test
	public void testInitializeStartupArmy() {
		pla.initializeStartupArmy(2);
		assertEquals(40,pla.getArmyToPlace());
	}

	@Test
	public void testRewardInitialArmy() {
		
		pla.addContinent(con);
		pla.extraArmyToAdd();
		pla.rewardInitialArmy();
		assertEquals(8,pla.getArmyToPlace());
	}

	@Test
	public void testGetArmyToPlace() {
		assertEquals(0,pla.getArmyToPlace());
	}

	@Ignore
	public void testDeployArmy() {
		fail("Not yet implemented");
	}

}
