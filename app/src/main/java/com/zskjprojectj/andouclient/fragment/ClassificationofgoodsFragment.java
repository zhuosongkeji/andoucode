package com.zskjprojectj.andouclient.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.ClassificationofgoodsActivity;
import com.zskjprojectj.andouclient.adapter.HomeAdapter;
import com.zskjprojectj.andouclient.adapter.MenusAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.CategoryBean;
import com.zskjprojectj.andouclient.view.TopView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 在线商城商品分类
 */
public class ClassificationofgoodsFragment extends BaseFragment {
    private List<String> menuList = new ArrayList<>();
    private List<CategoryBean.DataBean> homeList = new ArrayList<>();
    private List<Integer> showTitle;

    private ListView lv_menu;
    private ListView lv_home;

    private MenusAdapter menuAdapter;
    private HomeAdapter homeAdapter;
    private int currentItem;

    private TextView tv_title;
    private TopView mTopVIew;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTopVIew = view.findViewById(R.id.alltopview);
        mTopVIew.setTitle("商品分类");
        getBarDistance(mTopVIew);

        lv_menu = (ListView) view.findViewById(R.id.lv_menu);
        tv_title = (TextView) view.findViewById(R.id.tv_titile);
        lv_home = (ListView)view.findViewById(R.id.lv_home);

    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_classificationofgoods;
    }

    @Override
    protected void getDataFromServer() {
        String json = getJson(mAty, "category.json");
        CategoryBean categoryBean = JSONObject.parseObject(json, CategoryBean.class);
        showTitle = new ArrayList<>();
        for (int i = 0; i < categoryBean.getData().size(); i++) {
            CategoryBean.DataBean dataBean = categoryBean.getData().get(i);
            menuList.add(dataBean.getModuleTitle());
            showTitle.add(i);
            homeList.add(dataBean);
        }
        tv_title.setText(categoryBean.getData().get(0).getModuleTitle());
        menuAdapter.notifyDataSetChanged();
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        menuAdapter = new MenusAdapter(mAty, menuList);
        lv_menu.setAdapter(menuAdapter);

        homeAdapter = new HomeAdapter(mAty, homeList);
        lv_home.setAdapter(homeAdapter);
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
                tv_title.setText(menuList.get(position));
                lv_home.setSelection(showTitle.get(position));
            }
        });
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
                    tv_title.setText(menuList.get(currentItem));
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
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
