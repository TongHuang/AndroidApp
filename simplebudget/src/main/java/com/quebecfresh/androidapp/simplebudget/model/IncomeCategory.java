package com.quebecfresh.androidapp.simplebudget.model;

import java.math.BigDecimal;

/**
 * Created by Tong Huang on 2015-02-19, 9:00 AM.
 */
public class IncomeCategory extends BaseData {
    private Cycle cycle;
    private BigDecimal budgetAmount = new BigDecimal("0");

    public IncomeCategory() {
    }

    public IncomeCategory(String name) {
        super.setName(name);
    }

    public IncomeCategory(String name, Cycle cycle) {
        super.setName(name);
        this.setCycle(cycle);
    }

    public IncomeCategory(String name, Cycle cycle, BigDecimal budgetAmount) {
        super.setName(name);
        this.setCycle(cycle);
        this.setBudgetAmount(budgetAmount);
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
}
