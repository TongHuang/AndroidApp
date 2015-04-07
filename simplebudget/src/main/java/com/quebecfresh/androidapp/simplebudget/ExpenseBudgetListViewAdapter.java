package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;

import java.util.List;

/**
 * Created by Tong Huang on 2015-03-09, 10:47 AM.
 */
public class ExpenseBudgetListViewAdapter extends BaseAdapter implements ListAdapter {

    private List<ExpenseBudget> mBudgetList;
    private Context mContext;

    public ExpenseBudgetListViewAdapter(List<ExpenseBudget> budgetList, Context context) {
        this.mBudgetList = budgetList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.mBudgetList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mBudgetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.mBudgetList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ExpenseBudget budget = mBudgetList.get(position);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_expense_budget, null);
        }

        TextView textViewBudgetName = (TextView) view.findViewById(R.id.textViewBudgetName);
        textViewBudgetName.setText(budget.getName());
        TextView textViewCycle = (TextView) view.findViewById(R.id.textViewCycle);
        textViewCycle.setText(budget.getCycle().getLabel(mContext));
        TextView textViewBudgetAmount = (TextView) view.findViewById(R.id.textViewAmount);
        textViewBudgetAmount.setText(budget.getBudgetAmount().toString());
        return view;
    }
}
