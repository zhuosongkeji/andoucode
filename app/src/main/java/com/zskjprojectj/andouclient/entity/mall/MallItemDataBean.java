package com.zskjprojectj.andouclient.entity.mall;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/9 20:10
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallItemDataBean implements MultiItemEntity {

//    public static final int SEARCH = 1;
//    public static final int BANNER = 1;
    public static final int MENU = 2;
    public static final int PLAN = 3;
    public static final int NEWGOODS = 4;
    public static final int RECOMMEND = 5;
    public static final int GOODSDETAILS = 6;

//    public static final int SEARCH_SPAN_SIZE = 4;
//    public static final int BANNER_SPAN_SIZE = 4;
    public static final int MENU_SPAN_SIZE = 1;
    public static final int PLAN_SPAN_SIZE = 2;
    public static final int NEWGOODS_SPAN_SIZE = 4;
    public static final int RECOMMEND_SPAN_SIZE = 4;
    public static final int GOODSDETAILS_SPAN_SIZE = 2;


    private int spanSize;
    private int itemType;
    private String Content;

    public MallItemDataBean(int spanSize, int itemType, String content) {
        this.spanSize = spanSize;
        this.itemType = itemType;
        this.Content = content;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public MallItemDataBean(int itemType, int spanSize) {
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
