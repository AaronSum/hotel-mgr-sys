package com.mk.hms.model;

/**
 * Created by Thinkpad on 2015/11/2.
 */
public class BasePageExample {
    private int limitStart = -1;

    private int limitEnd = -1;

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
