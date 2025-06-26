package com.amay.scu.util;

import java.time.*;

public class EpochRangeUtil {

    /**
     * Returns a long array of size 2:
     * [0] = start epoch (30 days ago at 00:00)
     * [1] = end epoch (today at 23:59:59)
     *
     * @param zoneId the ZoneId to use (e.g., ZoneId.of("Asia/Kolkata"))
     * @return long[] {startEpoch, endEpoch}
     */
    public static long[] getLast30DaysRange(ZoneId zoneId) {
        LocalDate today = LocalDate.now(zoneId);

        LocalDateTime startDateTime = today.minusDays(30).atStartOfDay();
        LocalDateTime endDateTime = today.atTime(23, 59, 59);

        long startEpoch = startDateTime.atZone(zoneId).toInstant().toEpochMilli();
        long endEpoch = endDateTime.atZone(zoneId).toInstant().toEpochMilli();

        return new long[] { startEpoch, endEpoch };
    }

    /**
     * Overload that uses the system default time zone.
     */
    public static long[] getLast30DaysRange() {
        return getLast30DaysRange(ZoneId.systemDefault());
    }
}
