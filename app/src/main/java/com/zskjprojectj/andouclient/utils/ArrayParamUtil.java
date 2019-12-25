package com.zskjprojectj.andouclient.utils;

public class ArrayParamUtil {

    public static String getParam(String[] params) {
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            builder.append(param).append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }
}
