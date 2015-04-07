package com.quebecfresh.androidapp.simplebudget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.quebecfresh.androidapp.simplebudget.model.Cycle;

/**
 * Created by Tong Huang on 2015-02-26, 10:53 AM.
 */
public class CycleSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
    private Cycle[] mCycles;
    public CycleSpinnerAdapter( Cycle[] cycles, Context context) {
        this.mContext = context;
        this.mCycles = cycles;
    }

    @Override
    public int getCount() {
        return this.mCycles.length;
    }

    @Override
    public Object getItem(int position) {
        return this.mCycles[position];
    }

    @Override
    public long getItemId(int position) {
        return this.mCycles[position].ordinal();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_item_cycle, null);
        }
        TextView textView = (TextView)view.findViewById(R.id.textViewCycle);
        textView.setText(mCycles[position].getLabel(mContext));
        return  view;
    }
}
