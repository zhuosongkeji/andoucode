package com.zskjprojectj.andouclient.utils;

import android.text.TextUtils;

import com.atuan.datepickerlibrary.CalendarUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.utils
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2020/1/9 9:44
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CalendarViewUtil {

    public static String FormatDateYMD(String date) {
        if (TextUtils.isEmpty(date)) {
            new Throwable();
        }

        String year = date.split("-")[0];
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        return year + "-" + month + "-" + day;
    }

    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0L;

        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / 86400000L;
        } catch (Exception var7) {
            return "";
        }

        return day + "";
    }



}
