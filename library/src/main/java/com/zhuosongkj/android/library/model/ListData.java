package com.zhuosongkj.android.library.model;

import java.util.ArrayList;

/**
 * 项目名称： andoucode
 * 包名：com.zhuosongkj.android.library.model
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/14 9:58
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ListData<T> extends ArrayList<T> implements IListData<T> {
    @Override
    public ArrayList<T> getDataList() {
        return this;
    }
}
