package UI;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import Entities.*;

public class readMap {
	private static Vector<Continent> continentsList = new Vector<Continent>();
	private static Vector<Country> countriesList = new Vector<Country>();
	private static Vector<Player> playerList = new Vector<Player>();
	private static int x,y;
	
	private Color getColor(String color) {
        switch (color) {
            case "red": return Color.red;
            case "yellow": return Color.yellow;
            case "blue": return Color.blue;
            case "green": return Color.green;
            case "black": return Color.black;
            case "grey": return Color.gray;
            case "magenta": return Color.magenta;
            case "orange": return Color.orange;
            default: return Color.white;
        }
    }
	
	private void readFile(String address) {
		Vector <String> filesLoad = new Vector <String>();
		try {
			String pathname = address;
			File filename = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			do {
				line = br.readLine();
				if(line.contains("Dimensions")) {
					String[] tmp = line.split("\\s+");
					x = Integer.parseInt(tmp[2]);
					y = Integer.parseInt(tmp[4]);
				}
			}while(!line.trim().equals("[files]"));
			line = br.readLine();
			while(!line.trim().isEmpty()) {
				filesLoad.add(line);
				line = br.readLine();
			}
			do {
				line = br.readLine();
			}while(!line.trim().equals("[continents]"));
			line = br.readLine();
			int counter = 0;
			while(!line.trim().isEmpty()) {
				counter++;
				String[] tmp = line.split("\\s+");
				Continent c = new Continent(counter, tmp[0], Integer.parseInt(tmp[1]), getColor(tmp[2]));
				continentsList.add(c);
				line = br.readLine();
			}
			do {
				line = br.readLine();
			}while(!line.trim().equals("[countries]"));
			line = br.readLine();
			while(!line.trim().isEmpty()) {
				String[] tmp = line.split("\\s+");
				Country c = new Country(Integer.parseInt(tmp[0]), tmp[1], Integer.parseInt(tmp[3]), Integer.parseInt(tmp[4]));
				for(Continent con:continentsList) {
					if(con.getID()==Integer.parseInt(tmp[2])) {
						con.addToCountriesList(c);
						break;
					}
				}
				countriesList.add(c);
				line = br.readLine();
			}
			do {
				line = br.readLine();
			}while(!line.trim().equals("[borders]"));
			line = br.readLine();
			while(line!=null) {
				String[] tmp = line.split("\\s+");
				for(Country c:countriesList) {
					if(c.getID()==Integer.parseInt(tmp[0])) {
						for(int i = 1;i<tmp.length;i++) {
							Country tc = new Country();
							for(int j = 0;j<countriesList.size();j++) {
								if(countriesList.get(j).getID()==Integer.parseInt(tmp[i])) {
									tc = countriesList.get(j);
									break;
								}
							}
							c.addNeighbour(tc);
						}
					}
				}
				line = br.readLine();
			}
		}
		 catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	public static void main (String args[]) {
		
		readMap r = new readMap();
		r.readFile("luca.txt");
		/*for(Continent con:continentsList) {
			System.out.println(con.getName());
			con.printSelfCountries();
			System.out.println();
		}*/
		for(Country c:countriesList) {
			System.out.println(c.getName());
			c.printLinkedCountriesNum();
			System.out.println();
		}
	}
	
}
