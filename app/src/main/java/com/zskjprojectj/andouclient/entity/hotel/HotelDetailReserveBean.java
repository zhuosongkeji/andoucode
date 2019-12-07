package com.zskjprojectj.andouclient.entity.hotel;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 10:15
 * des:酒店详情评论实体类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailReserveBean {
    private int imageResource;
    private String hotelName;
    private String hotelPrice;

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }
}
