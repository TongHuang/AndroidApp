package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
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
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.ExpensePersist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NewExpenseActivity extends ActionBarActivity {

    private Button buttonDate;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private Expense expense = new Expense();
    private ExpensePersist expensePersist;
    private ExpenseBudgetPersist expenseBudgetPersist;
    private List<ExpenseBudget> expenseBudgetList = new ArrayList<ExpenseBudget>();
    private Button buttonChooseExpenseBudget;
    private EditText editTextExpenseAmount;
    private EditText editTextNote;

    public void chooseDate(View view) {
        ChooseDateDialogFragment datePickerFragment = new ChooseDateDialogFragment();
        datePickerFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                expense.setSpentDate(c.getTimeInMillis());
                buttonDate.setText(expense.getSpendDateLabel());
            }
        });

        datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }


    public void chooseExpenseBudget(View view) {
        ChooseExpenseBudgetDialogFragment chooseExpenseBudgetDialogFragment = new ChooseExpenseBudgetDialogFragment();
        chooseExpenseBudgetDialogFragment.setBudgetList(this.expenseBudgetList);
        chooseExpenseBudgetDialogFragment.setExpenseBudgetChooseListener(new ChooseExpenseBudgetDialogFragment.ExpenseBudgetChooseListener() {
            @Override
            public void Choose(ExpenseBudget expenseBudget) {
                expense.setExpenseBudget(expenseBudget);
                buttonChooseExpenseBudget.setText(expense.getExpenseBudget().getName());
                editTextExpenseAmount.setText(null);
                editTextExpenseAmount.setHint(getResources().getString(R.string.unused_balance) +   " " + expense.getExpenseBudget().getUnusedBalance().toString());
            }
        });
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
        setContentView(R.layout.activity_new_expense);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        expensePersist = new ExpensePersist(db);
        expenseBudgetPersist = new ExpenseBudgetPersist(db);
        expenseBudgetList = expenseBudgetPersist.readAllUnusedBalanceNotZero();

        this.expense = new Expense();
        this.expense.setExpenseBudget(expenseBudgetList.get(0));
        this.expense.setSpentDate(System.currentTimeMillis());

        buttonDate = (Button) findViewById(R.id.buttonChooseDate);
        buttonDate.setText(this.expense.getSpendDateLabel());
        buttonChooseExpenseBudget = (Button) findViewById(R.id.buttonChooseExpenseBudget);
        buttonChooseExpenseBudget.setText(expense.getExpenseBudget().getName());
        editTextExpenseAmount = (EditText) findViewById(R.id.editTextExpenseAmount);
        editTextExpenseAmount.setText(null);
        editTextExpenseAmount.setHint(getResources().getString(R.string.unused_balance) + " " + expense.getExpenseBudget().getUnusedBalance().toString());
        editTextNote = (EditText) findViewById(R.id.editTextExpenseNote);
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
            expense.setAmount(new BigDecimal(editTextExpenseAmount.getText().toString()));
            expense.setNote(editTextNote.getText().toString());
            ExpenseBudget expenseBudget = expense.getExpenseBudget();
            expenseBudget.setUnusedBalance(expenseBudget.getUnusedBalance().subtract(expense.getAmount()));
            this.expenseBudgetPersist.update(expenseBudget);
            this.expensePersist.insert(this.expense);
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
