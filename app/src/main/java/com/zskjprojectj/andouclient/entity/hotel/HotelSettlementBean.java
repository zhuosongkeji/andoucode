package com.zskjprojectj.andouclient.entity.hotel;

import java.io.Serializable;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/7 16:11
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelSettlementBean implements Serializable {

    /**
     * start : 入住时间
     * end : 离店时间
     * days : 入住天数
     * room : {"house_name":"房间名字","img":"房间图片","price":"单价","merchant_id":"商户id","id":"房间id","name":"酒店名字"}
     * integral : 使用积分
     * allprice : 总价格
     */

    private String start;
    private String end;
    private String days;
    private RoomBean room;
    private String integral;
    private String allprice;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public RoomBean getRoom() {
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getAllprice() {
        return allprice;
    }

    public void setAllprice(String allprice) {
        this.allprice = allprice;
    }

    public static class RoomBean implements Serializable{
        /**
         * house_name : 房间名字
         * img : 房间图片
         * price : 单价
         * merchant_id : 商户id
         * id : 房间id
         * name : 酒店名字
         */

        private String house_name;
        private String img;
        private String price;
        private String merchant_id;
        private String id;
        private String name;

        public String getHouse_name() {
            return house_name;
        }

        public void setHouse_name(String house_name) {
            this.house_name = house_name;
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

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
