package ui.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import entities.Country;

public class CountryObsLabel extends JLabel implements Observer{

	private static final long serialVersionUID = 1L;

	public CountryObsLabel(String valueOf) {
		this.setText(valueOf);
		this.setOpaque(true);
	}

	@Override
	public void update(Observable obs, Object x) {
		this.setBackground(((Country)obs).getOwner().getColor());
		this.setText(String.valueOf(((Country)obs).getArmyNum()));
	}
	
	

}
