package com.zskjprojectj.andouclient.activity.hotel;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atuan.datepickerlibrary.CalendarUtil;
import com.atuan.datepickerlibrary.DatePopupWindow;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MainActivity;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.activity.mall.MallPaySuccessActivity;
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.WXPayBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelSettlementBean;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.fragment.hotel.CustomViewDialog;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.CalendarViewUtil;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelOnlineReserveActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

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

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_online_reserve);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        //商户ID
        merchant_id = hotelSettlementBean.getRoom().getMerchant_id();
        //房间ID
        home_id = hotelSettlementBean.getRoom().getId();


        Glide.with(mAt).load(UrlUtil.getImageUrl(hotelSettlementBean.getRoom().getImg()))
                .apply(new RequestOptions().placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) findViewById(R.id.hotel_image));

        ((TextView) findViewById(R.id.hotel_name)).setText(hotelSettlementBean.getRoom().getName());
        ((TextView) findViewById(R.id.hotel_des)).setText(hotelSettlementBean.getRoom().getHouse_name());
        ((TextView) findViewById(R.id.hotel_price)).setText(hotelSettlementBean.getRoom().getPrice());
        ((TextView) findViewById(R.id.start_time)).setText(hotelSettlementBean.getStart());
        ((TextView) findViewById(R.id.end_time)).setText(hotelSettlementBean.getEnd());
        ((TextView) findViewById(R.id.night_numeber)).setText(hotelSettlementBean.getDays() + "晚");
        ((TextView) findViewById(R.id.tv_integral)).setText(hotelSettlementBean.getIntegral());
        ((TextView) findViewById(R.id.tv_allprice)).setText("¥" + hotelSettlementBean.getRoom().getPrice());


    }

    @Override
    protected void initViews() {
        getBarDistance(mHeaderTitleView);
        mHeaderTitle.setText("在线预订");

        hotelSettlementBean = (HotelSettlementBean) getIntent().getSerializableExtra("hotelSettlementBean");

    }

    @Override
    public void getDataFromServer() {

        //请求支付方式
        HttpRxObservable.getObservable(ApiUtils.getApiService().getMallPayWays()).subscribe(new BaseObserver<List<MallPayWaysBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<MallPayWaysBean> mallPayWaysBeans) throws IOException {
                mRvPayWays.setLayoutManager(new LinearLayoutManager(mAt));
                PayWaysAdapter adapter = new PayWaysAdapter(R.layout.pay_ways_item, mallPayWaysBeans);
                mRvPayWays.addItemDecoration(new DividerItemDecoration(mAt, DividerItemDecoration.VERTICAL));
                mRvPayWays.setAdapter(adapter);
                adapter.setItemPayWays(new PayWaysAdapter.ItemPayWays() {
                    @Override
                    public void getPayWays(String payWays, int position) {
                        payId = mallPayWaysBeans.get(position).getId();
                    }
                });
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }



    @OnClick({R.id.iv_header_back, R.id.bt_minus, R.id.bt_add, R.id.tv_reserve, R.id.rv_slector_time})
    public void clickBack(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
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
                    ToastUtil.showToast("请填写真实姓名");
                    return;
                } else if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast("请输入手机号");
                    return;
                } else {

                    int id = Integer.parseInt(payId);
                    switch (id) {
                        case WXPAY:
                            HttpRxObservable.getObservable(ApiUtils.getApiService().hotelOrder(
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
                                    "0"

                            )).subscribe(new BaseObserver<WXPayBean>(mAt) {
                                @Override
                                public void onHandleSuccess(WXPayBean wxPayBean) throws IOException {
                                    startWXPay(wxPayBean);
                                }
                            });

                            break;
                        case YUEPAY:

                            Log.d("wangbin", "酒店预订: "+LoginInfoUtil.getToken()+"\n"
                                    +home_id+"\n"
                                    +merchant_id+"\n"
                                    +start+"\n"
                                    +end+"\n"
                                    +name+"\n"
                                    +phone+"\n"
                                    +personNUm+"\n"
                                    +datNum+"\n"
                                    +payId+"\n"
                            );

                            HttpRxObservable.getObservable(ApiUtils.getApiService().hotelOrder(
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
                                    "0"

                            )).subscribe(new BaseObserver<WXPayBean>(mAt) {
                                @Override
                                public void onHandleSuccess(WXPayBean wxPayBean) throws IOException {
                                    startActivity(new Intent(HotelOnlineReserveActivity.this, MallPaySuccessActivity.class));
                                    finish();
                                }
                            });

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
                                mNighitNumber.setText(substring+"晚");

                                //计算总房价
                                int days = Integer.parseInt(substring);
                                int prices = Integer.parseInt(hotelSettlementBean.getAllprice());
                                int allPrices=days*prices;
                                String all = String.valueOf(allPrices);

                                ((TextView) findViewById(R.id.tv_allprice)).setText("¥" + all);

                            }
                        })
                        .builder();
                break;
        }
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private void startWXPay(WXPayBean wxPayBean) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(HotelOnlineReserveActivity.this, wxPayBean.getAppid());
        //将该app注册到微信
        msgApi.registerApp(wxPayBean.getAppid());

//        创建支付请求对象
        PayReq req = new PayReq();
        req.appId = wxPayBean.getAppid();
        req.partnerId = wxPayBean.getMch_id();
        req.prepayId = wxPayBean.getPrepay_id();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = wxPayBean.getNonce_str();
        req.timeStamp = wxPayBean.getTimestamp();
        req.sign = wxPayBean.getSign();
        msgApi.sendReq(req);
//        WXTextObject textObj = new WXTextObject();
//        textObj.text = "测试分享";
//
////用 WXTextObject 对象初始化一个 WXMediaMessage 对象
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObj;
//        msg.description = "测试分享";
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = "text";
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneSession;
////调用api接口，发送数据到微信
//        msgApi.sendReq(req);
    }

}
