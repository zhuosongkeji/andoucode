package com.zskjprojectj.andouclient.entity.hotel;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/7 11:07
 * des: 酒店商家评论
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailCommentBean {


    /**
     * stars : 评星
     * created_at : 评论时间
     * content : 评论内容
     * name : 用户名
     * avator : 用户头像
     */

    private String stars;
    private String created_at;
    private String content;
    private String name;
    private String avator;

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
