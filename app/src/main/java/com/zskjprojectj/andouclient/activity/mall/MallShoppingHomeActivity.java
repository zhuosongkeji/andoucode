package com.zskjprojectj.andouclient.activity.mall;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.MallShoppingHomeAdapter;
import com.zskjprojectj.andouclient.adapter.mall.MallShoppingPopuAdapter;
import com.zskjprojectj.andouclient.utils.CustomPartShadowPopupView;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingHomeBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MallShoppingHomeActivity extends BaseActivity {

    //商户名字
    @BindView(R.id.tv_shopping_name)
    TextView mTvShoppingName;
    //商户背景
    @BindView(R.id.iv_shopping_background)
    ImageView mIvShoppingBackground;
    //商户头像
    @BindView(R.id.iv_shopping_headpic)
    ImageView mIvShoppingHeadpic;

    @BindView(R.id.recyclerView)
    RecyclerView mRecycler;

    @BindView(R.id.ll_mall_shopping_classify)
    LinearLayout mShoppingClassify;

    //店铺关注
    @BindView(R.id.tv_mall_merchants_focuson)
    TextView mtvMallMerchantsFocuson;
    //d店铺图标
    @BindView(R.id.iv_isfocuson)
    ImageView ivisfocuson;
    //搜索框输入内容
    @BindView(R.id.mSearchEditText)
    EditText mSearchEdittext;

    //销量
    @BindView(R.id.tv_volume)
    TextView mTvVolume;

    //销量图标
    @BindView(R.id.iv_volume)
    ImageView mIvVolume;

    //价格
    @BindView(R.id.tv_price)
    TextView mTvPrice;

    //价格图标
    @BindView(R.id.iv_price)
    ImageView mIvPrice;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rl_root_view)
    RelativeLayout mRootView;


    private RecyclerView mRecyclerPopu;
    private Button mConfirm;
    private PopupWindow mPopWindow;
    private String merchantId;
    private boolean volumeflag = false;
    private boolean priceflag = false;

    //关键字查询
    private String keyword;
    //分类id查询
    private String type_id;
    //价格排序(非必传1为倒序,0为正序)
    private String price_sort = "0";
    //销量排序(非必传1为倒序,0为正序)
    private String volume_sort = "0";
    private List<MallShoppingHomeBean.TypeBean> typeBeanList;
    private boolean isfocuson = false;
    private String type;
    MallShoppingHomeAdapter adapter = new MallShoppingHomeAdapter();
    private PageLoadUtil<MallShoppingHomeBean.GoodsBean> pageLoadUtil;
    private CustomPartShadowPopupView popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.transparentStatusBar(mActivity);
        int barHeight = com.blankj.utilcode.util.BarUtils.getStatusBarHeight();
        if (barHeight > 0) {
            //设置状态栏的高度
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRootView.getLayoutParams();
            layoutParams.topMargin = BarUtils.getStatusBarHeight(mActivity) + layoutParams.topMargin;
            mRootView.setLayoutParams(layoutParams);
        }
        initViews();
        getDataFromServer();
    }


    private void initViews() {
        merchantId = getIntent().getStringExtra("merchant_id");
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));

        HttpRxObservable.getObservable(ApiUtils.getApiService().mallMerchant(
                merchantId,
                LoginInfoUtil.getUid(),
                keyword,
                type_id,
                price_sort,
                volume_sort,
                1

        )).subscribe(new BaseObserver<MallShoppingHomeBean>(this) {
            @Override
            public void onHandleSuccess(MallShoppingHomeBean mallShoppingHomeBean) throws IOException {
                mTvShoppingName.setText(mallShoppingHomeBean.getName());
                Glide.with(MallShoppingHomeActivity.this).load(UrlUtil.getImageUrl(mallShoppingHomeBean.getLogo_img()))
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_head_photo).error(R.mipmap.ic_default_head_photo))
                        .into(mIvShoppingHeadpic);
                if ("0".equals(mallShoppingHomeBean.getStatus())) {
                    mtvMallMerchantsFocuson.setText("关注");
                    isfocuson = false;
                    ivisfocuson.setVisibility(View.VISIBLE);
                } else {
                    mtvMallMerchantsFocuson.setText("已关注");
                    isfocuson = true;
                    ivisfocuson.setVisibility(View.GONE);
                }
                Glide.with(MallShoppingHomeActivity.this).load(UrlUtil.getImageUrl(mallShoppingHomeBean.getBanner_img())).into(mIvShoppingBackground);
                typeBeanList = mallShoppingHomeBean.getType();
            }
        });

    }


    private void getDataFromServer() {

        pageLoadUtil = PageLoadUtil.get(mActivity, mRecycler, adapter, mRefreshLayout);
        pageLoadUtil.load(() -> ApiUtils.getApiService().mallMerchant(
                merchantId,
                LoginInfoUtil.getUid(),
                keyword,
                type_id,
                price_sort,
                volume_sort,
                pageLoadUtil.page
        ));
        adapter.openLoadAnimation();
        adapter.setOnItemClickListener((adapter1, view, position) ->
                MallGoodsDetailsActivity.start(adapter.getItem(position).getId(),"PUTONG"));

    }


    /**
     * 分类
     */
    @OnClick(R.id.ll_mall_shopping_classify)
    public void clickClassify() {
        //设置contentView
        View contentView = LayoutInflater.from(MallShoppingHomeActivity.this).inflate(R.layout.activity_mall_shopping_classify_view, null);

        mRecyclerPopu = contentView.findViewById(R.id.recyclerView);
        mConfirm = contentView.findViewById(R.id.confirm);
        initRecycler();
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置背景色
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.8f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);


        //popupWindow获取焦点
        mPopWindow.setFocusable(true);
        mPopWindow.setAnimationStyle(R.style.popmenu_animation); //动画
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //设置popupWindow消失时的监听
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        //显示方式
        mPopWindow.showAsDropDown(mShoppingClassify, 0, 0);

    }

    private void initRecycler() {
        mRecyclerPopu.setLayoutManager(new GridLayoutManager(this, 2));
        MallShoppingPopuAdapter adapter1 = new MallShoppingPopuAdapter(R.layout.activity_mall_shopping_popu_view, typeBeanList);
        mRecyclerPopu.setAdapter(adapter1);
        //重置选项菜单
        adapter1.onChange(-1);
        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                adapter1.onChange(position);
                adapter1.notifyDataSetChanged();
            }
        });

        adapter1.setItemListener(new MallShoppingPopuAdapter.OnItemListener() {
            @Override
            public void getContentId(String contentId) {
                type_id = contentId;
            }
        });


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                getDataFromServer();

                pageLoadUtil.load(() -> ApiUtils.getApiService().mallMerchant(
                        merchantId,
                        LoginInfoUtil.getUid(),
                        keyword,
                        type_id,
                        price_sort,
                        volume_sort,
                        pageLoadUtil.page
                ));
                mPopWindow.dismiss();

            }
        });

    }


    @OnClick({R.id.ll_sale_volume,R.id.busiess_back_image, R.id.ll_price_star, R.id.iv_Goods_search, R.id.mall_merchants_focuson})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.busiess_back_image:
                finish();
                break;
            //销量
            case R.id.ll_sale_volume:
