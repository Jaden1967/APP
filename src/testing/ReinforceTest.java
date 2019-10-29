package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import entities.Country;
import entities.GamePlay;
import entities.Player;

/**
 * Test operations on the Reinforcement phase
 * A game save file junit_reinforce.save was pre-made with the following relevant data:
 * Players a,b,c all have randomly placed armies on the map on the startup phase
 * It is currently player a's turn
 * Player a has 4 armies to place
 * Japan is assigned to Player a with army count 3
 * Siam is assigned to Player b with army count 3
 * Ural is assigned to Player b with army count 2
 * China is assigned to Player c with army count 1
 */
public class ReinforceTest {
	Controller control;
	String address;
	String command;
	boolean loadgame_success;
	Vector<Country> countries_list;
	Vector<Player> players_list;
	GamePlay game;
	
	@Before
	public void setUp(){
		address = "junit_reinforce";
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
	
	/**
	 * Test if the process of loading the game (along with the map) is successful
	 */
	@Test
	public void loadGame() {
		assertTrue(loadgame_success);
	}
	
	@Test
	public void reinforceCountry() {
		command = "reinforce Japan 4";
		//player a can successfully place 4 armies on its owned country: Japan
		assertEquals("S",control.processInput(command)[0]);
		//after placing 4 armies on Japan, Japan now has 7 armies
		assertEquals(7, getCountry("Japan").getArmyNum());
		//player a now has 0 armies to place
		assertEquals(0,getPlayer("a").getArmyToPlace());
		//after player a's reinforcement phase has ended, it is now player a's attack phase
		assertEquals("Attack Phase 1",game.getPhase());
		//end player a's turn by not attacking and not fortifying
		control.processInput("-noattack");
		control.processInput("fortify -none");
		//it is now player b's turn, b has been rewarded with its initial army to reinforce (4)
		assertEquals("b",game.getPlayer().getID());
		assertEquals(4,getPlayer("b").getArmyToPlace());
		//player b cannot reinforce armies on Japan, which is owned by player a
		assertEquals("F",control.processInput(command)[0]);
		//after failing to reinforce Japan, player b can reinforce 2 armies to its owned country Siam, which now has 5 armies
		command = "reinforce Siam 2";
		assertEquals("S",control.processInput(command)[0]);
		assertEquals(5,getCountry("Siam").getArmyNum());
		//it is still player b's turn since there are still 2 army number left to reinforce to b's countries
		assertEquals("b",game.getPlayer().getID());
		assertEquals(2,getPlayer("b").getArmyToPlace());
		//player b can place 2 armies that was left on Ural
		command = "reinforce Ural 2";
		assertEquals("S",control.processInput(command)[0]);
		assertEquals(4,getCountry("Ural").getArmyNum());
		//it is now player b's attack phase
		assertEquals("b",game.getPlayer().getID());
		assertEquals("Attack Phase 1",game.getPhase());

	}
	
	@After
	public void dispose() {
		control = null;
		game.clearData();
		
	}
}
