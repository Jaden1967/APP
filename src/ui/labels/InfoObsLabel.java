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
		String phase = ((GamePlay)obs).getPhase();
		String phase_info;
		switch (phase) {
			case "Startip Phase": phase_info = "Army: "+ ((GamePlay)obs).getArmyToPlace(); break;
			case "Reinforcement Phase": phase_info = "Army: "+ ((GamePlay)obs).getArmyToPlace(); break;
			case "Attack Phase 1": phase_info = "Owned Countries: "+ ((GamePlay)obs).getPlayerCountryNum(); break;
			default: phase_info = ""; break;
		}
		
		
		String s = "Phase: " +phase +"      "+
				"Player: "+ ((GamePlay)obs).getPlayerID() + "      "+
				phase_info;
		this.setText(s);
		
	}
	
}
