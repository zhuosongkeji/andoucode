package com.zskjprojectj.andouclient.activity.restaurant;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zhuosongkj.android.library.model.ListData;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.DateAdapter;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingHomeBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

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
    DateAdapter adapter = new DateAdapter();
    private int num;

    @BindView(R.id.wxPayBtn)
    View wxPayBtn;
    @BindView(R.id.accountPayBtn)
    View accountPayBtn;

    private static final ArrayList<String> times = new ArrayList<>();

    static {
        for (int i = 24; i >= 0; i--) {
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
        adapter.setOnItemClickListener((adapter1, view, position) ->
                adapter.setSelected(adapter.getItem(position), true, true));
        timeTxt.setOnClickListener(v -> {
            OptionsPickerView<String> pvOptions = new OptionsPickerBuilder(mActivity, (options1, option2, options3, v1) ->
                    timeTxt.setText(times.get(options1)))
                    .setDividerColor(Color.TRANSPARENT)
                    .build();
            pvOptions.setPicker(times);
            pvOptions.show();
        });
        Bill bill = (Bill) getIntent().getSerializableExtra(KEY_DATA);
        ViewUtil.setText(mActivity, R.id.nameTxt, bill.restaurant.name);
        Glide.with(mActivity)
                .load(UrlUtil.getImageUrl(bill.restaurant.logo_img))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) findViewById(R.id.logoImg));
        for (int i = 0; i < bill.cartFoods.size(); i++) {
            if (i >= 3) {
                break;
            }
            addFoodView(bill.cartFoods.get(i));
        }
        moreBtn.setVisibility(bill.cartFoods.size() > 3 ? View.VISIBLE : View.GONE);
        moreBtn.setOnClickListener(v -> {
            for (int i = 0; i < bill.cartFoods.size(); i++) {
                if (i >= 3) {
                    addFoodView(bill.cartFoods.get(i));
                }
            }
            moreBtn.setVisibility(View.GONE);
        });
        adapter.setNewData(getNextSevenDate());
        wxPayBtn.setEnabled(false);
        wxPayBtn.setOnClickListener(v -> {
            v.setEnabled(v.isEnabled());
            accountPayBtn.setEnabled(wxPayBtn.isEnabled());
        });
        accountPayBtn.setOnClickListener(v -> {
            v.setEnabled(v.isEnabled());
            wxPayBtn.setEnabled(wxPayBtn.isEnabled());
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

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_bill;
    }

    public static void start(Activity activity, Restaurant restaurant, List<Food> cartFoods, int requestCode) {
        Bundle bundle = new Bundle();
        Bill bill = new Bill(restaurant, cartFoods);
        bundle.putSerializable(KEY_DATA, bill);
        ActivityUtils.startActivityForResult(bundle, activity, RestaurantBillActivity.class, requestCode);
    }

    static class Bill implements Serializable {
        public Bill(Restaurant restaurant, List<Food> cartFoods) {
            this.restaurant = restaurant;
            this.cartFoods = cartFoods;
        }

        public Restaurant restaurant;
        public List<Food> cartFoods;
    }
}
