package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

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

    @BindView(R.id.iv_like)
    ImageView mIvLike;

    private String goodsId;
    private String orderId;
    private String merchantsId;
    private String img;
    private String content;
    private String ratingNum;
    private int likeStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "发表评论");
        goodsId = getIntent().getStringExtra("goods_id");
        orderId = getIntent().getStringExtra("order_id");
        merchantsId = getIntent().getStringExtra("merchants_id");
        img = getIntent().getStringExtra("img");
        Glide.with(this).load(UrlUtil.INSTANCE.getImageUrl(img)).apply(new RequestOptions()
                .placeholder(R.mipmap.ic_placeholder)).into(mGoodsImg);
    }


    public static void start(String goods_id, String order_id, String merchants_id, String img) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods_id", goods_id);
        bundle.putSerializable("order_id", order_id);
        bundle.putSerializable("merchants_id", merchants_id);
        bundle.putSerializable("img", img);
        ActivityUtils.startActivity(bundle, ToevaluateActivity.class);
    }

    @OnClick({R.id.iv_like,R.id.btn_add_comment})
    public void comment(View view) {
        switch (view.getId()) {
            case R.id.iv_like:
                mIvLike.setSelected(!mIvLike.isSelected());
                if (mIvLike.isSelected()) {
                    likeStatus = 1;
                } else {
                    likeStatus = 0;
                }
                break;
            case R.id.btn_add_comment:
                //获得星级
                float rating = mSimpleRatingBar.getRating();
                ratingNum = Float.toString(rating);
                content = mAddCommentContent.getText().toString();
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().addcomment(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                goodsId,
                                orderId,
                                merchantsId,
                                content,
                                ratingNum,
                                likeStatus
                                ),
                        result -> {
                            startActivity(new Intent(mActivity, CommentSuccessActivity.class));
                        });
                break;
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_toevaluate;
    }
}
