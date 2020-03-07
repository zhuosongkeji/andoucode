package com.zskjprojectj.andouclient.adapter.hotel;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.entity.hotel.MeHotelBean;
import com.zskjprojectj.andouclient.model.HotelOrderStatus;

public class HotelListAdapter extends BaseQuickAdapter<MeHotelBean, BaseViewHolder> {

    public HotelListAdapter() {
        super(R.layout.layout_hotel_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeHotelBean item) {
        helper.setText(R.id.tv_order_number, "订单编号:" + item.getBook_sn())
                .setText(R.id.tv_order_name, item.getMerchants_name())
                .setText(R.id.tv_house_name, item.getHouse_name())
                .setText(R.id.tv_house_price, "¥" + item.getPrice())
                .setText(R.id.tv_house_all_price, item.getPrice())
                .setText(R.id.tv_status, getStatusStr(item.getStatus()))
                .addOnClickListener(R.id.btn_hotelorderdetails)
                .addOnClickListener(R.id.btn_evaluate);

        Glide.with(mContext).load(UrlUtil.INSTANCE.getImageUrl(item.getLogo_img())).apply(new RequestOptions()
                .placeholder(R.mipmap.ic_placeholder)).into((ImageView) helper.getView(R.id.iv_order_img));
        Log.d("wangbin", "convert: " + UrlUtil.INSTANCE.getImageUrl(item.getImg()) + "\n" + item.getImg());
        Glide.with(mContext).load(UrlUtil.INSTANCE.getImageUrl(item.getImg())).apply(new RequestOptions()
                .placeholder(R.mipmap.ic_placeholder)).into((ImageView) helper.getView(R.id.iv_house_img));
        helper.setGone(R.id.btn_evaluate, HotelOrderStatus.DAI_PING_JIA.status.equals(item.getStatus()));

    }

    private String getStatusStr(String status) {
        //10-未支付 20-已支付 40-已发货 50-交易成功（确认收货） 60-交易关闭（已评论）
        switch (status) {
            case "20":
                return "待入住";
            case "40":
                return "待评价";
            case "10":
                return "已取消";
            default:
                return "";
        }
    }

}
