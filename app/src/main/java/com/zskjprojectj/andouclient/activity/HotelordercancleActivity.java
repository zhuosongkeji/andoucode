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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.yhao.floatwindow.FloatWindow;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelordercancleActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    private LinearLayout ly_chooserefundreason;
    private Dialog bottomDialog;
    private String merchant_id;
    private String id;
    private String book_sn;

    @OnClick(R.id.iv_header_back)
    public void clickView(){
        finish();
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

        ly_chooserefundreason = findViewById(R.id.ly_chooserefundreason);
        ly_chooserefundreason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBuyNow();
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
    private void initBuyNow() {
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
//        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public static void start(String merchant_id, String id, String book_sn) {

        Bundle bundle = new Bundle();
        bundle.putString("merchant_id", merchant_id);
        bundle.putString("id", id);
        bundle.putString("book_sn", book_sn);
        ActivityUtils.startActivity(bundle, HotelordercancleActivity.class);
    }
}
