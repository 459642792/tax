package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.ReportListSearchDTO;
import com.blueteam.entity.dto.ReportSubmitDTO;
import com.blueteam.entity.vo.AdminReportDetailVO;
import com.blueteam.entity.vo.AdminReportListVO;

import java.util.List;

/**
 * Created by  NastyNas on 2018/1/11.
 */
public interface ReportAdminMapper {
    Integer countReportList(ReportListSearchDTO reportListSearchDTO);

    List<AdminReportListVO> listReportInfo(ReportListSearchDTO reportListSearchDTO);

    AdminReportDetailVO getReportDetailInfo(String reportId);

    Integer updateReportInfo(ReportSubmitDTO reportSubmitDTO);
}