//                price_sort = "0";
//                if (!volumeflag) {
//                    volume_sort = "1";
//                    getDataFromServer();

//                    volumeflag = true;
//                } else {
//                    volume_sort = "2";
//                    getDataFromServer();
//                    volumeflag = false;
//                }

                mTvVolume.setTextColor(android.graphics.Color.parseColor("#5ED3AE"));
                mIvVolume.setImageResource(R.mipmap.icon_cate_img);
                if (popupView == null) {
                    popupView = (CustomPartShadowPopupView) new XPopup.Builder(mActivity)
                            .atView(view)
                            .autoOpenSoftInput(false)
                            .setPopupCallback(new SimpleCallback() {
                                @Override
                                public void onShow() {

                                }

                                @Override
                                public void onDismiss() {
                                    mTvVolume.setTextColor(getResources().getColor(R.color.color_common_font));
                                    mIvVolume.setImageResource(R.mipmap.ic_busiess_xiala);
                                    popupView = null;
                                }
                            })
                            .asCustom(new CustomPartShadowPopupView(mActivity, 2));
                    popupView.show();

                    popupView.setOnclickItem(new CustomPartShadowPopupView.OnclickItem() {
                        @Override
                        public void itemView(String sort) {

                            pageLoadUtil.load(() -> ApiUtils.getApiService().mallMerchant(
                                    merchantId,
                                    LoginInfoUtil.getUid(),
                                    keyword,
                                    type_id,
                                    price_sort,
                                    sort,
                                    pageLoadUtil.page
                            ));
                        }
                    });


                } else if (popupView != null && popupView.isShow()) {
                    popupView.dismiss();
                }

                break;
            //价格
            case R.id.ll_price_star:
