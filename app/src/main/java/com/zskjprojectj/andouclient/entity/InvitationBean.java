package com.zskjprojectj.andouclient.entity;

public class InvitationBean {


    /**
     * id : 用户id
     * name : 用户名字
     * avator : 用户头像
     * invitation : 邀请码
     * qrcode : 邀请二维码
     */

    private String id;
    private String name;
    private String avator;
    private String invitation;
    private String qrcode;

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

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getInvitation() {
        return invitation;
    }

    public void setInvitation(String invitation) {
        this.invitation = invitation;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
