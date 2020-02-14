package com.zskjprojectj.andouclient.entity;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/4 12:01
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RefundReasonBean {


    /**
     * name : 退货理由
     * type : 退货分类（1-商城 2-酒店）
     */

    private String name;
    private String type;
    private String id;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
