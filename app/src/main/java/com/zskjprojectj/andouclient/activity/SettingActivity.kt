package com.zskjprojectj.andouclient.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.tencent.bugly.beta.Beta
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.LoginActivity
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.http.BaseObserver
import com.zskjprojectj.andouclient.utils.*
import com.zskjprojectj.andouclient.utils.UrlUtil.getImageUrl
import com.zskjprojectj.andouclient.view.PromtOnlyExtraDialog
import kotlinx.android.synthetic.main.activity_setting.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "设置")
        checkUpdateBtn.setOnClickListener {
            Beta.checkUpgrade(true, false)
        }
        img_setpic.setOnClickListener(View.OnClickListener { startSelectPic(555) })
        btn_exit.setOnClickListener(View.OnClickListener {
            RequestUtil.request(mActivity, true, false,
                    { ApiUtils.getApiService().quit(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                    {
                        LoginInfoUtil.saveLoginInfo("", "")
                        finish()
                        val intent = Intent(mActivity, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        ActivityUtils.startActivity(intent)
                    })
        })
        rl_modifythenickname.setOnClickListener { ActivityUtils.startActivity(ModifythenicknameActivity::class.java) }
        rl_modifythephone.setOnClickListener { ActivityUtils.startActivity(ModifythephoneActivity::class.java) }
        rl_modifythepassword.setOnClickListener { ActivityUtils.startActivity(ModifythepasswordActivity::class.java) }
        rl_modifyfeedback.setOnClickListener { ActivityUtils.startActivity(ModifyfeedbackActivity::class.java) }
        rl_modifyaboutus.setOnClickListener { ActivityUtils.startActivity(ModifyaboutusActivity::class.java) }
        rl_clear.setOnClickListener {
            val s = tv_msize.text.toString()
            val promtOnlyExtraDialog = PromtOnlyExtraDialog(mActivity,
                    R.style.dialog, "系统提示", "是否清理缓存$s", "清理",
                    PromtOnlyExtraDialog.OnCloseListener { dialog, confirm ->
                if (confirm) {
                    DataCleanManager.clearAllCache(mActivity)
                    ToastUtil.showToast("清理成功!")
                    tv_msize.text = "0kb"
                }
            })
            promtOnlyExtraDialog.show()
        }
        protocolContainer.setOnClickListener { v: View? -> dialog = DialogUtil.showProtocolDialogNoBtns(mActivity) }
        tv_usersetversion.text = "当前版本" + AppUtils.getAppVersionName()
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().set(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                {
                    tv_usersetname.text = it.data.name
                    tv_usersetphone.text = it.data.mobile
                    Glide.with(mActivity).load(getImageUrl(it.data.avator))
                            .into(img_setpic)
                })
    }

    private fun startSelectPic(requestCode: Int) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .isCamera(true)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(requestCode)
    }

    var dialog: Dialog? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == BaseObserver.REQUEST_CODE_LOGIN) { //            getDataFromServer();
            return
        }
        var path = PictureSelector.obtainMultipleResult(data)[0].androidQToPath
        if (TextUtils.isEmpty(path)) {
            path = PictureSelector.obtainMultipleResult(data)[0].path
        }
        var imageView: ImageView? = null
        when (requestCode) {
            555 -> imageView = img_setpic
        }
        val file = File(path)
        val requestFile = RequestBody.create(MediaType.parse("image/jpg"), file)
        val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
        val uid = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getUid())
        val token = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getToken())
        val finalImageView = imageView
        RequestUtil.request(mActivity, true, false,
                { ApiUtils.getApiService().uploadImg(uid, token, body) },
                {
                    RequestUtil.request(mActivity, true, false,
                            { ApiUtils.getApiService().user_pictures(LoginInfoUtil.getUid(), LoginInfoUtil.getToken(), it.data) },
                            { _ ->
                                Glide.with(mActivity).load(getImageUrl(it.data)).into(finalImageView!!)
                            })
                })
    }

    override fun onResume() {
        super.onResume()
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().set(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                {
                    tv_usersetname.text = it.data.name
                    tv_usersetphone.text = it.data.mobile
                    Glide.with(mActivity).load(getImageUrl(it.data.avator))
                            .into(img_setpic)
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
    }

    override fun getContentView(): Int {
        return R.layout.activity_setting
    }
}