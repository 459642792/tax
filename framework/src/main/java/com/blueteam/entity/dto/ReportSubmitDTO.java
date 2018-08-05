package com.blueteam.entity.dto;

/**
 * 举报提交信息DTO
 * Created by  NastyNas on 2018/1/15.
 */
public class ReportSubmitDTO {
    //举报id
    String reportId;
    //回复
    String reply;
    //结果
    String result;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
