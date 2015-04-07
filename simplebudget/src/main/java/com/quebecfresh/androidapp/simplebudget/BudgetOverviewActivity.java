package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.content.Intent;
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


public class BudgetOverviewActivity extends ActionBarActivity {


    public static final String EXTRA_SELECTED_CYCLE = "com.quebecfresh.androidapp.123budget.budgetoverviewactivity.cycle";
    public static final String EXTRA_SELECTED_DATE = "com.quebecfresh.androidapp.123budget.budgetoverviewactivity.date";

    private SQLiteDatabase mReadableDatabase;


    private Calendar selectedDate = Calendar.getInstance();
    private Cycle selectedCycle = Cycle.Monthly;
    private Button mButtonChooseDate;

    //Initialize UI or update UI when selection change;
    private void updateOverView() {

        SimpleDateFormat simpleDateFormat;
        switch (selectedCycle) {
            case Weekly:
                simpleDateFormat = new SimpleDateFormat("yyyy-w");
                mButtonChooseDate.setText(simpleDateFormat.format(selectedDate.getTime()));
                break;
            case Monthly:
                simpleDateFormat = new SimpleDateFormat("yyyy-MMM");
                mButtonChooseDate.setText(simpleDateFormat.format(selectedDate.getTime()));
                break;
            case Yearly:
                simpleDateFormat = new SimpleDateFormat("yyyy");
                mButtonChooseDate.setText(simpleDateFormat.format(selectedDate.getTime()));
                break;
        }

        long begin = Utils.getBeginOfCycle(this.selectedCycle, this.selectedDate);
        long end = Utils.getEndOfCycle(this.selectedCycle, this.selectedDate);


        AccountPersist accountPersist = new AccountPersist();
        BigDecimal balanceTotal = accountPersist.readTotalBalance(mReadableDatabase);

        IncomePersist incomePersist = new IncomePersist();
        BigDecimal incomeTotal = incomePersist.readTotal(begin, end, mReadableDatabase);

        ExpensePersist expensePersist = new ExpensePersist();
        BigDecimal expenseTotal = expensePersist.readTotalAmount(begin, end, mReadableDatabase);

        IncomeBudgetPersist incomeBudgetPersist = new IncomeBudgetPersist();
        List<IncomeBudget> incomeBudgetList = incomeBudgetPersist.readAll(mReadableDatabase);
        BigDecimal incomeBudgetTotal = new BigDecimal("0");
        for (IncomeBudget incomeBudget : incomeBudgetList) {
            incomeBudgetTotal = incomeBudgetTotal.add(incomeBudget.convertBudgetAmountTo(selectedCycle));
        }

        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist();
        List<ExpenseBudget> expenseBudgetList = expenseBudgetPersist.readAll(mReadableDatabase);
        BigDecimal expenseBudgetTotal = new BigDecimal("0");
        for (ExpenseBudget expenseBudget : expenseBudgetList) {
            expenseBudgetTotal = expenseBudgetTotal.add(expenseBudget.convertBudgetAmountTo(selectedCycle));
        }

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_overview);

        DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
        mReadableDatabase = mDatabaseHelper.getReadableDatabase();

        mButtonChooseDate = (Button) findViewById(R.id.buttonChooseDate);
        CycleSpinnerAdapter cycleSpinnerAdapter = new CycleSpinnerAdapter(Cycle.valuesShort(),this);
        Spinner spinnerCycle = (Spinner) this.findViewById(R.id.spinnerCycle);
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
        intent.putExtra(EXTRA_SELECTED_CYCLE, this.selectedCycle.toString());
        intent.putExtra(EXTRA_SELECTED_DATE, this.selectedDate.getTimeInMillis());
        startActivity(intent);
    }

    public void showBalance(View view) {
        Intent intent = new Intent(this, BalanceActivity.class);
        startActivity(intent);
    }

    public void showIncomeBudget(View view) {
        Intent intent = new Intent(this, IncomeBudgetActivity.class);
        intent.putExtra(EXTRA_SELECTED_CYCLE, this.selectedCycle.toString());
        intent.putExtra(EXTRA_SELECTED_DATE, this.selectedDate.getTimeInMillis());
        startActivity(intent);
    }

    public void showExpenseBudget(View view) {
        Intent intent = new Intent(this, ExpenseBudgetActivity.class);
        intent.putExtra(EXTRA_SELECTED_CYCLE, this.selectedCycle.toString());
        intent.putExtra(EXTRA_SELECTED_DATE, this.selectedDate.getTimeInMillis());
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        this.updateOverView();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget_overview, menu);
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
