package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Account;

import static com.quebecfresh.androidapp.simplebudget.model.Account.Contract.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tong Huang on 2015-02-21, 9:00 PM.
 */
public class AccountPersist {

    private SQLiteDatabase db;

    public AccountPersist(SQLiteDatabase db) {
        this.db = db;
    }

    public Account insert(Account account) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, account.getName());
        contentValues.put(_NUMBER, account.getAccountNumber());
        contentValues.put(_BALANCE, account.getBalance().toString());
        contentValues.put(_NOTE, account.getNote());
        long newRowId;
        newRowId = db.insert(_TABLE, null, contentValues);
        account.setId(newRowId);
        return account;
    }

    public Account read(Long rowId) {
        String sql = "Select * from " + _TABLE + " where "
                + _ID + " = " + rowId;

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        Account account = new Account();
        account.setId(rowId);
        account.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        account.setAccountNumber(cursor.getString(cursor.getColumnIndexOrThrow(_NUMBER)));
        account.setBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BALANCE))));
        account.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        return account;
    }

    public List<Account> readAll() {
        String sql = "Select * from " + _TABLE;

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<Account> accounts = new ArrayList<Account>();
        while (!cursor.isAfterLast()) {
            Account account = new Account();
            account.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            account.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            account.setAccountNumber(cursor.getString(cursor.getColumnIndexOrThrow(_NUMBER)));
            account.setBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BALANCE))));
            account.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            accounts.add(account);
            cursor.moveToNext();
        }
        return accounts;
    }


    public List<Account> readAllBalanceNotZero() {
        String sql = "Select * from " + _TABLE + " where " + _BALANCE + " != 0";

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<Account> accounts = new ArrayList<Account>();
        while (!cursor.isAfterLast()) {
            Account account = new Account();
            account.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            account.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            account.setAccountNumber(cursor.getString(cursor.getColumnIndexOrThrow(_NUMBER)));
            account.setBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BALANCE))));
            account.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            accounts.add(account);
            cursor.moveToNext();
        }
        return accounts;
    }


    public BigDecimal readTotalBalance() {
        String sql = "select sum(" + _BALANCE + ") as total from " + _TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        BigDecimal total;
        Double totalDouble = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        total = new BigDecimal(totalDouble);
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public Account update(Account account) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, account.getName());
        contentValues.put(_NUMBER, account.getAccountNumber());
        contentValues.put(_BALANCE, account.getBalance().toString());
        contentValues.put(_NOTE, account.getNote());
        db.update(_TABLE, contentValues, _ID + " = " + account.getId(), null);
        return account;
    }

    public Boolean delete(Long rowID) {
        this.db.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }



    public Boolean initialize() {

        Account account = new Account();
        account.setName("Bank account");
        account.setAccountNumber("000002");

        this.insert(account);

        account = new Account();
        account.setName("Cash on Hand");
        account.setAccountNumber("000001");
        account.setNote("Cash cash cash !!!!.");

        this.insert(account);


        account = new Account();
        account.setName("Credit card");
        account.setAccountNumber("000003");
        this.insert(account);
        return true;
    }

    public boolean save(Account account) {
        if (account.getId() > 0) {
            this.update(account);
        } else {
            this.insert(account);
        }

        return true;
    }

    public Boolean create() {
        this.db.execSQL(CREATE);
        return true;
    }

    public Boolean drop() {
        this.db.execSQL(DROP);
        return true;
    }

}
