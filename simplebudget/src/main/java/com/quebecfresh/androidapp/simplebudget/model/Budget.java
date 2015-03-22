package com.quebecfresh.androidapp.simplebudget.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Tong Huang on 2015-02-19, 10:02 AM.
 */
public class Budget extends BaseData {
    private Cycle cycle = Cycle.Monthly;
    private BigDecimal budgetAmount = new BigDecimal("0");
    private Account account;
    private int cycleStartDate = 1; //The budget amount will be fill to balance on that day;
    //eg: cycle is weekly, possible value is 1,2,3,4,5,6,7. eg: cycle is monthly, possible value
    //is 1-the first day of that month, 5-5th of that month, 10-10th of the month, 15-15th of that month
    //20-20th of that month,25-25th of that month, 31-the last day of that month.
    //eg: cycle is yearly, possible value is 1- 1st January, 2-31st January, 3-1st July,
    // 4-31st July, 5-1st December, 6-31st December
    private Long lastPutDate = 0L;



    public Budget() {

    }

    public Budget(String name) {
        super.setName(name);
    }

    public Budget(String name, Cycle cycle) {
        super.setName(name);
        this.setCycle(cycle);
    }

    public Budget(String name, Cycle cycle, BigDecimal budgetAmount) {
        super.setName(name);
        this.setCycle(cycle);
        this.setBudgetAmount(budgetAmount);
    }


    private int calcDaysBetween2Dates(Calendar begin, Calendar end) {
        int days = 0;
        while (end.get(Calendar.YEAR) > begin.get(Calendar.YEAR) ||
                (end.get(Calendar.YEAR) == begin.get(Calendar.YEAR) &&
                        end.get(Calendar.DAY_OF_YEAR) > begin.get(Calendar.WEEK_OF_YEAR))) {
            begin.roll(Calendar.DAY_OF_YEAR, 1);
            days++;
        }
        return days;
    }

    private int calcWeeksBetween2Dates(Calendar begin, Calendar end, int dayOfWeek) {
        int weeks = 0;
        while (end.get(Calendar.YEAR) > begin.get(Calendar.YEAR) ||
                (end.get(Calendar.YEAR) == begin.get(Calendar.YEAR) &&
                        end.get(Calendar.WEEK_OF_YEAR) > begin.get(Calendar.WEEK_OF_YEAR))) {
            begin.roll(Calendar.WEEK_OF_YEAR, 1);
            weeks++;
        }

        if(end.get(Calendar.DAY_OF_WEEK) < dayOfWeek){
            weeks--;//if the end date's day of week is before specify day of week, it doesn't count
        }
        if(weeks < 0){  // Don't return minus, minimum is zero
            weeks = 0;
        }
        return weeks;
    }

    private int calcMonthsBetween2Dates(Calendar begin, Calendar end, int dayOfMonth) {
        int months = 0;
        while (end.get(Calendar.YEAR) > begin.get(Calendar.YEAR) ||
                (end.get(Calendar.YEAR) == begin.get(Calendar.YEAR) &&
                        end.get(Calendar.MONTH) > begin.get(Calendar.MONTH))) {
            begin.roll(Calendar.MONTH, 1);
            months++;
        }

        //if specify dayOfMonth is 31, it means the last day of that month, and if end is not the last
        //day of that month, it doesn't count

        if(dayOfMonth ==31){
            if( end.get(Calendar.DAY_OF_MONTH) <  end.getActualMaximum(Calendar.DAY_OF_MONTH) ){
                months--;
            }
        }else{
            //if the end date's dayOfMonth is before the specify dayOfMonth, it doesn't count.
            if(end.get(Calendar.DAY_OF_MONTH) < dayOfMonth){
                months--;
            }
        }

        if(months < 0){ //Don't return minus, minimum is zero;
            months = 0;
        }
        return months;
    }

    /**
     * Base on Cycle type, how many cycles since last time put money into pocket, budget amount
     * and if user selected RollOver option.
     */

    /*
    public void putMoneyIntoPocket(boolean rollOver) {
        Calendar now = Calendar.getInstance();
        Calendar lastPutDate = Calendar.getInstance();
        lastPutDate.setTime(new Date(this.lastPutDate));
        Integer cycleNumber = 0;
        BigDecimal amountPutting = new BigDecimal("0");
        //If now and lastPutDate is the same day, do nothing.
        if (calcDaysBetween2Dates(lastPutDate, now) == 0) {
            return;
        }
        switch (this.cycle) {
            case Daily:
                if (rollOver) {
                    cycleNumber = this.calcDaysBetween2Dates(lastPutDate, now);
                    amountPutting = this.budgetAmount.multiply(new BigDecimal(cycleNumber.toString()));
                    this.balance = this.balance.add(amountPutting);
                }else{
                    amountPutting = this.budgetAmount.subtract(this.balance);
                    this.balance = amountPutting;
                }
                this.account.setBalance(this.account.getBalance().subtract(amountPutting));
            case Weekly:
                if(rollOver){
                    cycleNumber = this.calcWeeksBetween2Dates(lastPutDate, now, cycleStartDate);
                    amountPutting = this.budgetAmount.multiply(new BigDecimal(cycleNumber.toString()));
                    this.balance = this.balance.add(amountPutting);
                }else{
                    this.balance = this.budgetAmount;
                }
                this.account.setBalance(this.account.getBalance().subtract(amountPutting));
            case Every_2_Weeks:
                if (now.get(Calendar.DAY_OF_WEEK) == this.cycleStartDate && (lastPutDate.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
                return true;
            }
            return false;

            case Monthly:

                if (now.get(Calendar.DAY_OF_MONTH) == this.cycleStartDate || (now.getActualMaximum(Calendar.DAY_OF_MONTH) <= this.cycleStartDate
                        && now.getActualMaximum(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH))) {
                    return true;
                }
                return false;
        }
        return false;
    }
*/
    /*
    Calculate how much will be fill to the budget balance;
     */
    public BigDecimal calcFillAmount() {
        GregorianCalendar gc = new GregorianCalendar();
        BigDecimal fillAmount = new BigDecimal("0");
        double day = 0;
        do {
            gc.roll(GregorianCalendar.DAY_OF_WEEK, true);
            day++;
        } while (gc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY);

        fillAmount = new BigDecimal(day / 7D).multiply(this.budgetAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
        return fillAmount;
    }

    public String getLastFillDateLabel() {
        Date date = new Date(this.lastPutDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public Long getLastPutDate() {
        return lastPutDate;
    }

    public void setLastPutDate(Long lastPutDate) {
        this.lastPutDate = lastPutDate;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal convertBudgetAmountTo(Cycle cycle) {
        BigDecimal convertedAmount = this.budgetAmount;
        convertedAmount = convertedAmount.multiply(new BigDecimal(this.cycle.ratio(cycle)));
        convertedAmount = convertedAmount.setScale(2, RoundingMode.HALF_UP);
        return convertedAmount;
    }

    public int getCycleStartDate() {
        return cycleStartDate;
    }

    public void setCycleStartDate(int cycleStartDate) {
        this.cycleStartDate = cycleStartDate;
    }


}
