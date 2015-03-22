package com.quebecfresh.androidapp.simplebudget.model;

import java.util.Calendar;

/**
 * Created by Tong Huang on 2015-03-21, 5:41 PM.
 */
public  final  class Utils {

    public static long getStartOfDay(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long start =  calendar.getTimeInMillis();
        return  start;
    }

    public static  long getEndOfDay(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long end = calendar.getTimeInMillis();
        return  end;
    }

    public static long getStartOfWeek(Calendar calendar){
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        return  getStartOfDay(calendar);
    }

    public static long getEndOfWeek(Calendar calendar){
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        return getEndOfDay(calendar);
    }

    public static long getStartOfMonth(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getStartOfDay(calendar);
    }

    public static long getEndOfMonth(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return  getEndOfDay(calendar);
    }

    public static long getStartOfYear(Calendar calendar){
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        return getStartOfDay(calendar);
    }

    public static long getEndOfYear(Calendar calendar){
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return getEndOfDay(calendar);
    }
}
