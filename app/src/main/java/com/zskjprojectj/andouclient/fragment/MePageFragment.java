package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.BrowsingActivity;
import com.zskjprojectj.andouclient.activity.BusinessresidenceActivity;
import com.zskjprojectj.andouclient.activity.DownloadappActivity;
import com.zskjprojectj.andouclient.activity.FoodorderActivity;
import com.zskjprojectj.andouclient.activity.HotelorderActivity;
import com.zskjprojectj.andouclient.activity.InvitationActivity;
import com.zskjprojectj.andouclient.activity.MesettingActivity;
import com.zskjprojectj.andouclient.activity.MyFocusonActivity;
import com.zskjprojectj.andouclient.activity.MyaddressActivity;
import com.zskjprojectj.andouclient.activity.MycollectionActivity;
import com.zskjprojectj.andouclient.activity.MymessageActivity;
import com.zskjprojectj.andouclient.activity.MyreleaseActivity;
import com.zskjprojectj.andouclient.activity.MyscoreActivity;
import com.zskjprojectj.andouclient.activity.MywalletActivity;
import com.zskjprojectj.andouclient.activity.OpeningmemberActivity;
import com.zskjprojectj.andouclient.activity.OperationvideoActivity;
import com.zskjprojectj.andouclient.activity.PlatformshoppingcartActivity;
import com.zskjprojectj.andouclient.activity.RestaurantOrderListActivity;
import com.zskjprojectj.andouclient.activity.ShoporderActivity;
import com.zskjprojectj.andouclient.activity.VegetableMarketActivity;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.utils.UrlUtil;import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.PersonalBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import butterknife.BindView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 * 我的界面
 *
 * @author yizhubao
 */
public class MePageFragment extends BaseFragment {

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitle;


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
    private LinearLayout mycenter_shoppingcart_layout;
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
    //我的关注
    private LinearLayout mycenter_myfocuson_layout;
    //二维码
    private LinearLayout mycenter_qrcode_layout;
    //我的发布
    private LinearLayout mycenter_releas_layout;
    //设置界面
    private ImageView img_meset;
    //个人信息
    private ImageView img_touxiang,iv_message;
    private TextView tv_nickname, tv_viplevel, tv_collectionnum, tv_focusonnum, tv_browsenum, tv_moneynum, tv_integralnumm;
    private ImageView img_hotelnum,img_mallnum,img_restaurantnum;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mycenter_vegetablemarket_layout = view.findViewById(R.id.mycenter_vegetablemarket_layout);
        mycenter_foodorder_layout = view.findViewById(R.id.mycenter_foodorder_layout);
        mycenter_hotelorder_layout = view.findViewById(R.id.mycenter_hotelorder_layout);
        mycenter_shoporder_layout = view.findViewById(R.id.mycenter_shoporder_layout);
        mycenter_invitation_with_courtesy_layout = view.findViewById(R.id.mycenter_invitation_with_courtesy_layout);
        mycenter_mywallet_layout = view.findViewById(R.id.mycenter_mywallet_layout);
        mycenter_myrelease_layout = view.findViewById(R.id.mycenter_myrelease_layout);
        mycenter_shoppingcart_layout = view.findViewById(R.id.mycenter_shoppingcart_layout);
        mycenter_mycollection_layout = view.findViewById(R.id.mycenter_mycollection_layout);
        mycenter_browsing_layout = view.findViewById(R.id.mycenter_browsing_layout);
        mycenter_myaddress_layout = view.findViewById(R.id.mycenter_myaddress_layout);
        mycenter_myscore_layout = view.findViewById(R.id.mycenter_myscore_layout);
        mycenter_mymessage_layout = view.findViewById(R.id.mycenter_mymessage_layout);
        mycenter_business_residence_layout = view.findViewById(R.id.mycenter_business_residence_layout);
        mycenter_downloadapp_layout = view.findViewById(R.id.mycenter_downloadapp_layout);
        mycenter_operationvideo_layout = view.findViewById(R.id.mycenter_operationvideo_layout);
        mycenter_restaurant_layout = view.findViewById(R.id.mycenter_restaurant_layout);
        mycenter_myfocuson_layout = view.findViewById(R.id.mycenter_myfocuson_layout);
        mycenter_qrcode_layout=view.findViewById(R.id.mycenter_qrcode_layout);
        mycenter_releas_layout=view.findViewById(R.id.mycenter_releas_layout);
        img_meset = view.findViewById(R.id.img_meset);
        img_touxiang = view.findViewById(R.id.img_touxiang);
        tv_nickname = view.findViewById(R.id.tv_nickname);
        tv_viplevel = view.findViewById(R.id.tv_viplevel);
        tv_collectionnum = view.findViewById(R.id.tv_collectionnum);
        tv_focusonnum = view.findViewById(R.id.tv_focusonnum);
        tv_browsenum = view.findViewById(R.id.tv_browsenum);
        tv_moneynum = view.findViewById(R.id.tv_moneynum);
        tv_integralnumm = view.findViewById(R.id.tv_integralnumm);
        iv_message=view.findViewById(R.id.iv_message);
        img_hotelnum=view.findViewById(R.id.img_hotelnum);
        img_mallnum=view.findViewById(R.id.img_mallnum);
        img_restaurantnum=view.findViewById(R.id.img_restaurantnum);

