package com.zskjprojectj.andouclient.activity.hotel;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.HotelCateGoryAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelHomeAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelPriceAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelStarAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.hotel.HotelCategoryBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelSearchConditionBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.GridSectionAverageGapItemDecoration;

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
    private View contentView;
    private Dialog bottomDialog;
    private RecyclerView mPriceRecycler;
    private RecyclerView mStarRecycler;
    private Button mCancle;
    private HotelCateGoryAdapter adapter=new HotelCateGoryAdapter();
    private HotelHomeAdapter hoteladapter = new HotelHomeAdapter();
    private HotelPriceAdapter priceAdapter;
    private HotelStarAdapter starAdapter;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        HttpRxObservable.getObservable(ApiUtils.getApiService().hotelHomeList()).subscribe(new BaseObserver<HotelHomeBean>(mAt) {
            @Override
            public void onHandleSuccess(HotelHomeBean hotelHomeBean) throws IOException {

                List<HotelHomeBean.MerchantsBean> merchants = hotelHomeBean.getMerchants();

                hoteladapter.setNewData(merchants);
                hoteladapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        HotelDetailActivity.start(merchants.get(position).getId());
                    }
                });
            }
        });


        hoteladapter.openLoadAnimation();
        mRvRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvRecycler.setAdapter(hoteladapter);



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
        //弹窗 价格展示
        mPriceRecycler = contentView.findViewById(R.id.rv_price_recycler);
        //弹窗 星级展示
        mStarRecycler = contentView.findViewById(R.id.rv_star_recycler);
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
        //价格
        mPriceRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mPriceRecycler.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 10, 0, 10));
        //星级
        mStarRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mStarRecycler.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 10, 0, 10));

        HttpRxObservable.getObservable(ApiUtils.getApiService().hotelSearchCondition()).subscribe(new BaseObserver<HotelSearchConditionBean>(mAt) {
            @Override
            public void onHandleSuccess(HotelSearchConditionBean hotelSearchConditionBean) throws IOException {
                //价格
                priceAdapter = new HotelPriceAdapter(R.layout.item_section_content, hotelSearchConditionBean.getPrice_range());
                mPriceRecycler.setAdapter(priceAdapter);
                //星级
                starAdapter = new HotelStarAdapter(R.layout.item_section_content, hotelSearchConditionBean.getStar());
                mStarRecycler.setAdapter(starAdapter);
            }
        });




        priceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


//                if (0 < position && position < 8) {
//                    priceAdapter.onChange1(position);
//                    priceAdapter.notifyDataSetChanged();
//                } else if (8 < position && position < 13) {
//                    priceAdapter.onChange2(position);
//                    priceAdapter.notifyDataSetChanged();
//                }


            }
        });

        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                priceAdapter.cancle(-1);
//                priceAdapter.notifyDataSetChanged();

            }
        });


    }

    @OnClick(R.id.busiess_back_image)
    public void clickBack() {
        finish();
    }

}
