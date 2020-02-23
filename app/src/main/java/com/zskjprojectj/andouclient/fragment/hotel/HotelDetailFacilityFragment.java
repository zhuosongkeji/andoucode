package com.zskjprojectj.andouclient.fragment.hotel;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.FacilityAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.utils.GlideEngine;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.util.ArrayList;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 9:36
 * des:酒店环境设施
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailFacilityFragment extends BaseFragment {

    private RecyclerView mRecycler;
    private ArrayList<String> mDataList;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mDataList= (ArrayList<String>) getArguments().getSerializable("facilities");
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_hotel_detail_facility;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        FacilityAdapter adapter=new FacilityAdapter(R.layout.facility_item_view,mDataList);
        adapter.openLoadAnimation();
//        View footButton = getLayoutInflater().inflate(R.layout.facility_button_foot, (ViewGroup) mRecycler.getParent(), false);
//        adapter.addFooterView(footButton);

//        footButton.findViewById(R.id.see_more_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //startActivity(new Intent(getActivity(), HotelDetailFacilityActivity.class));
//            }
//        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
              //  ToastUtil.showToast("sdsafd");
                ArrayList<LocalMedia> localMedia=new ArrayList<>();
                for (String s : mDataList) {
                    localMedia.add(new LocalMedia(UrlUtil.getImageUrl(s),0, PictureMimeType.ofImage(), PictureMimeType.JPEG));
                }
                PictureSelector.create(mAty)
                        .themeStyle(R.style.picture_default_style)
                        .isNotPreviewDownload(true)
                        .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                        .openExternalPreview(position, localMedia);

            }
        });

        mRecycler.setAdapter(adapter);

    }
}
