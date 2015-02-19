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
    MathContext mathContext = new MathContext(20, RoundingMode.HALF_UP);
    BigDecimal balance = new BigDecimal(0, mathContext);
    Currency currency = Currency.getInstance(Locale.CANADA);
    String accountNumber="";
    public Account() {
        super();
        balance.setScale(2);
    }

    public Account(String name) {
        super(name);
        balance.setScale(2);
    }

    public Account(String name, String note) {
        super(name, note);
        balance.setScale(2);
    }

    public Account(Integer id, String name, String note) {
        super(id, name, note);
        balance.setScale(2);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
