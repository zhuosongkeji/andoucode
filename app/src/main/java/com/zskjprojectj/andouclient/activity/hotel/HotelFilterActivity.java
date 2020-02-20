package com.zskjprojectj.andouclient.activity.hotel;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.Catagory1Adapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelPriceAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelResultAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelStarAdapter;
import com.zskjprojectj.andouclient.entity.hotel.CategoryBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelSearchConditionBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.GridSectionAverageGapItemDecoration;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 酒店列表
 * Bin
 * 2019/12/6
 */
public class HotelFilterActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private LinearLayout mPriceStar;
    private RecyclerView mPriceRecycler;
    private RecyclerView mStarRecycler;
    private Button mCancle;
    private Button mConfirm;
    private PopupWindow mPopWindow;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.ll_classify)
    LinearLayout mClassify;

    @BindView(R.id.tv_selector_star)
    TextView mSelectorStar;

    @BindView(R.id.tv_capacity_sort)
    TextView mCapacitySort;


    @BindView(R.id.tv_location)
    TextView mLocation;

    @BindView(R.id.header_title)
    LinearLayout mRootView;

    @BindView(R.id.edit_search)
    EditText mSearch;
    private RecyclerView mCatagory2;
    private RecyclerView mCatagory1;
    private RecyclerView mCatagory3;
    private Catagory1Adapter catagory2Adapter;
    private Catagory1Adapter catagory3Adapter;
    private Catagory1Adapter catagory1Adapter;
    private ArrayList<CategoryBean> datalist1;

    private HotelPriceAdapter priceAdapter;
    private HotelStarAdapter starAdapter;
    //价格区间最小值
    private String startPrice;

    //价格区间最大值
    private String endPrice;
    //酒店星级
    private String hotelStar;
    //排序方式(不是必传 1按距离,2按点价格)
    private String type;
    //关键字搜索
    private String keywords;
    //酒店ID
    private String hotelId;
    HotelResultAdapter adapter = new HotelResultAdapter();
    private PageLoadUtil<HotelHomeBean> pageLoadUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.blankj.utilcode.util.BarUtils.transparentStatusBar(mActivity);
        com.blankj.utilcode.util.BarUtils.setNavBarLightMode(mActivity,false);
        int barHeight = com.blankj.utilcode.util.BarUtils.getStatusBarHeight();
        if (barHeight > 0) {
            //设置状态栏的高度
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRootView.getLayoutParams();
            layoutParams.topMargin = BarUtils.getStatusBarHeight(mActivity) + layoutParams.topMargin;
            mRootView.setLayoutParams(layoutParams);
        }
        initViews();
        initData();
    }

    private void initData() {


        pageLoadUtil = PageLoadUtil.get(mActivity, mRecycler, adapter, mRefreshLayout);
        pageLoadUtil.load(() -> ApiUtils.getApiService().hotelHomeList(
                keywords,
                startPrice,
                endPrice,
                hotelStar,
                type,
                pageLoadUtil.page));
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> HotelDetailActivity.start(adapter.getItem(position).getId()));

    }

    private void initViews() {
        hotelId = getIntent().getStringExtra("hotelId");
        mRecycler = findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }


    @OnClick({R.id.btn_clear, R.id.ll_selector_location, R.id.ll_price_star, R.id.ll_selector_sort, R.id.ll_selector_screen})
    public void clickSelector(View v) {
        switch (v.getId()) {

            //搜索
            case R.id.btn_clear:
                String content = mSearch.getText().toString().trim();
                pageLoadUtil.load(() -> ApiUtils.getApiService().hotelHomeList(
                        content,
                        startPrice,
                        endPrice,
                        hotelStar,
                        type,
                        pageLoadUtil.page));
                break;

            //位置区域
            case R.id.ll_selector_location:
                initLocation();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            //价格星级
            case R.id.ll_price_star:
                mSelectorStar.setTextColor(getResources().getColor(R.color.colorNavy));
                initPriceStar();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            //智能排序
            case R.id.ll_selector_sort:
                initSort();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            //筛选
            case R.id.ll_selector_screen:
                initScreen();
                break;
        }
    }

    private void initScreen() {

    }

    private void initSort() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_sort, null);

        initPopuWindow(contentView, mCapacitySort);
        //不限
        contentView.findViewById(R.id.tv_unlimited).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageLoadUtil.load(() -> ApiUtils.getApiService().hotelHomeList(
                        keywords,
                        startPrice,
                        endPrice,
                        hotelStar,
                        type,
                        pageLoadUtil.page));
                mPopWindow.dismiss();
            }
        });
        //距离
        contentView.findViewById(R.id.tv_distance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageLoadUtil.load(() -> ApiUtils.getApiService().hotelHomeList(
                        keywords,
                        startPrice,
                        endPrice,
                        hotelStar,
                        "1",
                        pageLoadUtil.page));
                mPopWindow.dismiss();
            }
        });
        //价格
        contentView.findViewById(R.id.tv_price).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageLoadUtil.load(() -> ApiUtils.getApiService().hotelHomeList(
                        keywords,
                        startPrice,
                        endPrice,
                        hotelStar,
                        "2",
                        pageLoadUtil.page));
                mPopWindow.dismiss();
            }
        });


    }

    private void initPriceStar() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_star_price, null);

        initPopuWindow(contentView, mSelectorStar);
        //价格
        mPriceRecycler = contentView.findViewById(R.id.rv_price_recycler);
        //星级
        mStarRecycler = contentView.findViewById(R.id.rv_star_recycler);
        mCancle = contentView.findViewById(R.id.bt_cancle);
        mConfirm = contentView.findViewById(R.id.confirm);
        initDialogRecycler();
    }

    //初始化popuwindow
    private void initPopuWindow(View contentView, TextView textView) {
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
                textView.setTextColor(getResources().getColor(R.color.color_common_font));
            }
        });
    }

    private void initDialogRecycler() {
        //价格
        mPriceRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mPriceRecycler.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 10, 0, 10));
        //星级
        mStarRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mStarRecycler.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 10, 0, 10));

        HttpRxObservable.getObservable(ApiUtils.getApiService().hotelSearchCondition()).subscribe(new BaseObserver<HotelSearchConditionBean>(mActivity) {
            @Override
            public void onHandleSuccess(HotelSearchConditionBean hotelSearchConditionBean) throws IOException {
                //价格
                priceAdapter = new HotelPriceAdapter(R.layout.item_section_content, hotelSearchConditionBean.getPrice_range());
                mPriceRecycler.setAdapter(priceAdapter);
                priceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        priceAdapter.onChange(position);
                        priceAdapter.notifyDataSetChanged();
                    }
                });
                priceAdapter.setOnItemGetContent(new HotelPriceAdapter.onItemGetContent() {
                    @Override
                    public void content(String star_price, String end_price) {
                        startPrice = star_price;
                        endPrice = end_price;
                    }
                });


                //星级
                starAdapter = new HotelStarAdapter(R.layout.item_section_content, hotelSearchConditionBean.getStar());
                mStarRecycler.setAdapter(starAdapter);
                starAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        starAdapter.onChange(position);
                        starAdapter.notifyDataSetChanged();
                    }
                });

                starAdapter.setOnItemGetContent(new HotelStarAdapter.onItemGetContent() {
                    @Override
                    public void content(String content) {
                        hotelStar = content;
                    }
                });


            }
        });


        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceAdapter.cancle(-1);
                priceAdapter.notifyDataSetChanged();
                starAdapter.cancle(-1);
                starAdapter.notifyDataSetChanged();

            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageLoadUtil.load(() -> ApiUtils.getApiService().hotelHomeList(
                        keywords,
                        startPrice,
                        endPrice,
                        hotelStar,
                        type,
                        pageLoadUtil.page));

                mPopWindow.dismiss();
            }
        });


    }

    private void initLocation() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_location, null);
        initPopuWindow(contentView, mLocation);

        datalist1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CategoryBean databean = new CategoryBean();
            databean.setContent("一级" + i);
            for (int j = 0; j < 5; j++) {
                CategoryBean dataBean2 = new CategoryBean();
                dataBean2.setContent("二级" + i + j);
                for (int k = 0; k < 5; k++) {
                    CategoryBean dataBean3 = new CategoryBean();
                    dataBean3.setContent("三级" + i + j + k);
                    dataBean2.categories.add(dataBean3);
                }
                databean.categories.add(dataBean2);
            }
            datalist1.add(databean);
        }

