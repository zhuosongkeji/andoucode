package com.zskjprojectj.andouclient.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.yhao.floatwindow.FloatWindow;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.CauseRecyclerAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.RefundReasonBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请退款
 */
public class ShopordersendetailsrefundActivity extends BaseActivity {

    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;


    private LinearLayout ly_refundreason;
    private Dialog bottomDialog;
    private String type;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_shopordersendetailsrefund);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");

        if ("sales_return".equals(type)){
            mHeaderTitle.setText("申请退货");
        }else if ("refund".equals(type)){
            mHeaderTitle.setText("申请退款");
        }


    }

    @Override
    protected void initViews() {
        ly_refundreason = findViewById(R.id.ly_refundreason);
        ly_refundreason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().refundreason()).subscribe(new BaseObserver<List<RefundReasonBean>>(mAt) {
                    @Override
                    public void onHandleSuccess(List<RefundReasonBean> refundReasonBeans) throws IOException {
                        initBuyNow(refundReasonBeans);
                    }
                });


            }
        });
    }

    @Override
    public void getDataFromServer() {

    }

    /**
     * 弹出窗口
     *
     * @return
     */
    private void initBuyNow(List<RefundReasonBean> refundReasonBeans) {
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
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_shopcancle_discount, null);
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
        CauseRecyclerAdapter adapter=new CauseRecyclerAdapter();
        adapter.setNewData(refundReasonBeans);
        mRvCauseRecycler.setAdapter(adapter);
        mRvCauseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    public static void start(String type) {

        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        ActivityUtils.startActivity(bundle, ShopordersendetailsrefundActivity.class);

    }


    @OnClick({R.id.iv_header_back})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                finish();
                break;


        }
    }
}
