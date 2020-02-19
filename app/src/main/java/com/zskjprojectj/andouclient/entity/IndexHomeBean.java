package com.zskjprojectj.andouclient.entity;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/3 14:50
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class IndexHomeBean {


    private List<BannerBean> banner;
    private ArrayList<MerchantTypeBean> merchant_type;
    private List<MerchantsBean> merchants;
    private List<NoticeBean> notice;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public ArrayList<MerchantTypeBean> getMerchant_type() {
        return merchant_type;
    }

    public void setMerchant_type(ArrayList<MerchantTypeBean> merchant_type) {
        this.merchant_type = merchant_type;
    }

    public List<MerchantsBean> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<MerchantsBean> merchants) {
        this.merchants = merchants;
    }

    public List<NoticeBean> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeBean> notice) {
        this.notice = notice;
    }

    public static class BannerBean extends SimpleBannerInfo {
        /**
         * id : 轮播图id
         * img : 图片地址
         * url : 跳转地址
         */

        private String id;
        private String img;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public Object getXBannerUrl() {
            return "http://andou.zhuosongkj.com/index.php";
        }
    }

    public static class MerchantTypeBean {
        /**
         * id : 商户分类id
         * img : 商户分类图片
         */

        private String id;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class MerchantsBean {
        /**
         * id : 商户id
         * logo_img : 商户logo
         * name : 商户名字
         */

        private String id;
        private String logo_img;
        private String name;
        private String merchant_type_id;

        public String getMerchant_type_id() {
            return merchant_type_id;
        }

        public void setMerchant_type_id(String merchant_type_id) {
            this.merchant_type_id = merchant_type_id;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo_img() {
            return logo_img;
        }

        public void setLogo_img(String logo_img) {
            this.logo_img = logo_img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class NoticeBean {
        /**
         * id : 公告id
         * content : 公告内容
         * updated_at : 更新时间
         */

        private String id;
        private String content;
        private String updated_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
