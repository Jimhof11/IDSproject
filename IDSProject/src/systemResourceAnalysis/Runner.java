package systemResourceAnalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/* This class receives the log file from github and parses it into a new csv file
 * 
 * @author Joseph Yang
 * @version 1.0
 * @TODO indexPlaceHolder needs to be implemented into the counter events
 */
public class Runner {
	public static void main(String[] args) throws IOException {
		Scanner scnr = new Scanner(new FileReader("resources//topdata.csv"));
		File file = new File("resources//newTopData.csv");
		PrintWriter pw;
		
		if(!file.exists())
			file.createNewFile();
		
		pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		
		parseData(scnr, pw);
		pw.close();
	}
	
	private static void parseData(Scanner scnr, PrintWriter pw) {
		int counter = 0;
		while(scnr.hasNextLine()) {
			String tempString = scnr.nextLine();
			String temp = "";
			
			if(counter == 0) {
				temp = temp + tempString.substring(tempString.indexOf(" - ") + 3, tempString.indexOf(" up")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("up ") + 3, tempString.indexOf(",  ")) + ",";
				temp = temp + tempString.substring(tempString.indexOf(",  ") + 3, tempString.indexOf(" user")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("average: ") + 9, tempString.indexOf(", ", tempString.indexOf("average"))) + ",";
				temp = temp + tempString.substring(tempString.indexOf(", ", tempString.indexOf("average")) + 2, tempString.indexOf(", ", tempString.indexOf(", ", tempString.indexOf("average")) + 2)) + ",";
				temp = temp + tempString.substring(tempString.indexOf(", ", tempString.indexOf(", ", tempString.indexOf("average")) + 1) + 2);
				temp = temp.replaceAll(":", ".");
			}
			else if(counter == 1) {
				temp = temp + tempString.substring(tempString.indexOf("Tasks: ") + 7, tempString.indexOf(" total")) + ",";
				temp = temp + tempString.substring(tempString.indexOf(",   ") + 4, tempString.indexOf(" run")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("ning, ") + 6, tempString.indexOf(" sleep")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("ping,   ") + 8, tempString.indexOf(" stopped")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("ped,   ") + 7, tempString.indexOf(" zomb")) + ",";
			}
			else if(counter == 2) {
				temp = temp + tempString.substring(tempString.indexOf("(s):  ") + 6, tempString.indexOf(" us")) + ",";
				temp = temp + tempString.substring(tempString.indexOf(" us,  ") + 6, tempString.indexOf(" sy")) + ",";
				temp = temp + tempString.substring(tempString.indexOf(" sy,  ") + 6, tempString.indexOf(" ni")) + ",";
				temp = temp + tempString.substring(tempString.indexOf(" ni, ") + 5, tempString.indexOf(" id")) + ",";
				temp = temp + tempString.substring(tempString.indexOf(" id,  ") + 6, tempString.indexOf(" wa")) + ",";
				temp = temp + tempString.substring(tempString.indexOf(" wa,  ") + 6, tempString.indexOf(" hi,")) + ",";
				temp = temp + tempString.substring(tempString.indexOf(" hi,  ") + 6, tempString.indexOf(" si,")) + ",";
				temp = temp + tempString.substring(tempString.indexOf(" si,  ") + 6, tempString.indexOf(" st"));
			}
			else if(counter == 3) {
				temp = temp + tempString.substring(tempString.indexOf("Mem :  ") + 7, tempString.indexOf(" total")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("total,   ") + 9, tempString.indexOf(" free")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("free,  ") + 7, tempString.indexOf(" used")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("used,  ") + 7, tempString.indexOf(" buff"));
			}
			else if(counter == 4) {
				temp = temp + tempString.substring(tempString.indexOf("Swap:  ") + 7, tempString.indexOf(" total")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("total,   ") + 9, tempString.indexOf(" free")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("free,   ") + 8, tempString.indexOf(" used")) + ",";
				temp = temp + tempString.substring(tempString.indexOf("used.  ") + 7, tempString.indexOf(" avail"));
			}
			
			System.out.println(temp);
			
			if(counter < 5) {
				counter++;
				pw.println(temp);
			}
			else
				counter = 0;
		}
	}
}