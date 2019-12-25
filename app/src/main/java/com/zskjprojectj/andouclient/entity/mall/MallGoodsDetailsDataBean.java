package com.zskjprojectj.andouclient.entity.mall;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/24 17:47
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallGoodsDetailsDataBean {


    /**
     * name : 安全帽
     * merchant_id : 1
     * weight : 5.00
     * img : /uploads/9dda642343f82536effc08d9b4fa23f2.jpg
     * album : ["/shop/shopImage/201912201729155674.jpg","/shop/shopImage/201912201729157091.jpg"]
     * price : 29.00
     * dilivery : 10.00
     * volume : 0
     * is_collection : 0
     * merchant : {"id":1,"name":"测试商家","logo_img":null}
     */

    private String name;
    private int merchant_id;
    private String weight;
    private String img;
    private String price;
    private String dilivery;
    private int volume;
    private int is_collection;
    private MerchantBean merchant;
    private List<String> album;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDilivery() {
        return dilivery;
    }

    public void setDilivery(String dilivery) {
        this.dilivery = dilivery;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(int is_collection) {
        this.is_collection = is_collection;
    }

    public MerchantBean getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantBean merchant) {
        this.merchant = merchant;
    }

    public List<String> getAlbum() {
        return album;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }

    public static class MerchantBean {
        /**
         * id : 1
         * name : 测试商家
         * logo_img : null
         */

        private int id;
        private String name;
        private Object logo_img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getLogo_img() {
            return logo_img;
        }

        public void setLogo_img(Object logo_img) {
            this.logo_img = logo_img;
        }
    }
}