//                volume_sort = "0";
//                if (!priceflag) {
//                    price_sort = "1";
//                    getDataFromServer();
//                    priceflag = true;
//                } else {
//                    price_sort = "2";
//                    getDataFromServer();
//                    priceflag = false;
//                }

                mTvPrice.setTextColor(android.graphics.Color.parseColor("#5ED3AE"));
                mIvPrice.setImageResource(R.mipmap.icon_cate_img);
                if (popupView == null) {
                    popupView = (CustomPartShadowPopupView) new XPopup.Builder(mActivity)
                            .atView(view)
                            .autoOpenSoftInput(false)
                            .setPopupCallback(new SimpleCallback() {
                                @Override
                                public void onShow() {

                                }

                                @Override
                                public void onDismiss() {
                                    mTvPrice.setTextColor(getResources().getColor(R.color.color_common_font));
                                    mIvPrice.setImageResource(R.mipmap.ic_busiess_xiala);
                                    popupView = null;
                                }
                            })
                            .asCustom(new CustomPartShadowPopupView(mActivity, 3));
                    popupView.show();

                    popupView.setOnclickItem(new CustomPartShadowPopupView.OnclickItem() {
                        @Override
                        public void itemView(String sort) {

                            pageLoadUtil.load(() -> ApiUtils.getApiService().mallMerchant(
                                    merchantId,
                                    LoginInfoUtil.getUid(),
                                    keyword,
                                    type_id,
                                    sort,
                                    volume_sort,
                                    pageLoadUtil.page
                            ));
                        }
                    });


                } else if (popupView != null && popupView.isShow()) {
                    popupView.dismiss();
                }
                break;

            case R.id.iv_Goods_search:

                String searchContent = mSearchEdittext.getText().toString().trim();
                keyword = "";
                keyword = searchContent;
//                getDataFromServer();

                pageLoadUtil.load(() -> ApiUtils.getApiService().mallMerchant(
                        merchantId,
                        LoginInfoUtil.getUid(),
                        keyword,
                        type_id,
                        price_sort,
                        volume_sort,
                        pageLoadUtil.page
                ));

                break;
            case R.id.mall_merchants_focuson:
                if (!isfocuson) {
                    mtvMallMerchantsFocuson.setText("已关注");
                    ivisfocuson.setVisibility(View.GONE);
                    isfocuson = true;
                    type = "1";
                } else {
                    mtvMallMerchantsFocuson.setText("关注");
                    ivisfocuson.setVisibility(View.VISIBLE);
                    type = "0";
                    isfocuson = false;
                }
                HttpRxObservable.getObservable(ApiUtils.getApiService().mallgoodsfollow(merchantId
                        ,
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        type
                )).subscribe(new BaseObserver<Object>(this) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {

                    }
                });
                break;
        }
    }


    public static void start(String merchant_id) {
        Bundle bundle = new Bundle();
        bundle.putString("merchant_id", merchant_id);
        ActivityUtils.startActivity(bundle, MallShoppingHomeActivity.class);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mall_shopping_home;
    }
}
