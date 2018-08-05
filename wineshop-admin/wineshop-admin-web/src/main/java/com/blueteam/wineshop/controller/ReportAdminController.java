package com.blueteam.wineshop.controller;

import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.RStr;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.vo.AdminReportDetailVO;
import com.blueteam.entity.vo.AdminReportListVO;
import com.blueteam.wineshop.service.ReportAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 举报管理
 * Created by  NastyNas on 2018/1/9.
 */
@Controller
@RequestMapping("/reportAdmin")
public class ReportAdminController extends BaseController {

    @Autowired
    ReportAdminService reportAdminService;

    /**
     * 举报列表页分页展示
     *
     * @return
     */
    @RequestMapping(value = "/listReport", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listReport(ReportListSearchDTO reportListSearchDTO) {
        PageResult<List<AdminReportListVO>> reportVOList = reportAdminService.listReportInfo(reportListSearchDTO);
        return ApiResult.success(reportVOList.getList(), reportVOList.getCount());
    }

    /**
     * 获取举报详情
     *
     * @return
     */
    @RequestMapping(value = "/getReportDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getReportDetail(@RequestParam("reportId") String reportId) {
        AdminReportDetailVO reportDetailVO = reportAdminService.getReportDetailInfo(reportId);
        return ApiResult.success(reportDetailVO);
    }

    /**
     * 提交客服回复
     *
     * @return
     */
    @RequestMapping(value = "/submitReply", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult submitReply(ReportSubmitDTO reportSubmitDTO) {
        if (reportSubmitDTO == null || RStr.isEmpty(reportSubmitDTO.getReportId()) || RStr.isEmpty(reportSubmitDTO.getReply())) {
            throw new BusinessException("客服回复提交信息不为空");
        }
        reportAdminService.updateReportInfo(reportSubmitDTO);
        return ApiResult.success();
    }

    /**
     * 提交处理结果
     *
     * @return
     */
    @RequestMapping(value = "/submitResult", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult submitResult(ReportSubmitDTO reportSubmitDTO) {
        if (reportSubmitDTO == null || RStr.isEmpty(reportSubmitDTO.getReportId()) || RStr.isEmpty(reportSubmitDTO.getResult())) {
            throw new BusinessException("处理结果提交信息不为空");
        }
        reportAdminService.updateReportInfo(reportSubmitDTO);
        return ApiResult.success();
    }

    /**
     * 页面跳转
     *
     * @return
     */
    @RequestMapping(value = "/showReportList", method = RequestMethod.GET)
    public ModelAndView showReportList() {
        return new ModelAndView("customerservice/reportadmin/report_admin_list");
    }
}
