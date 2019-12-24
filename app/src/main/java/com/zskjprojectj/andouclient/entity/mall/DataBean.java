package com.zskjprojectj.andouclient.entity.mall;



import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/23 14:22
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class DataBean {
    private List<BannerBean> banner;
    private List<CategoryBean> category;
    private List<RecommendGoodsBean> recommend_goods;
    private List<BargainGoodsBean> bargain_goods;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public List<RecommendGoodsBean> getRecommend_goods() {
        return recommend_goods;
    }

    public void setRecommend_goods(List<RecommendGoodsBean> recommend_goods) {
        this.recommend_goods = recommend_goods;
    }

    public List<BargainGoodsBean> getBargain_goods() {
        return bargain_goods;
    }

    public void setBargain_goods(List<BargainGoodsBean> bargain_goods) {
        this.bargain_goods = bargain_goods;
    }

    public static class BannerBean extends SimpleBannerInfo {
        /**
         * id : 12
         * img : /uploads/7bad71d9e8327d8bf1ea49e09e8ac3dd.jpg
         * url : www.baidu.com
         */

        private int id;
        private String img;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String getXBannerUrl() {
            return "http://andou.zhuosongkj.com/index.php";
        }
    }

    public static class CategoryBean {
        /**
         * id : 1
         * img : /uploads/c6ff072673d5c35aba145fa71ce5d039.jpg
         * name : 生活类
         */

        private int id;
        private String img;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class RecommendGoodsBean {
        /**
         * id : 11
         * img : /uploads/c07ef6ea010a1e9b2a01b054c36cc9f1.jpg
         * name : 羽绒服
         * price : 15.00
         */

        private int id;
        private String img;
        private String name;
        private String price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class BargainGoodsBean {
        /**
         * id : 3
         * img : /uploads/76b14ac1a67893727eabe4ed9f622c82.jpg
         * name : 牛仔裤
         * price : 15.00
         */

        private int id;
        private String img;
        private String name;
        private String price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}