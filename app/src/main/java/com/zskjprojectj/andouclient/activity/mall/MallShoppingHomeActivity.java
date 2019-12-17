package com.zskjprojectj.andouclient.activity.mall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.MallShoppingHomeAdapter;
import com.zskjprojectj.andouclient.adapter.mall.MallShoppingPopuAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingHomeBean;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingPopuBean;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MallShoppingHomeActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.rv_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.ll_mall_shopping_classify)
    LinearLayout mShoppingClassify;

    @BindView(R.id.rl_root_view)
    RelativeLayout mRootView;


    private ArrayList<MallShoppingHomeBean> dataList;
    private RecyclerView mRecyclerPopu;
    private Button mConfirm;
    private String classify;
    private PopupWindow mPopWindow;


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mall_shopping_home);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MallShoppingHomeBean dataBean = new MallShoppingHomeBean();
            dataBean.setName("aaa");
            dataList.add(dataBean);
        }

        MallShoppingHomeAdapter adapter = new MallShoppingHomeAdapter(R.layout.activity_mall_shopping_item_view, dataList);
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected void initViews() {


        //设置状态栏的高度
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRootView.getLayoutParams();
        layoutParams.topMargin = BarUtils.getStatusBarHeight(this);
        mRootView.setLayoutParams(layoutParams);

        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));


    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(MallShoppingHomeActivity.this, MallGoodsDetailsActivity.class));
    }


    @OnClick(R.id.ll_mall_shopping_classify)
    public void clickClassify() {
        //设置contentView
        View contentView = LayoutInflater.from(MallShoppingHomeActivity.this).inflate(R.layout.activity_mall_shopping_classify_view, null);

        mRecyclerPopu = contentView.findViewById(R.id.rv_recycler);
        mConfirm = contentView.findViewById(R.id.confirm);
        initRecycler();
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
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //设置popupWindow消失时的监听
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        //显示方式
        mPopWindow.showAsDropDown(mShoppingClassify,0,100);

    }

    private void initRecycler() {


        mRecyclerPopu.setLayoutManager(new GridLayoutManager(this,2));
        ArrayList<MallShoppingPopuBean> dataList=new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            MallShoppingPopuBean dataBean=new MallShoppingPopuBean();
            dataBean.setContent("不限");
            dataList.add(dataBean);
        }

        MallShoppingPopuAdapter adapter1= new MallShoppingPopuAdapter(R.layout.activity_mall_shopping_popu_view,dataList);
        mRecyclerPopu.setAdapter(adapter1);

        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                adapter1.onChange(position);
                adapter1.notifyDataSetChanged();
            }
        });

        adapter1.setItemListener(new MallShoppingPopuAdapter.OnItemListener() {
            @Override
            public void getContent(String content) {
               classify=content;
            }
        });


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtil.showToast(classify);
                mPopWindow.dismiss();

            }
        });

    }


    @OnClick(R.id.busiess_back_image)
    public void clickBack(){
        finish();
    }
}
