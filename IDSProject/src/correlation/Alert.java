package correlation;

import java.util.Calendar;

public class Alert {
	Calendar calendar;
	String type, details;
	
	public Alert(Calendar calendar, String type, String details) {
		this.calendar = calendar;
		this.type = type;
		this.details = details;
	}
	
	public String toString() {
		return "Time: " + calendar.getTime() + " | Type: " + type + " | Details: " + details;
	}
}
