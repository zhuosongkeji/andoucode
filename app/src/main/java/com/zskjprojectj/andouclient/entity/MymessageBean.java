package com.zskjprojectj.andouclient.entity;

public class MymessageBean {
    /**
     * id : 标题id
     * title : 公告标题
     * message : 已读消息用户id
     * messageStatus : 公告状态 1已读 0未读
     * created_at : 发布时间
     */

    private String id;
    private String title;
    private String message;
    private String messageStatus;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
