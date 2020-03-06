package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.AboutusBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.io.IOException;

/**
 * 关于我们
 */
public class ModifyaboutusActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "关于我们");
        ImageView img_about = findViewById(R.id.img_about);
        TextView tv_abouttitle = findViewById(R.id.tv_abouttitle);
        TextView tv_aboutcontent = findViewById(R.id.tv_aboutcontent);
        TextView tv_aboutvalue = findViewById(R.id.tv_aboutvalue);
        TextView tv_aboutcopyright = findViewById(R.id.tv_aboutcopyright);
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().about(),
                result -> {
                    Glide.with(mActivity).load(UrlUtil.INSTANCE.getImageUrl(result.data.getImage()))
                            .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder)).into(img_about);
                    tv_abouttitle.setText(result.data.getTitle());
                    tv_aboutcontent.setText(result.data.getContent());
                    tv_aboutvalue.setText("当前版本" + result.data.getValue());
                    tv_aboutcopyright.setText("版权所有:" + result.data.getCopyright());
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modifyaboutus;
    }
}
