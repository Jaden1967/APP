package ui.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;

import entities.GamePlay;

/**
 * Outcome text representing outcome of player commands in the game play
 * Implements Observer in the observer pattern, Observes game outcome in the GamePlay object
 * Updates the String text to be shown in the UI
 * @author bo_yu
 *
 */
public class OutcomeObsLabel extends JTextArea implements Observer{

	private static final long serialVersionUID = 1L;
	
	public void update(Observable obs, Object arg1) {
		this.setText(((GamePlay)obs).getOutcome());	
	}
}
