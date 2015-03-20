package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    private Calendar calendar = new GregorianCalendar();
    private Spinner spinnerCycle;
    private TextView textViewSelectedCycle;

    /**
     * Calculate the string for textViewSelectedCycle
     *
     * @param cycle
     * @param calendar
     * @param value    the value will be roll by calendar, it can be zero, zero means don't roll.
     * @return
     */
    private String calcCycleString(Cycle cycle, Calendar calendar, int value) {
        SimpleDateFormat simpleDateFormat;
        switch (cycle) {
            case Weekly:
                calendar.add(Calendar.WEEK_OF_YEAR, value);
                this.calcIncomes(Cycle.Weekly, calendar);
                simpleDateFormat = new SimpleDateFormat("yyyy-w");
                return simpleDateFormat.format(calendar.getTime());
            case Monthly:
                calendar.add(Calendar.MONTH, value);
                simpleDateFormat = new SimpleDateFormat("yyyy-MMM");
                return simpleDateFormat.format(calendar.getTime());
            case Yearly:
                calendar.add(Calendar.YEAR, value);
                simpleDateFormat = new SimpleDateFormat("yyyy");
                return simpleDateFormat.format(calendar.getTime());
        }
        return null;
    }

    private BigDecimal calcIncomes(Cycle cycle, Calendar calendar) {
        long start;
        long end;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        switch (cycle) {
            case Weekly:
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
                start = calendar.getTimeInMillis();
                System.out.println(sdf.format(new Date(start)));
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
                end = calendar.getTimeInMillis();
                System.out.println(sdf.format(new Date(end)));
                break;
            case Monthly:
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                start = calendar.getTimeInMillis();
                System.out.println(sdf.format(new Date(start)));
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                end = calendar.getTimeInMillis();
                System.out.println(sdf.format(new Date(end)));
                break;
            case Yearly:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
                start = calendar.getTimeInMillis();
                System.out.println(sdf.format(new Date(start)));
                calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
                end = calendar.getTimeInMillis();
                System.out.println(sdf.format(new Date(end)));
                break;
        }

        return null;
    }


    public void moveToPreviousCycle(View view) {
        Cycle cycle = (Cycle) spinnerCycle.getSelectedItem();
        textViewSelectedCycle.setText(this.calcCycleString(cycle, calendar, -1));
    }

    public void moveToNextCycle(View view) {
        Cycle cycle = (Cycle) spinnerCycle.getSelectedItem();
        textViewSelectedCycle.setText(this.calcCycleString(cycle, calendar, 1));
    }

    public void showIncomeList(View view) {
        Intent intent = new Intent(this, IncomeActivity.class);
        startActivity(intent);
    }

    public void showExpenseList(View view) {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public void editAccounts(View view) {
        Intent intent = new Intent(this, InitializeAccountActivity.class);
        startActivity(intent);
    }

    public void editIncomeBudget(View view) {
        Intent intent = new Intent(this, InitializeIncomeBudgetActivity.class);
        startActivity(intent);
    }

    public void editExpenseBudget(View view) {
        Intent intent = new Intent(this, InitializeExpenseBudgetActivity.class);
        startActivity(intent);
    }

    public void newExpense(View view) {
        Intent intent = new Intent(this, NewExpenseActivity.class);
        startActivity(intent);
    }

    public void newIncome(View view) {
        Intent intent = new Intent(this, NewIncomeActivity.class);
        startActivity(intent);
    }


    public void demo(View view) {
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean(getString(R.string.initialize_done), false);
//        editor.commit();

        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(this, Cycle.valuesShort());
        spinnerCycle = (Spinner) this.findViewById(R.id.spinnerCycle);
        spinnerCycle.setAdapter(cycleSpinnerAdapter);
        spinnerCycle.setSelection(1);
        spinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cycle cycle = (Cycle) parent.getSelectedItem();
                textViewSelectedCycle.setText(calcCycleString(cycle, calendar, 0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        textViewSelectedCycle = (TextView) findViewById(R.id.textViewCurrentCycle);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
        Boolean initializeDone = preferences.getBoolean(getString(R.string.initialize_done), false);
        if (initializeDone == false) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
            AccountPersist accountPersist = new AccountPersist(sqLiteDatabase);
            List<Account> accounts = accountPersist.readAll();
            BigDecimal accountTotal = new BigDecimal("0");
            for (int i = 0; i < accounts.size(); i++) {
                accountTotal = accountTotal.add(accounts.get(i).getBalance());
            }

            ExpenseBudgetPersist expenseCategoryPersist = new ExpenseBudgetPersist(sqLiteDatabase);
            List<ExpenseBudget> expenseCategoryList = expenseCategoryPersist.readAll();
            BigDecimal expenseBudgetTotal = new BigDecimal("0");
            for (int i = 0; i < expenseCategoryList.size(); i++) {
                expenseBudgetTotal = expenseBudgetTotal.add(expenseCategoryList.get(i).getBudgetAmount());
            }

            IncomeBudgetPersist incomeCategoryPersist = new IncomeBudgetPersist(sqLiteDatabase);
            List<IncomeBudget> incomeCategoryList = incomeCategoryPersist.readAll();
            BigDecimal incomeBudgetTotal = new BigDecimal("0");
            for (IncomeBudget incomeCategory : incomeCategoryList) {
                incomeBudgetTotal = incomeBudgetTotal.add(incomeCategory.getBudgetAmount());
            }


            BigDecimal max = accountTotal.max(expenseBudgetTotal).max(incomeBudgetTotal);

            ProgressBar progressBarBalance = (ProgressBar) this.findViewById(R.id.progressBarBalance);
            TextView textViewProgressBarBalanceCenter = (TextView) this.findViewById(R.id.textViewProgressBarBalanceCenter);
            progressBarBalance.setMax(max.intValue());
            progressBarBalance.setProgress(accountTotal.intValue());
            textViewProgressBarBalanceCenter.setText(accountTotal.toString());


            ProgressBar progressBarIncomeBudget = (ProgressBar) this.findViewById(R.id.progressBarIncomeBudget);
            TextView textViewProgressBarIncomeBudgetCenter = (TextView) this.findViewById(R.id.textViewProgressBarIncomeBudgetCenter);
            progressBarIncomeBudget.setMax(max.intValue());
            progressBarIncomeBudget.setProgress(incomeBudgetTotal.intValue());
            textViewProgressBarIncomeBudgetCenter.setText(incomeBudgetTotal.toString());

            ProgressBar progressBarExpenseBudget = (ProgressBar) this.findViewById(R.id.progressBarExpenseBudget);
            TextView textViewProgressBarExpenseBudgetCenter = (TextView) this.findViewById(R.id.textViewProgressBarExpenseBudgetCenter);
            progressBarExpenseBudget.setMax(max.intValue());
            progressBarExpenseBudget.setProgress(expenseBudgetTotal.intValue());
            textViewProgressBarExpenseBudgetCenter.setText(expenseBudgetTotal.toString());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent;
        switch (id) {
            case R.id.action_reInitialize:
                SharedPreferences preferences = this.getSharedPreferences(getString(R.string.initialize_done), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(getString(R.string.initialize_done), false);
                editor.putLong(getString(R.string.initialize_date), 0);
                editor.commit();
                intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                break;
            case R.id.action_demo:
                intent = new Intent(this, DemoActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
