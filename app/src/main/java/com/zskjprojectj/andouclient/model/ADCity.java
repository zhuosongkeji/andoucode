package com.zskjprojectj.andouclient.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ADCity extends District {
    public JsonElement areas;

    public ArrayList<ADArea> getAreas() {
        if (areas.isJsonArray()) {
            return new Gson().fromJson(areas, new TypeToken<ArrayList<ADArea>>() {
            }.getType());
        } else {
            return new ArrayList<>();
        }
    }
}
