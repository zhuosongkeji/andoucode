package com.zskjprojectj.andouclient.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wihaohao.PageGridView;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BookorderBean;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 在线预约点餐BaseListActivity
 */
public class OnlineBookingorderActivity extends BaseActivity {
    //下拉列表
    private NiceSpinner one ,two,three;
    private ImageView img_back;
    List<BookorderBean> mList;
    private String[] proName = {"火锅","自助餐","甜品饮品","小吃快餐","西餐","韩式烤肉",
            "香锅烤鱼","海鲜","水果"};
    private int[] proicon = {R.mipmap.hg,R.mipmap.zzc,R.mipmap.tp,R.mipmap.xckc,R.mipmap.xc,R.mipmap.kr,
            R.mipmap.xgky,R.mipmap.hx,R.mipmap.hx};
    List<String> spinnerDataone = new LinkedList<>(Arrays.asList("全部美食", "水果", "自助餐", "甜品"));
    List<String> spinnerDatatwo = new LinkedList<>(Arrays.asList("附近", "全城", "推荐商圈", "渝中区"));
    List<String> spinnerDatathree = new LinkedList<>(Arrays.asList("只能排序", "离我最近", "好评优先", "销量最高"));

    private PageGridView<BookorderBean> mPageGridView;
    private LinearLayout ly_detailspage;

    @Override
    protected void setRootView() {
       setContentView(R.layout.activity_onlinebookingorder);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initViews() {
        initDatas();
        img_back=findViewById(R.id.img_back);
        one=findViewById(R.id.one_spinner);
        two=findViewById(R.id.two_spinner);
        three=findViewById(R.id.three_spinner);

        one.attachDataSource(spinnerDataone);
        one.setSelectedIndex(0);
      //  one.setBackgroundResource(R.drawable.textview_round_border);
        one.setTextColor(Color.BLACK);
        one.setTextSize(13);

        two.attachDataSource(spinnerDatatwo);
       // two.setBackgroundResource(R.drawable.textview_round_border);
        two.setTextColor(Color.BLACK);
        two.setTextSize(13);

        three.attachDataSource(spinnerDatathree);
        //three.setBackgroundResource(R.drawable.textview_round_border);
        three.setTextColor(Color.BLACK);
        three.setTextSize(13);
        mPageGridView =findViewById(R.id.vp_grid_view);
        mPageGridView.setData(mList);
        mPageGridView.setOnItemClickListener(new PageGridView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Toast.makeText(AppHomeActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });
        ly_detailspage=findViewById(R.id.ly_detailspage);
        //设置点击事件
        ly_detailspage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到商家详情
                jumpActivity(BusinessdetailsActivity.class);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAt.finish();
            }
        });
    }
    private void initDatas() {
        mList=new ArrayList<>();
        for(int i=0;i<proName.length;i++){
            mList.add(new BookorderBean(proName[i],proicon[i]));
        }
    }
    @Override
    public void getDataFromServer() {
    }

//    @Override
//    protected void findView() {
//
//    }
//    @Override
//    protected Observable<List> getHttpListObservable() {
//        String key="3279cde306e48687bc89cc63e392a135";
//        String menu="西红柿";
//        return HttpRxObservable.getObservable(ApiUtils.getApiService().getinfo(key, menu), this).map(new Function<BaseResult<TestBean.ResultBean>, List>() {
//            @Override
//            public List apply(BaseResult<TestBean.ResultBean> resultBeanBaseResult) throws Exception {
//
//                return resultBeanBaseResult.getResult().data;
//            }
//
//        });
//    }
//
//    @Override
//    protected BaseQuickAdapter createAdapter(List mDatas) {
//        LiveAdapter liveAdapter=new LiveAdapter(mDatas);
//        return liveAdapter;
//    }
//
//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
