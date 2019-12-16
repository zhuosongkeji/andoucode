package com.zskjprojectj.andouclient.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.CheckthelogisticsBean;

import java.util.List;

public class CheckthelogisticsAdapter extends BaseQuickAdapter<CheckthelogisticsBean, BaseViewHolder> {
    private Context context;
    private List<CheckthelogisticsBean> data;
    public CheckthelogisticsAdapter(Context context,int layoutResId, @Nullable List<CheckthelogisticsBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckthelogisticsBean item) {
        //获取物流信息和物流时间的字体颜色, 最新的一条物流数据字体为绿色
        int newInfoColor = context.getResources().getColor(helper.getLayoutPosition() == 0 ? R.color.green : R.color.grey_1);
        //当前item的索引==0 && 物流数据的数量大于1条   ->  显示绿色大圆圈
        helper.setGone(R.id.iv_new, helper.getLayoutPosition() == 0 && data.size() > 1)
                //当前item的索引!=0 && 物流数据的数量大于1条   ->  显示灰色小圆圈
                .setGone(R.id.iv_old, helper.getLayoutPosition() != 0 && data.size() > 1)
                //当前item的索引 != 0    ->  显示圆点上面短一点的灰线
                .setVisible(R.id.v_short_line, helper.getLayoutPosition() != 0)
                //当前item的索引 != 物流数据的最后一条    ->  显示圆点下面长一点的灰线
                .setGone(R.id.v_long_line, helper.getLayoutPosition() != data.size() - 1)
                //当前item的索引 != 物流数据的最后一条    ->  显示物流时间下面的横向的灰线
                .setGone(R.id.v_bottom_line, helper.getLayoutPosition() != data.size() - 1)
                .setTextColor(R.id.tv_info, newInfoColor)
                .setTextColor(R.id.tv_date, newInfoColor)
                //物流信息
                .setText(R.id.tv_info, item.getAcceptStation())
                //物流时间
                .setText(R.id.tv_date, item.getAcceptTime());

    }
}
