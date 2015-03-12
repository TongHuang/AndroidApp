package com.quebecfresh.androidapp.simplebudget;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class DemoActivity extends ActionBarActivity {

    private ProgressBar progressBar;
    private int progress = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Drawable customDrawable = getResources().getDrawable(R.drawable.custom_progressbar);

        // set the drawable as progress drawavle

//        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

//        progressBar.setProgressDrawable(customDrawable);
//        progressBar.setIndeterminate(false);
//        progressBar.setMax(100);
//        progressBar.setProgress(0);

    }

    public void start(View view) {
        LinearLayout up = (LinearLayout)findViewById(R.id.up);
        LinearLayout down = (LinearLayout)findViewById(R.id.down);


////        int color = up.getDrawingCacheBackgroundColor();
//       up.setBackgroundColor(getResources().getColor(R.color.light_red));
//        down.setBackgroundColor(getResources().getColor(R.color.light_blue));


    }

//        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
//        executor.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                progressBar.setProgress(progressBar.getProgress() + 1);
//
//            }
//        }, 0, 1, TimeUnit.SECONDS);
//        executor.shutdown();




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
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
