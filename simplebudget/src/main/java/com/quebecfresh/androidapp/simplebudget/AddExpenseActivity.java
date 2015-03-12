package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Budget;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddExpenseActivity extends ActionBarActivity implements ChooseAccountDialogFragment.AccountClickListener,
        ChooseExpenseBudgetDialogFragment.BudgetClickListener {

    public static final int CHOOSE_ACCOUNT_FOR_RESULT_CODE = 1;
    public static final int CHOOSE_BUDGET_FOR_RESULT_CODE = 2;

    private Button buttonDate;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private AccountPersist accountPersist;
    private ExpenseBudgetPersist expenseBudgetPersist;
    private List<Account> accountBalanceNotZeroList = new ArrayList<Account>();
    private List<Budget> expenseBudgetBalanceNotZeroList = new ArrayList<Budget>();
    private Button buttonChooseAccount;
    private Button buttonChooseExpenseBudget;
    private EditText editTextExpenseAmount;

    @Override
    public void click(Account account) {
        this.buttonChooseAccount.setText(account.getName() + " : " + account.getBalance().toString());
    }

    @Override
    public void click(Budget budget) {
        this.buttonChooseExpenseBudget.setText(budget.getName());
        this.editTextExpenseAmount.setText(null);
        this.editTextExpenseAmount.setHint("Unused balance:" + budget.getUnusedBalance().toString());
    }

    public void chooseDate(View view) {
        ChooseDateDialogFragment datePickerFragment = new ChooseDateDialogFragment();
        datePickerFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");

                buttonDate.setText(simpleDateFormat.format(c.getTime()));
            }
        });

        datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void chooseAccount(View view) {
        ChooseAccountDialogFragment chooseAccountDialog = new ChooseAccountDialogFragment();
        chooseAccountDialog.show(getSupportFragmentManager(), "Dialog");
        chooseAccountDialog.setAccountClickListener(this);

//        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//       fragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_NONE);
//        fragmentTransaction.add(android.R.id.content, chooseAccountDialog).addToBackStack(null).commit();
//        Intent intent = new Intent(this, InitializeAccountActivity.class);
//        startActivityForResult(intent, CHOOSE_ACCOUNT_FOR_RESULT_CODE);
    }

    public void chooseExpenseBudget(View view) {
        ChooseExpenseBudgetDialogFragment chooseExpenseBudgetDialogFragment = new ChooseExpenseBudgetDialogFragment();
        chooseExpenseBudgetDialogFragment.setBudgetClickListener(this);
        chooseExpenseBudgetDialogFragment.setBudgetList(this.expenseBudgetBalanceNotZeroList);
        chooseExpenseBudgetDialogFragment.show(getSupportFragmentManager(), "ChooseExpenseBudget");

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case CHOOSE_ACCOUNT_FOR_RESULT_CODE:
//                Long accountID = data.getLongExtra(InitializeAccountActivity.EXTRA_ACCOUNT_ID, 1);
//                AccountPersist accountPersist = new AccountPersist(databaseHelper.getReadableDatabase());
//                account = accountPersist.read(accountID);
//                Button buttonChooseAccount = (Button) findViewById(R.id.buttonChooseAccount);
//                buttonChooseAccount.setText(account.getName());
//                break;
//            case CHOOSE_BUDGET_FOR_RESULT_CODE:
//                break;
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        Calendar c = Calendar.getInstance();
        buttonDate = (Button) findViewById(R.id.buttonChooseDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");

        buttonDate.setText(simpleDateFormat.format(c.getTime()));


        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        accountPersist = new AccountPersist(db);
        accountBalanceNotZeroList = accountPersist.readAllBalanceNotZero();
        expenseBudgetPersist = new ExpenseBudgetPersist(db);
        expenseBudgetBalanceNotZeroList.clear();
        expenseBudgetBalanceNotZeroList.addAll(expenseBudgetPersist.readAllUnusedBalanceNotZero());
//        if (accountBalanceNotZeroList.size() > 0) {
//            Account account = accountBalanceNotZeroList.get(0);
//            buttonChooseAccount = (Button) findViewById(R.id.buttonChooseAccount);
//            buttonChooseAccount.setText(account.getName() + " : "  + account.getBalance().toString());
//        }
        if(expenseBudgetBalanceNotZeroList.size() > 0) {
            Budget budget = expenseBudgetBalanceNotZeroList.get(0);
            buttonChooseExpenseBudget = (Button) findViewById(R.id.buttonChooseExpenseBudget);
            buttonChooseExpenseBudget.setText(budget.getName());
            editTextExpenseAmount = (EditText) findViewById(R.id.editTextExpenseAmount);
            editTextExpenseAmount.setText(budget.getBudgetAmount().toString());
        }


//        buttonDate.setText(c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
