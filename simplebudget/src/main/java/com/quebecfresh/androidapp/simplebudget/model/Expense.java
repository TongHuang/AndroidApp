package com.quebecfresh.androidapp.simplebudget.model;

import android.provider.BaseColumns;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tong Huang on 2015-02-13, 9:04 AM.
 */
public class Expense extends BaseData {

    private Account account;
    private ExpenseBudget expenseBudget;
    private BigDecimal amount;
    private Long spentDate;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ExpenseBudget getExpenseBudget() {
        return expenseBudget;
    }

    public void setExpenseBudget(ExpenseBudget expenseBudget) {
        this.expenseBudget = expenseBudget;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSpendDateShortLabel(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        return simpleDateFormat.format(this.spentDate);
    }

    public String getSpendDateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");
        return simpleDateFormat.format(this.spentDate);
    }

    public Long getSpentDate() {
        return spentDate;
    }

    public void setSpentDate(Long spentDate) {
        this.spentDate = spentDate;
    }

    public static abstract class Contract implements BaseColumns {
        public static final String _TABLE = "expense";
        public static final String _NAME = "name";
        public static final String _NOTE = "note";
        public static final String _BUDGET_ID = "budget_id";
        public static final String _AMOUNT = "amount";
        public static final String _SPENT_DATE = "spent_date";

        public static final String CREATE = "Create table " + _TABLE + " (" + _ID + TYPE_ID
                + COMMA + _NAME + TYPE_TEXT + COMMA + _NOTE + TYPE_TEXT + COMMA + _BUDGET_ID
                + TYPE_INTEGER + COMMA + _AMOUNT + TYPE_TEXT + DEFAULT_ZERO +  COMMA + _SPENT_DATE
                + TYPE_INTEGER + ")";
        public static final String DROP = "drop table if exists " + _TABLE;
    }
}
