package com.zskjprojectj.andouclient.adapter.mall;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/30 9:21
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class PayWaysAdapter extends BaseQuickAdapter<MallPayWaysBean, BaseViewHolder> {



    private Map<Integer, Boolean> map = new HashMap<>();
    private boolean onBind;
    private int checkedPosition = -1;

    public PayWaysAdapter(int layoutResId, @Nullable List<MallPayWaysBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallPayWaysBean item) {

        Glide.with(mContext).load(UrlUtil.getImageUrl(item.getLogo())).into((ImageView) helper.getView(R.id.iv_balance));
        helper.setText(R.id.tv_pay_ways,item.getPay_way());
        AppCompatCheckBox checkBox = helper.getView(R.id.cb_balance_selector_pay);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()){
                    map.clear();
                    map.put(helper.getLayoutPosition(),true);
                    checkedPosition=helper.getLayoutPosition();
                    itemPayWays.getPayWays(item.getPay_way(),helper.getLayoutPosition());
                }else {
                    map.remove(helper.getLayoutPosition());
                    if (map.size()==0){
                        checkedPosition=-1;//表示一个都没选择
                    }
                }
                if (!onBind){
                    notifyDataSetChanged();
                }

            }
        });

        onBind=true;

        if (map!=null&&map.containsKey(helper.getLayoutPosition())){
            checkBox.setChecked(true);
        }else {
            checkBox.setChecked(false);
        }
        onBind=false;
    }

    public interface ItemPayWays{
        void getPayWays(String payWays,int position);
    }
    private ItemPayWays itemPayWays;

    public void setItemPayWays(ItemPayWays itemPayWays) {
        this.itemPayWays = itemPayWays;
    }
}
