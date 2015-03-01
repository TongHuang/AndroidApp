package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.IncomeCategory;

/**
 * Created by Tong Huang on 2015-02-27, 10:09 AM.
 */
public class IncomeCategoryGroupSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private IncomeCategory.INCOME_CATEGORY_GROUP[] groups;

    public IncomeCategoryGroupSpinnerAdapter(Context context, IncomeCategory.INCOME_CATEGORY_GROUP[] groups){
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return this.groups.length;
    }

    @Override
    public Object getItem(int position) {
        return this.groups[position];
    }

    @Override
    public long getItemId(int position) {
        return this.groups[position].ordinal();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        IncomeCategory.INCOME_CATEGORY_GROUP group = this.groups[position];
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.spinner_item_income_group, null);
        }
        TextView textViewGroup = (TextView)view.findViewById(R.id.textViewIncomeGroup);
        textViewGroup.setText(group.getLabel(context));
        return view;
    }
}
