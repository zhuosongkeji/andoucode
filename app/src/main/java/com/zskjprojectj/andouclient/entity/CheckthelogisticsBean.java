package com.zskjprojectj.andouclient.entity;

public class CheckthelogisticsBean {
    private String acceptTime;
    private String acceptStation;

    public CheckthelogisticsBean() {
    }

    public CheckthelogisticsBean(String acceptTime, String acceptStation) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }
}
