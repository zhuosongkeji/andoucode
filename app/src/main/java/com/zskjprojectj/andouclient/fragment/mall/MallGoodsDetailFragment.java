package com.zskjprojectj.andouclient.fragment.mall;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.mall.MallDetailsBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;

import java.io.IOException;

import butterknife.BindView;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/11 14:42
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallGoodsDetailFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.wv_details)
    WebView mWvDetailsView;


    private String goodId;


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mall_goods_details;
    }

    @Override
    protected void getDataFromServer() {

        Log.d(TAG, "getDataFromServer:bundle " + goodId);
        //商品详情
        HttpRxObservable.getObservable(ApiUtils.getApiService().mallDetails(goodId)).subscribe(new BaseObserver<MallDetailsBean>(mAty) {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onHandleSuccess(MallDetailsBean mallDetailsBean) throws IOException {
                String details = mallDetailsBean.getDetails();
                Log.d(TAG, "onHandleSuccess:+ " + details);
                String htmlData = getHtmlData(details);
                //图片宽度改为100%  高度为自适应
//                String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
                mWvDetailsView.loadData(htmlData,"text/html", "UTF-8");
                mWvDetailsView.setVerticalScrollBarEnabled(false);
                mWvDetailsView.setHorizontalScrollBarEnabled(false);

                mWvDetailsView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return (event.getAction() == MotionEvent.ACTION_MOVE);
                    }
                });
            }
        });
    }

    /**
     * 拼接html字符串片段
     * @param bodyHTML
     * @return
     */
    private  String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width:100% !important; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body style:'height:auto;max-width: 100%; width:auto;'>" + bodyHTML + "</body></html>";
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        goodId = bundle.getString("id");


    }
}
