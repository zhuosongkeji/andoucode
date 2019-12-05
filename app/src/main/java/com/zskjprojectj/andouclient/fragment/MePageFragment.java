package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.FoodorderActivity;
import com.zskjprojectj.andouclient.activity.HotelorderActivity;
import com.zskjprojectj.andouclient.activity.InvitationActivity;
import com.zskjprojectj.andouclient.activity.MyreleaseActivity;
import com.zskjprojectj.andouclient.activity.MywalletActivity;
import com.zskjprojectj.andouclient.activity.ShoporderActivity;
import com.zskjprojectj.andouclient.activity.VegetableMarketActivity;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.view.TopView;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 *我的界面
 * @author yizhubao
 */
public class MePageFragment extends BaseFragment {
    private TopView topView;
    //菜市场
    private LinearLayout mycenter_vegetablemarket_layout;
    //美食订单
    private LinearLayout mycenter_foodorder_layout;
    //酒店预订
    private LinearLayout mycenter_hotelorder_layout;
    //商城订单
    private LinearLayout mycenter_shoporder_layout;
    //分享有礼
    private LinearLayout mycenter_invitation_with_courtesy_layout;
    //我的钱包
    private LinearLayout mycenter_mywallet_layout;
    //我的发布
    private LinearLayout mycenter_myrelease_layout;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mycenter_vegetablemarket_layout=view.findViewById(R.id.mycenter_vegetablemarket_layout);
        mycenter_foodorder_layout=view.findViewById(R.id.mycenter_foodorder_layout);
        mycenter_hotelorder_layout=view.findViewById(R.id.mycenter_hotelorder_layout);
        mycenter_shoporder_layout=view.findViewById(R.id.mycenter_shoporder_layout);
        mycenter_invitation_with_courtesy_layout=view.findViewById(R.id.mycenter_invitation_with_courtesy_layout);
        mycenter_mywallet_layout=view.findViewById(R.id.mycenter_mywallet_layout);
        mycenter_myrelease_layout=view.findViewById(R.id.mycenter_myrelease_layout);
        topView=view.findViewById(R.id.alltopview);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mepage;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        topView.setTitle("会员中心");
        /**
         * 菜市场
         */
        mycenter_vegetablemarket_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), VegetableMarketActivity.class));
            }
        });
        /**
         * 美食订单
         */
        mycenter_foodorder_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FoodorderActivity.class));
            }
        });
        /**
         * 酒店预订
         */
        mycenter_hotelorder_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HotelorderActivity.class));
            }
        });
        /**
         * 商城订单
         */
        mycenter_shoporder_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ShoporderActivity.class));
            }
        });
        /**
         * 分享有礼
         */
        mycenter_invitation_with_courtesy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InvitationActivity.class));
            }
        });
        /**
         *我的钱包
         */
        mycenter_mywallet_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MywalletActivity.class));
            }
        });
        /**
         * 我的发布
         */
        mycenter_myrelease_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyreleaseActivity.class));
            }
        });
    }
}
