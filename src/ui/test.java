package ui;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import entities.Country;
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
		String isCommandPattern = "(gameplayer -(add|remove) \\w*( (aggressive|benevolent|random|cheater))?|loadmap \\w*\\.map|populatecountries)";
		
		System.out.println(Pattern.matches(isCommandPattern, "gameplayer -add a"));
		
		System.out.println("aggressive".substring(0,1));
	}
}