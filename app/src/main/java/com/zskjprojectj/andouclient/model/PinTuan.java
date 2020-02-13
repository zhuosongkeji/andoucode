package com.zskjprojectj.andouclient.model;

import java.util.ArrayList;

public class PinTuan {
    public ArrayList<PinTuanGoods> mustGoods = new ArrayList<>();
    public ArrayList<PinTuanGoods> recommendGoods = new ArrayList<>();
    public ArrayList<PinTuanType> types = new ArrayList<>();

    public static class PinTuanType {
        public String title;
        public ArrayList<PinTuanGoods> goods = new ArrayList<>();
    }

    public static PinTuan getTest() {
        PinTuan pinTuan = new PinTuan();
        PinTuanGoods goods = new PinTuanGoods();
        goods.id = "23";
        goods.img = "http://img0.imgtn.bdimg.com/it/u=1473544931,889757245&fm=15&gp=0.jpg";
        goods.name = "拼团测试商品";
        goods.price = 128.99;
        goods.pintuanPrice = 88.88;
        goods.people = 3;
        goods.joinPeople = 3312;
        goods.progress = 60;
        pinTuan.mustGoods.add(goods);
        pinTuan.mustGoods.add(goods);
        pinTuan.mustGoods.add(goods);
        pinTuan.recommendGoods.add(goods);
        pinTuan.recommendGoods.add(goods);
        pinTuan.recommendGoods.add(goods);
        for (int i = 0; i < 10; i++) {
            PinTuanType pinTuanType = new PinTuanType();
            pinTuanType.title = "分类" + i;
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuanType.goods.add(goods);
            pinTuan.types.add(pinTuanType);
        }
        return pinTuan;
    }
}
