package com.quebecfresh.androidapp.simplebudget.model;

import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Tong Huang on 2015-03-21, 5:41 PM.
 */
public final class Utils {




    public List<Transaction> mergeAndSort2List(List<Expense> expenseList, List<Income> incomeList){
        List<Transaction> transactionList = new ArrayList<Transaction>();
        transactionList.addAll(expenseList);
        transactionList.addAll(incomeList);
        Collections.sort(transactionList);
        return transactionList;

    }

    public static String formatDate(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        return  simpleDateFormat.format(new Date(time));
    }

    public static String formatDateAndTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        return  simpleDateFormat.format(new Date(time));
    }

    public static BigDecimal calTotalExpense(List<Expense> expenseList) {
        BigDecimal total = new BigDecimal("0");
        for (Expense expense : expenseList) {
            total = total.add(expense.getAmount());
        }
        return total;
    }


    public static BigDecimal calTotalIncome(List<Income> incomeList) {
        BigDecimal total = new BigDecimal("0");
        for (Income income : incomeList) {
            total = total.add(income.getAmount());
        }
        return total;
    }

    public static BigDecimal calTotalExpenseBudgetAmount(List<ExpenseBudget> expenseBudgetList, Cycle cycle) {
        BigDecimal total = new BigDecimal("0");
        for (Budget budget : expenseBudgetList) {
            if (budget.getBudgetAmount().compareTo(new BigDecimal("0")) > 0) {
                total = total.add(budget.getBudgetAmount().multiply(new BigDecimal(budget.getCycle().ratio(cycle))));
            }

        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calTotalIncomeBudgetAmount(List<IncomeBudget> incomeBudgetList, Cycle cycle) {
        BigDecimal total = new BigDecimal("0");
        for (Budget budget : incomeBudgetList) {
            if (budget.getBudgetAmount().compareTo(new BigDecimal("0")) > 0) {
                total = total.add(budget.getBudgetAmount().multiply(new BigDecimal(budget.getCycle().ratio(cycle))));
            }

        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }


    public static Boolean isEditTextNumeric(EditText editText) {
        if (isEditTextEmpty(editText)) {
            return false;
        }
        return editText.getText().toString().matches("[-+]?\\d+(\\.\\d+)?");
    }

    public static Boolean isEditTextEmpty(EditText editText) {
        if (editText == null || editText.getText() == null || editText.getText().length() <= 0
                || editText.getText().toString().trim().length() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * @param cycle    specify a cycle eg. Weekly, Yearly.
     * @param calendar specify a date.
     * @return the begin date of cycle.
     */

    public static long getBeginOfCycle(Cycle cycle, Calendar calendar) {
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
        return begin;
    }

    /**
     * @param cycle    specify a cycle eg. Weekly, Yearly.
     * @param calendar specify a date.
     * @return the end date of cycle
     */
    public static long getEndOfCycle(Cycle cycle, Calendar calendar) {
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
        return end;
    }

    /**
     * @param calendar specify a date.
     * @return the instant that that day begin
     */
    private static long getBeginOfDay(Calendar calendar) {
        long originalTime = calendar.getTimeInMillis(); //Save time  for restoring
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long start = calendar.getTimeInMillis();
        calendar.setTimeInMillis(originalTime); //Restore time
        return start;
    }

    /**
     * @param calendar specify a date.
     * @return the instant that that day end
     */
    private static long getEndOfDay(Calendar calendar) {
        long originalTime = calendar.getTimeInMillis(); //Save time  for restoring
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        long end = calendar.getTimeInMillis();
        calendar.setTimeInMillis(originalTime); //Restore time
        return end;
    }


    private static long getBeginOfWeek(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        return getBeginOfDay(calendar);
    }

    private static long getEndOfWeek(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        return getEndOfDay(calendar);
    }

    private static long getBeginOfMonth(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getBeginOfDay(calendar);
    }

    private static long getEndOfMonth(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getEndOfDay(calendar);
    }

    private static long getBeginOfYear(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        return getBeginOfDay(calendar);
    }

    private static long getEndOfYear(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return getEndOfDay(calendar);
    }


    public static long getBeginOfPastCycle(Cycle cycle, Calendar current) {
        long end = current.getTimeInMillis();
        switch (cycle) {
            case Daily:
                current.add(Calendar.DAY_OF_YEAR, -1);
                end = current.getTimeInMillis();
                current.add(Calendar.DAY_OF_YEAR, 1);
                break;
            case Weekly:
                current.add(Calendar.WEEK_OF_YEAR, -1);
                end = current.getTimeInMillis();
                current.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case Every_2_Weeks:
                current.add(Calendar.WEEK_OF_YEAR, -2);
                end = current.getTimeInMillis();
                current.add(Calendar.WEEK_OF_YEAR, 2);
                break;
            case Monthly:
                current.add(Calendar.MONTH, -1);
                end = current.getTimeInMillis();
                current.add(Calendar.MONTH, 1);
                break;
            case Every_2_Months:
                current.add(Calendar.MONTH, -2);
                end = current.getTimeInMillis();
                current.add(Calendar.MONTH, 2);
                break;
            case Every_3_Months:
                current.add(Calendar.MONTH, -3);
                end = current.getTimeInMillis();
                current.add(Calendar.MONTH, 3);
                break;
            case Every_6_Months:
                current.add(Calendar.MONTH, -6);
                end = current.getTimeInMillis();
                current.add(Calendar.MONTH, 6);
                break;
            case Yearly:
                current.add(Calendar.YEAR, -1);
                end = current.getTimeInMillis();
                current.add(Calendar.YEAR, 1);
                break;
        }
        return end;
    }

    public static long getEndOfNextCycle(Cycle cycle, Calendar current) {
        long end = current.getTimeInMillis();
        switch (cycle) {
            case Daily:
                current.add(Calendar.DAY_OF_YEAR, 1);
                end = current.getTimeInMillis();
                current.add(Calendar.DAY_OF_YEAR, -1);
                break;
            case Weekly:
                current.add(Calendar.WEEK_OF_YEAR, 1);
                end = current.getTimeInMillis();
                current.add(Calendar.WEEK_OF_YEAR, -1);
                break;
            case Every_2_Weeks:
                current.add(Calendar.WEEK_OF_YEAR, 2);
                end = current.getTimeInMillis();
                current.add(Calendar.WEEK_OF_YEAR, -2);
                break;
            case Monthly:
                current.add(Calendar.MONTH, 1);
                end = current.getTimeInMillis();
                current.add(Calendar.MONTH, -1);
                break;
            case Every_2_Months:
                current.add(Calendar.MONTH, 2);
                end = current.getTimeInMillis();
                current.add(Calendar.MONTH, -2);
                break;
            case Every_3_Months:
                current.add(Calendar.MONTH, 3);
                end = current.getTimeInMillis();
                current.add(Calendar.MONTH, -3);
                break;
            case Every_6_Months:
                current.add(Calendar.MONTH, 6);
                end = current.getTimeInMillis();
                current.add(Calendar.MONTH, -6);
                break;
            case Yearly:
                current.add(Calendar.YEAR, 1);
                end = current.getTimeInMillis();
                current.add(Calendar.YEAR, -1);
                break;
        }
        return end;
    }


    private int calcDaysBetween2Dates(Calendar begin, Calendar end) {
        int days = 0;
        while (end.get(Calendar.YEAR) > begin.get(Calendar.YEAR) ||
                (end.get(Calendar.YEAR) == begin.get(Calendar.YEAR) &&
                        end.get(Calendar.DAY_OF_YEAR) > begin.get(Calendar.WEEK_OF_YEAR))) {
            begin.add(Calendar.DAY_OF_YEAR, 1);
            days++;
        }
        return days;
    }
}
