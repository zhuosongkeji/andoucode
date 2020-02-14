package com.zskjprojectj.andouclient.entity.mall;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/30 9:17
 * des: 支付方式
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallPayWaysBean {

    /**
     * id : 支付方式id
     * pay_way : 支付方式名字
     * logo : 图标
     */

    private String id;
    private String pay_way;
    private String logo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
