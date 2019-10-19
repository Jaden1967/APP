package ui;
import java.util.Random;
import java.util.regex.*;

public class test {
	public static String isCommandPattern=("placearmy \\w*|placeall|reinforce \\w* [1-9][0-9]*|fortify (\\w* \\w* [1-9][0-9]*|none)");
	public static void main (String args[] ) {
		System.out.println(Pattern.matches(isCommandPattern,"fortify none node 8"));
	}
}