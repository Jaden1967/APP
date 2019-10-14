package UI.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import Entities.GamePlay;

public class OutcomeObsLabel extends JLabel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int type = 2;
	@Override
	
	public void update(Observable obs, Object arg1) {
		// TODO Auto-generated method stub
		if (((GamePlay)obs).getAlertType() == type) {
			this.setText(((GamePlay)obs).getOutcome());
		}
	}

}