package entities.strategy;

import entities.GamePlay;

public class StrategyRandom extends Strategy{

	
	public StrategyRandom(GamePlay game) {
		this.game = game;
		this.player = game.getPlayer();		
		this.type = "r";

	}
}
