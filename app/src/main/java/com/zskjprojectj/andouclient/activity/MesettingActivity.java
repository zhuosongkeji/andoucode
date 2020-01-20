package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.SetBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.BitmapUtil;
import com.zskjprojectj.andouclient.utils.DataCleanManager;
import com.zskjprojectj.andouclient.utils.DialogUtil;
import com.zskjprojectj.andouclient.utils.GlideEngine;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.view.PromtOnlyExtraDialog;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.zskjprojectj.andouclient.http.BaseObserver.REQUEST_CODE_LOGIN;

/**
 * 个人中心设置界面
 */
public class MesettingActivity extends BaseActivity {


    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    private TextView tv_usersetversion, tv_usersetphone, tv_usersetname, mFsize;
    private ImageView img_setpic;
    private RelativeLayout rl_modifythephone, rl_modifythepassword, rl_modifyfeedback, rl_modifyaboutus, rl_clear, rl_modifythenickname;
    private Button btn_exit;
    private static final int REQUEST_CODE_TOUXIANG_LOGO = 555;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("设置");
    }

    @Override
    protected void initViews() {
        rl_modifythephone = findViewById(R.id.rl_modifythephone);
        rl_modifythepassword = findViewById(R.id.rl_modifythepassword);
        rl_modifyfeedback = findViewById(R.id.rl_modifyfeedback);
        rl_modifyaboutus = findViewById(R.id.rl_modifyaboutus);
        tv_usersetversion = findViewById(R.id.tv_usersetversion);
        tv_usersetphone = findViewById(R.id.tv_usersetphone);
        tv_usersetname = findViewById(R.id.tv_usersetname);
        img_setpic = findViewById(R.id.img_setpic);
        rl_clear = findViewById(R.id.rl_clear);
        /**
         * 修改头像
         */
        img_setpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSelectPic(REQUEST_CODE_TOUXIANG_LOGO);
            }
        });
        rl_modifythenickname = findViewById(R.id.rl_modifythenickname);
        mFsize = findViewById(R.id.tv_msize);
        btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().quit(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        LoginInfoUtil.saveLoginInfo("", "");
                        finish();
                        Intent intent = new Intent(mAt, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ActivityUtils.startActivity(intent);
                    }
                });
            }
        });
        /**
         * 修改昵称
         */
        rl_modifythenickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(ModifythenicknameActivity.class);
            }
        });
        /**
         * 电话号码修改
         */
        rl_modifythephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(ModifythephoneActivity.class);
            }
        });
        /**
         * 设置密码
         */
        rl_modifythepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(ModifythepasswordActivity.class);
            }
        });
        /**
         * 意见反馈
         */
        rl_modifyfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(ModifyfeedbackActivity.class);
            }
        });
        /**
         * 关于我们
         */
        rl_modifyaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(ModifyaboutusActivity.class);
            }
        });
        /**
         * 清除缓存
         */
        rl_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = mFsize.getText().toString();
                PromtOnlyExtraDialog promtOnlyExtraDialog = new PromtOnlyExtraDialog(mAt, R.style.dialog, "系统提示", "是否清理缓存" + s, "清理", new PromtOnlyExtraDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            DataCleanManager.clearAllCache(mAt);
                            ToastUtil.showToast("清理成功!");
                            mFsize.setText("0kb");
                        }
                    }
                });
                promtOnlyExtraDialog.show();
            }
        });
        findViewById(R.id.protocolContainer).setOnClickListener(v ->
                dialog = DialogUtil.showProtocolDialogNoBtns(mAt));
    }
    private void startSelectPic(int requestCode) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .isCamera(true)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(requestCode);
    }

    Dialog dialog;

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().set(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(new BaseObserver<SetBean>(mAt) {
            @Override
            public void onHandleSuccess(SetBean setBean) throws IOException {
                tv_usersetname.setText(setBean.getName());
                tv_usersetphone.setText(setBean.getMobile());
                tv_usersetversion.setText("当前版本" + setBean.getEdition());
                Glide.with(mAt).load(UrlUtil.getImageUrl(setBean.getAvator()))
                        .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                        .into((ImageView) findViewById(R.id.img_setpic));
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode==REQUEST_CODE_LOGIN)
        {
            getDataFromServer();
            return;
        }
        String path = PictureSelector.obtainMultipleResult(data).get(0).getAndroidQToPath();
        if (TextUtils.isEmpty(path)) {
            path = PictureSelector.obtainMultipleResult(data).get(0).getPath();
        }
        ImageView imageView = null;
        switch (requestCode)
        {
            case  REQUEST_CODE_TOUXIANG_LOGO:
                imageView=img_setpic;
                break;
        }
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
        RequestBody uid = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getUid());
        RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getToken());
        ImageView finalImageView = imageView;
        String finalPath = path;
        HttpRxObservable.getObservable(ApiUtils.getApiService().uploadImg(uid,token,body)).subscribe(new BaseObserver<String>(mAt) {

            @Override
            public void onHandleSuccess(String s) throws IOException {
            //    finalImageView.setTag(s);
              HttpRxObservable.getObservable(ApiUtils.getApiService().user_pictures(LoginInfoUtil.getUid(),LoginInfoUtil.getToken(),s)).subscribe(new BaseObserver<String>(mAt) {
                  @Override
                   public void onHandleSuccess(String s) throws IOException {
                        BitmapUtil.recycle(finalImageView);
                        finalImageView.setImageBitmap(BitmapFactory.decodeFile(finalPath));
                 }
              });

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @OnClick(R.id.iv_header_back)
    public void clickView() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
