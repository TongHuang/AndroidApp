package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InitializeExpenseBudgetActivity extends ActionBarActivity {

    public static final String EXTRA_EXPENSE_BUDGET_ID = "com.quebecfresh.androidapp.simplebudget.expense.budget.id";
    private Integer mExpandedCategoryPosition = 0;
    List<ExpenseBudget> mExpenseBudgetList;
    TextView textViewTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_expense_budget);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initialize_expense_budget, menu);
        return true;
    }


    private  BigDecimal calcTotal(Cycle cycle){
        BigDecimal total = new BigDecimal("0");
        for (int i = 0; i < mExpenseBudgetList.size(); i++) {
            total = total.add(mExpenseBudgetList.get(i).convertBudgetAmountTo(cycle));
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    protected void onResume() {

        //When initialization is not done, disabled actionBar up button
//        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
//        Boolean initializeDone = preferences.getBoolean(getString(R.string.initialize_done), false);
//        if(initializeDone){
//            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }else{
//            this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ExpenseBudgetPersist persist = new ExpenseBudgetPersist(this);
        mExpenseBudgetList = persist.readAll();

        ExpandableExpenseBudgetFragment expandableExpenseBudgetFragment = new ExpandableExpenseBudgetFragment();
        expandableExpenseBudgetFragment.setExpenseBudgetList(mExpenseBudgetList);

        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerExpenseBudget, expandableExpenseBudgetFragment);
        fragmentTransaction.commit();



//        List<ExpenseBudget> foods = new ArrayList<ExpenseBudget>();
//        List<ExpenseBudget> shelters = new ArrayList<ExpenseBudget>();
//        List<ExpenseBudget> utilities = new ArrayList<ExpenseBudget>();
//        List<ExpenseBudget> transportation = new ArrayList<ExpenseBudget>();
//        List<ExpenseBudget> others = new ArrayList<ExpenseBudget>();
//        ExpenseBudget category;
//        for (int i = 0; i < mExpenseBudgetList.size(); i++) {
//            category = mExpenseBudgetList.get(i);
//            switch (category.getExpenseBudgetCategory()) {
//                case FOODS:
//                    foods.add(category);
//                    break;
//                case SHELTER:
//                    shelters.add(category);
//                    break;
//                case UTILITIES:
//                    utilities.add(category);
//                    break;
//                case TRANSPORTATION:
//                    transportation.add(category);
//                    break;
//                case OTHERS:
//                    others.add(category);
//                    break;
//            }
//        }
//
//        List<String> group = new ArrayList<String>();
//        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.FOODS.getLabel(this));
//        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.SHELTER.getLabel(this));
//        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.UTILITIES.getLabel(this));
//        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.TRANSPORTATION.getLabel(this));
//        group.add(ExpenseBudget.EXPENSE_BUDGET_CATEGORY.OTHERS.getLabel(this));
//
//        HashMap<String, List<ExpenseBudget>> categoryMap = new HashMap<String, List<ExpenseBudget>>();
//        categoryMap.put(group.get(0), foods);
//        categoryMap.put(group.get(1), shelters);
//        categoryMap.put(group.get(2), utilities);
//        categoryMap.put(group.get(3), transportation);
//        categoryMap.put(group.get(4), others);

//        final Spinner spinnerCycle = (Spinner)this.findViewById(R.id.spinnerCycle);
//        CycleSpinnerAdapter spinnerAdapter = new CycleSpinnerAdapter(this, Cycle.values());
//        spinnerCycle.setAdapter(spinnerAdapter);
//        spinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                textViewTotal.setText(calcTotal((Cycle) spinnerCycle.getItemAtPosition(position)).toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        spinnerCycle.setSelection(3);
//        textViewTotal = (TextView) this.findViewById(R.id.textViewTotal);
//        textViewTotal.setText(this.calcTotal(Cycle.Monthly).toString());

//        ExpenseBudgetExpandableListViewAdapter adapter = new ExpenseBudgetExpandableListViewAdapter(group, categoryMap, this);
//        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListViewExpenseCategory);
//        listView.setAdapter(adapter);
//        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Intent intent = new Intent(InitializeExpenseBudgetActivity.this, EditExpenseBudgetActivity.class);
//                intent.putExtra(EXTRA_EXPENSE_BUDGET_ID, id);
//                startActivity(intent);
//                mExpandedCategoryPosition = groupPosition;
//                return true;
//            }
//        });
//        if (this.mExpandedCategoryPosition >= 0) {
//            listView.expandGroup(this.mExpandedCategoryPosition);
//        }


        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, EditExpenseBudgetActivity.class);
                intent.putExtra(EXTRA_EXPENSE_BUDGET_ID, -1L);
                startActivity(intent);
                break;
            case R.id.action_done:
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.initialize_expense_budget_done), true);
                editor.commit();
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
