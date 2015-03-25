package com.quebecfresh.androidapp.simplebudget.model;

import android.widget.EditText;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Tong Huang on 2015-03-21, 5:41 PM.
 */
public  final  class Utils {


    public static BigDecimal calTotalIncome(List<Income> incomeList){
        BigDecimal total = new BigDecimal("0");
        for(Income income : incomeList){
            total = total.add(income.getAmount());
        }
        return  total;
    }

    public static Boolean isEditTextNumeric(EditText editText){
        if(isEditTextEmpty(editText)){
            return false;
        }
        return editText.getText().toString().matches("[-+]?\\d+(\\.\\d+)?");
    }

    public static Boolean isEditTextEmpty(EditText editText){
        if(editText == null || editText.getText() == null || editText.getText().length() <= 0
                || editText.getText().toString().trim().length() <= 0){
            return true;
        }
        return false;
    }

    public static long getBeginOfCycle(Cycle cycle, Calendar calendar){
        long begin = 0;
        long originalTime = calendar.getTimeInMillis();
        switch (cycle) {
            case Weekly:
                begin = Utils.getBeginOfWeek(calendar);
                break;
            case Monthly:
                begin = Utils.getBeginOfMonth(calendar);
                break;
            case Yearly:
                begin = Utils.getBeginOfYear(calendar);
                break;
        }
        calendar.setTimeInMillis(originalTime); //Restore time
        return  begin;
    }

    public static long getEndOfCycle(Cycle cycle, Calendar calendar){
        long end = 0;
        long originalTime = calendar.getTimeInMillis();
        switch (cycle) {
            case Weekly:
                end = Utils.getEndOfWeek(calendar);
                break;
            case Monthly:
                end = Utils.getEndOfMonth(calendar);
                break;
            case Yearly:
                end = Utils.getEndOfYear(calendar);
                break;
        }
        calendar.setTimeInMillis(originalTime); //Restore time
        return  end;
    }

    private static long getBeginOfDay(Calendar calendar){
        long originalTime = calendar.getTimeInMillis(); //Save time  for restoring
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long start =  calendar.getTimeInMillis();
        calendar.setTimeInMillis(originalTime); //Restore time
        return  start;
    }

    private static  long getEndOfDay(Calendar calendar){
        long originalTime = calendar.getTimeInMillis(); //Save time  for restoring
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long end = calendar.getTimeInMillis();
        calendar.setTimeInMillis(originalTime); //Restore time
        return  end;
    }

    private static long getBeginOfWeek(Calendar calendar){
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        return  getBeginOfDay(calendar);
    }

    private static long getEndOfWeek(Calendar calendar){
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        return getEndOfDay(calendar);
    }

    private static long getBeginOfMonth(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getBeginOfDay(calendar);
    }

    private static long getEndOfMonth(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return  getEndOfDay(calendar);
    }

    private static long getBeginOfYear(Calendar calendar){
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        return getBeginOfDay(calendar);
    }

    private static long getEndOfYear(Calendar calendar){
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return getEndOfDay(calendar);
    }
}
