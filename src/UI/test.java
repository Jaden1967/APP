package ui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import java.text.SimpleDateFormat;

public class test {

	
	public static void main (String args[] ) {
		Vector<String> v = new Vector<String>();
		for(String s:v) {
			System.out.println(s);
		}
		try {
			FileWriter out=new FileWriter(".\\save\\test.save",true);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			out.write(df.format(new Date())+"\r\n");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}