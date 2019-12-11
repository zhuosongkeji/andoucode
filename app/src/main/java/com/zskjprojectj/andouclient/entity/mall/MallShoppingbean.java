package com.zskjprojectj.andouclient.entity.mall;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/10 17:24
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallShoppingbean {

    private  int imageResource;
    private String mallShoppingName;
    private String mallShoppongPrice;

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getMallShoppingName() {
        return mallShoppingName;
    }

    public void setMallShoppingName(String mallShoppingName) {
        this.mallShoppingName = mallShoppingName;
    }

    public String getMallShoppongPrice() {
        return mallShoppongPrice;
    }

    public void setMallShoppongPrice(String mallShoppongPrice) {
        this.mallShoppongPrice = mallShoppongPrice;
    }
}
