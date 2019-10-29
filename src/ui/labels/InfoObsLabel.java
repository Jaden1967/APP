package ui.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import entities.GamePlay;

public class InfoObsLabel extends JLabel implements Observer{

	private static final long serialVersionUID = 1L;
	
	public InfoObsLabel (String value) {
		this.setText(value);
	}
	
	public void update(Observable obs, Object x) {
		String s = "Phase: " +((GamePlay)obs).getPhase() +"      "+
				"Player: "+ ((GamePlay)obs).getPlayerID() + "      "+
				"Army: "+ ((GamePlay)obs).getArmyToPlace();
		this.setText(s);
		
	}
	
}
