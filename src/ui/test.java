package ui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

import entities.GamePlay;

import java.text.SimpleDateFormat;

public class test extends Observable{

	
	public static void main (String args[] ) {
		Vector<String> v = new Vector<String>();
		for(String s:v) {
			System.out.println(s);
		}
		try {
			GamePlay g = new GamePlay();
			FileWriter out=new FileWriter(".\\save\\test.save",true);
			out.write(Integer.toString(g.getPlayerIndex()));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}