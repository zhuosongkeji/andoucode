package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MainActivity;
import com.zskjprojectj.andouclient.adapter.hotel.HotelCateGoryAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelHomeAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.SectionAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.hotel.HotelCategoryBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeBean;
import com.zskjprojectj.andouclient.entity.hotel.Mysection;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.GridSectionAverageGapItemDecoration;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 酒店预约
 * bin
 * 2019/12/5
 */
public class HotelActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv_check_in_time)
    RelativeLayout mCheckInTime;
    @BindView(R.id.tv_check_in_time)
    TextView mTvCheckInTime;
    @BindView(R.id.rv_check_out_time)
    RelativeLayout mCheckOutTime;
    @BindView(R.id.tv_check_out_time)
    TextView mTVCheckOutTime;
    @BindView(R.id.et_input_number)
    EditText mInputNumber;

    @BindView(R.id.rv_hotel_category)
    RecyclerView mHotelCategory;


    private RecyclerView mRvRecycler;
    private LinearLayout mTitle, mSearchHotel;
    private ArrayList<HotelHomeBean> dataList;
    private View contentView;
    private Dialog bottomDialog;
    private RecyclerView mViewRecycler;
    private ArrayList<Mysection> mData;
    private Button mCancle;
    private HotelCateGoryAdapter adapter=new HotelCateGoryAdapter();

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HotelHomeBean databean = new HotelHomeBean();
            databean.setName("精尚来公寓酒店");
            dataList.add(databean);

        }

        HotelHomeAdapter adapter = new HotelHomeAdapter(R.layout.activity_hotel_home_view, dataList);
        adapter.openLoadAnimation();
        mRvRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HotelActivity.this, HotelDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initViews() {

        mInputNumber.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        mTitle = findViewById(R.id.titlt_view);
        mRvRecycler = findViewById(R.id.rv_recycler);
        mRvRecycler.setLayoutManager(new LinearLayoutManager(this));
        //设置状态栏的高度
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTitle.getLayoutParams();
        layoutParams.topMargin = BarUtils.getStatusBarHeight(this);
        mTitle.setLayoutParams(layoutParams);

        mSearchHotel = findViewById(R.id.search_hotel);
        mSearchHotel.setOnClickListener(this);

        mTvCheckInTime.setText(getNowTime());
        mTVCheckOutTime.setText(getNowTime());

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHotelCategory.setLayoutManager(layoutManager);
        mHotelCategory.setAdapter(adapter);


    }

    private String getNowTime() {

        Date now = new Date(); // 创建一个Date对象，获取当前时间
        // 指定格式化格式
        SimpleDateFormat f = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        // 将当前时间袼式化为指定的格式
        return f.format(now);
    }

    @Override
    public void getDataFromServer() {

        HttpRxObservable.getObservable(ApiUtils.getApiService().hotelCategory()).subscribe(new BaseObserver<List<HotelCategoryBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<HotelCategoryBean> hotelCategoryBeans) throws IOException {
                adapter.setNewData(hotelCategoryBeans);
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_hotel:
                Intent searchTotelIntent = new Intent(HotelActivity.this, HotelFilterActivity.class);
                startActivity(searchTotelIntent);
                break;
        }
    }

    @OnClick({R.id.rv_check_in_time, R.id.rv_check_out_time, R.id.tv_set_like})
    public void clickCheckIn(View view) {
        switch (view.getId()) {
            case R.id.rv_check_in_time:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(HotelActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        if (mTvCheckInTime != null) {
                            mTvCheckInTime.setText("");
                            mTvCheckInTime.setText(getTime(date));
                        }
                    }
                }).build();

                pvTime.show();
                break;
            case R.id.rv_check_out_time:
                //时间选择器
                TimePickerView pvTimeq = new TimePickerBuilder(HotelActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        if (mTVCheckOutTime != null) {
                            mTVCheckOutTime.setText("");
                            mTVCheckOutTime.setText(getTime(date));
                        }
                    }
                })
                        .build();

                pvTimeq.show();
                break;

            case R.id.tv_set_like:
                showDialog();
                break;
        }
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
//        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
        return format.format(date);
    }


    private void showDialog() {

        bottomDialog = new Dialog(this, R.style.BottomDialog);
        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_star_price, null);
        mViewRecycler = contentView.findViewById(R.id.rv_recycler);
        mCancle = contentView.findViewById(R.id.bt_cancle);
        initDialogRecycler();
        bottomDialog.setContentView(contentView);

        Window window = bottomDialog.getWindow();
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置宽度
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);


        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

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

    @OnClick(R.id.busiess_back_image)
    public void clickBack() {
        finish();
    }

}
