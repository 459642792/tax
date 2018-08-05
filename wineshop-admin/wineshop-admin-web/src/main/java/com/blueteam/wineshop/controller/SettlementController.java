package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.SettlementQueryDTO;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.SettlementPO;
import com.blueteam.entity.po.VendorBankPO;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.entity.vo.PageUtil;
import com.blueteam.entity.vo.SettlementListSumInfoVO;
import com.blueteam.wineshop.service.SettlementService;
import com.blueteam.wineshop.service.VendorBankService;
import com.blueteam.wineshop.service.VendorInfoService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 结 算
 *
 * @author ycl
 * @date 2018年2月6日
 */

@Controller
@RequestMapping("/settlement")
@AdminApiLogin
public class SettlementController {
	private static Logger log=Logger.getLogger(SettlementController.class);
	@Autowired
	private SettlementService settlementService;
	@Autowired
	private VendorInfoService vendorInfoService;
	@Autowired
	private VendorBankService vendorBankService;

	private static final String PATTERN = "^[1-2]\\d{3}-(0[1-9]|[1-9]|1[0-2])-(0[1-9]|[1-9]|[1-2][0-9]|3[0-1])";
	private static final String dateformat_pattern = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String day_dateformat_pattern = "yyyy-MM-dd";

	/**
	 * 跳转页面
	 *
	 * @author ycl
	 * @date 2018年2月6日
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView listSettlementRecord() throws Exception {
		ModelAndView mav = new ModelAndView("settlement/settlement");
		return mav;
	}

	/**
	 * 结算主页高级查询
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageQuerySettlementSum", method = RequestMethod.GET)
	public BaseResult pageQuerySettlementSum(SettlementQueryDTO settlementQueryDTO) {
		checksettlementQueryDTO(settlementQueryDTO);
		PageUtil data = settlementService.pageQuerySettlementSum(settlementQueryDTO);
		return ApiResult.success(data.getArr(),data.getCount());
	}
	private void checksettlementQueryDTO(SettlementQueryDTO settlementQueryDTO) {
	}

	/**
	 * 单个商家结算信息统详情
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSettlementSumInfo", method = RequestMethod.GET)
	public BaseResult getSettlementSumInfo(@RequestParam(value = "vendorId", required = false) Integer vendorId) {
		if (vendorId == null) {
			log.error("用户请求单个商家结算信息统详情 vendorId为空");
			return ApiResult.error("vendorId不能为空!");
		}
		
		SettlementListSumInfoVO data = settlementService.getSettlementSumInfo(vendorId);
		return ApiResult.success(data);
	}

	/**
	 * 单个商家结算历史信息列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageVendorSettlementHistory", method = RequestMethod.GET)
	public BaseResult pageVendorSettlementHistory(SettlementQueryDTO settlementQueryDTO) {
		checksettlementQueryDTO(settlementQueryDTO);
		PageUtil data = settlementService.pageVendorSettlementHistory(settlementQueryDTO);
		return ApiResult.success(data.getArr(),data.getCount());
	}

	/**
	 * 单个商家未结算订单列表
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/pageNotSettlementOrder", method = RequestMethod.GET)
	public BaseResult pageNotSettlementOrder(	
			@RequestParam(value = "pageIndex", required = true,defaultValue="1") Integer pageIndex,
			@RequestParam(value = "pageSize", required = true,defaultValue="10") Integer pageSize,
			@RequestParam(value = "startTime", required = true) String startTime,
			@RequestParam(value = "endTime", required = true) String endTime,
			@RequestParam(value = "vendorId", required = false) Integer vendorId) throws ParseException {
		//参数校验
		Pattern r = Pattern.compile(PATTERN);
		Matcher m = r.matcher(startTime);
		if (!m.matches()) {
			log.error("用户请求单个商家未结算订单列表,结算开始时间错误startTime="+startTime);
			return ApiResult.error("结算开始时间错误!");
		}
		startTime+=" 00:00:00.000";
		m = r.matcher(endTime);
		if (!m.matches()) {
			log.error("用户请求单个商家未结算订单列表,结算截止时间错误endTime="+endTime);
			return ApiResult.error("结算截止时间错误!");
		}
		endTime+=" 23:59:59.999";
		SimpleDateFormat format=new SimpleDateFormat(dateformat_pattern);
		Date startTimeDate = format.parse(startTime);
		Date endTimeDate = format.parse(endTime);
		//请求参数封装
		SettlementQueryDTO settlementQueryDTO=new SettlementQueryDTO();
		settlementQueryDTO.setPageIndex(pageIndex);
		settlementQueryDTO.setPageSize(pageSize);
		settlementQueryDTO.setVendorId(vendorId);
		settlementQueryDTO.setStartTime(startTimeDate);
		settlementQueryDTO.setEndTime(endTimeDate);
		
		PageUtil data = settlementService.pageNotSettlementOrder(settlementQueryDTO);
		Map<String, BigDecimal> orderAmounts = settlementService.getOrderAmounts(settlementQueryDTO);
		BigDecimal bigDecimal = orderAmounts.get("willBalenceAmounts");
		return ApiResult.success(data.getArr(),data.getCount(),bigDecimal+"");
	}

	/**
	 * 单个商家已经结算订单列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageSettlementedOrder", method = RequestMethod.GET)
	public BaseResult pageSettlementedOrder(SettlementQueryDTO settlementQueryDTO) {
		checksettlementQueryDTO(settlementQueryDTO);
		PageUtil data = settlementService.pageSettlementedOrder(settlementQueryDTO);
		return ApiResult.success(data.getArr(),data.getCount());
	}

	/**
	 * 单个商家结算订单
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/settleOrder", method = RequestMethod.GET)
	public BaseResult settleOrder(@RequestParam(value = "vendorId", required = false) Integer vendorId,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "payType", required = false, defaultValue="2") Integer payType,
			@RequestParam(value = "balenceAcounts", required = false) BigDecimal balenceAcounts
			, HttpServletRequest request) throws ParseException {
		//敏感操作 ,参数验证
		if (startTime == null) {
			log.error("用户请求单个商家结算订单,结算开始时间错误startTime="+startTime);
			return ApiResult.error("请设置结算开始时间!");
		}
		Pattern r = Pattern.compile(PATTERN);
		Matcher m = r.matcher(startTime);
		if (!m.matches()) {
			log.error("用户请求单个商家结算订单,结算开始时间错误startTime="+startTime);
			return ApiResult.error("结算开始时间错误!");
		}
		startTime+=" 00:00:00.000";
		if (endTime == null) {
			log.error("用户请求单个商家结算订单,结算截止时间错误endTime="+endTime);
			return ApiResult.error("请设置结算截止时间!");
		}
		m = r.matcher(endTime);
		if (!m.matches()) {
			log.error("用户请求单个商家结算订单,结算截止时间错误endTime="+endTime);
			return ApiResult.error("结算截止时间错误!");
		}
//		String endTimeOld=endTime;
		endTime+=" 23:59:59.999";
		SimpleDateFormat format=new SimpleDateFormat(dateformat_pattern);
		Date startTimeDate = format.parse(startTime);
		Date endTimeDate = format.parse(endTime);
		if(startTimeDate.after(endTimeDate)) {
			log.error("用户请求单个商家结算订单,结算时间范围选择错误 startTimeDate="+startTimeDate+"endTimeDate="+endTimeDate);
			return ApiResult.error("结算时间范围选择错误!");
		}
//		//只能结算本周以前的订单
//		Calendar date = Calendar.getInstance(Locale.CHINA);
//		date.setFirstDayOfWeek(Calendar.MONDAY);// 将每周第一天设为星期一，默认是星期天
//		date.add(Calendar.WEEK_OF_MONTH, -1);// 周数减一，即上周
//		date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 日子设为星期天
//		Date lastSunday = date.getTime();
//		SimpleDateFormat dayFormat = new SimpleDateFormat(day_dateformat_pattern);
//		String lastSundayStr = dayFormat.format(lastSunday);
//		lastSundayStr+=" 23:59:59.999";
//		lastSunday = format.parse(lastSundayStr);
//		if(endTimeDate.after(lastSunday)) {
//			return ApiResult.error("只能结算本周以前的订单!");
//		}
		//判断今天是否周一
		//是周一就不能结算上周日的订单
//		date.setTime(new Date()); 
//		int week=date.get(Calendar.DAY_OF_WEEK)-1;
//		if(week==1&&lastSundayStr.equals(endTimeOld)) {
//			return ApiResult.error("只能结算到上周周日以前的订单!");
//		}
		
		Calendar date = Calendar.getInstance(Locale.CHINA);
		date.setFirstDayOfWeek(Calendar.MONDAY);// 将每周第一天设为星期一，默认是星期天
		date.add(Calendar.DAY_OF_MONTH, -2);// 时间少2天
		Date lastday = date.getTime();
		SimpleDateFormat dayFormat = new SimpleDateFormat(day_dateformat_pattern);
		String lastdayStr = dayFormat.format(lastday);
		lastdayStr+=" 23:59:59.999";
		lastday = format.parse(lastdayStr);
		if(endTimeDate.after(lastday)) {
			log.error("用户请求单个商家结算订单,结算截止时间错误endTime="+endTime+"应需结算最到时间为:"+lastday);
			return ApiResult.error("只能结算一天前的订单!");
		}
		if (vendorId == null) {
			log.error("用户请求单个商家结算订单,vendorId为空!");
			return ApiResult.error("vendorId 不能为空!");
		}
		VendorInfo vendorInfo = vendorInfoService.getNewVendorById(vendorId);
		if(vendorInfo==null) {
			log.error("用户请求单个商家结算订单,获取商家信息失,vendorId="+vendorId);
			return ApiResult.error("获取商家信息失败!");
		}
		//验证未结算的balenceAcounts 是否和时间段中的数据一直
		SettlementQueryDTO settlementQueryDTO=new SettlementQueryDTO();
		settlementQueryDTO.setVendorId(vendorId);
		settlementQueryDTO.setStartTime(startTimeDate);
		settlementQueryDTO.setEndTime(endTimeDate);
		Map<String, BigDecimal>  orderAmounts = settlementService.getOrderAmounts(settlementQueryDTO);
		BigDecimal willBalenceOrderAmounts = orderAmounts.get("willBalenceAmounts");
		
		if(!balenceAcounts.equals(willBalenceOrderAmounts)) {
			log.error("用户请求单个商家结算订单,请求结算和实际能结算的额度不一致,vendorId="+vendorId
					+"请求结算开始时间:"+endTimeDate+"结束时间:"+endTimeDate
					+"balenceAcounts="+balenceAcounts+"实际能结算额度="+willBalenceOrderAmounts);
			return ApiResult.error("请求结算和实际能结算的额度不一致!");
		}
		if(payType!=1&&payType!=2&&payType!=3) {
			log.error("用户请求单个商家结算订单,支付方式错误!");
			return ApiResult.error("支付方式错误!");
		}
		//封装settlement
		SettlementPO settlement=new SettlementPO();
		settlement.setVendorId(vendorId);
		settlement.setVendorName(vendorInfo.getName());
		settlement.setRegion(vendorInfo.getTradingArea());
		settlement.setEndTime(endTimeDate);
		settlement.setStartTime(startTimeDate);
		settlement.setBalancedAmounts(willBalenceOrderAmounts);
		BigDecimal allBalenceAmounts = orderAmounts.get("allBalencedAmounts");
		allBalenceAmounts=allBalenceAmounts.add(willBalenceOrderAmounts);
		settlement.setAllBalancedAmounts(allBalenceAmounts);
		//测试, 实际要获得当前用户信息 ,requst 中获得
		UserIdentify identify = (UserIdentify) request.getAttribute(Constants.LOGINIDENTIFY_KEY);
		settlement.setCreateUserId(identify.getUserId());
		settlement.setCreateUsername(identify.getUserName());
		settlement.setCreateTime(new Date());
		if(payType==2) {
			VendorBankPO infoByVendorId = vendorBankService.getInfoByVendorId(vendorId);
			if(infoByVendorId!=null) {
				settlement.setCountNo(infoByVendorId.getAccount());
				settlement.setCountType(2);
			}
			//userinfo 中的 vendorWxOpenId 和 WxOpenId都为空?
		}else if(payType==1) {
			settlement.setCountNo("weixin");
			settlement.setCountType(1);
		}
	
		String errorMsg=settlementService.settleOrder(settlement);
		if(errorMsg==null) {
			return ApiResult.success("添加结算记录成功!");
		}
		return ApiResult.error(errorMsg);
	}
}
