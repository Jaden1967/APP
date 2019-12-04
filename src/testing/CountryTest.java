package testing;
import static org.junit.Assert.*;
import java.awt.Color;
import java.awt.Font;
import java.util.HashSet;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.Controller;
import entities.Continent;
import entities.Country;
import entities.GamePlay;
import entities.Player;
import ui.labels.CountryObsLabel;

/**
 * One to one test class for Country class object
 * Choose one country, Alberta, to test all class functions
 * Relevant information:
 * Alberta currently has 1 army and belongs to Player a
 * Alaska,North-West-Territory,Ontario,Western-United-States  all neighbor Alberta, North-West-Territory belongs to enemy Player c
 * Alberta belongs to the continent North-America
 * Alberta has id 2,  Ontario has id 6
 * 
 * @author Yuhua Jiang 40083453
 * @author Boxiao Yu 40070128
 *
 */
public class CountryTest {
	Controller control;
	String address;
	String command;
	boolean loadgame_success;
	Vector<Country> countries_list;
	Vector<Player> players_list;
	GamePlay game;
	Country alberta;

	@Before
	public void setUp() throws Exception {
		address = "junit_attack";
		game = new GamePlay();
		control = new Controller(game);
		loadgame_success = control.loadFile(address);
		countries_list = game.getCountries();
		players_list = game.getPlayers();
		alberta = game.getCountries().get(2); //Alberta is country index 10
		game.isTest();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testSetOwner() {
		Color co = Color.red;
		Player plann = new Player("newone",co,game);
//		System.out.print(plann.getID());
//		System.out.print(country.getOwner().getColor());
		alberta.setOwner(plann);
		assertEquals(plann.getID(),alberta.getOwner().getID());
	}

	@Test
	public void testAddNeighbour() {
		Country neighbour1 = new Country();
		int neighbornum = alberta.getNeighbors().size();
		alberta.addNeighbour(neighbour1);
		assertEquals(neighbornum+1,alberta.getNeighbors().size());
	}

	@Test
	public void testHasNeighbour() {
		assertTrue(alberta.hasNeighbour("Ontario"));
		assertFalse(alberta.hasNeighbour("Quebec"));
	}

	@Test
	public void testHasEnemyNeighbour() {
		assertTrue(alberta.hasEnemyNeighbour());
	}

	@Test
	public void testGetOneEnemyNeighbor() {
		assertEquals("North-West-Territory",alberta.getOneEnemyNeighbor().getName());
	}

	@Test
	public void testHasPathTo() {
		assertTrue(alberta.hasPathTo("Quebec", "a", new HashSet<String>()));
		assertFalse(alberta.hasPathTo("China", "a", new HashSet<String>()));
	}

	@Test
	public void testGetLinkCountries() {
		HashSet<String> linked = new HashSet<>();
		linked.add("Alaska");
		linked.add("Ontario");
		linked.add("Quebec");
		linked.add("Western-United-States");
		linked.add("Alberta");
		HashSet<Country> visited = alberta.getLinkCountries(alberta.getOwner().getID(), new HashSet<>());
		for(Country c: visited) {
			assertTrue(linked.contains(c.getName()));
		}
	}

	@Test
	public void testAddArmy() {
		alberta.addArmy(10);
		assertEquals(11,alberta.getArmyNum());
	}

	@Test
	public void testRemoveArmy() {
		alberta.addArmy(30);
		alberta.removeArmy(15);
		assertEquals(16,alberta.getArmyNum());
	}

	@Test
	public void testMoveArmy() {
		alberta.addArmy(10);
		//moving 5 army to Ontario
		alberta.moveArmy(7, 5);
		assertEquals(6,alberta.getArmyNum());
		assertEquals(9,game.getCountries().get(6).getArmyNum());
	}

	@Test
	public void testGetID() {
		assertEquals(3,alberta.getID());
	}

	@Test
	public void testGetArmyNum() {
		assertEquals(1,alberta.getArmyNum());
	}

	@Test
	public void testSetArmy() {
		alberta.setArmy(30);
		assertEquals(30,alberta.getArmyNum());
	}

	@Test
	public void testGetName() {
		assertEquals("Alberta",alberta.getName());
	}

	@Test
	public void testGetContinent() {
		assertEquals("North-America",alberta.getContinent().getName());
	}

	@Test
	public void testSetContinent() {
		Continent newCont = new Continent(7,"newCont",10,Color.red);
		alberta.setContinent(newCont);
		assertEquals("newCont",alberta.getContinent().getName());
	}

	@Test
	public void testGetNeighbors() {
		HashSet<String> neighbors = new HashSet<>();
		neighbors.add("Alaska");
		neighbors.add("North-West-Territory");
		neighbors.add("Ontario");
		neighbors.add("Western-United-States");
		for(Country c: alberta.getNeighbors()) {
			assertTrue(neighbors.contains(c.getName()));
		}

	}


}
