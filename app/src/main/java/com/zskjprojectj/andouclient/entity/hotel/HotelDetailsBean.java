package com.zskjprojectj.andouclient.entity.hotel;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/6 16:03
 * des: 商家详情
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailsBean {

    /**
     * id : 4
     * name : 希尔顿酒店
     * tel : 18323899463
     * door_img :
     * stars_all : 4
     * address : 重庆
     * praise_num : 0
     * desc : 这是重庆市希尔顿酒店的介绍
     * facilities : ["/shop/shopImage/201912201729155674.jpg","/shop/shopImage/201912201729157091.jpg"]
     */

    private String id;
    private String name;
    private String tel;
    private String door_img;
    private String stars_all;
    private String address;
    private String praise_num;
    private String desc;
    private List<String> facilities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDoor_img() {
        return door_img;
    }

    public void setDoor_img(String door_img) {
        this.door_img = door_img;
    }

    public String getStars_all() {
        return stars_all;
    }

    public void setStars_all(String stars_all) {
        this.stars_all = stars_all;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }
}
