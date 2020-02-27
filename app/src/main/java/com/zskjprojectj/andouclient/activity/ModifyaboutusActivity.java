package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
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
    private ImageView img_about;
    private TextView tv_abouttitle,tv_aboutcontent,tv_aboutvalue,tv_aboutcopyright;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_modifyaboutus);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("关于我们");
    }

    @Override
    protected void initViews() {
        img_about=findViewById(R.id.img_about);
        tv_abouttitle=findViewById(R.id.tv_abouttitle);
        tv_aboutcontent=findViewById(R.id.tv_aboutcontent);
        tv_aboutvalue=findViewById(R.id.tv_aboutvalue);
        tv_aboutcopyright=findViewById(R.id.tv_aboutcopyright);
    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().about()).subscribe(new BaseObserver<AboutusBean>(mAt) {

            @Override
            public void onHandleSuccess(AboutusBean aboutusBean) throws IOException {
                Glide.with(mAt).load(UrlUtil.INSTANCE.getImageUrl(aboutusBean.getImage()))
                        .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder)).into(img_about);
                tv_abouttitle.setText(aboutusBean.getTitle());
                tv_aboutcontent.setText(aboutusBean.getContent());
                tv_aboutvalue.setText("当前版本"+aboutusBean.getValue());
                tv_aboutcopyright.setText("版权所有:"+aboutusBean.getCopyright());
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