//        ArrayList<Group> one = new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            Group group1 =new Group("一级"+i);
//            for (int j = 0; j < 12; j++) {
//                Group group2 = new Group("二级"+j);
//                for (int k = 0; k < 12; k++) {
//                    Group group3 = new Group("三级"+k);
//                    group2.items.add(group3);
//                }
//                group1.items.add(group2);
//            }
//            one.add(group1);
//        }
//
//
//    }
//
//    class Group {
//        private  String title;
//
//        public Group(String title) {
//            this.title = title;
//        }
//
//        ArrayList<Group> items = new ArrayList<>();

        initCatagory1(contentView);
        initCatagory2(contentView);
        initCatagory3(contentView);
    }

    private void initCatagory1(View view) {
        mCatagory1 = view.findViewById(R.id.catagory1);
        mCatagory1.setLayoutManager(new LinearLayoutManager(this));
        catagory1Adapter = new Catagory1Adapter(R.layout.catagory1_item_view, datalist1);
        catagory1Adapter.openLoadAnimation();
        mCatagory1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCatagory1.setAdapter(catagory1Adapter);

        catagory1Adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                catagory1Adapter.select(position);
                catagory2Adapter.setNewData(catagory1Adapter.getItem(position).categories);
                catagory2Adapter.select(0);
                catagory3Adapter.setNewData(catagory1Adapter.getItem(position).categories.get(0).categories);
                catagory3Adapter.select(0);
            }
        });

    }

    private void initCatagory2(View view) {
        mCatagory2 = view.findViewById(R.id.catagory2);
        mCatagory2.setLayoutManager(new LinearLayoutManager(this));

        catagory2Adapter = new Catagory1Adapter(R.layout.catagory1_item_view, datalist1.get(0).categories);
        catagory2Adapter.openLoadAnimation();
        mCatagory2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCatagory2.setAdapter(catagory2Adapter);

        catagory2Adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                catagory2Adapter.select(position);
                catagory3Adapter.setNewData(catagory2Adapter.getItem(position).categories);
                catagory3Adapter.select(0);
            }
        });
    }

    private void initCatagory3(View view) {
        mCatagory3 = view.findViewById(R.id.catagory3);
        mCatagory3.setLayoutManager(new LinearLayoutManager(this));


        catagory3Adapter = new Catagory1Adapter(R.layout.catagory1_item_view, datalist1.get(0).categories.get(0).categories);
        catagory3Adapter.openLoadAnimation();
        mCatagory3.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCatagory3.setAdapter(catagory3Adapter);

        catagory3Adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                catagory3Adapter.select(position);
            }
        });
    }

    @OnClick(R.id.iv_header_back)
    public void clickBack() {
        finish();
    }


    public static void start(String hotelId) {

        Bundle bundle = new Bundle();
        bundle.putString("hotelId", hotelId);
        ActivityUtils.startActivity(bundle, HotelFilterActivity.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_hotel_filter;
    }
}
