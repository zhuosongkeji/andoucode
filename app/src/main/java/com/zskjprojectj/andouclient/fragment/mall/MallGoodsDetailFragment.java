package com.zskjprojectj.andouclient.fragment.mall;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;

import butterknife.BindView;

public class MallGoodsDetailFragment extends BaseFragment {

    @BindView(R.id.wv_details)
    WebView mWvDetailsView;


    private String goodId;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        goodId = bundle.getString("id");
        RequestUtil.request(mActivity, true, false,
                () -> ApiUtils.getApiService().mallDetails(goodId),
                result -> {
                    String details = result.data.getDetails();
                    String htmlData = getHtmlData(details);
                    mWvDetailsView.loadData(htmlData, "text/html", "UTF-8");
                    mWvDetailsView.setVerticalScrollBarEnabled(false);
                    mWvDetailsView.setHorizontalScrollBarEnabled(false);
                    mWvDetailsView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return (event.getAction() == MotionEvent.ACTION_MOVE);
                        }
                    });
                });
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width:100% !important; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body style:'height:auto;max-width: 100%; width:auto;'>" + bodyHTML + "</body></html>";
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mall_goods_details;
    }
}
