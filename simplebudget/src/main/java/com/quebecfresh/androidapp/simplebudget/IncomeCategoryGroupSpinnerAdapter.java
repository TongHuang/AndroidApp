package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.IncomeBudget;

/**
 * Created by Tong Huang on 2015-02-27, 10:09 AM.
 */
public class IncomeCategoryGroupSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context mContext;
    private IncomeBudget.INCOME_BUDGET_CATEGORY[] mCategories;

    public IncomeCategoryGroupSpinnerAdapter(Context context, IncomeBudget.INCOME_BUDGET_CATEGORY[] categories){
        this.mContext = context;
        this.mCategories = categories;
    }

    @Override
    public int getCount() {
        return this.mCategories.length;
    }

    @Override
    public Object getItem(int position) {
        return this.mCategories[position];
    }

    @Override
    public long getItemId(int position) {
        return this.mCategories[position].ordinal();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        IncomeBudget.INCOME_BUDGET_CATEGORY group = this.mCategories[position];
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.spinner_item_budget_category, null);
        }
        TextView textViewGroup = (TextView)view.findViewById(R.id.textViewExpenseCategoryGroup);
        textViewGroup.setText(group.getLabel(mContext));
        return view;
    }
}
