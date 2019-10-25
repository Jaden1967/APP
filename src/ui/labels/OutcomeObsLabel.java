package ui.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;

import entities.GamePlay;

public class OutcomeObsLabel extends JTextArea implements Observer{

	private static final long serialVersionUID = 1L;
	
	public void update(Observable obs, Object arg1) {
		this.setText(((GamePlay)obs).getOutcome());	
	}
}
