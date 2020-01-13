package com.zskjprojectj.andouclient.model;

import java.io.Serializable;
import java.util.List;

public class Restaurant implements Serializable {

    public String id;
    public String door_img;
    public String name;
    public String praise_num;
    public String address;
    public int stars_all;
    public List<Food> cai;
    public boolean isHeader;
    public String logo_img;

    public boolean isVip() {
        return false;
    }
}
