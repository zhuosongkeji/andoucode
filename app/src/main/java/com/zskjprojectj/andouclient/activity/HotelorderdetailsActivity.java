package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.hotel.MeHotelBean;
import com.zskjprojectj.andouclient.entity.hotel.MeHotelDetailsBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.HotelOrderStatus;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单详情
 */

public class HotelorderdetailsActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;


    private Button btn_hotelordercancle, btn_hotelordercall;
    private MeHotelBean meHotelBean;

    @OnClick(R.id.iv_header_back)
    public void clickView(){
        finish();
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelorderdetails);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHeaderTitle.setText("订单详情");
        getBarDistance(mTitleView);
    }

    @Override
    protected void initViews() {

        meHotelBean = (MeHotelBean) getIntent().getSerializableExtra("MeHotelBean");

        btn_hotelordercancle = findViewById(R.id.btn_hotelordercancle);
        btn_hotelordercall = findViewById(R.id.btn_hotelordercall);
        btn_hotelordercancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(HotelordercancleActivity.class);
            }
        });
        btn_hotelordercall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "13812342345";
                Intent myCallIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel" + phoneNumber));
                startActivity(myCallIntent);
            }
        });
    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().mehotelOrderDetails(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                meHotelBean.getBook_sn()
        )).subscribe(new BaseObserver<MeHotelDetailsBean>(mAt) {
            @Override
            public void onHandleSuccess(MeHotelDetailsBean meHotelDetailsBean) throws IOException {
                bindView(meHotelDetailsBean);
            }
        });
    }

    private void bindView(MeHotelDetailsBean meHotelDetailsBean) {
        if (HotelOrderStatus.DAI_RU_ZHU.status.equals(meHotelDetailsBean.getStatus())) {
            findViewById(R.id.ll_order_check_in).setVisibility(View.VISIBLE);
            //取消订单
            findViewById(R.id.btn_hotelordercancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HotelordercancleActivity.start(meHotelDetailsBean.getMerchant_id(),
                            meHotelDetailsBean.getId(), meHotelDetailsBean.getBook_sn());
                }
            });
            //联系商家
            findViewById(R.id.btn_hotelordercall).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call(meHotelDetailsBean.getMobile());
                }
            });


        } else if (HotelOrderStatus.DAI_PING_JIA.status.equals(meHotelDetailsBean.getStatus())) {
            findViewById(R.id.ll_order_evaluate).setVisibility(View.VISIBLE);
            //订单评价
            findViewById(R.id.btn_hotelOrderRate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HotelordergotoevaluationActivity.start(meHotelDetailsBean.getMerchant_id(),
                            meHotelDetailsBean.getId(), meHotelDetailsBean.getBook_sn());
                }
            });
            //联系商家
            findViewById(R.id.btn_hotelordercall1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call(meHotelDetailsBean.getMobile());
                }
            });
        } else if (HotelOrderStatus.YI_QU_XIAO.status.equals(meHotelDetailsBean.getStatus())) {
            findViewById(R.id.ll_order_cancel).setVisibility(View.VISIBLE);
            //联系商家
            findViewById(R.id.btn_hotelordercall_Cancle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call(meHotelDetailsBean.getMobile());
                }
            });
        }

        ((TextView) findViewById(R.id.tv_merchants_name)).setText(meHotelDetailsBean.getMerchants_name());
        ((TextView) findViewById(R.id.tv_house_name)).setText(meHotelDetailsBean.getHouse_name());
        ((TextView) findViewById(R.id.tv_house_price)).setText("¥" + meHotelDetailsBean.getPrice());
        ((TextView) findViewById(R.id.tv_house_all_price)).setText("¥" + meHotelDetailsBean.getMoney());
        ((TextView) findViewById(R.id.tv_all_price)).setText(meHotelDetailsBean.getPay_money());
        //入住时间
        String start_time = meHotelDetailsBean.getStart_time();
        String start = start_time.substring(5);
        String end_time = meHotelDetailsBean.getEnd_time();
        String end = end_time.substring(5);
        String day_num = meHotelDetailsBean.getDay_num();
        ((TextView) findViewById(R.id.tv_check_in)).setText(start + "-" + end + " " + "共" + day_num + "晚");

        ((TextView) findViewById(R.id.tv_check_in_name)).setText(meHotelDetailsBean.getReal_name());
        ((TextView) findViewById(R.id.tv_check_in_phone)).setText(meHotelDetailsBean.getMobile());
        ((TextView) findViewById(R.id.tv_order_number)).setText(meHotelDetailsBean.getBook_sn());
        ((TextView) findViewById(R.id.created_at)).setText(meHotelDetailsBean.getCreated_at());
        ((TextView) findViewById(R.id.pay_way)).setText(meHotelDetailsBean.getPay_way());
        Glide.with(mAt).load(BaseUrl.BASE_URL + meHotelDetailsBean.getImg()).apply(new RequestOptions()
                .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) findViewById(R.id.img_iconleft));

    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public static void start(MeHotelBean meHotelBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("MeHotelBean", meHotelBean);
        ActivityUtils.startActivity(bundle, HotelorderdetailsActivity.class);
    }

    private void call(String phoneNumber){
        Intent myCallIntent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + phoneNumber));
        startActivity(myCallIntent);
    }
}
