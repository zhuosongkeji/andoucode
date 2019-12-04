package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.view.TopView;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/23
 *     desc   :
 *     version: 1.0
 * </pre>
 *
 * @author yizhubao
 */
public class RegisteredActivity extends BaseActivity {
    private TopView topView;
    //设置布局文件
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_registered);
    }
    //设置配置数据可以添加某些权限，这几个方法在使用的时候特别注意执行顺序
    @Override
    protected void initData(Bundle savedInstanceState) {

    }
    //初始化对象
    @Override
    protected void initViews() {
        topView=findViewById(R.id.alltopview);
        topView.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //获取网络数据现成请求在这里处理
    @Override
    public void getDataFromServer() {

    }
    //创建Presenter这个方法可以不管
    @Override
    protected BasePresenter createPresenter() { return null; }
}
