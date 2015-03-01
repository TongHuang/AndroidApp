package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Category;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseCategory;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseCategoryPersist;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BudgetExpenseActivity extends ActionBarActivity {

    public static final String EXTRA_EXPENSE_CATEGORY_ID = "com.quebecfresh.androidapp.simplebudget.id";
    private Integer expandedGroupPosition = 0;
    List<ExpenseCategory> categoryList;
    TextView textViewTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget__expense);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget__expense, menu);
        return true;
    }

    private  BigDecimal calcTotal(Cycle cycle){
        BigDecimal total = new BigDecimal("0");
        for (int i = 0; i < categoryList.size(); i++) {
            total = total.add(categoryList.get(i).convertBudgetAmountTo(cycle));
        }
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ExpenseCategoryPersist persist = new ExpenseCategoryPersist(db);
        categoryList = persist.readAll();

        List<Category> foods = new ArrayList<Category>();
        List<Category> shelters = new ArrayList<Category>();
        List<Category> utilities = new ArrayList<Category>();
        List<Category> transportation = new ArrayList<Category>();
        List<Category> others = new ArrayList<Category>();
        ExpenseCategory category;
        for (int i = 0; i < categoryList.size(); i++) {
            category = categoryList.get(i);
            switch (category.getCategoryGroup()) {
                case FOODS:
                    foods.add(category);
                    break;
                case SHELTER:
                    shelters.add(category);
                    break;
                case UTILITIES:
                    utilities.add(category);
                    break;
                case TRANSPORTATION:
                    transportation.add(category);
                    break;
                case OTHERS:
                    others.add(category);
                    break;
            }
        }

        List<String> group = new ArrayList<String>();
        group.add(ExpenseCategory.EXPENSE_CATEGORY_GROUP.FOODS.getLabel(this));
        group.add(ExpenseCategory.EXPENSE_CATEGORY_GROUP.SHELTER.getLabel(this));
        group.add(ExpenseCategory.EXPENSE_CATEGORY_GROUP.UTILITIES.getLabel(this));
        group.add(ExpenseCategory.EXPENSE_CATEGORY_GROUP.TRANSPORTATION.getLabel(this));
        group.add(ExpenseCategory.EXPENSE_CATEGORY_GROUP.OTHERS.getLabel(this));

        HashMap<String, List<Category>> categoryMap = new HashMap<String, List<Category>>();
        categoryMap.put(group.get(0), foods);
        categoryMap.put(group.get(1), shelters);
        categoryMap.put(group.get(2), utilities);
        categoryMap.put(group.get(3), transportation);
        categoryMap.put(group.get(4), others);

        final Spinner spinnerCycle = (Spinner)this.findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter spinnerAdapter = new CycleSpinnerAdapter(this, Cycle.values());
        spinnerCycle.setAdapter(spinnerAdapter);
        spinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textViewTotal.setText(calcTotal((Cycle)spinnerCycle.getItemAtPosition(position)).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerCycle.setSelection(3);
        textViewTotal = (TextView) this.findViewById(R.id.textViewTotal);
        textViewTotal.setText(this.calcTotal(Cycle.Monthly).toString());

        CategoryExpandableListViewAdapter adapter = new CategoryExpandableListViewAdapter(group, categoryMap, this);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expandableListViewExpenseCategory);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(BudgetExpenseActivity.this, EditExpenseCategoryActivity.class);
                intent.putExtra(EXTRA_EXPENSE_CATEGORY_ID, id);
                startActivity(intent);
                expandedGroupPosition = groupPosition;
                return true;
            }
        });
        if (this.expandedGroupPosition >= 0) {
            listView.expandGroup(this.expandedGroupPosition);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(this, EditExpenseCategoryActivity.class);
                intent.putExtra(EXTRA_EXPENSE_CATEGORY_ID, -1L);
                startActivity(intent);
                break;
            case R.id.action_done:
                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(getString(R.string.initialize_expense_budget_done), true);
                editor.commit();
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
