package UI.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import Entities.GameInfoTab;

public class InfoObsLabel extends JLabel implements Observer{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	public InfoObsLabel (String value) {
		this.setText(value);
	}
	
	@Override
	public void update(Observable obs, Object x) {
		String s =(String.format("%25s", "Phase: " +((GameInfoTab)obs).getPhaseIn()) +
				String.format("%25s", "Player: "+ ((GameInfoTab)obs).getPlayerIn()) + 
				String.format("%25s", "Army: "+ ((GameInfoTab)obs).getArmyIn()));
		// TODO Auto-generated method stub
		this.setText(s);
	}
	
}
