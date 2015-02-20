package com.quebecfresh.androidapp.simplebudget.model;

import android.provider.BaseColumns;

import org.w3c.dom.Text;

/**
 * Created by Tong Huang on 2015-02-20, 2:35 PM.
 */
public final class DatabaseContract {

    private DatabaseContract(){

    }

    private static final String ID_PRIMARY_KEY=" INTEGER PRIMARY KEY";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";


    public static final String CREATE_ACCOUNT="CREATE TABLE " + Account.TABLE_NAME + Account._ID + ID_PRIMARY_KEY
            + COMMA_SEP + Account.COLUMN_NAME + TEXT_TYPE + COMMA_SEP + Account.COLUMN_NUMBER + TEXT_TYPE
            + COMMA_SEP + Account.COLUMN_BALANCE + TEXT_TYPE + COMMA_SEP + Account.COLUMN_NOTE + TEXT_TYPE;
    public static final String DROP_ACCOUNT="DROP TABLE IF EXIST" + Account.TABLE_NAME;

    public static  abstract class Account implements BaseColumns{
        public static final String TABLE_NAME ="accounts";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_BALANCE="balance";
        public static final String COLUMN_NOTE="note";
    }


}
