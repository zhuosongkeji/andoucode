package com.zskjprojectj.andouclient.model;

import com.zskjprojectj.andouclient.activity.restaurant.RestaurantBillActivity;

import java.io.Serializable;
import java.util.List;

public class Restaurant implements Serializable, RestaurantBillActivity.IBill {

    public String id;
    public String banner_img;
    public String name;
    public String praise_num;
    public String address;
    public int stars_all;
    public List<Food> cai;
    public boolean isHeader;
    public String logo_img;
    public String desc;
    public String tel;
    public String business_start;
    public String business_end;
    public int status;

    public boolean isVip() {
        return false;
    }

    @Override
    public String getMerchantId() {
        return id;
    }

    @Override
    public List<Food> getFoods() {
        return null;
    }

    @Override
    public String getMerchantName() {
        return name;
    }

    @Override
    public String getMerchantLogo() {
        return logo_img;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getDinnertime() {
        return null;
    }

    @Override
    public String getPeople() {
        return null;
    }

    @Override
    public String getRemark() {
        return null;
    }

    @Override
    public String getOrderSN() {
        return null;
    }

    @Override
    public String getStartTime() {
        return business_start;
    }

    @Override
    public String getEndTime() {
        return business_end;
    }
}
