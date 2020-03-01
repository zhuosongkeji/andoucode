package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.CauseRecyclerAdapter;
import com.zskjprojectj.andouclient.adapter.TuiKuanAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.RefundReasonBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.OrderDetail;
import com.zskjprojectj.andouclient.utils.GlideEngine;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 申请退款
 */
public class ShopordersendetailsrefundActivity extends BaseActivity {

    @BindView(R.id.mHeaderTitle)
    TextView mHeaderTitle;

    @BindView(R.id.mTitleView)
    RelativeLayout mTitleView;



    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @BindView(R.id.ly_refundreason)
    TextView mCause;

    @BindView(R.id.tv_tui_price)
    TextView mTuiPrice;
    @BindView(R.id.et_dec)
    EditText mEtDec;

    @BindView(R.id.iv_image)
    ImageView mImage;


    private RelativeLayout select_cause;
    private Dialog bottomDialog;
    private String type;
    private OrderDetail orderdetail;
    private String reasonId;
    private static final int REQUEST_CODE_SELECT_IMG = 123;
    private String content;
    private String image = "";

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_shopordersendetailsrefund);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        getBarDistance(mTitleView);
        if ("sales_return".equals(type)) {
            mHeaderTitle.setText("申请退货");
        } else if ("refund".equals(type)) {
            mHeaderTitle.setText("申请退款");
        }

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectPic(REQUEST_CODE_SELECT_IMG);
            }
        });

    }

    @Override
    protected void initViews() {
        orderdetail = (OrderDetail) getIntent().getSerializableExtra("details");
        mTuiPrice.setText(orderdetail.order_money);
        TuiKuanAdapter adapter=new TuiKuanAdapter();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setNewData(orderdetail.details);

        mCause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().refundreason()).subscribe(new BaseObserver<List<RefundReasonBean>>(mAt) {
                    @Override
                    public void onHandleSuccess(List<RefundReasonBean> refundReasonBeans) throws IOException {
                        initBuyNow(refundReasonBeans);
                    }
                });


            }
        });
    }



    @Override
    public void getDataFromServer() {


    }


    private void startSelectPic(int requestCode) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .isCamera(true)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(requestCode);
    }


    /**
     * 弹出窗口
     *
     * @return
     */
    private void initBuyNow(List<RefundReasonBean> refundReasonBeans) {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        Window window = bottomDialog.getWindow();
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置宽度
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_shopcancle_discount, null);
        bottomDialog.setContentView(contentView);
        ImageView mCancle = contentView.findViewById(R.id.iv_cancle);
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

        RecyclerView mRvCauseRecycler = contentView.findViewById(R.id.rv_cause_recycler);

        mRvCauseRecycler.setLayoutManager(new LinearLayoutManager(mRvCauseRecycler.getContext()));
        CauseRecyclerAdapter adapter = new CauseRecyclerAdapter();
        adapter.setNewData(refundReasonBeans);
        mRvCauseRecycler.setAdapter(adapter);
        mRvCauseRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.setOnItemContent(new CauseRecyclerAdapter.OnItemContent() {
            @Override
            public void content(String content, String reason_id) {
                reasonId = reason_id;
                mCause.setText(content);
                bottomDialog.dismiss();
            }
        });
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    public static void start(String type, OrderDetail goodsdetail) {

        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("details", goodsdetail);
        ActivityUtils.startActivity(bundle, ShopordersendetailsrefundActivity.class);

    }


    @OnClick({R.id.mHeaderBack, R.id.btn_commit})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.mHeaderBack:
                finish();
                break;
            case R.id.btn_commit:
                content = mEtDec.getText().toString().trim();
                if (TextUtils.isEmpty(reasonId)) {
                    ToastUtil.showToast("请选择退货理由!");
                } else {
                    HttpRxObservable.getObservable(ApiUtils.getApiService().mallrefund(
                            LoginInfoUtil.getUid(),
                            LoginInfoUtil.getToken(),
                            orderdetail.order_id,
                            reasonId,
                            content,
                            image
                    )).subscribe(new BaseObserver<Object>(mAt) {
                        @Override
                        public void onHandleSuccess(Object o) throws IOException {
                            finish();
                            ToastUtils.showShort("提交申请退款成功");
                        }
                    });

                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        String path = PictureSelector.obtainMultipleResult(data).get(0).getAndroidQToPath();
        if (TextUtils.isEmpty(path)) {
            path = PictureSelector.obtainMultipleResult(data).get(0).getPath();
        }
        ImageView imageView = null;
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            imageView = mImage;
        }
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
        RequestBody uid = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getUid());
        RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getToken());
        ImageView finalImageView = imageView;
        HttpRxObservable.getObservable(ApiUtils.getApiService().uploadImg(uid, token, body)).subscribe(new BaseObserver<String>(mAt) {

            @Override
            public void onHandleSuccess(String s) throws IOException {
                image = s;
                Glide.with(mAt).load(UrlUtil.INSTANCE.getImageUrl(s)).into(finalImageView);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }
}
