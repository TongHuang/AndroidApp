package com.quebecfresh.androidapp.simplebudget;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.model.Income;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomePersist;

import java.util.List;


public class IncomeActivity extends ActionBarActivity {

    private ListView listViewIncomes;
    private List<Income> incomeList;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private IncomePersist incomePersist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        incomePersist = new IncomePersist(databaseHelper.getReadableDatabase());
        incomeList = incomePersist.readAll();
        listViewIncomes = (ListView)findViewById(R.id.listViewIncome);
        IncomeListViewAdapter incomeListViewAdapter = new IncomeListViewAdapter(incomeList, this);
        listViewIncomes.setAdapter(incomeListViewAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_income, menu);
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
