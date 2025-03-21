package utilities;

import java.text.SimpleDateFormat;
import java.util.*;

public class TimeHelper {

    // Parse date string to Date object
    public static Date parseDateString(String dateString) throws Exception {
        return new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
    }

    // Format date to specified pattern
    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String convertDateStringToString(String dateString) throws Exception {
        SimpleDateFormat format1 = new SimpleDateFormat("MMM dd yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
        Date date = format1.parse(dateString);
        return format2.format(date);
    }
}
