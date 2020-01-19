package com.zskjprojectj.andouclient.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yhao.floatwindow.FloatWindow;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.CauseRecyclerAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelRefundAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.hotel.HotelrefundreasonBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelordercancleActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    @BindView(R.id.tv_refund_reason)
    TextView mRefundReason;

    @BindView(R.id.tv_price)
    TextView mPrice;

    private LinearLayout ly_chooserefundreason;
    private Dialog bottomDialog;
    private String merchant_id;
    private String id;
    private String book_sn;
    private String reasonId;
    private String refund_msg;
    private String pay_money;

    @OnClick({R.id.iv_header_back, R.id.rv_refund_reason, R.id.btn_commint})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;
            case R.id.rv_refund_reason:

                HttpRxObservable.getObservable(ApiUtils.getApiService().hotelrefundreason(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        merchant_id
                )).subscribe(new BaseObserver<List<HotelrefundreasonBean>>(mAt) {
                    @Override
                    public void onHandleSuccess(List<HotelrefundreasonBean> hotelrefundreasonBeans) throws IOException {
                        initBuyNow(hotelrefundreasonBeans);
                    }
                });
                break;
            case R.id.btn_commint:
                Log.d("wangbin", "clickView: " + LoginInfoUtil.getUid() + "\n"
                        + LoginInfoUtil.getToken() + "\n"
                        + book_sn + "\n"
                        + reasonId + "\n"
                        + refund_msg + "\n"
                );


                HttpRxObservable.getObservable(ApiUtils.getApiService().hotelrefund(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        book_sn,
                        reasonId,
                        refund_msg
                )).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        //TODO 酒店取消订单
                        ToastUtils.showShort("取消订单成功");
                        finish();
                    }
                });
                break;
        }

    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelordercancle);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHeaderTitle.setText("申请退款");
        getBarDistance(mTitleView);
    }

    @Override
    protected void initViews() {

        merchant_id = getIntent().getStringExtra("merchant_id");
        id = getIntent().getStringExtra("id");
        book_sn = getIntent().getStringExtra("book_sn");
        pay_money = getIntent().getStringExtra("pay_money");

        mPrice.setText("¥" + pay_money);
    }

    @Override
    public void getDataFromServer() {

    }

    /**
     * 弹出窗口
     *
     * @param hotelrefundreasonBeans
     * @return
     */
    private void initBuyNow(List<HotelrefundreasonBean> hotelrefundreasonBeans) {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        Window window = bottomDialog.getWindow();
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置宽度
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotelcancle_discount, null);
        bottomDialog.setContentView(contentView);
        ImageView mCancle = contentView.findViewById(R.id.iv_cancle);
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

        RecyclerView mRvCauseRecycler = contentView.findViewById(R.id.rv_cause_recycler);

        mRvCauseRecycler.setLayoutManager(new LinearLayoutManager(mRvCauseRecycler.getContext()));
        HotelRefundAdapter adapter = new HotelRefundAdapter();
        adapter.setNewData(hotelrefundreasonBeans);
        mRvCauseRecycler.setAdapter(adapter);
        mRvCauseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.setOnItemContent(new HotelRefundAdapter.OnItemContent() {
            @Override
            public void content(String content, String reason_id) {
                reasonId = reason_id;
                mRefundReason.setText(content);
                bottomDialog.dismiss();
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public static void start(String merchant_id, String id, String book_sn, String pay_money) {

        Bundle bundle = new Bundle();
        bundle.putString("merchant_id", merchant_id);
        bundle.putString("id", id);
        bundle.putString("book_sn", book_sn);
        bundle.putString("pay_money", pay_money);
        ActivityUtils.startActivity(bundle, HotelordercancleActivity.class);
    }
}
