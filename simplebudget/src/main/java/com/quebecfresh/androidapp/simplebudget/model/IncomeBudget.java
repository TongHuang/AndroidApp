package com.quebecfresh.androidapp.simplebudget.model;

import android.content.Context;
import android.provider.BaseColumns;

import com.quebecfresh.androidapp.simplebudget.R;

import java.math.BigDecimal;

/**
 * Created by Tong Huang on 2015-02-19, 9:00 AM.
 */
public class IncomeBudget extends Budget {

    private BigDecimal mUnrealizedBalance;

    public enum INCOME_BUDGET_CATEGORY {
        EMPLOYMENT, GOVERNMENT_BENEFIT, INVESTMENT, OTHERS;

        public String getLabel(Context context) {
            switch (this) {
                case EMPLOYMENT:
                    return context.getString(R.string.Income_Category_Group_Employment);
                case GOVERNMENT_BENEFIT:
                    return context.getString(R.string.Income_Category_Group_Government_Benefit);
                case INVESTMENT:
                    return context.getString(R.string.Income_Category_Group_Investment);
                default:
                    return context.getString(R.string.Income_Category_Group_Others);
            }
        }
    }

    private INCOME_BUDGET_CATEGORY incomeBudgetCategory = INCOME_BUDGET_CATEGORY.EMPLOYMENT;

    public IncomeBudget() {
    }

    public IncomeBudget(String name) {
        super.setName(name);
    }

    public IncomeBudget(String name, Cycle cycle) {
        super.setName(name);
        super.setCycle(cycle);
    }

    public IncomeBudget(String name, Cycle cycle, BigDecimal budgetAmount) {
        super.setName(name);
        super.setCycle(cycle);
        super.setBudgetAmount(budgetAmount);
    }

    public INCOME_BUDGET_CATEGORY getIncomeBudgetCategory() {
        return incomeBudgetCategory;
    }

    public void setIncomeBudgetCategory(INCOME_BUDGET_CATEGORY incomeBudgetCategory) {
        this.incomeBudgetCategory = incomeBudgetCategory;
    }

    public BigDecimal getUnrealizedBalance() {
        return mUnrealizedBalance;
    }

    public void setUnrealizedBalance(BigDecimal unrealizedBalance) {
        this.mUnrealizedBalance = unrealizedBalance;
    }

    public static abstract class Contract implements BaseColumns {
        public static final String _TABLE = "income_budget";
        public static final String _NAME = "name";
        public static final String _NOTE = "note";
        public static final String _CYCLE = "cycle";
        public static final String _BUDGET_AMOUNT = "budget_amount";
        public static final String _BUDGET_CATEGORY = "budget_category";
        public static final String _UNREALIZED_BALANCE = "_unrealized_balance";
        public static final String _ROLL_OVER = "roll_over";
        public static final String _ACCOUNT_ID = "account_id";

        public static final String CREATE = "Create table " + _TABLE + " (" + _ID + TYPE_ID
                + COMMA + _NAME + TYPE_TEXT + COMMA + _NOTE + TYPE_TEXT + COMMA + _CYCLE
                + TYPE_TEXT + COMMA + _BUDGET_AMOUNT + TYPE_TEXT + DEFAULT_ZERO +  COMMA
                + _BUDGET_CATEGORY  + TYPE_TEXT + COMMA + _UNREALIZED_BALANCE + TYPE_TEXT
                + DEFAULT_ZERO + COMMA + _ROLL_OVER + TYPE_INTEGER + DEFAULT_ONE
                + COMMA +  _ACCOUNT_ID + TYPE_INTEGER +  ")";
        public static final String DROP = "Drop table if exists " + _TABLE;

    }
}
