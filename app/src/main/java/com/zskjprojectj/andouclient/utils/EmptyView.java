package com.zskjprojectj.andouclient.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;


public class EmptyView extends RelativeLayout {
    public static final int NETWORK_ERROR = 0x10;//网络失败
    public static final int NO_DATA = 2;//没有数据
    public static final int NO_SEARCH = 3;//搜索空
    public static final int NOT_FOUND = 4;
    public static final int NO_LOGIN = 6;
    public static final int OVER_TIME = 0x170;
    private ImageView imageView;
    private TextView textView, emptyTipTx;
    private OnEmptyRefreshListener listener;
    private OnNetErrorRefreshListener onNetErrorRefreshListener;
    private OnEmptyLoginListener loginListener;

    private TextView overTimeTx, overTimeBtn;
    private OnClickListener overTimeBtnListener;

    private String noDataTip;

    public void setLoginListener(OnEmptyLoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
            LayoutInflater.from(context).inflate(R.layout.view_empty_layout, this, true);
        init();
    }

    private void init() {
        imageView = findViewById(R.id.empty_view);
        textView = findViewById(R.id.empty_text);
        emptyTipTx = findViewById(R.id.emptyTipTx);
        overTimeTx = findViewById(R.id.overTimeTx);
        overTimeBtn = findViewById(R.id.overTimeBtn);
    }

    public OnNetErrorRefreshListener getOnNetErrorRefreshListener() {
        return onNetErrorRefreshListener;
    }

    public void setOnNetErrorRefreshListener(OnNetErrorRefreshListener onNetErrorRefreshListener) {
        this.onNetErrorRefreshListener = onNetErrorRefreshListener;
    }

    public void setListener(OnEmptyRefreshListener listener) {
        this.listener = listener;
    }

    public void setEmptyType(int type) {
        switch (type) {
            case NETWORK_ERROR:
                imageView.setImageResource(R.drawable.ic_no_net);
                setTextView("网络竟然崩了");
                emptyTipTx.setText("检查网络后刷新试试");
                emptyTipTx.setVisibility(VISIBLE);
//                setOnClickListener(c -> {
//                    if (onNetErrorRefreshListener != null)
//                        onNetErrorRefreshListener.onNetErrorRefresh();
//                });
                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onNetErrorRefreshListener != null)
                        onNetErrorRefreshListener.onNetErrorRefresh();
                    }
                });
                break;
            case NO_DATA:
                imageView.setImageResource(R.drawable.ic_no_data);
                setTextView("还没有数据哦！到其它地方逛逛吧!");
                emptyTipTx.setVisibility(GONE);
//                setOnClickListener(c -> {
//                    if (listener != null)
//                        listener.onEmptyRefresh();
//                });
                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null)
                          listener.onEmptyRefresh();
                    }
                });
                break;
            case NO_SEARCH:
                //imageView.setImageResource(R.mipmap.no_search_result);
                setTextView("是我词库太少，还是你用词太飘~");
                setOnClickListener(null);
                break;
            case NO_LOGIN:
                setTextView("登录后，可以查看更多信息");
                // imageView.setImageResource(R.mipmap.no_login);
                setOnClickListener(null);

                break;
            case NOT_FOUND:
                //imageView.setImageResource(R.mipmap.not_fond);
                setTextView("您所访问的内容已关闭或暂时无法访问");
                setOnClickListener(null);
                break;
            case OVER_TIME:
                imageView.setVisibility(GONE);
                overTimeBtn.setVisibility(VISIBLE);
                textView.setVisibility(GONE);
                overTimeTx.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(noDataTip))
                    overTimeTx.setText(noDataTip);
                if (overTimeBtnListener != null)
                    overTimeBtn.setOnClickListener(overTimeBtnListener);
                break;
        }
    }

    public void setOverTimeBtnListener(OnClickListener overTimeBtnListener) {
        this.overTimeBtnListener = overTimeBtnListener;
    }

    private String getNoDataTip() {
        return noDataTip == null ? "还没有数据哦！到其它地方逛逛吧!" : noDataTip;
    }

    public void setNoDataTip(String noDataTip) {
        this.noDataTip = noDataTip;
    }

    private void setTextView(String msg) {
        textView.setText(msg);
    }

    public interface OnEmptyRefreshListener {
        void onEmptyRefresh();
    }

    public interface OnNetErrorRefreshListener {
        void onNetErrorRefresh();
    }

    public interface OnEmptyLoginListener {
        void onEmptyLogin();
    }
}
