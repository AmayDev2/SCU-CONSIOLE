package com.amay.scu.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class TimeUtil {

    public static final long MILLISECONDS_IN_A_DAY = 86400000;
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static long getDaysBetween(long start, long end) {
        return (end - start) / MILLISECONDS_IN_A_DAY;
    }

    public static long getDaysBetween(long start) {
        return getDaysBetween(start, System.currentTimeMillis());
    }

    public static String epochMilliToFormattedSystemTime(String issueTime, String format) {
        long epoch = Long.parseLong(issueTime);
        SimpleDateFormat formatter = new SimpleDateFormat(format==null?DATE_FORMAT:format);
        return formatter.format(new Date(epoch));
    }

    public static String epochMilliToFormattedSystemTime(long epoch, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format==null?DATE_FORMAT:format);
        return formatter.format(new Date(epoch));
    }


    public static String getCurrentTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getCurrentDateInEpoch() {
        return String.valueOf(LocalDate.now().atStartOfDay().toEpochSecond(java.time.ZoneOffset.UTC));
    }

    public static String datePickerToEpoch(LocalDate value) {
        return String.valueOf(value.atStartOfDay().toEpochSecond(java.time.ZoneOffset.UTC)*1000);
    }
}
