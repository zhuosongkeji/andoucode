package com.zskjprojectj.andouclient.entity;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/19 15:03
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MerchantHomeTypeBean {


    private List<MerchantTypeBean> merchant_type;

    public List<MerchantTypeBean> getMerchant_type() {
        return merchant_type;
    }

    public void setMerchant_type(List<MerchantTypeBean> merchant_type) {
        this.merchant_type = merchant_type;
    }

    public static class MerchantTypeBean {
        /**
         * id : 2
         * type_name : 商城商家
         */

        private String id;
        private String type_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}
