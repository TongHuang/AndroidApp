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

import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tong Huang on 2015-03-08, 9:42 PM.
 */
public class ChooseExpenseBudgetDialogFragment extends DialogFragment {

    private List<ExpenseBudget> expenseBudgetList = new ArrayList<ExpenseBudget>();
    private ExpenseBudgetChooseListener expenseBudgetChooseListener;

    public interface ExpenseBudgetChooseListener {
        public void Choose(ExpenseBudget expenseBudget);
    }

    public List<ExpenseBudget> getExpenseBudgetList() {
        return expenseBudgetList;
    }

    public void setExpenseBudgetList(List<ExpenseBudget> expenseBudgetList) {
        this.expenseBudgetList.clear();
        this.expenseBudgetList.addAll(expenseBudgetList);
    }

    public ExpenseBudgetChooseListener getExpenseBudgetChooseListener() {
        return expenseBudgetChooseListener;
    }

    public void setExpenseBudgetChooseListener(ExpenseBudgetChooseListener expenseBudgetChooseListener) {
        this.expenseBudgetChooseListener = expenseBudgetChooseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_budget, container, false);
        ListView listViewBudget = (ListView) view.findViewById(R.id.listViewBudget);
//        DatabaseHelper databaseHelper = new DatabaseHelper(this.getActivity());
//        ExpenseBudgetPersist expenseBudgetPersist = new ExpenseBudgetPersist(databaseHelper.getReadableDatabase());
//        expenseBudgetList.clear();
//        expenseBudgetList.addAll(expenseBudgetPersist.readAllUnusedBalanceNotZero());
        ExpenseBudgetListViewAdapter adapter = new ExpenseBudgetListViewAdapter(expenseBudgetList, this.getActivity());
        listViewBudget.setAdapter(adapter);
        listViewBudget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(expenseBudgetChooseListener != null && position >= 0 ) {
                    expenseBudgetChooseListener.Choose(expenseBudgetList.get(position));
                }
                ChooseExpenseBudgetDialogFragment.this.dismiss();
            }
        });

//        View headerView = inflater.inflate(R.layout.list_header_budget, null);
//        listViewBudget.addHeaderView(headerView);
        return view;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.setTitle("Please choose a pocket");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }
}
