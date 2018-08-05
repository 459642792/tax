package com.blueteam.entity.vo.message;

import com.blueteam.base.lang.RStr;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaojiang
 * @create 2018-01-29  10:43
 */
public class MessageVo {
    /** 主题标题 */
    private String subjectTitle;
    /** 主题内容 */
    private String subjectContent;
    /** 主题状态 0 未读 1已读 */
    private Integer subjectState;
    /** 主题分类标识 */
    private String subjectGenre;
    /** 主题时间 */
    private Date createTime;
    /** 详细内容id 根据需求关联表 */
    private String detailedId;

    public MessageVo() {
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getSubjectContent() {
        return subjectContent;
    }

    public void setSubjectContent(String subjectContent) {
        this.subjectContent = subjectContent;
    }

    public Integer getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(Integer subjectState) {
        this.subjectState = subjectState;
    }

    public String getSubjectGenre() {
        return subjectGenre;
    }

    public void setSubjectGenre(String subjectGenre) {
        this.subjectGenre = subjectGenre;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDetailedId() {
        return detailedId;
    }

    public void setDetailedId(String detailedId) {
        this.detailedId = detailedId;
    }

    public MessageVo(String subjectTitle, String subjectContent, Integer subjectState, String subjectGenre, Date createTime, String detailedId) {
        this.subjectTitle = subjectTitle;
        this.subjectContent = subjectContent;
        this.subjectState = subjectState;
        this.subjectGenre = subjectGenre;
        this.createTime = createTime;
        this.detailedId = detailedId;
    }
    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (RStr.isNotEmpty(this.getCreateTime())){
            return sdf.format(this.getCreateTime());
        }
        return "";
    }
}
