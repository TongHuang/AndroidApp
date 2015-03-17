package com.quebecfresh.androidapp.simplebudget;

import java.util.Calendar;

/**
 * Created by Tong Huang on 2015-02-18, 3:37 PM.
 */
public class MainClass {

    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.set(1995,2,28);
        calendar.roll(Calendar.MONTH, 1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));

    }


}
