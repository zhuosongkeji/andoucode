package com.zskjprojectj.andouclient.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.GlideEngine;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadPicAdapter extends BaseQuickAdapter<UploadPicAdapter.SelectPic, BaseViewHolder> {

    private int selectPicCount;
    public BaseActivity activity;

    public UploadPicAdapter(int selectPicCount) {
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
                    .load(UrlUtil.INSTANCE.getImageUrl(item.path))
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
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
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            for (LocalMedia localMedia : selectList) {
                String path = localMedia.getAndroidQToPath();
                if (TextUtils.isEmpty(path)) {
                    path = localMedia.getPath();
                }
                File file = new File(path);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
                RequestBody uid = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getUid());
                RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getToken());
                RequestUtil.request(activity, true, false,
                        () -> ApiUtils.getApiService().uploadImg(uid, token, body)
                        , result -> {
                            remove(getItemCount() - 1);
                            SelectPic selectPic = new SelectPic();
                            selectPic.path = result.data;
                            addData(selectPic);
                            checkAddBtn();
                        });
            }
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

    public static class SelectPic {
        public String path = "";
    }
}
