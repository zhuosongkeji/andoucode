package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 酒店订单去评价界面
 */
public class HotelordergotoevaluationActivity extends BaseActivity {

    @BindView(R.id.mTitleView)
    RelativeLayout mTitleView;
    @BindView(R.id.mHeaderTitle)
    TextView mHeaderTitle;

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
    private String likeStatus="0";


    @OnClick({R.id.mHeaderBack, R.id.iv_like,R.id.btn_evaluate})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.mHeaderBack:
                finish();
                break;
            case R.id.iv_like:
                if (mIvLike.isSelected()){
                    likeStatus="0";
                    mIvLike.setSelected(false);
                }else {
                    mIvLike.setSelected(true);

                    likeStatus="1";
                }
//                HttpRxObservable.getObservable(ApiUtils.getApiService().addhotelfabulous(
//                        LoginInfoUtil.getUid(),
//                        LoginInfoUtil.getToken(),
//                        merchant_id
//                )).subscribe(new BaseObserver<Object>(mAt) {
//                    @Override
//                    public void onHandleSuccess(Object o) throws IOException {
//
//                    }
//                });
                break;
            case R.id.btn_evaluate:
                float rating1 = mSimpleRatingBar.getRating();
                int rat= (int) rating1;

                String rating = String.valueOf(rat);
                Log.d(TAG, "clickView: "+likeStatus+" "+rating);
                HttpRxObservable.getObservable(ApiUtils.getApiService().addhotelcomment(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        id,
                        book_sn,
                        merchant_id,
                        mEtEvaluateContent.getText().toString(),
                        rating,
                        likeStatus
                )).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        ToastUtil.showToast("评价成功");
                        finish();
                    }
                });
                break;
        }


    }


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelordergotoevaluation);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHeaderTitle.setText("发布评论");
        getBarDistance(mTitleView);

        mSimpleRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                mTvEvaluate.setText(getEvaluate((int) rating));
            }
        });
    }

    @Override
    protected void initViews() {
        merchant_id = getIntent().getStringExtra("merchant_id");
        id = getIntent().getStringExtra("id");
        book_sn = getIntent().getStringExtra("book_sn");

    }

    @Override
    public void getDataFromServer() {



    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
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


}
