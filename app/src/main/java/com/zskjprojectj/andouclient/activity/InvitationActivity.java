package com.zskjprojectj.andouclient.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.utils.UrlUtil;import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.InvitationBean;
import com.zskjprojectj.andouclient.http.ApiService;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.SharedPreferencesManager;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分享有礼
 */
public class InvitationActivity extends BaseActivity {
    private TextView tv_username;
    private ImageView img_toptouxiang,img_code;
    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    @OnClick(R.id.iv_header_back)
    public void clickView(){
        finish();
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_invitation);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHeaderTitle.setText("邀请有礼");
        getBarDistance(mTitleView);
//            getBarDistance(mTitleView);
//            mHeaderTitle.setText("分享有礼");
    }

    @Override
    protected void initViews() {
        tv_username=findViewById(R.id.tv_username);
        img_toptouxiang=findViewById(R.id.img_toptouxiang);
        img_code=findViewById(R.id.img_code);
    }

    @Override
    public void getDataFromServer() {
            HttpRxObservable.getObservable(ApiUtils.getApiService().invitationsvip(LoginInfoUtil.getUid(),LoginInfoUtil.getToken())).subscribe(new BaseObserver<InvitationBean>(mAt) {
                @Override
                public void onHandleSuccess(InvitationBean invitationBean) throws IOException {
//                  zxing(invitationBean.getQrcode());
                    Glide.with(mAt).load(UrlUtil.getImageUrl(invitationBean.getAvator()))
                            .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                            .into((ImageView)findViewById(R.id.img_toptouxiang));
                  //  Glide.with(mAt).load(UrlUtil.getImageUrl(invitationBean.getQrcode())).into((img_toptouxiang));
                    Glide.with(mAt).load(UrlUtil.getImageUrl(invitationBean.getQrcode()))
                            .apply(new RequestOptions().placeholder(R.drawable.default_image))
                            .into((ImageView)findViewById(R.id.img_code));
                    tv_username.setText(invitationBean.getName());
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                }
            });
    }
//    public void zxing(String string){
//        try {
//            ;
//            BitMatrix bitMatrix = new QRCodeWriter().encode(string, BarcodeFormat.CODE_93, 250, 250);
//            int fixbit[]=new int[bitMatrix.getWidth()*bitMatrix.getHeight()];
//            for (int y=0;y<bitMatrix.getHeight();y++){
//                for (int x=0;x<bitMatrix.getWidth();x++){
//                    if (bitMatrix.get(x,y)){
//                        fixbit[x+y*bitMatrix.getWidth()]= Color.BLACK;
//                    }else{
//                        fixbit[x+y*bitMatrix.getWidth()]=Color.WHITE;
//                    }
//                }
//            }
//            Bitmap bitmap=Bitmap.createBitmap(fixbit,bitMatrix.getWidth(),bitMatrix.getHeight(), Bitmap.Config.ARGB_8888);
//            img_code.setImageBitmap(bitmap);
//
//        }catch (WriterException e){
//
//        }
//
//    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
