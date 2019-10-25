package ui.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import entities.GamePlay;

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
