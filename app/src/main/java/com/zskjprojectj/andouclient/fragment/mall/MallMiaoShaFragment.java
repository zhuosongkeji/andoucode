package com.zskjprojectj.andouclient.fragment.mall;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.adapter.mall.MallMiaoShaAdapter;
import com.zskjprojectj.andouclient.adapter.mall.MallMiaoShaGoodsAdapter;

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

    MallMiaoShaGoodsAdapter goodsAdapter = new MallMiaoShaGoodsAdapter();
    MallMiaoShaAdapter adapter = new MallMiaoShaAdapter();


    @BindView(R.id.mRvGoodGoods)
    RecyclerView mRvGoodGoods;

    @BindView(R.id.mRvMiaoShaGoods)
    RecyclerView mRvMiaoShaGoods;

    @BindView(R.id.timerContainer)
    View timerContainer;

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
        adapter.bindToRecyclerView(mRvMiaoShaGoods);
        adapter.setOnItemChildClickListener(
                (adapter1, view, position) -> {
//                    if (miaoSha.state == MiaoSha.State.JIN_XING_ZHONG) {
                    MallGoodsDetailsActivity.start(adapter.getItem(position).id);
//                    }
                });
        goodsAdapter.setOnItemClickListener(
                (adapter1, view, position) -> {
//                    if (miaoSha.state == MiaoSha.State.JIN_XING_ZHONG) {
                    MallGoodsDetailsActivity.start(adapter.getItem(position).id);
//                    }
                });
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        }, filter);
        onBind(miaoSha);
        if (miaoSha.state == MiaoSha.State.JIN_XING_ZHONG) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    countTime();
                }
            }, 0, 1000);
        }
    }

    private void onBind(MiaoSha miaoSha) {
        goodsAdapter.setNewData(miaoSha.recommends);
        adapter.miaoSha = miaoSha;
        adapter.setNewData(miaoSha.goods);
        timerContainer.setVisibility(
                miaoSha.state == MiaoSha.State.JIN_XING_ZHONG ? View.VISIBLE : View.GONE);
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
