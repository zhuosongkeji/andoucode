package com.zskjprojectj.andouclient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodCategory implements Serializable {
    public String id;
    public String name;
    public int index;
    @SerializedName("information")
    public ArrayList<Food> foods = new ArrayList<>();
}
