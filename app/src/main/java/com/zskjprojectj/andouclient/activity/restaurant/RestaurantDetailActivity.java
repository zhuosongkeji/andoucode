package com.zskjprojectj.andouclient.activity.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zhuosongkj.android.library.util.RecyclerViewUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.RestaurantOrderListActivity;
import com.zskjprojectj.andouclient.adapter.restaurant.CartAdapter;
import com.zskjprojectj.andouclient.fragment.FoodListFragment;
import com.zskjprojectj.andouclient.fragment.RestaurantInfoFragment;
import com.zskjprojectj.andouclient.fragment.ReviewListFragment;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.MapUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zskjprojectj.andouclient.utils.ConstantKt.KEY_DATA;
import static com.zskjprojectj.andouclient.utils.ConstantKt.REQUEST_CODE_LOGIN;

public class RestaurantDetailActivity extends BaseActivity {

    private CartAdapter cartAdapter = new CartAdapter();

    @BindView(R.id.fixContainer)
    View fixContainer;

    @BindView(R.id.cartContainer)
    View cartContainer;

    @BindView(R.id.cartRecyclerView)
    RecyclerView cartRecyclerView;

    @BindView(R.id.cartDialogContainer)
    View cartDialogContainer;
    FoodListFragment foodListFragment;

    String id;

