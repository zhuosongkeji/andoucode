package com.zskjprojectj.andouclient.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.ItemTransformation;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.ClassificationofgoodsActivity;
import com.zskjprojectj.andouclient.adapter.CategoryLevelAdapter;
import com.zskjprojectj.andouclient.adapter.HomeAdapter;
import com.zskjprojectj.andouclient.adapter.MenusAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.CategoryBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsCateBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.view.TopView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 在线商城商品分类
 */
public class ClassificationofgoodsFragment extends BaseFragment {


    private List<String> menuList = new ArrayList<>();
    private List<MallGoodsCateBean> homeList = new ArrayList<>();
    private List<Integer> showTitle;

    private RecyclerView lv_menu;
    private ListView lv_home;

    private CategoryLevelAdapter level1Adapter = new CategoryLevelAdapter(R.layout.layout_category_level_1_item);
    private HomeAdapter homeAdapter;
    private int currentItem;
    private TextView tv_title;


    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        lv_menu = view.findViewById(R.id.lv_menu);
        tv_title = (TextView) view.findViewById(R.id.tv_titile);
        lv_home = (ListView) view.findViewById(R.id.lv_home);


        final CarouselLayoutManager layoutManager1 = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
        layoutManager1.setPostLayoutListener(new CarouselZoomPostLayoutListener() {
            @Override
            public ItemTransformation transformChild(View child, float itemPositionToCenterDiff, int orientation) {
                final float scale = (float) (2 * (2 * -StrictMath.atan(Math.abs(itemPositionToCenterDiff / 4) + 1.0) / Math.PI + 1));
                return new ItemTransformation(scale, scale, 0, 0);
            }
        });
        layoutManager1.setMaxVisibleItems(5);
        layoutManager1.addOnItemSelectionListener(adapterPosition -> {
            if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                MallGoodsCateBean bean = level1Adapter.getItem(adapterPosition);
                tv_title.setText(bean.name);
                lv_home.setSelection(adapterPosition);
            }
        });
        lv_menu.addOnScrollListener(new CenterScrollListener());
        lv_menu.setLayoutManager(layoutManager1);
        lv_menu.setHasFixedSize(true);
        level1Adapter.bindToRecyclerView(lv_menu);

    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_classificationofgoods;
    }

    @Override
    protected void getDataFromServer() {
//        String json = getJson(mAty, "category.json");
//        CategoryBean categoryBean = JSONObject.parseObject(json, CategoryBean.class);
//        showTitle = new ArrayList<>();
//        for (int i = 0; i < categoryBean.data.size(); i++) {
//            CategoryBean.DataBean dataBean = categoryBean.data.get(i);
//            menuList.add(dataBean.getModuleTitle());
//            showTitle.add(i);
//            homeList.add(dataBean);
//        }
//        tv_title.setText(categoryBean.data.get(0).getModuleTitle());
//        menuAdapter.notifyDataSetChanged();
//        homeAdapter.notifyDataSetChanged();


        //请求商品分类
        HttpRxObservable.getObservable(ApiUtils.getApiService().getMallGoodsCate()).subscribe(new BaseObserver<List<MallGoodsCateBean>>(mAty) {
            @Override
            public void onHandleSuccess(List<MallGoodsCateBean> mallGoodsCateBeans) throws IOException {
                showTitle = new ArrayList<>();
                for (int i = 0; i < mallGoodsCateBeans.size(); i++) {
                    showTitle.add(i);
                    MallGoodsCateBean mallGoodsCateBean = mallGoodsCateBeans.get(i);
                    homeList.add(mallGoodsCateBean);
                }
                tv_title.setText(mallGoodsCateBeans.get(0).name);
                homeAdapter.notifyDataSetChanged();
                level1Adapter.setNewData(mallGoodsCateBeans);
            }
        });


    }

    @Override
    protected void initData() {

        homeAdapter = new HomeAdapter(mAty, homeList);
        lv_home.setAdapter(homeAdapter);
        lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = showTitle.indexOf(firstVisibleItem);
//				lv_home.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    tv_title.setText(level1Adapter.getItem(currentItem).name);
                }
            }
        });
    }

    /**
     * 得到json文件中的内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
