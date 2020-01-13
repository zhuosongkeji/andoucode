package com.zskjprojectj.andouclient.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantOrder implements Serializable {
    public String name;
    public String logo_img;
    public String prices;
    public String merchant_id;
    public String id;
    public String order_sn;
    public int status;
    public ArrayList<Food> foods = new ArrayList<>();

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
