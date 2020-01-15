package com.zskjprojectj.andouclient.model;

import java.util.ArrayList;

public class RestaurantOrderDetail {
    public String order_sn;
    public String people;
    public String prices;
    public String dinnertime;
    public int method;
    public String remark;
    public String integral;
    public String pay_money;
    public String orderingtime;
    public String id;
    public String name;
    public String logo_img;
    public int status;
    public ArrayList<Food> foods = new ArrayList<>();
}
