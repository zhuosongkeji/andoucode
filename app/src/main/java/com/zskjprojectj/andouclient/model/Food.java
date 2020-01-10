package com.zskjprojectj.andouclient.model;

import java.io.Serializable;

public class Food implements Serializable {
    public String id;
    public String image;
    public String name;
    public String remark;
    public String price;
    public boolean isHeader;
    public int headerPosition = -1;
    public FoodCategory category = new FoodCategory();
}
