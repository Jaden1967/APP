package testing;

import static org.junit.Assert.*;
import static org.junit.Assert.*;
import java.util.Vector;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import controller.Controller;
import entities.Continent;
import entities.Country;
import entities.GamePlay;
import entities.Player;
import java.util.Vector;

/**
 * Test operations on the FortifyTest
 * A game save file junit_fortification.save was pre-made with the following relevant data:
 * It is currently player a's Fortify phase (to issue command: fortify fromcountry tocountry numdice)
 * Alaska is assigned to Player a with army count 2
 * Ukraine is assigned to Player a with army count 1
 * Ural is assigned to Player b with army count 2
 * Alberta is assigned to Player a with army count 1
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */

public class FortifyTest {

	Controller control;
	String command;
	String address;
	Vector<Country> countries_list;
	Vector<Player> players_list;
	GamePlay game;
	boolean loadgame_success;

	@Before
	public void setUp() {
		game = new GamePlay();
		address = "junit_fortification";
		control = new Controller(game);
		countries_list = game.getCountries();
		players_list = game.getPlayers();
		loadgame_success = control.loadFile(address);
		game.isTest();
	}

	@Test
	public void fortifyNoLink() {

		// player cannot send army from Ukraine to Great-Britain
		// since there is no connection between them
		command = "fortify Ukraine Great-Britain 1";
		assertEquals("F", control.processInput(command)[0]);
	}

	@Test
	public void fortifyArmyOutOfRange() {
		// player cannot send army from Ukraine to Ural
		// since the player does not own enough armies
		command = "fortify Ukraine Ural 4";
		assertEquals("F", control.processInput(command)[0]);
	}

	@Test
	public void fortifyOwner() {
		// player cannot send army from Ukraine to Alaska
		// since the player do not own both of countries
		command = "fortify Ukraine Alaska  1";
		assertEquals("F", control.processInput(command)[0]);

	}

	@Test
	public void fortifyEmptyCountry() {
		// player cannot send army from Alaska to Alberta
		// since the player cannot leave an empty contry
		command = "fortify Alaska Alberta 2";
		assertEquals("F", control.processInput(command)[0]);
	}

	@Test
	public void fortifySuccessMove() {
		// player successfully send 1 army from Alaska to Alberta
		command = "fortify Alaska Alberta 1";
		assertEquals("S", control.processInput(command)[0]);

	}

	@Ignore
	public void fortifyChangePlayer() {
		// player choose does not sent any army 
		// change to another phase
		command = "fortify -none";
//		assertEquals("F", control.processInput(command)[0]);
		assertEquals("b", game.getPlayer().getID());

	}

}