    @BindView(R.id.collectBtn)
    View collectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.transparentStatusBar(mActivity);
        BarUtils.setStatusBarLightMode(mActivity, false);
        ActionBarUtil.setTitle(mActivity, "商家详情", false);
        ActionBarUtil.getBackground(mActivity, false).setAlpha(0);
        cartDialogContainer.setOnClickListener(v -> toggleCartContainer());
        cartContainer.setOnClickListener(v -> {
        });
        ((AppBarLayout) findViewById(R.id.appBarLayout))
                .addOnOffsetChangedListener((appBarLayout, verticalOffset) ->
                        ActionBarUtil.getBackground(mActivity, false)
                                .setAlpha(Math.abs(verticalOffset) * 0.01f));
        fixContainer.setPadding(fixContainer.getPaddingLeft(), BarUtils.getStatusBarHeight(), fixContainer.getPaddingEnd(), 0);
        id = getIntent().getStringExtra(KEY_DATA);
        loadCart();
        RecyclerViewUtil.disableItemAnimator(cartRecyclerView);
        cartAdapter.bindToRecyclerView(cartRecyclerView);
        cartAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Food food = cartAdapter.getItem(position);
            if (view.getId() == R.id.subBtn) {
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().changeFoodCart(
                                LoginInfoUtil.getUid(),
                                id,
                                food.id,
                                0),
                        result -> {
                            food.num -= 1;
                            cartChanged(food, position);
                        });
            } else if (view.getId() == R.id.addBtn) {
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().changeFoodCart(
                                LoginInfoUtil.getUid(),
                                id,
                                food.id,
                                1),
                        result -> {
                            food.num += 1;
                            cartChanged(food, position);
                        });
            }
        });
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getRestaurantDetail(LoginInfoUtil.getUid(), id),
                result -> bindRestaurant(result.data));
    }

    private void cartChanged(Food food, int position) {
        if (food.num == 0) {
            cartAdapter.remove(cartAdapter.getData().indexOf(food));
        }
        if (cartAdapter.getData().isEmpty()) {
            toggleCartContainer();
        }
        cartAdapter.notifyItemChanged(position);
        foodListFragment.refresh(food);
        changeCart();
    }

    private void changeCart() {
        BigDecimal amount = new BigDecimal(0);
        int count = 0;
        for (Food food : cartAdapter.getData()) {
            count += food.num;
            amount = amount.add(new BigDecimal(food.getAmount()));
        }
        ViewUtil.setText(mActivity, R.id.cartCountTxt, String.valueOf(count));
        ViewUtil.setText(mActivity, R.id.totalTxt, FormatUtil.getMoneyString(amount.doubleValue()));
    }

    private void bindRestaurant(Restaurant restaurant) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        foodListFragment = new FoodListFragment(restaurant);
        foodListFragment.onCartChangedListener = foods -> loadCart();
        fragments.add(foodListFragment);
        fragments.add(new ReviewListFragment(restaurant));
        fragments.add(new RestaurantInfoFragment(restaurant));
        ((SlidingTabLayout) findViewById(R.id.tabLayout)).setViewPager(
                findViewById(R.id.viewPager),
                new String[]{"预订", "评论", "商家"},
                mActivity,
                fragments);
        ViewUtil.setText(mActivity, R.id.nameTxt, restaurant.name);
        ViewUtil.setText(mActivity, R.id.likeTxt, restaurant.praise_num);
        ViewUtil.setText(mActivity, R.id.addressTxt, restaurant.address);
        Glide.with(mActivity)
                .load(UrlUtil.INSTANCE.getImageUrl(restaurant.banner_img))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_placeholder)
                        .centerCrop())
                .into((ImageView) findViewById(R.id.bannerImg));
        findViewById(R.id.buyBtn).setOnClickListener(v -> {
            if (cartAdapter.getData().isEmpty()) {
                ToastUtils.showShort("~购物车空空如也~");
                return;
            }
            RestaurantBillActivity.start(mActivity, restaurant, 666);
        });
        findViewById(R.id.callBtn).setOnClickListener(v ->
                startActivity(IntentUtils.getDialIntent(restaurant.tel)));
        findViewById(R.id.locationBtn).setOnClickListener(v -> {
            MapUtil.start(restaurant.address, mActivity);
        });
        collectBtn.setSelected(restaurant.status != 1);
        collectSuccess();
        collectBtn.setOnClickListener(v -> {
            if (collectBtn.isSelected()) {
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().mallgoodsfollow(restaurant.id,
                                LoginInfoUtil.getUid(), LoginInfoUtil.getToken(), "0")
                        , result -> collectSuccess());
            } else {
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().mallgoodsfollow(restaurant.id,
                                LoginInfoUtil.getUid(), LoginInfoUtil.getToken(), "1")
                        , result -> collectSuccess());
            }
        });
        ViewUtil.setText(mActivity, R.id.openTimeTxt, getOpeningTime(restaurant.business_start, restaurant.business_end));
    }


    private String getOpeningTime(String business_start, String business_end) {
        if (TextUtils.isEmpty(business_start) || TextUtils.isEmpty(business_end)) {
            return "营业中 00:00 ~ 24:00";
        }
        Calendar now = Calendar.getInstance();
        Calendar startCalendar = Calendar.getInstance();
        Date startDate = TimeUtils.string2Date(business_start, "HH:mm");
        startCalendar.setTime(startDate);

        Calendar endCalendar = Calendar.getInstance();
        Date endDate = TimeUtils.string2Date(business_end, "HH:mm");
        endCalendar.setTime(endDate);

        startCalendar.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_YEAR));
        startCalendar.get(Calendar.YEAR);
        endCalendar.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_YEAR));
        endCalendar.get(Calendar.YEAR);
        String openingTimeStr = business_start + "~" + business_end;
        if (now.after(startCalendar) && now.before(endCalendar)) {
            return "营业中 " + openingTimeStr;
        } else {
            return "已打烊 " + openingTimeStr;
        }
    }

    private void collectSuccess() {
        collectBtn.setSelected(!collectBtn.isSelected());
        if (collectBtn.isSelected()) {
            ViewUtil.setText(mActivity, R.id.collectTxt, "已关注");
        } else {
            ViewUtil.setText(mActivity, R.id.collectTxt, "关注");
        }
    }

    private void loadCart() {
        if (TextUtils.isEmpty(LoginInfoUtil.getToken())) return;
        RequestUtil.request(mActivity, true, false,
                () -> ApiUtils.getApiService().getCart(LoginInfoUtil.getUid(), LoginInfoUtil.getToken(), id
                ), result -> {
                    cartAdapter.setNewData(result.data);
                    changeCart();
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CODE_LOGIN) {
            loadCart();
            foodListFragment.refreshCart();
        } else if (requestCode == 666) {
            finish();
            ActivityUtils.startActivity(RestaurantOrderListActivity.class);
        }
    }

    @OnClick(R.id.cartBtn)
    void onCartBtnClick() {
        toggleCartContainer();
    }

    @OnClick(R.id.closeBtn)
    void onCloseBtnClick() {
        toggleCartContainer();
    }

    @Override
    public void onBackPressed() {
        if (cartDialogContainer.isShown()) {
            toggleCartContainer();
            return;
        }
        super.onBackPressed();
    }

    private void toggleCartContainer() {
        if (cartDialogContainer.isShown()) {
            cartDialogContainer.setVisibility(View.GONE);
        } else {
            if (cartAdapter.getData().isEmpty()) {
                ToastUtils.showShort("~购物车空空如也~");
                return;
            }
            cartDialogContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_detail;
    }

    public static void start(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DATA, id);
        ActivityUtils.startActivity(bundle, RestaurantDetailActivity.class);
    }
}
