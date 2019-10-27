package ui.labels;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JTextField;

import entities.Country;

public class CountryObsLabel extends JLabel implements Observer{

	private static final long serialVersionUID = 1L;
	private String name;
	
	public CountryObsLabel(String valueOf, String name) {
		this.setText(valueOf);
		this.setOpaque(true);
		this.name = name;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(name), null);
			}
		});
	}

	@Override
	public void update(Observable obs, Object x) {
		this.setToolTipText("Name: "+((Country)obs).getName()+"  Owner: "+((Country)obs).getOwner().getID()+"  Army: "+String.valueOf(((Country)obs).getArmyNum()));
		this.setBackground(((Country)obs).getOwner().getColor());
		this.setText(String.valueOf(((Country)obs).getArmyNum()));
	}
	
}
