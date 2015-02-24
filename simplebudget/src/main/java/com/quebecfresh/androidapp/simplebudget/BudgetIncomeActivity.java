package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.quebecfresh.androidapp.simplebudget.model.Category;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeCategory;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeCategoryPersist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BudgetIncomeActivity extends ActionBarActivity {

    public static final String EXTRA_INCOME_CATEGORY_ID = "com.quebecfresh.androidapp.simplebudget.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_income);
    }


    @Override
    protected void onResume() {

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        IncomeCategoryPersist incomeCategoryPersist = new IncomeCategoryPersist(databaseHelper.getReadableDatabase());

        List<IncomeCategory> incomeCategories = incomeCategoryPersist.readAll();

        List<Category> employments = new ArrayList<Category>();
        List<Category> governmentBenefits = new ArrayList<Category>();
        List<Category> investments = new ArrayList<Category>();
        List<Category> others = new ArrayList<Category>();

        for(int i = 0; i < incomeCategories.size(); i++){
            IncomeCategory incomeCategory = incomeCategories.get(i);
            switch (incomeCategory.getGroup()){
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
        group.add(IncomeCategory.GROUP.EMPLOYMENT.getLabel(this));
        group.add(IncomeCategory.GROUP.GOVERNMENT_BENEFIT.getLabel(this));
        group.add(IncomeCategory.GROUP.INVESTMENT.getLabel(this));
        group.add(IncomeCategory.GROUP.OTHERS.getLabel(this));

        HashMap<String, List<Category>> incomeCategoryMap = new HashMap<String, List<Category>>();
        incomeCategoryMap.put(group.get(0), employments);
        incomeCategoryMap.put(group.get(1), governmentBenefits);
        incomeCategoryMap.put(group.get(2), investments);
        incomeCategoryMap.put(group.get(3), others);

        CategoryExpandableListViewAdapter adapter = new CategoryExpandableListViewAdapter(group, incomeCategoryMap, this);
        ExpandableListView expandableListView = (ExpandableListView)this.findViewById(R.id.expandableListView_incomeCategory);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BudgetIncomeActivity.this, EditIncomeCategoryActivity.class);
                intent.putExtra(EXTRA_INCOME_CATEGORY_ID, id);
                startActivity(intent);
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(BudgetIncomeActivity.this, EditIncomeCategoryActivity.class);
                intent.putExtra(EXTRA_INCOME_CATEGORY_ID, id);
                startActivity(intent);
                return true;
            }
        });

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget_income, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            Intent intent =new Intent(this, BudgetExpenseActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
