package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.content.Context;
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
public class AccountPersist  extends  Persist{


//    public AccountPersist(Context context) {
//        super(context);
//    }

    public Account insert(Account account, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, account.getName());
        contentValues.put(_NUMBER, account.getAccountNumber());
        contentValues.put(_BALANCE, account.getBalance().toString());
        contentValues.put(_NOTE, account.getNote());
        long newRowId;
        newRowId = database.insert(_TABLE, null, contentValues);
        account.setId(newRowId);
        return account;
    }

    public Account read(Long rowId, SQLiteDatabase database) {
        String sql = "Select * from " + _TABLE + " where "
                + _ID + " = " + rowId;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        Account account = new Account();
        account.setId(rowId);
        account.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        account.setAccountNumber(cursor.getString(cursor.getColumnIndexOrThrow(_NUMBER)));
        account.setBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BALANCE))));
        account.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        cursor.close();
        return account;
    }

    public List<Account> readAll(SQLiteDatabase database) {
        String sql = "Select * from " + _TABLE + " order  by " + _NAME + " asc";
        Cursor cursor = database.rawQuery(sql, null);
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
        cursor.close();
        return accounts;
    }


    public List<Account> readAllBalanceNotZero(SQLiteDatabase database) {
        String sql = "Select * from " + _TABLE + " where " + _BALANCE + " != 0";
        Cursor cursor = database.rawQuery(sql, null);
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
        cursor.close();
        return accounts;
    }


    public BigDecimal readTotalBalance(SQLiteDatabase database) {
        String sql = "select sum(" + _BALANCE + ") as total from " + _TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        BigDecimal total;
        Double totalDouble = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        total = new BigDecimal(totalDouble);
        cursor.close();
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public Account update(Account account, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, account.getName());
        contentValues.put(_NUMBER, account.getAccountNumber());
        contentValues.put(_BALANCE, account.getBalance().toString());
        contentValues.put(_NOTE, account.getNote());
        database.update(_TABLE, contentValues, _ID + " = " + account.getId(), null);
        return account;
    }

    public Boolean delete(Long rowID, SQLiteDatabase database) {
        database.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }





    public boolean save(Account account, SQLiteDatabase database) {
        if (account.getId() > 0) {
            this.update(account, database);
        } else {
            this.insert(account, database);
        }

        return true;
    }

    /**
     * This static method is only used by DatabaseHelper to create database;
     * @param db
     * @return
     */
    public static Boolean create(SQLiteDatabase db) {
        db.execSQL(CREATE);
        return true;
    }

    /**
     * This static method is only used by DatabaseHelper to drop table when Upgrade database;
     * @param db
     * @return
     */

    public static Boolean drop(SQLiteDatabase db) {
        db.execSQL(DROP);
        return true;
    }

    /**
     Insert initial account data into database;
     This static method is only used by DatabaseHelper;
     * @param db
     * @return
     */
    public static Boolean initialize(SQLiteDatabase db) {

        String[][] accounts = {{"Cash in hand", "00001"},{"Bank account","00002"},{"Credit card", "00003"}};

        ContentValues contentValues = new ContentValues();
        for( int i = 0 ; i < accounts.length; i++){
            contentValues.put(_NAME, accounts[i][0]);
            contentValues.put(_NUMBER, accounts[i][1]);
            db.insert(_TABLE, null, contentValues);
            contentValues.clear();
        }


        return true;
    }

}
