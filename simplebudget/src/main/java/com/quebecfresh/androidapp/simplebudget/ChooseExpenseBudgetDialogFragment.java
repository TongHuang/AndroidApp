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

import java.util.List;

/**
 * Created by Tong Huang on 2015-03-08, 9:42 PM.
 */
public class ChooseExpenseBudgetDialogFragment extends DialogFragment {

    private List<ExpenseBudget> mExpenseBudgetList;
    private ExpenseBudgetChooseListener mExpenseBudgetChooseListener;

    public interface ExpenseBudgetChooseListener {
        public void Choose(ExpenseBudget expenseBudget);
    }

    public List<ExpenseBudget> getExpenseBudgetList() {
        return mExpenseBudgetList;
    }

    public void setExpenseBudgetList(List<ExpenseBudget> expenseBudgetList) {
        mExpenseBudgetList = expenseBudgetList;
    }

    public ExpenseBudgetChooseListener getmExpenseBudgetChooseListener() {
        return mExpenseBudgetChooseListener;
    }

    public void setmExpenseBudgetChooseListener(ExpenseBudgetChooseListener mExpenseBudgetChooseListener) {
        this.mExpenseBudgetChooseListener = mExpenseBudgetChooseListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_budget, container, false);
        ListView listViewBudget = (ListView) view.findViewById(R.id.listViewBudget);
        ExpenseBudgetListViewAdapter adapter = new ExpenseBudgetListViewAdapter(mExpenseBudgetList, this.getActivity());
        listViewBudget.setAdapter(adapter);
        listViewBudget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mExpenseBudgetChooseListener != null && position >= 0 ) {
                    mExpenseBudgetChooseListener.Choose(mExpenseBudgetList.get(position));
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
