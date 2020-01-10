package com.zhuosongkj.android.library.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public final HashMap<T, Boolean> selectMap = new HashMap<>();
    public boolean isSelectedAll;
    public boolean isLoadMoreEnd;
    public OnSelectedStateChangedListener onSelectedStateChangedListener;

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
    }

    public BaseAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public void setSelectedAll(boolean isSelectedAll) {
        for (T data : selectMap.keySet()) {
            selectMap.put(data, isSelectedAll);
        }
        this.isSelectedAll = isSelectedAll;
        notifyDataSetChanged();
        if (onSelectedStateChangedListener != null) {
            onSelectedStateChangedListener.onSelectedStateChanged();
        }
    }

    public void setSelected(T data, boolean selected) {
        selectMap.put(data, selected);
        notifyItemChanged(getData().indexOf(data));
        isSelectedAll = selected && isLoadMoreEnd && !selectMap.containsValue(false);
        if (onSelectedStateChangedListener != null) {
            onSelectedStateChangedListener.onSelectedStateChanged();
        }
    }

    @Override
    public void setNewData(@Nullable List<T> data) {
        super.setNewData(data);
        selectMap.clear();
        isLoadMoreEnd = false;
        for (T newDatum : getData()) {
            selectMap.put(newDatum, isSelectedAll);
        }
    }

    @Override
    public void addData(int position, @NonNull Collection<? extends T> newData) {
        super.addData(position, newData);
        for (T newDatum : newData) {
            selectMap.put(newDatum, isSelectedAll);
        }
    }

    @Override
    public void addData(@NonNull T data) {
        super.addData(data);
        selectMap.put(data, isSelectedAll);
    }

    @Override
    public void addData(@NonNull Collection<? extends T> newData) {
        super.addData(newData);
        for (T newDatum : newData) {
            selectMap.put(newDatum, isSelectedAll);
        }
    }

    @Override
    public void addData(int position, @NonNull T data) {
        super.addData(position, data);
        selectMap.put(data, isSelectedAll);
    }

    @Override
    public void replaceData(@NonNull Collection<? extends T> data) {
        super.replaceData(data);
        selectMap.clear();
        for (T newDatum : data) {
            selectMap.put(newDatum, isSelectedAll);
        }
    }

    @Override
    public void remove(int position) {
        super.remove(position);
        selectMap.remove(getItem(position));
    }

    @Override
    public void loadMoreEnd(boolean gone) {
        super.loadMoreEnd(gone);
        isLoadMoreEnd = true;
    }

    public interface OnSelectedStateChangedListener {
        void onSelectedStateChanged();
    }
}
