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
public class PinTuanDetails {

    public String img;
    public String person;

    public static List<PinTuanDetails> getTest(){

        List<PinTuanDetails> data=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            PinTuanDetails pinTuanDetails =new PinTuanDetails();
            pinTuanDetails.img="https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1581580723&di=628a24247399ac37cb1bd6032742ef20&src=http://b-ssl.duitang.com/uploads/item/201701/30/20170130181206_x8ScK.thumb.700_0.jpeg";
            pinTuanDetails.person="2";
            data.add(pinTuanDetails);
        }
        return data;

    }

}
