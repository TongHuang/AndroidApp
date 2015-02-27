package com.quebecfresh.androidapp.simplebudget.model;

import android.app.Application;
import android.content.Context;

import com.quebecfresh.androidapp.simplebudget.R;

import java.util.ResourceBundle;

/**
 * Created by Tong Huang on 2015-02-17, 7:43 AM.
 */
public enum Cycle {
    Daily,
    Weekly,
    Every_2_Weeks,
    Every_3_Weeks,
    Every_4_Weeks,
    Monthly,
    Every_2_Months,
    Every_3_Months,
    Every_4_Months,
    Every_5_Months,
    Every_6_Months,
    Every_7_Months,
    Every_8_Months,
    Every_9_Months,
    Every_10_Months,
    Every_11_Months,
    Yearly;



    public static  String[] labels(Context context){
        Cycle[] cycles = Cycle.values();
        String[] labels  = new String[cycles.length];

        for(int i = 0; i < cycles.length; i++){
            labels[i] = cycles[i].getLabel(context);
        }
        return labels;
    }

    public String getLabel(Context context) {
        String label = "";
        switch (this) {
            case Daily:
                return  context.getString(R.string.Cycle_Daily);
            case Weekly:
                return context.getString(R.string.Cycle_Weekly);
            case Every_2_Weeks:
                return context.getString(R.string.Cycle_Every_2_Weeks);
            case Every_3_Weeks:
                return context.getString(R.string.Cycle_Every_3_Weeks);
            case Every_4_Weeks:
                return context.getString(R.string.Cycle_Every_4_Weeks);
            case Monthly:
                return context.getString(R.string.Cycle_Monthly);
            case Every_2_Months:
                return context.getString(R.string.Cycle_Every_2_Months);
            case Every_3_Months:
                return context.getString(R.string.Cycle_Every_3_Months);
            case Every_4_Months:
                return context.getString(R.string.Cycle_Every_4_Months);
            case Every_5_Months:
                return context.getString(R.string.Cycle_Every_5_Months);
            case Every_6_Months:
                return context.getString(R.string.Cycle_Every_6_Months);
            case Every_7_Months:
                return context.getString(R.string.Cycle_Every_7_Months);
            case Every_8_Months:
                return context.getString(R.string.Cycle_Every_8_Months);
            case Every_9_Months:
                return context.getString(R.string.Cycle_Every_9_Months);
            case Every_10_Months:
                return context.getString(R.string.Cycle_Every_10_Months);
            case Every_11_Months:
                return context.getString(R.string.Cycle_Every_11_Months);
            default:
                return context.getString(R.string.Cycle_Yearly);
        }

    }

}
