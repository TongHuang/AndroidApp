package com.quebecfresh.androidapp.simplebudget.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by Tong Huang on 2015-04-23, 3:20 AM.
 */
public class Transaction extends  BaseData {

    private Account account;
    private BigDecimal amount = new BigDecimal("0");
    private Long date = 0L;


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getDateShortLabel(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        return simpleDateFormat.format(this.date);

    }

    public String getDateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");
        return simpleDateFormat.format(this.date);
    }
}
