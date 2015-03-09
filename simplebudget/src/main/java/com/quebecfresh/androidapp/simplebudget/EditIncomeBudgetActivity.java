package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;

import java.math.BigDecimal;


public class EditIncomeBudgetActivity extends ActionBarActivity {

    private Long rowID;
    private EditText editTextName;
    private Spinner spinnerIncomeGroup;
    private Spinner spinnerCycle;
    private EditText editTextAmount;
    private EditText editTextNote;
    private CheckBox checkBoxRollover;
    private IncomeBudget incomeBudget;
    IncomeBudgetPersist persist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income_budget);

        Intent intent = getIntent();
        rowID = intent.getLongExtra(InitializeIncomeBudgetActivity.EXTRA_INCOME_CATEGORY_ID, 0);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        persist = new IncomeBudgetPersist(dbHelper.getReadableDatabase());
        if(rowID > 0) {
            incomeBudget = persist.read(rowID);
        }else{
            incomeBudget = new IncomeBudget();
        }

        editTextName = (EditText)this.findViewById(R.id.editTextName);
        editTextName.setText(incomeBudget.getName());
        spinnerIncomeGroup  = (Spinner)this.findViewById(R.id.spinnerGroup);
        IncomeCategoryGroupSpinnerAdapter incomeCategoryGroupSpinnerAdapter = new IncomeCategoryGroupSpinnerAdapter(this, IncomeBudget.INCOME_BUDGET_CATEGORY.values());
        spinnerIncomeGroup.setAdapter(incomeCategoryGroupSpinnerAdapter);
        spinnerIncomeGroup.setSelection(incomeBudget.getIncomeBudgetCategory().ordinal());
        spinnerCycle = (Spinner)this.findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter adapter = new CycleSpinnerAdapter(this,  Cycle.values());
        spinnerCycle.setAdapter(adapter);
        spinnerCycle.setSelection(incomeBudget.getCycle().ordinal());
        editTextAmount = (EditText)this.findViewById(R.id.editTextAmount);
        editTextAmount.setText(incomeBudget.getBudgetAmount().toString());
        checkBoxRollover = (CheckBox)findViewById(R.id.checkBoxRollover);
        checkBoxRollover.setChecked(incomeBudget.getRollOver());
        editTextNote = (EditText)this.findViewById(R.id.editTextNote);
        editTextNote.setText(incomeBudget.getNote());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_income_budget, menu);
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
                incomeBudget.setName(editTextName.getText().toString());
                incomeBudget.setIncomeBudgetCategory((IncomeBudget.INCOME_BUDGET_CATEGORY) spinnerIncomeGroup.getSelectedItem());
                incomeBudget.setCycle((Cycle) spinnerCycle.getSelectedItem());
                incomeBudget.setBudgetAmount(new BigDecimal(editTextAmount.getText().toString()));
                incomeBudget.setNote(editTextNote.getText().toString());
                incomeBudget.setRollOver(checkBoxRollover.isChecked());
                persist.save(incomeBudget);
                break;
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    public void showWhatIsRollover(View view){
        WhatIsThisDialogFragment dialogFragment = new WhatIsThisDialogFragment();
        dialogFragment.setTitle(getString(R.string.what_is_roll_over_title));
        dialogFragment.setMessage(getString(R.string.what_is_roll_over));
        dialogFragment.show(getSupportFragmentManager(), "tag");
    }
}
