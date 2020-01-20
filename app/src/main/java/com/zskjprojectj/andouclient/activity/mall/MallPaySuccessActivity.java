package com.zskjprojectj.andouclient.activity.mall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.zxing.ViewfinderView;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MainActivity;
import com.zskjprojectj.andouclient.activity.ShoporderActivity;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.utils.PaySuccessBackEvent;
import com.zskjprojectj.andouclient.utils.PaySuccessEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class MallPaySuccessActivity extends BaseActivity {

    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mall_pay_success);
    }

    @Override
    protected void initViews() {
        getBarDistance(mHeaderTitleView);
        mHeaderTitle.setText("支付成功");
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

    @OnClick({R.id.iv_header_back, R.id.btn_see_order})
    public void clickBack(View view) {
        switch (view.getId()) {
            case R.id.iv_header_back:
                EventBus.getDefault().post(new PaySuccessBackEvent());
                finish();
                break;
            case R.id.btn_see_order:
//                startActivity(new Intent(MallPaySuccessActivity.this,));
//                Intent intent=new Intent(MallPaySuccessActivity.this, ShoporderActivity.class);
//                intent.putExtra("flag","MallPaySuccess");
//                startActivity(intent);
                finish();
                EventBus.getDefault().post(new PaySuccessEvent());
                break;

            default:
                break;
        }

    }
}
