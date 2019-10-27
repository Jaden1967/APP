package ui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Observable;
import java.util.Vector;

import entities.GamePlay;

import java.text.SimpleDateFormat;

public class test extends Observable{

	
	public static void main (String args[] ) {
		Vector<String> v = new Vector<>();
		v.add("a");
		v.remove("b");
		for(String s:v) {
			System.out.println(s);
		}
	}
}