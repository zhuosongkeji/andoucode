package com.zskjprojectj.andouclient.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Merchant implements Serializable {
    public String logo_img;
    public String name;
    public int praise_num;
    public String address;
    public String id;
    public String banner_img;
    public String tel;
    public String merchant_type_id;
    public String price;
    public String stars_all;

    public ArrayList<Goods> goods = new ArrayList<>();
}
