package com.zskjprojectj.andouclient.entity.hotel;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/9 17:02
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MeHotelDetailsBean {


    /**
     * book_sn : 订单编号
     * created_at : 下单时间
     * pay_way : 支付方式
     * id : 房间id
     * merchant_id : 商户id
     * merchants_name : 商户名字
     * status : 订单状态（0-取消订单 10-未支付订单 20-已支付(待入住) 30 已入住 40-已完成(离店) 50-已评价）
     * img : 房间图片
     * house_name : 房间名字
     * price : 单价
     * integral : 使用积分
     * money : 订单总金额
     * start_time : 入住时间
     * end_time : 离开时间
     * day_num : 入住天数
     * real_name : 入住人
     * tel : 联系电话
     * pay_money : 支付金额
     */

    private String book_sn;
    private String created_at;
    private String pay_way;
    private String id;
    private String merchant_id;
    private String merchants_name;
    private String status;
    private String img;
    private String house_name;
    private String price;
    private String integral;
    private String money;
    private String start_time;
    private String end_time;
    private String day_num;
    private String real_name;
    private String mobile;
    private String tel;
    private String pay_money;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBook_sn() {
        return book_sn;
    }

    public void setBook_sn(String book_sn) {
        this.book_sn = book_sn;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchants_name() {
        return merchants_name;
    }

    public void setMerchants_name(String merchants_name) {
        this.merchants_name = merchants_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDay_num() {
        return day_num;
    }

    public void setDay_num(String day_num) {
        this.day_num = day_num;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }
}
