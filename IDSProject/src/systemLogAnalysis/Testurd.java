package systemLogAnalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/* This class takes the output from the strace command and parses it into a csv file
 * 
 * @author Joseph Yang
 * @version 1.0
 */
public class Testurd {
	public static void main(String[] args) throws IOException {
		Scanner scnr = new Scanner(new FileReader("resources//lsLog.txt"));
		File file = new File("resources//newlsLog.csv");
		PrintWriter pw;
		
		if(!file.exists())
			file.createNewFile();
		
		pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		
		parseData(scnr, pw);
		pw.close();
	}
	
	/* This method reads the strace command and writes it onto the newlslog.csv file
	 * 
	 * @author Joseph Yang
	 * @param scnr the Scanner
	 * @param pw the PrintWriter
	 */
	private static void parseData(Scanner scnr, PrintWriter pw) {
		String tempString = scnr.nextLine();
		
		//Format is Timestamp,action,object
		while(tempString.indexOf("+++ exited with 0 +++") == -1) {
			String temp = "";
			
			temp += tempString.substring(0, tempString.indexOf(" ")) + ",";
			temp += tempString.substring(tempString.indexOf(" ") + 1, tempString.indexOf("(")) + ",";
			
			if(tempString.indexOf("\"") != -1)
				temp += tempString.substring(tempString.indexOf("\""), tempString.indexOf("\"", tempString.indexOf("\"") + 1) + 1);
			else if(tempString.indexOf(",") == -1) {
				temp += tempString.substring(tempString.indexOf("(") + 1, tempString.indexOf(")"));
			}
			else
				temp += tempString.substring(tempString.indexOf("(") + 1, tempString.indexOf(","));
			
			tempString = scnr.nextLine();
			pw.println(temp);
			temp = "";
		}
	}
}