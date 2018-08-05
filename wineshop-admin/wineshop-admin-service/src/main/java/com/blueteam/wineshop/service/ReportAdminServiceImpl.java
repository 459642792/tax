package com.blueteam.wineshop.service;

import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.RDbTrans;
import com.blueteam.base.lang.RList;
import com.blueteam.base.lang.RStr;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.ReportListSearchDTO;
import com.blueteam.entity.dto.ReportSubmitDTO;
import com.blueteam.entity.vo.AdminReportDetailVO;
import com.blueteam.entity.vo.AdminReportListVO;
import com.blueteam.wineshop.mapper.ReportAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by  NastyNas on 2018/1/11.
 */
@Service
public class ReportAdminServiceImpl implements ReportAdminService {


    @Autowired
    ReportAdminMapper reportAdminMapper;

    @Override
    public PageResult<List<AdminReportListVO>> listReportInfo(ReportListSearchDTO reportListSearchDTO) {
        //举报总数
        Integer count = reportAdminMapper.countReportList(reportListSearchDTO);
        //分页举报列表
        List<AdminReportListVO> voList = reportAdminMapper.listReportInfo(reportListSearchDTO);
        //封装VO
        wrapVOList(voList);
        PageResult pageResult = new PageResult();
        pageResult.setCount(count);
        pageResult.setList(voList);
        return pageResult;
    }

    @Override
    public AdminReportDetailVO getReportDetailInfo(String reportId) {
        AdminReportDetailVO reportDetailVO = reportAdminMapper.getReportDetailInfo(reportId);
        return wrapDetailVO(reportDetailVO);
    }

    @Override
    public void updateReportInfo(ReportSubmitDTO reportSubmitDTO) {
        Integer updateResult = reportAdminMapper.updateReportInfo(reportSubmitDTO);
        if (updateResult == null || updateResult > 1) {
            throw new BusinessException("举报信息更新错误");
        }
    }

    private AdminReportDetailVO wrapDetailVO(AdminReportDetailVO reportDetailVO) {
        String photoString = reportDetailVO.getReportPhotoString();
        if (RStr.isNotEmpty(photoString) && photoString.split("\\^").length > 0) {
            reportDetailVO.setReportPhotoList(RList.asList(photoString.split("\\^")));
        }
        reportDetailVO.setPayPrice(RDbTrans.asShowPrice(reportDetailVO.getPayPrice()));
        return reportDetailVO;
    }

    private void wrapVOList(List<AdminReportListVO> voList) {
        for (AdminReportListVO vo : voList) {
            vo.setReportTime(RDbTrans.asShowDate(vo.getReportTime()));
        }
    }

}
