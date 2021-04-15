package correlation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Sprint2 {
	final static int timeWindowInMinutes = 1;
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scnr = new Scanner(new FileReader("resources//allAlertsCorrelated.csv"));
		ArrayList<Alert> alerts = readAlerts(scnr);
		alerts = sortAlertsByDate(alerts);
		detectAnomalies(alerts);
	}
	
	public static ArrayList<Alert> readAlerts(Scanner scnr) {
		ArrayList<Alert> alerts = new ArrayList<Alert>();
		
		while(scnr.hasNextLine()) {
			String[] alertInfo = scnr.nextLine().split(",");
			String[] tempCal = alertInfo[0].split("\\.");
			int[] calInfo = new int[tempCal.length];
			
			for(int i = 0; i < calInfo.length; i++)
				calInfo[i] = Integer.parseInt(tempCal[i]);
			
			Calendar cal = Calendar.getInstance();
			cal.set(calInfo[0], calInfo[1] - 1, calInfo[2], calInfo[3], calInfo[4], calInfo[5]);
			Alert alert = new Alert(cal, alertInfo[1], alertInfo[2]);
			alerts.add(alert);
		}
		
		return alerts;
	}
	
	public static ArrayList<Alert> sortAlertsByDate(ArrayList<Alert> alerts) {
		for(int i = 1; i < alerts.size(); i++) {
			int j = i;
			Alert temp = alerts.get(j);
			
			while(j > 0 && temp.calendar.compareTo(alerts.get(j - 1).calendar) < 0) {
				alerts.set(j, alerts.get(j - 1));
				j--;
			}
			
			alerts.set(j, temp);
		}
		
		return alerts;
	}
	
	public static void detectAnomalies(ArrayList<Alert> alerts) {
		int i = alerts.size() - 1;
		int counter = 1;
		
		while(i > 0) {
			Calendar cal1 = alerts.get(i - 1).calendar, cal2 = alerts.get(i).calendar;
			if(cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH) && cal1.get(Calendar.HOUR_OF_DAY) != cal2.get(Calendar.HOUR_OF_DAY))
				counter = 0;
			else if(Math.abs(cal1.get(Calendar.MINUTE) - cal2.get(Calendar.MINUTE)) < timeWindowInMinutes)
				counter++;
			
			if(counter > 3)
				System.out.println("Anomaly Detected: 3 or more alerts in the time window of " + timeWindowInMinutes + " minutes at: " + cal1.getTime());
			
			i--;
		}
	}
}