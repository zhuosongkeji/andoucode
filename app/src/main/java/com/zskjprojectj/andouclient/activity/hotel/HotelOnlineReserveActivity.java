package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.hotel.HotelSettlementBean;
import com.zskjprojectj.andouclient.fragment.hotel.CustomViewDialog;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelOnlineReserveActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    //减人数
    @BindView(R.id.bt_minus)
    Button mBtMinus;
    //加人数
    @BindView(R.id.bt_add)
    Button mBtAdd;
    //人数
    @BindView(R.id.tv_person_number)
    TextView mPersonNumber;


    private TextView mReserve;
    private HotelSettlementBean hotelSettlementBean;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_online_reserve);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Glide.with(mAt).load(UrlUtil.getImageUrl(hotelSettlementBean.getRoom().getImg()))
                .apply(new RequestOptions().placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) findViewById(R.id.hotel_image));

        ((TextView) findViewById(R.id.hotel_name)).setText(hotelSettlementBean.getRoom().getName());
        ((TextView) findViewById(R.id.hotel_des)).setText(hotelSettlementBean.getRoom().getHouse_name());
        ((TextView) findViewById(R.id.hotel_price)).setText("¥" + hotelSettlementBean.getRoom().getPrice());
        ((TextView) findViewById(R.id.start_time)).setText(hotelSettlementBean.getStart());
        ((TextView) findViewById(R.id.end_time)).setText(hotelSettlementBean.getEnd());
        ((TextView) findViewById(R.id.night_numeber)).setText(hotelSettlementBean.getDays() + "晚");
        ((TextView) findViewById(R.id.tv_integral)).setText(hotelSettlementBean.getIntegral());
        ((TextView) findViewById(R.id.tv_allprice)).setText("¥" + hotelSettlementBean.getAllprice());

    }

    @Override
    protected void initViews() {
        getBarDistance(mHeaderTitleView);
        mHeaderTitle.setText("在线预订");

        hotelSettlementBean = (HotelSettlementBean) getIntent().getSerializableExtra("hotelSettlementBean");

        mReserve = findViewById(R.id.tv_reserve);
        mReserve.setOnClickListener(this);
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reserve:
                CustomViewDialog dialog = new CustomViewDialog(this, R.layout.hotel_pay_hint, new int[]{R.id.bt_cancel, R.id.bt_go_on});
                dialog.setOnCenterItemClickListener(new CustomViewDialog.OnCenterItemClickListener() {
                    @Override
                    public void OnCenterItemClick(CustomViewDialog dialog, View view) {
                        switch (view.getId()) {
                            case R.id.bt_cancel:
                                dialog.dismiss();
                                break;
                            case R.id.bt_go_on:
                                ToastUtil.showToast("订房成功");
                                break;
                        }
                    }
                });
                dialog.show();
                break;
        }
    }

    @OnClick({R.id.iv_header_back, R.id.rv_edt_name})
    public void clickBack(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            //填写姓名
            case R.id.rv_edt_name:

                break;
            //填写手机号
            case R.id.rv_edt_phone:
                break;
            //预订须知
            case R.id.rv_live_notice:
                break;
            //使用积分
            case R.id.rv_live_integral:
                break;
        }
    }

}
