package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    public IncomeBudgetPersist(SQLiteDatabase db){
        this.db = db;
    }

    public IncomeBudget insert(IncomeBudget incomeCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeCategory.getName());
        contentValues.put(_NOTE, incomeCategory.getNote());
        contentValues.put(_CYCLE, incomeCategory.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeCategory.getBudgetAmount().toString());
        contentValues.put(_CATEGORY_GROUP, incomeCategory.getCategoryGroup().name());
        contentValues.put(_UNUSED_BALANCE, incomeCategory.getUnusedBalance().toString());
        contentValues.put(_ROLL_OVER, incomeCategory.getRollOver() == true ? 1:0);
        Long rowID = this.db.insert(_TABLE, null, contentValues);
        incomeCategory.setId(rowID);
        return incomeCategory;
    }

    public IncomeBudget read(Long rowID){
        String sql = "Select * from " + _TABLE + " where " + _ID + "=" + rowID;

        Cursor cursor = this.db.rawQuery(sql,null);
        cursor.moveToFirst();
        IncomeBudget incomeCategory = new IncomeBudget();
        incomeCategory.setId(rowID);
        incomeCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        incomeCategory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        incomeCategory.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
        incomeCategory.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
        incomeCategory.setUnusedBalance(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE))));
        incomeCategory.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER))==1? true:false);
        return incomeCategory;
    }

    public List<IncomeBudget> readAll(){
        String sql = " Select * from " + _TABLE;
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<IncomeBudget> incomeCategories = new ArrayList<IncomeBudget>();
        while(!cursor.isAfterLast()){
            IncomeBudget incomeCategory = new IncomeBudget();
            incomeCategory.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            incomeCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            incomeCategory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            incomeCategory.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            incomeCategory.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
            incomeCategory.setUnusedBalance(new BigDecimal((cursor.getString(cursor.getColumnIndexOrThrow(_UNUSED_BALANCE)))));
            incomeCategory.setRollOver(cursor.getInt(cursor.getColumnIndexOrThrow(_ROLL_OVER))==1?true:false);
            incomeCategories.add(incomeCategory);
            cursor.moveToNext();
        }
        return incomeCategories;
    }

    public IncomeBudget update(IncomeBudget incomeCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeCategory.getName());
        contentValues.put(_NOTE, incomeCategory.getNote());
        contentValues.put(_CYCLE, incomeCategory.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeCategory.getBudgetAmount().toString());
        contentValues.put(_CATEGORY_GROUP, incomeCategory.getCategoryGroup().name());
        contentValues.put(_UNUSED_BALANCE, incomeCategory.getUnusedBalance().toString());
        contentValues.put(_ROLL_OVER, incomeCategory.getRollOver() == true? 1:0);
        this.db.update(_TABLE,contentValues,_ID + " = " + incomeCategory.getId(),null);
        return incomeCategory;
    }

    public Boolean delete(Long rowID){
        this.db.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }

    public void initialize(){
        IncomeBudget incomeCategory =  new IncomeBudget("Salary", Cycle.Weekly);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeBudget("Part-time job salary", Cycle.Every_2_Weeks);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeBudget("Bonus", Cycle.Yearly);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT);
        this.insert(incomeCategory);


        incomeCategory =new IncomeBudget("Social welfare", Cycle.Monthly);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeBudget("Child care benefit", Cycle.Monthly);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeBudget("Employment Insurance", Cycle.Every_2_Weeks);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
        this.insert(incomeCategory);
        incomeCategory =new IncomeBudget("Housing Allowance", Cycle.Monthly);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT);
        this.insert(incomeCategory);

        incomeCategory = new IncomeBudget("Saving Interest", Cycle.Yearly);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeBudget("Property renting", Cycle.Monthly);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT);
        this.insert(incomeCategory);
        incomeCategory =new IncomeBudget("Stock market revenue", Cycle.Yearly);
        incomeCategory.setCategoryGroup(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT);
        this.insert(incomeCategory);
    }

    public boolean save(IncomeBudget incomeCategory){
        if(incomeCategory.getId() > 0){
            this.update(incomeCategory);
        }else{
            this.insert(incomeCategory);
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

