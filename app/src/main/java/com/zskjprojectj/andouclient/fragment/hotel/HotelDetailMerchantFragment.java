package com.zskjprojectj.andouclient.fragment.hotel;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseFragment;

import butterknife.BindView;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.hotel
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/7 9:35
 * des: 酒店详情 商家
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailMerchantFragment extends BaseFragment {

    @BindView(R.id.wv_details)
    WebView mWvDetailsView;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String desc = bundle.getString("desc");

        String htmlData = getHtmlData(desc);
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

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_hotel_detail_merchant;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {

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
}
