package controller;

import entities.*;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Observer;
import java.util.Vector;

import javax.imageio.ImageIO;

import java.util.Date;
import java.text.SimpleDateFormat;


public class Controller {
	GamePlay game;
	Vector <String> files_load = new Vector <String>();
	String map_file_name = "";

	public Controller(GamePlay game) {
		this.game = game;
	}
	
	public Controller() {
		
	}
	
	public void startGame() {
		game.populateCountries();
	}
	
	/**
	 * Convert string type of player list into actual object type of player list
	 * @param list
	 */
	public void addPlayers(Vector<String[]> list) {
		Vector<Player> player_list = new Vector<>();
		for(String [] s: list) {
			player_list.add(new Player(s[0],getColor(s[1])));
		}
		game.setPlayers(player_list);
	}
	
	public void attachObserver (Observer ob) {
		game.addObserver(ob);
	}
	
	/**
	 * Reads map data file
	 * Parses lines in file into relevant data to be used in game
	 * Generate Game Object entities and their respective connections to each other
	 * @param address address of map file
	 * @return Validation of map data file
	 */
	public boolean readFile(String address) {
		Vector<Continent> continents_list = new Vector<Continent>();
		Vector<Country> countries_list = new Vector<Country>();
		int x=0,y=0;
		try {
			String pathname = "map\\"+address;
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
				files_load.add(line);
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
				continents_list.add(c);
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
				for(Continent con:continents_list) {
					if(con.getID()==Integer.parseInt(tmp[2])) {
						con.addToCountriesList(c);
						c.setContinent(con);
						flag = 1;
						break;
					}
				}
				if(flag == 0) {
					br.close();
					return false;
				}
				countries_list.add(c);
				line = br.readLine();
			}
			do {
				line = br.readLine();
			}while(!line.trim().equals("[borders]"));
			line = br.readLine();
			while(line!=null) {
				String[] tmp = line.split("\\s+");
				int flag = 0;
				for(Country c:countries_list) {
					if(c.getID()==Integer.parseInt(tmp[0])) {
						flag = 1;
						for(int i = 1;i<tmp.length;i++) {
							Country tc = new Country();
							for(int j = 0;j<countries_list.size();j++) {
								if(countries_list.get(j).getID()==Integer.parseInt(tmp[i])) {
									tc = countries_list.get(j);
									break;
								}
							}
							if(tc.getLabel().getText().equals("")) {
								br.close();
								return false;
							}
							c.addNeighbour(tc);
						}
					}
				}
				if(flag==0) {
					br.close();
					return false;
				}
				line = br.readLine();
			}
			this.game.setContinents(continents_list);
			this.game.setCountries(countries_list);
			br.close();
			map_file_name = address;
			return true;
		}
		 catch (Exception e) {
				e.printStackTrace();
				return false;
		}
	}
	
	/**
	 * Save current data
	 * @param name name of the file
	 * @return success or not
	 */
	public boolean saveFile(String name) {
		try {
			FileWriter out=new FileWriter(".\\save\\"+name+".save");
			out.write(name+".save\r\n");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			out.write("Save at "+df.format(new Date())+"\r\n");
			out.write("\r\n[map]\r\n");
			out.write(map_file_name+"\r\n");
			out.write("\r\n[players]\r\n");
			for(Player p:game.getPlayers()) {
				out.write(p.getID()+" "+p.getTotalCountriesNumber()+" "+p.getColorStr()+" ");
				out.write("{");
				for(int i = 0;i<p.getOwnCard().size();i++) {
					if(i==0) {
						out.write(p.getOwnCard().get(i).getType());
					}
					else {
						out.write(","+p.getOwnCard().get(i).getType());
					}
				}
				out.write("} "+p.getArmyToPlace()+"\r\n");
			}
			out.write("\r\n[countries]\r\n");
			for(Country c:game.getCountries()) {
				out.write(c.getID()+" "+c.getOwner().getID()+" "+c.getArmyNum()+"\r\n");
			}
			out.write("\r\n[status]\r\n");
			out.write(game.getPlayerIndex()+" ");
			if(game.getPhase().equals("Startup Phase")) {
				out.write("0");
			}
			else if(game.getPhase().equals("Reinforcement Phase")) {
				out.write("1");
			}
			else if(game.getPhase().equals("Attack Phase")) {
				out.write("2");
			}
			else if(game.getPhase().equals("Fortification Phase")) {
				out.write("3");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Returns corresponding Color object to the parsed String
	 * @param color
	 * @return default color white
	 */
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
	
	
	
	
	
	//Commands from MapUI
	
	public String [] processInput(String input) {
		String [] splitted = input.split("\\s+");
		
		// Command placeall
		if(splitted[0].equals("placeall")) {
	        if(game.getPhase().equals("Startup Phase")) {
	            //place all armies randomly for current player
	            game.placeAll();
	        }else {
	        	return new String [] {"F","Not in correct phase"};
	        }
	    }
		//Command placearmy countryid
		else if(splitted[0].equals("placearmy")){
			if(game.getPhase().equals("Startup Phase")) {
				String countryId = splitted [1];
				boolean c_exists = false;
				Country temp = new Country();
				for (Country c: game.getCountries()) {
					if (c.getName().equals(countryId)) {
						c_exists = true;
					temp = c;
					break;
					}
				}
				if(c_exists) {
					if(temp.getOwner().getID().equals(game.getPlayerID())) {
						game.placeArmy(temp);
					}
					else {
			        	return new String [] {"F","Country not owned by Player!"};
					}
				}
				else{
		        	return new String [] {"F","Country Does not Exist!"};
				}
			}
			else{
	        	return new String [] {"F","Not in Startup Phase!"};
	        }
		}
		//Command reinforce countryid num
		else if(splitted[0].equals("reinforce")) {
			if(game.getPhase().equals("Reinforcement Phase")) {
			
				String countryId = splitted[1];
				int num = Integer.parseInt(splitted[2]);
				boolean c_exists = false;
				Country temp = new Country();
				for (Country c: game.getCountries()) {
					if (c.getName().equals(countryId)) {
						c_exists = true;
						temp = c;
						break;
					}
				}
				if(c_exists) {
					if(temp.getOwner().getID().equals(game.getPlayerID())) {
						if(num<= game.getPlayer().getArmyToPlace()) {
							game.reinforceArmy(temp, num);
						}
						else {
				        	return new String [] {"F","Number exceeds assigning limit!"};
						}
					}
					else {
			        	return new String [] {"F","Country not owned by Player!"};
					}
				}
				else {
		        	return new String [] {"F","Country Does not Exist!"};
				}
			}
			else {
	        	return new String [] {"F","Not in Reinforcement Phase!"};
			}
		}
		//Command fortify none
		//Command fortify fromcountry tocountry num
		else if(splitted[0].equals("fortify")) {
			if (game.getPhase().equals("Fortification Phase")) {
				if(splitted[1].equals("none")){
					game.nextPlayer();
				}
				else {
					String fromCountry = splitted[1];
					String toCountry = splitted[2];
					int number = Integer.parseInt(splitted[3]);
					boolean f_exists = false;
					boolean t_exists = false;
					Country tempFrom = new Country();
					Country tempTo = new Country();
					for (Country c: game.getCountries()) {
						if (c.getName().equals(fromCountry)) {
							f_exists = true;
							tempFrom = c;
							break;
						}
					}
					for (Country c: game.getCountries()) {
						if (c.getName().equals(toCountry)) {
							t_exists = true;
							tempTo = c;
							break;
						}
					}
					if(f_exists && t_exists) {
						if(tempFrom.getOwner().getID().equals(tempTo.getOwner().getID())) {
							HashSet<String> visited = new HashSet<>();
							if(tempFrom.hasPathTo(toCountry,tempFrom.getOwner().getID(),visited)) {
								if(tempFrom.getArmyNum() > number) {
									game.fortify(tempFrom,tempTo,number);
								}
								else {
						        	return new String [] {"F","Fortifying army exceeds limit!"};
								}
							}
							else {
					        	return new String [] {"F","No linked path between countries "+fromCountry+" and "+toCountry+"!"};
							}
						}
						else {
				        	return new String [] {"F","Countries not owned by the same player!"};
						}
					}
					else {
			        	return new String [] {"F","Country(ies)"+ (f_exists?"":tempFrom)+
								(t_exists?"":toCountry)+" do(es)'nt exist!"};
					}
					
				}
			}
			else {
	        	return new String [] {"F","Not in Fortification Phase!"};
			}
		}
			return new String [] {"S"};
	}
	

	public Vector<String> getFilesLoad() {
		return this.files_load;
	}
	
	
	
}
