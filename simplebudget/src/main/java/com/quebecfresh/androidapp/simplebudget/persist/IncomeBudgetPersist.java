package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.quebecfresh.androidapp.simplebudget.model.IncomeBudget.Contract.*;

/**
 * Created by Tong Huang on 2015-02-22, 7:05 PM.
 */
public class IncomeBudgetPersist {
    private SQLiteDatabase db;
    private  AccountPersist accountPersist;

    public IncomeBudgetPersist(SQLiteDatabase db){
        this.db = db;
        this.accountPersist = new AccountPersist(db);
    }



    public IncomeBudget insert(IncomeBudget incomeBudget){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeBudget.getName());
        contentValues.put(_NOTE, incomeBudget.getNote());
        contentValues.put(_CYCLE, incomeBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeBudget.getBudgetAmount().toString());
        contentValues.put(_BUDGET_CATEGORY, incomeBudget.getIncomeBudgetCategory().name());
        contentValues.put(_ACCOUNT_ID, incomeBudget.getAccount().getId());
        Long rowID = this.db.insert(_TABLE, null, contentValues);
        incomeBudget.setId(rowID);
        return incomeBudget;
    }

    public IncomeBudget read(Long rowID){
        String sql = "Select * from " + _TABLE + " where " + _ID + "=" + rowID;

        Cursor cursor = this.db.rawQuery(sql,null);
        cursor.moveToFirst();
        IncomeBudget incomeBudget = new IncomeBudget();
        incomeBudget.setId(rowID);
        incomeBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        incomeBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        incomeBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
        incomeBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));

        long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
        incomeBudget.setAccount(accountPersist.read(accountID));
        return incomeBudget;
    }

    public List<IncomeBudget> readAll(){
        String sql = " Select * from " + _TABLE + " order by " + _BUDGET_AMOUNT + " desc";
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<IncomeBudget> incomeBudgetList = new ArrayList<IncomeBudget>();
        while(!cursor.isAfterLast()){
            IncomeBudget incomeBudget = new IncomeBudget();
            incomeBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            incomeBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            incomeBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            incomeBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            incomeBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));

            long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            incomeBudget.setAccount(accountPersist.read(accountID));
            incomeBudgetList.add(incomeBudget);
            cursor.moveToNext();
        }
        return incomeBudgetList;
    }

    public List<IncomeBudget> readAllBudgetAmountNotZero(){
        String sql = " Select * from " + _TABLE + " where " + _BUDGET_AMOUNT + " != 0 "
                + " order by " + _BUDGET_AMOUNT + " desc";
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<IncomeBudget> incomeBudgetList = new ArrayList<IncomeBudget>();
        while(!cursor.isAfterLast()){
            IncomeBudget incomeBudget = new IncomeBudget();
            incomeBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            incomeBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            incomeBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            incomeBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            incomeBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
           incomeBudgetList.add(incomeBudget);

            long accountID = cursor.getLong(cursor.getColumnIndexOrThrow(_ACCOUNT_ID));
            incomeBudget.setAccount(accountPersist.read(accountID));
            cursor.moveToNext();
        }
        return incomeBudgetList;
    }


    /*
    public List<IncomeBudget> readAllUnusedBalanceNotZero(){
        String sql = " Select * from " + _TABLE + " where " + _UNUSED_BALANCE + " != 0 ";
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<IncomeBudget> incomeBudgetList = new ArrayList<IncomeBudget>();
        while(!cursor.isAfterLast()){
            IncomeBudget incomeBudget = new IncomeBudget();
            incomeBudget.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            incomeBudget.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            incomeBudget.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            incomeBudget.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            incomeBudget.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_CATEGORY))));
            incomeBudget.setUnusedBalance(new BigDecimal((cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE)))));
            incomeBudget.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER)) == 1 ? true : false);
            incomeBudgetList.add(incomeBudget);
            cursor.moveToNext();
        }
        return incomeBudgetList;
    }
*/

    public IncomeBudget update(IncomeBudget incomeBudget){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeBudget.getName());
        contentValues.put(_NOTE, incomeBudget.getNote());
        contentValues.put(_CYCLE, incomeBudget.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeBudget.getBudgetAmount().toString());
        contentValues.put(_BUDGET_CATEGORY, incomeBudget.getIncomeBudgetCategory().name());
        contentValues.put(_ACCOUNT_ID, incomeBudget.getAccount().getId());
        this.db.update(_TABLE, contentValues, _ID + " = " + incomeBudget.getId(), null);
        return incomeBudget;
    }

    public Boolean delete(Long rowID){
        this.db.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }

    public void initialize(){
        //Read the first account as the default account
        Account account = this.accountPersist.read(1L);

        IncomeBudget incomeBudget =  new IncomeBudget("Salary", Cycle.Weekly);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);
        incomeBudget = new IncomeBudget("Part-time job salary", Cycle.Every_2_Weeks);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);
        incomeBudget = new IncomeBudget("Bonus", Cycle.Yearly);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);


        incomeBudget =new IncomeBudget("Social welfare", Cycle.Monthly);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);
        incomeBudget = new IncomeBudget("Child care benefit", Cycle.Monthly);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);
        incomeBudget = new IncomeBudget("Employment Insurance", Cycle.Every_2_Weeks);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);
        incomeBudget =new IncomeBudget("Housing Allowance", Cycle.Monthly);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);

        incomeBudget = new IncomeBudget("Saving Interest", Cycle.Yearly);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);
        incomeBudget = new IncomeBudget("Property renting", Cycle.Monthly);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);
        incomeBudget =new IncomeBudget("Stock market revenue", Cycle.Yearly);
        incomeBudget.setIncomeBudgetCategory(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT);
        incomeBudget.setAccount(account);
        this.insert(incomeBudget);
    }



    public boolean save(IncomeBudget incomeBudget){
        if(incomeBudget.getId() > 0){
            this.update(incomeBudget);
        }else{
            this.insert(incomeBudget);
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

