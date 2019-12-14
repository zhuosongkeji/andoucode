package com.zskjprojectj.andouclient.adapter.mall;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingPopuBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/14 11:09
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallShoppingPopuAdapter extends BaseQuickAdapter<MallShoppingPopuBean, BaseViewHolder> {

    public static int SELECTOR_POSITION=-1;


    public MallShoppingPopuAdapter(int layoutResId, @Nullable List<MallShoppingPopuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallShoppingPopuBean item) {

        TextView mContent = helper.getView(R.id.tv_content);
        int position = helper.getLayoutPosition();
        if (SELECTOR_POSITION==position){
            mContent.setSelected(true);
            String classify = mContent.getText().toString();
            if (itemListener!=null){
                itemListener.getContent(classify);
            }

        }else {
            mContent.setSelected(false);
        }

    }

    public void onChange(int position){
        SELECTOR_POSITION=position;
        notifyDataSetChanged();
    }



    public interface OnItemListener{
        void  getContent(String content);
    }
    private OnItemListener itemListener;

    public void setItemListener(OnItemListener itemListener) {
        this.itemListener = itemListener;
    }
}
