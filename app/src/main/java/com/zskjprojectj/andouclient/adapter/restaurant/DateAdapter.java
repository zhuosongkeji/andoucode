package com.zskjprojectj.andouclient.adapter.restaurant;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAdapter extends BaseAdapter<Date> {
    public DateAdapter() {
        super(R.layout.layout_date_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Date item) {
        helper.setText(R.id.dayTxt, TimeUtils.getChineseWeek(item))
                .setText(R.id.dateTxt,
                        TimeUtils.date2String(item, new SimpleDateFormat("MM-dd", Locale.getDefault())));
        helper.itemView.setSelected(isSelect(item));
    }
}
