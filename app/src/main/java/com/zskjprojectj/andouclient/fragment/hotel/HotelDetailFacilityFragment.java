package com.zskjprojectj.andouclient.fragment.hotel;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.FacilityAdapter;
import com.zskjprojectj.andouclient.utils.GlideEngine;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.util.ArrayList;

public class HotelDetailFacilityFragment extends BaseFragment {

    private RecyclerView mRecycler;
    private ArrayList<String> mDataList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler = view.findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mDataList = (ArrayList<String>) getArguments().getSerializable("facilities");
        FacilityAdapter adapter = new FacilityAdapter(R.layout.facility_item_view, mDataList);
        adapter.openLoadAnimation();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArrayList<LocalMedia> localMedia = new ArrayList<>();
                for (String s : mDataList) {
                    localMedia.add(new LocalMedia(UrlUtil.INSTANCE.getImageUrl(s), 0, PictureMimeType.ofImage(), PictureMimeType.JPEG));
                }
                PictureSelector.create(mActivity)
                        .themeStyle(R.style.picture_default_style)
                        .isNotPreviewDownload(true)
                        .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                        .openExternalPreview(position, localMedia);

            }
        });
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_hotel_detail_facility;
    }
}
