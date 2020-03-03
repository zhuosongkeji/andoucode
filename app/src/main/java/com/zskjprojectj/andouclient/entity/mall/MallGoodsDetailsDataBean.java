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

    @Override
    public String toString() {
        return "MallGoodsDetailsDataBean{" +
                "name='" + name + '\'' +
                ", merchant_id=" + merchant_id +
                ", weight='" + weight + '\'' +
                ", img='" + img + '\'' +
                ", price='" + price + '\'' +
                ", dilivery='" + dilivery + '\'' +
                ", store_num='" + store_num + '\'' +
                ", volume=" + volume +
                ", is_collection=" + is_collection +
                ", merchant=" + merchant +
                ", album=" + album +
                '}';
    }

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
    private String merchant_id;
    private String weight;
    private String img;
    private String price;
    private String dilivery;
    private String store_num;
    private String volume;
    private String tel;
    private String is_collection;
    private MerchantBean merchant;
    private List<String> album;
    public boolean isMiaoSha;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStore_num() {
        return store_num;
    }

    public void setStore_num(String store_num) {
        this.store_num = store_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
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

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
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
        private String logo_img;

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

        public String getLogo_img() {
            return logo_img;
        }

        public void setLogo_img(String logo_img) {
            this.logo_img = logo_img;
        }
    }
}
