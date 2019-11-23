package ui.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import entities.GamePlay;

/**
 * Player turn label representing the current player's turn during gameplay
 * Implements Observer in the observer pattern, Observes current player in the game play
 * Updates the Color of the current player to be shown by the label in UI
 * @author bo_yu
 *
 */
public class PlayerTurnObsLabel extends JLabel implements Observer{

	private static final long serialVersionUID = 1L;
	
	public PlayerTurnObsLabel() {
		this.setOpaque(true);
	}
	
	@Override
	public void update(Observable obs, Object arg1) {
		this.setBackground(((GamePlay)obs).getPlayer().getColor());
	}
}
