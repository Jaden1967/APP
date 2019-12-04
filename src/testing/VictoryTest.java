package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import entities.Country;
import entities.GamePlay;
import entities.Player;

/**
 * Test to verify if the player has won
 * A custom editted map: map1 was used with the following relevant information:
 * player a controls 3 out of 4 countries, with China having 56 armies
 * player b controls 1 out of 4 countries, with Japan having 1 army
 * China neighbors Japan
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 *
 */
public class VictoryTest {

	Controller control;
	String address;
	String command;
	boolean loadgame_success;
	Vector<Country> countries_list;
	Vector<Player> players_list;
	GamePlay game;
	
	@Before
	public void setUp(){
		address = "junit_victory";
		game = new GamePlay();
		control = new Controller(game);
		loadgame_success = control.loadFile(address);
		countries_list = game.getCountries();
		players_list = game.getPlayers();
		game.isTest();
	}
	
	/**
	 * Test if the process of loading the game (along with the map) is successful
	 */
	@Test
	public void loadGame() {
		assertTrue(loadgame_success);
	}
	
	private Country getCountry(String id) {
		for (Country c:countries_list) {
			if(c.getName().equals(id)) return c;
		}
		return null;
	}
	
	private Player getPlayer (String id) {
		for (Player p: players_list) {
			if(p.getID().equals(id)) return p;
		}
		return null;
	}
	
	@Test
	public void victory() {
		assertEquals("Attack Phase 1", game.getPhase());
		command = "attack China Japan -allout";
		assertEquals("S",control.processInput(command)[0]);
		command = "attackmove 3";
		assertEquals("S",control.processInput(command)[0]);
		assertTrue(game.game_ended);		
	}
}
