package com.zskjprojectj.andouclient.entity;

public class MycollectionBean {
    /**
     * id : 商品id
     * created_at : 创建时间
     * price : 商品价格
     * logo_img : 商品图片
     * name : 商品名字
     */

    private String id;
    private String created_at;
    private String price;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
