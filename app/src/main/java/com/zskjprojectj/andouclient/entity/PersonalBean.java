package com.zskjprojectj.andouclient.entity;

public class PersonalBean {

    /**
     * id : 15
     * name : 用户:17318203548
     * avator : /uploads/images/avators/201911/29//1575020535_VGSxFj53YP.jpg
     * //    "goodordernum":"商城订单数",
     * //            "foodsordernum":"饭店订单数",
     * //            "booksordernum":"酒店订单数",
     * grade : 该用户还不是Vip用户
     */

    private int id;
    private String name;
    private String avator;
    private String grade;
    private String money;
    private String goodordernum;
    private String foodsordernum;
    private String booksordernum;
    private String status;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getGoodordernum() {
        return goodordernum;
    }

    public void setGoodordernum(String goodordernum) {
        this.goodordernum = goodordernum;
    }

    public String getFoodsordernum() {
        return foodsordernum;
    }

    public void setFoodsordernum(String foodsordernum) {
        this.foodsordernum = foodsordernum;
    }

    public String getBooksordernum() {
        return booksordernum;
    }

    public void setBooksordernum(String booksordernum) {
        this.booksordernum = booksordernum;
    }



    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    private String integral;
    private String collect;
    private String focus;
    private String record;

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

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
