package com.quebecfresh.androidapp.simplebudget.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tong Huang on 2015-02-13, 12:24 PM.
 */
public class BaseData {

    protected static final String TYPE_ID =" INTEGER PRIMARY KEY ";
    protected static final String TYPE_NULL = " NULL ";
    protected static final String TYPE_INTEGER=" INTEGER ";
    protected  static final String DEFAULT_ONE = " DEFAULT 1"; //USE for Roll_Over true ;
    protected static final String TYPE_REAL = " REAL ";
    protected static final String TYPE_TEXT = " TEXT ";
    protected  static final String DEFAULT_ZERO= " DEFAULT '0'";//Use for balance, budget_amount etc..
    protected static final String TYPE_BLOB = " BLOB ";
    protected static final String COMMA = ",";

    private Long id = -1L;
    private String name = "";
    private String note = "";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy ");

    public BaseData(){

    }

    public BaseData(String name){
        this.name = name;
    }

    public BaseData(String name, String note){
        this.name = name;
        this.note = note;
    }

    public BaseData(Long id, String name, String note){
        this.id = id;
        this.name = name;
        this.note = note;
    }


    protected String formatDate(long date){
        return simpleDateFormat.format(new Date(date));
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
