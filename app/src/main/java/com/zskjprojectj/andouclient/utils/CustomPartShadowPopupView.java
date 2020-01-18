package com.zskjprojectj.andouclient.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.zskjprojectj.andouclient.R;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.utils
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/17 16:28
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CustomPartShadowPopupView extends PartShadowPopupView {
    private int type;

    public CustomPartShadowPopupView(@NonNull Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_shop_comprehensive;
    }

    TextView mPopu_unlimited;
    TextView tv_popu_credit;
    TextView tv_popu_price;

    @Override
    protected void onCreate() {
        super.onCreate();

        //不限
        mPopu_unlimited = findViewById(R.id.tv_popu_unlimited);
        //评价升序排列
        tv_popu_credit = findViewById(R.id.tv_popu_credit);
        //评价降序排列
        tv_popu_price = findViewById(R.id.tv_popu_price);

        switch (type) {
            case 1:
                mPopu_unlimited.setText("不限");
                tv_popu_credit.setText("评价升序排列");
                tv_popu_price.setText("评价降序排列");
                break;
            case 2:
                mPopu_unlimited.setText("不限");
                tv_popu_credit.setText("销量升序排列");
                tv_popu_price.setText("销量降序排列");
                break;
            case 3:
                mPopu_unlimited.setText("不限");
                tv_popu_credit.setText("价格升序排列");
                tv_popu_price.setText("价格降序排列");
                break;
        }

    }





}
