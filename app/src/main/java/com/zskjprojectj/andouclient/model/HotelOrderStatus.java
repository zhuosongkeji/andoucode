package com.zskjprojectj.andouclient.model;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.model
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2020/1/9 16:27
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public enum HotelOrderStatus {

    DAI_RU_ZHU("20"), DAI_PING_JIA("40"), YI_QU_XIAO("10");
    public String status;

    HotelOrderStatus(String status) {
        this.status = status;
    }
}
