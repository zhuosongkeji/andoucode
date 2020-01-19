package com.zskjprojectj.andouclient.entity.hotel;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/7 14:50
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelHomeDetailsBean {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * img : 房间图片
     * price : 房间价格
     * house_name : 房间名称
     * areas : 面积
     * has_window : 窗户
     * wifi : wifi
     * num : 可住人数
     * has_breakfast : 有无早餐
     * bed_type : 床型
     * other_sets : 配套设置
     */

    private int id;
    private List<String> img;
    private String price;
    private String house_name;
    private String areas;
    private String has_window;
    private String wifi;
    private String num_people;
    private String has_breakfast;
    private String bed_type;
    private String other_sets;

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getHas_window() {
        return has_window;
    }

    public void setHas_window(String has_window) {
        this.has_window = has_window;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getNum_people() {
        return num_people;
    }

    public void setNum_people(String num_people) {
        this.num_people = num_people;
    }

    public String getHas_breakfast() {
        return has_breakfast;
    }

    public void setHas_breakfast(String has_breakfast) {
        this.has_breakfast = has_breakfast;
    }

    public String getBed_type() {
        return bed_type;
    }

    public void setBed_type(String bed_type) {
        this.bed_type = bed_type;
    }

    public String getOther_sets() {
        return other_sets;
    }

    public void setOther_sets(String other_sets) {
        this.other_sets = other_sets;
    }
}
