package com.zskjprojectj.andouclient.fragment.mall;

import com.zskjprojectj.andouclient.model.MiaoShaGoods;

import java.util.ArrayList;

public class MiaoSha {
    public String startTime;
    public String endTime;
    public State state;

    public ArrayList<MiaoShaGoods> recommends = new ArrayList<>();
    public ArrayList<MiaoShaGoods> goods = new ArrayList<>();

    public enum State {
        YI_QIANG_GOU("已抢购"), JIN_XING_ZHONG("抢购进行中"), JI_JIANG("即将开场");

        public String title;

        State(String title) {
            this.title = title;
        }
    }

    public static MiaoSha getTest(String time, State state) {
        MiaoSha miaoSha = new MiaoSha();
        miaoSha.startTime = time;
        miaoSha.endTime = "23:00";
        miaoSha.state = state;
        MiaoShaGoods goods = new MiaoShaGoods();
        goods.id = "23";
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

    public static ArrayList<MiaoSha> getTests() {
        ArrayList<MiaoSha> data = new ArrayList<>();
        data.add(getTest("08:00", State.YI_QIANG_GOU));
        data.add(getTest("10:00", State.YI_QIANG_GOU));
        data.add(getTest("12:00", State.JIN_XING_ZHONG));
        data.add(getTest("14:00", State.JI_JIANG));
        data.add(getTest("16:00", State.JI_JIANG));
        return data;
    }
}
