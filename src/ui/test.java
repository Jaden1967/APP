package ui;

import java.util.regex.Pattern;

public class test {

	
	public static void main (String args[] ) {
		String pattern = "attack \\w*(\\-\\w+)* \\w*(\\-\\w+)* [1-3]( \\-allout)?|"
				+ "\\-noattack|defence [1-2]|attackmove [1-9][0-9]*";
		System.out.println(Pattern.matches(pattern, "attackmove 23"));
	}
}