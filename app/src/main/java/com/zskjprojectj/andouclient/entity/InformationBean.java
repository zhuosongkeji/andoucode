package com.zskjprojectj.andouclient.entity;

public class InformationBean {

    /**
     * id : 标题id
     * title : 公告标题
     * content : 公告内容
     * created_at : 创建时间
     */

    private String id;
    private String title;
    private String content;
    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