        //设置状态栏的高度
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mHeaderTitle.getLayoutParams();
        layoutParams.topMargin = BarUtils.getStatusBarHeight(mAty);
        mHeaderTitle.setLayoutParams(layoutParams);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mepage;
    }

    @Override
    protected void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().getpersonal(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(new BaseObserver<PersonalBean>(mAty) {
            @Override
            public void onHandleSuccess(PersonalBean personalBean) throws IOException {
                tv_viplevel.setText(personalBean.getGrade());
                tv_nickname.setText(personalBean.getName());
                tv_collectionnum.setText(personalBean.getCollect());
                tv_focusonnum.setText(personalBean.getFocus());
                tv_browsenum.setText(personalBean.getRecord());
                tv_moneynum.setText(personalBean.getMoney());
                tv_integralnumm.setText(personalBean.getIntegral());
                int numhotel=Integer.parseInt( personalBean.getBooksordernum());
                int numfood=Integer.parseInt(personalBean.getFoodsordernum());
                int nummall=Integer.parseInt(personalBean.getGoodordernum());
                new QBadgeView(getContext()).bindTarget(img_hotelnum).setBadgeNumber(numhotel).setBadgeTextSize(8,true).setBadgeGravity(Gravity.END|Gravity.TOP).setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (STATE_SUCCEED == dragState){
                            badge.hide(true);
                        }
                    }
                });
                new QBadgeView(getContext()).bindTarget(img_mallnum).setBadgeNumber(nummall).setBadgeTextSize(8,true).setBadgeGravity(Gravity.END|Gravity.TOP).setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (STATE_SUCCEED == dragState){
                            badge.hide(true);
                        }
                    }
                });
                new QBadgeView(getContext()).bindTarget(img_restaurantnum).setBadgeNumber(numfood).setBadgeTextSize(8,true).setBadgeGravity(Gravity.END|Gravity.TOP).setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (STATE_SUCCEED == dragState){
                            badge.hide(true);
                        }
                    }
                });
                Glide.with(mAty).load(UrlUtil.getImageUrl(personalBean.getAvator())).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(img_touxiang);
            }
        });
    }

    @Override
    protected void initData() {

        /**
         * 开通Vip
         */
        img_touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OpeningmemberActivity.class));
            }
        });
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
         * 我的二维码
         */
        mycenter_qrcode_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("功能暂未开通敬请期待....");
            }
        });
        /**
         * 我的发布
         */
        mycenter_releas_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("功能暂未开通敬请期待....");
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
                // ToastUtil.showToast("数据链接异常");
                startActivity(new Intent(getContext(), ShoporderActivity.class));
            }
        });
        /**
         * 饭店订单
         */
        mycenter_restaurant_layout.setOnClickListener(view ->
                ActivityUtils.startActivity(RestaurantOrderListActivity.class));
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
        iv_message.setOnClickListener(new View.OnClickListener() {
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
        /**
         * 关注
         */
        mycenter_myfocuson_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MyFocusonActivity.class));
            }
        });
        /**
         * 设置界面跳转
         */
        img_meset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MesettingActivity.class));
            }
        });
    }
}
