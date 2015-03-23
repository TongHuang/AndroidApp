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

        System.out.println(sdf.format(new Date(1426996800000L)));
        System.out.println(sdf.format(new Date(1427083199999L)));

        System.out.println("Original:" + sdf.format(calendar.getTime()));


    }


}
