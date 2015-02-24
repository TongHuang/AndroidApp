package com.quebecfresh.androidapp.simplebudget.model;

import android.provider.BaseColumns;

import java.math.BigDecimal;
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

    public Account(Long id, String name, String note) {
        super(id, name, note);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getBalanceString(){
        return this.getBalance().toString();
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setBalanceString(String balanceString){
        this.setBalance(new BigDecimal(balanceString));
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



//    public static  String CREATE="CREATE TABLE " + Contract._TABLENAME + " ( " + Contract._ID + TYPE_ID
//            + COMMA + Contract._NAME + TYPE_TEXT + COMMA + Contract._NUMBER + TYPE_TEXT
//            + COMMA + Contract._BALANCE + TYPE_TEXT + COMMA + Contract._NOTE + TYPE_TEXT + " ) ";
//    public static final String DROP="DROP TABLE IF EXISTS " + Contract._TABLENAME;

    public static  abstract class Contract implements BaseColumns {
        public static final String _TABLENAME ="accounts";
        public static final String _NAME="name";
        public static final String _NUMBER = "number";
        public static final String _BALANCE="balance";
        public static final String _NOTE="note";

        public static  String CREATE="CREATE TABLE " + _TABLENAME + " ( " + _ID + TYPE_ID
                + COMMA + _NAME + TYPE_TEXT + COMMA + _NUMBER + TYPE_TEXT
                + COMMA + _BALANCE + TYPE_TEXT + COMMA + _NOTE + TYPE_TEXT + " ) ";
        public static final String DROP="DROP TABLE IF EXISTS " + _TABLENAME;

    }
}
