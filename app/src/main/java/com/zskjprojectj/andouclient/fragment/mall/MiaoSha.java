package com.zskjprojectj.andouclient.fragment.mall;

import com.zskjprojectj.andouclient.model.Goods;
import com.zskjprojectj.andouclient.model.MiaoShaGoods;

import java.util.ArrayList;

public class MiaoSha {
    public ArrayList<MiaoShaGoods> recommends = new ArrayList<>();
    public ArrayList<MiaoShaGoods> goods = new ArrayList<>();

    public static MiaoSha getTest() {
        MiaoSha miaoSha = new MiaoSha();
        MiaoShaGoods goods = new MiaoShaGoods();
        goods.img = "http://img0.imgtn.bdimg.com/it/u=1473544931,889757245&fm=15&gp=0.jpg";
        goods.name = "秒杀测试商品";
        goods.price = 128.99;
        goods.miaoshaPrice = 88.88;
        goods.progress = 80;
        miaoSha.recommends.add(goods);
        miaoSha.recommends.add(goods);
        miaoSha.recommends.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        miaoSha.goods.add(goods);
        return miaoSha;
    }
}
