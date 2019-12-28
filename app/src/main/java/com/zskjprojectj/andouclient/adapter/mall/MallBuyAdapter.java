package com.zskjprojectj.andouclient.adapter.mall;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.mall.MallBuyBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/26 17:21
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallBuyAdapter extends BaseQuickAdapter<MallBuyBean.SpecInfo, BaseViewHolder> {
    public MallBuyAdapter(int layoutResId, @Nullable List<MallBuyBean.SpecInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallBuyBean.SpecInfo item) {

        helper.setText(R.id.tv_specification,item.name);

        //流式布局
        TagFlowLayout flowLayout  = helper.getView(R.id.id_flowlayout);
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        flowLayout.setAdapter(new TagAdapter<String>(item.value) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.tv, flowLayout, false);
                tv.setText(s);
                tv.setSelected(true);
                return tv;
            }
        });

        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {

                ToastUtil.showToast("选择的是"+item.value.get(position));

                return true;
            }
        });



    }
}
