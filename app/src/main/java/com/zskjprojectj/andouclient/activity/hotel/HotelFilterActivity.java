package com.zskjprojectj.andouclient.activity.hotel;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.Catagory1Adapter;
import com.zskjprojectj.andouclient.adapter.hotel.Catagory2Adapter;
import com.zskjprojectj.andouclient.adapter.hotel.Catagory3Adapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelResultAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.SectionAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.hotel.Category1Bean;
import com.zskjprojectj.andouclient.entity.hotel.HotelResultBean;
import com.zskjprojectj.andouclient.entity.hotel.Mysection;
import com.zskjprojectj.andouclient.utils.GridSectionAverageGapItemDecoration;

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
    private ArrayList<HotelResultBean> mDataList;
    private LinearLayout mPriceStar;
    private RecyclerView mViewRecycler;
    private Button mCancle;
    private ArrayList<Mysection> mData;
    private PopupWindow mPopWindow;


    @BindView(R.id.ll_classify)
    LinearLayout mClassify;

    @BindView(R.id.tv_selector_star)
    TextView mSelectorStar;

    @BindView(R.id.tv_capacity_sort)
    TextView mCapacitySort;

    @BindView(R.id.header_title)
    LinearLayout mHeaderTitle;

    @BindView(R.id.tv_location)
    TextView mLocation;
    private RecyclerView mCatagory2;
    private RecyclerView mCatagory1;
    private RecyclerView mCatagory3;
    private Catagory2Adapter catagory2Adapter;
    private Catagory3Adapter catagory3Adapter;
    private Catagory1Adapter catagory1Adapter;
    private ArrayList<Category1Bean> datalist1;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_filter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HotelResultBean databean = new HotelResultBean();
            databean.setHotelName("精尚来公寓酒店");
            mDataList.add(databean);
        }

        HotelResultAdapter adapter = new HotelResultAdapter(R.layout.hotelresuilt_item_view, mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HotelFilterActivity.this, HotelDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initViews() {
        getBarDistance(mHeaderTitle);
        mRecycler = findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.ll_selector_location, R.id.ll_price_star, R.id.ll_selector_sort, R.id.ll_selector_screen})
    public void clickSelector(View v) {
        switch (v.getId()) {
            //位置区域
            case R.id.ll_selector_location:
//                initLocation();
//                if (mPopWindow != null && !mPopWindow.isShowing()) {
//                    mPopWindow.showAsDropDown(mClassify, 0, 0);
//                }
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

    }

    private void initPriceStar() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_star_price, null);

        initPopuWindow(contentView, mSelectorStar);

        mViewRecycler = contentView.findViewById(R.id.rv_recycler);
        mCancle = contentView.findViewById(R.id.bt_cancle);
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
            }
        });
    }

    private void initDialogRecycler() {
        mViewRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mViewRecycler.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 10, 0, 10));

        mData = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mData.add(new Mysection(true, "价格"));
            mData.add(new Mysection("¥0~100"));
            mData.add(new Mysection("¥100~200"));
            mData.add(new Mysection("¥200~300"));
            mData.add(new Mysection("¥300~400"));
            mData.add(new Mysection("¥400~500"));
            mData.add(new Mysection("¥500~600"));
            mData.add(new Mysection("¥600以上"));
            mData.add(new Mysection(true, "星级"));
            mData.add(new Mysection("经济型"));
            mData.add(new Mysection("舒适三星"));
            mData.add(new Mysection("高档四星"));
            mData.add(new Mysection("豪华五星"));
        }
        SectionAdapter sectionAdapteradapter = new SectionAdapter(R.layout.item_section_content, R.layout.def_section_head, mData);
        mViewRecycler.setAdapter(sectionAdapteradapter);


        sectionAdapteradapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//                sectionAdapteradapter.onChange(position);
//                sectionAdapteradapter.notifyDataSetChanged();


                if (0 < position && position < 8) {
                    sectionAdapteradapter.onChange1(position);
                    sectionAdapteradapter.notifyDataSetChanged();
                } else if (8 < position && position < 13) {
                    sectionAdapteradapter.onChange2(position);
                    sectionAdapteradapter.notifyDataSetChanged();
                }


            }
        });

        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionAdapteradapter.cancle(-1);
                sectionAdapteradapter.notifyDataSetChanged();

            }
        });


    }

    private void initLocation() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_location, null);
        initPopuWindow(contentView, mLocation);

        datalist1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Category1Bean databean = new Category1Bean();
            databean.setContent("A");
            databean.setContent("B");
            databean.setContent("C");
            databean.setContent("D");
            for (int j = 0; j < 5; j++) {
                Category1Bean.Category2Bean dataBean2 = new Category1Bean.Category2Bean();
                dataBean2.setContent2("日");
                dataBean2.setContent2("神");
                dataBean2.setContent2("牛");
                dataBean2.setContent2("逼");
                for (int k = 0; k < 5; k++) {
                    Category1Bean.Category2Bean.Category3Bean dataBean3 = new Category1Bean.Category2Bean.Category3Bean();
                    dataBean3.setContent3("日");
                    dataBean3.setContent3("神");
                    dataBean3.setContent3("牛");
                    dataBean3.setContent3("逼");

                    dataBean2.getCategory3Beans().add(dataBean3);
                }

                databean.getCategory2Beans().add(dataBean2);
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
                catagory2Adapter.select(position);

            }
        });

    }

    private void initCatagory2(View view) {
        mCatagory2 = view.findViewById(R.id.catagory2);
        mCatagory2.setLayoutManager(new LinearLayoutManager(this));

        catagory2Adapter = new Catagory2Adapter(R.layout.catagory2_item_view, datalist1);
        catagory2Adapter.openLoadAnimation();
        mCatagory2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mCatagory2.setAdapter(catagory2Adapter);

        catagory2Adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                catagory2Adapter.select(position);
                catagory3Adapter.select(position);
            }
        });
    }

    private void initCatagory3(View view) {
        mCatagory3 = view.findViewById(R.id.catagory3);
        mCatagory3.setLayoutManager(new LinearLayoutManager(this));


        catagory3Adapter = new Catagory3Adapter(R.layout.catagory3_item_view, datalist1);
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

}
