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
/**
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */
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