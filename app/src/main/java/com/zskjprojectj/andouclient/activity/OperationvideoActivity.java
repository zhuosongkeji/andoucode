package com.zskjprojectj.andouclient.activity;

import android.net.Uri;
import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * 操作视频
 */
public class OperationvideoActivity extends BaseActivity {
    private JzvdStd jzvdStd;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_operationvideo);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("操作视频");
    }

    @Override
    protected void initViews() {
        jzvdStd=findViewById(R.id.jz_video);
        jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , "");
        jzvdStd.thumbImageView.setImageURI(Uri.parse("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
