package testing;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import entities.Continent;
import entities.Country;
import entities.Player;

/**
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class LoadmapTest {
	
	private Vector<Continent> continentsList = new Vector<Continent>();
	private Vector<Country> countriesList = new Vector<Country>();
	private Vector<Player> playerList = new Vector<Player>();
	private Vector <String> filesLoad = new Vector <String>();
	private Vector<String> colorList = new Vector<String>();
	int x,y;
	String address;
	
	private Color getColor(String color) {
        switch (color) {
            case "red": return Color.red;
            case "yellow": return Color.yellow;
            case "blue": return Color.blue;
            case "green": return Color.green;
            case "lightyellow": return new Color(107,142,35);
            case "grey": return Color.gray;
            case "magenta": return Color.magenta;
            case "orange": return Color.orange;
            case "pink": return Color.pink;
            case "cyan": return Color.cyan;
            case "DeepPink": return new Color(255,20,147);
            case "skyblue": return new Color(176, 196, 222);
            case "white": return Color.white;
            case "purple": return new Color(128, 0, 128);
            default: return Color.white;
        }
    }
	
	public boolean readFile(String address) {
		try {
			File file = new File(address);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
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
				int flag = 0;
				String[] tmp = line.split("\\s+");
				Country c = new Country(Integer.parseInt(tmp[0]), tmp[1], Integer.parseInt(tmp[3]), Integer.parseInt(tmp[4]), x, y, 940, 585);
				for(Continent con:continentsList) {
					if(con.getID()==Integer.parseInt(tmp[2])) {
						con.addToCountriesList(c);
						c.setContinent(con);
						flag = 1;
						break;
					}
				}
				if(flag == 0) {
					return false;
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
				int flag = 0;
				for(Country c:countriesList) {
					if(c.getID()==Integer.parseInt(tmp[0])) {
						flag = 1;
						for(int i = 1;i<tmp.length;i++) {
							Country tc = new Country();
							for(int j = 0;j<countriesList.size();j++) {
								if(countriesList.get(j).getID()==Integer.parseInt(tmp[i])) {
									tc = countriesList.get(j);
									break;
								}
							}
							if(tc.getLabel().equals("")) {
								return false;
							}
							c.addNeighbour(tc);
						}
					}
				}
				if(flag==0) {
					return false;
				}
				line = br.readLine();
			}
			return true;
		}
		 catch (Exception e) {
				e.printStackTrace();
				return false;
		}
	}
	
	public void visit (Country curr,HashSet<String> visited) {
		if(visited.contains(curr.getName())) return;
		visited.add(curr.getName());
		for(Country c: curr.getNeighbors()) {
			visit(c,visited);
		}
	}
	
	public void visitInContinent(Country curr,HashSet<String> visited) {
		if(visited.contains(curr.getName())) return;
		visited.add(curr.getName());
		for (Country c: curr.getNeighbors()) {
			if(c.getContinent().getName().equals(curr.getContinent().getName())) {
				visitInContinent(c,visited);
			}	
		}

	}
	
	@Before
	public void load() {
		address = "map\\luca.map";
		readFile(address);
		for (Country c:countriesList) {
			c.addObserver(c.getLabel());
		}
	}
	
	@Test
	public void loadSuccess () {
		assertTrue(readFile(address));
	}
	
	@Test
	public void initializeCountry() {
		assertEquals("Alaska",countriesList.get(0).getName());
		assertEquals("Eastern-Australia",countriesList.get(countriesList.size()-1).getName());
		assertEquals("North-America",countriesList.get(0).getContinent().getName());
		assertEquals(42, countriesList.size());
	}
	
	@Test
	public void initializeContinent() {
		assertEquals("North-America",continentsList.get(0).getName());
		assertEquals("Australia",continentsList.get(continentsList.size()-1).getName());
		assertEquals(getColor("yellow"),continentsList.get(0).getColor());
		assertEquals(5,continentsList.get(0).getValue());
	}
	
	@Test
	public void setNeighbours() {
		assertEquals(3, countriesList.get(0).getNeighbors().size());
		assertEquals("North-West-Territory",countriesList.get(0).getNeighbors().get(0).getName());
	}
	
	@Test
	public void hasPathTo() {
		Player p = new Player ("a", getColor("blue"), null);
		HashSet<String> visited = new HashSet<>();
		countriesList.get(0).setOwner(p);
		countriesList.get(2).setOwner(p);
		countriesList.get(6).setOwner(p);
		countriesList.get(7).setOwner(p);

		assertEquals(true,countriesList.get(0).hasPathTo("Quebec", "a", visited));
	}
	
	@Test 
	public void noPathTo() {
		Player p = new Player ("a",getColor("blue"), null);
		HashSet<String> visited = new HashSet<>();
		countriesList.get(0).setOwner(p);
		countriesList.get(7).setOwner(p);
		assertEquals(false,countriesList.get(0).hasPathTo("Quebec", "a", visited));

	}
	
	@Test
	public void isConnectedWorld() {
		HashSet<String> visited = new HashSet<>();
		visit(countriesList.get(0),visited);
		assertEquals(countriesList.size(),visited.size());
	}
	
	@Test
	public void isConnectedContinent() {
		HashSet<String> visited = new HashSet<>();
		visitInContinent(countriesList.get(0),visited);
		assertEquals(9,visited.size());
	}
	
	@Test
	public void observerChange() {
		Player a = new Player ("a",getColor("blue"), null);
		Player b = new Player ("b",getColor("green"), null);
		Country c = countriesList.get(0);
		c.setOwner(a);
		assertEquals(a.getColor(),c.getLabel().getBackground());
		c.setOwner(b);
		assertNotEquals(a.getColor(),c.getLabel().getBackground());
		assertEquals(b.getColor(),c.getLabel().getBackground());

	}
	
}
