package com.zskjprojectj.andouclient.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.model
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/2/13 15:18
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class PinTuan {

    public String img;
    public String person;

    public static List<PinTuan> getTest(){

        List<PinTuan> data=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            PinTuan pinTuan=new PinTuan();
            pinTuan.img="https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1581580723&di=628a24247399ac37cb1bd6032742ef20&src=http://b-ssl.duitang.com/uploads/item/201701/30/20170130181206_x8ScK.thumb.700_0.jpeg";
            pinTuan.person="2";
            data.add(pinTuan);
        }
        return data;

    }

}
