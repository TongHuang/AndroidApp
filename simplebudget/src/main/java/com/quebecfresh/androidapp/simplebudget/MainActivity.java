package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Account;
import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;
import com.quebecfresh.androidapp.simplebudget.model.Utils;
import com.quebecfresh.androidapp.simplebudget.persist.AccountPersist;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.ExpensePersist;
import com.quebecfresh.androidapp.simplebudget.persist.IncomeBudgetPersist;
import com.quebecfresh.androidapp.simplebudget.persist.IncomePersist;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity {


    public static final String EXTRA_SELECTED_CYCLE = "com.quebecfresh.androidapp.123budget.mainactivity.cycle";
    public static final String EXTRA_SELECTED_DATE = "com.quebecfresh.androidapp.123budget.mainactivity.date";

    private Calendar selectedDate = Calendar.getInstance();
    private Cycle selectedCycle = Cycle.Monthly;
    private Spinner spinnerCycle;
    private Button buttonChooseDate;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private SQLiteDatabase readableDB;
    private BigDecimal balanceTotal;
    private BigDecimal incomeTotal;
    private BigDecimal incomeBudgetTotal;
    private BigDecimal expenseTotal;
    private BigDecimal expenseBudgetTotal;

    private void calcTotalIncomesAndExpenses() {
        long begin = Utils.getBeginOfCycle(this.selectedCycle, this.selectedDate);
        long end = Utils.getEndOfCycle(this.selectedCycle, this.selectedDate);
        IncomePersist incomePersist = new IncomePersist(this);
        incomeTotal =incomePersist.readTotal(begin, end);

        ExpensePersist expensePersist = new ExpensePersist(this);
        expenseTotal = expensePersist.readTotalAmount(begin, end);

    }

    private void updateOverView() {
        SimpleDateFormat simpleDateFormat;
        switch (selectedCycle) {
            case Weekly:
                simpleDateFormat = new SimpleDateFormat("yyyy-w");
                buttonChooseDate.setText(simpleDateFormat.format(selectedDate.getTime()));
                break;
            case Monthly:
                simpleDateFormat = new SimpleDateFormat("yyyy-MMM");
                buttonChooseDate.setText(simpleDateFormat.format(selectedDate.getTime()));
                break;
            case Yearly:
                simpleDateFormat = new SimpleDateFormat("yyyy");
                buttonChooseDate.setText(simpleDateFormat.format(selectedDate.getTime()));
                break;
        }




        this.calcTotalIncomesAndExpenses();

        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist(this);
        List<IncomeBudget> incomeBudgetList = incomeBudgetPersist.readAll();
        incomeBudgetTotal = new BigDecimal("0");
        for (IncomeBudget incomeBudget : incomeBudgetList) {
            incomeBudgetTotal = incomeBudgetTotal.add(incomeBudget.convertBudgetAmountTo(selectedCycle));
        }

        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist(this);
        List<ExpenseBudget> expenseBudgetList = expenseBudgetPersist.readAll();
        expenseBudgetTotal = new BigDecimal("0");
        for (ExpenseBudget expenseBudget : expenseBudgetList) {
            expenseBudgetTotal = expenseBudgetTotal.add(expenseBudget.convertBudgetAmountTo(selectedCycle));
        }


        AccountPersist accountPersist = new AccountPersist(this);

        balanceTotal = accountPersist.readTotalBalance().add(expenseBudgetPersist.readTotalUnusedBalance());

        BigDecimal max = balanceTotal.max(expenseBudgetTotal).max(incomeBudgetTotal).max(incomeTotal).max(expenseTotal);

        ProgressBar progressBarBalance = (ProgressBar) this.findViewById(R.id.progressBarBalance);
        TextView textViewProgressBarBalanceCenter = (TextView) this.findViewById(R.id.textViewProgressBarBalanceCenter);
        progressBarBalance.setMax(max.intValue());
        progressBarBalance.setProgress(balanceTotal.intValue());
        textViewProgressBarBalanceCenter.setText(balanceTotal.toString());

        ProgressBar progressBarIncome = (ProgressBar) this.findViewById(R.id.progressBarIncome);
        TextView textViewProgressBarIncomeCenter = (TextView) this.findViewById(R.id.textViewProgressBarIncomeCenter);
        progressBarIncome.setMax(max.intValue());
        progressBarIncome.setProgress(incomeTotal.intValue());
        textViewProgressBarIncomeCenter.setText(incomeTotal.toString());

        ProgressBar progressBarIncomeBudget = (ProgressBar) this.findViewById(R.id.progressBarIncomeBudget);
        TextView textViewProgressBarIncomeBudgetCenter = (TextView) this.findViewById(R.id.textViewProgressBarIncomeBudgetCenter);
        progressBarIncomeBudget.setMax(max.intValue());
        progressBarIncomeBudget.setProgress(incomeBudgetTotal.intValue());
        textViewProgressBarIncomeBudgetCenter.setText(incomeBudgetTotal.toString());


        ProgressBar progressBarExpense = (ProgressBar) findViewById(R.id.progressBarExpense);
        TextView textViewProgressBarExpenseCenter = (TextView) findViewById(R.id.textViewProgressBarExpenseCenter);
        progressBarExpense.setMax(max.intValue());
        progressBarExpense.setProgress(expenseTotal.intValue());
        textViewProgressBarExpenseCenter.setText(expenseTotal.toString());


        ProgressBar progressBarExpenseBudget = (ProgressBar) this.findViewById(R.id.progressBarExpenseBudget);
        TextView textViewProgressBarExpenseBudgetCenter = (TextView) this.findViewById(R.id.textViewProgressBarExpenseBudgetCenter);
        progressBarExpenseBudget.setMax(max.intValue());
        progressBarExpenseBudget.setProgress(expenseBudgetTotal.intValue());
        textViewProgressBarExpenseBudgetCenter.setText(expenseBudgetTotal.toString());
    }


    public void chooseOverViewDate(View view) {
        ChooseDateDialogFragment chooseDateDialogFragment = new ChooseDateDialogFragment();
        chooseDateDialogFragment.setCalendar(this.selectedDate);
        chooseDateDialogFragment.setDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, monthOfYear);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateOverView();
            }
        });
        chooseDateDialogFragment.show(this.getSupportFragmentManager(), "Choose date");
    }


    public void showIncomeList(View view) {
        Intent intent = new Intent(this, IncomeActivity.class);
        intent.putExtra(EXTRA_SELECTED_CYCLE, this.selectedCycle.toString());
        intent.putExtra(EXTRA_SELECTED_DATE, this.selectedDate.getTimeInMillis());
        startActivity(intent);
    }



    public void showExpenseList(View view) {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public void showBalance(View view) {
        Intent intent = new Intent(this, BalanceActivity.class);
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

        this.readableDB = dbHelper.getReadableDatabase();
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean(getString(R.string.initialize_done), false);
//        editor.commit();

        buttonChooseDate = (Button)findViewById(R.id.buttonChooseDate);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(this, Cycle.valuesShort());
        spinnerCycle = (Spinner) this.findViewById(R.id.spinnerCycle);
        spinnerCycle.setAdapter(cycleSpinnerAdapter);
        spinnerCycle.setSelection(1);
        spinnerCycle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCycle = (Cycle) parent.getSelectedItem();
                updateOverView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            this.updateOverView();
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
                SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
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
