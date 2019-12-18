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

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

public class HotelordercancleActivity extends BaseActivity {
   // private ImageView img_chooserefundreason;
    private LinearLayout ly_chooserefundreason;
    private Dialog bottomDialog;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelordercancle);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("申请退款");
    }

    @Override
    protected void initViews() {
        ly_chooserefundreason=findViewById(R.id.ly_chooserefundreason);
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
     * @return
     */
     private  void initBuyNow()
     {
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
}
