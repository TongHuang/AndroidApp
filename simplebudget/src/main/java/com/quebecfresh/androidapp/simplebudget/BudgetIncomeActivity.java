package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Category;
import com.quebecfresh.androidapp.simplebudget.model.IncomeCategory;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeCategoryPersist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BudgetIncomeActivity extends ActionBarActivity {

    public static final String EXTRA_INCOME_CATEGORY_ID = "com.quebecfresh.androidapp.simplebudget.id";
    private Integer expandedGroupPosition = 0;

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
            switch (incomeCategory.getCategoryGroup()){
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
        group.add(IncomeCategory.INCOME_CATEGORY_GROUP.EMPLOYMENT.getLabel(this));
        group.add(IncomeCategory.INCOME_CATEGORY_GROUP.GOVERNMENT_BENEFIT.getLabel(this));
        group.add(IncomeCategory.INCOME_CATEGORY_GROUP.INVESTMENT.getLabel(this));
        group.add(IncomeCategory.INCOME_CATEGORY_GROUP.OTHERS.getLabel(this));

        HashMap<String, List<Category>> incomeCategoryMap = new HashMap<String, List<Category>>();
        incomeCategoryMap.put(group.get(0), employments);
        incomeCategoryMap.put(group.get(1), governmentBenefits);
        incomeCategoryMap.put(group.get(2), investments);
        incomeCategoryMap.put(group.get(3), others);

        CategoryExpandableListViewAdapter adapter = new CategoryExpandableListViewAdapter(group, incomeCategoryMap, this);
        ExpandableListView expandableListView = (ExpandableListView)this.findViewById(R.id.expandableListView_incomeCategory);
        expandableListView.setAdapter(adapter);

        BigDecimal total = new BigDecimal("0");
        for(int i = 0; i < incomeCategories.size(); i++){
            total = total.add(incomeCategories.get(i).getBudgetAmount());
        }
        TextView textViewTotal = (TextView)this.findViewById(R.id.textViewTotal);
        textViewTotal.setText("Total:" + total.toString());

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(BudgetIncomeActivity.this, EditIncomeCategoryActivity.class);
                intent.putExtra(EXTRA_INCOME_CATEGORY_ID, id);
                expandedGroupPosition = groupPosition;
                startActivity(intent);
                return true;
            }
        });
        if(this.expandedGroupPosition >= 0) {
            expandableListView.expandGroup(expandedGroupPosition);
        }
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

        switch(id){
            case R.id.action_add:
                break;
            case R.id.action_done:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
