package com.zskjprojectj.andouclient.entity.hotel;

import java.util.ArrayList;
import java.util.List;

public class CategoryBean {

    private String content;
    public final List<CategoryBean> categories = new ArrayList<>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
