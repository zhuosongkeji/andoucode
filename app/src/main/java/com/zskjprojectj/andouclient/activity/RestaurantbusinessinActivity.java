package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import cn.yhq.dialog.core.DialogBuilder;

/**
 * 在线预订
 */
public class RestaurantbusinessinActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "饭店商家入驻");
        Button btn_applicationin = findViewById(R.id.btn_applicationin);
        btn_applicationin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToastUtil.showToast("申请已提交,请时刻注意审核信息,感谢你的信任支持");
                DialogBuilder.messageDialog(mActivity).setMessage("申请已提交,请时刻注意审核信息,感谢你的信任支持").show();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurantbusinessin;
    }
}
