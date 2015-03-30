package com.quebecfresh.androidapp.simplebudget.model;

import android.provider.BaseColumns;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

/**
 * Created by Tong Huang on 2015-03-15, 11:06 PM.
 */
public class Income extends BaseData {

    private IncomeBudget incomeBudget;
    private Account account;
    private BigDecimal amount = new BigDecimal("0");
    private Long receivedDate = 0L;
    private Boolean confirmed = Boolean.FALSE;


    public IncomeBudget getIncomeBudget() {
        return incomeBudget;
    }

    public void setIncomeBudget(IncomeBudget incomeBudget) {
        this.incomeBudget = incomeBudget;
    }

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

    public Long getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Long receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getReceivedDateShortLabel(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        return simpleDateFormat.format(this.receivedDate);
    }

    public String getReceivedDateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");
        return simpleDateFormat.format(this.receivedDate);
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public static abstract class Contract implements BaseColumns {
        public static final String _TABLE = "income";
        public static final String _NAME = "name";
        public static final String _NOTE = "note";
        public static final String _BUDGET_ID = "budget_id";
        public static final String _AMOUNT = "amount";
        public static final String _RECEIVED_DATE = "received_date";
        public static final String _ACCOUNT_ID = "account_id";
        public static final String _CONFIRMED = "confirmed";

        public static final String CREATE = "create table " + _TABLE + " (" + _ID + TYPE_ID
                + COMMA + _NAME + TYPE_TEXT + COMMA + _NOTE + TYPE_TEXT + COMMA + _BUDGET_ID
                + TYPE_INTEGER + COMMA + _AMOUNT + TYPE_TEXT + DEFAULT_ZERO + COMMA + _RECEIVED_DATE
                + TYPE_INTEGER + COMMA + _ACCOUNT_ID + TYPE_INTEGER + COMMA + _CONFIRMED
                + TYPE_INTEGER + ")";
        public static final String DROP = "Drop table if exists " + _TABLE;
    }
}
