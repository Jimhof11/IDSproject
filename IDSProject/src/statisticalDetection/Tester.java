package statisticalDetection;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


/* This class 100 different numbers with the desired mean and standard deviation
 * 
 * @author Joseph Y.
 * @version 1.0
 */
public class Tester {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	static ArrayList<Double> numberList = new ArrayList<Double>();
	static double baseLineMean = 10, baseLineSD = 1;
	static int slidingWindowSize = 5;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		generate();
		findDiscrepancies(numberList);
	}

	/* This method creates 100 different numbers with mean of 10 and SD of 1 and appends it to numberList.
	 * It creates an additional 100 different numbers with mean of 50 and SD of 10 and it to numberList. 
	 */
	public static void generate() {
		double[] numbers = new double[100];
		Random r = new Random();
		double mean = 10, sd = 1;
		
		for(int i = 0; i < numbers.length; i++) {
			numbers[i] = r.nextGaussian() * sd + mean;
			numberList.add(numbers[i]);
		}
		
		mean = 50; sd = 10;
		
		for(int i = 0; i < numbers.length; i++) {
			numbers[i] = r.nextGaussian() * sd + mean;
			numberList.add(numbers[i]);
		}
	}
	
	//This method finds the mean of the given ArrayList
	public static double findMean(double[] arr) {
		double sum = 0;
		
		for(double num : arr)
			sum += num;
		
		return sum / arr.length;
	}
	
	//This method finds the standard deviation of the given ArrayList
	public static double findSD(double[] arr) {
		double mean = findMean(arr), sdSum = 0, variance;
		
		for(double num: arr)
			sdSum += Math.pow((num - mean) , 2);
		
		variance = Math.sqrt(sdSum / arr.length);
		
		return variance;
	}
	
	public static void findDiscrepancies(ArrayList<Double> arr) throws FileNotFoundException, IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("src//alerts.csv")));
		Date date = new Date();
		
		for(int i = 0; i < arr.size() - slidingWindowSize; i++) {
			double[] tempArr = new double[slidingWindowSize];
			
			for(int j = 0; j < slidingWindowSize; j++) {
				tempArr[j] = numberList.get(i + j);
			}
			
			double tempMean = findMean(tempArr);
			
			if(tempMean > (baseLineMean + baseLineSD) || tempMean < (baseLineMean - baseLineSD))
				pw.println(sdf.format(date) + ",Statistical Detection," + "Irregular Mean found");
		}
		
		pw.close();
	}
}