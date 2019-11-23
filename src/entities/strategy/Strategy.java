package entities.strategy;

import entities.GamePlay;
import entities.Player;

public abstract class Strategy{
	public GamePlay game;
	public Player current_player;
	
	public void reinforce() {}
	
	public void attack() {}
	
	public void fortify() {}
}
