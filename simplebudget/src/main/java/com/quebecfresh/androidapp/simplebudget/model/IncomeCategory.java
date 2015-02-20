package com.quebecfresh.androidapp.simplebudget.model;

import java.math.BigDecimal;

/**
 * Created by Tong Huang on 2015-02-19, 9:00 AM.
 */
public class IncomeCategory extends Category {

    public IncomeCategory() {
    }

    public IncomeCategory(String name){
        super.setName(name);
    }

    public IncomeCategory(String name, Cycle cycle){
        super.setName(name);
        super.setCycle(cycle);
    }

    public IncomeCategory(String name, Cycle cycle, BigDecimal budgetAmount){
        super.setName(name);
        super.setCycle(cycle);
        super.setBudgetAmount(budgetAmount);
    }
}
