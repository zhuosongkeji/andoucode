package com.zhuosongkj.android.library.exception;

public class ApiException extends Exception {

    /***
     * 数据错误类型
     */
    public static final String TYPE_NETWORK_ERROR = "0x10";//没有网络
    public static final String TYPE_JSON_ERROR = "0x20";//解析错误
    public static final String TYPE_PARAMS_ERROR = "0x30";//参数错误
    public static final String TYPE_OUT_TIME_ERROR = "0x40";//超时连接
    public static final String TYPE_USER_CANCEL = "0x50";//用户自己取消
    public static final String TYPE_SYSTEM = "0x60";//引起系统的错误

    public static final String TYPE_NOT_LOGIN = "666666"; // 未登录
    public static final String TYPE_REQUEST_ERROR = "-1"; // 数据请求错误
    public static final int TYPE_NONE = 0;

    private String errorCode;

    public ApiException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return super.toString() + "   errorCode=" + errorCode;
    }
}
