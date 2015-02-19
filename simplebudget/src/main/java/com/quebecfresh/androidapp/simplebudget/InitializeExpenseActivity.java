package com.quebecfresh.androidapp.simplebudget;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseCategory;
import com.quebecfresh.androidapp.simplebudget.model.Payee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class InitializeExpenseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize__expense);

        ArrayList<String> categoryGroup = new ArrayList<String>();
        categoryGroup.add("Foods");
        categoryGroup.add("shelter");
        categoryGroup.add("utilities");
        categoryGroup.add("transportation");

        HashMap<String, List<ExpenseCategory>> categoryHashMap = new HashMap<String, List<ExpenseCategory>>();

        List<ExpenseCategory> foods = new ArrayList<ExpenseCategory>();
        foods.add(new ExpenseCategory("Groceries", Cycle.Weekly));
        foods.add(new ExpenseCategory("Restaurant", Cycle.Weekly));
        foods.add(new ExpenseCategory("Pet foods", Cycle.Weekly));

        List<ExpenseCategory> shelter = new ArrayList<ExpenseCategory>();
        shelter.add(new ExpenseCategory("Mortgage", Cycle.Every_2_Weekly));
        shelter.add(new ExpenseCategory("Rent", Cycle.Monthly));
        shelter.add(new ExpenseCategory("Property Taxes", Cycle.Yearly));
        shelter.add(new ExpenseCategory("House repair", Cycle.Yearly));
        shelter.add(new ExpenseCategory("Insurance", Cycle.Yearly));

        List<ExpenseCategory> utilities = new ArrayList<ExpenseCategory>();
        utilities.add(new ExpenseCategory("Electricity", Cycle.Monthly));
        utilities.add(new ExpenseCategory("Phone", Cycle.Monthly));
        utilities.add(new ExpenseCategory("Cable TV", Cycle.Monthly));
        utilities.add(new ExpenseCategory("Internet service", Cycle.Monthly));
        utilities.add(new ExpenseCategory("Water", Cycle.Yearly));
        utilities.add(new ExpenseCategory("Garbage", Cycle.Yearly));
        utilities.add(new ExpenseCategory("Heating", Cycle.Yearly));



        List<ExpenseCategory> transportation = new ArrayList<ExpenseCategory>();
        transportation.add(new ExpenseCategory("Fuel", Cycle.Weekly));
        transportation.add(new ExpenseCategory("Tire", Cycle.Every_6_Months));
        transportation.add(new ExpenseCategory("Oil change", Cycle.Every_6_Months));
        transportation.add(new ExpenseCategory("Insurance", Cycle.Monthly));
        transportation.add(new ExpenseCategory("Auto plate", Cycle.Yearly));
        transportation.add(new ExpenseCategory("Driver licence", Cycle.Yearly));
        transportation.add(new ExpenseCategory("Bus ticket", Cycle.Monthly));

        categoryHashMap.put(categoryGroup.get(0), foods);
        categoryHashMap.put(categoryGroup.get(1), shelter);
        categoryHashMap.put(categoryGroup.get(2), utilities);
        categoryHashMap.put(categoryGroup.get(3), transportation);

        ArrayList<String> cycles = new ArrayList<String>();
        cycles.add("Daily");
        cycles.add("Weekly");
        cycles.add("Every_2_Weekly");
        cycles.add("Every_3_Weekly");
        cycles.add("Every_4_Weekly");
        cycles.add("Monthly");
        cycles.add("Every_2_Months");
        cycles.add("Every_3_Months");
        cycles.add("Every_4_Months");
        cycles.add("Every_5_Months");
        cycles.add("Every_6_Months");
        cycles.add("Yearly");

        InitializeExpenseListViewAdapter adapter =new InitializeExpenseListViewAdapter(categoryGroup, categoryHashMap, cycles, this);
        ExpandableListView listView = (ExpandableListView)findViewById(R.id.expandableListView_Category);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initialize__expense_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
