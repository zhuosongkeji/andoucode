package com.zskjprojectj.andouclient.entity.hotel;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.entity.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 11:07
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailCommentBean {

    private int headPic;
    private String name;
    private String comment;
    private int commentImage;

    public int getHeadPic() {
        return headPic;
    }

    public void setHeadPic(int headPic) {
        this.headPic = headPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCommentImage() {
        return commentImage;
    }

    public void setCommentImage(int commentImage) {
        this.commentImage = commentImage;
    }
}
