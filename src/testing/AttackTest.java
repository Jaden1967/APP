package testing;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import entities.Country;
import entities.GamePlay;
import entities.Player;

/**
 * Test operations on the Attack phase
 * A game save file junit_attack.save was pre-made with the following relevant data:
 * It is currently player a's Attack Phase 1 (to issue command: attack fromcountry tocountry numdice)
 * Peru is assigned to Player a with army count 30
 * Venezuela is assigned to Player a with army count 2
 * Brazil is assigned to Player c with army count 1
 * Argentina is assigned to Player b with army count 3
 * Peru is neighbor to the three countries above
 * Central-America is assigned to Player c with army count 3
 * Central-America does not neighbor Peru, but neighbors Venezuela and Western-United-States
 * Western-United-States is assigned to Player a with army count 1
 */
public class AttackTest {
	Controller control;
	String address;
	String command;
	boolean loadgame_success;
	Vector<Country> countries_list;
	Vector<Player> players_list;
	GamePlay game;
	
	@Before
	public void setUp(){
		address = "junit_attack";
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
	public void attackOnce() {
		//player a cannot issue attack on its own country
		command = "attack Peru Venezuela 3";
		assertEquals("F",control.processInput(command)[0]);
		//player a cannot attack with a country that is not owned by himself
		command = "attack Argentina Brazil 2";
		assertEquals("F",control.processInput(command)[0]);
		//player a cannot issue an attack if fromcountry and tocountry are not neighbors
		command = "attack Peru Central-America 3";
		assertEquals("F",control.processInput(command)[0]);
		//player a cannot roll 2 dice if there are only 2 army count in the attacking country (Venezuela)
		command = "attack Venezuela Brazil 2";
		assertEquals("F",control.processInput(command)[0]);
		//player a cannot attack with a country that only has an army count of 1 (Western-United-States)
		command = "attack Western-United-States Central-America 1";
		assertEquals("F",control.processInput(command)[0]);
		//player a can issue a command from Peru to Argentina (owned by player b) with 3 dice
		command = "attack Peru Argentina 3";
		assertEquals("S",control.processInput(command)[0]);
		//after successfully issuing an attack command, it is now attack phase 2
		assertEquals("Attack Phase 2",game.getPhase());
		//player cannot issue another attack command in attack phase 2
		command = "attack Venezuela Brazil 1";
		assertEquals("F",control.processInput(command)[0]);
		
		int a_count = game.getAttacker().getArmyNum();
		int b_count = game.getDefender().getArmyNum();
		//player b cannot roll more than 2 dice while defending
		command = "defend 3";
		assertEquals("F",control.processInput(command)[0]);
		command = "defend 2";
		assertEquals("S",control.processInput(command)[0]);
		//there will always be 2 armies lost in total if at least 2 dice is rolled on both sides
		assertEquals(2,(a_count-game.getAttacker().getArmyNum())+b_count-game.getDefender().getArmyNum());
		//since Argentina would still have at least 1 army left after the one-round attack, it is back to player a's Attack Phase 1
		assertEquals("a",game.getPlayer().getID());
		assertEquals("Attack Phase 1",game.getPhase());
	}
	
	/**
	 * attack fromcountry tocountry -allout has similar contraints as the attack once command
	 * this test method asserts that after an allout attack with an almost certain victory (30 vs 1),
	 * player a's Peru would have successfully reduced player c's Brazil,
	 * thus advancing the phase to Attack Phase 3, where player a moves a certain amount of army to 
	 * the conquered Brazil
	 */
	@Test
	public void attackAllout() {
		//attack from Peru, with army number 30, to Brazil, with army number 1
		getCountry("Peru").setArmy(30);
		command = "attack Peru Brazil -allout";
		assertEquals("S",control.processInput(command)[0]);
		//after successfully conquering Brazil, the game is now Attack Phase 3
		assertEquals("Attack Phase 3",game.getPhase());
		//since player a would have rolled 3 dice last turn to win the battle, he cannot place less than that amount
		//to move in the newly conquered city
		command = "attackmove 2";
		assertEquals("F",control.processInput(command)[0]);
		command = "attackmove 3";
		assertEquals("S",control.processInput(command)[0]);
		//Brazil now belongs to player a and has an army number of 3
		assertEquals("a",getCountry("Brazil").getOwner().getID());
		assertEquals(3,getCountry("Brazil").getArmyNum());
		//It is now Attack Phase 1 and Player a can attack again
		assertEquals("Attack Phase 1",game.getPhase());
		//Player a is rewarded a random Card for successfully conquering a new country during the attack phase
		assertEquals(1,game.getPlayer().getOwnCard().size());
	}

}
