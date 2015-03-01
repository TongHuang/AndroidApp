package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeCategory;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeCategoryPersist;

import java.math.BigDecimal;


public class EditIncomeCategoryActivity extends ActionBarActivity {

    private Long rowID;
    private EditText editTextName;
    private Spinner spinnerIncomeGroup;
    private Spinner spinnerCycle;
    private EditText editTextAmount;
    private EditText editTextNote;
    private IncomeCategory incomeCategory;
    IncomeCategoryPersist persist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income_category);

        Intent intent = getIntent();
        rowID = intent.getLongExtra(BudgetIncomeActivity.EXTRA_INCOME_CATEGORY_ID, 0);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        persist = new IncomeCategoryPersist(dbHelper.getReadableDatabase());
        if(rowID > 0) {
            incomeCategory = persist.read(rowID);
        }else{
            incomeCategory = new IncomeCategory();
        }

        editTextName = (EditText)this.findViewById(R.id.editTextName);
        editTextName.setText(incomeCategory.getName());
        spinnerIncomeGroup  = (Spinner)this.findViewById(R.id.spinnerGroup);
        IncomeCategoryGroupSpinnerAdapter incomeCategoryGroupSpinnerAdapter = new IncomeCategoryGroupSpinnerAdapter(this, IncomeCategory.INCOME_CATEGORY_GROUP.values());
        spinnerIncomeGroup.setAdapter(incomeCategoryGroupSpinnerAdapter);
        spinnerIncomeGroup.setSelection(incomeCategory.getCategoryGroup().ordinal());
        spinnerCycle = (Spinner)this.findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter adapter = new CycleSpinnerAdapter(this,  Cycle.values());
        spinnerCycle.setAdapter(adapter);
        spinnerCycle.setSelection(incomeCategory.getCycle().ordinal());
        editTextAmount = (EditText)this.findViewById(R.id.editTextAmount);
        editTextAmount.setText(incomeCategory.getBudgetAmount().toString());
        editTextNote = (EditText)this.findViewById(R.id.editTextNote);
        editTextNote.setText(incomeCategory.getNote());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_income_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){

            case R.id.action_save:
                incomeCategory.setName(editTextName.getText().toString());
                incomeCategory.setCategoryGroup((IncomeCategory.INCOME_CATEGORY_GROUP)spinnerIncomeGroup.getSelectedItem());
                incomeCategory.setCycle((Cycle)spinnerCycle.getSelectedItem());
                incomeCategory.setBudgetAmount(new BigDecimal(editTextAmount.getText().toString()));
                incomeCategory.setNote(editTextNote.getText().toString());
                persist.save(incomeCategory);
                break;
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
