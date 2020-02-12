package com.zskjprojectj.andouclient.entity.mall;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/31 9:27
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallGoodsListBean  {


    /**
     * name : 商品名字
     * img : 商品图片
     * price : 价格
     * id : 商品id
     */

    private String name;
    private String img;
    private String price;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
