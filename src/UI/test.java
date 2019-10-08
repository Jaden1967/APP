package UI;
import java.util.regex.*;

public class test {
	public static void main (String args[] ) {
		String isCommandPattern = "-(add|remove)\\s\\w*";
		String str = "-remove go1b_js";
		System.out.println(Pattern.matches(isCommandPattern, str));
	}
}
