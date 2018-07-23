package org.chat21.android.tilewidget.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by stefanodp91 on 04/05/18.
 */

public class TimeUtils {

    private static final String SERVER_TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String DEFAULT_TIMEZONE = "UTC";

    public static long timeToMillisTimestamp(String timeToConvert) throws ParseException {
        TimeZone utc = TimeZone.getTimeZone(DEFAULT_TIMEZONE);
        SimpleDateFormat f = new SimpleDateFormat(SERVER_TIMESTAMP_PATTERN);
        f.setTimeZone(utc);
        GregorianCalendar cal = new GregorianCalendar(utc);
        cal.setTime(f.parse(timeToConvert));
        return cal.getTimeInMillis();
    }
}
