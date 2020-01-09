package com.zskjprojectj.andouclient.model;

import java.io.Serializable;

public class Food implements Serializable {
    public String id;
    public String image;
    public boolean isHeader;
    public FoodCategory category = new FoodCategory();
}
