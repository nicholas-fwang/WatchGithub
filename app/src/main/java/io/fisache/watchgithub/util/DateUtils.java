package io.fisache.watchgithub.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
public class DateUtils {

    private final static long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

    public static Date getCurrentDate() {
        return new Date(Calendar.getInstance().getTime().getTime());
    }

    public static Date convertStringToDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        java.util.Date parsed = null;
        try {
            parsed = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(parsed.getTime());
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return sdf.format(date);
    }

    public static long differInDate(Date from, Date to) {
        return (to.getTime() - from.getTime()) / MILLISECONDS_IN_DAY;
    }

    public static long getTermsFromLastPushed(String pushed_at) {
        Date from = convertStringToDate(pushed_at);
        Date to = getCurrentDate();
        return differInDate(from, to);
    }

}
