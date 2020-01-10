package com.zskjprojectj.andouclient.model;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodCategory implements Serializable {
    public String id;
    public String name;
    public int index;
    public ArrayList<Food> foods = new ArrayList<>();
}
