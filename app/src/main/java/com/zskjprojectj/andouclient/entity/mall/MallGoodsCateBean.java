package com.zskjprojectj.andouclient.entity.mall;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/30 16:56
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallGoodsCateBean {


    /**
     * id : 一级分类id
     * name : 一级分类名字
     * towcate : [{"id":"二级分类id","name":"二级分类名字","img":"分类图片"}]
     */

    private String id;
    private String name;
    private List<TowcateBean> towcate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TowcateBean> getTowcate() {
        return towcate;
    }

    public void setTowcate(List<TowcateBean> towcate) {
        this.towcate = towcate;
    }

    public static class TowcateBean {
        /**
         * id : 二级分类id
         * name : 二级分类名字
         * img : 分类图片
         */

        private String id;
        private String name;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
