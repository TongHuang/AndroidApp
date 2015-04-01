package com.quebecfresh.androidapp.simplebudget.model;

import android.provider.BaseColumns;

import java.math.BigDecimal;

/**
 * Created by Tong Huang on 2015-03-31, 10:14 AM.
 */
public class ExpenseBudgetHistory extends  BaseData{

    private Long beginTime;
    private  Long endTime;
    private Long budgetID = -1L;
    private BigDecimal budgetAmount = new BigDecimal("0");

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(Long budgetID) {
        this.budgetID = budgetID;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public static final class Contract implements BaseColumns {
        public static final String _TABLE = "expense_budget_history";
        public static final String _NAME = "name";
        public static final String _NOTE = "note";
        public static final String _BEGIN_TIME = "begin_time";
        public static final String _END_TIME = "end_time";
        public static final String _BUDGET_ID = "budget_id";

        public static final String CREATE = "create table " + _TABLE + "(" + _ID
                + TYPE_ID + COMMA + _NAME + TYPE_TEXT + COMMA  + _NOTE + TYPE_TEXT + COMMA
                + _BEGIN_TIME + TYPE_INTEGER + COMMA + _END_TIME + TYPE_INTEGER
                + COMMA + _BUDGET_ID + TYPE_INTEGER  + ")";
        public static final String DROP = "drop table if exists " + _TABLE;

    }
}
