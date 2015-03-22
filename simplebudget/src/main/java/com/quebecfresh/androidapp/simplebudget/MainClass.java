package com.quebecfresh.androidapp.simplebudget;

import com.quebecfresh.androidapp.simplebudget.model.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tong Huang on 2015-02-18, 3:37 PM.
 */
public class MainClass {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSSZ");

        System.out.println("Original:" + sdf.format(calendar.getTime()));

        calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
        System.out.println("Begin of Month 2:" + sdf.format(new Date(Utils.getStartOfMonth(calendar))));
        System.out.println("End of Month 2" + sdf.format(new Date(Utils.getEndOfMonth(calendar))));


        calendar.set(Calendar.MONTH, Calendar.MARCH);
        System.out.println("Begin of Month 3:" + sdf.format(new Date(Utils.getStartOfMonth(calendar))));
        System.out.println("End of Month 3" + sdf.format(new Date(Utils.getEndOfMonth(calendar))));

        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        System.out.println("Begin of Month 11:" + sdf.format(new Date(Utils.getStartOfMonth(calendar))));
        System.out.println("End of Month 11:" + sdf.format(new Date(Utils.getEndOfMonth(calendar))));


        System.out.println("*****************************************");
        calendar.set(Calendar.YEAR, 1995);
        System.out.println("Begin of Year 1995:" + sdf.format(new Date(Utils.getStartOfYear(calendar))));
        System.out.println("End of Year 1995:" + sdf.format(new Date(Utils.getEndOfYear(calendar))));
        calendar.set(Calendar.YEAR, 2005);
        System.out.println("Begin of Year 2005:" + sdf.format(new Date(Utils.getStartOfYear(calendar))));
        System.out.println("End of Year 2005:" + sdf.format(new Date(Utils.getEndOfYear(calendar))));
        calendar.set(Calendar.YEAR, 2015);
        System.out.println("Begin of Year 2015:" + sdf.format(new Date(Utils.getStartOfYear(calendar))));
        System.out.println("End of Year 2015:" + sdf.format(new Date(Utils.getEndOfYear(calendar))));

    }


}
