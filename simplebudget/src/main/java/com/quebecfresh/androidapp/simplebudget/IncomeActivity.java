package com.quebecfresh.androidapp.simplebudget;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;
import com.quebecfresh.androidapp.simplebudget.model.Income;
import com.quebecfresh.androidapp.simplebudget.model.Utils;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.IncomePersist;

import java.util.Calendar;
import java.util.List;


public class IncomeActivity extends ActionBarActivity {

    private ListView listViewIncomes;
    private List<Income> incomeList;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private IncomePersist incomePersist;


    private Calendar selectedDate = Calendar.getInstance();
    private Cycle selectedCycle = Cycle.Monthly;


    private  void updateView(){
        long begin = Utils.getBeginOfCycle(this.selectedCycle, this.selectedDate);
        long end = Utils.getEndOfCycle(this.selectedCycle, this.selectedDate);
        incomeList = incomePersist.readAll(begin, end);
        IncomeListViewAdapter incomeListViewAdapter = new IncomeListViewAdapter(incomeList, this);
        TextView header  = new TextView(this);
        header.setText("Balance");
        TextView footer  = new TextView(this);
        footer.setText("1356.26");
        listViewIncomes.addHeaderView(header);
        listViewIncomes.addFooterView(footer);
        listViewIncomes.setAdapter(incomeListViewAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        this.selectedCycle = Cycle.valueOf(getIntent().getStringExtra(MainActivity.EXTRA_SELECTED_CYCLE));
        this.selectedDate.setTimeInMillis(getIntent().getLongExtra(MainActivity.EXTRA_SELECTED_DATE, System.currentTimeMillis()));

        incomePersist = new IncomePersist(databaseHelper.getReadableDatabase());
        listViewIncomes = (ListView)findViewById(R.id.listViewIncome);
        this.updateView();
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
