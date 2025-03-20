package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeHelper {

    //Generates a ScheduleID using the given prefix, current date, and time.
    public static String generateScheduleIDWithCurrentDateTime(String prefix) {
        // Current date in "yyyyMMdd" format
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // Current time in "HHmmss" format
        String currentTime = new SimpleDateFormat("HHmmss").format(new Date());
        // Combine prefix, current date, and current time into the ScheduleID
        return prefix + currentDate + "T" + currentTime;
    }

    //This method returns the current date and time as a formatted string.
    public static String getCurrentDateTime() {

        // Create a SimpleDateFormat instance with the required format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Create a new Date instance representing the current date and time
        Date now = new Date();

        // Format the current date and time into the specified format
        String currentDateTime = dateFormat.format(now);

        // Return the formatted date and time string
        return currentDateTime;
    }

    // Method to parse a string into a Date object
    public static Date parseDate(String currentDateTime) {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;

        try {
            // Parse the string into a Date object
            date = dateFormat.parse(currentDateTime);
        } catch (ParseException e) {
            // Handle parsing exceptions
            e.printStackTrace();
        }

        // Return the Date object
        return date;
    }

    //Modify the specified date by adding or subtracting the given number of minutes and/or days.
    public static Date modifyTime(Date d, Integer minutes, Integer days) {
        // Create a new instance of Calendar using GregorianCalendar
        Calendar gc = new GregorianCalendar();

        // Set the time of the calendar to the given date 'd'
        gc.setTime(d);

        // Add/subtract the specified number of minutes if provided
        if (minutes != null) {
            gc.add(Calendar.MINUTE, minutes);
        }

        // Add/subtract the specified number of days if provided
        if (days != null) {
            gc.add(Calendar.DATE, days);
        }


        // Get the modified time after addition/subtraction
        return gc.getTime();
    }

        // Method to format Date object into a String in the format "yyyy-MM-dd'T'HH:mm:ss"
        //Used for StartDateTime and EndDateTime
        public static String formatDateTime(Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formatter for date
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); // Formatter for time
            return dateFormat.format(date) + "T" + timeFormat.format(date); // Combine date and time
        }

    //Formats the ScheduleDateTime based on whether the dates are the same.
    public static String formatScheduleDateTime(Date dt1, Date dt2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sameDayFormat = new SimpleDateFormat("MMM d, yyyy h:mm a");
        SimpleDateFormat sameDayEndFormat = new SimpleDateFormat("h:mm a");
        SimpleDateFormat differentDayFormat = new SimpleDateFormat("MMM d, yyyy h:mm a");

        // Check if the dates are the same
        if (dateFormat.format(dt1).equals(dateFormat.format(dt2))) {
            // Format for the same day: "MMM d, yyyy h:mm a - h:mm a"
            return sameDayFormat.format(dt1) + " - " + sameDayEndFormat.format(dt2);
        } else {
            // Format for different days: "MMM d, yyyy h:mm a - MMM d, yyyy h:mm a"
            return differentDayFormat.format(dt1) + " - " + differentDayFormat.format(dt2);
        }
    }

    //Data Structure for add more ZoneTimes as "America/New_York" for Eastern Daylight Time,
    // Eastern Standard Time and Eastern Time that is not on the ZoneId Java Class.
    //If we need to add more ZoneTimes, they should be added on mapZone variable.
    // Generate mapped timezones
    public static Map<String, String> generateMappedTimeZones() {
        // Create a map with short timezone IDs as keys and full timezone IDs as values
        Map<String, String> mapZone = new HashMap<>(ZoneId.SHORT_IDS);

        // Manually add specific timezone mappings
        mapZone.put("EDT", "America/New_York"); // Eastern Daylight Time
        mapZone.put("EST", "America/New_York"); // Eastern Standard Time
        mapZone.put("ET", "America/New_York");  // Eastern Time
        mapZone.put("CDT", "America/Chicago");  // Central Daylight Time
        mapZone.put("CT", "America/Chicago");   // Central Time
        // Add more timezones if necessary

        // Return the completed map of timezone mappings
        return mapZone;
    }


    public static String getDateTimeByZoneID(String timezone) {
        // Get the mapped timezones
        Map<String, String> mapZone = generateMappedTimeZones();

        // Find the ZoneId from the map
        ZoneId zoneId = ZoneId.of(mapZone.getOrDefault(timezone, "UTC"));

        // Get the current date and time in the given time zone
        ZonedDateTime dateTime = ZonedDateTime.now(zoneId);

        // Format the date and time
        String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));

        // Return the formatted date and time string
        return formattedDateTime;
    }

    // Get current time according to given timezone
    public static String getCurrentTimeForZone(String timezone) throws Exception {
        Map<String, String> mapZone = generateMappedTimeZones();
        return new SimpleDateFormat("yyyy-MM-d hh:mm:ss a").format(Functions.getDateTimeByZoneId(mapZone.get(timezone)));
    }

    // Parse date string to Date object
    public static Date parseDateString(String dateString) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse(dateString);
    }

    // Adjust the date by a given number of minutes
    public static Date adjustDateByMinutes(Date date, int minutes) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    // Format date to specified pattern
    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    // Format two dates to a combined date-time string
    public static String formatCombinedDateTime(Date dt1, Date dt2) {
        String DateTime1 = formatDate(dt1, "MMM d, yyyy");
        String DateTime2 = formatDate(dt2, "MMM d, yyyy");
        String DateTime3 = formatDate(dt1, "MMM d, yyyy h:mm a");
        if (DateTime1.equals(DateTime2)) {
            String DateTime4 = formatDate(dt2, "h:mm a");
            return DateTime3 + " - " + DateTime4;
        } else {
            String DateTime5 = formatDate(dt2, "MMM d, yyyy h:mm a");
            return DateTime3 + " - " + DateTime5;
        }
    }
}
