package com.quebecfresh.androidapp.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.quebecfresh.androidapp.calculator.R;

/**
 * Created by Tong Huang on 2015-02-09, 5:05 PM.
 */
public class CalculatorActivity extends ActionBarActivity {

    public static final String EXTRA_HISTORIES = "com.quebecfresh.androidapp.calculator.histories";

    private static Calculator calculator = new Calculator();


    public void btnOneClick(View view) {
        this.calculator.appendChar('1');
        this.updateViewStatus();
    }

    public void btnTwoClick(View view) {
        this.calculator.appendChar('2');
        this.updateViewStatus();
    }

    public void btnThreeClick(View view) {
        this.calculator.appendChar('3');
        this.updateViewStatus();
    }

    public void btnFourClick(View view) {
        this.calculator.appendChar('4');
        this.updateViewStatus();
    }

    public void btnFiveClick(View view) {
        this.calculator.appendChar('5');
        this.updateViewStatus();
    }

    public void btnSixClick(View view) {
        this.calculator.appendChar('6');
        this.updateViewStatus();
    }

    public void btnSevenClick(View view) {
        this.calculator.appendChar('7');
        this.updateViewStatus();
    }

    public void btnEightClick(View view) {
        this.calculator.appendChar('8');
        this.updateViewStatus();
    }

    public void btnNineClick(View view) {
        this.calculator.appendChar('9');
        this.updateViewStatus();
    }

    public void btnZeroClick(View view) {
        this.calculator.appendChar('0');
        this.updateViewStatus();
    }

    public void btnPlusClick(View view) {
        this.calculator.appendChar('+');
        this.updateViewStatus();
    }

    public void btnMinusClick(View view) {
        this.calculator.appendChar('-');
        this.updateViewStatus();
    }

    public void btnMultiplyClick(View view) {
        this.calculator.appendChar('*');
        this.updateViewStatus();
    }

    public void btnDivideClick(View view) {
        this.calculator.appendChar('/');
        this.updateViewStatus();
    }

    public void btnLeftBracketClick(View view) {
        this.calculator.appendChar('(');
        this.updateViewStatus();
    }

    public void btnRightBracketClick(View view) {
        this.calculator.appendChar(')');
        this.updateViewStatus();
    }

    public void btnDotClick(View view) {
        this.calculator.appendChar('.');
        this.updateViewStatus();
    }

    public void btnBackSpaceClick(View view) {
        this.calculator.removeLastChar();
        this.updateViewStatus();
    }

    public void btnClearClick(View view) {
        this.calculator.reset();
        this.updateViewStatus();
    }

    public void btnEqualClick(View view) {
        this.calculator.calculate();
        this.updateViewStatus();
    }


    private void updateViewStatus() {

        //update Display
        TextView txt_view_result = (TextView) this.findViewById(R.id.txt_view_result);
        txt_view_result.setText(calculator.getStatus().getDisplay());

        //update number button
        View view = this.findViewById(R.id.btn_one);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());
        view = this.findViewById(R.id.btn_two);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());
        view = this.findViewById(R.id.btn_three);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());
        view = this.findViewById(R.id.btn_four);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());
        view = this.findViewById(R.id.btn_five);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());
        view = this.findViewById(R.id.btn_six);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());
        view = this.findViewById(R.id.btn_seven);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());
        view = this.findViewById(R.id.btn_eight);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());
        view = this.findViewById(R.id.btn_nine);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());
        view = this.findViewById(R.id.btn_zero);
        view.setEnabled(this.calculator.getStatus().getNumberBtnEnabled());

        //update dot button
        view = this.findViewById(R.id.btn_dot);
        view.setEnabled(this.calculator.getStatus().getDotBtnEnabled());

        //update operator button
        view = this.findViewById(R.id.btn_plus);
        view.setEnabled(this.calculator.getStatus().getPlusMinusBtnEnabled());
        view = this.findViewById(R.id.btn_minus);
        view.setEnabled(this.calculator.getStatus().getPlusMinusBtnEnabled());
        view = this.findViewById(R.id.btn_multi);
        view.setEnabled(this.calculator.getStatus().getMultiplyDivideBtnEnabled());
        view = this.findViewById(R.id.btn_divide);
        view.setEnabled(this.calculator.getStatus().getMultiplyDivideBtnEnabled());

        //update equal button
        view = this.findViewById(R.id.btn_equal);
        view.setEnabled(this.calculator.getStatus().getEqualBtnEnabled());

        //update other button
        view = this.findViewById(R.id.btn_leftBrackets);
        view.setEnabled(this.calculator.getStatus().getLeftBracketsBtnEnabled());
        view = this.findViewById(R.id.btn_rightBrackets);
        view.setEnabled(this.calculator.getStatus().getRightBracketsBtnEnabled());
        view = this.findViewById(R.id.btn_backSpace);
        view.setEnabled(this.calculator.getStatus().getBackspaceBtnEnabled());
        view = this.findViewById(R.id.btn_clear);
        view.setEnabled(this.calculator.getStatus().getClearBtnEnabled());


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent;

        if (id == R.id.action_histories) {
            intent = new Intent(this, HistoriesActivity.class);
            intent.putStringArrayListExtra(this.EXTRA_HISTORIES, calculator.getHistories());
            this.startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }


}
