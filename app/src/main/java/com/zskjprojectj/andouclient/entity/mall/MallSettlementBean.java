package com.zskjprojectj.andouclient.entity.mall;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/28 16:31
 * des: 购买结算页 实体类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallSettlementBean {


    /**
     * order_money : 109
     * id : 13
     * shipping_free : 10
     * order_sn : 20191228X31pYT164827
     * address_id : 9
     * integral : 9
     * userinfo : {"name":"测试收件人2","address":"什么地方6东7好2123123","mobile":"13888888888","province":"北京","city":"北京","area":"东城"}
     * details : [{"img":"/uploads/d0b1f88cda6218384147d41f2a09ee80.png","name":"资生堂可悠然（KUYURA）美肌 沐浴露套装.","num":1,"shipping_free":10,"price":"99.00","attr_value":["4G+32G","精包装","白","皮革"]}]
     */

    private String order_money;
    private String id;
    private String shipping_free;
    private String order_sn;
    private String address_id;
    private String integral;
    private UserinfoBean userinfo;
    private List<DetailsBean> details;

    public String getOrder_money() {
        return order_money;
    }

    public void setOrder_money(String order_money) {
        this.order_money = order_money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShipping_free() {
        return shipping_free;
    }

    public void setShipping_free(String shipping_free) {
        this.shipping_free = shipping_free;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class UserinfoBean {
        /**
         * name : 测试收件人2
         * address : 什么地方6东7好2123123
         * mobile : 13888888888
         * province : 北京
         * city : 北京
         * area : 东城
         */

        private String name;
        private String address;
        private String mobile;
        private String province;
        private String city;
        private String area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }

    public static class DetailsBean {
        /**
         * img : /uploads/d0b1f88cda6218384147d41f2a09ee80.png
         * name : 资生堂可悠然（KUYURA）美肌 沐浴露套装.
         * num : 1
         * shipping_free : 10
         * price : 99.00
         * attr_value : ["4G+32G","精包装","白","皮革"]
         */

        private String img;
        private String name;
        private String num;
        private String shipping_free;
        private String price;
        private List<String> attr_value;

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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getShipping_free() {
            return shipping_free;
        }

        public void setShipping_free(String shipping_free) {
            this.shipping_free = shipping_free;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public List<String> getAttr_value() {
            return attr_value;
        }

        public void setAttr_value(List<String> attr_value) {
            this.attr_value = attr_value;
        }
    }
}
