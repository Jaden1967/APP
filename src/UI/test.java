package UI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.*;

public class test {
	public static void main (String args[] ) {
		String isCommandPattern = "-(add|remove)\\s\\w*";
		String str = "-remove go1b_js";
		//System.out.println(Pattern.matches(isCommandPattern, str));
		
		String lucaTxtLoc = "C:\\Users\\Allan Yu\\Desktop\\luca.txt";
		ArrayList <String[]> filesLoad = new  ArrayList<>();
		ArrayList <String[]> continentsLoad = new ArrayList<>();
		ArrayList <String[]> countriesLoad = new ArrayList<>();
		ArrayList <int[]> bordersLoad = new ArrayList<>();
		try {
			Scanner sc = new Scanner (new File(lucaTxtLoc));
			String curr;
			
			//Load [files] contents
			do {
				curr = sc.nextLine();
			}while(!curr.trim().equals("[files]"));
			curr = sc.nextLine();
			while(!curr.trim().isEmpty()) {
				filesLoad.add(curr.split("\\s+"));
				curr = sc.nextLine();
			}
			
			//Load [continents] contents
			do {
				curr = sc.nextLine();
			}while(!curr.trim().equals("[continents]"));
			curr = sc.nextLine();
			while(!curr.trim().isEmpty()) {
				continentsLoad.add(curr.split("\\s+"));
				curr = sc.nextLine();
			}
			
			//Load [countries] contents
			do {
				curr = sc.nextLine();
			}while(!curr.trim().equals("[countries]"));
			curr = sc.nextLine();
			while(!curr.trim().isEmpty()) {
				countriesLoad.add(curr.split("\\s+"));
				curr = sc.nextLine();
			}
			
			//Load [borders] contents
			do {
				curr = sc.nextLine();
			}while(!curr.trim().equals("[borders]"));
			curr = sc.nextLine();
			do {
				bordersLoad.add(Arrays.stream(curr.split("\\s+")).mapToInt(Integer::parseInt).toArray());
				curr = sc.nextLine();
			}while(sc.hasNextLine());
			bordersLoad.add(Arrays.stream(curr.split("\\s+")).mapToInt(Integer::parseInt).toArray());
		
			
			System.out.println(bordersLoad.size());
			
			
			sc.close();
		}catch(IOException e) {}
		
	}
}
