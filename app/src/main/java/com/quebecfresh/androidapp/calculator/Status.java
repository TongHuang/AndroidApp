package com.quebecfresh.androidapp.calculator;

/**
 * Created by Tong Huang on 2015-02-09, 4:56 PM.
 */
public class Status {
    private Boolean numberBtnEnabled = true;
    private Boolean plusMinusBtnEnabled = false;
    private Boolean multiplyDivideBtnEnabled = false;
    private Boolean equalBtnEnabled = false;
    private Boolean dotBtnEnabled = false;
    private Boolean backspaceBtnEnabled = false;
    private Boolean clearBtnEnabled = false;
    private Boolean leftBracketsBtnEnabled = true;
    private Boolean rightBracketsBtnEnabled = false;
    private String display = "";

    /*
    reset status to initial status,
     */
    public void reset() {
        numberBtnEnabled = true;
        plusMinusBtnEnabled = true;
        multiplyDivideBtnEnabled = false;
        equalBtnEnabled = false;
        dotBtnEnabled = false;
        backspaceBtnEnabled = false;
        clearBtnEnabled = false;
        leftBracketsBtnEnabled = true;
        rightBracketsBtnEnabled = false;
        display = "";
    }

    /*

     */



    /*
    update Calculator's status, according to the info dig out of expression.
     */
    public void update(Expression expression) {

        if (expression.isEmpty()) {
            this.reset();
            return;
        }
        this.display = expression.getExpressionString();
        backspaceBtnEnabled = true;
        clearBtnEnabled = true;

        if (expression.endWithNumber()) {
            numberBtnEnabled = true;
            plusMinusBtnEnabled = true;
            multiplyDivideBtnEnabled = true;
            leftBracketsBtnEnabled = false;
            if (expression.isBracketsCompleted()) {
                equalBtnEnabled = true;
                rightBracketsBtnEnabled = false;
            } else {
                equalBtnEnabled = false;
                rightBracketsBtnEnabled = true;
            }
            dotBtnEnabled = true;

            return;
        }
        if (expression.endWithOperator()) {
            numberBtnEnabled = true;
            plusMinusBtnEnabled = true;
            multiplyDivideBtnEnabled = false;
            equalBtnEnabled = false;
            dotBtnEnabled = false;
            leftBracketsBtnEnabled = true;
            rightBracketsBtnEnabled = false;
            return;
        }
        if (expression.endWithDot()) {
            numberBtnEnabled = true;
            plusMinusBtnEnabled = false;
            multiplyDivideBtnEnabled = false;
            equalBtnEnabled = false;
            dotBtnEnabled = false;
            leftBracketsBtnEnabled = false;
            rightBracketsBtnEnabled = false;
        }
        if (expression.endWithLeftBracket()) {
            numberBtnEnabled = true;
            plusMinusBtnEnabled = true;
            multiplyDivideBtnEnabled = false;
            equalBtnEnabled = false;
            dotBtnEnabled = false;
            leftBracketsBtnEnabled = true;
            rightBracketsBtnEnabled = false;

        }
        if (expression.endWithRightBracket()) {
            numberBtnEnabled = false;
            plusMinusBtnEnabled = true;
            multiplyDivideBtnEnabled = true;
            if (expression.isBracketsCompleted()) {
                equalBtnEnabled = true;
                rightBracketsBtnEnabled = false;
            } else {
                equalBtnEnabled = false;
                rightBracketsBtnEnabled = true;
            }
            dotBtnEnabled = false;
            leftBracketsBtnEnabled = false;
        }


    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }


    public Boolean getNumberBtnEnabled() {
        return numberBtnEnabled;
    }

    public void setNumberBtnEnabled(Boolean numberBtnEnabled) {
        this.numberBtnEnabled = numberBtnEnabled;
    }

    public Boolean getPlusMinusBtnEnabled() {
        return plusMinusBtnEnabled;
    }

    public void setPlusMinusBtnEnabled(Boolean plusMinusBtnEnabled) {
        this.plusMinusBtnEnabled = plusMinusBtnEnabled;
    }

    public Boolean getMultiplyDivideBtnEnabled() {
        return multiplyDivideBtnEnabled;
    }

    public void setMultiplyDivideBtnEnabled(Boolean multiplyDivideBtnEnabled) {
        this.multiplyDivideBtnEnabled = multiplyDivideBtnEnabled;
    }

    public Boolean getEqualBtnEnabled() {
        return equalBtnEnabled;
    }

    public void setEqualBtnEnabled(Boolean equalBtnEnabled) {
        this.equalBtnEnabled = equalBtnEnabled;
    }

    public Boolean getDotBtnEnabled() {
        return dotBtnEnabled;
    }

    public void setDotBtnEnabled(Boolean dotBtnEnabled) {
        this.dotBtnEnabled = dotBtnEnabled;
    }

    public Boolean getBackspaceBtnEnabled() {
        return backspaceBtnEnabled;
    }

    public void setBackspaceBtnEnabled(Boolean backspaceBtnEnabled) {
        this.backspaceBtnEnabled = backspaceBtnEnabled;
    }

    public Boolean getClearBtnEnabled() {
        return clearBtnEnabled;
    }

    public void setClearBtnEnabled(Boolean clearBtnEnabled) {
        this.clearBtnEnabled = clearBtnEnabled;
    }

    public Boolean getLeftBracketsBtnEnabled() {
        return leftBracketsBtnEnabled;
    }

    public void setLeftBracketsBtnEnabled(Boolean leftBracketsBtnEnabled) {
        this.leftBracketsBtnEnabled = leftBracketsBtnEnabled;
    }

    public Boolean getRightBracketsBtnEnabled() {
        return rightBracketsBtnEnabled;
    }

    public void setRightBracketsBtnEnabled(Boolean rightBracketsBtnEnabled) {
        this.rightBracketsBtnEnabled = rightBracketsBtnEnabled;
    }
}
