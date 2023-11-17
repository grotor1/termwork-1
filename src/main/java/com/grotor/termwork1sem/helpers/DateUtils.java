package com.grotor.termwork1sem.helpers;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {
    private static final Calendar cal = Calendar.getInstance();
    public static String getYear(Date date) {
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    public static String getFullDate(Date date) {
        cal.setTime(date);
        DateFormat df =  new SimpleDateFormat("dd MMM yyy");
        return df.format(cal.getTime());
    }
}
