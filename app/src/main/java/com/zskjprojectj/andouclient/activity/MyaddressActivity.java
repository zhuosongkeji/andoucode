package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.MyaddressAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Address;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.List;

/**
 * 我的地址
 */
public class MyaddressActivity extends BaseActivity {
    public static final String KEY_DATA = "KEY_DATA";
    private RecyclerView mRecycler;
    private Button btn_addressadd;
    MyaddressAdapter adapter = new MyaddressAdapter();

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_myaddress);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("我的地址");
        adapter.openLoadAnimation();
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.deleteBtn) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().delAddress(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        this.adapter.getItem(position).id
                )).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        ToastUtil.showToast("删除成功!");
                        getDataFromServer();
                    }
                });
            } else if (view.getId() == R.id.editBtn) {
                Intent intent = new Intent(mAt, NewaddressActivity.class);
                intent.putExtra(KEY_DATA, this.adapter.getItem(position));
                startActivityForResult(intent, 666);
            }
        });
        mRecycler.addItemDecoration(new DividerItemDecoration(mAt, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initViews() {
        mRecycler = findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
        btn_addressadd = findViewById(R.id.btn_addressadd);
        btn_addressadd.setOnClickListener(view ->
                startActivityForResult(new Intent(mAt, NewaddressActivity.class), 666));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            getDataFromServer();
        }
    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().address(
                LoginInfoUtil.getUid()
                , LoginInfoUtil.getToken()
        )).subscribe(new BaseObserver<List<Address>>(mAt) {
            @Override
            public void onHandleSuccess(List<Address> addresses) throws IOException {
                adapter.setNewData(addresses);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
