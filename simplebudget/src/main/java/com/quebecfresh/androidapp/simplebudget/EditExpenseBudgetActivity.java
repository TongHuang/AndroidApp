package com.quebecfresh.androidapp.simplebudget;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.math.BigDecimal;
import java.util.List;


public class EditExpenseBudgetActivity extends ActionBarActivity implements ChooseAccountDialogFragment.AccountClickListener {

    private EditText editTextName;
    private Button buttonAccount;
    private Spinner spinnerExpenseBudgetCategory;
    private Spinner spinnerCycle;
    private EditText editTextBudgetAmount;
    private EditText editTextNote;
    private CheckBox checkBoxRollover;
    private ExpenseBudget expenseBudget;
    private ExpenseBudgetPersist persist;
    private Long rowID;

    @Override
    public void click(Account account) {
        this.expenseBudget.setAccount(account);
        this.buttonAccount.setText(account.getName() + " : " + account.getBalance().toString());
    }

    public void chooseAccount(View view) {
        ChooseAccountDialogFragment chooseAccountDialogFragment = new ChooseAccountDialogFragment();
        chooseAccountDialogFragment.show(this.getSupportFragmentManager(), "Choose account");
        chooseAccountDialogFragment.setAccountClickListener(this);
    }

    public void calFillAmount(View view){
        System.out.println(expenseBudget.calcFillAmount());
        TextView textViewBudgetDate  = (TextView)this.findViewById(R.id.textViewBudgetDate);
        textViewBudgetDate.setText(expenseBudget.calcFillAmount().toString());
    }

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
        buttonAccount = (Button) findViewById(R.id.buttonAccount);
        if (expenseBudget.getAccount() != null) {
            buttonAccount.setText(expenseBudget.getAccount().getName() + " : " + expenseBudget.getAccount().getBalance().toString());
        }
        spinnerExpenseBudgetCategory = (Spinner) this.findViewById(R.id.spinnerExpenseBudgetCategory);
        ExpenseCategoryGroupSpinnerAdapter expenseCategoryGroupSpinnerAdapter = new ExpenseCategoryGroupSpinnerAdapter(this, ExpenseBudget.EXPENSE_BUDGET_CATEGORY.values());
        spinnerExpenseBudgetCategory.setAdapter(expenseCategoryGroupSpinnerAdapter);
        spinnerExpenseBudgetCategory.setSelection(expenseBudget.getExpenseBudgetCategory().ordinal());
        spinnerCycle = (Spinner) findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(this, Cycle.values());
        spinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textViewBudgetDate = (TextView) findViewById(R.id.textViewBudgetDate);

                switch ((Cycle) parent.getSelectedItem()) {
                    case Daily:
                        textViewBudgetDate.setText("Every day");
                        break;
                    case Weekly:
                        textViewBudgetDate.setText("Every Sunday");
                        break;
                    case Every_2_Weeks:
                        textViewBudgetDate.setText("Every 2 Sunday");
                        break;
                     case Monthly:
                         textViewBudgetDate.setText("First day of every month");
                         break;
                     case Every_2_Months:
                         textViewBudgetDate.setText("First day of every 2 months");
                         break;
                     case Every_3_Months:
                         textViewBudgetDate.setText("First day of every 3 months");
                          break;
                     case Every_6_Months:
                         textViewBudgetDate.setText("First day of every 6 months");
                         break;
                     case Yearly:
                         textViewBudgetDate.setText("First day of year");
                         break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                expenseBudget.setExpenseBudgetCategory((ExpenseBudget.EXPENSE_BUDGET_CATEGORY) spinnerExpenseBudgetCategory.getSelectedItem());
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
