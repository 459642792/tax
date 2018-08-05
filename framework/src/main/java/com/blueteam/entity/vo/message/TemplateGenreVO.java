package com.blueteam.entity.vo.message;

import java.util.List;

/**
 * 消息种类
 *
 * @author xiaojiang
 * @create 2018-01-26  17:49
 */
public class TemplateGenreVO {
    /** 种类名称 */
    private String genreName;
    /**  种类值 */
    private int genreValue;
    /**  种类未读的数量*/
    private int unreadCount;
    /**  类型列表*/
   private List<SubjectGenreVO> subjectGenre;

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getGenreValue() {
        return genreValue;
    }

    public void setGenreValue(int genreValue) {
        this.genreValue = genreValue;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public List<SubjectGenreVO> getSubjectGenre() {
        return subjectGenre;
    }

    public void setSubjectGenre(List<SubjectGenreVO> subjectGenre) {
        this.subjectGenre = subjectGenre;
    }

    public TemplateGenreVO() {
    }

    public TemplateGenreVO(String genreName, int genreValue, int unreadCount, List<SubjectGenreVO> subjectGenre) {
        this.genreName = genreName;
        this.genreValue = genreValue;
        this.unreadCount = unreadCount;
        this.subjectGenre = subjectGenre;
    }
}
