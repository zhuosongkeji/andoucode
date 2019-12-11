package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import cn.yhq.dialog.core.DialogBuilder;

/**
 * 在线预订
 */
public class RestaurantbusinessinActivity extends BaseActivity {
    private Button btn_applicationin;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_restaurantbusinessin);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("饭店商家入驻");
    }

    @Override
    protected void initViews() {
        btn_applicationin=findViewById(R.id.btn_applicationin);
        btn_applicationin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToastUtil.showToast("申请已提交,请时刻注意审核信息,感谢你的信任支持");
                DialogBuilder.messageDialog(mAt).setMessage("申请已提交,请时刻注意审核信息,感谢你的信任支持").show();
            }
        });
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
