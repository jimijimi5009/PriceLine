package utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateAndTimeUtill {

    public static String currentDateTime(String currentDateTimePattern) {
        LocalDateTime datetime = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(currentDateTimePattern);
        String formattedDate = datetime.format(pattern);
        return formattedDate;
    }

    public static String currentDatePlusDays(Integer daysToAdd) {
        LocalDateTime datetime = LocalDateTime.now().plusDays(daysToAdd);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = datetime.format(pattern);
        return formattedDate;
    }

    public static String currentMonthStartDate() {
        LocalDateTime datetime = LocalDateTime.now();
        LocalDateTime start = datetime.withDayOfMonth(1);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = start.format(pattern);
        return formattedDate;
    }
    public static String parseDate(String date) {
        try {
            TemporalAccessor temporal = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss.S")
                    .parse(date); // use parse(date, LocalDateTime::from) to get LocalDateTime
            String output = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(temporal);
            return output;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getParseDayName(String date) {
        String sMyDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date myDate = sdf.parse(date);
            sdf.applyPattern("EEEEEEE");
            sMyDate = sdf.format(myDate);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        return sMyDate;
    }



    public static String arFormatDate(String ar_date){

        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss-SSSS", Locale.ENGLISH);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(ar_date, inputFormatter);
            String formattedDate = outputFormatter.format(date);
            System.out.println(formattedDate);
            return formattedDate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

    public static boolean biggerThanTime(String time1,String time2){
        String hhmmss1[] = time1.split(":");
        String hhmmss2[] = time2.split(":");
        for (int i = 0; i < hhmmss1.length; i++) {
            if(Integer.parseInt(hhmmss1[i])>Integer.parseInt(hhmmss2[i]))
                return true;
        }
        return false;
    }


    public static String setDate(String patternString) {
        LocalDateTime datetime = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(patternString);
        String formattedDate = datetime.format(pattern);
        return formattedDate;
    }

    public static String setDate(Integer daysToAdd, String patternString) {
        LocalDateTime datetime = LocalDateTime.now().plusDays(daysToAdd);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(patternString);
        String formattedDate = datetime.format(pattern);
        return formattedDate;
    }
    public static LocalDate stringToDate(String stringDate) {
        String cleanDate = interpretDate(stringDate);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(cleanDate, pattern);
        return date;

    }

    public static Integer dateDiffToDays(String startDate, String endDate) {
        LocalDate sDate = stringToDate(startDate);
        LocalDate eDate = stringToDate(endDate);
        Integer numberOfDays = Math.toIntExact(ChronoUnit.DAYS.between(sDate, eDate));
        return numberOfDays;
    }

    public static String currentTime() {
        LocalDateTime datetime = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm:ss SSS");
        String formattedTime = datetime.format(pattern);
        return formattedTime;
    }
    public static String convertDataToUtc( String timezone,String parseTime) throws java.text.ParseException {

		try {
			DateFormat formatterIST = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			formatterIST.setTimeZone(TimeZone.getTimeZone(timezone));
			 Date date = formatterIST.parse(parseTime);

			DateFormat formatterUTC = new SimpleDateFormat("dd/MM/yyyy hh:mm ");
			formatterUTC.setTimeZone(TimeZone.getTimeZone("UTC")); // UTC timezone
			String conStr = formatterUTC.format(date);
			return conStr;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

    public static String changeDateTime(String utcTime, String timezone) throws java.text.ParseException {
        SimpleDateFormat sdfOriginal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfOriginal.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = sdfOriginal.parse(utcTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sdf.format(calendar.getTime());
    }
    public static String changeDateTimeIgnoreZero(String utcTime, String timezone) throws java.text.ParseException {
        SimpleDateFormat sdfOriginal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfOriginal.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = sdfOriginal.parse(utcTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm aa");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sdf.format(calendar.getTime());
    }
    public static long getTimeDifference(String d2, String d1)  {
        Duration dur = null;
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("h:m a");

            LocalTime time1 = LocalTime.parse(d1, format);
            LocalTime time2 = LocalTime.parse(d2, format);
            dur = Duration.between(time1, time2);
            //return dur.toMinutes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dur.toMinutes();
    }

    public static String interpretDate(String testData) {
        String setDate = null;
        int tempDate = 0;
        testData = testData.toLowerCase();

        try {
            if ((testData.contains("today") || testData.contains("current")) && testData.contains("+")) {
                String[] splitText = testData.split("\\+");
                tempDate = Integer.parseInt(splitText[1].trim());
                setDate = currentDatePlusDays(tempDate);
            } else if ((testData.contains("today") || testData.contains("current")) && testData.contains("-")) {
                String[] splitText = testData.split("\\-");
                tempDate = Integer.parseInt(splitText[1].trim());
                tempDate *= -1;
                setDate = currentDatePlusDays(tempDate);
            } else if ((testData.contains("today") || testData.contains("current"))) {
                tempDate = 0;
                setDate = currentDatePlusDays(tempDate);
            } else if (testData.contains("future")) {
                tempDate = 3; // keeping default value of 3 days in future
                setDate = currentDatePlusDays(tempDate);
            } else if (testData.contains("/")) {
                setDate = testData;
            } else {
                System.err.println("Please check the date value.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return setDate;
    }

}
