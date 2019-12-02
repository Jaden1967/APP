package testing;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import controller.Controller;
import entities.Country;
import entities.GamePlay;
import entities.Player;

public class GamePlayTest {
	Controller control;
	String address;
	String command;
	boolean loadgame_success;
	Vector<Country> countries_list;
	Vector<Player> players_list;
	GamePlay game;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
//		address = "junit_reinforce";
		game = new GamePlay();
		control = new Controller(game);
		loadgame_success = control.loadFile(address);
		countries_list = game.getCountries();
		players_list = game.getPlayers();
		game.isTest();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Ignore
	public void testPopulateCountries() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testPlaceArmy() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testPlaceAll() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testStartUpNext() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testPhaseRecruit() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testReinforceArmy() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testCheckIfCanAttack() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testPhaseAttack() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testNoAttack() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testSetAttack() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testAllOutAttack() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testCommenceAttack() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetAttacker() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetDefender() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testAttackMove() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testReSetOwner() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testCheckWin() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testCheckIfCanFortify() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testPhaseFortify() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testFortify() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testNextPlayer() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetPhase() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testSetPhase() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetPlayerID() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetPlayer() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetPlayerString() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetArmyToPlace() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetAlertType() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetOutcome() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetContinents() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testSetContinents() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetCountries() {
		fail("Not yet implemented");
	}


}
