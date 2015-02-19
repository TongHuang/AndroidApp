package com.quebecfresh.androidapp.simplebudget;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by Tong Huang on 2015-02-18, 3:37 PM.
 */
public class MainClass {

    public static void main(String[] args){

        BigDecimal b = new BigDecimal("123455.2155");

        System.out.println(b.toString());


        System.out.println(b.setScale(2, BigDecimal.ROUND_HALF_UP));
    }


}
