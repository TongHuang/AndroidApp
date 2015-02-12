package com.quebecfresh.androidapp.arithmeticforkid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;



/**
 * Created by Tong Huang on 2015-02-10, 10:04 AM.
 */
public class PracticeOptionsActivity extends ActionBarActivity {

    public static final String EXTRA_TYPE = "com.quebecfresh.androidapp.arithmeticforkid.type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_practiceoptions);
    }

    public void practiceAddition(View view){
        Intent intent = new Intent(this, PracticeActivity.class);
        intent.putExtra(this.EXTRA_TYPE, Practice.PRACTICE_TYPE.ADDITION.ordinal());
        this.startActivity(intent);
    }

    public void practiceSubtraction(View view){
        Intent intent = new Intent(this, PracticeActivity.class);
        intent.putExtra(this.EXTRA_TYPE, Practice.PRACTICE_TYPE.SUBTRACTION.ordinal());
        this.startActivity(intent);
    }

    public void practiceMultiplication(View view){
        Intent intent = new Intent(this, PracticeActivity.class);
        intent.putExtra(this.EXTRA_TYPE, Practice.PRACTICE_TYPE.MULTIPLICATION.ordinal());
        this.startActivity(intent);
    }

    public void practiceDivision(View view){
        Intent intent = new Intent(this, PracticeActivity.class);
        intent.putExtra(this.EXTRA_TYPE, Practice.PRACTICE_TYPE.DIVISION.ordinal());
        this.startActivity(intent);
    }
}