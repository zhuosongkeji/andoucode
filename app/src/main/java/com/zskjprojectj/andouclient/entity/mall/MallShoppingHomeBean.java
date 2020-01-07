package com.zskjprojectj.andouclient.entity.mall;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/13 16:34
 * des:商家主页
 * * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallShoppingHomeBean {
    private String name;
    /**
     * banner_img : 背景海报图
     * logo_img : 头像图片
     * goods : [{"name":"商品名字","img":"商品图片","price":"价格","id":"商品id"}]
     * type : [{"name":"分类名字","id":"分类id"}]
     */

    private String banner_img;
    private String logo_img;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    private List<GoodsBean> goods;
    private List<TypeBean> type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }

    public String getLogo_img() {
        return logo_img;
    }

    public void setLogo_img(String logo_img) {
        this.logo_img = logo_img;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public static class GoodsBean {
        /**
         * name : 商品名字
         * img : 商品图片
         * price : 价格
         * id : 商品id
         */

        @SerializedName("name")
        private String nameX;
        private String img;
        private String price;
        private String id;

        public String getNameX() {
            return nameX;
        }

        public void setNameX(String nameX) {
            this.nameX = nameX;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class TypeBean {
        /**
         * name : 分类名字
         * id : 分类id
         */

        @SerializedName("name")
        private String nameX;
        private String id;

        public String getNameX() {
            return nameX;
        }

        public void setNameX(String nameX) {
            this.nameX = nameX;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
