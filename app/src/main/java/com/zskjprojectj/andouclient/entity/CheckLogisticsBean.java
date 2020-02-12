package com.zskjprojectj.andouclient.entity;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2020/1/2 14:58
 * des: 查看物流
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CheckLogisticsBean {


    /**
     * wuliu_msg : {"message":"ok","nu":"3101959187888","ischeck":"1","condition":"D01","com":"yunda","status":"200","state":"3","data":[{"startTime":"2018-09-16 19:17:37","ftime":"2018-09-16 19:17:37","context":"【泸州市】快件已被 代签收。如有问题请电联业务员：刘会【14780417298】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】"},{"startTime":"2018-09-16 15:08:09","ftime":"2018-09-16 15:08:09","context":"【泸州市】四川叙永县公司 派件员 刘会 14780417298 正在为您派件"},{"startTime":"2018-09-15 08:23:20","ftime":"2018-09-15 08:23:20","context":"【成都市】已离开 四川成都分拨中心；发往 四川叙永县公司"},{"startTime":"2018-09-14 21:01:24","ftime":"2018-09-14 21:01:24","context":"【重庆市】已离开 重庆分拨中心；发往 四川成都分拨中心"},{"startTime":"2018-09-14 20:46:26","ftime":"2018-09-14 20:46:26","context":"【重庆市】已到达 重庆分拨中心"},{"startTime":"2018-09-14 16:50:51","ftime":"2018-09-14 16:50:51","context":"【重庆市】已离开 重庆北碚区公司；发往 重庆分拨中心"},{"startTime":"2018-09-14 14:40:33","ftime":"2018-09-14 14:40:33","context":"【重庆市】重庆北碚区公司 已揽收"}]}
     * name : 韵达快递
     * courier_num : 3101959187888
     */

    private WuliuMsgBean wuliu_msg;
    private String name;
    private String courier_num;

    public WuliuMsgBean getWuliu_msg() {
        return wuliu_msg;
    }

    public void setWuliu_msg(WuliuMsgBean wuliu_msg) {
        this.wuliu_msg = wuliu_msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourier_num() {
        return courier_num;
    }

    public void setCourier_num(String courier_num) {
        this.courier_num = courier_num;
    }

    public static class WuliuMsgBean {
        /**
         * message : ok
         * nu : 3101959187888
         * ischeck : 1
         * condition : D01
         * com : yunda
         * status : 200
         * state : 3
         * data : [{"startTime":"2018-09-16 19:17:37","ftime":"2018-09-16 19:17:37","context":"【泸州市】快件已被 代签收。如有问题请电联业务员：刘会【14780417298】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】"},{"startTime":"2018-09-16 15:08:09","ftime":"2018-09-16 15:08:09","context":"【泸州市】四川叙永县公司 派件员 刘会 14780417298 正在为您派件"},{"startTime":"2018-09-15 08:23:20","ftime":"2018-09-15 08:23:20","context":"【成都市】已离开 四川成都分拨中心；发往 四川叙永县公司"},{"startTime":"2018-09-14 21:01:24","ftime":"2018-09-14 21:01:24","context":"【重庆市】已离开 重庆分拨中心；发往 四川成都分拨中心"},{"startTime":"2018-09-14 20:46:26","ftime":"2018-09-14 20:46:26","context":"【重庆市】已到达 重庆分拨中心"},{"startTime":"2018-09-14 16:50:51","ftime":"2018-09-14 16:50:51","context":"【重庆市】已离开 重庆北碚区公司；发往 重庆分拨中心"},{"startTime":"2018-09-14 14:40:33","ftime":"2018-09-14 14:40:33","context":"【重庆市】重庆北碚区公司 已揽收"}]
         */

        private String message;
        private String nu;
        private String ischeck;
        private String condition;
        private String com;
        private String status;
        private String state;
        private List<DataBean> data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getNu() {
            return nu;
        }

        public void setNu(String nu) {
            this.nu = nu;
        }

        public String getIscheck() {
            return ischeck;
        }

        public void setIscheck(String ischeck) {
            this.ischeck = ischeck;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * startTime : 2018-09-16 19:17:37
             * ftime : 2018-09-16 19:17:37
             * context : 【泸州市】快件已被 代签收。如有问题请电联业务员：刘会【14780417298】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】
             */

            private String time;
            private String ftime;
            private String context;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getFtime() {
                return ftime;
            }

            public void setFtime(String ftime) {
                this.ftime = ftime;
            }

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }
        }
    }
}
