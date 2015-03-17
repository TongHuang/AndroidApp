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
public class BudgetListViewAdapter extends BaseAdapter implements ListAdapter {

    private List<ExpenseBudget> budgetList;
    private Context context;

    public BudgetListViewAdapter(List<ExpenseBudget> budgetList, Context context) {
        this.budgetList = budgetList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.budgetList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.budgetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.budgetList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ExpenseBudget budget = budgetList.get(position);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_expense_budget, null);
        }

        TextView textViewBudgetName = (TextView) view.findViewById(R.id.textViewBudgetName);
        textViewBudgetName.setText(budget.getName());
        TextView textViewCycle = (TextView) view.findViewById(R.id.textViewCycle);
        textViewCycle.setText(budget.getCycle().getLabel(context));
        TextView textViewBudgetAmount = (TextView) view.findViewById(R.id.textViewAmount);
        textViewBudgetAmount.setText(budget.getBudgetAmount().toString());
        TextView textViewUnusedBalance = (TextView)view.findViewById(R.id.textViewUnusedBalance);
        textViewUnusedBalance.setText(budget.getUnusedBalance().toString());
        return view;
    }
}
