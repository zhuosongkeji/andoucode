package com.zskjprojectj.andouclient.entity.hotel;

import java.io.Serializable;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/9 15:08
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MeHotelBean implements Serializable {

    /**
     * book_sn : 20200109OVc7Af104950
     * logo_img : /uploads/1c62d62f0950edeeb87667bc74f83ab8.jpg
     * merchants_name : 希尔顿酒店
     * status : 10
     * img : /uploads/20200107/mVSW2X7mJt8ufUXhdanrRipvBOADBdudFvfNfyHZ.jpeg
     * house_name : 单间
     * price : 99.00
     */

    private String book_sn;
    private String logo_img;
    private String merchants_name;
    private String status;
    private String img;
    private String house_name;
    private String price;

    public String getBook_sn() {
        return book_sn;
    }

    public void setBook_sn(String book_sn) {
        this.book_sn = book_sn;
    }

    public String getLogo_img() {
        return logo_img;
    }

    public void setLogo_img(String logo_img) {
        this.logo_img = logo_img;
    }

    public String getMerchants_name() {
        return merchants_name;
    }

    public void setMerchants_name(String merchants_name) {
        this.merchants_name = merchants_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
