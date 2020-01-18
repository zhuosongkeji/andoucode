package com.zskjprojectj.andouclient.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yhao.floatwindow.FloatWindow;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.CauseRecyclerAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.RefundReasonBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.model.OrderDetail;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

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

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;

    @BindView(R.id.img_picleft)
    ImageView mPicLeft;

    @BindView(R.id.tv_goods_name)
    TextView mGoodsName;

    @BindView(R.id.tv_goods_details)
    TextView mGoodsDetails;

    @BindView(R.id.tv_goods_price)
    TextView mGoodsPrice;

    @BindView(R.id.tv_goods_num)
    TextView mGoodsNum;

    @BindView(R.id.tv_cause)
    TextView mCause;

    @BindView(R.id.tv_tui_price)
    TextView mTuiPrice;
    @BindView(R.id.et_dec)
    EditText mEtDec;


    private RelativeLayout select_cause;
    private Dialog bottomDialog;
    private String type;
    private OrderDetail.Goodsdetail goodsdetail;
    private String reasonId;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_shopordersendetailsrefund);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        getBarDistance(mTitleView);
        if ("sales_return".equals(type)) {
            mHeaderTitle.setText("申请退货");
        } else if ("refund".equals(type)) {
            mHeaderTitle.setText("申请退款");
        }
    }

    @Override
    protected void initViews() {
        goodsdetail = (OrderDetail.Goodsdetail) getIntent().getSerializableExtra("details");

        Glide.with(mAt).load(UrlUtil.getImageUrl(goodsdetail.img)).apply(new RequestOptions()
                .error(R.drawable.default_image).placeholder(R.drawable.default_image)).into(mPicLeft);
        mGoodsDetails.setText(getSpec(goodsdetail.attr_value));
        mGoodsName.setText(goodsdetail.name);
        mGoodsPrice.setText("¥" + goodsdetail.price);
        mTuiPrice.setText("¥" + goodsdetail.price);
        mGoodsNum.setText("X" + goodsdetail.num);
        select_cause = findViewById(R.id.select_cause);
        select_cause.setOnClickListener(new View.OnClickListener() {
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

    private String getSpec(String[] attr_value) {
        StringBuilder builder = new StringBuilder();
        for (String s : attr_value) {
            builder.append(s).append("+");
        }
        return builder.substring(0, builder.length() - 1);
    }

    @Override
    public void getDataFromServer() {
        String content = mEtDec.getText().toString().trim();
        HttpRxObservable.getObservable(ApiUtils.getApiService().mallrefund(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                goodsdetail.id,
                reasonId,
                content,
                ""
        )).subscribe(new BaseObserver<Object>(mAt) {
            @Override
            public void onHandleSuccess(Object o) throws IOException {

            }
        });

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
        CauseRecyclerAdapter adapter = new CauseRecyclerAdapter();
        adapter.setNewData(refundReasonBeans);
        mRvCauseRecycler.setAdapter(adapter);
        mRvCauseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.setOnItemContent(new CauseRecyclerAdapter.OnItemContent() {
            @Override
            public void content(String content,String reason_id) {
                reasonId=reason_id;
                mCause.setText(content);
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


    public static void start(String type, OrderDetail.Goodsdetail goodsdetail) {

        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("details", goodsdetail);
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
