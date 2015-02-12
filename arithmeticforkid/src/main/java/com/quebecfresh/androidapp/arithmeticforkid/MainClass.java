package com.quebecfresh.androidapp.arithmeticforkid;

import java.util.Random;

/**
 * Created by Tong Huang on 2015-02-11, 2:06 PM.
 */
public class MainClass {

    public static void main(String args[]){
        Random  a = new Random();
        System.out.println(a.nextInt(10));
        Random b = new Random(123);
        System.out.println(b.nextInt(10));

        System.out.println(8%2);
        System.out.println(8%3);
        System.out.println(3%8);
    }
}
