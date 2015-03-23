package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Account;
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
public class ExpenseBudgetPersist {
    private SQLiteDatabase db;
    private AccountPersist accountPersist;

    public ExpenseBudgetPersist(SQLiteDatabase db) {
        this.db = db;
        this.accountPersist = new AccountPersist(db);
    }

    public ExpenseBudget insert(ExpenseBudget expenseBudget) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expenseBudget.getName());
        contentValues.put(_CYCLE, expenseBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, expenseBudget.getBudgetAmount().toString());
        contentValues.put(_NOTE, expenseBudget.getNote());
        contentValues.put(_CATEGORY_GROUP, expenseBudget.getExpenseBudgetCategory().name());
        contentValues.put(_UNUSED_BALANCE, expenseBudget.getUnusedBalance().toString());
        contentValues.put(_ROLL_OVER, expenseBudget.getRollOver()==Boolean.TRUE?1:0);
        contentValues.put(_ACCOUNT_ID, expenseBudget.getAccount().getId());
        contentValues.put(_LAST_FILL_DATE, expenseBudget.getLastPutDate());
        Long rowID = this.db.insert(_TABLE, null, contentValues);
        expenseBudget.setId(rowID);
        return expenseBudget;
    }

    public ExpenseBudget read(Long rowID) {
        String sql = "select * from " + _TABLE + " where " + _ID + "=" + rowID;
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        ExpenseBudget expenseBudget = new ExpenseBudget();
        expenseBudget.setId(rowID);
        expenseBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        expenseBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
        expenseBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
        expenseBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
        expenseBudget.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
        expenseBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
        expenseBudget.setLastPutDate(cursor.getLong(cursor.getColumnIndexOrThrow(_LAST_FILL_DATE)));

        long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
        expenseBudget.setAccount(accountPersist.read(accountID));

        return expenseBudget;
    }

    public List<ExpenseBudget> readAll(){
        String sql = "select * from " + _TABLE  + " order by " + _UNUSED_BALANCE + " desc";
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<ExpenseBudget> expenseBudgetList = new ArrayList<ExpenseBudget>();
        long accountID;
        while(!cursor.isAfterLast()){
            ExpenseBudget expenseBudget = new ExpenseBudget();
            expenseBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expenseBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            expenseBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            expenseBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
            expenseBudget.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
            expenseBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            expenseBudget.setLastPutDate(cursor.getLong(cursor.getColumnIndexOrThrow(_LAST_FILL_DATE)));

            accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            expenseBudget.setAccount(accountPersist.read(accountID));
            expenseBudgetList.add(expenseBudget);
            cursor.moveToNext();
        }
        return expenseBudgetList;
    }

    /*

     */
    public List<ExpenseBudget> readAllBudgetAmountNotZero(){
        String sql = "select * from " + _TABLE + " where " + _BUDGET_AMOUNT  + " != 0 order by "
                + _BUDGET_AMOUNT + " desc";
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<ExpenseBudget> budgetList = new ArrayList<ExpenseBudget>();
        long accountID;
        while(!cursor.isAfterLast()){
            ExpenseBudget expenseBudget = new ExpenseBudget();
            expenseBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expenseBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            expenseBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            expenseBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
            expenseBudget.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
            expenseBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            expenseBudget.setLastPutDate(cursor.getLong(cursor.getColumnIndexOrThrow(_LAST_FILL_DATE)));

            accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            expenseBudget.setAccount(accountPersist.read(accountID));
            budgetList.add(expenseBudget);
            cursor.moveToNext();
        }
        return budgetList;
    }

    public List<ExpenseBudget> readAllUnusedBalanceNotZero(){
        String sql = "select * from " + _TABLE + " where " + _UNUSED_BALANCE + " != 0 " +
                " order by " + _UNUSED_BALANCE + " asc";
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<ExpenseBudget> budgetList = new ArrayList<ExpenseBudget>();
        long accountID;
        while(!cursor.isAfterLast()){
            ExpenseBudget expenseBudget = new ExpenseBudget();
            expenseBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            expenseBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            expenseBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            expenseBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            expenseBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
            expenseBudget.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
            expenseBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            expenseBudget.setLastPutDate(cursor.getLong(cursor.getColumnIndexOrThrow(_LAST_FILL_DATE)));
            accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            expenseBudget.setAccount(accountPersist.read(accountID));
            budgetList.add(expenseBudget);
            cursor.moveToNext();
        }
        return budgetList;
    }

    public BigDecimal readTotalUnusedBalance(){
        String sql = "select sum(" + _UNUSED_BALANCE + ") as total from " + _TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        BigDecimal total;
        String totalStr = cursor.getString(cursor.getColumnIndexOrThrow("total"));
        if(totalStr != null){
            total =  new BigDecimal(totalStr);
        }else {
            total = new BigDecimal("0");
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }


    public ExpenseBudget update(ExpenseBudget expenseBudget){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, expenseBudget.getName());
        contentValues.put(_CYCLE, expenseBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, expenseBudget.getBudgetAmount().toString());
        contentValues.put(_NOTE, expenseBudget.getNote());
        contentValues.put(_CATEGORY_GROUP, expenseBudget.getExpenseBudgetCategory().name());
        contentValues.put(_UNUSED_BALANCE, expenseBudget.getUnusedBalance().toString());
        contentValues.put(_ROLL_OVER, expenseBudget.getRollOver() == true?1:0);
        contentValues.put(_ACCOUNT_ID, expenseBudget.getAccount().getId());
        contentValues.put(_LAST_FILL_DATE, expenseBudget.getLastPutDate());
        this.db.update(_TABLE, contentValues, _ID + " = " + expenseBudget.getId(), null);
        return expenseBudget;
    }

    public Boolean delete(Long rowID){
        this.db.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }

    public void initialize(){

        //Read the first account as the default account
        Account account = this.accountPersist.read(1L);

        ExpenseBudget expenseBudget;
        expenseBudget = new ExpenseBudget("Groceries", Cycle.Weekly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Restaurant", Cycle.Weekly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Pet foods", Cycle.Weekly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);




        expenseBudget = new ExpenseBudget("Mortgage", Cycle.Every_2_Weeks);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Rent", Cycle.Monthly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Property Taxes", Cycle.Yearly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("House repair", Cycle.Yearly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Insurance", Cycle.Yearly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);

        expenseBudget = new ExpenseBudget("Electricity", Cycle.Monthly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Phone", Cycle.Monthly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Cable TV", Cycle.Monthly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Internet service", Cycle.Monthly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Water", Cycle.Yearly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Garbage", Cycle.Yearly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Heating", Cycle.Yearly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);


        expenseBudget = new ExpenseBudget("Fuel", Cycle.Weekly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Tire", Cycle.Every_6_Months);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Oil change", Cycle.Every_6_Months);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Insurance", Cycle.Monthly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Auto plate", Cycle.Yearly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Driver licence", Cycle.Yearly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);
        expenseBudget = new ExpenseBudget("Bus ticket", Cycle.Monthly);
        expenseBudget.setExpenseBudgetCategory(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION);
        expenseBudget.setAccount(account);
        this.insert(expenseBudget);

    }

    public void fillEnvelope(ExpenseBudget expenseBudget){
        expenseBudget.setUnusedBalance(expenseBudget.getUnusedBalance().add(expenseBudget.getBudgetAmount()));
        this.update(expenseBudget);
    }

    public void fillAllEnvelopes(){
        List<ExpenseBudget> expenseBudgetList = this.readAllBudgetAmountNotZero();
        for(ExpenseBudget expenseBudget : expenseBudgetList){
            this.fillEnvelope(expenseBudget);
        }
    }

    public Boolean save(ExpenseBudget expenseBudget){
        if(expenseBudget.getId() > 0){
            this.update(expenseBudget);
        }else{
            this.insert(expenseBudget);
        }
        return true;
    }

    public Boolean create(){
         this.db.execSQL(CREATE);
        return true;
    }

    public Boolean drop(){
        this.db.execSQL(DROP);
        return true;
    }
}
