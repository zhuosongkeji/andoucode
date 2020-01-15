package com.zskjprojectj.andouclient.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Food implements Serializable {
    public String id;
    public String image;
    public String name;
    public String remark;
    public String price;
    public int num;
    public boolean isHeader;
    public int headerPosition = -1;
    public FoodCategory category = new FoodCategory();

    public double getAmount() {
        try {
            return new BigDecimal(price).multiply(new BigDecimal(num)).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
