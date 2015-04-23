package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    private AccountPersist mAccountPersist;

    private Expense mExpense = new Expense();
    private List<ExpenseBudget> mExpenseBudgetList;
    private ExpenseBudget mSelectedExpenseBudget;
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
                mExpense.setDate(c.getTimeInMillis());
                updateView();
//                mButtonDate.setText(mExpense.getSpendDateLabel());
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
//                mButtonChooseExpenseAccount.setText(account.getName());
                mExpense.setAccount(account);
                updateView();
//                if (mExpense.getAccount().getBalance().intValue() > 0) {
//                    mButtonChooseExpenseAccount.setTextColor(getResources().getColor(R.color.neutral));
//                } else {
//                    mButtonChooseExpenseAccount.setTextColor(getResources().getColor(R.color.negative));
//                }

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
//                mSelectedExpenseBudget = expenseBudget;
//                updateView();

            }
        });
        chooseExpenseBudgetDialogFragment.show(getSupportFragmentManager(), "ChooseExpenseBudget");

    }


    private void updateView() {
        mButtonChooseExpenseBudget.setText(mExpense.getExpenseBudget().getName());
        mEditTextExpenseAmount.requestFocus();
        mButtonDate.setText(this.mExpense.getDateLabel());
        mButtonChooseExpenseAccount.setText(mExpense.getExpenseBudget().getAccount().getName());

        Calendar current = Calendar.getInstance();
        long end = current.getTimeInMillis();
        long begin = Utils.getBeginOfPastCycle(mExpense.getExpenseBudget().getCycle(), current);
        List<Expense> expenseList = mExpensePersist.readAll(begin, end, mSelectedExpenseBudget, mWritableDatabase);
        BigDecimal budgetAmount = mExpense.getExpenseBudget().getBudgetAmount();
        BigDecimal usedAmount = Utils.calTotalExpense(expenseList);
        BigDecimal unusedBalance = budgetAmount.subtract(usedAmount);
        mEditTextExpenseAmount.setText("");
        mEditTextExpenseAmount.setHint(getResources().getString(R.string.unused_budget) + " " + unusedBalance.toString());

        if (unusedBalance.intValue() > 0) {
            mEditTextExpenseAmount.setTextColor(getResources().getColor(R.color.neutral));
            mButtonChooseExpenseBudget.setTextColor(getResources().getColor(R.color.neutral));
        } else {
            mEditTextExpenseAmount.setTextColor(getResources().getColor(R.color.negative));
            mButtonChooseExpenseBudget.setTextColor(getResources().getColor(R.color.negative));
        }

        if (mExpense.getAccount().getBalance().intValue() > 0) {
            mButtonChooseExpenseAccount.setTextColor(getResources().getColor(R.color.neutral));
        } else {
            mButtonChooseExpenseAccount.setTextColor(getResources().getColor(R.color.negative));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense);

        //Set the layout size to fit phone screen
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutInsideScrollView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        linearLayout.setMinimumHeight(metrics.heightPixels*80/100);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mWritableDatabase = databaseHelper.getWritableDatabase();

        mAccountPersist = new AccountPersist();
        mAccountList = mAccountPersist.readAll(mWritableDatabase);


        ExpenseBudgetPersist mExpenseBudgetPersist = new ExpenseBudgetPersist();
        mExpenseBudgetList = mExpenseBudgetPersist.readAllBudgetAmountNotZero(mWritableDatabase);
        if(mExpenseBudgetList.size() > 0){
            mSelectedExpenseBudget = mExpenseBudgetList.get(0);
        }

        mExpensePersist = new ExpensePersist();
        this.mExpense = new Expense();
        if (mExpenseBudgetList.size() > 0) {
            this.mExpense.setExpenseBudget(mExpenseBudgetList.get(0));
        }
        this.mExpense.setDate(System.currentTimeMillis());
        if (mAccountList.size() > 0) {
            mExpense.setAccount(mAccountList.get(0));
        }

        mButtonDate = (Button) findViewById(R.id.buttonChooseDate);
        mButtonChooseExpenseBudget = (Button) findViewById(R.id.buttonChooseExpenseBudget);
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

        switch (id){
            case R.id.action_save:
                if(validateInput()){
                    mExpense.setAmount(new BigDecimal(mEditTextExpenseAmount.getText().toString()));
                    mExpense.setNote(mEditTextNote.getText().toString());
                    this.mExpensePersist.insert(this.mExpense, mWritableDatabase);
                    Account account = mExpense.getAccount();
                    account.setBalance(account.getBalance().subtract(mExpense.getAmount()));
                    mAccountPersist.update(account, mWritableDatabase);
                    this.finish();
                }
        }

        return super.onOptionsItemSelected(item);
    }


    private Boolean validateInput(){
        if(Utils.isEditTextNumeric(mEditTextExpenseAmount)){
            return true;
        }else{
            Toast.makeText(this,getResources().getString(R.string.toast_amount_have_to_be_numeric), Toast.LENGTH_LONG).show();
            mEditTextExpenseAmount.requestFocus();
            return false;
        }
    }


}
