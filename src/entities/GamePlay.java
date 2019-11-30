package entities;

import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.Menu;
import ui.Trade;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Observable;
import java.util.Random;

/**
 * Observable Model for the Risk Game
 * Contains Vectors of relevant class entities, such as continents, countries and players that has been created by the loadmap function from 
 * the controller
 * Observed by the MapUI JLabels to update information on the current game status
 * public methods within GamePlay is called by the controller while processing user inputs from the UI during game play
 * Attributes can only be accessed by getters and setters from the controller
 * handles change of game data and state and reflects to relevant observers
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
public class GamePlay extends Observable{
	private Vector <Continent> continents_list;
	private Vector<Country> countries_list;	
	private Vector<Player> player_list;
	private Player player;
	private int player_index = 0; //index deciding whose turn to play
	private int army_to_place;
	private int alert_type = 0;
	private String outcome;
	private String phase = "";
	private Country attacker = null;
	private Country defender = null;
	private int att_dice=0;
	private int def_dice=0;
	private int add_flag = 0;
	private JFrame mapui = null;
	boolean is_test = false;
	public boolean game_ended = false;
	
	/**
	 * Empty default ctor
	 */
	public GamePlay() {
	}
	
	/**
	 * Set the JFrame for the MapUI related to the game play
	 * @param m JFrame of MapUI
	 */
	public void setJFrame(JFrame m) {
		mapui = m;
	}
	
	/**
	 * Clear all existing lists of Continents, Countries and Players
	 */
	public void clearData() {
		countries_list.clear();
		player_list.clear();
		continents_list.clear();
	}
	
	/**
	 * Player input: populatecountries
	 * randomly assign ownership of countries on the map until all are filled
	 */
	public void populateCountries(){
		HashSet <Integer> polledCountries = new HashSet <>();
		Random rand = new Random();
		int cInd; // Country index in list
		int pInd = 0; // Player index in list
		while (polledCountries.size()!=countries_list.size()) {
			do {
				cInd = rand.nextInt(countries_list.size()); //generate random unassigned Country index 
			} while (polledCountries.contains(cInd));
			
			polledCountries.add(cInd);
			
			countries_list.get(cInd).setOwner(player_list.get(pInd)); //set unassigned Country to Player in List in order
			countries_list.get(cInd).addArmy(1);
			player_list.get(pInd).addCountry(countries_list.get(cInd));
			player_list.get(pInd).deployArmy(1);
			pInd++;
			if (pInd % player_list.size() == 0) {
				pInd = 0;
			}
		}
		outcome = "Randomly assigned countries to all players";
		alertObservers();
		for(int i = 0;i<continents_list.size();i++) {
			continents_list.get(i).checkIfConquered();
		}
		for (Player p: player_list) {
			if(p.isAI()) {
				p.setOwnCountriesInStrategy();
			}
		}
		
		phaseZero();
	}
	
	
	
	/**
     * Startup Phase
     * Set status to appropriate player and phase
     * Alert observers
     */
	private void phaseZero() {
		phase = "Startup Phase";
		for(Player p: player_list) p.initializeStartupArmy(player_list.size());
		player = player_list.get(player_index);
		army_to_place = player.getArmyToPlace();
		alertObservers();
	}
	
	/**
	 * From player command: placearmy countryname
     * place one army to target country in parameter, then give the turn to the next player 
     * @param c Country to play an army
	 */
	public void placeArmy(Country c) {
		outcome+= "Player "+player.getID()+" placed army on "+c.getName()+"\n";
		c.addArmy(1);
		player.deployArmy(1);
		startUpNext();
	}
	
	/**
     * From player command: placeall
     * During startup phase, randomly places army on the map for all players until all players have no more units to place
     * Enters Reinforcement phase once all troops are deployed
     * Randomly places armies to current player's owned countries
     */

    public void placeAll() {
        for (Player p: player_list) {
            player = p;
            Vector <Country> toAdd = new Vector <>();
            for(Country c: countries_list) {
                if(c.getOwner().getID().equals(player.getID())) {
                    toAdd.add(c);
                }
            }
            int ind = 0;
            Random rand = new Random();
            while (player.getArmyToPlace()!=0) {
                ind = rand.nextInt(toAdd.size());
                toAdd.get(ind).addArmy(1);
                player.deployArmy(1);
            }
            army_to_place = player.getArmyToPlace();
        }
        player = player_list.get(0);
        player_index = 0;
        player.rewardInitialArmy();
        outcome = "Randomly assigned armies to owned countries";
        phaseRecruit();
    }
    
	
	
	/**
	 * For next player to place army during Startup Phase
	 * Switches GamePlay context to next player's datasets
	 */
	public void startUpNext() {
		player_index++;
		if(player_index % player_list.size()==0) {
			player_index = 0;
		}
		player = player_list.get(player_index);
		army_to_place = player.getArmyToPlace();
		outcome += "Next player's turn\n";
		if(army_to_place==0) {
			player.rewardInitialArmy();
			phaseRecruit();
		}
		alertObservers();

		if(player.isAI()) {
			System.out.println("player "+player.getID()+" is placing random army");
			//if the player is an ai, randomly placearmy on one of its owned Countries, go to the next player's startup phase
			Random rand = new Random();
			placeArmy(player.getOwnCountries().get(rand.nextInt(player.getTotalCountriesNumber())));
		}

	}
	
	
	
	/**
     * Reinforcement Phase
     * Set phase to "Reinforcement Phase"
     * Alerts InfoObsLabel
     */
	public void phaseRecruit() {
		showDialog("Reinforcement Phase for player "+player.getID());
		add_flag = 0;
		//TODO add AI automatic trade
		if(player.getOwnCard().size()==5) {
			showDialog("You have reached the maximum number of cards, please trade!");
			Trade t = new Trade(this);
			t.setVisible(true);
		}
		// System.out.println("It is now player "+player.getID()+"'s turn, ai: "+player.isAI());
		phase = "Reinforcement Phase";
		player.reSetArmy();
		player.rewardInitialArmy();
		army_to_place = player.getArmyToPlace();
		if(player.isAI()) {
			player.doStrategy();
		}
		alertObservers();

	}
	
	/**
     * From player command: reinforce country number
     * During reinforcement phase, player assigns the input amount of army to target country 
     * @param c target countryId
     * @param num amount of army to assign
     */
	public void reinforceArmy (Country c, int num) {
		c.addArmy(num);
		player.deployArmy(num);
		outcome += "Player "+player.getID()+ " Reinforced "+c.getName()+" with "+num+" armies\n";
		alertObservers();
		if(army_to_place==0 && !player.isAI()) {
			phaseAttack();
		}
	}
	
	/**
	 * Check if a player has at least one country able to attack
	 * 1. Country belongs to the player
	 * 2. Country has at least 2 armies
	 * 3. Country has at least 1 enemy neighbor
	 * @param p Player to test if can attack
	 * @return true if can false if not
	 */
	public boolean checkIfCanAttack(Player p) {
		for (Country c:countries_list) {
			if(c.getOwner().getID().equals(p.getID()) && c.getArmyNum()>=2 && c.hasEnemyNeighbour()) return true;
		}
		return false;
	}
	
    /**
     * Attack Phase
     * If player is able to attack with at least one of its countries, continue the attack phase
     * Else go directly into fortify phase
     */
	public void phaseAttack() {
		showDialog("Attack Phase for player "+player.getID());
		if(checkIfCanAttack(player)) {
			phase = "Attack Phase 1";
			alertObservers();
		}else {
			phaseFortify();
		}
		
	}
	
	/**
	 * From player command: -noattack
	 * During attack phase 1, player chooses not to attack any countries
	 * Goes directly into Fortification Phase
	 */
	public void noAttack() {
		outcome += "Player "+player.getID()+" chose not to attack";
		phaseFortify();
	}
	
	/**
	 * Set the attacker and the defender country for one round of attack
	 * Set the number of dice to be rolled by the attacker
	 * @param from Attacking country
	 * @param to Defending country
	 * @param dicenum Number of dice to be rolled by the attacking country
	 */
	public void setAttack(Country from, Country to, int dicenum) {
		attacker = from;
		defender = to;
		att_dice = dicenum;
		outcome += "Attacking from "+from.getName()+" to "+to.getName()+" \nChoose defender's number of dice to be rolled\n";
		if(!defender.getOwner().isAI()){
			player = defender.getOwner();
			phase = "Attack Phase 2";
			alertObservers();
		}else {
			commenceAttack(Math.min(2, defender.getArmyNum()));
		}
	}
	
	/**
	 * From player command: attack countryfrom countryto numdice -allout
	 * Commence a loop of attack rounds until one of the countries run out of army to attack/defend
	 * Always choose the maximum number of dice on both sides
	 * @param from Attacking Country
	 * @param to Defending Country
	 * @return true if the Country is successfully conquered during the all out attack
	 */
	public boolean allOutAttack (Country from, Country to) {
		outcome += "All-out Attacking from "+from.getName()+" ("+from.getArmyNum()+") to "+ to.getName()+"\n";
		attacker = from;
		defender = to;
		boolean conquered = false;
		while (attacker.getArmyNum()!=1 && defender.getArmyNum()!=0) {
			maxDiceNum(from,to);
			conquered = attackRound(true);
		}
		if (conquered) {
			phase = "Attack Phase 3";
		}
		else {
			if (checkIfCanAttack(player)) {
				phase = "Attack Phase 1";
				alertObservers();
			}
			else {
				if(!player.isAI()) {
					phaseFortify();
				}
			}
		}
		return conquered;
	}
	
	/**
	 * Begins a round of attack after the defender has chosen the number of dice to be rolled
	 * @param defenderDice Number of dice to be rolled by the defender
	 * @return true if the Attacker successfully conquered the defending country
	 */
	public boolean commenceAttack(int defenderDice) {
		def_dice = defenderDice;
		player = attacker.getOwner();
		outcome+= "defender chose "+defenderDice+" dice\n";
		return attackRound(false);
	}
	
	/**
	 * One round of attack during attack phase 2
	 * Random dice roll is performed on both the attacker and the defender
	 * The number of rolls is dependent on the amount of dice possessed by each side
	 * Multiple comparisons is performed depending on amount of dice rolled
	 * The largest dice value on both sides is compared first, then the second largest
	 * If the attacker rolled a larger value than the defender, the attacker wins the comparison, or else the defender wins
	 * When one side loses a comparison, 1 army number is subtracted from the losing side's country
	 * Set game phase back to attack phase 1 after the attack is performed and the defender still has armies
	 * Set game phase to attack phase 3 if the defender loses all armies after the attack
	 * @param allout If the call comes from an all out attack, used to determine if observer should be alerted
	 * @return true if the defender no longer has any more armies to defend the attack
	 */
	private boolean attackRound(boolean allout) {
		Vector<Integer> adice = new Vector<>();
		Vector<Integer> ddice = new Vector<>();
		Random rand = new Random();
		for (int i=0;i<att_dice;i++) {
			adice.add(rand.nextInt(6)+1);
		}
		for (int i=0;i<def_dice;i++) {
			ddice.add(rand.nextInt(6)+1);
		}
		Comparator<Integer> comparator = Collections.reverseOrder();
		Collections.sort(adice,comparator);
		Collections.sort(ddice,comparator);
		outcome += "Player "+attacker.getOwner().getID()+" rolled "
		+ adice.toString() + "\nPlayer "+defender.getOwner().getID() + " rolled " + ddice.toString()+"\n"; 
		
		int alose = 0, dlose = 0;
		while (adice.size()!=0 && ddice.size()!=0 && attacker.getArmyNum()!=0 && defender.getArmyNum()!=0) {
			if(adice.remove(0) > ddice.remove(0)) {
				defender.removeArmy(1);
				dlose++;
			}
			else {
				attacker.removeArmy(1);
				alose++;
			}
		}
		outcome += "Attacker lost "+alose+ " , Defender lost "+dlose+"\n";
		if(defender.getArmyNum()==0) {
			outcome += "Successfully conquered "+defender.getName()+" with "+attacker.getArmyNum()+" armies remaining\n";
			if(add_flag==0) {
				addCard();
				add_flag++;
			}
			outcome += "Choose the number of army to be moved to "+defender.getName()+"\n";
			phase = "Attack Phase 3";
			alertObservers();
			return true;
		}
		if(!allout) {
			if(checkIfCanAttack(player)) {
				outcome += "Choose next attack move";
				phase = "Attack Phase 1";
			}
			else {
				outcome += "No more countries is able to attack";
				phaseFortify();
			}
			alertObservers();
		}
		return false;
	}
	
	/**
	 * Set the maximum possible dice number for both attacking and defending sides during an all-out attack
	 * @param from Attacking country
	 * @param to Defending country
	 */
	private void maxDiceNum(Country from, Country to) {
		att_dice = from.getArmyNum()-1;
		if (att_dice>3) att_dice =3;
		def_dice = to.getArmyNum();
		if (def_dice>2) def_dice =2;
	}
	
	/**
	 * Getter for the current attacking country
	 * @return Attacking Country
	 */
	public Country getAttacker() {
		return attacker;
	}
	
	/**
	 * Getter for the current defending country
	 * @return Defending Country
	 */
	public Country getDefender() {
		return defender;
	}
	
	/**
	 * Send army to the target country after the defender country lost all its armies
	 * If defender has lost all its countries, remove the defender from the game
	 * Moves phase to attack phase 1 or fortification depending on if the current player still can perform an attack
	 * @param number Number of army to move
	 */
	public void attackMove(int number) {
		attacker.removeArmy(number);
		defender.addArmy(number);
		outcome += "Moved "+number+" to "+defender.getName()+"\n";
		phase = "Attack Phase 1";
		attacker.getOwner().addCountry(defender);
		defender.getOwner().removeCountry(defender);
		defender.getOwner().getOwnContinent().remove(defender.getContinent());
		if(defender.getOwner().getTotalCountriesNumber()==0) {
			removePlayer(defender.getOwner());
		}
		if (checkIfCanAttack(player)) {
			phase = "Attack Phase 1";
			outcome += "Continue Attacking.\n";
		}
		else {
			if(!player.isAI()) {
				phaseFortify();
			}
		}
		reSetOwner();
		alertObservers();
	}
	
	/**
	 * Reset the owner for the defending country to the current owner after a successful conquer
	 */
	public void reSetOwner() {
		defender.setOwner(player);
		defender.getContinent().checkIfConquered();
		checkWin();
	}
	
	public void checkWin() {
		if(player.checkWin(continents_list.size())) {
			game_ended = true;
			showDialog("Player "+player.getID()+", you win!");
			if(!is_test) {
				
				mapui.dispose();
				Menu m = new Menu();
				m.setVisible(true);
			}
		}
	}
	
	/**
	 * Checks if the current player has the ability to fortify units in the fortification phase
	 * @param p The player to check
	 * @return true if the player can fortify at least 1 country
	 */
	public boolean checkIfCanFortify(Player p) {
		if(p.getTotalCountriesNumber()==1) {
			return false;
		}
		for (Country c:countries_list) {
			if ((c.getOwner().getID().equals(p.getID()) && c.getArmyNum()>=2)) {
				return true;
			}
		}
		return false;
	}
	
    /**
     * Fortify Phase
     * Set phase to "Fortification Phase"
     * alerts InfoObsLabel
     */
	public void phaseFortify() {
		showDialog("Fortification Phase for player "+player.getID());
		if(checkIfCanFortify(player)) {
			phase = "Fortification Phase";
			alertObservers();
		}
		else {
			nextPlayer();
		}
	}
	
	/**
	 * From player command fortify fromcountry tocountry num
	 * removes army number from from country, add it to to country
	 * @param from Country mobilizing army
	 * @param to Country receiving army
	 * @param num Amount of army mobilized
	 */
	public void fortify(Country from, Country to, int num) {
		from.removeArmy(num);
		to.addArmy(num);
		outcome += "Sucessfully mobilized from "+ from.getName()+" to "+to.getName()+" "+num+" armies\n";
		nextPlayer();
		
	}
	
	/**
	 * Switches context to next player in chronological order
	 * Initiate reinforcement phase for that player
	 */
	public void nextPlayer() {
		player_index = (player_index+1)% player_list.size();
		player = player_list.get(player_index);
		player.rewardInitialArmy();
		System.out.println("Current player is "+player.getID()+" ai: "+player.isAI());
		army_to_place = player.getArmyToPlace();
		outcome += "Next player's turn\n";
		alertObservers();
		phaseRecruit();
	}
	
    /**
     * Getter for phase attribute
     * @return phase as String
     */
    public String getPhase() {
        return phase;
    }
    
    /**
     * Set the phase for the game for the load game function
     * @param s The string 
     */
    public void setPhase(String s) {
    	switch (s) {
    		case "0": phase = "Startup Phase";return;
    		case "1": phase = "Reinforcement Phase";return;
    		case "2": phase = "Attack Phase 1";return;
    		case "3": phase = "Attack Phase 2";return;
    		case "4": phase = "Attack Phase 3";return;
    		case "5": phase = "Fortification Phase";return;
    	}
    }
    
    /**
     * Getter for current player ID
     * @return player id as String
     */
    public String getPlayerID() {
        return player.getID();
    }
    
    /**
     * Getter for current Player
     * @return player as Player object
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Getter for player with specific input id
     * @param id ID name of player
     * @return Specified Player object
     */
    public Player getPlayer(String id) {
    	for (Player p: player_list) {
    		if(p.getID().equals(id)) return p;
    	}
    	return null;
    }
    
    /**
     * Getter for army to place
     * @return armyToPlace as String
     */
    public int getArmyToPlace() {
        return army_to_place;
    }
    
    /**
     * Getter for alert type for observers
     * @return type as int
     */
    public int getAlertType() {
        return alert_type;
    }
    
    /**
     * Getter for outcome after a player action
     * @return outcome as String
     */
    public String getOutcome() {
        return outcome;
    }
    
    /**
     * Getter for vector of continent
     * @return continents_list as vector
     */
    public Vector<Continent> getContinents(){
    	return continents_list;
    }
    
    /**
     * Setter for continents_list
     * @param continentsList List of continents
     */
    public void setContinents(Vector<Continent> continentsList) {
		continents_list = continentsList;
	}
	
    /**
     * Getter for vector of countries
     * @return countries_list as vector
     */
	public Vector<Country> getCountries() {
		return countries_list;
	}
	
	/**
	 * Setter for countries_list
	 * @param countriesList list of Countries in gameplay
	 */
	public void setCountries(Vector<Country> countriesList) {
		countries_list = countriesList;
	}
	
    /**
     * Getter for vector of players
     * @return player_list as vector
     */
	public Vector<Player> getPlayers(){
		return player_list;
	}
	
	/**
	 * Setter for player_list
	 * @param playerList list of players in gameplay
	 */
	public void setPlayers(Vector<Player> playerList) {
		player_list = playerList;
		player = playerList.get(0);
	}
    
	/**
	 * After change of game state, alert concerned Observers
	 */
	public void alertObservers() {
		army_to_place = player.getArmyToPlace();

		setChanged();
		notifyObservers(this);
		if(!player.isAI()) {
			outcome = "";
		}
		alert_type = 0;
	}
	
	/**
	 * Add a random Card to the current player
	 * Method is called once the current player successfully conquered a new Country after an attack
	 */
	public void addCard() {
		String tmp[] = {"Infantry","Cavalry","Artillery"};
		int r = (int)(Math.random()*3);
		if(player.getOwnCard().size()>=5) {
			player.getOwnCard().remove(0);
		}
		player.addCard(tmp[r]);
		outcome += "Added "+tmp[r]+" card to current player\n";
	}
	
	/**
	 * Getter of player index
	 * @return integer of player index
	 */
	public int getPlayerIndex() {
		return player_index;
	}
	
	/**
	 * Getter of player owned country number
	 * @return String value of total number of countries owned by current player
	 */
	public String getPlayerCountryNum() {
		return String.valueOf(this.player.getTotalCountriesNumber());
	}
	
	/**
	 * Setter for the current player index
	 * Sets the current player according to the index
	 * @param i Index to be set for current player
	 */
	public void setPlayerIndex(int i) {
		player_index = i;
		player = player_list.get(i);
	}
	
	/**
	 * Get the number of attack dice that has been set by the attacker
	 * @return att_dice return the number of attack dice
	 */
	public int getAttackDice() {
		return att_dice;
	}
	
	/**
	 * Setter for the flag for the current player to indicate if the player has already received a Card after a successful attack
	 * @param f 1 if the current player received a card this turn, 0 if not
	 */
	public void setAddFlag(int f) {
		add_flag = f;
	}
	
	/**
	 * returns
	 * @return add_flag return the flag
	 */
	public int getAddFlag() {
		return add_flag;
	}
	
	/**
	 * Used to determine the current running game is a part of a JUnit test
	 * Will disable Message Dialogues from popping up during the test runs by checking the is_test boolean
	 */
	public void isTest() {
		is_test = true;
	}
	
	public void removePlayer(Player p) {
		player_list.remove(defender.getOwner());
		if(!is_test) {
			JOptionPane.showMessageDialog(null, "Player "+defender.getOwner().getID()+" is out!", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		if(player_index>=player_list.size()) {
			player_index--;
		}
	}
	
	/**
	 * Prompts a message dialogue showing an important message in game
	 * Will only show the dialogue if the game is not a test being run in JUnit
	 * @param s Message to be shown in the Message Dialogue
	 */
	private void showDialog(String s) {
		if(!is_test && (!player.isAI()||game_ended)) {
			JOptionPane.showMessageDialog(null, s, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}