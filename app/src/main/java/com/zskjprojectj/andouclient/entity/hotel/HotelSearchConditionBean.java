package com.zskjprojectj.andouclient.entity.hotel;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2020/1/6 14:13
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelSearchConditionBean {

    private List<StarBean> star;
    private List<PriceRangeBean> price_range;

    public List<StarBean> getStar() {
        return star;
    }

    public void setStar(List<StarBean> star) {
        this.star = star;
    }

    public List<PriceRangeBean> getPrice_range() {
        return price_range;
    }

    public void setPrice_range(List<PriceRangeBean> price_range) {
        this.price_range = price_range;
    }

    public static class StarBean {
        /**
         * id : 星级id
         * name : 星级名字
         */

        private String id;
        private String name;

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
    }

    public static class PriceRangeBean {
        /**
         * start : 最小价格
         * end : 最大价格
         */

        private String start;
        private String end;

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }
    }
}
