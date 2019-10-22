package ui.labels;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import entities.Country;

public class CountryObsLabel extends JLabel implements Observer{

	private static final long serialVersionUID = 1L;
	private String name;
	
	
	public CountryObsLabel(String valueOf) {
		this.setText(valueOf);
		this.setOpaque(true);
	}

	@Override
	public void update(Observable obs, Object x) {
		this.setToolTipText("Name: "+((Country)obs).getName()+"  Owner: "+((Country)obs).getOwner().getID()+"  Army: "+String.valueOf(((Country)obs).getArmyNum()));
		this.setBackground(((Country)obs).getOwner().getColor());
		this.setText(String.valueOf(((Country)obs).getArmyNum()));
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
