package com.zskjprojectj.andouclient.entity;

/**
 * 购物车实体类
 */
public class PlatformshoppingcartBean {
    private  String shopname;
    private  int goodspic;
    private  String goodsintroduction;
    private  String goodsprice;
    public int getGoodspic() {
        return goodspic;
    }

    public void setGoodspic(int goodspic) {
        this.goodspic = goodspic;
    }


    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }



    public String getGoodsintroduction() {
        return goodsintroduction;
    }

    public void setGoodsintroduction(String goodsintroduction) {
        this.goodsintroduction = goodsintroduction;
    }

    public String getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(String goodsprice) {
        this.goodsprice = goodsprice;
    }


}
