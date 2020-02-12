package com.zskjprojectj.andouclient.adapter;

import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.RefundReasonBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2020/1/4 13:38
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CauseRecyclerAdapter extends BaseQuickAdapter<RefundReasonBean, BaseViewHolder> {

    private Map<Integer, Boolean> map = new HashMap<>();
    private boolean onBind;
    public CauseRecyclerAdapter() {
        super(R.layout.refund_reason_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundReasonBean item) {

        helper.setText(R.id.tv_refund, item.getName());
        AppCompatCheckBox checkBox = helper.getView(R.id.checkbox);
        int position = helper.getLayoutPosition();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    map.clear();
                    map.put(position, true);
                    if (onItemContent != null) {
                        onItemContent.content(item.getName(),item.getId());
                    }

                } else {
                    map.remove(position);
                }
                if (!onBind){
                    notifyDataSetChanged();
                }
            }
        });
        onBind=true;
        if (map != null && map.containsKey(position)) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        onBind=false;
    }

    public interface OnItemContent {
        void content(String content,String reason_id);
    }

    private OnItemContent onItemContent;

    public void setOnItemContent(OnItemContent onItemContent) {
        this.onItemContent = onItemContent;
    }
}
