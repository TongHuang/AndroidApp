package com.quebecfresh.androidapp.calculator;

/**
 * Created by Tong Huang on 2015-02-09, 4:55 PM.
 */
public class Expression {
    private String expressionString = "";

    public void setExpressionString(String expressionString) {
        this.expressionString = expressionString;
    }

    public String getExpressionString() {
        return this.expressionString;
    }


    public Integer getLeftBracketsNumber() {
        //If the "(" is the last character, have to append a space to get correct result.
        return (this.expressionString + " ").split("\\(").length - 1;
    }

    public Integer getRightBracketsNumber() {
        //If the ")" is the last character, have to append a space to get correct result.
        return (this.expressionString + " ").split("\\)").length - 1;
    }

    public Boolean isBracketsCompleted() {
        return this.getLeftBracketsNumber() == this.getRightBracketsNumber();
    }

    public Boolean endWithEqual() {
        return this.expressionString.endsWith("=");
    }

    public Boolean endWithLeftBracket() {
        return this.expressionString.endsWith("(");
    }

    public Boolean endWithRightBracket() {
        return this.expressionString.endsWith(")");
    }

    public Boolean isEmpty() {
        if (this.expressionString == null || this.expressionString.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean endWithPlus() {
        return this.expressionString.endsWith("+");
    }

    public Boolean endWithMinus() {
        return this.expressionString.endsWith("-");
    }

    public Boolean endWithMultiply() {
        return this.expressionString.endsWith("*");
    }

    public Boolean endWithDivide() {
        return this.expressionString.endsWith("/");
    }

    public Boolean endWithOperator() {
        return this.expressionString.endsWith("+") ||
                this.expressionString.endsWith("-") ||
                this.expressionString.endsWith("*") ||
                this.expressionString.endsWith("/");
    }

    public Boolean endWithDot() {

        return this.expressionString.endsWith(".");
    }


    public Boolean endWithNumber() {
        return this.expressionString.endsWith("1") ||
                this.expressionString.endsWith("2") ||
                this.expressionString.endsWith("3") ||
                this.expressionString.endsWith("4") ||
                this.expressionString.endsWith("5") ||
                this.expressionString.endsWith("6") ||
                this.expressionString.endsWith("7") ||
                this.expressionString.endsWith("8") ||
                this.expressionString.endsWith("9") ||
                this.expressionString.endsWith("0");

    }


    public void appendChar(Character c) {
        this.expressionString = this.expressionString + c;
    }

    public void removeLastChar() {
        this.expressionString = this.expressionString.substring(0, this.expressionString.length() - 1);
    }

}