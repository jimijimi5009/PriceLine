package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AR_Demo {

	public static void main(String[] args) {
		String empname = "2022-06-30 18:00:00.000";
		
		  String[] namesplit=empname.split(" "); 
		  String trimedempname=namesplit[0].trim(); 
		  System.out.println("After Trim: "+trimedempname);

		
		

	}

}
