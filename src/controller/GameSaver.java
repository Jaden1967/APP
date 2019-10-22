package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import entities.Country;
import entities.GamePlay;
import entities.Player;

public class GameSaver {
	public static void saveGame(String name ,GamePlay game, Vector<String> files) {
		File f = new File(name+".txt");
		try {
			PrintWriter pw = new PrintWriter (f);
			pw.append(savePlayers(pw,game.getPlayers()));
			pw.append(saveCountries(pw,game.getCountries()));
			pw.append(saveGameState(pw,game));
			
			
			
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Save state of all players as a line of string each
	 * Format: playerId playerColor playerArmyToPlace playerCountryNum
	 * @param pw
	 * @param players
	 * @return
	 */
	private static String savePlayers(PrintWriter pw, Vector<Player> players) {
		String s= "[Players]\n";
		for (Player p : players) {
			s += p.getID()+" ";
			s += p.getColorStr()+ " ";
			s += p.getArmyToPlace()+ "\n";
		}
		return s;
	}
	
	private static String saveCountries(PrintWriter pw, Vector<Country> countries) {
		String s = "[Countries]\n";
		
		
		return s;
	}
	
	private static String saveGameState(PrintWriter pw, GamePlay game) {
		String s = "[State]\n";
		
		return s;
	}
}
