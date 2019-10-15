package UI.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import Entities.GamePlay;

public class PlayerTurnObsLabel extends JLabel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int type = 1;
	
	public PlayerTurnObsLabel() {
		this.setOpaque(true);
	}
	
	@Override
	public void update(Observable obs, Object arg1) {
		this.setBackground(((GamePlay)obs).getPlayer().getColor());
	}
	
}
