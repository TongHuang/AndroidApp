package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.Utils;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.ExpensePersist;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


public class NewExpenseActivity extends ActionBarActivity {

    private SQLiteDatabase mWritableDatabase;
    private ExpensePersist mExpensePersist;
    private ExpenseBudgetPersist mExpenseBudgetPersist;

    private Expense mExpense = new Expense();
    private List<ExpenseBudget> mExpenseBudgetList;
    private List<Account> mAccountList;

    private Button mButtonDate;
    private Button mButtonChooseExpenseBudget;
    private EditText mEditTextExpenseAmount;
    private Button mButtonChooseExpenseAccount;
    private EditText mEditTextNote;

    public void chooseDate(View view) {
        ChooseDateDialogFragment datePickerFragment = new ChooseDateDialogFragment();
        datePickerFragment.setCalendar(Calendar.getInstance());
        datePickerFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mExpense.setSpentDate(c.getTimeInMillis());
                mButtonDate.setText(mExpense.getSpendDateLabel());
            }
        });

        datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void chooseExpenseAccount(View view) {
        ChooseAccountDialogFragment chooseAccountDialogFragment = new ChooseAccountDialogFragment();
        chooseAccountDialogFragment.setAccountList(this.mAccountList);
        chooseAccountDialogFragment.setAccountChooseListener(new ChooseAccountDialogFragment.AccountChooseListener() {
            @Override
            public void choose(Account account) {
                mButtonChooseExpenseAccount.setText(account.getName());
                mExpense.setAccount(account);
                if (mExpense.getAccount().getBalance().intValue() > 0) {
                    mButtonChooseExpenseAccount.setBackgroundColor(getResources().getColor(R.color.light_blue));
                } else {
                    mButtonChooseExpenseAccount.setBackgroundColor(getResources().getColor(R.color.red));
                }

            }
        });
        chooseAccountDialogFragment.show(this.getSupportFragmentManager(), "Choose account");
    }


    public void chooseExpenseBudget(View view) {
        ChooseExpenseBudgetDialogFragment chooseExpenseBudgetDialogFragment = new ChooseExpenseBudgetDialogFragment();
        chooseExpenseBudgetDialogFragment.setExpenseBudgetList(this.mExpenseBudgetList);
        chooseExpenseBudgetDialogFragment.setmExpenseBudgetChooseListener(new ChooseExpenseBudgetDialogFragment.ExpenseBudgetChooseListener() {
            @Override
            public void Choose(ExpenseBudget expenseBudget) {
                mExpense.setExpenseBudget(expenseBudget);
                mExpense.setAccount(expenseBudget.getAccount());
                updateView();

            }
        });
        chooseExpenseBudgetDialogFragment.show(getSupportFragmentManager(), "ChooseExpenseBudget");

    }


    private void updateView() {
        mButtonChooseExpenseBudget.setText(mExpense.getExpenseBudget().getName());
        mButtonChooseExpenseAccount.setText(mExpense.getExpenseBudget().getAccount().getName());
        Calendar current = Calendar.getInstance();
        long end = current.getTimeInMillis();
        long begin = Utils.getBeginOfPastCycle(mExpense.getExpenseBudget().getCycle(), current);
        List<Expense> expenseList = mExpensePersist.readAll(begin, end, mWritableDatabase);
        BigDecimal budgetAmount = mExpense.getExpenseBudget().getBudgetAmount();
        BigDecimal usedAmount = Utils.calTotalExpense(expenseList);
        BigDecimal unusedBalance = budgetAmount.subtract(usedAmount);
        mEditTextExpenseAmount.setText("");
        mEditTextExpenseAmount.setHint(getResources().getString(R.string.unused_budget) + " " + unusedBalance.toString());
        if (unusedBalance.intValue() > 0) {
            mEditTextExpenseAmount.setTextColor(getResources().getColor(R.color.green));
        } else {
            mEditTextExpenseAmount.setTextColor(getResources().getColor(R.color.red));
        }
        ExpenseBudgetCheckerFragment expenseBudgetCheckerFragment = new ExpenseBudgetCheckerFragment();
        expenseBudgetCheckerFragment.setBudgetAmount(budgetAmount);
        expenseBudgetCheckerFragment.setUsedAmount(usedAmount);
        expenseBudgetCheckerFragment.setUnusedBalance(unusedBalance);
        expenseBudgetCheckerFragment.setDateLabel(Utils.formatDate(begin));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerExpenseBudgetChecker, expenseBudgetCheckerFragment);
        fragmentTransaction.commit();
        if (mExpense.getAccount().getBalance().intValue() > 0) {
            mButtonChooseExpenseAccount.setBackgroundColor(getResources().getColor(R.color.light_blue));
        } else {
            mButtonChooseExpenseAccount.setBackgroundColor(getResources().getColor(R.color.red));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mWritableDatabase = databaseHelper.getWritableDatabase();

        AccountPersist accountPersist = new AccountPersist();
        mAccountList = accountPersist.readAll(mWritableDatabase);

        mExpensePersist = new ExpensePersist();
        mExpenseBudgetPersist = new ExpenseBudgetPersist();
        mExpenseBudgetList = mExpenseBudgetPersist.readAllBudgetAmountNotZero(mWritableDatabase);

        this.mExpense = new Expense();
        if (mExpenseBudgetList.size() > 0) {
            this.mExpense.setExpenseBudget(mExpenseBudgetList.get(0));
        }
        this.mExpense.setSpentDate(System.currentTimeMillis());
        if (mAccountList.size() > 0) {
            mExpense.setAccount(mAccountList.get(0));
        }

        mButtonDate = (Button) findViewById(R.id.buttonChooseDate);
        mButtonDate.setText(this.mExpense.getSpendDateLabel());
        mButtonChooseExpenseBudget = (Button) findViewById(R.id.buttonChooseExpenseBudget);
        mButtonChooseExpenseBudget.setText(mExpense.getExpenseBudget().getName());
        mEditTextExpenseAmount = (EditText) findViewById(R.id.editTextExpenseAmount);
        mButtonChooseExpenseAccount = (Button) findViewById(R.id.buttonChooseExpenseAccount);
        mEditTextNote = (EditText) findViewById(R.id.editTextExpenseNote);
        this.updateView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_save) {
            mExpense.setAmount(new BigDecimal(mEditTextExpenseAmount.getText().toString()));
            mExpense.setNote(mEditTextNote.getText().toString());
            ExpenseBudget expenseBudget = mExpense.getExpenseBudget();
            expenseBudget.setUnusedBalance(expenseBudget.getUnusedBalance().subtract(mExpense.getAmount()));
            this.mExpenseBudgetPersist.update(expenseBudget, mWritableDatabase);
            this.mExpensePersist.insert(this.mExpense, mWritableDatabase);
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }


}
