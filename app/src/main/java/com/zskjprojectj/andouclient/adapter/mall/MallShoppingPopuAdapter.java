package com.zskjprojectj.andouclient.adapter.mall;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingHomeBean;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingPopuBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/14 11:09
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallShoppingPopuAdapter extends BaseQuickAdapter<MallShoppingHomeBean.TypeBean, BaseViewHolder> {

    public static int SELECTOR_POSITION=-1;


    public MallShoppingPopuAdapter(int layoutResId, @Nullable List<MallShoppingHomeBean.TypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallShoppingHomeBean.TypeBean item) {

        helper.setText(R.id.tv_content,item.getNameX());


        TextView mContent = helper.getView(R.id.tv_content);
        int position = helper.getLayoutPosition();
        if (SELECTOR_POSITION==position){
            mContent.setSelected(true);
            String typeId = item.getId();
            if (itemListener!=null){
                itemListener.getContentId(typeId);
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
        void  getContentId(String content);
    }
    private OnItemListener itemListener;

    public void setItemListener(OnItemListener itemListener) {
        this.itemListener = itemListener;
    }
}
