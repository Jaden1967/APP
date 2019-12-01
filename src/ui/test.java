package ui;

import java.util.regex.Pattern;

/**
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class test{
	
	
	public static void main (String args[] ) {
		String isCommandPattern = "(placearmy \\w*(\\-\\w+)*|placeall|"
				+ "reinforce \\w*(\\-\\w+)* [1-9][0-9]*|"
				+ "fortify (\\w*(\\-\\w+)* \\w*(\\-\\w+)* [1-9][0-9]*|\\-none))|"
				+ "cheat|trade|view|"
				+ "attack \\w*(\\-\\w+)* \\w*(\\-\\w+)* ([1-3]|\\-allout)|"
				+ "\\-noattack|defend [1-2]|attackmove [1-9][0-9]*|"
				+ "tournament \\-M \\[((\\w)*\\.map)(,(\\w)*\\.map)*\\] -P \\[(aggressive|benevolent|cheater|random)(,(aggressive|benevolent|cheater|random))*\\] -G [1-9] -D ([1-9]|[1-8][0-9]|9[0-9]|100)";
		String t = "tournament \\-M \\[((\\w)*\\.map)(,(\\w)*\\.map)*\\] -P \\[(aggressive|benevolent|cheater|random)(,(aggressive|benevolent|cheater|random))*\\] -G [1-9] -D ([1-9]|[1-8][0-9]|9[0-9]|100)";
		
		String test = "tournament -M [luca_asdf.map,test1.map] -P [aggressive,benevolent,aggressive] -G 6 -D 3";
		System.out.println(Pattern.matches(t, test));
		
	}
}