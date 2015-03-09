package com.quebecfresh.androidapp.simplebudget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quebecfresh.androidapp.simplebudget.model.Budget;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;
import com.quebecfresh.androidapp.simplebudget.persist.DatabaseHelper;
import com.quebecfresh.androidapp.simplebudget.persist.ExpenseBudgetPersist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tong Huang on 2015-03-08, 9:42 PM.
 */
public class ChooseExpenseBudgetDialogFragment extends DialogFragment {

    private List<Budget> budgetList = new ArrayList<Budget>();
    private BudgetClickListener budgetClickListener;

    public interface BudgetClickListener {
        public void click(Budget budget);
    }

    public List<Budget> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<Budget> budgetList) {
        this.budgetList.clear();
        this.budgetList.addAll(budgetList);
    }

    public BudgetClickListener getBudgetClickListener() {
        return budgetClickListener;
    }

    public void setBudgetClickListener(BudgetClickListener budgetClickListener) {
        this.budgetClickListener = budgetClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_budget, container, false);
        ListView listViewBudget = (ListView) view.findViewById(R.id.listViewBudget);
//        DatabaseHelper databaseHelper = new DatabaseHelper(this.getActivity());
//        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist(databaseHelper.getReadableDatabase());
//        budgetList.clear();
//        budgetList.addAll(expenseBudgetPersist.readAllUnusedBalanceNotZero());
        BudgetListViewAdapter adapter = new BudgetListViewAdapter(budgetList, this.getActivity());
        listViewBudget.setAdapter(adapter);
        listViewBudget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                budgetClickListener.click( budgetList.get(position));
                ChooseExpenseBudgetDialogFragment.this.dismiss();
            }
        });
        return view;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
