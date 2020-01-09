package com.zskjprojectj.andouclient.entity.hotel;

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
     * door_img : 商家门头图
     * stars_all : 商家星级
     * address : 详细地址
     * praise_num : 点赞数量
     * name : 商家名称
     * tel : 商家电话
     * id : 商户id
     * desc : 商家简介
     * facilities : 商家环境设施
     */

    private String door_img;
    private String stars_all;
    private String address;
    private String praise_num;
    private String name;
    private String tel;
    private String id;
    private String desc;
    private String facilities;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
}
