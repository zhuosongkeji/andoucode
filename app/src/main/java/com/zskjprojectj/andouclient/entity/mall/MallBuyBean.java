package com.zskjprojectj.andouclient.entity.mall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/26 15:22
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallBuyBean {

    public HashMap<String, PriceInfo> price = new HashMap<>();

    public ArrayList<SpecInfo> res = new ArrayList<>();

    public class PriceInfo {
        public String id;
        public float price;
        public int num;
    }

    public static class SpecInfo {
        public String name;
        public ArrayList<String> value = new ArrayList<>();
    }
}
