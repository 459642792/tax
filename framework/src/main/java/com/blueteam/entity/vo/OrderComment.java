package com.blueteam.entity.vo;

/**
 * 评论
 *
 * @author xiaojiang
 * @create 2018-01-15  10:28
 */
public class OrderComment
{
    /** 订单评价 评分*/
    private Integer score;
    /** 订单内容*/
    private String commentContent;
    /** 订单图片*/
    private String commentPicture;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentPicture() {
        return commentPicture;
    }

    public void setCommentPicture(String commentPicture) {
        this.commentPicture = commentPicture;
    }

    public OrderComment() {
    }

    public OrderComment(Integer score, String commentContent, String commentPicture) {
        this.score = score;
        this.commentContent = commentContent;
        this.commentPicture = commentPicture;
    }

    @Override
    public String toString() {
        return "OrderComment{" +
                "score=" + score +
                ", commentContent='" + commentContent + '\'' +
                ", commentPicture='" + commentPicture + '\'' +
                '}';
    }
}
