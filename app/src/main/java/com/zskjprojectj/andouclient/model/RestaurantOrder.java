package com.zskjprojectj.andouclient.model;

import com.zskjprojectj.andouclient.activity.restaurant.RestaurantBillActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestaurantOrder implements Serializable, RestaurantBillActivity.IBill {
    public String name;
    public String logo_img;
    public String prices;
    public String remark;
    public String merchant_id;
    public String id;
    public String order_sn;
    public String dinnertime;
    public String people;
    public int status;
    public ArrayList<Food> foods = new ArrayList<>();
    public int method;
    public String integral;
    public String pay_money;
    public String orderingtime;
    public String tel;
    public String address;

    @Override
    public String getMerchantId() {
        return null;
    }

    @Override
    public List<Food> getFoods() {
        return foods;
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
        return status;
    }

    @Override
    public String getDinnertime() {
        return dinnertime;
    }

    @Override
    public String getPeople() {
        return people;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public String getOrderSN() {
        return order_sn;
    }

    public enum STATE {
        ALL(0, "全部订单"),
        DAI_ZHI_FU(10, "待支付"),
        DAI_SHI_YONG(20, "待使用"),
        DAI_PING_JIA(30, "待评价"),
        YI_WAN_CHENG(40, "已完成");

        public int stateInt;
        public String stateStr;

        STATE(int stateInt, String stateStr) {
            this.stateInt = stateInt;
            this.stateStr = stateStr;
        }
    }

    public static STATE getState(int stateInt) {
        for (STATE value : STATE.values()) {
            if (value.stateInt == stateInt) {
                return value;
            }
        }
        return STATE.ALL;
    }
}
