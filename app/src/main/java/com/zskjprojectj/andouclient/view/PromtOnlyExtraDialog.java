package com.zskjprojectj.andouclient.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;


/**
 * Created by admin on 2018/4/19.
 */

public class PromtOnlyExtraDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private String content;
    private TextView mTvContent;
    private TextView submit;

    private String title;
    private String subtext;
    private OnCloseListener listener;


    public PromtOnlyExtraDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public PromtOnlyExtraDialog(Context context, int themeResId, String mtitle, String content, OnCloseListener onCloseListener) {
        super(context, themeResId);
        this.mContext = context;
        this.title=mtitle;
        this.content = content;
        this.listener=onCloseListener;
    }

    public PromtOnlyExtraDialog(Context context, int themeResId, String mtitle, String content, String subtxt, OnCloseListener onCloseListener) {
        super(context, themeResId);
        this.mContext = context;
        this.title=mtitle;
        this.content = content;
        this.subtext=subtxt;
        this.listener=onCloseListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_only_extra_prompt);

        initView();

        setCanceledOnTouchOutside(true);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int widthPixels=displayMetrics.widthPixels;
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width= (int) (widthPixels*0.8);
        dialogWindow.setAttributes(lp);
    }
    private TextView mTvtitle;
    private ImageView mClose;
    private void initView() {
        mTvContent = findViewById(R.id.content);
        mTvtitle=findViewById(R.id.title);
        mClose=findViewById(R.id.mClose);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        mTvContent.setText(content);
        if (!TextUtils.isEmpty(title)){
            mTvtitle.setText(title);
        }
        if (!TextUtils.isEmpty(subtext)){
            submit.setText(subtext);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (listener!=null){
                    listener.onClick(this,true);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }

}
