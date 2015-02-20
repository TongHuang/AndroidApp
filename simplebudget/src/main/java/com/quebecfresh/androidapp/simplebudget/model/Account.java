package com.quebecfresh.androidapp.simplebudget.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Tong Huang on 2015-02-18, 7:02 AM.
 */
public class Account extends BaseData {
    BigDecimal balance = new BigDecimal("0");
    Currency currency = Currency.getInstance(Locale.CANADA);
    String accountNumber="";
    public Account() {
        super();
    }

    public Account(String name) {
        super(name);
    }

    public Account(String name, String note) {
        super(name, note);
    }

    public Account(Integer id, String name, String note) {
        super(id, name, note);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
