package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.adapter.ClassificationofgoodsAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsListBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 分类页详细
 */
public class ClassificationofgoodsActivity extends BaseActivity {

    private static final String CATAID = "cataId";

    @BindView(R.id.ll_classify)
    LinearLayout mClassify;
    private PopupWindow mPopWindow;

    @BindView(R.id.root_view)
    RelativeLayout mRootView;
    private RecyclerView mRecycler;
    //搜索内容
    @BindView(R.id.search_edittext)
    EditText mSearchEditText;

    //综合
    @BindView(R.id.tv_selector_star)
    TextView mTvSelectorStar;
    @BindView(R.id.iv_selector_star_img)
    ImageView mIvSelectorImg;

    //销量
    @BindView(R.id.tv_capacity_sort)
    TextView mTvCapacitySort;
    @BindView(R.id.iv_capacity_sort)
    ImageView mIvCapacitySort;

    //价格
    @BindView(R.id.tv_screen)
    TextView mTvScreen;
    @BindView(R.id.iv_screen)
    ImageView mIvScreen;

    //关键字
    private String keyword;
    //分类id
    private String cataId;
    //推荐产品
    private String is_recommend;
    //特价产品
    private String is_bargain;
    //价格排序
    private String price_sort;
    //销量排序
    private String volume_sort;


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_classificationofgoods);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        cataId = getIntent().getStringExtra(CATAID);
    }

    @Override
    protected void initViews() {
        getBarDistance(mRootView);
        mRecycler = findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
    }

    //初始化popuwindow
    private void initPopuWindow(View contentView, TextView textView, ImageView imageView) {
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);

        //设置背景色
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.8f;
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
                textView.setTextColor(getResources().getColor(R.color.color_common_font));
                imageView.setImageResource(R.mipmap.ic_busiess_xiala);
            }
        });
    }

    @Override
    public void getDataFromServer() {
        //推荐id
        is_recommend = getIntent().getStringExtra("recommend");
        //特价id
        is_bargain = getIntent().getStringExtra("special");

        HttpRxObservable.getObservable(ApiUtils.getApiService().mallGoodsList(
                keyword,
                cataId,
                is_recommend,
                is_bargain,
                price_sort,
                volume_sort

        )).subscribe(new BaseObserver<List<MallGoodsListBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<MallGoodsListBean> mallGoodsListBeans) throws IOException {

                ClassificationofgoodsAdapter adapter = new ClassificationofgoodsAdapter(R.layout.item_classificationofgoods, mallGoodsListBeans);
                adapter.openLoadAnimation();
                mRecycler.setAdapter(adapter);

                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        String shippingId = mallGoodsListBeans.get(position).getId();
                        Log.d(TAG, "onItemClick: " + shippingId);
                        MallGoodsDetailsActivity.start(shippingId);

                    }
                });

            }
        });
    }

    @OnClick({R.id.ll_price_comprehensive, R.id.ll_selector_sales, R.id.ll_selector_price, R.id.search_image})
    public void clickSelector(View v) {
        switch (v.getId()) {
            //综合
            case R.id.ll_price_comprehensive:
                initComprehensive();
                mTvSelectorStar.setTextColor(android.graphics.Color.parseColor("#5ED3AE"));
                mIvSelectorImg.setImageResource(R.mipmap.icon_cate_img);
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            //销量
            case R.id.ll_selector_sales:
                initsales();
                mTvCapacitySort.setTextColor(android.graphics.Color.parseColor("#5ED3AE"));
                mIvCapacitySort.setImageResource(R.mipmap.icon_cate_img);
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            //价格
            case R.id.ll_selector_price:
                initprice();
                mTvScreen.setTextColor(android.graphics.Color.parseColor("#5ED3AE"));
                mIvScreen.setImageResource(R.mipmap.icon_cate_img);
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            //搜索按钮
            case R.id.search_image:
                keyword = mSearchEditText.getText().toString();
                getDataFromServer();
                break;
        }
    }

    /**
     * 综合排序
     */
    private void initComprehensive() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_shop_comprehensive, null);
        initPopuWindow(contentView, mTvSelectorStar, mIvSelectorImg);
        //不限
        TextView mTvUnlimited = contentView.findViewById(R.id.tv_popu_unlimited);
        mTvUnlimited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromServer();
                mPopWindow.dismiss();
            }
        });
        //销量
        TextView mTvCredit = contentView.findViewById(R.id.tv_popu_credit);
        //价格
        TextView mTvPrice = contentView.findViewById(R.id.tv_popu_price);

    }

    /**
     * 销量排序
     *
     * @return
     */
    private void initsales() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_shop_sales, null);
        initPopuWindow(contentView, mTvCapacitySort, mIvCapacitySort);
        //销量不限
        TextView mVolumUnlimited = contentView.findViewById(R.id.tv_volum_unlimited);
        mVolumUnlimited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromServer();
                mPopWindow.dismiss();
            }
        });
        //销量升
        TextView mVolumUp = contentView.findViewById(R.id.tv_volum_up);
        mVolumUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volume_sort = "0";
                getDataFromServer();
                mPopWindow.dismiss();
            }
        });
        //销量降
        TextView mVolumDeline = contentView.findViewById(R.id.tv_volum_decline);
        mVolumDeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volume_sort = "1";
                getDataFromServer();
                mPopWindow.dismiss();
            }
        });
    }

    /**
     * 价格排序
     *
     * @return
     */
    private void initprice() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_shop_price, null);
        initPopuWindow(contentView, mTvScreen, mIvScreen);
        //价格不限
        TextView mPriceUnlimeted = contentView.findViewById(R.id.tv_price_unlimited);
        mPriceUnlimeted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromServer();
                mPopWindow.dismiss();
            }
        });
        //价格升
        TextView mPriceUp = contentView.findViewById(R.id.tv_price_up);
        mPriceUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price_sort = "0";
                getDataFromServer();
                mPopWindow.dismiss();
            }
        });
        //价格降
        TextView mPriceDecline = contentView.findViewById(R.id.tv_price_decline);
        mPriceDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price_sort = "1";
                getDataFromServer();
                mPopWindow.dismiss();
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.img_back)
    public void clickBack() {
        finish();
    }

    public static void getCataId(String cataId) {
        Bundle bundle=new Bundle();
        bundle.putString(CATAID,cataId);
        ActivityUtils.startActivity(bundle,ClassificationofgoodsActivity.class);
    }


}
