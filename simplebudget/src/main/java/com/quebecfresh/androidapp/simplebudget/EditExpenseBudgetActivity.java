package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.math.BigDecimal;


public class EditExpenseBudgetActivity extends ActionBarActivity {

    private EditText editTextName;
    private Spinner spinnerExpenseBudgetCategory;
    private Spinner spinnerCycle;
    private EditText editTextBudgetAmount;
    private EditText editTextNote;
    private CheckBox checkBoxRollover;
    private ExpenseBudget expenseBudget;
    private ExpenseBudgetPersist persist;
    private Long rowID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense_budget);

        Intent intent = getIntent();
        rowID = intent.getLongExtra(InitializeExpenseBudgetActivity.EXTRA_EXPENSE_BUDGET_ID, 0);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        persist = new ExpenseBudgetPersist(db);
        if (rowID > 0) {
            expenseBudget = persist.read(rowID);
        } else {
            expenseBudget = new ExpenseBudget();
        }

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setText(expenseBudget.getName());
        spinnerExpenseBudgetCategory = (Spinner) this.findViewById(R.id.spinnerExpenseBudgetCategory);
        ExpenseCategoryGroupSpinnerAdapter expenseCategoryGroupSpinnerAdapter = new ExpenseCategoryGroupSpinnerAdapter(this, ExpenseBudget.EXPENSE_BUDGET_CATEGORY.values());
        spinnerExpenseBudgetCategory.setAdapter(expenseCategoryGroupSpinnerAdapter);
        spinnerExpenseBudgetCategory.setSelection(expenseBudget.getCategoryGroup().ordinal());
        spinnerCycle = (Spinner) findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(this, Cycle.values());
        spinnerCycle.setAdapter(cycleSpinnerAdapter);
        spinnerCycle.setSelection(expenseBudget.getCycle().ordinal());
        editTextBudgetAmount = (EditText) findViewById(R.id.editTextBudgetAmount);
        editTextBudgetAmount.setText(expenseBudget.getBudgetAmount().toString());
        checkBoxRollover = (CheckBox) findViewById(R.id.checkBoxRollover);
        checkBoxRollover.setChecked(expenseBudget.getRollOver());
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        editTextNote.setText(expenseBudget.getNote());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_expense_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_save:
                expenseBudget.setName(editTextName.getText().toString());
                expenseBudget.setCategoryGroup((ExpenseBudget.EXPENSE_BUDGET_CATEGORY) spinnerExpenseBudgetCategory.getSelectedItem());
                expenseBudget.setCycle(Cycle.valueOf(spinnerCycle.getSelectedItem().toString()));
                expenseBudget.setBudgetAmount(new BigDecimal(editTextBudgetAmount.getText().toString()));
                expenseBudget.setRollOver(checkBoxRollover.isChecked());
                expenseBudget.setNote(editTextNote.getText().toString());
                this.persist.save(expenseBudget);
        }
//        Intent intent = new Intent(this, BudgetExpenseActivity.class);
//        startActivity(intent);
        this.finish();

        return super.onOptionsItemSelected(item);
    }

    public void showWhatIsRollover(View view) {
        WhatIsThisDialogFragment dialogFragment = new WhatIsThisDialogFragment();
        dialogFragment.setTitle(getString(R.string.what_is_roll_over_title));
        dialogFragment.setMessage(getString(R.string.what_is_roll_over));
        dialogFragment.show(getSupportFragmentManager(), "tag");
    }
}
