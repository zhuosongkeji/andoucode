package com.zskjprojectj.andouclient.entity;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/25 10:38
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class XBannerBean extends SimpleBannerInfo {

    public XBannerBean(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public Object getXBannerUrl() {
        return null;
    }
}
