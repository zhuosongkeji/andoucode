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
public class CustomPartShadowPopupView extends PartShadowPopupView  {
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
        mPopu_unlimited = findViewById(R.id.mPopuunlimited);
        //评价升序排列
        tv_popu_credit = findViewById(R.id.mPopucredit);
        //评价降序排列
        tv_popu_price = findViewById(R.id.mPopuprice);

        switch (type) {
            //评价
            case 1:
                mPopu_unlimited.setText("不限");
                mPopu_unlimited.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclickItem!=null){
                            onclickItem.itemView("");
                            dismiss();
                        }
                    }
                });
                tv_popu_credit.setText("评价升序排列");
                tv_popu_credit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclickItem!=null){
                            onclickItem.itemView("0");
                            dismiss();
                        }
                    }
                });
                tv_popu_price.setText("评价降序排列");
                tv_popu_price.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclickItem!=null){
                            onclickItem.itemView("1");
                            dismiss();
                        }
                    }
                });
                break;
                //销量
            case 2:
                mPopu_unlimited.setText("不限");
                mPopu_unlimited.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclickItem!=null){
                            onclickItem.itemView("");
                            dismiss();
                        }
                    }
                });
                tv_popu_credit.setText("销量升序排列");
                tv_popu_credit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclickItem!=null){
                            onclickItem.itemView("0");
                            dismiss();
                        }
                    }
                });
                tv_popu_price.setText("销量降序排列");
                tv_popu_price.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclickItem!=null){
                            onclickItem.itemView("1");
                            dismiss();
                        }
                    }
                });
                break;
                //价格
            case 3:
                mPopu_unlimited.setText("不限");
                mPopu_unlimited.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclickItem!=null){
                            onclickItem.itemView("");
                            dismiss();
                        }
                    }
                });
                tv_popu_credit.setText("价格升序排列");
                tv_popu_credit.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclickItem!=null){
                            onclickItem.itemView("0");
                            dismiss();
                        }
                    }
                });
                tv_popu_price.setText("价格降序排列");
                tv_popu_price.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onclickItem!=null){
                            onclickItem.itemView("1");
                            dismiss();
                        }
                    }
                });
                break;
        }

    }



    public interface OnclickItem{
        void itemView(String sort);
    }

    private OnclickItem onclickItem;

    public void setOnclickItem(OnclickItem onclickItem) {
        this.onclickItem = onclickItem;
    }
}
