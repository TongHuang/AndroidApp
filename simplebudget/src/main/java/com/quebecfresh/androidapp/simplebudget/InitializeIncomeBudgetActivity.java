package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Budget;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InitializeIncomeBudgetActivity extends ActionBarActivity {

    public static final String EXTRA_INCOME_CATEGORY_ID = "com.quebecfresh.androidapp.simplebudget.id";
    private Integer expandedGroupPosition = 0;
    private List<IncomeBudget> incomeCategories;
    private  TextView textViewTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_income_budget);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    private BigDecimal calcTotal(Cycle cycle){

        BigDecimal total = new BigDecimal("0");
        for (int i = 0; i < incomeCategories.size(); i++) {
            total = total.add(incomeCategories.get(i).convertBudgetAmountTo(cycle));
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    protected void onResume() {

        //When initialization is not done, disabled actionBar up button
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
        Boolean initializeDone = preferences.getBoolean(getString(R.string.initialize_done), false);
        if(initializeDone){
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }else{
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        IncomeBudgetPersist incomeCategoryPersist = new IncomeBudgetPersist(databaseHelper.getReadableDatabase());

        incomeCategories = incomeCategoryPersist.readAll();

        List<Budget> employments = new ArrayList<Budget>();
        List<Budget> governmentBenefits = new ArrayList<Budget>();
        List<Budget> investments = new ArrayList<Budget>();
        List<Budget> others = new ArrayList<Budget>();

        for (int i = 0; i < incomeCategories.size(); i++) {
            IncomeBudget incomeCategory = incomeCategories.get(i);
            switch (incomeCategory.getIncomeBudgetCategory()) {
                case EMPLOYMENT:
                    employments.add(incomeCategory);
                    break;
                case GOVERNMENT_BENEFIT:
                    governmentBenefits.add(incomeCategory);
                    break;
                case INVESTMENT:
                    investments.add(incomeCategory);
                    break;
                case OTHERS:
                    others.add(incomeCategory);
                    break;
            }
        }


        List<String> group = new ArrayList<String>();
        group.add(IncomeBudget.INCOME_BUDGET_CATEGORY.EMPLOYMENT.getLabel(this));
        group.add(IncomeBudget.INCOME_BUDGET_CATEGORY.GOVERNMENT_BENEFIT.getLabel(this));
        group.add(IncomeBudget.INCOME_BUDGET_CATEGORY.INVESTMENT.getLabel(this));
        group.add(IncomeBudget.INCOME_BUDGET_CATEGORY.OTHERS.getLabel(this));

        HashMap<String, List<Budget>> incomeCategoryMap = new HashMap<String, List<Budget>>();
        incomeCategoryMap.put(group.get(0), employments);
        incomeCategoryMap.put(group.get(1), governmentBenefits);
        incomeCategoryMap.put(group.get(2), investments);
        incomeCategoryMap.put(group.get(3), others);

        BudgetExpandableListViewAdapter adapter = new BudgetExpandableListViewAdapter(group, incomeCategoryMap, this);
        ExpandableListView expandableListView = (ExpandableListView) this.findViewById(R.id.expandableListView_incomeCategory);
        expandableListView.setAdapter(adapter);

        CycleSpinnerAdapter spinnerAdapter = new CycleSpinnerAdapter(this, Cycle.values());
        final Spinner cycleSpinner = (Spinner) this.findViewById(R.id.spinnerCycle);
        cycleSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewTotal.setText(calcTotal((Cycle)cycleSpinner.getItemAtPosition(position)).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cycleSpinner.setAdapter(spinnerAdapter);
        cycleSpinner.setSelection(3);

        textViewTotal = (TextView) this.findViewById(R.id.textViewTotal);
        textViewTotal.setText(this.calcTotal(Cycle.Monthly).toString());

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(InitializeIncomeBudgetActivity.this, EditIncomeBudgetActivity.class);
                intent.putExtra(EXTRA_INCOME_CATEGORY_ID, id);
                expandedGroupPosition = groupPosition;
                startActivity(intent);
                return true;
            }
        });
        if (this.expandedGroupPosition >= 0) {
            expandableListView.expandGroup(expandedGroupPosition);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initialize_income_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, EditIncomeBudgetActivity.class);
                intent.putExtra(EXTRA_INCOME_CATEGORY_ID, -1L);
                startActivity(intent);
                break;
            case R.id.action_done:
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.initialize_income_budget_done), true);
                editor.commit();
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
