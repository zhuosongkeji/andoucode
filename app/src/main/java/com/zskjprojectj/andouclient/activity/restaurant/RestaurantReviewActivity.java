package com.zskjprojectj.andouclient.activity.restaurant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.RestaurantOrder;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

public class RestaurantReviewActivity extends BaseActivity {

    @BindView(R.id.ratingBar)
    ScaleRatingBar ratingBar;
    @BindView(R.id.likeBtn)
    View likeBtn;
    @BindView(R.id.contentEdt)
    EditText contentEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "发表评论");
        ratingBar.setOnRatingChangeListener((ratingBar, rating, fromUser) ->
                ViewUtil.setText(mActivity, R.id.reviewTxt, getEvaluate(rating)));
        likeBtn.setOnClickListener(v -> likeBtn.setSelected(!likeBtn.isSelected()));
    }

    @OnClick(R.id.submitBtn)
    void onSubmitBtnClick() {
        RestaurantOrder order = (RestaurantOrder) getIntent().getSerializableExtra(KEY_DATA);
        int star = (int) ratingBar.getRating();
        if (star == 0) {
            ToastUtil.showToast("请为该饭店评级!");
            return;
        }
        RequestUtil.request(mActivity, true, false,
                () -> ApiUtils.getApiService().addComment(
                        LoginInfoUtil.getUid(),
                        order.order_sn,
                        order.merchant_id,
                        contentEdt.getText().toString(),
                        String.valueOf(star),
                        "",
                        likeBtn.isSelected() ? 1 : 0), result -> {
                    ToastUtil.showToast(result.msg);
                    setResult(Activity.RESULT_OK);
                    finish();
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_review;
    }

    private String getEvaluate(float rating) {
        if (rating == 1) {
            return "非常差";
        } else if (rating == 2) {
            return "差";
        } else if (rating == 3) {
            return "一般";
        } else if (rating == 4) {
            return "满意";
        } else if (rating == 5) {
            return "非常满意";
        }
        return "";
    }

    public static void start(Activity activity, RestaurantOrder order, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, order);
        ActivityUtils.startActivityForResult(bundle, activity, RestaurantReviewActivity.class, requestCode);
    }
}
