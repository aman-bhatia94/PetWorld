package com.ateam.petworld.utils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DateFunctions {

    public static long calculateNoOfDays(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }
}
