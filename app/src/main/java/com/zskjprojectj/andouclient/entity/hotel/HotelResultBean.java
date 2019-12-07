package com.zskjprojectj.andouclient.entity.hotel;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/6 15:35
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelResultBean {
    private String hotelName;
    private int hotelImage;
    private String hotelLikeNumber;
    private String hotelStarNumber;
    private String hotelAdress;
    private String hotelPrice;
    private boolean isVip;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(int hotelImage) {
        this.hotelImage = hotelImage;
    }

    public String getHotelLikeNumber() {
        return hotelLikeNumber;
    }

    public void setHotelLikeNumber(String hotelLikeNumber) {
        this.hotelLikeNumber = hotelLikeNumber;
    }

    public String getHotelStarNumber() {
        return hotelStarNumber;
    }

    public void setHotelStarNumber(String hotelStarNumber) {
        this.hotelStarNumber = hotelStarNumber;
    }

    public String getHotelAdress() {
        return hotelAdress;
    }

    public void setHotelAdress(String hotelAdress) {
        this.hotelAdress = hotelAdress;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }
}
