package com.zskjprojectj.andouclient.activity.restaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
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
import com.zskjprojectj.andouclient.adapter.restaurant.CartAdapter;
import com.zskjprojectj.andouclient.fragment.FoodListFragment;
import com.zskjprojectj.andouclient.fragment.RestaurantInfoFragment;
import com.zskjprojectj.andouclient.fragment.ReviewListFragment;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.Restaurant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

public class RestaurantDetailActivity extends BaseActivity {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CartAdapter cartAdapter = new CartAdapter();

    @BindView(R.id.fixContainer)
    View fixContainer;

    @BindView(R.id.cartContainer)
    View cartContainer;

    @BindView(R.id.cartRecyclerView)
    RecyclerView cartRecyclerView;

    @BindView(R.id.cartDialogContainer)
    View cartDialogContainer;

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
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra(KEY_DATA);
        FoodListFragment foodListFragment = new FoodListFragment(restaurant);
        foodListFragment.onCartChangedListener = foods -> {
            ArrayList<Food> cartFoods = new ArrayList<>();
            cartFoods.clear();
            for (Food food : foods) {
                if (food.num > 0) {
                    cartFoods.add(food);
                }
            }
            cartAdapter.setNewData(cartFoods);
            changeCart();
        };
        fragments.add(foodListFragment);
        fragments.add(new ReviewListFragment());
        fragments.add(new RestaurantInfoFragment());
        RecyclerViewUtil.disableItemAnimator(cartRecyclerView);
        cartAdapter.bindToRecyclerView(cartRecyclerView);
        cartAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Food food = cartAdapter.getItem(position);
            if (view.getId() == R.id.addBtn) {
                food.num += 1;
            } else if (view.getId() == R.id.subBtn) {
                food.num -= 1;
            }
            if (food.num == 0) {
                cartAdapter.remove(cartAdapter.getData().indexOf(food));
            }
            if (cartAdapter.getData().isEmpty()) {
                toggleCartContainer();
            }
            cartAdapter.notifyItemChanged(position);
            foodListFragment.refresh(food);
            changeCart();
        });
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().getRestaurantDetail(restaurant.id),
                result -> bindRestaurant(result.data));
        ((SlidingTabLayout) findViewById(R.id.tabLayout)).setViewPager(
                findViewById(R.id.viewPager),
                new String[]{"预订", "评论", "商家"},
                mActivity,
                fragments);
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
        ViewUtil.setText(mActivity, R.id.nameTxt, restaurant.name);
        ViewUtil.setText(mActivity, R.id.likeTxt, restaurant.praise_num);
        ViewUtil.setText(mActivity, R.id.addressTxt, restaurant.address);
        Glide.with(mActivity)
                .load(restaurant.door_img)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_placeholder)
                        .centerCrop())
                .into((ImageView) findViewById(R.id.bannerImg));
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

    public static void start(Restaurant restaurant) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, restaurant);
        ActivityUtils.startActivity(bundle, RestaurantDetailActivity.class);
    }
}
