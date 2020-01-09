package com.zskjprojectj.andouclient.entity.hotel;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 10:15
 * des:酒店详情预订实体类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailReserveBean {

    private List<HotelRoomBean> hotel_room;

    public List<HotelRoomBean> getHotel_room() {
        return hotel_room;
    }

    public void setHotel_room(List<HotelRoomBean> hotel_room) {
        this.hotel_room = hotel_room;
    }

    public static class HotelRoomBean {
        /**
         * id : 2
         * house_name : 单间
         * price : 99.00
         * img : /uploads/201912070712307157.jpg
         * desc : 1,2
         * name : 上网wifi,独立,
         */

        private int id;
        private String house_name;
        private String price;
        private String img;
        private String desc;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
