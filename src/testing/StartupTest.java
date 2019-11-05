package testing;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import entities.Country;
import entities.GamePlay;
import entities.Player;

/**
 * Test operations on the startup phase
 * A game save file junit_startup.save was pre-made with the following relevant data:
 * Players a,b,c all have 21 armies to place in the starting phase
 * It is currently player a's turn
 * All countries have been randomly assigned to each player evenly
 * Japan is assigned to Player a
 * Siam is assigned to Player b
 * China is assigned to Player c
 * 
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class StartupTest {
	Controller control;
	String address;
	String command;
	boolean loadgame_success;
	Vector<Country> countries_list;
	Vector<Player> players_list;
	GamePlay game;
	
	@Before
	public void setUp(){
		address = "junit_startup";
		game = new GamePlay();
		control = new Controller(game);
		loadgame_success = control.loadFile(address);
		countries_list = game.getCountries();
		players_list = game.getPlayers();
		game.isTest();
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
	
	private int allCountryArmyCount() {
		int count = 0;
		for(Country c:countries_list) {
			count+= c.getArmyNum();
		}
		return count;
	}

	/**
	 * Test if the process of loading the game (along with the map) is successful
	 */
	@Test
	public void loadGame() {
		assertTrue(loadgame_success);
	}
	
	/**
	 * Test operations of the command "placearmy country" for player a and player b
	 */
	@Test
	public void placeArmy() {
		//player cannot place army on a country that doesn't exist
		command = "placearmy Nullcountry";
		assertEquals("F",control.processInput(command)[0]);
		//inputting the command for player a to place army on its owned country Japan is successful
		command = "placearmy Japan";
		assertEquals("S",control.processInput(command)[0]);
		//after the command, Japan has incremented its army value
		assertEquals(2,getCountry("Japan").getArmyNum());
		//Player a now has 20 armies to place before Recruitment phase
		assertEquals(20,getPlayer("a").getArmyToPlace());
		//it is now Player b's turn to place an army
		assertEquals("b",game.getPlayer().getID());
		//Player b cannot place an army on Japan, which is owned by player a
		assertEquals("F",control.processInput(command)[0]);
		//after the failed input, it is still player b's turn to place, and that it still has 21 armies to place
		assertEquals("b",game.getPlayer().getID());
		assertEquals(21,getPlayer("b").getArmyToPlace());
		command = "placearmy Siam";
		//place an army on Player b's owned country China is successful
		assertEquals("S",control.processInput(command)[0]);
	}
	
	/**
	 * Test operations of the command "placeall" is successfull
	 * All players should have all their armies to place reduced to 0 after placing armies randomly on the map
	 */
	@Test 
	public void placeAll() {
		command = "placeall";
		//inputting the placeall command is processed
		assertEquals("S",control.processInput(command)[0]);
		//it is now back to the first player: Player a's turn
		assertEquals("a",game.getPlayer().getID());
		//it is now Reinforcement Phase since all startup armies have been placed
		assertEquals("Reinforcement Phase",game.getPhase());
		//Player a has been rewarded 4 armies as the initial reward from Reinforcement phase, 
		//while b and c have 0 armies to place before their own Reinforcement phase
		assertEquals(4,getPlayer("a").getArmyToPlace());
		assertEquals(0,getPlayer("b").getArmyToPlace());
		assertEquals(0,getPlayer("c").getArmyToPlace());
		//the cumulative army count for all countries should be 42(initial) + 21 * 3 (each player) = 105 after placeall command
		assertEquals(105,allCountryArmyCount());
	}
	
	@After
	public void dispose() {
		control = null;
		game.clearData();
		
	}

}
