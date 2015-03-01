package com.quebecfresh.androidapp.simplebudget.model;

import com.quebecfresh.androidapp.simplebudget.R;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Tong Huang on 2015-02-27, 2:37 PM.
 */
public class Utils {

    public static BigDecimal calcTotalAmount(List<IncomeCategory> incomes, Cycle cycle){
        BigDecimal amount = new BigDecimal("0");
        return amount;
    }

//    public static BigDecimal unifyAmount(BigDecimal amount, Cycle from, Cycle to){
//        switch (from) {
//            case Daily:
//                return  context.getString(R.string.Cycle_Daily);
//            case Weekly:
//                return context.getString(R.string.Cycle_Weekly);
//            case Every_2_Weeks:
//                return context.getString(R.string.Cycle_Every_2_Weeks);
//            case Every_3_Weeks:
//                return context.getString(R.string.Cycle_Every_3_Weeks);
//            case Every_4_Weeks:
//                return context.getString(R.string.Cycle_Every_4_Weeks);
//            case Monthly:
//                return context.getString(R.string.Cycle_Monthly);
//            case Every_2_Months:
//                return context.getString(R.string.Cycle_Every_2_Months);
//            case Every_3_Months:
//                return context.getString(R.string.Cycle_Every_3_Months);
//            case Every_4_Months:
//                return context.getString(R.string.Cycle_Every_4_Months);
//            case Every_5_Months:
//                return context.getString(R.string.Cycle_Every_5_Months);
//            case Every_6_Months:
//                return context.getString(R.string.Cycle_Every_6_Months);
//            case Every_7_Months:
//                return context.getString(R.string.Cycle_Every_7_Months);
//            case Every_8_Months:
//                return context.getString(R.string.Cycle_Every_8_Months);
//            case Every_9_Months:
//                return context.getString(R.string.Cycle_Every_9_Months);
//            case Every_10_Months:
//                return context.getString(R.string.Cycle_Every_10_Months);
//            case Every_11_Months:
//                return context.getString(R.string.Cycle_Every_11_Months);
//            default:
//                return context.getString(R.string.Cycle_Yearly);
//        }
//    }


}
