package com.quebecfresh.androidapp.simplebudget.model;

import android.content.Context;
import android.provider.BaseColumns;

import com.quebecfresh.androidapp.simplebudget.R;

import java.math.BigDecimal;

/**
 * Created by Tong Huang on 2015-02-19, 9:00 AM.
 */
public class IncomeCategory extends Category {

    public enum INCOME_CATEGORY_GROUP {
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

    private INCOME_CATEGORY_GROUP categoryGroup = INCOME_CATEGORY_GROUP.EMPLOYMENT;

    public IncomeCategory() {
    }

    public IncomeCategory(String name) {
        super.setName(name);
    }

    public IncomeCategory(String name, Cycle cycle) {
        super.setName(name);
        super.setCycle(cycle);
    }

    public IncomeCategory(String name, Cycle cycle, BigDecimal budgetAmount) {
        super.setName(name);
        super.setCycle(cycle);
        super.setBudgetAmount(budgetAmount);
    }

    public INCOME_CATEGORY_GROUP getCategoryGroup() {
        return categoryGroup;
    }

    public void setCategoryGroup(INCOME_CATEGORY_GROUP categoryGroup) {
        this.categoryGroup = categoryGroup;
    }

    public static abstract class Contract implements BaseColumns {
        public static final String _TABLE = "income_category";
        public static final String _NAME = "name";
        public static final String _NOTE = "note";
        public static final String _CYCLE = "cycle";
        public static final String _BUDGET_AMOUNT = "budget_amount";
        public static final String _CATEGORY_GROUP = "category_group";

        public static final String CREATE = "Create table " + _TABLE + " (" + _ID + TYPE_ID
                + COMMA + _NAME + TYPE_TEXT + COMMA + _NOTE + TYPE_TEXT + COMMA + _CYCLE
                + TYPE_TEXT + COMMA + _BUDGET_AMOUNT + TYPE_TEXT + COMMA + _CATEGORY_GROUP + TYPE_TEXT + ")";
        public static final String DROP = "Drop table if exists " + _TABLE;

    }
}
