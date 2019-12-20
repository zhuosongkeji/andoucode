package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.fragment.hotel.CustomViewDialog;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelOnlineReserveActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;


    private TextView mReserve;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_online_reserve);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        // 输入对话框
//        new InputDialog.Builder(this)
//                // 标题可以不用填写
//                .setTitle("我是标题")
//                // 内容可以不用填写
//                .setContent("我是内容")
//                // 提示可以不用填写
//                .setHint("我是提示")
//                // 确定按钮文本
//                .setConfirm(getString(R.string.common_confirm))
//                // 设置 null 表示不显示取消按钮
//                .setCancel(getString(R.string.common_cancel))
//                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
//                .setListener(new InputDialog.OnListener() {
//
//                    @Override
//                    public void onConfirm(BaseDialog dialog, String content) {
//                        toast("确定了：" + content);
//                    }
//
//                    @Override
//                    public void onCancel(BaseDialog dialog) {
//                        toast("取消了");
//                    }
//                })
//                .show();

    }

    @Override
    protected void initViews() {
        getBarDistance(mHeaderTitleView);
        mHeaderTitle.setText("在线预订");
        mReserve=findViewById(R.id.tv_reserve);
        mReserve.setOnClickListener(this);
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_reserve:
                CustomViewDialog dialog=new CustomViewDialog(this,R.layout.hotel_pay_hint,new int[]{R.id.bt_cancel,R.id.bt_go_on});
                    dialog.setOnCenterItemClickListener(new CustomViewDialog.OnCenterItemClickListener() {
                        @Override
                        public void OnCenterItemClick(CustomViewDialog dialog, View view) {
                            switch (view.getId()){
                                case R.id.bt_cancel:
                                    dialog.dismiss();
                                    break;
                                case R.id.bt_go_on:
                                    ToastUtil.showToast("订房成功");
                                    break;
                            }
                        }
                    });
                    dialog.show();
                break;
        }
    }

@OnClick(R.id.iv_header_back)
    public void clickBack(){
        finish();
}

}
