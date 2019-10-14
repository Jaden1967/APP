package UI.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import Entities.GamePlay;

public class InfoObsLabel extends JLabel implements Observer{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private final int type = 1;
	
	public InfoObsLabel (String value) {
		this.setText(value);
	}
	
	@Override
	public void update(Observable obs, Object x) {
		if(((GamePlay)obs).getAlertType() == type ) {
			String s =(String.format("%25s", "Phase: " +((GamePlay)obs).getPhase()) +
					String.format("%25s", "Player: "+ ((GamePlay)obs).getPlayerID()) + 
					String.format("%25s", "Army: "+ ((GamePlay)obs).getArmyToPlace()));
			// TODO Auto-generated method stub
			this.setText(s);
		}
	}
	
}
