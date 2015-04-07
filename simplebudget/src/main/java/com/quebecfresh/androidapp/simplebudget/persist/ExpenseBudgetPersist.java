package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget.Contract.*;

/**
 * Created by Tong Huang on 2015-02-24, 5:44 AM.
 */
public class ExpenseBudgetPersist extends Persist {


    public ExpenseBudget insert(ExpenseBudget expenseBudget, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expenseBudget.getName());
        contentValues.put(_CYCLE, expenseBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, expenseBudget.getBudgetAmount().toString());
        contentValues.put(_NOTE, expenseBudget.getNote());
        contentValues.put(_BUDGET_CATEGORY, expenseBudget.getExpenseBudgetCategory().name());
        contentValues.put(_UNUSED_BALANCE, expenseBudget.getUnusedBalance().toString());
        contentValues.put(_ROLL_OVER, expenseBudget.getRollOver() == Boolean.TRUE ? 1 : 0);
        contentValues.put(_ACCOUNT_ID, expenseBudget.getAccount().getId());
        contentValues.put(_LAST_FILL_DATE, expenseBudget.getLastFillDate());
        contentValues.put(_CYCLE_START_DATE, expenseBudget.getCycleStartDate());
        Long rowID = database.insert(_TABLE, null, contentValues);
        expenseBudget.setId(rowID);
        return expenseBudget;
    }

    public ExpenseBudget read(Long rowID, SQLiteDatabase database) {
        String sql = "select * from " + _TABLE + " where " + _ID + "=" + rowID;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        AccountPersist accountPersist = new AccountPersist();
        ExpenseBudget expenseBudget = new ExpenseBudget();
        expenseBudget.setId(rowID);
        expenseBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        expenseBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
        expenseBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
        expenseBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
        expenseBudget.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
        expenseBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
        expenseBudget.setLastFillDate(cursor.getLong(cursor.getColumnIndexOrThrow(_LAST_FILL_DATE)));
        expenseBudget.setCycleStartDate(cursor.getLong(cursor.getColumnIndexOrThrow(_CYCLE_START_DATE)));
        long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
        expenseBudget.setAccount(accountPersist.read(accountID, database));
        cursor.close();
        return expenseBudget;
    }

    public List<ExpenseBudget> readAll(SQLiteDatabase database) {
        String sql = "select * from " + _TABLE + " order by " + _UNUSED_BALANCE + " desc";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        AccountPersist accountPersist = new AccountPersist();
        List<ExpenseBudget> expenseBudgetList = new ArrayList<ExpenseBudget>();
        long accountID;
        while (!cursor.isAfterLast()) {
            ExpenseBudget expenseBudget = new ExpenseBudget();
            expenseBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expenseBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            expenseBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            expenseBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
            expenseBudget.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
            expenseBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            expenseBudget.setLastFillDate(cursor.getLong(cursor.getColumnIndexOrThrow(_LAST_FILL_DATE)));
            expenseBudget.setCycleStartDate(cursor.getLong(cursor.getColumnIndexOrThrow(_CYCLE_START_DATE)));
            accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            expenseBudget.setAccount(accountPersist.read(accountID, database));
            expenseBudgetList.add(expenseBudget);
            cursor.moveToNext();
        }
        cursor.close();
        return expenseBudgetList;
    }

    /*

     */
    public List<ExpenseBudget> readAllBudgetAmountNotZero(SQLiteDatabase database) {
        String sql = "select * from " + _TABLE + " where " + _BUDGET_AMOUNT + " != 0 order by "
                + _BUDGET_AMOUNT + " desc";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        AccountPersist accountPersist = new AccountPersist();
        List<ExpenseBudget> budgetList = new ArrayList<ExpenseBudget>();
        long accountID;
        while (!cursor.isAfterLast()) {
            ExpenseBudget expenseBudget = new ExpenseBudget();
            expenseBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expenseBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            expenseBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            expenseBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
            expenseBudget.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
            expenseBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            expenseBudget.setLastFillDate(cursor.getLong(cursor.getColumnIndexOrThrow(_LAST_FILL_DATE)));
            expenseBudget.setCycleStartDate(cursor.getLong(cursor.getColumnIndexOrThrow(_CYCLE_START_DATE)));
            accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            expenseBudget.setAccount(accountPersist.read(accountID, database));
            budgetList.add(expenseBudget);
            cursor.moveToNext();
        }
        cursor.close();
        return budgetList;
    }

    public List<ExpenseBudget> readAllUnusedBalanceNotZero(SQLiteDatabase database) {
        String sql = "select * from " + _TABLE + " where " + _UNUSED_BALANCE + " != 0 " +
                " order by " + _UNUSED_BALANCE + " asc";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        AccountPersist accountPersist = new AccountPersist();
        List<ExpenseBudget> budgetList = new ArrayList<ExpenseBudget>();
        long accountID;
        while (!cursor.isAfterLast()) {
            ExpenseBudget expenseBudget = new ExpenseBudget();
            expenseBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expenseBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            expenseBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            expenseBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
            expenseBudget.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
            expenseBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            expenseBudget.setLastFillDate(cursor.getLong(cursor.getColumnIndexOrThrow(_LAST_FILL_DATE)));
            expenseBudget.setCycleStartDate(cursor.getLong(cursor.getColumnIndexOrThrow(_CYCLE_START_DATE)));
            accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            expenseBudget.setAccount(accountPersist.read(accountID, database));
            budgetList.add(expenseBudget);
            cursor.moveToNext();
        }
        cursor.close();
        return budgetList;
    }

    public BigDecimal readTotalUnusedBalance(SQLiteDatabase database) {
        String sql = "select sum(" + _UNUSED_BALANCE + ") as total from " + _TABLE;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        BigDecimal total;
        Double totalDouble = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        total = new BigDecimal(totalDouble);
        cursor.close();
        return total.setScale(2, RoundingMode.HALF_UP);
    }



    public ExpenseBudget update(ExpenseBudget expenseBudget, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expenseBudget.getName());
        contentValues.put(_CYCLE, expenseBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, expenseBudget.getBudgetAmount().toString());
        contentValues.put(_NOTE, expenseBudget.getNote());
        contentValues.put(_BUDGET_CATEGORY, expenseBudget.getExpenseBudgetCategory().name());
        contentValues.put(_UNUSED_BALANCE, expenseBudget.getUnusedBalance().toString());
        contentValues.put(_ROLL_OVER, expenseBudget.getRollOver() == true ? 1 : 0);
        contentValues.put(_ACCOUNT_ID, expenseBudget.getAccount().getId());
        contentValues.put(_LAST_FILL_DATE, expenseBudget.getLastFillDate());
        contentValues.put(_CYCLE_START_DATE, expenseBudget.getCycleStartDate());
        database.update(_TABLE, contentValues, _ID + " = " + expenseBudget.getId(), null);
        return expenseBudget;
    }

    public Boolean delete(Long rowID, SQLiteDatabase database) {
        database.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }



    public void fillEnvelope(ExpenseBudget expenseBudget, SQLiteDatabase database) {
        expenseBudget.setUnusedBalance(expenseBudget.getUnusedBalance().add(expenseBudget.getBudgetAmount()));
        this.update(expenseBudget, database);
    }

    public void fillAllEnvelopes(SQLiteDatabase database) {
        List<ExpenseBudget> expenseBudgetList = this.readAllBudgetAmountNotZero(database);

        for (ExpenseBudget expenseBudget : expenseBudgetList) {
            this.fillEnvelope(expenseBudget, database);
        }
    }

    public Boolean save(ExpenseBudget expenseBudget, SQLiteDatabase database) {
        if (expenseBudget.getId() > 0) {
            this.update(expenseBudget,database);
        } else {
            this.insert(expenseBudget,database);
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
     Insert initial expense budget data into database;
     This static method is only used by DatabaseHelper;
     * @param db
     * @return
     */
    public static void initialize(SQLiteDatabase db) {
        //Account id: 1-Cash on hand, 2-Bank account, 3-Credit card
        String[][] expenseBudgets = {{"Groceries",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS.toString(), Cycle.Weekly.toString(), "1"},
                {"Restaurant",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS.toString(),  Cycle.Weekly.toString(), "3"},
                {"Pet foods",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS.toString(),  Cycle.Weekly.toString(), "1"},
                {"Mortgage",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER.toString(),  Cycle.Every_2_Weeks.toString(), "2"},
                {"Rent",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER.toString(),  Cycle.Monthly.toString(), "2"},
                {"Property Taxes",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER.toString(),  Cycle.Yearly.toString(), "2"},
                {"House repair",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER.toString(),  Cycle.Yearly.toString(), "2"},
                {"House Insurance",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER.toString(),  Cycle.Yearly.toString(), "2"},
                {"Phone",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES.toString(),  Cycle.Monthly.toString(), "2"},
                {"Cable TV",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES.toString(),  Cycle.Monthly.toString(), "2"},
                {"Internet service",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES.toString(),  Cycle.Monthly.toString(), "2"},
                {"Water",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES.toString(),  Cycle.Yearly.toString(), "2"},
                {"Garbage",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES.toString(),  Cycle.Yearly.toString(), "2"},
                {"Heating",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES.toString(),  Cycle.Yearly.toString(), "2"},
                {"Fuel",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION.toString(),  Cycle.Weekly.toString(), "3"},
                {"Tire",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION.toString(),  Cycle.Yearly.toString(), "3"},
                {"Oil Change",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION.toString(),  Cycle.Every_6_Months.toString(), "1"},
                {"Auto Insurance",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION.toString(),  Cycle.Yearly.toString(), "2"},
                {"Car plate",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION.toString(),  Cycle.Yearly.toString(), "2"},
                {"Driver license",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION.toString(),  Cycle.Yearly.toString(), "2"},
                {"Bus ticket",ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION.toString(),  Cycle.Monthly.toString(), "3"},};

        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < expenseBudgets.length; i++){
            contentValues.put(_NAME, expenseBudgets[i][0]);
            contentValues.put(_BUDGET_CATEGORY, expenseBudgets[i][1]);
            contentValues.put(_CYCLE, expenseBudgets[i][2]);
            contentValues.put(_ACCOUNT_ID, expenseBudgets[i][3]);
            db.insert(_TABLE, null, contentValues);
            contentValues.clear();
        }

    }
}
