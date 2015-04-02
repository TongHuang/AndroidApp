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

    public void showBudgetOverView(View view){
        Intent intent = new Intent(this, BudgetOverviewActivity.class);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
