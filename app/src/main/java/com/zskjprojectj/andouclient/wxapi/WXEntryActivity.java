package com.zskjprojectj.andouclient.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.Constants;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        api.handleIntent(getIntent(), this);
	}
	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {

		int result = 0;

		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:


			String code = ((SendAuth.Resp) resp).code;
			Log.d("ASd_sdx0",code);
			Intent intent=new Intent();
			intent.setAction("wxdl");
			intent.putExtra("code",code);
			sendOrderedBroadcast(intent,null);
			result = R.string.errcode_success;
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = R.string.errcode_cancel;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			break;
		case BaseResp.ErrCode.ERR_UNSUPPORT:
			result = R.string.errcode_unsupported;
			break;
		default:
			result = R.string.errcode_unknown;
			break;
		}
        finish();
	}
	

}