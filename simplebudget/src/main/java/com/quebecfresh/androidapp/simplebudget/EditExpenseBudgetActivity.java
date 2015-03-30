package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.math.BigDecimal;
import java.util.Calendar;


public class EditExpenseBudgetActivity extends ActionBarActivity implements ChooseAccountDialogFragment.AccountChooseListener {

    private EditText mEditTextName;
    private Button mButtonAccount;
    private Spinner mSpinnerExpenseBudgetCategory;
    private Spinner mSpinnerCycle;
    private Button mButtonChooseCycleStartDate;
    private EditText mEditTextBudgetAmount;
    private EditText mEditTextNote;
    private CheckBox mCheckBoxRollover;
    private ExpenseBudget mExpenseBudget;
    private ExpenseBudgetPersist mExpenseBudgetPersist;
    private Long mRowID;

    @Override
    public void choose(Account account) {
        this.mExpenseBudget.setAccount(account);
        this.mButtonAccount.setText(account.getName());
    }

    public void chooseAccount(View view) {
        ChooseAccountDialogFragment chooseAccountDialogFragment = new ChooseAccountDialogFragment();
        AccountPersist accountPersist = new AccountPersist(this);
        chooseAccountDialogFragment.setAccountList(accountPersist.readAll());
        chooseAccountDialogFragment.show(this.getSupportFragmentManager(), "Choose account");
        chooseAccountDialogFragment.setAccountChooseListener(this);
    }


    public void chooseCycleStartDate(View view){
        ChooseDateDialogFragment chooseDateDialogFragment = new ChooseDateDialogFragment();
        chooseDateDialogFragment.setCalendar(Calendar.getInstance());
        chooseDateDialogFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mExpenseBudget.setCycleStartDate(c.getTimeInMillis());
                mButtonChooseCycleStartDate.setText(mExpenseBudget.getCycleStartDateLabel());
            }
        });
        chooseDateDialogFragment.show(getSupportFragmentManager(), "Choose cycle start date");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense_budget);

        Intent intent = getIntent();
        mRowID = intent.getLongExtra(InitializeExpenseBudgetActivity.EXTRA_EXPENSE_BUDGET_ID, 0);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        mExpenseBudgetPersist = new ExpenseBudgetPersist(this);
        if (mRowID > 0) {
            mExpenseBudget = mExpenseBudgetPersist.read(mRowID);
        } else {
            mExpenseBudget = new ExpenseBudget();
        }


        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextName.setText(mExpenseBudget.getName());
        mButtonAccount = (Button) findViewById(R.id.buttonAccount);
        if (mExpenseBudget.getAccount() != null) {
            mButtonAccount.setText(mExpenseBudget.getAccount().getName() + " : " + mExpenseBudget.getAccount().getBalance().toString());
        }
        mSpinnerExpenseBudgetCategory = (Spinner) this.findViewById(R.id.spinnerExpenseBudgetCategory);
        ExpenseCategoryGroupSpinnerAdapter expenseCategoryGroupSpinnerAdapter = new ExpenseCategoryGroupSpinnerAdapter(this, ExpenseBudget.EXPENSE_BUDGET_CATEGORY.values());
        mSpinnerExpenseBudgetCategory.setAdapter(expenseCategoryGroupSpinnerAdapter);
        mSpinnerExpenseBudgetCategory.setSelection(mExpenseBudget.getExpenseBudgetCategory().ordinal());
        mSpinnerCycle = (Spinner) findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(this, Cycle.values());
        mSpinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                TextView textViewBudgetDate = (TextView) findViewById(R.id.textViewBudgetDate);
//
//                switch ((Cycle) parent.getSelectedItem()) {
//                    case Daily:
//                        textViewBudgetDate.setText("Every day");
//                        break;
//                    case Weekly:
//                        textViewBudgetDate.setText("Every Sunday");
//                        break;
//                    case Every_2_Weeks:
//                        textViewBudgetDate.setText("Every 2 Sunday");
//                        break;
//                    case Monthly:
//                        textViewBudgetDate.setText("First day of every month");
//                        break;
//                    case Every_2_Months:
//                        textViewBudgetDate.setText("First day of every 2 months");
//                        break;
//                    case Every_3_Months:
//                        textViewBudgetDate.setText("First day of every 3 months");
//                        break;
//                    case Every_6_Months:
//                        textViewBudgetDate.setText("First day of every 6 months");
//                        break;
//                    case Yearly:
//                        textViewBudgetDate.setText("First day of year");
//                        break;
//
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerCycle.setAdapter(cycleSpinnerAdapter);
        mSpinnerCycle.setSelection(mExpenseBudget.getCycle().ordinal());
        mButtonChooseCycleStartDate = (Button)findViewById(R.id.buttonChooseCycleStartDate);
        mEditTextBudgetAmount = (EditText) findViewById(R.id.editTextBudgetAmount);
        mEditTextBudgetAmount.setText(mExpenseBudget.getBudgetAmount().toString());
        mCheckBoxRollover = (CheckBox) findViewById(R.id.checkBoxRollover);
        mCheckBoxRollover.setChecked(mExpenseBudget.getRollOver());
        mEditTextNote = (EditText) findViewById(R.id.editTextNote);
        mEditTextNote.setText(mExpenseBudget.getNote());
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
                mExpenseBudget.setName(mEditTextName.getText().toString());
                mExpenseBudget.setExpenseBudgetCategory((ExpenseBudget.EXPENSE_BUDGET_CATEGORY) mSpinnerExpenseBudgetCategory.getSelectedItem());
                mExpenseBudget.setCycle(Cycle.valueOf(mSpinnerCycle.getSelectedItem().toString()));
                mExpenseBudget.setBudgetAmount(new BigDecimal(mEditTextBudgetAmount.getText().toString()));
                mExpenseBudget.setRollOver(mCheckBoxRollover.isChecked());
                mExpenseBudget.setNote(mEditTextNote.getText().toString());
                this.mExpenseBudgetPersist.save(mExpenseBudget);
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
