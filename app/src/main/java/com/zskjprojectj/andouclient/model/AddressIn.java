package com.zskjprojectj.andouclient.model;

import androidx.annotation.NonNull;

import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;


public class AddressIn {
    public Province province;
    public City city;
    public County county;

    public AddressIn(Province province, City city, County county) {
        this.province = province;
        this.city = city;
        this.county = county;
    }

    @NonNull
    @Override
    public String toString() {
        return !province.name.equals(city.name) ? province.name : "" + city.name + county.name;
    }
}
