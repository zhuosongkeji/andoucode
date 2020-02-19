package com.zskjprojectj.andouclient.model;

import java.util.List;


public class PinTuanDetails {

    /**
     * group_goods : {"price":"团购价格","storage":"团购库存","top_member":"单团人数上限","begin_time":"团购开始时间","finish_time":"团购结束时间","code":"状态0上架 1下架","sale_total":"团购销量"}
     * total_member : 参与团购总人数
     * team_list : [{"group_id":"团队id","left_member":"剩余空位","captain_avatar":"队长头像"},{"group_id":1,"left_member":8,"captain_avatar":"http://andou.test/images/7520e6faa309a1eed8a4fd95fb49770.jpg"}]
     */

    private GroupGoodsBean group_goods;
    private String total_member;
    private List<TeamListBean> team_list;

    public GroupGoodsBean getGroup_goods() {
        return group_goods;
    }

    public void setGroup_goods(GroupGoodsBean group_goods) {
        this.group_goods = group_goods;
    }

    public String getTotal_member() {
        return total_member;
    }

    public void setTotal_member(String total_member) {
        this.total_member = total_member;
    }

    public List<TeamListBean> getTeam_list() {
        return team_list;
    }

    public void setTeam_list(List<TeamListBean> team_list) {
        this.team_list = team_list;
    }

    public static class GroupGoodsBean {
        /**
         * price : 团购价格
         * storage : 团购库存
         * top_member : 单团人数上限
         * begin_time : 团购开始时间
         * finish_time : 团购结束时间
         * code : 状态0上架 1下架
         * sale_total : 团购销量
         */

        private String price;
        private String storage;
        private String top_member;
        private String begin_time;
        private String finish_time;
        private String code;
        private String sale_total;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStorage() {
            return storage;
        }

        public void setStorage(String storage) {
            this.storage = storage;
        }

        public String getTop_member() {
            return top_member;
        }

        public void setTop_member(String top_member) {
            this.top_member = top_member;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSale_total() {
            return sale_total;
        }

        public void setSale_total(String sale_total) {
            this.sale_total = sale_total;
        }
    }

    public static class TeamListBean {
        /**
         * group_id : 团队id
         * left_member : 剩余空位
         * captain_avatar : 队长头像
         */

        private String group_id;
        private String left_member;
        private String captain_avatar;

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getLeft_member() {
            return left_member;
        }

        public void setLeft_member(String left_member) {
            this.left_member = left_member;
        }

        public String getCaptain_avatar() {
            return captain_avatar;
        }

        public void setCaptain_avatar(String captain_avatar) {
            this.captain_avatar = captain_avatar;
        }
    }
}
