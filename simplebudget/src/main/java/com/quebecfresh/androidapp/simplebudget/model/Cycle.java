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
    Monthly,
    Every_2_Months,
    Every_3_Months,
    Every_6_Months,
    Yearly;


    private  static Cycle[] shortValues = {Cycle.Weekly,Cycle.Monthly, Cycle.Yearly};

    public static Cycle[] valuesShort(){
        return shortValues;
    }

    public Double toDailyRatio() {
        Double ratio = 1D;
        switch (this) {
            case Daily:
                ratio = 1D;
                break;
            case Weekly:
                ratio = 1D/7D;
                break;
            case Every_2_Weeks:
                ratio = 1D/14D;
                break;
            case Monthly:
                ratio = 1D/364D*12D;
                break;
            case Every_2_Months:
                ratio = 1D/364*6D;
                break;
            case Every_3_Months:
                ratio = 1D/364D * 3D;
                break;
            case Every_6_Months:
                ratio = 1D/364D * 2D;
                break;
            case Yearly:
                ratio = 1D/364D;
                break;

        }
        return ratio;
    }

    public Double ratio(Cycle cycle) {

        Double ratio = 0D;
        switch (cycle) {
            case Daily:
                ratio = this.toDailyRatio();
                break;
            case Weekly:
                ratio = this.toDailyRatio()*7D;
                break;
            case Every_2_Weeks:
                ratio = this.toDailyRatio()*14D;
                break;
            case Monthly:
                ratio = this.toDailyRatio()* 364D / 12D;
                break;
            case Every_2_Months:
                ratio =this.toDailyRatio()*364D / 6D;
                break;
            case Every_3_Months:
                ratio = this.toDailyRatio()*364D / 3D;
                break;
            case Every_6_Months:
                ratio = this.toDailyRatio()* 364D / 2D;
                break;
            case Yearly:
                ratio = this.toDailyRatio()* 364D;
                break;
        }
        return ratio;
    }


    public static String[] labels(Context context) {
        Cycle[] cycles = Cycle.values();
        String[] labels = new String[cycles.length];

        for (int i = 0; i < cycles.length; i++) {
            labels[i] = cycles[i].getLabel(context);
        }
        return labels;
    }

    public String getLabel(Context context) {
        switch (this) {
            case Daily:
                return context.getString(R.string.Cycle_Daily);
            case Weekly:
                return context.getString(R.string.Cycle_Weekly);
            case Every_2_Weeks:
                return context.getString(R.string.Cycle_Every_2_Weeks);
            case Monthly:
                return context.getString(R.string.Cycle_Monthly);
            case Every_2_Months:
                return context.getString(R.string.Cycle_Every_2_Months);
            case Every_3_Months:
                return context.getString(R.string.Cycle_Every_3_Months);
            case Every_6_Months:
                return context.getString(R.string.Cycle_Every_6_Months);
            default:
                return context.getString(R.string.Cycle_Yearly);
        }
    }





}
