package com.zskjprojectj.andouclient.fragment.hotel;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.zskjprojectj.andouclient.R;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/9 16:41
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CustomViewDialog extends Dialog implements View.OnClickListener {
    private Context context;//上下文
    private int layoutResID;//布局文件id
    private int[] listenedItem;//监听的控件id

    public CustomViewDialog(@NonNull Context context, int layoutResID, int[] listenedItem) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItem = listenedItem;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提前设置Dialog的一些样式
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);//设置dialog显示居中
//        dialogWindow.setWindowAnimations();//设置动画效果
        setContentView(layoutResID);
        setCanceledOnTouchOutside(false);

        for(int id:listenedItem){
            findViewById(id).setOnClickListener(this);
        }
    }

    private OnCenterItemClickListener listener;
    public interface OnCenterItemClickListener {
        void OnCenterItemClick(CustomViewDialog dialog, View view);
    }

    //很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        listener.OnCenterItemClick(this,v);
    }
}
