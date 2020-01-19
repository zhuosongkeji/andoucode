package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zhuosongkj.android.library.model.IListData;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity;
import com.zskjprojectj.andouclient.activity.mall.MallShoppingHomeActivity;
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity;
import com.zskjprojectj.andouclient.adapter.MerchantAdapter;
import com.zskjprojectj.andouclient.adapter.MerchantListAdapter;
import com.zskjprojectj.andouclient.entity.MerchantHomeTypeBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Merchant;
import com.zskjprojectj.andouclient.model.MerchantsResponse;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.StatusBarUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 * 商家信息
 *
 * @author yizhubao
 */
public class MerchantsPageFragment extends BaseFragment {

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.iv_header_back)
    ImageView mHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;


    @BindView(R.id.ll_classify)
    LinearLayout mClassify;
    private PopupWindow mPopWindow;
    @BindView(R.id.tv_capacity_sort)
    TextView mCapacitySort;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private RecyclerView mRecycler;
    MerchantListAdapter adapter = new MerchantListAdapter();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int barHeight = StatusBarUtil.getStatusBarHeight(mActivity);
        if (barHeight > 0) {
            //设置状态栏的高度
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTitleView.getLayoutParams();
            layoutParams.topMargin = BarUtils.getStatusBarHeight(mActivity) + layoutParams.topMargin;
            mTitleView.setLayoutParams(layoutParams);
        }
        mHeaderTitle.setText("商家");
        mHeaderBack.setVisibility(View.GONE);
        mRecycler = view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        PageLoadUtil<Merchant> pageLoadUtil = PageLoadUtil
                .get((BaseActivity) getActivity(), mRecycler, adapter, refreshLayout);
        pageLoadUtil.load(new RequestUtil.ObservableProvider<IListData<Merchant>>() {
            @Override
            public Observable<? extends BaseResult<? extends IListData<Merchant>>> getObservable() {
                return ApiUtils.getApiService().merchants_two(pageLoadUtil.page);
            }
        });
        initData();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                switch (adapter.getItem(position).merchant_type_id){
                    //商家商城
                    case "2":
                        MallShoppingHomeActivity.start(adapter.getItem(position).id);
                        break;
                    //酒店商家
                    case "3":
                        HotelDetailActivity.start(adapter.getItem(position).id);
                        break;
                    //饭店商家
                    case "4":
                        RestaurantDetailActivity.start(adapter.getItem(position).id);
                        break;
                    //农家乐
                    case "5":
                        break;
                    //旅游
                    case "6":
                        break;
                    //美食预订
                    case "7":
                        break;
                    //农家乐民宿
                    case "8":
                        break;
                }
            }
        });
    }


    //初始化popuwindow
    private void initPopuWindow(View contentView, TextView textView) {
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);

        //设置背景色
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.8f;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);

        //popupWindow获取焦点
        mPopWindow.setFocusable(true);
        mPopWindow.setAnimationStyle(R.style.popmenu_animation); //动画
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //设置popupWindow消失时的监听
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
                textView.setTextColor(getResources().getColor(R.color.color_common_font));
            }
        });
    }



    private void getMerchantType(String type, String id) {
        switch (type) {
            //商家商城
            case "2":
                MallShoppingHomeActivity.start(id);
                break;
            //酒店商家
            case "3":
                HotelDetailActivity.start(id);
                break;
            //饭店商家
            case "4":
                RestaurantDetailActivity.start(id);
                break;
            //农家乐
            case "5":
                break;
            //旅游
            case "6":
                break;
            //美食预订
            case "7":
                break;
            //农家乐民宿
            case "8":
                break;
        }

    }


    @OnClick({R.id.ll_merchants_classification, R.id.ll_in_screening, R.id.ll_sorting_way})
    public void clickSelector(View v) {
        switch (v.getId()) {
            case R.id.ll_merchants_classification:
                initclassification();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            case R.id.ll_in_screening:
                initscreening();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            case R.id.ll_sorting_way:
                initsortingway();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
        }
    }

    /**
     * 商家分类
     */
    private void initclassification() {

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shop_comprehensive_view, null);

        initPopuWindow(contentView, mCapacitySort);

        RecyclerView mMerchantRecycler = contentView.findViewById(R.id.rv_merchant_recycler);
        mMerchantRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        MerchantAdapter adapter=new MerchantAdapter();
        mMerchantRecycler.setAdapter(adapter);
        HttpRxObservable.getObservable(ApiUtils.getApiService().merchanttype()).subscribe(new BaseObserver<MerchantHomeTypeBean>(mActivity) {
            @Override
            public void onHandleSuccess(MerchantHomeTypeBean merchantHomeTypeBeans) throws IOException {
                adapter.setNewData(merchantHomeTypeBeans.getMerchant_type());
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                        String id = adapter.getItem(position).getId();
                        //TODO
                    }
                });
            }
        });

    }

    /**
     * 地区筛选
     */
    private void initscreening() {

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shop_comprehensive, null);

        initPopuWindow(contentView, mCapacitySort);
    }

    /**
     * 排序方式
     */
    private void initsortingway() {

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shop_comprehensive, null);

        initPopuWindow(contentView, mCapacitySort);
    }

    private void initData() {
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                Intent intent = new Intent(getContext(), HotelDetailActivity.class);
                intent.putExtra(KEY_DATA, adapter.getItem(position));
                startActivity(intent);
            }
        });

    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_merchantspage;
    }
}
