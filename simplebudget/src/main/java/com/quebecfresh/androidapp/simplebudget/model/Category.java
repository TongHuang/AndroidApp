package com.quebecfresh.androidapp.simplebudget.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Tong Huang on 2015-02-19, 10:02 AM.
 */
public class Category extends  BaseData{
    private Cycle cycle = Cycle.Monthly;
    private BigDecimal budgetAmount = new BigDecimal("0");
    private BigDecimal unusedBalance = new BigDecimal("0");
    private Boolean rollOver = Boolean.TRUE;


    public Category(){

    }

    public Category(String name){
        super.setName(name);
    }

    public Category(String name, Cycle cycle){
        super.setName(name);
        this.setCycle(cycle);
    }

    public Category(String name, Cycle cycle, BigDecimal budgetAmount){
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

    public BigDecimal getUnusedBalance() {
        return unusedBalance;
    }

    public void setUnusedBalance(BigDecimal unusedBalance) {
        this.unusedBalance = unusedBalance;
    }

    public Boolean getRollOver() {
        return rollOver;
    }

    public void setRollOver(Boolean rollOver) {
        this.rollOver = rollOver;
    }

    public BigDecimal convertBudgetAmountTo(Cycle cycle){
        BigDecimal convertedAmount = new BigDecimal("0");
        convertedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        convertedAmount = this.budgetAmount;
        convertedAmount = convertedAmount.multiply(new BigDecimal(this.cycle.ratio(cycle)));
        return convertedAmount;
    }
}
