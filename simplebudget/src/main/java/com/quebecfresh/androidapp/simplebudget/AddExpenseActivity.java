package com.quebecfresh.androidapp.simplebudget;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddExpenseActivity extends ActionBarActivity {

    private  Button  buttonDate;

    public void chooseDate(View view){
        ChooseDateDialogFragment datePickerFragment  = new ChooseDateDialogFragment();
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        Calendar c = Calendar.getInstance();
        buttonDate = (Button)findViewById(R.id.buttonChooseDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");

        buttonDate.setText(simpleDateFormat.format(c.getTime()));
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
