package com.zskjprojectj.andouclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.ClassificationofgoodsActivity;
import com.zskjprojectj.andouclient.entity.CategoryBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsCateBean;
import com.zskjprojectj.andouclient.view.GridViewForScrollView;

import java.util.List;

/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<MallGoodsCateBean> foodDatas;

    public HomeAdapter(Context context, List<MallGoodsCateBean> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }

    @Override
    public int getCount() {
        if (foodDatas != null) {
            return foodDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MallGoodsCateBean mallGoodsCateBean = foodDatas.get(position);
        List<MallGoodsCateBean.TowcateBean> towcate = mallGoodsCateBean.getTowcate();
//        List<CategoryBean.DataBean.DataListBean> dataList = dataBean.getDataList();
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home, null);
            viewHold = new ViewHold();
            viewHold.gridView = (GridViewForScrollView) convertView.findViewById(R.id.gridView);
            viewHold.blank = (TextView) convertView.findViewById(R.id.blank);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        HomeItemAdapter adapter = new HomeItemAdapter(context, towcate);
        viewHold.blank.setText(mallGoodsCateBean.getName());
        viewHold.gridView.setAdapter(adapter);
        viewHold.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //商品分类跳转到商品列表
                ClassificationofgoodsActivity.getCataId(towcate.get(position).getId());
            }
        });
        return convertView;
    }

    private static class ViewHold {
        private GridViewForScrollView gridView;
        private TextView blank;
    }

}
