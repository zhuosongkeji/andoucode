package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评价界面
 */
public class ToevaluateActivity extends BaseActivity {

    @BindView(R.id.iv_goods_img)
    ImageView mGoodsImg;

    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar mSimpleRatingBar;

    @BindView(R.id.et_add_comment_content)
    EditText mAddCommentContent;

    private String goodsId;
    private String orderId;
    private String merchantsId;
    private String img;

    private String content;
    private String ratingNum;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_toevaluate);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("发表评论");
        goodsId = getIntent().getStringExtra("goods_id");
        orderId = getIntent().getStringExtra("order_id");
        merchantsId = getIntent().getStringExtra("merchants_id");
        img = getIntent().getStringExtra("img");


        Glide.with(this).load(BaseUrl.BASE_URL+img).apply(new RequestOptions()
                .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into(mGoodsImg);



    }

    @Override
    protected void initViews() {


    }

    @Override
    public void getDataFromServer() {


    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public static void start(String goods_id,String order_id,String merchants_id,String img){

        Bundle bundle=new Bundle();
        bundle.putSerializable("goods_id",goods_id);
        bundle.putSerializable("order_id",order_id);
        bundle.putSerializable("merchants_id",merchants_id);
        bundle.putSerializable("img",img);
        ActivityUtils.startActivity(bundle,ToevaluateActivity.class);


    }


    @OnClick(R.id.btn_add_comment)
    public void comment(){

        //获得星级
        float rating = mSimpleRatingBar.getRating();
        ratingNum = Float.toString(rating);

        content = mAddCommentContent.getText().toString();

        Log.d(TAG, "发表评论: "+" "+goodsId+" "+orderId+" "+merchantsId+" "+ratingNum+" "+content);

        HttpRxObservable.getObservable(ApiUtils.getApiService().addcomment(

                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                goodsId,
                orderId,
                merchantsId,
                content,
                ratingNum
        )).subscribe(new BaseObserver<Object>(mAt) {
            @Override
            public void onHandleSuccess(Object o) throws IOException {
                startActivity(new Intent(mAt,CommentSuccessActivity.class));
            }
        });

    }


}
