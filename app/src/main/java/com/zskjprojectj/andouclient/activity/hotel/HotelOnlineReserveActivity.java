package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.atuan.datepickerlibrary.DatePopupWindow;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.HotelorderActivity;
import com.zskjprojectj.andouclient.activity.mall.MallPaySuccessActivity;
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter;
import com.zskjprojectj.andouclient.entity.hotel.HotelSettlementBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.CalendarViewUtil;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PaySuccessBackEvent;
import com.zskjprojectj.andouclient.utils.PaySuccessEvent;
import com.zskjprojectj.andouclient.utils.PayUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelOnlineReserveActivity extends BaseActivity {
    //人数
    @BindView(R.id.tv_person_number)
    TextView mPersonNumber;

    @BindView(R.id.rv_pay_ways)
    RecyclerView mRvPayWays;

    @BindView(R.id.tv_live_name)
    EditText mTvLiveName;

    @BindView(R.id.tv_live_phone)
    EditText mTvLivePhone;

    //入住时间
    @BindView(R.id.start_time)
    TextView mStartTime;
    //离开时间
    @BindView(R.id.end_time)
    TextView mEndTime;
    //天数
    @BindView(R.id.night_numeber)
    TextView mNighitNumber;

    @BindView(R.id.cb_use_integral)
    AppCompatCheckBox mIntegral;


    private HotelSettlementBean hotelSettlementBean;
    private String payId;
    private final static int WXPAY = 1;
    private final static int YUEPAY = 4;
    private String merchant_id;
    private String home_id;
    private int startGroup = -1;
    private int endGroup = -1;
    private int startChild = -1;
    private int endChild = -1;
    private String is_integral = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "在线预订");
        hotelSettlementBean = (HotelSettlementBean) getIntent().getSerializableExtra("hotelSettlementBean");
        //商户ID
        merchant_id = hotelSettlementBean.getRoom().getMerchant_id();
        //房间ID
        home_id = hotelSettlementBean.getRoom().getId();


        Glide.with(mActivity).load(UrlUtil.INSTANCE.getImageUrl(hotelSettlementBean.getRoom().getImg()))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder)).into((ImageView) findViewById(R.id.hotel_image));

        ((TextView) findViewById(R.id.hotel_name)).setText(hotelSettlementBean.getRoom().getName());
        ((TextView) findViewById(R.id.hotel_des)).setText(hotelSettlementBean.getRoom().getHouse_name());
        ((TextView) findViewById(R.id.hotel_price)).setText(hotelSettlementBean.getRoom().getPrice());
        ((TextView) findViewById(R.id.start_time)).setText(hotelSettlementBean.getStart());
        ((TextView) findViewById(R.id.end_time)).setText(hotelSettlementBean.getEnd());
        ((TextView) findViewById(R.id.night_numeber)).setText(hotelSettlementBean.getDays() + "晚");
        ((TextView) findViewById(R.id.tv_integral)).setText(hotelSettlementBean.getIntegral());
        ((TextView) findViewById(R.id.tv_allprice)).setText("¥" + hotelSettlementBean.getRoom().getPrice());
        ((TextView) findViewById(R.id.tv_all_price)).setText("¥" + hotelSettlementBean.getRoom().getPrice());
        mIntegral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    BigDecimal bigDecimal = new BigDecimal(hotelSettlementBean.getRoom().getPrice());
                    BigDecimal subtract = bigDecimal.subtract(new BigDecimal(hotelSettlementBean.getIntegral()));
                    ((TextView) findViewById(R.id.tv_allprice)).setText("¥" + subtract.toString());
                    ((TextView) findViewById(R.id.tv_all_price)).setText("¥" + subtract.toString());
                    is_integral = "1";
                } else {
                    ((TextView) findViewById(R.id.tv_allprice)).setText("¥" + hotelSettlementBean.getRoom().getPrice());
                    ((TextView) findViewById(R.id.tv_all_price)).setText("¥" + hotelSettlementBean.getRoom().getPrice());
                    is_integral = "0";
                }
            }
        });
        //1、注册广播
        EventBus.getDefault().register(HotelOnlineReserveActivity.this);
        //请求支付方式
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getMallPayWays(),
                result -> {
                    mRvPayWays.setLayoutManager(new LinearLayoutManager(mActivity));
                    PayWaysAdapter adapter = new PayWaysAdapter(R.layout.pay_ways_item, result.data);
                    mRvPayWays.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
                    mRvPayWays.setAdapter(adapter);
                    adapter.setItemPayWays(new PayWaysAdapter.ItemPayWays() {
                        @Override
                        public void getPayWays(String payWays, int position) {
                            payId = result.data.get(position).getId();
                        }
                    });
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(HotelOnlineReserveActivity.this);
    }

    @OnClick({R.id.bt_minus, R.id.bt_add, R.id.tv_reserve, R.id.rv_slector_time})
    public void clickBack(View view) {
        switch (view.getId()) {
            //减
            case R.id.bt_minus:
                String personNum = mPersonNumber.getText().toString();
                int num = Integer.parseInt(personNum);
                if (num > 1) {
                    num -= 1;
                    String addNumber = Integer.toString(num);
                    mPersonNumber.setText(addNumber);
                }
                break;
            //加
            case R.id.bt_add:
                String number = mPersonNumber.getText().toString();
                int i = Integer.parseInt(number);
                i += 1;
                String addNumber = Integer.toString(i);
                mPersonNumber.setText(addNumber);
                break;

            case R.id.tv_reserve:
                //姓名
                String name = mTvLiveName.getText().toString();
                //电话
                String phone = mTvLivePhone.getText().toString();
                //入住人数
                String personNUm = mPersonNumber.getText().toString();
                //入住天数
                String day = mNighitNumber.getText().toString();
                String datNum = day.replace("晚", "");
                //入住时间
                String start = mStartTime.getText().toString();
                //离开时间
                String end = mEndTime.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showShort("请填写真实姓名");
                    return;
                } else if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                } else {
                    if (TextUtils.isEmpty(payId)) {
                        ToastUtils.showShort("请选择支付方式");
                        return;
                    }
                    int id = Integer.parseInt(payId);


                    switch (id) {
                        case WXPAY:
                            RequestUtil.request(mActivity, true, false,
                                    () -> ApiUtils.getApiService().hotelOrder(
                                            LoginInfoUtil.getUid(),
                                            LoginInfoUtil.getToken(),
                                            home_id,
                                            merchant_id,
                                            start,
                                            end,
                                            name,
                                            phone,
                                            personNUm,
                                            datNum,
                                            payId,
                                            is_integral

                                    ),
                                    result -> {
                                        PayUtil.INSTANCE.startWXPay(mActivity, result.data);
                                    });
                            break;
                        case YUEPAY:
                            new AlertDialog.Builder(mActivity)
                                    .setTitle("温馨提示")
                                    .setMessage("确定用余额支付该订单吗？")
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("确定",
                                            (dialog, which) -> RequestUtil.request(mActivity, true, false,
                                                    () -> ApiUtils.getApiService().hotelOrder(
                                                            LoginInfoUtil.getUid(),
                                                            LoginInfoUtil.getToken(),
                                                            home_id,
                                                            merchant_id,
                                                            start,
                                                            end,
                                                            name,
                                                            phone,
                                                            personNUm,
                                                            datNum,
                                                            payId,
                                                            is_integral
                                                    ),
                                                    result -> {
                                                        startActivity(new Intent(HotelOnlineReserveActivity.this, MallPaySuccessActivity.class));

                                                    }))
                                    .show();
                            break;
                    }
                }
                break;
            case R.id.rv_slector_time:
                new DatePopupWindow
                        .Builder(HotelOnlineReserveActivity.this, Calendar.getInstance().getTime(), view)
                        .setInitSelect(startGroup, startChild, endGroup, endChild)
                        .setInitDay(false)
                        .setDateOnClickListener(new DatePopupWindow.DateOnClickListener() {
                            @Override
                            public void getDate(String startDate, String endDate, int startGroupPosition, int startChildPosition, int endGroupPosition, int endChildPosition) {
                                startGroup = startGroupPosition;
                                startChild = startChildPosition;
                                endGroup = endGroupPosition;
                                endChild = endChildPosition;
                                String sartTime = CalendarViewUtil.FormatDateYMD(startDate);
                                String endTime = CalendarViewUtil.FormatDateYMD(endDate);
                                String twoDay = CalendarViewUtil.getTwoDay(startDate, endDate);
                                String substring = twoDay.substring(1);

                                mStartTime.setText(sartTime);
                                mEndTime.setText(endTime);
                                mNighitNumber.setText(substring + "晚");

                                //计算总房价
                                int days = Integer.parseInt(substring);
                                int prices = Integer.parseInt(hotelSettlementBean.getAllprice());
                                int allPrices = days * prices;
                                String all = String.valueOf(allPrices);

                                ((TextView) findViewById(R.id.tv_allprice)).setText("¥" + all);

                            }
                        })
                        .builder();
                break;
        }
    }

    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentEventBus(PaySuccessEvent paySuccessEvent) {
        Intent intent = new Intent(HotelOnlineReserveActivity.this, HotelorderActivity.class);
        intent.putExtra("flag", "MallPaySuccess");
        startActivity(intent);
        finish();
    }


    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backEventBus(PaySuccessBackEvent paySuccessBackEvent) {
        finish();
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_hotel_online_reserve;
    }
}
