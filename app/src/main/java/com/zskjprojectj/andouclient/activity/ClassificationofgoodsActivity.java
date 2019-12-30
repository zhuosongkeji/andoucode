package com.zskjprojectj.andouclient.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.BalanceofprepaidPaywayAdapter;
import com.zskjprojectj.andouclient.adapter.ClassificationofgoodsAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BalanceofprepaidpaywayBean;
import com.zskjprojectj.andouclient.entity.ClassificationofgoodsBean;
import com.zskjprojectj.andouclient.utils.BarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分类页详细
 *
 */
public class ClassificationofgoodsActivity extends BaseActivity {
    //推荐产品
    private final static int recommend=1;



    @BindView(R.id.ll_classify)
    LinearLayout mClassify;
    private PopupWindow mPopWindow;
    @BindView(R.id.tv_capacity_sort)
    TextView mCapacitySort;
    @BindView(R.id.root_view)
    RelativeLayout mRootView;
    private RecyclerView mRecycler;
    private ArrayList<ClassificationofgoodsBean> mDataList;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_classificationofgoods);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mDataList = new ArrayList<>();
        for (int i=0;i<10;i++){
            ClassificationofgoodsBean databean=new ClassificationofgoodsBean();
            mDataList.add(databean);
        }
        ClassificationofgoodsAdapter adapter=new ClassificationofgoodsAdapter(R.layout.item_classificationofgoods,mDataList);
        adapter.openLoadAnimation();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                jumpActivity(MallGoodsDetailsActivity.class);
            }
        });
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initViews() {
        getBarDistance(mRootView);
        mRecycler=findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
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
    @Override
    public void getDataFromServer() {

    }
    @OnClick({R.id.ll_price_comprehensive, R.id.ll_selector_sales, R.id.ll_selector_price})
    public void clickSelector(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_price_comprehensive:
                initComprehensive();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            case R.id.ll_selector_sales:
                initsales();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            case R.id.ll_selector_price:
                initprice();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
        }
    }

    /**
     * 综合排序
     */
    private void initComprehensive() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_shop_comprehensive, null);

        initPopuWindow(contentView,mCapacitySort);

    }

    /**
     * 销量排序
     *
     * @return
     */
    private void initsales()
    {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_shop_sales, null);

        initPopuWindow(contentView,mCapacitySort);
    }

    /**
     * 价格排序
     * @return
     */
    private  void initprice()
    {
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_shop_price, null);

        initPopuWindow(contentView,mCapacitySort);
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.img_back)
    public void clickBack(){
        finish();
    }
}
