package com.zskjprojectj.andouclient.entity;

/**
 * 订单详情实体类
 */
public class BalancesubsidiaryBean {
    private String titlename;

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String time;
    private  String price;
}
