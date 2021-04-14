package systemResourceAnalysis;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/* This class receives the log file from github and checks to see if the last 10
 * readings are abnormal in terms of CPU usage and Memory (1 standard devation
 * or more from the mean)
 * 
 * @author Joseph Yang
 * @version 1.0
 */
public class Sprint2 {
	
	final static int desiredLogAmount = 10;
	
	public static void main(String[] args) throws IOException {
		Scanner scnr = new Scanner(new FileReader("resources//newTopData.csv"));
		ArrayList<String[]> topData = readData(scnr);
		ArrayList<ArrayList<Double>> logs = getCPUAndMem(topData);
		double[] mean = new double[logs.get(0).size()], sd = new double[logs.get(0).size()];
		ArrayList<String> time = getTime(topData);
		
		int row = 0, col = 0;
		while(col < logs.get(row).size()) {
			ArrayList<Double> temp = new ArrayList<Double>();
			
			while(row < logs.size()) {
				temp.add(logs.get(row).get(col));
				row++;
			}
			
			mean[col] = getMean(temp); sd[col] = getStandardDeviation(temp, mean[col]);
			row = 0; col++;
		}
		
		System.out.println("Alerts within the last " + desiredLogAmount + " logs of data");
		System.out.println("========================================");
		for(int i = 0; i < logs.size(); i++)
			for(int j = 0; j < logs.get(i).size(); j++) {
				double upperLimit = mean[j] + sd[j], lowerLimit = mean[j] - sd[j];
				
				if(logs.get(i).get(j) > upperLimit || logs.get(i).get(j) < lowerLimit) {
					System.out.println("Alert: 33% Deviation found at: " + time.get(i));
					break;
				}
			}
		
		scnr.close();
	}
	
	public static ArrayList<String[]> readData(Scanner scnr) {
		ArrayList<String[]> temp = new ArrayList<String[]>();
		
		while(scnr.hasNextLine())
			temp.add(scnr.nextLine().split(","));
		
		return temp;
	}
	
	public static ArrayList<ArrayList<Double>> getCPUAndMem(ArrayList<String[]> topData) {
		ArrayList<String[]> cpuAndMemLogs = new ArrayList<String[]>();
		
		for(int i = 2; i < topData.size(); i += 5) {
			int tempSize = topData.get(i).length + topData.get(i + 1).length;
			String[] temp = new String[tempSize];
			
			int tempIndex = 0;
			
			for(int j = 0; j < topData.get(i).length; j++) {
				temp[tempIndex] = topData.get(i)[j];
				tempIndex++;
			}
			
			for(int j = 0; j < topData.get(i + 1).length; j++) {
				temp[tempIndex] = topData.get(i + 1)[j];
				tempIndex++;
			}
			
			cpuAndMemLogs.add(temp);
		}
		
		ArrayList<ArrayList<Double>> doubleLogs = new ArrayList<ArrayList<Double>>();
		
		for(int i = 0; i < cpuAndMemLogs.size(); i++) {
			ArrayList<Double> temp = new ArrayList<Double>();
			
			for(int j = 0; j < cpuAndMemLogs.get(i).length; j++)
				temp.add(Double.parseDouble(cpuAndMemLogs.get(i)[j]));
			
			doubleLogs.add(temp);
		}
		
		while(doubleLogs.size() > desiredLogAmount)
			doubleLogs.remove(0);
		
		return doubleLogs;
	}
	
	public static ArrayList<String> getTime(ArrayList<String[]> arr) {
		ArrayList<String> temp = new ArrayList<String>();
		int counter = 0;
		
		for(int i = 0; i < arr.size(); i += 5) {
			temp.add(arr.get(i)[0]);
			counter++;
		}
		
		while(temp.size() > desiredLogAmount)
			temp.remove(0);
		
		return temp;
	}
	
	public static double getMean(ArrayList<Double> arr) {
		double sum = 0;
		
		for(double d: arr)
			sum += d;
		
		return Math.round(sum/arr.size() * 100) / 100.0;
	}
	
	public static double getStandardDeviation(ArrayList<Double> arr, double mean) {
		double sum = 0;
		
		for(double d: arr)
			sum += Math.pow((d - mean), 2);
		
		return Math.round(Math.sqrt(sum / arr.size()) * 100) / 100.0;
	}
}

	