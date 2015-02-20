package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Category;
import com.quebecfresh.androidapp.simplebudget.model.ExpenseCategory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tong Huang on 2015-02-17, 8:24 AM.
 */
public class CategoryExpandableListViewAdapter extends BaseExpandableListAdapter {
   private HashMap<String,List<Category>> categories;
   private List<String> categoryGroup ;
   private  List<String> cycles;
   private Context context;

    public CategoryExpandableListViewAdapter(List<String> categoryGroup, HashMap<String, List<Category>> categories, List<String> cycles, Context context) {
        this.categoryGroup = categoryGroup;
        this.categories = categories;
        this.cycles = cycles;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return this.categoryGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String group = categoryGroup.get(groupPosition);
        return categories.get(group).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String group = categoryGroup.get(groupPosition);
        return categories.get(group).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group = this.categoryGroup.get(groupPosition);
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_group_category, null);
        }
        TextView textViewGroup = (TextView)view.findViewById(R.id.textView_Group);
        textViewGroup.setText(group);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String group = this.categoryGroup.get(groupPosition);
        Category category = this.categories.get(group).get(childPosition);
        View view = convertView;

        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_category, null);
        }

        EditText editTextCategory = (EditText)view.findViewById(R.id.editText_Category);
        editTextCategory.setText(category.getName());
        Spinner spinnerCycles = (Spinner)view.findViewById(R.id.spinner_cycle);
        ArrayAdapter  arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, cycles);
        spinnerCycles.setAdapter(arrayAdapter);
       spinnerCycles.setSelection(category.getCycle().ordinal());
        EditText editTextAmount = (EditText)view.findViewById(R.id.editText_Amount);
        editTextAmount.setText(category.getBudgetAmount().toString());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
