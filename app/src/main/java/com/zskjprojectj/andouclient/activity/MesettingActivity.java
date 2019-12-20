package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

import butterknife.BindView;

/**
 * 个人中心设置界面
 */
public class MesettingActivity extends BaseActivity {
   private RelativeLayout rl_modifythephone,rl_modifythepassword,rl_modifyfeedback,rl_modifyaboutus;
   private Button btn_exit;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("设置");
    }

    @Override
    protected void initViews() {
        rl_modifythephone=findViewById(R.id.rl_modifythephone);
        rl_modifythepassword=findViewById(R.id.rl_modifythepassword);
        rl_modifyfeedback=findViewById(R.id.rl_modifyfeedback);
        rl_modifyaboutus=findViewById(R.id.rl_modifyaboutus);
//        btn_exit=findViewById(R.id.btn_exit);
//        btn_exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.exit(0);
//            }
//        });
        /**
         * 电话号码修改
         */
//        rl_modifythephone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                jumpActivity(ModifythephoneActivity.class);
//            }
//        });
//        /**
//         * 设置密码
//         */
//        rl_modifythepassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                jumpActivity(ModifythepasswordActivity.class);
//            }
//        });
        /**
         * 意见反馈
         */
        rl_modifyfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(ModifyfeedbackActivity.class);
            }
        });
        /**
         * 关于我们
         */
        rl_modifyaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(ModifyaboutusActivity.class);
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
