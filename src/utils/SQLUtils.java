package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dave on 25/03/2017.
 */
public class SQLUtils {
    private static final String dateTimeStringFormat = "yyyy-MM-dd HH:mm";

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(dateTimeStringFormat);

    public static String timeToString(Date date) {
        return dateTimeFormat.format(date);
    }

    public static Date parseDateTime(String string) {
        try {
            return dateTimeFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
