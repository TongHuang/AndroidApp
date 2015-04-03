package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.quebecfresh.androidapp.simplebudget.model.Expense;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.Utils;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.ExpensePersist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NewExpenseActivity extends ActionBarActivity {

    private Expense mExpense = new Expense();
    private ExpensePersist mExpensePersist;
    private ExpenseBudgetPersist mExpenseBudgetPersist;
    private List<ExpenseBudget> mExpenseBudgetList = new ArrayList<ExpenseBudget>();
    private Button mButtonDate;
    private Button mButtonChooseExpenseBudget;
    private EditText mEditTextExpenseAmount;
    private Button mButtonChooseAccount;
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


    public void chooseExpenseBudget(View view) {
        ChooseExpenseBudgetDialogFragment chooseExpenseBudgetDialogFragment = new ChooseExpenseBudgetDialogFragment();
        chooseExpenseBudgetDialogFragment.setExpenseBudgetList(this.mExpenseBudgetList);
        chooseExpenseBudgetDialogFragment.setExpenseBudgetChooseListener(new ChooseExpenseBudgetDialogFragment.ExpenseBudgetChooseListener() {
            @Override
            public void Choose(ExpenseBudget expenseBudget) {
                mExpense.setExpenseBudget(expenseBudget);
                mButtonChooseExpenseBudget.setText(mExpense.getExpenseBudget().getName());
                mEditTextExpenseAmount.setText(null);
                mEditTextExpenseAmount.setHint(getResources().getString(R.string.unused_balance) + " " + mExpense.getExpenseBudget().getUnusedBalance().toString());
                mButtonChooseAccount.setText(expenseBudget.getAccount().getName());
                Calendar current = Calendar.getInstance();
                long end = current.getTimeInMillis();
                long begin = Utils.getBeginOfPastCycle(mExpense.getExpenseBudget().getCycle(),current);
                List<Expense> expenseList =  mExpensePersist.readAll(begin, end);
                BigDecimal budgetAmount = expenseBudget.getBudgetAmount();
                BigDecimal usedAmount = Utils.calTotalExpense(expenseList);
                BigDecimal unusedBalance = budgetAmount.subtract(usedAmount);
                ExpenseBudgetCheckerFragment expenseBudgetCheckerFragment = new ExpenseBudgetCheckerFragment();
                expenseBudgetCheckerFragment.setBudgetAmount(budgetAmount);
                expenseBudgetCheckerFragment.setUsedAmount(usedAmount);
                expenseBudgetCheckerFragment.setUnusedBalance(unusedBalance);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerExpenseBudgetChecker, expenseBudgetCheckerFragment);
                fragmentTransaction.commit();
            }
        });
        chooseExpenseBudgetDialogFragment.show(getSupportFragmentManager(), "ChooseExpenseBudget");

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);
        mExpensePersist = new ExpensePersist(this);
        mExpenseBudgetPersist = new ExpenseBudgetPersist(this);
        mExpenseBudgetList = mExpenseBudgetPersist.readAllUnusedBalanceNotZero();

        this.mExpense = new Expense();
        this.mExpense.setExpenseBudget(mExpenseBudgetList.get(0));
        this.mExpense.setSpentDate(System.currentTimeMillis());

        mButtonDate = (Button) findViewById(R.id.buttonChooseDate);
        mButtonDate.setText(this.mExpense.getSpendDateLabel());
        mButtonChooseExpenseBudget = (Button) findViewById(R.id.buttonChooseExpenseBudget);
        mButtonChooseExpenseBudget.setText(mExpense.getExpenseBudget().getName());
        mEditTextExpenseAmount = (EditText) findViewById(R.id.editTextExpenseAmount);
        mEditTextExpenseAmount.setText(null);
        mEditTextExpenseAmount.setHint(getResources().getString(R.string.unused_balance) + " " + mExpense.getExpenseBudget().getUnusedBalance().toString());
        mButtonChooseAccount = (Button)findViewById(R.id.buttonChooseAccount);
        mEditTextNote = (EditText) findViewById(R.id.editTextExpenseNote);
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
            this.mExpenseBudgetPersist.update(expenseBudget);
            this.mExpensePersist.insert(this.mExpense);
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }


}
