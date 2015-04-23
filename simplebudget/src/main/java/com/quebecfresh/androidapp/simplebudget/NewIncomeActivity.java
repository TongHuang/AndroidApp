package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    private IncomePersist mIncomePersist;
    private AccountPersist mAccountPersist;

    private List<Account> mAccountList;
    private List<IncomeBudget> mIncomeBudgetList;
    private Income mIncome;
    private Button mButtonChooseIncomeBudget;
    private EditText mEditTextIncomeAmount;
    private Button mButtonChooseIncomeDate;
    private Button mButtonChooseIncomeAccount;
    private EditText mEditTextIncomeNote;

    public void chooseIncomeBudget(View view) {
        ChooseIncomeBudgetDialogFragment chooseIncomeBudgetDialogFragment = new ChooseIncomeBudgetDialogFragment();
        chooseIncomeBudgetDialogFragment.setIncomeBudgetList(this.mIncomeBudgetList);
        chooseIncomeBudgetDialogFragment.setIncomeBudgetChooseListener(new ChooseIncomeBudgetDialogFragment.IncomeBudgetChooseListener() {
            @Override
            public void choose(IncomeBudget incomeBudget) {
                mIncome.setIncomeBudget(incomeBudget);
                mIncome.setAccount(incomeBudget.getAccount());
                updateView();
            }
        });

        chooseIncomeBudgetDialogFragment.show(this.getSupportFragmentManager(), "Choose IncomeBudget");
    }

    public void chooseIncomeAccount(View view) {
        ChooseAccountDialogFragment chooseAccountDialogFragment = new ChooseAccountDialogFragment();
        chooseAccountDialogFragment.setAccountList(this.mAccountList);
        chooseAccountDialogFragment.setAccountChooseListener(new ChooseAccountDialogFragment.AccountChooseListener() {
            @Override
            public void choose(Account account) {
                mButtonChooseIncomeAccount.setText(account.getName());
                mIncome.setAccount(account);
                updateView();
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
                mIncome.setDate(c.getTimeInMillis());
                updateView();
            }
        });
        chooseDateDialogFragment.show(this.getSupportFragmentManager(), "Choose income date");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);

        //Set the layout size to fit phone screen
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayoutInsideScrollView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        linearLayout.setMinimumHeight(displayMetrics.heightPixels*8/10);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mWritableDatabase = databaseHelper.getWritableDatabase();
        mIncomePersist = new IncomePersist();
        mAccountPersist = new AccountPersist();
        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist();
        mAccountList = mAccountPersist.readAll(mWritableDatabase);
        mIncomeBudgetList = incomeBudgetPersist.readAllBudgetAmountNotZero(mWritableDatabase);

        mIncome = new Income();
        mIncome.setIncomeBudget(mIncomeBudgetList.get(0));
        mIncome.setDate(System.currentTimeMillis());
        mIncome.setAccount(mAccountList.get(0));

        mButtonChooseIncomeBudget = (Button) findViewById(R.id.buttonChooseIncomeBudget);
        mEditTextIncomeAmount = (EditText) findViewById(R.id.editTextIncomeAmount);
        mButtonChooseIncomeDate = (Button) findViewById(R.id.buttonChooseIncomeDate);
        mButtonChooseIncomeAccount = (Button) findViewById(R.id.buttonChooseIncomeAccount);
        mEditTextIncomeNote = (EditText) findViewById(R.id.editTextIncomeNote);
        this.updateView();
    }

    private void updateView(){
        mButtonChooseIncomeBudget.setText(mIncome.getIncomeBudget().getName());
        mEditTextIncomeAmount.setHint(getResources().getString(R.string.budget_amount) + " " + mIncome.getIncomeBudget().getBudgetAmount().toString());
        mEditTextIncomeAmount.requestFocus();
        mButtonChooseIncomeDate.setText(mIncome.getDateLabel());
        mButtonChooseIncomeAccount.setText(mIncome.getAccount().getName());

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
            mIncome.setAmount(new BigDecimal(mEditTextIncomeAmount.getText().toString()));
            mIncome.setNote(mEditTextIncomeNote.getText().toString());
            mIncomePersist.insert(mIncome, mWritableDatabase);
            Account account = mIncome.getAccount();
            account.setBalance(account.getBalance().add(mIncome.getAmount()));
            mAccountPersist.update(account, mWritableDatabase);

        }
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
