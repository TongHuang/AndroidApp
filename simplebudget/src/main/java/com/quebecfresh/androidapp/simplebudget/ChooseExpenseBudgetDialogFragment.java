package com.quebecfresh.androidapp.simplebudget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;

import java.util.List;

/**
 * Created by Tong Huang on 2015-03-08, 9:42 PM.
 */
public class ChooseExpenseBudgetDialogFragment extends DialogFragment {

    private List<ExpenseBudget> expenseBudgetList;
    private ExpenseBudgetClickListener expenseBudgetClickListener;
    public interface ExpenseBudgetClickListener {
        public void click(ExpenseBudget expenseBudge);
    }

    public List<ExpenseBudget> getExpenseBudgetList() {
        return expenseBudgetList;
    }

    public void setExpenseBudgetList(List<ExpenseBudget> expenseBudgetList) {
        this.expenseBudgetList = expenseBudgetList;
    }

    public ExpenseBudgetClickListener getExpenseBudgetClickListener() {
        return expenseBudgetClickListener;
    }

    public void setExpenseBudgetClickListener(ExpenseBudgetClickListener expenseBudgetClickListener) {
        this.expenseBudgetClickListener = expenseBudgetClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
