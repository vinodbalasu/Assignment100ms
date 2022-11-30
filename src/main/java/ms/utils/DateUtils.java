package ms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    /**
     * Purpose - to get current date and time
     * @return - String (returns date and time)
     */
    public static String getCurrentDateTime(String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy:HH.mm.ss");
        return formatter.format(new Date().getTime());
    }

    /* Convert long type milliseconds to format hh:mm:ss */
    public static String convertTimeToString(long miliSeconds) {
        int hrs = (int) TimeUnit.MILLISECONDS.toHours(miliSeconds) % 24;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(miliSeconds) % 60;
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(miliSeconds) % 60;
        return String.format("%02d:%02d:%02d", hrs, min, sec);
    }
}
