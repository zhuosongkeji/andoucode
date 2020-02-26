package com.zskjprojectj.andouclient.fragment.mall;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.adapter.mall.MallMiaoShaAdapter;
import com.zskjprojectj.andouclient.adapter.mall.MallMiaoShaGoodsAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.MiaoShaGoods;
import com.zskjprojectj.andouclient.model.MiaoShaListResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class MallMiaoShaFragment extends BaseFragment {

    public MallMiaoShaFragment(MiaoSha miaoSha) {
        this.miaoSha = miaoSha;
    }

    MiaoSha miaoSha;
    OnStateReceiveListener listener;
    MallMiaoShaGoodsAdapter goodsAdapter = new MallMiaoShaGoodsAdapter();
    MallMiaoShaAdapter adapter = new MallMiaoShaAdapter();


    @BindView(R.id.mRvGoodGoods)
    RecyclerView mRvGoodGoods;

    @BindView(R.id.mRvMiaoShaGoods)
    RecyclerView mRvMiaoShaGoods;

    @BindView(R.id.timerContainer)
    View timerContainer;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.hourTxt)
    TextView hourTxt;

    @BindView(R.id.minuteTxt)
    TextView minuteTxt;

    @BindView(R.id.secondTxt)
    TextView secondTxt;

    @Override
    protected int getContentView() {
        return R.layout.fragment_mall_miao_sha;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        goodsAdapter.bindToRecyclerView(mRvGoodGoods);
        adapter.setOnItemChildClickListener(
                (adapter1, view, position) -> {
                    MiaoShaGoods goods = adapter.getItem(position);
                    MallGoodsDetailsActivity.start(goods.goods_id, "MIAOSHA", goods.sec_id);
                });
        goodsAdapter.setOnItemClickListener(
                (adapter1, view, position) -> {
                    MiaoShaGoods goods = adapter.getItem(position);
                    MallGoodsDetailsActivity.start(goods.goods_id, "MIAOSHA", goods.sec_id);
                });
        PageLoadUtil<MiaoShaGoods> pageLoadUtil = PageLoadUtil.get(mActivity, mRvMiaoShaGoods, adapter, refreshLayout);
        pageLoadUtil.load(() -> ApiUtils.getApiService().miaoShaList(miaoSha.getStartTimeParam(), pageLoadUtil.page)
                , (refresh, data) -> {
                    miaoSha.response = (MiaoShaListResponse) data;
                    if (listener != null) {
                        listener.onStateReceive(miaoSha.getState());
                    }
                    onBind(miaoSha);
                });
    }

    private void onBind(MiaoSha miaoSha) {
        goodsAdapter.setNewData(miaoSha.response.top_goods);
        adapter.miaoSha = miaoSha;
        timerContainer.setVisibility(
                miaoSha.getState() == MiaoSha.State.JIN_XING_ZHONG ? View.VISIBLE : View.GONE);
        if (miaoSha.getState() == MiaoSha.State.JIN_XING_ZHONG) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timerContainer.postDelayed(() -> countTime(), 1);
                }
            }, 0, 1000);
        }
    }

    public void setOnStateReceiveListener(OnStateReceiveListener listener) {
        this.listener = listener;
    }

    public interface OnStateReceiveListener {
        void onStateReceive(MiaoSha.State state);
    }

    long offset;
    Timer timer = new Timer();

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void countTime() {
        Date date = TimeUtils.string2Date(miaoSha.endTime, new SimpleDateFormat("HH:mm", Locale.getDefault()));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
        calendar.set(Calendar.MINUTE, date.getMinutes());
        calendar.set(Calendar.SECOND, 0);
        long endTime = calendar.getTime().getTime();
        long currentTime = System.currentTimeMillis();
        offset = (endTime - currentTime) / 1000;
        if (offset > 0) {
            secondTxt.setText(String.valueOf(offset % 60));
            minuteTxt.setText(String.valueOf(offset / 60 % 60));
            hourTxt.setText(String.valueOf(offset / 60 / 60 % 24));
            offset -= 1;
        }
    }
}
