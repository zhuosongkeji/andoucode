package com.zskjprojectj.andouclient.activity.mall;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuosongkj.android.library.app.BaseFragment;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.PinTuanGoodsAdapter;
import com.zskjprojectj.andouclient.model.PinTuan;

import butterknife.BindView;

public class PinTuanFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    PinTuanGoodsAdapter adapter = new PinTuanGoodsAdapter();

    public PinTuanFragment(PinTuan.PinTuanType pinTuanType) {
        this.pinTuanType = pinTuanType;
    }

    private PinTuan.PinTuanType pinTuanType;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setNewData(pinTuanType.goods);
    }

    @Override
    protected int getContentView() {
        return R.layout.framgent_pin_tuan;
    }
}
