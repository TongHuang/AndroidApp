package com.quebecfresh.androidapp.arithmeticforkid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Created by Tong Huang on 2015-02-10, 10:06 AM.
 */
public class PracticeActivity extends ActionBarActivity {

    Practice practice = new Practice();

    private Integer total = 0;
    private Integer correct =0;
    private Integer inCorrect = 0;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getInCorrect() {
        return inCorrect;
    }

    public void setInCorrect(Integer inCorrect) {
        this.inCorrect = inCorrect;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_practice);
        Intent intent = super.getIntent();
        int type = intent.getIntExtra(PracticeOptionsActivity.EXTRA_TYPE, Practice.PRACTICE_TYPE.ADDITION.ordinal());

        switch (type) {
            case 0:
                this.practice = new Practice();
                this.practice.setType(Practice.PRACTICE_TYPE.ADDITION);
                break;
            case 1:
                this.practice = new Practice();
                this.practice.setType(Practice.PRACTICE_TYPE.SUBTRACTION);
                break;
            case 2:
                this.practice = new Practice();
                this.practice.setType(Practice.PRACTICE_TYPE.MULTIPLICATION);
                break;
            case 3:
                this.practice = new Practice();
                this.practice.setType(Practice.PRACTICE_TYPE.DIVISION);
                break;
        }

        this.initializeTest();
    }

    private void initializeTest() {
        practice.generateTest();
        TextView textView = (TextView) this.findViewById(R.id.txtViewExpression);
        textView.setText(practice.getExpression());
        RadioButton radioButtonOne = (RadioButton) this.findViewById(R.id.radioButtonOne);
        radioButtonOne.setChecked(false);
        radioButtonOne.setText(String.valueOf(practice.getChoiceOne()));

        RadioButton radioButtonTwo = (RadioButton) this.findViewById(R.id.radioButtonTwo);
        radioButtonTwo.setChecked(false);
        radioButtonTwo.setText(String.valueOf(practice.getChoiceTwo()));

        RadioButton radioButtonThree = (RadioButton) this.findViewById(R.id.radioButtonThree);
        radioButtonThree.setChecked(false);
        radioButtonThree.setText(String.valueOf(practice.getChoiceThree()));

        RadioButton radioButtonFour = (RadioButton) this.findViewById(R.id.radioButtonFour);
        radioButtonFour.setChecked(false);
        radioButtonFour.setText(String.valueOf(practice.getChoiceFour()));

    }


    public void radioButtonOneClick(View view) {
        total++;
        RadioButton button = (RadioButton) view;
        int choice = Integer.valueOf(button.getText().toString());
        if (practice.getAnswer() == choice) {
            button.setBackgroundColor(getResources().getColor(R.color.practice_right));
            correct++;
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.practice_wrong));
            this.markRightChoice();
            inCorrect++;
        }
        this.markScore();
    }

    public void radioButtonTwoClick(View view) {
        total++;
        RadioButton button = (RadioButton) view;
        int choice = Integer.valueOf(button.getText().toString());
        if (practice.getAnswer() == choice) {
            button.setBackgroundColor(getResources().getColor(R.color.practice_right));
            correct++;
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.practice_wrong));
            this.markRightChoice();
            inCorrect++;
        }
        this.markScore();
    }

    public void radioButtonThreeClick(View view) {
        total++;
        RadioButton button = (RadioButton) view;
        int choice = Integer.valueOf(button.getText().toString());
        if (practice.getAnswer() == choice) {
            button.setBackgroundColor(getResources().getColor(R.color.practice_right));
            correct++;
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.practice_wrong));
            this.markRightChoice();
            inCorrect++;
        }
        this.markScore();
    }

    public void radioButtonFourClick(View view) {
        total++;
        RadioButton button = (RadioButton) view;
        int choice = Integer.valueOf(button.getText().toString());
        if (practice.getAnswer() == choice) {
            button.setBackgroundColor(getResources().getColor(R.color.practice_right));
            correct++;
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.practice_wrong));
            this.markRightChoice();
            inCorrect++;
        }
        this.markScore();
    }


    public void radioButtonScope1Click(View view){
        this.practice.setScope(Practice.PRACTICE_SCOPE.ZERO_TEN);
        this.initializeTest();
   }
    public void radioButtonScope2Click(View view){
        this.practice.setScope(Practice.PRACTICE_SCOPE.ZERO_TWENTY);
        this.initializeTest();
    }
    public void radioButtonScope3Click(View view){
        this.practice.setScope(Practice.PRACTICE_SCOPE.ZERO_FIFTY);
        this.initializeTest();
    }
    public void radioButtonScope4Click(View view){
        this.practice.setScope(Practice.PRACTICE_SCOPE.ZERO_HUNDRED);
        this.initializeTest();
    }


    public void btnNextClick(View view) {
        RadioButton button = (RadioButton) this.findViewById(R.id.radioButtonOne);
        button.setBackgroundColor(getResources().getColor(R.color.practice_normal));
        button = (RadioButton) this.findViewById(R.id.radioButtonTwo);
        button.setBackgroundColor(getResources().getColor(R.color.practice_normal));
        button = (RadioButton) this.findViewById(R.id.radioButtonThree);
        button.setBackgroundColor(getResources().getColor(R.color.practice_normal));
        button = (RadioButton) this.findViewById(R.id.radioButtonFour);
        button.setBackgroundColor(getResources().getColor(R.color.practice_normal));
        this.initializeTest();

    }

    private  void markScore(){
        TextView textViewTotal = (TextView)this.findViewById(R.id.textViewTotal);
        textViewTotal.setText(this.total.toString());
        TextView textViewCorrect = (TextView)this.findViewById(R.id.textViewCorrect);
        textViewCorrect.setText(this.correct.toString());
        TextView textViewIncorrect = (TextView)this.findViewById(R.id.textViewIncorrect);
        textViewIncorrect.setText(this.inCorrect.toString());
    }

    private void markRightChoice() {
        RadioButton button = (RadioButton) this.findViewById(R.id.radioButtonOne);
        int choice = Integer.valueOf(button.getText().toString());
        if (practice.getAnswer() == choice) {
            button.setBackgroundColor(getResources().getColor(R.color.practice_right));
            return;
        }
        button = (RadioButton) this.findViewById(R.id.radioButtonTwo);
        choice = Integer.valueOf(button.getText().toString());
        if (practice.getAnswer() == choice) {
            button.setBackgroundColor(getResources().getColor(R.color.practice_right));
            return;
        }
        button = (RadioButton) this.findViewById(R.id.radioButtonThree);
        choice = Integer.valueOf(button.getText().toString());
        if (practice.getAnswer() == choice) {
            button.setBackgroundColor(getResources().getColor(R.color.practice_right));
            return;
        }
        button = (RadioButton) this.findViewById(R.id.radioButtonFour);
        choice = Integer.valueOf(button.getText().toString());
        if (practice.getAnswer() == choice) {
            button.setBackgroundColor(getResources().getColor(R.color.practice_right));
            return;
        }

    }
}
