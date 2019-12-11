package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.BrowsingActivity;
import com.zskjprojectj.andouclient.activity.BusinessresidenceActivity;
import com.zskjprojectj.andouclient.activity.DownloadappActivity;
import com.zskjprojectj.andouclient.activity.FoodorderActivity;
import com.zskjprojectj.andouclient.activity.HotelorderActivity;
import com.zskjprojectj.andouclient.activity.InvitationActivity;
import com.zskjprojectj.andouclient.activity.MyaddressActivity;
import com.zskjprojectj.andouclient.activity.MycollectionActivity;
import com.zskjprojectj.andouclient.activity.MymessageActivity;
import com.zskjprojectj.andouclient.activity.MyreleaseActivity;
import com.zskjprojectj.andouclient.activity.MyscoreActivity;
import com.zskjprojectj.andouclient.activity.MywalletActivity;
import com.zskjprojectj.andouclient.activity.OperationvideoActivity;
import com.zskjprojectj.andouclient.activity.PlatformshoppingcartActivity;
import com.zskjprojectj.andouclient.activity.RestaurantOrderActivity;
import com.zskjprojectj.andouclient.activity.ShoporderActivity;
import com.zskjprojectj.andouclient.activity.VegetableMarketActivity;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.utils.ToastUtil;
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
    //购物车
    private  LinearLayout mycenter_shoppingcart_layout;
    //我的收藏
    private LinearLayout mycenter_mycollection_layout;
    //浏览痕迹
    private LinearLayout mycenter_browsing_layout;
    //我的地址
    private LinearLayout mycenter_myaddress_layout;
    //我的积分
    private LinearLayout mycenter_myscore_layout;
    //我的消息
    private LinearLayout mycenter_mymessage_layout;
    //商家入驻
    private LinearLayout mycenter_business_residence_layout;
    //app下载
    private LinearLayout mycenter_downloadapp_layout;
    //操作视频
    private LinearLayout mycenter_operationvideo_layout;
    //饭店预订
    private LinearLayout mycenter_restaurant_layout;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mycenter_vegetablemarket_layout=view.findViewById(R.id.mycenter_vegetablemarket_layout);
        mycenter_foodorder_layout=view.findViewById(R.id.mycenter_foodorder_layout);
        mycenter_hotelorder_layout=view.findViewById(R.id.mycenter_hotelorder_layout);
        mycenter_shoporder_layout=view.findViewById(R.id.mycenter_shoporder_layout);
        mycenter_invitation_with_courtesy_layout=view.findViewById(R.id.mycenter_invitation_with_courtesy_layout);
        mycenter_mywallet_layout=view.findViewById(R.id.mycenter_mywallet_layout);
        mycenter_myrelease_layout=view.findViewById(R.id.mycenter_myrelease_layout);
        mycenter_shoppingcart_layout=view.findViewById(R.id.mycenter_shoppingcart_layout);
        mycenter_mycollection_layout=view.findViewById(R.id.mycenter_mycollection_layout);
        mycenter_browsing_layout=view.findViewById(R.id.mycenter_browsing_layout);
        mycenter_myaddress_layout=view.findViewById(R.id.mycenter_myaddress_layout);
        mycenter_myscore_layout=view.findViewById(R.id.mycenter_myscore_layout);
        mycenter_mymessage_layout=view.findViewById(R.id.mycenter_mymessage_layout);
        mycenter_business_residence_layout=view.findViewById(R.id.mycenter_business_residence_layout);
        mycenter_downloadapp_layout=view.findViewById(R.id.mycenter_downloadapp_layout);
        mycenter_operationvideo_layout=view.findViewById(R.id.mycenter_operationvideo_layout);
        mycenter_restaurant_layout=view.findViewById(R.id.mycenter_restaurant_layout);
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
                ToastUtil.showToast("数据链接异常");
                //startActivity(new Intent(getContext(), ShoporderActivity.class));
            }
        });
        /**
         * 饭店订单
         */
        mycenter_restaurant_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("数据链接异常");
                //startActivity(new Intent(getContext(), RestaurantOrderActivity.class));
            }
        });
        /**
         * 邀请有礼
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
        /**
         * 购物车
         */
        mycenter_shoppingcart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PlatformshoppingcartActivity.class));
            }
        });
        /**
         * 我的收藏
         */
        mycenter_mycollection_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MycollectionActivity.class));
            }
        });
        /**
         * 浏览痕迹
         */
        mycenter_browsing_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BrowsingActivity.class));
            }
        });
        /**
         * 我的地址
         */
        mycenter_myaddress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyaddressActivity.class));
            }
        });
        /**
         * 我的积分
         */
        mycenter_myscore_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyscoreActivity.class));
            }
        });
        /**
         * 我的消息
         */
        mycenter_mymessage_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MymessageActivity.class));
            }
        });
        /**
         * 商家入驻
         */
        mycenter_business_residence_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BusinessresidenceActivity.class));
            }
        });
        /**
         * App下载
         */
        mycenter_downloadapp_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DownloadappActivity.class));
            }
        });
        /**
         * 操作视频
         */
        mycenter_operationvideo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OperationvideoActivity.class));
            }
        });
    }
}
