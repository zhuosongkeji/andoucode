package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 酒店订单去评价界面
 */
public class HotelordergotoevaluationActivity extends BaseActivity {

    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar mSimpleRatingBar;

    @BindView(R.id.tv_evaluate)
    TextView mTvEvaluate;

    @BindView(R.id.iv_like)
    ImageView mIvLike;

    @BindView(R.id.et_evaluate_content)
    EditText mEtEvaluateContent;

    private String merchant_id;
    private String id;
    private String book_sn;
    private String likeStatus = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        merchant_id = getIntent().getStringExtra("merchant_id");
        id = getIntent().getStringExtra("id");
        book_sn = getIntent().getStringExtra("book_sn");
        ActionBarUtil.setTitle(mActivity, "发布评论");
        mSimpleRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                mTvEvaluate.setText(getEvaluate((int) rating));
            }
        });
    }

    @OnClick({R.id.iv_like, R.id.btn_evaluate})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.iv_like:
                mIvLike.setSelected(!mIvLike.isSelected());
                if (mIvLike.isSelected()) {
                    likeStatus = "1";
                } else {
                    likeStatus = "0";
                }
                break;
            case R.id.btn_evaluate:
                float rating1 = mSimpleRatingBar.getRating();
                int rat = (int) rating1;
                String rating = String.valueOf(rat);
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().addhotelcomment(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                id,
                                book_sn,
                                merchant_id,
                                mEtEvaluateContent.getText().toString(),
                                rating,
                                likeStatus
                        ),
                        result -> {
                            ToastUtils.showShort("评价成功");
                            finish();
                        });
                break;
        }


    }

    public static void start(String merchant_id, String id, String book_sn) {

        Bundle bundle = new Bundle();
        bundle.putString("merchant_id", merchant_id);
        bundle.putString("id", id);
        bundle.putString("book_sn", book_sn);
        ActivityUtils.startActivity(bundle, HotelordergotoevaluationActivity.class);
    }

    private String getEvaluate(int rating) {
        switch (rating) {
            case 1:
                return "非常差";
            case 2:
                return "差";
            case 3:
                return "一般";
            case 4:
                return "满意";
            case 5:
                return "非常满意";
            default:
                return "";

        }

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_hotelordergotoevaluation;
    }
}
