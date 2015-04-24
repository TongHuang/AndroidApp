package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


public class EditExpenseBudgetActivity extends ActionBarActivity {

    private DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    private SQLiteDatabase mWritableDatabase;
    private ExpenseBudgetPersist mExpenseBudgetPersist;

    private EditText mEditTextName;
    private Button mButtonAccount;
    private Spinner mSpinnerExpenseBudgetCategory;
    private Spinner mSpinnerCycle;
    private EditText mEditTextBudgetAmount;
    private EditText mEditTextNote;
    private ExpenseBudget mExpenseBudget;

    private List<Account> mAccountList;
    private Long mRowID;


    public void chooseAccount(View view) {
        ChooseAccountDialogFragment chooseAccountDialogFragment = new ChooseAccountDialogFragment();

        chooseAccountDialogFragment.setAccountList(mAccountList);
        chooseAccountDialogFragment.show(this.getSupportFragmentManager(), "Choose account");
        chooseAccountDialogFragment.setAccountChooseListener(new ChooseAccountDialogFragment.AccountChooseListener() {
            @Override
            public void choose(Account account) {
                mExpenseBudget.setAccount(account);
                mButtonAccount.setText(account.getName());
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense_budget);


        DisplayMetrics metrics = new DisplayMetrics();
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutInsideScrollView);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        linearLayout.setMinimumHeight(metrics.heightPixels*8/10);

        mWritableDatabase = mDatabaseHelper.getWritableDatabase();

        Intent intent = getIntent();
        mRowID = intent.getLongExtra(ExpandableExpenseBudgetFragment.EXTRA_EXPENSE_BUDGET_ID, -1);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        AccountPersist accountPersist = new AccountPersist();
        mAccountList = accountPersist.readAll(mWritableDatabase);

        mExpenseBudgetPersist = new ExpenseBudgetPersist();
        if (mRowID > 0) {
            mExpenseBudget = mExpenseBudgetPersist.read(mRowID, mWritableDatabase);
        } else {
            mExpenseBudget = new ExpenseBudget();
        }


        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextName.setText(mExpenseBudget.getName());
        mEditTextName.requestFocus();
        mButtonAccount = (Button) findViewById(R.id.buttonAccount);
        if (mExpenseBudget.getAccount() != null) {
            mButtonAccount.setText(mExpenseBudget.getAccount().getName());
        }
        mSpinnerExpenseBudgetCategory = (Spinner) this.findViewById(R.id.spinnerExpenseBudgetCategory);
        ExpenseCategoryGroupSpinnerAdapter expenseCategoryGroupSpinnerAdapter = new ExpenseCategoryGroupSpinnerAdapter(this, ExpenseBudget.EXPENSE_BUDGET_CATEGORY.values());
        mSpinnerExpenseBudgetCategory.setAdapter(expenseCategoryGroupSpinnerAdapter);
        mSpinnerExpenseBudgetCategory.setSelection(mExpenseBudget.getExpenseBudgetCategory().ordinal());
        mSpinnerCycle = (Spinner) findViewById(R.id.spinnerCycle);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(Cycle.values(),this);

        mSpinnerCycle.setAdapter(cycleSpinnerAdapter);
        mSpinnerCycle.setSelection(mExpenseBudget.getCycle().ordinal());
        mEditTextBudgetAmount = (EditText) findViewById(R.id.editTextBudgetAmount);
        mEditTextBudgetAmount.setText(mExpenseBudget.getBudgetAmount().toString());
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
                mExpenseBudget.setNote(mEditTextNote.getText().toString());
                this.mExpenseBudgetPersist.save(mExpenseBudget, mWritableDatabase);
        }
//        Intent intent = new Intent(this, BudgetExpenseActivity.class);
//        startActivity(intent);
        this.finish();

        return super.onOptionsItemSelected(item);
    }

}
