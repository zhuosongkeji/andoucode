package com.zskjprojectj.andouclient.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.MyAddressAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.KEY_DATA
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_my_address.*

class MyAddressActivity : BaseActivity() {
    var adapter = MyAddressAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "我的地址")
        addBtn.setOnClickListener {
            ActivityUtils.startActivityForResult(mActivity, NewAddressActivity::class.java, 666)
        }
        adapter.bindToRecyclerView(recyclerView)
        adapter.setEmptyView(R.layout.layout_empty_view, recyclerView)
        adapter.setOnItemChildClickListener { _, view, position: Int ->
            if (view.id == R.id.deleteBtn) {
                RequestUtil.request(mActivity, true, false,
                        {
                            ApiUtils.getApiService().delAddress(
                                    LoginInfoUtil.getUid(),
                                    LoginInfoUtil.getToken(),
                                    adapter.getItem(position)!!.id
                            )
                        },
                        {
                            ToastUtil.showToast("删除成功!")
                            loadData()
                        })
            } else if (view.id == R.id.editBtn) {
                NewAddressActivity.start(mActivity, adapter.getItem(position), 666)
            }
        }
        if (intent.getBooleanExtra(KEY_DATA, false)) {
            adapter.setOnItemClickListener { _, _, position ->
                val intent = Intent()
                intent.putExtra("name", adapter.getItem(position)?.name)
                intent.putExtra("address", adapter.getItem(position)?.address)
                intent.putExtra("tel", adapter.getItem(position)?.mobile)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
        loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            loadData()
        }
    }

    private fun loadData() {
        RequestUtil.request(mActivity, true, true,
                {
                    ApiUtils.getApiService().address(
                            LoginInfoUtil.getUid()
                            , LoginInfoUtil.getToken()
                    )
                },
                { adapter.setNewData(it.data) })
    }

    override fun getContentView() = R.layout.activity_my_address

    companion object {
        fun start(activity: Activity, isSelect: Boolean = true, requestCode: Int = 666) {
            ActivityUtils.startActivityForResult(
                    bundleOf(Pair(KEY_DATA, isSelect)),
                    activity,
                    MyAddressActivity::class.java,
                    requestCode)
        }
    }
}