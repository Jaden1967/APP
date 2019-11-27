package entities.strategy;

import entities.GamePlay;
import entities.Player;

public class StrategyRandom extends Strategy{

	
	public StrategyRandom(GamePlay game, Player p) {
		this.game = game;
		this.player = p;		
		this.type = "r";

	}
}
