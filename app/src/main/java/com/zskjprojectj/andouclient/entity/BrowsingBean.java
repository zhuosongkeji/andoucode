package com.zskjprojectj.andouclient.entity;

public class BrowsingBean {


    /**
     * id : 2
     * address : 测试商家地址
     * tel : 18323899463
     * stars_all : 4
     * praise_num : 1
     * name : 桃园村酒店
     * logo_img : /uploads/fc8601f9af3e2f49fcc617ea097944fb.jpg
     */

    private int id;
    private String address;
    private String tel;
    private String stars_all;
    private String praise_num;
    private String name;
    private String logo_img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getStars_all() {
        return stars_all;
    }

    public void setStars_all(String stars_all) {
        this.stars_all = stars_all;
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

    public String getLogo_img() {
        return logo_img;
    }

    public void setLogo_img(String logo_img) {
        this.logo_img = logo_img;
    }
}
