package com.quebecfresh.androidapp.simplebudget.model;

import android.content.Context;
import android.provider.BaseColumns;

import java.math.BigDecimal;

/**
 * Created by Tong Huang on 2015-02-13, 9:23 AM.
 */
public class ExpenseBudget extends Budget {


    private Boolean rollOver = Boolean.TRUE;
    private BigDecimal unusedBalance = new BigDecimal("0");


    public enum EXPENSE_BUDGET_CATEGORY {
        FOODS, SHELTER, UTILITIES, TRANSPORTATION, OTHERS;

        public String getLabel(Context context) {
            switch (this) {
                case FOODS:
                    return "Foods";
                case SHELTER:
                    return "Shelter";
                case UTILITIES:
                    return "Utilities";
                case TRANSPORTATION:
                    return "Transportation";
                default:
                    return "Others";
            }
        }
    }

    private EXPENSE_BUDGET_CATEGORY expenseBudgetCategory = EXPENSE_BUDGET_CATEGORY.FOODS;

    public ExpenseBudget() {

    }

    public ExpenseBudget(String name) {
        super.setName(name);
    }

    public ExpenseBudget(String name, Cycle cycle) {
        super.setName(name);
        super.setCycle(cycle);
    }

    public ExpenseBudget(String name, Cycle cycle, BigDecimal budgetAmount) {
        super.setName(name);
        super.setCycle(cycle);
        super.setBudgetAmount(budgetAmount);
    }

    public EXPENSE_BUDGET_CATEGORY getExpenseBudgetCategory() {
        return expenseBudgetCategory;
    }

    public void setExpenseBudgetCategory(EXPENSE_BUDGET_CATEGORY expenseBudgetCategory) {
        this.expenseBudgetCategory = expenseBudgetCategory;
    }

    public Boolean getRollOver() {
        return rollOver;
    }

    public void setRollOver(Boolean rollOver) {
        this.rollOver = rollOver;
    }

    public BigDecimal getUnusedBalance() {
        return unusedBalance;
    }

    public void setUnusedBalance(BigDecimal unusedBalance) {
        this.unusedBalance = unusedBalance;
    }

    public static final class Contract implements BaseColumns {
        public static final String _TABLE = "expense_budget";
        public static final String _NAME = "name";
        public static final String _CYCLE = "cycle";
        public static final String _BUDGET_AMOUNT = "budget_amount";
        public static final String _NOTE = "note";
        public static final String _CATEGORY_GROUP = "category_group";
        public static final String _UNUSED_BALANCE = "_unused_balance";
        public static final String _ROLL_OVER = "roll_over";
        public static final String _ACCOUNT_ID = "_account_id";
        public static final String _LAST_FILL_DATE = "_last_fill_date";

        public static final String CREATE = "create table " + _TABLE + "(" + _ID
                + TYPE_ID + COMMA + _NAME + TYPE_TEXT + COMMA + _CYCLE + TYPE_TEXT + COMMA
                + _BUDGET_AMOUNT + TYPE_TEXT + COMMA + _NOTE + TYPE_TEXT + COMMA
                + _CATEGORY_GROUP + TYPE_TEXT + COMMA + _UNUSED_BALANCE + TYPE_TEXT
                + COMMA + _ROLL_OVER + TYPE_INTEGER + COMMA + _ACCOUNT_ID + TYPE_INTEGER
                + COMMA + _LAST_FILL_DATE + TYPE_INTEGER + ")";
        public static final String DROP = "drop table if exists " + _TABLE;

    }
}
