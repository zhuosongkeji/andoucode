package com.zskjprojectj.andouclient.entity.hotel;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/12 9:38
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelHomeBean {


    private String id;
    private String address;
    private String tel;
    private String created_at;
    private String stars_all;
    private String merchant_type_id;
    private String price;
    private String praise_num;
    private String logo_img;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStars_all() {
        return stars_all;
    }

    public void setStars_all(String stars_all) {
        this.stars_all = stars_all;
    }

    public String getMerchant_type_id() {
        return merchant_type_id;
    }

    public void setMerchant_type_id(String merchant_type_id) {
        this.merchant_type_id = merchant_type_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
    }

    public String getLogo_img() {
        return logo_img;
    }

    public void setLogo_img(String logo_img) {
        this.logo_img = logo_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
