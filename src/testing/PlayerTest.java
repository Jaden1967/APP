package testing;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Vector;
import org.junit.Ignore;
import org.junit.Test;

import entities.Continent;
import entities.Player;

public class PlayerTest {
	
	Player pla = new Player("Jaden",getColor("red"));
	int totalCountryNum = 9;
	
	Continent con = new Continent(1, "Asian", 5, null);
	Vector <Continent> ownedContinent = new Vector<Continent>();


	private Color getColor(String color) {
        switch (color) {
            case "red": return Color.red;
            case "yellow": return Color.yellow;
            case "blue": return Color.blue;
            case "green": return Color.green;
            case "lightyellow": return new Color(107,142,35);
            case "grey": return Color.gray;
            case "magenta": return Color.magenta;
            case "orange": return Color.orange;
            case "pink": return Color.pink;
            case "cyan": return Color.cyan;
            case "DeepPink": return new Color(255,20,147);
            case "skyblue": return new Color(176, 196, 222);
            case "white": return Color.white;
            case "purple": return new Color(128, 0, 128);
            default: return Color.white;
        }
    }

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
