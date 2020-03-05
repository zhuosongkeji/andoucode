package com.zskjprojectj.andouclient.model;


import com.zhuosongkj.android.library.model.IListData;

import java.io.Serializable;
import java.util.ArrayList;

public class TieBa implements Serializable, IListData<TieBa.CommentsBean> {

    private String id;
    private int user_id;
    private String title;
    private String vote;
    private String share;
    private String content;
    public String created_at;
    public String is_vote;
    private String name;
    private String avator;
    public int top_post;
    private String comment_count;
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<CommentsBean> comments = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentsBean> comments) {
        this.comments = comments;
    }

    @Override
    public ArrayList<CommentsBean> getDataList() {
        return comments;
    }

    public static class CommentsBean implements Serializable {
        private String id;
        private String name;
        private String content;
        private String to;
        public String avator;

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
