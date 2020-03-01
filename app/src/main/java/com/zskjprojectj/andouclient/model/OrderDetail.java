package com.zskjprojectj.andouclient.model;

import java.io.Serializable;
import java.util.List;

public class OrderDetail extends Order {

    public String order_sn;
    public String pay_time;
    public UserUnfo userinfo;
    public List<Goodsdetail> details;
    public String allnum;
    public String order_money;

    public class UserUnfo implements Serializable{
        public String name;
        public String address;
        public String mobile;
        public String province;
        public String city;
        public String area;
    }

    public class Goodsdetail implements Serializable {
        public String id;
        public String img;
        public String name;
        public String num;
        public String shipping_free;
        public String price;
        public String[] attr_value;
    }
}
