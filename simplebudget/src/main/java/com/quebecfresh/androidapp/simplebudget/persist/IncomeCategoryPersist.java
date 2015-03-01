package com.quebecfresh.androidapp.simplebudget.persist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.quebecfresh.androidapp.simplebudget.model.IncomeCategory.Contract.*;

/**
 * Created by Tong Huang on 2015-02-22, 7:05 PM.
 */
public class IncomeCategoryPersist {
    private SQLiteDatabase db;

    public IncomeCategoryPersist(SQLiteDatabase db){
        this.db = db;
    }

    public IncomeCategory insert(IncomeCategory incomeCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeCategory.getName());
        contentValues.put(_NOTE, incomeCategory.getNote());
        contentValues.put(_CYCLE, incomeCategory.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeCategory.getBudgetAmount().toString());
        contentValues.put(_CATEGORY_GROUP, incomeCategory.getCategoryGroup().name());
        Long rowID = this.db.insert(_TABLE, null, contentValues);
        incomeCategory.setId(rowID);
        return incomeCategory;
    }

    public IncomeCategory read(Long rowID){
        String sql = "Select * from " + _TABLE + " where " + _ID + "=" + rowID;

        Cursor cursor = this.db.rawQuery(sql,null);
        cursor.moveToFirst();
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setId(rowID);
        incomeCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
        incomeCategory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
        incomeCategory.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
        incomeCategory.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
        return incomeCategory;
    }

    public List<IncomeCategory> readAll(){
        String sql = " Select * from " + _TABLE;
        Cursor cursor = this.db.rawQuery(sql, null);
        cursor.moveToFirst();
        List<IncomeCategory> incomeCategories = new ArrayList<IncomeCategory>();
        while(!cursor.isAfterLast()){
            IncomeCategory incomeCategory = new IncomeCategory();
            incomeCategory.setId(cursor.getLong(cursor.getColumnIndexOrThrow(_ID)));
            incomeCategory.setName(cursor.getString(cursor.getColumnIndexOrThrow(_NAME)));
            incomeCategory.setNote(cursor.getString(cursor.getColumnIndexOrThrow(_NOTE)));
            incomeCategory.setCycle(Cycle.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CYCLE))));
            incomeCategory.setBudgetAmount(new BigDecimal(cursor.getString(cursor.getColumnIndexOrThrow(_BUDGET_AMOUNT))));
            incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(_CATEGORY_GROUP))));
            incomeCategories.add(incomeCategory);
            cursor.moveToNext();
        }
        return incomeCategories;
    }

    public IncomeCategory update(IncomeCategory incomeCategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_NAME, incomeCategory.getName());
        contentValues.put(_NOTE, incomeCategory.getNote());
        contentValues.put(_CYCLE, incomeCategory.getCycle().name());
        contentValues.put(_BUDGET_AMOUNT, incomeCategory.getBudgetAmount().toString());
        contentValues.put(_CATEGORY_GROUP, incomeCategory.getCategoryGroup().name());
        this.db.update(_TABLE,contentValues,_ID + " = " + incomeCategory.getId(),null);
        return incomeCategory;
    }

    public Boolean delete(Long rowID){
        this.db.delete(_TABLE, _ID + " = " + rowID, null);
        return true;
    }

    public void initialize(){
        IncomeCategory incomeCategory =  new IncomeCategory("Salary", Cycle.Weekly);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.EMPLOYMENT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeCategory("Part-time job salary", Cycle.Every_2_Weeks);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.EMPLOYMENT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeCategory("Bonus", Cycle.Yearly);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.EMPLOYMENT);
        this.insert(incomeCategory);


        incomeCategory =new IncomeCategory("Social welfare", Cycle.Monthly);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.GOVERNMENT_BENEFIT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeCategory("Child care benefit", Cycle.Monthly);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.GOVERNMENT_BENEFIT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeCategory("Employment Insurance", Cycle.Every_2_Weeks);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.GOVERNMENT_BENEFIT);
        this.insert(incomeCategory);
        incomeCategory =new IncomeCategory("Housing Allowance", Cycle.Monthly);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.GOVERNMENT_BENEFIT);
        this.insert(incomeCategory);

        incomeCategory = new IncomeCategory("Saving Interest", Cycle.Yearly);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.INVESTMENT);
        this.insert(incomeCategory);
        incomeCategory = new IncomeCategory("Property renting", Cycle.Monthly);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.INVESTMENT);
        this.insert(incomeCategory);
        incomeCategory =new IncomeCategory("Stock market revenue", Cycle.Yearly);
        incomeCategory.setCategoryGroup(IncomeCategory.INCOME_CATEGORY_GROUP.INVESTMENT);
        this.insert(incomeCategory);
    }

    public boolean save(IncomeCategory incomeCategory){
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

