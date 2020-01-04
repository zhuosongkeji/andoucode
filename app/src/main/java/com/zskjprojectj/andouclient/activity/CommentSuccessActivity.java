package com.zskjprojectj.andouclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentSuccessActivity extends BaseActivity {




    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_comment_success);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.btn_back_home)
    public void clickBack(){
        Intent intent=new Intent(mAt,MallMainActivity.class);
        intent.putExtra("tag","backHome");
        startActivity(intent);
    }
}
