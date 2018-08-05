package com.blueteam.entity.dto;

/**
 * 举报列表查询dto
 * Created by  NastyNas on 2018/1/15.
 */
public class ReportListSearchDTO extends BasePageSearch {
    //举报时间from
    String reportFrom;
    //举报时间to
    String reportTo;
    //关键字
    String keyword;
    //举报状态 0未处理，1处理中，2已处理
    Integer reportState;
    //举报内容
    String reportContext;

    public String getReportFrom() {
        return reportFrom;
    }

    public void setReportFrom(String reportFrom) {
        this.reportFrom = reportFrom;
    }

    public String getReportTo() {
        return reportTo;
    }

    public void setReportTo(String reportTo) {
        this.reportTo = reportTo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getReportState() {
        return reportState;
    }

    public void setReportState(Integer reportState) {
        this.reportState = reportState;
    }

    public String getReportContext() {
        return reportContext;
    }

    public void setReportContext(String reportContext) {
        this.reportContext = reportContext;
    }
}
