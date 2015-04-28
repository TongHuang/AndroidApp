package com.quebecfresh.androidapp.simplebudget.model;

import java.text.SimpleDateFormat;

/**
 * Created by Tong Huang on 2015-04-27, 10:13 PM.
 */
public class BudgetInfo extends  BaseData {
    private Long beginDate = 0L;
    private  Long endDate = 0L;

    public Long getBeginDate() {
        return beginDate;
    }

    public String getBeginDateShortLabel(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        return simpleDateFormat.format(this.beginDate);

    }

    public String getBeginDateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ");
        return simpleDateFormat.format(this.beginDate);
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getEndDateShortLabel(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        return simpleDateFormat.format(this.endDate);

    }

    public String getEndDateLabel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ");
        return simpleDateFormat.format(this.endDate);
    }
}
