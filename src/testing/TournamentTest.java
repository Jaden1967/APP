package testing;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import entities.GamePlay;

public class TournamentTest {

	private Vector<String> result = new Vector<>();
	private Vector<String> colorList = new Vector<String>();
	String[] mapList;
	String[] strategyList;
	int numGames;
	int numTurnsMax;
	
	
	/**
	 * Initial list of colors (string)
	 */
	private void initializeColors() {
		colorList.add("pink");
		colorList.add("cyan");
		colorList.add("DeepPink");
		colorList.add("skyblue");
		colorList.add("lightyellow");
		colorList.add("grey");
		colorList.add("white");
		colorList.add("purple");
	}
	
	/**
	 * Get random color from List colorList
	 * @return color as Color object
	 */
	private String getRandColor () {
        int c = new Random().nextInt(colorList.size());
        String color = colorList.get(c);
        colorList.remove(c);
        return color;
    }
	
	@Before
	public void setUp() {
		mapList = new String [] {"luca.map","risk.map","board.map"};
		strategyList = new String [] {"aggressive","benevolent"};
		numGames = 3;
		numTurnsMax = 40;
	}
	
	@Test
	public void testAggresiveVsBenevolent() {
		Vector<String[]> player_str_list = new Vector<>();
		  int r = 0, c = 0;
		  initializeColors();
		  int pnum= 1;
		  //initialize player list for all strategies
		  for (String s: strategyList) {
		   //String [] format: name, color(string), strategy-substring(1 letter)
		   //use ai strategy as its name
		   player_str_list.add(new String[] {s+" "+pnum,getRandColor(),s.substring(0,1)});
		   pnum++;
		  }
		Vector<String> printedResult = new Vector<>();

		//for each map in map List
		for (String m: mapList) {
			String map = m;
			for (int i=0; i<numGames;i++) {
				result = new Vector<>();
				System.out.println("On map "+m+", Game: "+(i+1));
				GamePlay game = new GamePlay();
				Controller control = new Controller (game);
				control.loadMap(map);
				control.addPlayers(player_str_list);
				game.isTournament(numTurnsMax);
				control.startGame(result);
				System.out.println("result size "+result.size());
				printedResult.add(result.get(0));
			}
		}
		
		int result_index=0;
		for (String m: mapList) {
			System.out.println("\nMap: "+m);
			for (int g=1;g<=numGames;g++) {
				System.out.print(String.format("%-6s: %-20s", "Game"+g,printedResult.get(result_index))+"");
				result_index++;
			}
		}
		//3 maps, 3 games each will yield 9 results
		assertEquals(9,printedResult.size());
		for(String res :printedResult) {
			//for games with maximum turns 40, an aggressive ai will always win
			assertEquals("aggressive",res.split("\\s+")[0]);
		}
	}
	
	@Test
	public void testCheater() {
		strategyList = new String [] {"cheater","aggressive","aggressive","random"};
		numTurnsMax = 15;
		
		Vector<String[]> player_str_list = new Vector<>();
		  int r = 0, c = 0;
		  initializeColors();
		  int pnum= 1;
		  //initialize player list for all strategies
		  for (String s: strategyList) {
		   //String [] format: name, color(string), strategy-substring(1 letter)
		   //use ai strategy as its name
		   player_str_list.add(new String[] {s+" "+pnum,getRandColor(),s.substring(0,1)});
		   pnum++;
		  }
		Vector<String> printedResult = new Vector<>();

		//for each map in map List
		for (String m: mapList) {
			String map = m;
			for (int i=0; i<numGames;i++) {
				result = new Vector<>();
				System.out.println("On map "+m+", Game: "+(i+1));
				GamePlay game = new GamePlay();
				Controller control = new Controller (game);
				control.loadMap(map);
				control.addPlayers(player_str_list);
				game.isTournament(numTurnsMax);
				control.startGame(result);
				System.out.println("result size "+result.size());
				printedResult.add(result.get(0));
			}
		}
		
		int result_index=0;
		for (String m: mapList) {
			System.out.println("\nMap: "+m);
			for (int g=1;g<=numGames;g++) {
				System.out.print(String.format("%-6s: %-20s", "Game"+g,printedResult.get(result_index))+"");
				result_index++;
			}
		}
		//3 maps, 3 games each will yield 9 results
		assertEquals(9,printedResult.size());
		
		int cheaterwin = 0;
		for(String res :printedResult) {
			if(res.split("\\s+")[0].equals("cheater")) cheaterwin++;
		}
		//for games with at least 10 turns max, cheater ai will always win on most maps
		assertTrue(cheaterwin>6);
	}
	
	
	@Test
	public void testDraw() {
		numTurnsMax = 3;

		Vector<String[]> player_str_list = new Vector<>();
		  int r = 0, c = 0;
		  initializeColors();
		  int pnum= 1;
		  //initialize player list for all strategies
		  for (String s: strategyList) {
		   //String [] format: name, color(string), strategy-substring(1 letter)
		   //use ai strategy as its name
		   player_str_list.add(new String[] {s+" "+pnum,getRandColor(),s.substring(0,1)});
		   pnum++;
		  }
		Vector<String> printedResult = new Vector<>();

		//for each map in map List
		for (String m: mapList) {
			String map = m;
			for (int i=0; i<numGames;i++) {
				result = new Vector<>();
				System.out.println("On map "+m+", Game: "+(i+1));
				GamePlay game = new GamePlay();
				Controller control = new Controller (game);
				control.loadMap(map);
				control.addPlayers(player_str_list);
				game.isTournament(numTurnsMax);
				control.startGame(result);
				System.out.println("result size "+result.size());
				printedResult.add(result.get(0));
			}
		}
		
		int result_index=0;
		for (String m: mapList) {
			System.out.println("\nMap: "+m);
			for (int g=1;g<=numGames;g++) {
				System.out.print(String.format("%-6s: %-20s", "Game"+g,printedResult.get(result_index))+"");
				result_index++;
			}
		}
		//3 maps, 3 games each will yield 9 results
		assertEquals(9,printedResult.size());
		for(String res :printedResult) {
			//for games with maximum turns 3, a game will almost always result in a draw
			assertEquals("draw",res.split("\\s+")[0]);
		}
	}
	

}
