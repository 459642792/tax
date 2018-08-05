package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.CityInfo;
import com.blueteam.entity.po.CouponInfo;
import com.blueteam.entity.po.UserInfo;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.wineshop.service.CityInfoService;
import com.blueteam.wineshop.service.CouponInfoService;
import com.blueteam.wineshop.service.UserService;
import com.blueteam.wineshop.service.VendorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AdminApiLogin
@Controller
@RequestMapping("/couponMain")
public class CouponMainController extends BaseController {
    @Autowired
    CouponInfoService couponInfoService;
    @Autowired
    UserService userService;
    @Autowired
    CityInfoService cityInfoService;
    @Autowired
    VendorInfoService vendorInfoService;

    //优惠券初始化页面
    @RequestMapping(value = "/Main", method = RequestMethod.GET)
    public ModelAndView Index() throws Exception {
        ModelAndView modelAndView = new ModelAndView("couponManage/couponList");
        return modelAndView;
    }

    //优惠券初始化页面
    @RequestMapping(value = "/pageMain", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult Index(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                            @RequestParam(value = "BeginTime", required = false) String BeginTime,
                            @RequestParam(value = "EndTime", required = false) String EndTime,
                            @RequestParam(value = "Name", required = false) String Name, @RequestParam(value = "Addr", required = false) String Addr, HttpServletResponse response) throws Exception {
        List<CouponInfo> couponList = couponInfoService.admincouponList(pageSize, pageIndex, Name, Addr, BeginTime, EndTime);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date date = calendar.getTime();
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        calendar2.add(calendar2.DAY_OF_YEAR, -1);
        Date date2 = calendar2.getTime();
        for (int i = 0; i < couponList.size(); i++) {
            List<CouponInfo> lstCoupons = couponInfoService.couponxiaofeiCount(couponList.get(i).getId(), Constants.DISCOUNT_COUPON_CODE);
            couponList.get(i).setUsedCont(lstCoupons.size());
            if (couponList.get(i).getType().equals(Constants.CREATE_COUPON_CODE_VENDOR_ADMIN.toString())) {
                UserInfo user = userService.selectByPrimaryKey(couponList.get(i).getForeignKey());
                couponList.get(i).setUpdateBy(null == user ? "-" : "管理员:" + user.getNickname());
            } else {
                VendorInfo objInfos = vendorInfoService.vendorDetail(couponList.get(i).getForeignKey());
                couponList.get(i).setUpdateBy(null == objInfos ? "-" : "商家:" + objInfos.getName());
            }

            if (couponList.get(i).getStatus() == CouponInfo.STATUS_STOP) {
                couponList.get(i).setStatusStr("已停止");
            } else {
                if (date.compareTo(sdf.parse(couponList.get(i).getBeginTime())) > 0 && date2.compareTo(sdf.parse(couponList.get(i).getEndTime())) < 0) {
                    couponList.get(i).setStatusStr("进行中");
                } else if (date.compareTo(sdf.parse(couponList.get(i).getBeginTime())) <= 0) {
                    couponList.get(i).setStatusStr("未开始");
                } else {
                    couponList.get(i).setStatusStr("已过期");
                }
            }
        }
        List<CouponInfo> ListCount = couponInfoService.admincouponCount(Name, Addr, BeginTime, EndTime);
        return ApiResult.success(couponList, ListCount.size());
    }

    //停止优惠券发放
    @RequestMapping(value = "endCoupon", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult endCoupon(Integer couponId) {
        CouponInfo model = couponInfoService.couponDetail(couponId);
        if (model == null) {
            return ApiResult.error("不存在该优惠券信息");
        }
        model.setCreateBy(getUserName());
        model.setCreateDate(getCurrentDateString());
        model.setStatus(CouponInfo.STATUS_STOP);
        couponInfoService.update(model);
        return BaseResult.success();
    }

    //新增或者修改界面跳转
    @RequestMapping(value = "/AddOrEdit", method = RequestMethod.GET)
    public ModelAndView AddOrEdit(@RequestParam(value = "Id") Integer Id, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView;
        if (Id != null && Id > 0) {
            modelAndView = new ModelAndView("couponManage/couponEdit");
            modelAndView.addObject("Id", Id);

        } else {
            modelAndView = new ModelAndView("couponManage/couponAdd");
        }
        return modelAndView;
    }

    //查询省级别的数据集合
    @RequestMapping(value = "/ProvinceList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult ProvinceList(HttpServletResponse response) throws Exception {
        CityInfo info = new CityInfo();
        info.setParentCode(Constants.CITY_ROOT_CODE);
        List<CityInfo> citys = cityInfoService.selectList(info);
        CityInfo objInfo = new CityInfo();
        objInfo.setName("全部");
        objInfo.setPinYin("quanbu");
        objInfo.setCode("0");
        objInfo.setParentCode("0");
        citys.add(0, objInfo);
        return ApiResult.success(citys);
    }

    //查询市级别的数据集合
    @RequestMapping(value = "/cityList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult cityList(@RequestParam(value = "parent") String parent, HttpServletResponse response) throws Exception {
        CityInfo info = new CityInfo();
        info.setParentCode(parent);
        List<CityInfo> citys = cityInfoService.selectList(info);
        CityInfo objInfo = new CityInfo();
        objInfo.setName("全部");
        objInfo.setPinYin("quanbu");
        objInfo.setCode("0");
        objInfo.setParentCode("0");
        citys.add(0, objInfo);
        return ApiResult.success(citys);
    }

    //查询区域级别的数据集合
    @RequestMapping(value = "/countyList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult countyList(@RequestParam(value = "parent") String parent, HttpServletResponse response) throws Exception {
        if (parent == null || parent.trim().isEmpty()) {
            return ApiResult.error(null, "参数错误");
        }
        List<CityInfo> citys = cityInfoService.queryCityList(parent);
        CityInfo objInfo = new CityInfo();
        objInfo.setName("全部");
        objInfo.setPinYin("quanbu");
        objInfo.setCode("0");
        objInfo.setParentCode("0");
        citys.add(0, objInfo);
        return ApiResult.success(citys);
    }

    //新增优惠券
    @RequestMapping(value = "couponAdd", method = RequestMethod.POST)
    @ResponseBody
    @AdminApiLogin
    public BaseResult couponAdd(CouponInfo model) throws Exception {
        model.setForeignKey(getCurrentUserID());
        model.setUserId(getCurrentUserID());
        model.setType(Constants.CREATE_COUPON_CODE_VENDOR_ADMIN);
        model.setCreateBy(getUserName());
        model.setCreateDate(getCurrentDateString());
        model.setUpdateBy(getUserName());
        model.setUpdateDate(getCurrentDateString());
        model.setCostStatus("N");
        couponInfoService.insertCoupon(model);
        return ApiResult.success();
    }

    //修改优惠券
    @RequestMapping(value = "couponUpdate", method = RequestMethod.POST)
    @ResponseBody
    @AdminApiLogin
    public BaseResult couponUpdate(CouponInfo model) throws Exception {
        CouponInfo objInfo = couponInfoService.couponDetail(model.getId());
        model.setUserId(getCurrentUserID());
        //model.setType(Constants.CREATE_COUPON_CODE_VENDOR_ADMIN);
        model.setCreateBy(getUserName());
        model.setCreateDate(getCurrentDateString());
        model.setUpdateBy(getUserName());
        model.setUpdateDate(getCurrentDateString());
        model.setCostStatus("N");
        couponInfoService.update(model);
        return ApiResult.success();
    }

    //查询优惠券详情信息
    @RequestMapping(value = "couponDetail", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult couponDetail(@RequestParam("couponId") int couponId, HttpServletResponse response) throws ParseException {
        if (couponId <= 0) {
            return ApiResult.error("传入的参数信息错误");
        }
        CouponInfo objInfo = couponInfoService.couponDetail(couponId);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, +0);
        Date date = calendar.getTime();
        if (null == objInfo) {
            return ApiResult.error("没有该优惠券信息");
        }
        if (objInfo.getStatus() == CouponInfo.STATUS_STOP) {
            objInfo.setStatusStr("已停止");
        } else {
            if (date.compareTo(sdf.parse(objInfo.getBeginTime())) > 0 && date.compareTo(sdf.parse(objInfo.getEndTime())) < 0) {
                objInfo.setStatusStr("进行中");
            } else if (date.compareTo(sdf.parse(objInfo.getBeginTime())) <= 0) {
                objInfo.setStatusStr("未开始");
            } else {
                objInfo.setStatusStr("已过期");
            }
        }
        return ApiResult.success(objInfo);
    }

    /**
     * 比较两个字符串时间的大小
     *
     * @param endTime
     * @param endTime2
     * @return endTime - endTime2
     * @throws Exception
     */
    private long cmpDate(String endTime, String endTime2) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(endTime).getTime() - sdf.parse(endTime2).getTime();
    }
}
