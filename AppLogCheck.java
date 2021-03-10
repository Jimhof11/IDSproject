import java.io.*; //program uses class FileReader and exception
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AppLogCheck {
	//format timestamp
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	public static void main(String[] args) throws IOException {
		//variables
		String filename;
		String alertfile;
		String message = "";
		String errorType = "AppLogAnalyze";

		//scan applog filename from user
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter in log filename with extension:");
		filename = scan.nextLine();

		//pass file 
		FileReader freader = new FileReader(filename);
		BufferedReader br = new BufferedReader(freader);

		//Open alert file
		PrintWriter outputFile = new PrintWriter("alertLog.csv");



		//get timestamp
		Date date = new Date();


		//error Check rule: each unique sequence must be in order and not skip numbers 
		String line;
		String heading = br.readLine();
		boolean error = false;
		int lineNum = 2;
		int a=0;
		
		System.out.println("Checking file lines...");
		while ((line = br.readLine()) != null) { 
			// use comma as separator 
			String[] cols = line.split(",");

			if(cols[0] != heading) {
				int b=Integer.parseInt(cols[0]);
				if(b == (a+1)) {  //check if in order
					lineNum++;
					a++;
				}else if(b == 1) {  //new sequence starts
					a=1;
					lineNum++;
				}else {  //print errors to alert file and console
					System.out.println("Error Found on line "+lineNum);
					message="Number Sequence Broken";
					outputFile.println(sdf.format(date)+","+errorType+","+message);
					lineNum++;
					error = true;
				}
			}

		}
		System.out.println("AppLog check Finished");

		if(error) {
		System.out.println("Alerts written to a file.");
		}
		// Close the file
		freader.close();
		outputFile.close(); //close file output stream
		scan.close(); //close input stream



	}

}
