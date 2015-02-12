package com.quebecfresh.androidapp.calculator;

import java.util.ArrayList;
import com.fathzer.soft.javaluator.DoubleEvaluator;


/**
 * Created by Tong Huang on 2015-02-09, 4:11 PM.
 */
public class Calculator {
    private ArrayList<String> histories = new ArrayList<String>();
    private Expression expression = new Expression();
    private Status status = new Status();

    public void reset() {
        this.expression = new Expression();
        this.status.reset();
    }
    /*
    Before calculation, it is very important to save expression string, because calculator have to
    reset to initial status. We can not reset after calculation, cause it will erase displaying
    calculation result message.
     */
    public void calculate() {
        String message;
        String expressionString = this.expression.getExpressionString();
        this.reset();
        DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
        try {
            Double result = doubleEvaluator.evaluate(expressionString);
            message =expressionString + "\r\n=" + result.toString();
        } catch (Exception e) {
            message=expressionString + "\r\n Error";

        }
        this.status.setDisplay(message);
        this.histories.add(message);
    }

    public void appendChar(Character c) {
        this.expression.appendChar(c);
        this.status.update(this.expression);
    }

    public void removeLastChar() {
        this.expression.removeLastChar();
        this.status.update(this.expression);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<String> getHistories() {
        return histories;
    }

    public void setHistories(ArrayList<String> histories) {
        this.histories = histories;
    }
}