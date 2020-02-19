package com.zskjprojectj.andouclient.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.GlideEngine;

import java.util.List;

public class SelectPicAdapter extends BaseQuickAdapter<SelectPicAdapter.SelectPic, BaseViewHolder> {

    public int selectPicCount;

    public SelectPicAdapter(int selectPicCount) {
        super(R.layout.layout_select_pic);
        this.selectPicCount = selectPicCount;
        addData(new SelectPic());
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectPic item) {
        if (item.path.isEmpty()) {
            helper.setVisible(R.id.deleteBtn, false)
                    .setImageResource(R.id.picImg, R.mipmap.ic_add_02);
            helper.itemView.setOnClickListener(v ->
                    PictureSelector.create((Activity) helper.itemView.getContext())
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(selectPicCount - getSelectedCount())
                            .isCamera(true)
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .forResult(PictureConfig.CHOOSE_REQUEST));
        } else {
            helper.setVisible(R.id.deleteBtn, true);
            helper.itemView.setOnClickListener(null);
            Glide.with(helper.itemView)
                    .load(item.path)
                    .into((ImageView) helper.itemView.findViewById(R.id.picImg));
            helper.itemView.findViewById(R.id.deleteBtn).setOnClickListener(v -> {
                remove(helper.getAdapterPosition());
                checkAddBtn();
            });
        }
    }

    private void checkAddBtn() {
        if (selectPicCount - getSelectedCount() > 0 && !getItem(getItemCount() - 1).path.isEmpty()) {
            addData(new SelectPic());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == Activity.RESULT_OK) {
            remove(getItemCount() - 1);
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            for (LocalMedia localMedia : selectList) {
                SelectPic selectPic = new SelectPic();
                String path = localMedia.getAndroidQToPath();
                if (TextUtils.isEmpty(path)) {
                    path = PictureSelector.obtainMultipleResult(data).get(0).getPath();
                }
                selectPic.path = path;
                addData(selectPic);
            }
            checkAddBtn();
        }
    }

    private int getSelectedCount() {
        int count = 0;
        for (SelectPic selectPic : getData()) {
            if (!selectPic.path.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public class SelectPic {
        public String path = "";
    }
}
