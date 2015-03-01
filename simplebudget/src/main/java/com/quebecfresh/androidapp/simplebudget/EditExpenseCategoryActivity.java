package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseCategory;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseCategoryPersist;

import java.math.BigDecimal;


public class EditExpenseCategoryActivity extends ActionBarActivity {

    private EditText editTextName;
    private Spinner spinnerGroup;
    private Spinner spinnerCycle;
    private EditText editTextBudgetAmount;
    private EditText editTextNote;
    private ExpenseCategory category;
    private ExpenseCategoryPersist persist;
    private Long rowID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense_category);

        Intent intent = getIntent();
        rowID = intent.getLongExtra(BudgetExpenseActivity.EXTRA_EXPENSE_CATEGORY_ID, 0);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        persist = new ExpenseCategoryPersist(db);
        if(rowID>0) {
            category = persist.read(rowID);
        }else{
            category = new ExpenseCategory();
        }

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setText(category.getName());
        spinnerGroup = (Spinner) this.findViewById(R.id.spinnerExpenseCategoryGroup);
        ExpenseCategoryGroupSpinnerAdapter expenseCategoryGroupSpinnerAdapter = new ExpenseCategoryGroupSpinnerAdapter(this, ExpenseCategory.EXPENSE_CATEGORY_GROUP.values());
        spinnerGroup.setAdapter(expenseCategoryGroupSpinnerAdapter);
        spinnerGroup.setSelection(category.getCategoryGroup().ordinal());
        spinnerCycle = (Spinner) findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(this, Cycle.values());
        spinnerCycle.setAdapter(cycleSpinnerAdapter);
        spinnerCycle.setSelection(category.getCycle().ordinal());
        editTextBudgetAmount = (EditText) findViewById(R.id.editTextBudgetAmount);
        editTextBudgetAmount.setText(category.getBudgetAmount().toString());
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        editTextNote.setText(category.getNote());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_expense_category, menu);
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
                category.setName(editTextName.getText().toString());
                category.setCategoryGroup((ExpenseCategory.EXPENSE_CATEGORY_GROUP)spinnerGroup.getSelectedItem());
                category.setCycle(Cycle.valueOf(spinnerCycle.getSelectedItem().toString()));
                category.setBudgetAmount(new BigDecimal(editTextBudgetAmount.getText().toString()));
                category.setNote(editTextNote.getText().toString());
                this.persist.save(category);
        }
//        Intent intent = new Intent(this, BudgetExpenseActivity.class);
//        startActivity(intent);
        this.finish();

        return super.onOptionsItemSelected(item);
    }
}
