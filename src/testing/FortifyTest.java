package testing;

import static org.junit.Assert.*;
import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Ignore;
import org.junit.Test;

import entities.Continent;
import entities.Country;

import java.util.Vector;

public class FortifyTest {
	
	Controller control;
	String command;
	Vector<Country> countries_list;
	Vector<Player> players_list;
	GamePlay game;
	
	@Before
	public void setUp(){
		game = new GamePlay();
		control = new Controller(game);
		countries_list = game.getCountries();
		players_list = game.getPlayers();
		
	}
	
	@Test
	public void attackOnce() {
		command = "fortify Peru Venezuela 3";
		assertEquals("F",control.processInput(command)[0]);
		command = "fortify Argentina Brazil 2";
		assertEquals("F",control.processInput(command)[0]);
		command = "fortify Peru Central-America 3";
		assertEquals("F",control.processInput(command)[0]);
		command = "fortify Venezuela Brazil 2";
		assertEquals("F",control.processInput(command)[0]);

		
	}

}