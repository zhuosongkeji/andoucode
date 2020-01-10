package com.zhuosongkj.android.library.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormatUtil {

    public static String getMoneyString(double money) {
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(money);
    }

    public static String getDateString1(long mills) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(mills);
    }

    public static String getDateString2(long mills) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return format.format(mills);
    }
}
