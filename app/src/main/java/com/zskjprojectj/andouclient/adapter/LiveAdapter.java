package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.TestBean;
import com.zskjprojectj.andouclient.utils.GlideTool;


import java.util.List;

public class LiveAdapter extends BaseQuickAdapter<TestBean.ResultBean.DataBean, BaseViewHolder> {
    public LiveAdapter( List<TestBean.ResultBean.DataBean> data) {
        super(R.layout.live_item, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, TestBean.ResultBean.DataBean item) {
        helper.setText(R.id.tv_bgtest,item.getSteps().get(1).getStep());
          GlideTool.glide(mContext, item.getSteps().get(1).getImg(), (ImageView) helper.getView(R.id.img_bgtest));
    }
}
