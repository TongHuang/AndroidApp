package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.ExpenseBudget;

/**
 * Created by Tong Huang on 2015-02-27, 10:33 AM.
 */
public class ExpenseCategoryGroupSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context context;
    private ExpenseBudget.EXPENSE_BUDGET_CATEGORY[] categories;

    public ExpenseCategoryGroupSpinnerAdapter(Context context, ExpenseBudget.EXPENSE_BUDGET_CATEGORY[] categories){
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return this.categories.length;
    }

    @Override
    public Object getItem(int position) {
        return this.categories[position];
    }

    @Override
    public long getItemId(int position) {
        return this.categories[position].ordinal();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = convertView;
        ExpenseBudget.EXPENSE_BUDGET_CATEGORY group = categories[position];
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.spinner_item_budget_category, null);
        }
        TextView textViewGroup = (TextView)view.findViewById(R.id.textViewExpenseCategoryGroup);
        textViewGroup.setText(group.getLabel(context));
        return view;
    }
}
