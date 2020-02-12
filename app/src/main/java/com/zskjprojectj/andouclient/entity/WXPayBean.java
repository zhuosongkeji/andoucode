package com.zskjprojectj.andouclient.entity;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/30 11:04
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WXPayBean {

    /**
     * appid : wxa2ea994d7f5b42e9
     * mch_id : 1527302001
     * nonce_str : T25XPt3Vi4CfsxTe
     * prepay_id : wx30110404158962b93706ca221295238700
     * result_code : SUCCESS
     * return_code : SUCCESS
     * return_msg : OK
     * sign : 2F426928074410584D2852CF9B90B2EA
     * trade_type : APP
     * timestamp : 1577675044
     */

    private String appid;
    private String mch_id;
    private String nonce_str;
    private String prepay_id;
    private String result_code;
    private String return_code;
    private String return_msg;
    private String sign;
    private String trade_type;
    private String timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
