package entities;

import java.util.Random;
import java.util.Vector;

import controller.Controller;

public class Tournament {
	private Vector<String> result = new Vector<>();
	private Vector<String> colorList = new Vector<String>();

	/**
	 * Contructed when the user inputs tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns
	 * in Initial.java UI
	 * @param mapList String array of maps in the form of "map1.map","luca.map"]
	 * @param strategyList Vector of strategies to be used in the form of ["aggressive","benevolent","random"]
	 * @param numGames Number of games to be played for each map
	 * @param numTurnsMax Number of turns before Game become a draw
	 */
	public Tournament(String[] mapList, String[] strategyList, int numGames, int numTurnsMax) {
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
	}
	
	
	
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
}
