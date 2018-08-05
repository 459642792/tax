package com.blueteam.wineshop.controller;

import com.blueteam.base.conf.WxMpConfig;
import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.AES;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.mapper.ThirdPartyUserInfoMapper;
import com.blueteam.wineshop.service.*;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import jodd.util.URLDecoder;
import org.apache.commons.collections.functors.FalsePredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Marx
 * <p>
 * VendorInfoController.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Controller
@RequestMapping("/vendor")
public class VendorInfoController extends BaseController {
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxMpConfig wxApiConfig;
    @Autowired
    VendorInfoService vendorInfoService;
    @Autowired
    ScoreInfoService scoreInfoService;
    @Autowired
    AdvertiseInfoService advertiseInfoService;
    @Autowired
    CouponInfoService couponInfoService;
    @Autowired
    VisitTrackInfoService visitService;
    @Autowired
    ImageInfoService imageService;
    @Autowired
    PinPaiInfoService PinPaiInfoService;
    @Autowired
    CityInfoService cityInfoService;
    @Autowired
    UserService userInfoService;
    @Autowired
    BrandInfoService brandInfoService;

    @Autowired
    private ThirdPartyUserInfoMapper thirdPartyUserInfoMapper;

    /**
     * @param CityCode
     * @param longitude
     * @param latitude
     * @param Reorder   1代表智能排序  2 代表认真优先  3代表距离最近   4代表 综合评分
     * @param pageSize
     * @param pageIndex
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/vendorList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult vendorList(@RequestParam("CityCode") String CityCode, @RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude, @RequestParam("Reorder") int Reorder,
                                 @RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex, HttpServletResponse response) throws Exception {
        List<VendorInfo> objvendorInfo = vendorInfoService.VendorInfoLists(CityCode);
        for (int i = 0; i < objvendorInfo.size(); i++) {
            double distance = GetDistance(Double.valueOf(longitude), Double.valueOf(latitude), Double.valueOf(objvendorInfo.get(i).getLongitude()), Double.valueOf(objvendorInfo.get(i).getLatitude()));
            objvendorInfo.get(i).setLujin(Double.toString(distance));
            int Sum = 0;
            if ((int) distance <= 100) {
                Sum = Sum + 6;
            } else if ((int) distance > 100 && (int) distance <= 500) {
                Sum = Sum + 5;
            } else if ((int) distance > 500 && (int) distance <= 1000) {
                Sum = Sum + 4;
            } else if ((int) distance > 1000 && (int) distance <= 2000) {
                Sum = Sum + 3;
            } else if ((int) distance > 2000 && (int) distance <= 5000) {
                Sum = Sum + 2;
            } else {
                Sum = Sum + 1;
            }
//            if (objvendorInfo.get(i).getAuditStatus().equals("Y")) {
//                Sum = Sum + 6;
//            }
//			double newScore=0;
//			double zonghefen=0;
//			List<ScoreInfo> objjifenInfo = scoreInfoService.ScoreInfoList(objvendorInfo.get(i).getId());
//			for(int k=0;k<objjifenInfo.size();k++)
//			{
//				newScore=add(Sum,objjifenInfo.get(k).getScore());
//				zonghefen=add(0,objjifenInfo.get(k).getScore());
//			}
//			if(objjifenInfo.size()==0) newScore=add(Sum,0);
//			objvendorInfo.get(i).setZonghe((int)zonghefen);
//			objvendorInfo.get(i).setScoreCount((int)newScore);

            objvendorInfo.get(i).setZonghe(objvendorInfo.get(i).getZonghe() == null ? 0 : objvendorInfo.get(i).getZonghe());
            objvendorInfo.get(i).setScoreCount((int) add(Sum, objvendorInfo.get(i).getZonghe() == null ? 0 : objvendorInfo.get(i).getZonghe()));
            List<CouponInfo> objCouponInfo = couponInfoService.CouponInfoListDetail(objvendorInfo.get(i).getId(),objvendorInfo.get(i).getCityCode(), Constants.CREATE_COUPON_CODE_VENDOR);
            objvendorInfo.get(i).setCouponList(objCouponInfo);
        }
        /*新加代码*/
        List<VendorInfo> objRevendorInfo = vendorInfoService.VendorRecommendList(CityCode);
        for (int p = 0; p < objRevendorInfo.size(); p++) {
            double distance = GetDistance(Double.valueOf(longitude), Double.valueOf(latitude), Double.valueOf(objRevendorInfo.get(p).getLongitude()), Double.valueOf(objRevendorInfo.get(p).getLatitude()));
            objRevendorInfo.get(p).setLujin(Double.toString(distance));
            objRevendorInfo.get(p).setRecommendStatus("Y");
            List<CouponInfo> objCouponInfo = couponInfoService.CouponInfoListDetail(objRevendorInfo.get(p).getId(),objRevendorInfo.get(p).getCityCode(), Constants.CREATE_COUPON_CODE_VENDOR);
            objRevendorInfo.get(p).setCouponList(objCouponInfo);
        }
        /*新加代码*/
        if (Reorder == 1) {
            Comparator<VendorInfo> cmp = new Comparator<VendorInfo>() {
                @Override
                public int compare(VendorInfo o1, VendorInfo o2) {
                    return o2.getScoreCount() - o1.getScoreCount();
                }
            };
            Collections.sort(objvendorInfo, cmp);
        } //else if (Reorder == 2) {
//            //表示认证咯的数据
//            List<VendorInfo> lstVendor = new ArrayList<VendorInfo>();
//            //表示没有认证的数据
//            List<VendorInfo> lstVendor2 = new ArrayList<VendorInfo>();
//            //获取认证咯的数据
//            for (int w = 0; w < objvendorInfo.size(); w++) {
//                if (objvendorInfo.get(w).getAuditStatus().equalsIgnoreCase("Y")) {
//                    lstVendor.add(objvendorInfo.get(w));
//                } else
//
//                {
//                    lstVendor2.add(objvendorInfo.get(w));
//                }
//            }
//
//            //对认证后的数据进行排序
//            Comparator<VendorInfo> cmp = new Comparator<VendorInfo>() {
//                @Override
//                public int compare(VendorInfo o1, VendorInfo o2) {
//                    return new BigDecimal(o1.getLujin()).compareTo(new BigDecimal(o2.getLujin()));
//                }
//            };
//            Collections.sort(lstVendor, cmp);
//
//            //对没有认证的数据进行排序
//            Comparator<VendorInfo> cmp2 = new Comparator<VendorInfo>() {
//                @Override
//                public int compare(VendorInfo o1, VendorInfo o2) {
//                    return new BigDecimal(o1.getLujin()).compareTo(new BigDecimal(o2.getLujin()));
//                }
//            };
//            Collections.sort(lstVendor2, cmp2);
//            for (int r = 0; r < lstVendor2.size(); r++) {
//                lstVendor.add(lstVendor2.get(r));
//            }
//            objvendorInfo = lstVendor;
//        }
        else if (Reorder == 3) {
            //距离最近排序
            Comparator<VendorInfo> cmp = new Comparator<VendorInfo>() {
                @Override
                public int compare(VendorInfo o1, VendorInfo o2) {
                    return new BigDecimal(o1.getLujin()).compareTo(new BigDecimal(o2.getLujin()));
                }
            };
            Collections.sort(objvendorInfo, cmp);
        } else {
            //代表综合排序
            Comparator<VendorInfo> cmp = new Comparator<VendorInfo>() {
                @Override
                public int compare(VendorInfo o1, VendorInfo o2) {
                    return o2.getZonghe() - o1.getZonghe();
                }
            };
            Collections.sort(objvendorInfo, cmp);
        }
        //分页数据处理
        //最后的返回数据集合
        /*新加代码 然后将之前(下面)的objvendorInfo  修改为objRevendorInfo*/
        objRevendorInfo.addAll(objvendorInfo);
        /*新加代码*/
        List<VendorInfo> lstRetrunVendor = new ArrayList<VendorInfo>();
        int count = (pageIndex - 1) * pageSize;
        int count2 = pageIndex * pageSize;
        if ((int) (objRevendorInfo.size() - (pageIndex - 1) * pageSize) < (int) pageSize) {
            count2 = objRevendorInfo.size();
        }
        for (int u = count; u < count2; u++) {
            lstRetrunVendor.add(objRevendorInfo.get(u));
        }
        for (int i = 0; i < lstRetrunVendor.size(); i++) {
            if (lstRetrunVendor.get(i).getAdvantage() != null && !lstRetrunVendor.get(i).getAdvantage().trim().isEmpty()) {
                VendorInfo v = lstRetrunVendor.get(i);
                v.setLstAdvantage(v.getAdvantage().split(","));
                lstRetrunVendor.set(i,v);
            }
        }
        return ApiResult.success(lstRetrunVendor, objRevendorInfo.size());
    }

    /**
     * @param ForeignKey
     * @param TypeCode
     * @return
     */
    @RequestMapping(value = "/ImageList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult vendorDetailTop(@RequestParam("ForeignKey") int ForeignKey, @RequestParam("TypeCode") String TypeCode) {
        //获取商家店铺图片
        List<AdvertiseInfo> objadvertiseInfo = advertiseInfoService.VendorTopList(ForeignKey, TypeCode);
        return ApiResult.success(objadvertiseInfo);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/SdppList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult SdppList(@RequestParam("ForeignKey") int ForeignKey) {
        //获取商家店铺图片
        List<AdvertiseInfo> objadvertiseInfo = advertiseInfoService.SdppList(ForeignKey, Constants.CREATE_Sdpp_IMAGELIST);
        return ApiResult.success(objadvertiseInfo);
    }

    /**
     * @param ForeignKey
     * @param TypeCode
     * @return
     */
    @RequestMapping(value = "/vendorPhotoList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult vendorDetailPhoto(@RequestParam("ForeignKey") int ForeignKey, @RequestParam("TypeCode") String TypeCode, @RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex) {
        //获取商家店铺图片
        List<AdvertiseInfo> objadvertiseInfo = advertiseInfoService.VendorPhotoList(ForeignKey, TypeCode);
        int count = (pageIndex - 1) * pageSize;

        //TODO:这段代码似乎是无必要的哦
        int count2 = pageIndex * pageSize;
        if (objadvertiseInfo.size() - pageIndex * pageSize < pageSize) {
            count2 = objadvertiseInfo.size();
        }

        int skipEnd = pageIndex * pageSize;

        List<AdvertiseInfo> lstRetrunVendor = new ArrayList<AdvertiseInfo>();
        for (int i = count; i < skipEnd && i < objadvertiseInfo.size(); i++) {
            lstRetrunVendor.add(objadvertiseInfo.get(i));
        }
        return ApiResult.success(lstRetrunVendor, objadvertiseInfo.size());
    }


    /**
     * @param Id
     * @return
     */
    @RequestMapping(value = "/vendorDetail", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult vendorDetail(@RequestParam("Id") int Id) {
        //获取商家店铺详情信息
        VendorInfo objvendorDateilInfo = vendorInfoService.vendorDetail(Id);
        if (objvendorDateilInfo == null) {
            return ApiResult.error("该商家信息不存在");
        }
//        List<CouponInfo> objCouponInfo = couponInfoService.CouponInfoListDetail(objvendorDateilInfo.getId(), Constants.CREATE_COUPON_CODE_VENDOR);
//        objvendorDateilInfo.setCouponList(objCouponInfo);
        if (null != objvendorDateilInfo.getAdvantage() && !objvendorDateilInfo.getAdvantage().trim().isEmpty()) {
            objvendorDateilInfo.setLstAdvantage(objvendorDateilInfo.getAdvantage().split(","));
        }
        double newScore = 0;
        //评论记录
        List<ScoreInfo> objInfo = scoreInfoService.CommonInfoList(objvendorDateilInfo.getId(), Constants.COMMENTVENDOR_CODE_VENDOR);
        for (int i = 0; i < objInfo.size(); i++) {
            newScore = add(newScore, objInfo.get(i).getScore());
        }
        if (objInfo.size() > 0) {
            objvendorDateilInfo.setOutScore(BigDecimal.valueOf(newScore / objInfo.size()));
        } else {
            objvendorDateilInfo.setOutScore(BigDecimal.valueOf(0));
        }
        return ApiResult.success(objvendorDateilInfo);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/vendorcommentList", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin
    public BaseResult vendorcommentList(@RequestParam("vendorId") int vendorId) {
        if (vendorId < 0) {
            return ApiResult.error("传入参数不正确");
        }
        VendorInfo objvendorDateilInfo = vendorInfoService.vendorDetail(vendorId);
//        //新增浏览记录
//        Date now = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        String date = dateFormat.format(now);
//        visitService.insertVisitTrackInfo(objvendorDateilInfo.getId(), 10, date, "", "");
        Integer count = visitService.insertVisitTrackInfo(vendorId, this.getCurrentUserID());
        if (count == 1)
            vendorInfoService.updateVisit(objvendorDateilInfo.getId(), (objvendorDateilInfo.getVisitCount() + 1));
        double newScore = 0;
        List<ScoreInfo> objInfo = scoreInfoService.CommonInfoList(vendorId, Constants.COMMENTVENDOR_CODE_VENDOR);
        //int Count=scoreInfoService.GetScoreCount(vendorId,Constants.COMMENTVENDOR_CODE_VENDOR);
        int counts = objInfo.size();
        for (int i = 0; i < (objInfo.size() >= 6 ? 6 : objInfo.size()); i++) {
            newScore = add(newScore, objInfo.get(i).getScore());
            List<ImageInfo> lstImage = imageService.ImageInfoList(Constants.COMMENT_CODE_VENDOR, objInfo.get(i).getId());
            if (lstImage.size() > 0) {
                objInfo.get(i).setLstImage(lstImage);
            }
            UserInfo objuserInfo = userInfoService.selectByPrimaryKey(objInfo.get(i).getUserId());
//            if (objuserInfo != null) {
//                objInfo.get(i).setHeadImageUrl(objuserInfo.getHeadimage());
//                objInfo.get(i).setUserName(objuserInfo.getNickname());
//            }
            List<ScoreInfo> lstScore = scoreInfoService.VendorBackList(objInfo.get(i).getOrderNo(), Constants.COMMENTADMIN_CODE_VENDOR);
            if (lstScore.size() > 0) objInfo.get(i).setVendorBack(lstScore);
        }
        return ApiResult.success(objInfo.subList(0, (counts >= 6 ? 6 : counts)), objInfo.size());
    }

    /**
     * @param Id
     * @return
     */
    @RequestMapping(value = "/vendorDetail2", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult vendorDetail2(@RequestParam("Id") int Id) {
        //获取商家店铺详情信息
        VendorInfo objvendorDateilInfo = vendorInfoService.vendorDetail(Id);
        if (objvendorDateilInfo == null) {
            return ApiResult.error("该商家信息不存在");
        }
        List<CouponInfo> objCouponInfo = couponInfoService.CouponInfoListDetail(objvendorDateilInfo.getId(), objvendorDateilInfo.getCityCode(),Constants.CREATE_COUPON_CODE_VENDOR);
        UserIdentify userIdentify = VerificationUtil.getUserIdentify(this.getToken());
        for (int i = 0; i < objCouponInfo.size(); i++) {
            List<CouponInfo> lstCoupons = couponInfoService.couponxiaofeiCount(objCouponInfo.get(i).getId(), Constants.DISCOUNT_COUPON_CODE);
            if (lstCoupons.size() >= objCouponInfo.get(i).getCount()) {
                objCouponInfo.get(i).setEndState("Y");
            } else {
                objCouponInfo.get(i).setEndState("N");
            }

            List<CouponInfo> objCouponInfos = couponInfoService.checkCoupon(userIdentify == null ? 0 : userIdentify.getUserId(), Constants.DISCOUNT_COUPON_CODE, objCouponInfo.get(i).getId());
            if (objCouponInfos.size() > 0) {
                //表示领取
                objCouponInfo.get(i).setState("Y");
            } else {
                //表示没有领取
                objCouponInfo.get(i).setState("N");
            }
        }
        objvendorDateilInfo.setCouponList(objCouponInfo);

        List<String> lstStr = new ArrayList<String>();
        if (objvendorDateilInfo.getPinpaiIds() != null && !objvendorDateilInfo.getPinpaiIds().trim().isEmpty()) {
            String Ids = objvendorDateilInfo.getPinpaiIds().replace(",,", ",");
            int Count = objvendorDateilInfo.getPinpaiIds().replace(",,", ",").length();
            for (int i = 0; i < Ids.substring(1, Count - 1).split(",").length; i++) {
                int idss = Integer.parseInt(Ids.substring(1, Count - 1).split(",")[i]);
                PinPaiInfo objPinpai = PinPaiInfoService.PinPaiName(idss);
                if (null != objPinpai) {
                    lstStr.add(objPinpai.getName());
                }
            }
        }
        if (objvendorDateilInfo.getAdvantage() != null && !objvendorDateilInfo.getAdvantage().trim().isEmpty()) {
            objvendorDateilInfo.setLstAdvantage(objvendorDateilInfo.getAdvantage().split(","));
        }
        objvendorDateilInfo.setPpList(lstStr);

        double newScore = 0;
        //评论记录
        List<ScoreInfo> objInfo = scoreInfoService.CommonInfoList(objvendorDateilInfo.getId(), Constants.COMMENTVENDOR_CODE_VENDOR);
        for (int i = 0; i < objInfo.size(); i++) {
            newScore = add(newScore, objInfo.get(i).getScore());
        }
        if (objInfo.size() > 0) {
            objvendorDateilInfo.setOutScore(BigDecimal.valueOf(newScore / objInfo.size()));
        } else {
            objvendorDateilInfo.setOutScore(BigDecimal.valueOf(0));
        }
        int abc = objInfo.size();
        objvendorDateilInfo.setScoreList(objInfo.subList(0, (objInfo.size() >= 3 ? 3 : objInfo.size())));
        return ApiResult.success(objvendorDateilInfo, abc);
    }


    /**
     * 评论列表页面
     */
    @RequestMapping(value = "/commentList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult commentList(@RequestParam("Id") int Id, @RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex) {
        List<ScoreInfo> lstScore = new ArrayList<ScoreInfo>();
        if (Id < 0) {
            return ApiResult.error("参数错误");
        } else {
            List<ScoreInfo> objInfo = scoreInfoService.CommonInfoList(Id, Constants.COMMENTVENDOR_CODE_VENDOR);
            //int Count=scoreInfoService.GetScoreCount(Id,Constants.COMMENTVENDOR_CODE_VENDOR);
            for (int i = 0; i < objInfo.size(); i++) {
                List<ImageInfo> lstImage = imageService.ImageInfoList(Constants.COMMENT_CODE_VENDOR, objInfo.get(i).getId());
                if (lstImage.size() > 0) {
                    objInfo.get(i).setLstImage(lstImage);
                }
                UserInfo objuserInfo = userInfoService.selectByPrimaryKey(objInfo.get(i).getUserId());
//				if(objuserInfo!=null)
//				{
//					objInfo.get(i).setHeadImageUrl(objuserInfo.getHeadimage());
//					objInfo.get(i).setUserName(objuserInfo.getNickname());
//				}
                List<ThirdPartyUserInfo> lists = thirdPartyUserInfoMapper.listThirdPartyUserInfo(objInfo.get(i).getUserId(), Enums.UserType.Every, Enums.ThirdPartyUserInfo.WEI_XIN, ThirdPartyUserInfo.THIRD_PARTY_STATUS_BIND);
                if (null != lists && lists.size() != 0) {
                    objInfo.get(i).setUserName(lists.get(0).getThirdPartyNickName() != null && !lists.get(0).getThirdPartyNickName().equals("") ? lists.get(0).getThirdPartyNickName() : objuserInfo.getTelephone());
                    objInfo.get(i).setHeadImageUrl(lists.get(0).getThirdPartyHeadImage());
                    objInfo.get(i).setOpenId(AES.decrypt(wxApiConfig.getApiaeskey(), lists.get(0).getThirdPartyId()));
                } else {
                    objInfo.get(i).setUserName(null != objuserInfo ? objuserInfo.getTelephone() : "");
                    objInfo.get(i).setHeadImageUrl("");
                    objInfo.get(i).setOpenId("");
                }
                List<ScoreInfo> lstScores = scoreInfoService.VendorBackList(objInfo.get(i).getOrderNo(), Constants.COMMENTADMIN_CODE_VENDOR);
                if (lstScores.size() > 0) objInfo.get(i).setVendorBack(lstScores);
            }
            int count = (pageIndex - 1) * pageSize;
            int count2 = pageIndex * pageSize <= objInfo.size() ? pageIndex * pageSize : objInfo.size();
//			if(objInfo.size()-pageIndex*pageSize<pageSize)
//			{
//				count2=objInfo.size();
//			}
            for (int u = count; u < count2; u++) {
                lstScore.add(objInfo.get(u));
            }
            return ApiResult.success(lstScore, objInfo.size());
        }
    }

    /**
     * 商家获取评分列表
     *
     * @return
     * @author Eric Lee, 2017-03-03
     */
    @ApiLogin
    @ResponseBody
    @RequestMapping(value = "vendorCommentList", method = RequestMethod.GET)
    public BaseResult vendorCommentList(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex
    ) {
        ApiResult<Map<String, Object>> result = ApiResult.success(null);
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            String type = Constants.COMMENTVENDOR_CODE_VENDOR;
            Map<String, Object> resultMap = scoreInfoService
                    .findAllVendorScore(getIdentify().getExtendId(), type, pageIndex, pageSize, Constants.COMMENTADMIN_CODE_VENDOR);
            result.setData(resultMap);
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }

        return result;
    }


    @RequestMapping(value = "/addMerchantsCommentsUsers", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult addMerchantsCommentsUsers(@RequestParam(value = "orderNo") String orderNo, @RequestParam(value = "detail") String detail, HttpServletResponse response) throws Exception {
        VendorInfo objvendorDateilInfo = vendorInfoService.vendorDetail(getIdentify().getExtendId());
//		VendorInfo objvendorDateilInfo = vendorInfoService.vendorDetail(1640);
        if (objvendorDateilInfo == null) {
            return ApiResult.error("该商家信息不存在");
        }
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = dateFormat.format(now);
        ScoreInfo objInfo = new ScoreInfo();
        objInfo.setProductId(0);
        objInfo.setScore(new BigDecimal(0));
        objInfo.setDetail(URLDecoder.decode(detail));
        objInfo.setCreateBy(this.getUserName());
        objInfo.setCreateDate(date);
        objInfo.setUpdateDate(date);
        objInfo.setUpdateBy(this.getUserName());
        objInfo.setType(Constants.COMMENTADMIN_CODE_VENDOR);
        objInfo.setUserId(this.getCurrentUserID());
        objInfo.setOrderNo(orderNo);
        objInfo.setVendorId(getIdentify().getExtendId());
        UserInfo objUser = userInfoService.getCityUserInfo(this.getCurrentUserID());
        objInfo.setUserName(objUser == null ? "" : objUser.getNickname());
        objInfo.setCreateBy(objvendorDateilInfo.getName());
        objInfo.setUpdateBy(objvendorDateilInfo.getName());
        scoreInfoService.insertComment(objInfo);
        BaseResult apiResult = new BaseResult();
        apiResult.setReturnId(String.valueOf(objInfo.getId()));
        apiResult.setSuccess(true);
        apiResult.setMessage("成功");
        apiResult.setStatus("200");
        return apiResult;
    }


    /**
     * 商家获取评分列表
     *
     * @return
     * @author Eric Lee, 2017-03-03
     */
    @ApiLogin
    @ResponseBody
    @RequestMapping(value = "vendorCommentList2", method = RequestMethod.GET)
    public BaseResult vendorCommentList2(
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex
    ) {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            String type = Constants.COMMENTVENDOR_CODE_VENDOR;
            return scoreInfoService.findNewAllVendorScore(getIdentify().getExtendId(), type, pageIndex, pageSize, Constants.COMMENTADMIN_CODE_VENDOR);

        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
    }

    /**
     * @param Name
     * @param longitude
     * @param latitude
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "/vendorQuery", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult vendorQuery(@RequestParam("CityCode") String CityCode, @RequestParam(value = "Name", required = false) String Name, @RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude, @RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex) {
        Name = Name == null ? "" : Name;
        List<VendorInfo> lstVendorInfo = vendorInfoService.quyuList(Name, CityCode);
        List<VendorInfo> objvendorInfo = vendorInfoService.vendorInfoQuey(Name);
        List<VendorInfo> lstvend = new ArrayList<VendorInfo>();
        String str = "";
        if (lstVendorInfo.size() > 0) {
            lstvend = lstVendorInfo;
        } else {
            lstvend = objvendorInfo;
            str = "该区域没有对应的商家信息";
        }
        for (int i = 0; i < objvendorInfo.size(); i++) {
            if (null != objvendorInfo.get(i).getLongitude() && !objvendorInfo.get(i).getLongitude().isEmpty() && null != objvendorInfo.get(i).getLatitude() && !objvendorInfo.get(i).getLatitude().isEmpty()) {
                double distance = GetDistance(Double.valueOf(longitude), Double.valueOf(latitude), Double.valueOf(objvendorInfo.get(i).getLongitude()), Double.valueOf(objvendorInfo.get(i).getLatitude()));
                objvendorInfo.get(i).setLujin(Double.toString(distance));
                CityInfo objCity = cityInfoService.selectCityName(objvendorInfo.get(i).getCityCode());
                if (null != objCity) {
                    objvendorInfo.get(i).setQuyuName(objCity.getName());
                }
            } else {
                objvendorInfo.get(i).setLujin("9999999999999");//因有脏数据 故设置最大距离
            }
        }
        List<VendorInfo> lstRetrunVendor = new ArrayList<VendorInfo>();
        List<VendorInfo> lstRetrunVendors = new ArrayList<VendorInfo>();
        for (int j = 0; j < objvendorInfo.size(); j++) {
            //距离最近排序
            Comparator<VendorInfo> cmp = new Comparator<VendorInfo>() {
                @Override
                public int compare(VendorInfo o1, VendorInfo o2) {
                    return new BigDecimal(o1.getLujin()).compareTo(new BigDecimal(o2.getLujin()));
                }
            };
            Collections.sort(objvendorInfo, cmp);

            if (objvendorInfo.get(j).getAdvantage() != null && !objvendorInfo.get(j).getAdvantage().trim().isEmpty()) {
                objvendorInfo.get(j).setLstAdvantage(objvendorInfo.get(j).getAdvantage().split(","));
            }
            if (objvendorInfo.get(j).getPinpaiIds() != null && !objvendorInfo.get(j).getPinpaiIds().trim().isEmpty()) {
                String Ids = objvendorInfo.get(j).getPinpaiIds().replace(",,", ",");
                int Count = objvendorInfo.get(j).getPinpaiIds().replace(",,", ",").length();
                List<String> lstStr = new ArrayList<String>();
                System.out.println(Ids + "=============" + Count);
                if (!Ids.isEmpty() && Count > 1) {
                    for (int i = 0; i < Ids.substring(1, Count - 1).split(",").length; i++) {
                        int idss = Integer.parseInt((Ids.substring(1, Count - 1).split(",")[i]));
                        PinPaiInfo objPinpai = PinPaiInfoService.PinPaiName(idss);
                        if (null != objPinpai) {
                            lstStr.add(objPinpai.getName());
                        }
                    }
                }

                objvendorInfo.get(j).setPpList(lstStr);
            }
            List<CouponInfo> objCouponInfo = couponInfoService.CouponInfoListDetail(objvendorInfo.get(j).getId(),objvendorInfo.get(j).getCityCode(),Constants.CREATE_COUPON_CODE_VENDOR);
            objvendorInfo.get(j).setCouponList(objCouponInfo);
            lstRetrunVendor.add(objvendorInfo.get(j));

            if (objvendorInfo.get(j).getCityCode().endsWith(CityCode)) {
                lstRetrunVendors.add(objvendorInfo.get(j));
            }
        }
        int count = (pageIndex - 1) * pageSize;
        int count2 = pageIndex * pageSize;
        String Strs = "N";
        if (lstRetrunVendors.size() > 0) {
            Strs = "Y";
            if (lstRetrunVendors.size() < count2) {
                count2 = lstRetrunVendors.size();
            }
        } else {
            Strs = "N";
            if (lstRetrunVendor.size() < count2) {
                count2 = lstRetrunVendor.size();
            }
        }
        return ApiResult.success(Strs.equals("Y") ? lstRetrunVendors.subList(count, count2) : lstRetrunVendor.subList(count, count2), (Strs.equals("Y") ? lstRetrunVendors.size() : lstRetrunVendor.size()), str, Strs);
    }

    /*
    *20大品牌页面,根据主品牌搜索店铺
    * @param mainBrand 主品牌名称
    * @param longitude 经度
    * @param latitude 纬度
    * @param pageIndex 页面序号
    * @param pageSize 页面展示个数
    * */
    @RequestMapping(value = "/vendorQueryByBrand", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult vendorQueryByBrand(String mainBrand, Double longitude, Double latitude,Integer pageIndex,Integer pageSize) throws Exception {
        return brandInfoService.listVendorsByMainBrand(mainBrand,longitude,latitude,pageIndex,pageSize);
    }

    /**
     * 计算两个差值
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 两个Double记性计算
     */
    public double add(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.add(b2).doubleValue();
    }

    public static double div(double d1, double d2, int len) {// 进行除法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public Double div(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    private static double EARTH_RADIUS = 6371.393;

    /**
     * 计算两个经纬度之间的距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;

    }

    /**
     * 计算连个经纬度之间的距离
     *
     * @param d
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * 方法的功能描述:TODO 根据商家id 获取商家详情，小程序端
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName getVendorByDetails
     * @param: Id
     * @author xiaojiang 2017/10/19 11:29
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/getVendorByDetails", method = RequestMethod.GET)
    @ApiLogin
    public @ResponseBody
    BaseResult getVendorByDetails(@RequestParam("vendorId") Integer vendorId) {
        if (vendorId < 0) {
            return ApiResult.error("传入参数不正确");
        }
        Integer count = visitService.insertVisitTrackInfo(vendorId, this.getCurrentUserID());
//        Integer count = visitService.insertVisitTrackInfo(vendorId, 7);
        //获取商家店铺详情信息
        Map<String, Object> map = vendorInfoService.getVendorByDetails(vendorId);
        if (map.isEmpty()) {
            return ApiResult.error("该商家信息不存在");
        }
        if (RStr.isEmpty(map.get("CityCode"))) {
            return ApiResult.error("该商家cityCode不存在");
        }
        String cityCode =(String)map.get("CityCode");
        if (count == 1) {
            map.put("visitCount", map.get("visitCount") != null ? (Integer) map.get("visitCount") + 1 : 1);
            vendorInfoService.updateVisit(vendorId, (Integer) map.get("visitCount"));
        }
        List<CouponInfo> objCouponInfo = couponInfoService.CouponInfoListDetail(vendorId,cityCode, Constants.CREATE_COUPON_CODE_VENDOR);
        //复制之前已经由其他同事做好的代码，可以优化 从sql出发
        for (int i = 0; i < objCouponInfo.size(); i++) {
            List<CouponInfo> lstCoupons = couponInfoService.couponxiaofeiCount(objCouponInfo.get(i).getId(), Constants.DISCOUNT_COUPON_CODE);
            if (lstCoupons.size() >= objCouponInfo.get(i).getCount()) {
                objCouponInfo.get(i).setEndState("Y");
            } else {
                objCouponInfo.get(i).setEndState("N");
            }

            List<CouponInfo> objCouponInfos = couponInfoService.checkCoupon(this.getCurrentUserID(), Constants.DISCOUNT_COUPON_CODE, objCouponInfo.get(i).getId());
            if (objCouponInfos.size() > 0) {
                //表示领取
                objCouponInfo.get(i).setState("Y");
            } else {
                //表示没有领取
                objCouponInfo.get(i).setState("N");
            }
        }
        map.put("counponList", objCouponInfo);
        return ApiResult.success(map);
    }

    /**
     * 方法的功能描述:TODO 根据商家id 获取商家详情，小程序端
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName getVendorByDetails
     * @param: Id
     * @author xiaojiang 2017/10/19 11:29
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/getShopsByDetails", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getShopsByDetails(@RequestParam("vendorId") Integer vendorId) {
        if (vendorId < 0) {
            return ApiResult.error("传入参数不正确");
        }
        Map<String, Object> map = vendorInfoService.getVendorByDetails(vendorId);
        if (map.isEmpty()) {
            return ApiResult.error("该商家信息不存在");
        }

        if (RStr.isEmpty(map.get("CityCode"))) {
            return ApiResult.error("该商家cityCode不存在");
        }
        String cityCode =(String)map.get("CityCode");
        List<CouponInfo> objCouponInfo = couponInfoService.CouponInfoListDetail(vendorId, cityCode,Constants.CREATE_COUPON_CODE_VENDOR);
        map.put("counponList", objCouponInfo);
        return ApiResult.success(map);
    }
}
