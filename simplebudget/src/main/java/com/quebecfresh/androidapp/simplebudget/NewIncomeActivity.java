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

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Income;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.IncomePersist;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


public class NewIncomeActivity extends ActionBarActivity {

    private  SQLiteDatabase mWritableDatabase;
    private IncomePersist incomePersist;
    private AccountPersist accountPersist;
    private IncomeBudgetPersist incomeBudgetPersist;

    private List<Account> accountList;
    private List<IncomeBudget> incomeBudgetList;
    private Income income;
    private Button buttonChooseIncomeBudget;
    private EditText editTextIncomeAmount;
    private Button buttonChooseIncomeDate;
    private Button buttonChooseIncomeAccount;
    private EditText editTextIncomeNote;

    public void chooseIncomeBudget(View view) {
        ChooseIncomeBudgetDialogFragment chooseIncomeBudgetDialogFragment = new ChooseIncomeBudgetDialogFragment();
        chooseIncomeBudgetDialogFragment.setIncomeBudgetList(this.incomeBudgetList);
        chooseIncomeBudgetDialogFragment.setIncomeBudgetChooseListener(new ChooseIncomeBudgetDialogFragment.IncomeBudgetChooseListener() {
            @Override
            public void choose(IncomeBudget incomeBudget) {
                buttonChooseIncomeBudget.setText(incomeBudget.getName());
                editTextIncomeAmount.setHint(getResources().getString(R.string.budget_amount) + " " + incomeBudget.getBudgetAmount());
                buttonChooseIncomeAccount.setText(incomeBudget.getAccount().getName());
                income.setIncomeBudget(incomeBudget);
                income.setAccount(incomeBudget.getAccount());
            }
        });

        chooseIncomeBudgetDialogFragment.show(this.getSupportFragmentManager(), "Choose IncomeBudget");
    }

    public void chooseIncomeAccount(View view) {
        ChooseAccountDialogFragment chooseAccountDialogFragment = new ChooseAccountDialogFragment();
        chooseAccountDialogFragment.setAccountList(this.accountList);
        chooseAccountDialogFragment.setAccountChooseListener(new ChooseAccountDialogFragment.AccountChooseListener() {
            @Override
            public void choose(Account account) {
                buttonChooseIncomeAccount.setText(account.getName());
                income.setAccount(account);
            }
        });
        chooseAccountDialogFragment.show(this.getSupportFragmentManager(), "Choose account");
    }


    public void chooseIncomeDate(View view) {
        ChooseDateDialogFragment chooseDateDialogFragment = new ChooseDateDialogFragment();
        chooseDateDialogFragment.setCalendar(Calendar.getInstance());
        chooseDateDialogFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                income.setReceivedDate(c.getTimeInMillis());
                buttonChooseIncomeDate.setText(income.getReceivedDateLabel());
            }
        });
        chooseDateDialogFragment.show(this.getSupportFragmentManager(), "Choose income date");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mWritableDatabase = databaseHelper.getWritableDatabase();
        incomePersist = new IncomePersist();
        accountPersist = new AccountPersist();
        incomeBudgetPersist = new IncomeBudgetPersist();
        List<Income> incomeUnconfirmedList = incomePersist.readAllUnconfirmedConfirmed(mWritableDatabase);
        accountList = accountPersist.readAll(mWritableDatabase);
        incomeBudgetList = incomeBudgetPersist.readAllBudgetAmountNotZero(mWritableDatabase);

        income = new Income();
        income.setIncomeBudget(incomeBudgetList.get(0));
        income.setReceivedDate(System.currentTimeMillis());
        income.setAccount(accountList.get(0));
        buttonChooseIncomeBudget = (Button) findViewById(R.id.buttonChooseIncomeBudget);
        buttonChooseIncomeBudget.setText(income.getIncomeBudget().getName());
        editTextIncomeAmount = (EditText) findViewById(R.id.editTextIncomeAmount);
        editTextIncomeAmount.setHint(income.getIncomeBudget().getBudgetAmount().toString());
        buttonChooseIncomeDate = (Button) findViewById(R.id.buttonChooseIncomeDate);
        buttonChooseIncomeDate.setText(income.getReceivedDateLabel());
        buttonChooseIncomeAccount = (Button) findViewById(R.id.buttonChooseIncomeAccount);
        buttonChooseIncomeAccount.setText(income.getAccount().getName());
        editTextIncomeNote = (EditText) findViewById(R.id.editTextIncomeNote);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_income, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            income.setAmount(new BigDecimal(editTextIncomeAmount.getText().toString()));
            income.setNote(editTextIncomeNote.getText().toString());
            Account account = income.getAccount();
            account.setBalance(account.getBalance().add(income.getAmount()));
            accountPersist.update(account, mWritableDatabase);
            incomePersist.insert(income, mWritableDatabase);
        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
