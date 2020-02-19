package com.zskjprojectj.andouclient.model;

import com.google.gson.annotations.SerializedName;

public class PinTuanGoods extends Goods {
    @SerializedName("top_member")
    public int people;
    @SerializedName("total_member")
    public int joinPeople;
    @SerializedName("sale_percent")
    public int progress;
    @SerializedName("old_price")
    public double oldPrice;
    public String goods_id;

}
