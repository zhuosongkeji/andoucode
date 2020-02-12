package com.zskjprojectj.andouclient.adapter.hotel;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.hotel.MeHotelBean;
import com.zskjprojectj.andouclient.model.HotelOrderStatus;
import com.zskjprojectj.andouclient.model.OrderStatus;
import com.zskjprojectj.andouclient.utils.UrlUtil;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2020/1/9 15:00
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MeHotelAdapter extends BaseQuickAdapter<MeHotelBean, BaseViewHolder> {


    public MeHotelAdapter() {
        super(R.layout.item_fragmentmehotelorder);
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

        Glide.with(mContext).load(UrlUtil.getImageUrl(item.getLogo_img())).apply(new RequestOptions()
                .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) helper.getView(R.id.iv_order_img));
        Log.d("wangbin", "convert: " + UrlUtil.getImageUrl(item.getImg()) + "\n" + item.getImg());
        Glide.with(mContext).load(UrlUtil.getImageUrl(item.getImg())).apply(new RequestOptions()
                .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) helper.getView(R.id.iv_house_img));
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
