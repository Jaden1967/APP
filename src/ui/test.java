package ui;

import java.io.FileWriter;
import java.io.IOException;

public class test {

	
	public static void main (String args[] ) {

		try {
			FileWriter out=new FileWriter(".\\save\\test.save",true);
			out.write("�һ�д���ļ���\r\n");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}