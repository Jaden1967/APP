package ui.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import entities.GamePlay;

public class OutcomeObsLabel extends JLabel implements Observer{

	private static final long serialVersionUID = 1L;
	
	public void update(Observable obs, Object arg1) {
		this.setText(((GamePlay)obs).getOutcome());	
	}
}
