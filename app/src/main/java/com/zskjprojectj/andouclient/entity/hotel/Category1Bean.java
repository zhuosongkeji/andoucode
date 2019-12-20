package com.zskjprojectj.andouclient.entity.hotel;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/20 15:09
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Category1Bean {

    private String content;
    private List<Category2Bean> category2Beans;

    public List<Category2Bean> getCategory2Beans() {
        return category2Beans;
    }

    public void setCategory2Beans(List<Category2Bean> category2Beans) {
        this.category2Beans = category2Beans;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class Category2Bean{
        private String content2;
        private List<Category3Bean> category3Beans;

        public String getContent2() {
            return content2;
        }

        public void setContent2(String content2) {
            this.content2 = content2;
        }

        public List<Category3Bean> getCategory3Beans() {
            return category3Beans;
        }

        public void setCategory3Beans(List<Category3Bean> category3Beans) {
            this.category3Beans = category3Beans;
        }

        public static class Category3Bean{
            private String content3;

            public String getContent3() {
                return content3;
            }

            public void setContent3(String content3) {
                this.content3 = content3;
            }
        }
    }
}
