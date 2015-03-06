package com.quebecfresh.androidapp.simplebudget.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Tong Huang on 2015-02-13, 9:04 AM.
 */
public class Expense extends BaseData{
    public enum EXPENSE_CYCLE{ANNUAL,MONTHLY,WEEKLY,DAILY,IRREGULAR}


    private ExpenseBudget category;
    private Payee payee;
    private BigDecimal amount;
    private Date dueDate;
    private Date paidDate;
    private Boolean isPaid = Boolean.FALSE;

    public Expense(){

    }

    public Expense(String name){
        super(name);
    }

    public Expense(String name, String note){
        super(name, note);
    }

    public Expense(Long id, String name, String note){
        super(id, name, note);
    }

    public ExpenseBudget getCategory() {
        return category;
    }

    public void setCategory(ExpenseBudget category) {
        this.category = category;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
