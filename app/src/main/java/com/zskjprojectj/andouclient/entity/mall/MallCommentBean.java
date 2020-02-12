package com.zskjprojectj.andouclient.entity.mall;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/25 11:18
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallCommentBean {


    /**
     * avator : 用户头像
     * name : 用户名字
     * id : 评论id
     * stars : 评论星级
     * content : 评论内容
     * created_at : 评论时间
     */

    private String avator;
    private String name;
    private String id;
    private String stars;
    private String content;
    private String created_at;

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
