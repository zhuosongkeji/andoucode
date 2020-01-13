package com.zskjprojectj.andouclient.activity.restaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallPaySuccessActivity;
import com.zskjprojectj.andouclient.adapter.restaurant.DateAdapter;
import com.zskjprojectj.andouclient.entity.WXPayBean;
import com.zskjprojectj.andouclient.event.RestaurantPaySuccess;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PayUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

public class RestaurantBillActivity extends BaseActivity {

    @BindView(R.id.foodContainer)
    ViewGroup foodContainer;
    @BindView(R.id.timeTxt)
    TextView timeTxt;
    @BindView(R.id.numTxt)
    TextView numTxt;
    @BindView(R.id.moreBtn)
    View moreBtn;
    @BindView(R.id.psEdt)
    EditText psEdt;
    DateAdapter adapter = new DateAdapter();
    private int num;

    @BindView(R.id.wxPayBtn)
    View wxPayBtn;
    @BindView(R.id.accountPayBtn)
    View accountPayBtn;

    private String dateStr;
    private String timeStr;
    private Restaurant restaurant;

    private static final ArrayList<String> times = new ArrayList<>();

    static {
        for (int i = 0; i < 25; i++) {
            if (i > 9) {
                times.add(i + ":00");
            } else {
                times.add("0" + i + ":00");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "提交预订");
        adapter.bindToRecyclerView(findViewById(R.id.dateRecyclerView));
        adapter.setOnItemClickListener((adapter1, view, position) -> {
                    Date date = adapter.getItem(position);
                    adapter.setSelected(date, true, true);
                    dateStr = TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));
                }
        );
        timeTxt.setOnClickListener(v -> {
            OptionsPickerView<String> pvOptions =
                    new OptionsPickerBuilder(mActivity, (options1, option2, options3, v1) -> {
                        timeStr = times.get(options1);
                        timeTxt.setText(timeStr);
                    }).setDividerColor(Color.TRANSPARENT).build();
            pvOptions.setPicker(times);
            pvOptions.setSelectOptions(11);
            pvOptions.show();
        });
        restaurant = (Restaurant) getIntent().getSerializableExtra(KEY_DATA);
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getCart(LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        restaurant.id),
                result -> {
                    int totalNum = 0;
                    BigDecimal totalAmount = new BigDecimal(0);
                    for (Food food : result.data) {
                        totalNum += food.num;
                        totalAmount = totalAmount.add(new BigDecimal(food.getAmount()));
                    }
                    ViewUtil.setText(mActivity, R.id.cartCountTxt, String.valueOf(totalNum));
                    ViewUtil.setText(mActivity, R.id.totalAmountTxt1, FormatUtil.getMoneyString(totalAmount.doubleValue()));
                    ViewUtil.setText(mActivity, R.id.totalAmountTxt2, FormatUtil.getMoneyString(totalAmount.doubleValue()));
                    ViewUtil.setText(mActivity, R.id.totalAmountTxt3, FormatUtil.getMoneyString(totalAmount.doubleValue()));
                    for (int i = 0; i < result.data.size(); i++) {
                        if (i >= 3) {
                            break;
                        }
                        addFoodView(result.data.get(i));
                    }
                    moreBtn.setVisibility(result.data.size() > 3 ? View.VISIBLE : View.GONE);
                    moreBtn.setOnClickListener(v -> {
                        for (int i = 0; i < result.data.size(); i++) {
                            if (i >= 3) {
                                addFoodView(result.data.get(i));
                            }
                        }
                        moreBtn.setVisibility(View.GONE);
                    });
                });
        ViewUtil.setText(mActivity, R.id.nameTxt, restaurant.name);
        Glide.with(mActivity)
                .load(UrlUtil.getImageUrl(restaurant.logo_img))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) findViewById(R.id.logoImg));
        adapter.setNewData(getNextSevenDate());
        wxPayBtn.setSelected(true);
        wxPayBtn.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            accountPayBtn.setSelected(!wxPayBtn.isSelected());
        });
        accountPayBtn.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            wxPayBtn.setSelected(!wxPayBtn.isSelected());
        });
    }

    private List<Date> getNextSevenDate() {
        ArrayList<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dates.add(calendar.getTime());
        }
        return dates;
    }

    private void addFoodView(Food cartFood) {
        View foodView = LayoutInflater.from(mActivity).inflate(R.layout.layout_bill_list_item, null);
        ViewUtil.setText(foodView, R.id.nameTxt, cartFood.name);
        ViewUtil.setText(foodView, R.id.countTxt, String.valueOf(cartFood.num));
        ViewUtil.setText(foodView, R.id.amountTxt, FormatUtil.getMoneyString(cartFood.getAmount()));
        Glide.with(mActivity)
                .load(UrlUtil.getImageUrl(cartFood.image))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) foodView.findViewById(R.id.img));
        foodContainer.addView(foodView);
    }

    @OnClick(R.id.addBtn)
    void onAddBtnClick() {
        num += 1;
        numTxt.setText(String.valueOf(num));
    }

    @OnClick(R.id.subBtn)
    void onSubBtnClick() {
        if (num <= 0) return;
        num -= 1;
        numTxt.setText(String.valueOf(num));
    }

    @OnClick(R.id.payBtn)
    void onPayBtn() {
        String people = numTxt.getText().toString();
        if (TextUtils.isEmpty(dateStr)) {
            ToastUtil.showToast("请选择预约日期");
        } else if (TextUtils.isEmpty(timeStr)) {
            ToastUtil.showToast("请选择预约时间");
        } else if ("0".equals(people)) {
            ToastUtil.showToast("请选择就餐人数");
        } else {
            if (getPayWay() == 4) {
                new AlertDialog.Builder(mActivity)
                        .setTitle("温馨提示")
                        .setMessage("确定用余额支付该订单吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定",
                                (dialog, which) -> RequestUtil.request(mActivity, true, false,
                                        () -> ApiUtils.getApiService().payBill(
                                                LoginInfoUtil.getUid(),
                                                restaurant.id,
                                                people,
                                                psEdt.getText().toString(),
                                                dateStr + " " + timeStr,
                                                0,
                                                getPayWay()
                                        ), result -> {
                                            finish();
                                            if (getPayWay() == 1) {
                                                WXPayBean wxPayBean = new Gson().fromJson(result.data, WXPayBean.class);
                                                PayUtil.startWXPay(mActivity, wxPayBean);
                                            } else if (getPayWay() == 4) {
                                                ActivityUtils.startActivity(MallPaySuccessActivity.class);
                                                EventBus.getDefault().post(new RestaurantPaySuccess());
                                            }
                                        }))
                        .show();
            }
        }
    }

    private int getPayWay() {
        int payWay = 0;
        if (findViewById(R.id.wxPayBtn).isSelected()) {
            payWay = 1;
        } else if (findViewById(R.id.accountPayBtn).isSelected()) {
            payWay = 4;
        }
        return payWay;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_bill;
    }

    public static void start(Activity activity, Restaurant restaurant, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, restaurant);
        ActivityUtils.startActivityForResult(bundle, activity, RestaurantBillActivity.class, requestCode);
    }
}
