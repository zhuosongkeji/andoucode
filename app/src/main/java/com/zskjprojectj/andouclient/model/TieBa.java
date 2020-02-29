package com.zskjprojectj.andouclient.model;


import java.util.List;

public class TieBa {


    /**
     * id : 2
     * user_id : 46
     * title : 测试发帖标题
     * vote : 1
     * share : 0
     * content : 发帖内容
     * name : 啊啊    就
     * avator : http://andou.zhuosongkj.com/uploads/20200221/H6AkcKpbB88LR2z5AUzz0HJrn9AqtuCTPcd57bfc.jpeg
     * comment_count : 13
     * images : ["http://andou.zhuosongkj.com/post/202002/2020022815064323095.jpg","http://andou.zhuosongkj.com/post/202002/2020022815064322377.gif"]
     * comments : [{"id":13,"name":"啊啊    就","content":"新增评论 10"},{"id":12,"name":"啊啊    就","content":"新增评论 9","to":"A_超"},{"id":11,"name":"啊啊    就","content":"新增评论 8"}]
     */

    private int id;
    private int user_id;
    private String title;
    private String vote;
    private String share;
    private String content;
    private String name;
    private String avator;
    private String comment_count;
    private List<String> images;
    private List<CommentsBean> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * id : 13
         * name : 啊啊    就
         * content : 新增评论 10
         * to : A_超
         */

        private int id;
        private String name;
        private String content;
        private String to;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }
}
