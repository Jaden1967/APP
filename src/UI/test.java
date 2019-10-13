package UI;
import java.util.Random;
import java.util.regex.*;

public class test {
	public static void main (String args[] ) {
		int a = 585;
		int b = 425;
		System.out.println((float)(a/b));
		
		Random rand = new Random() ;
			
		for (int i=0;i<30;i++) {
			System.out.println(rand.nextInt(1/1 -1)+1);
		}
	}
}
