package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.ReportListSearchDTO;
import com.blueteam.entity.dto.ReportSubmitDTO;
import com.blueteam.entity.vo.AdminReportDetailVO;
import com.blueteam.entity.vo.AdminReportListVO;

import java.util.List;

/**
 * 举报后台管理
 * Created by  NastyNas on 2018/1/11.
 */
public interface ReportAdminService {
    //分页查询举报列表
    PageResult<List<AdminReportListVO>> listReportInfo(ReportListSearchDTO reportListSearchDTO);

    //根据举报id查询举报信息详情
    AdminReportDetailVO getReportDetailInfo(String reportId);

    //根据举报提交信息，更新举报信息
    void updateReportInfo(ReportSubmitDTO reportSubmitDTO);
}
