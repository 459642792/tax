package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.ImageInfo;
import com.blueteam.entity.po.ScoreInfo;
import com.blueteam.entity.po.UserInfo;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.wineshop.service.*;
import jodd.util.URLDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Marx
 * <p>
 * CommentInfoController.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Controller
@RequestMapping("/comment")
@ApiLogin
public class CommentInfoController extends BaseController {
    @Autowired
    ScoreInfoService scoreInfoService;
    @Autowired
    VendorInfoService vendorInfoService;
    @Autowired
    ImageInfoService imageInfoService;
    @Autowired
    UserService userInfoService;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    ImageInfoService imageService;

    @RequestMapping(value = "/commentDetail", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult cityList(@RequestParam(value = "orderNo") String orderNo, @RequestParam(value = "jifen") int jifen, @RequestParam(value = "Detail", required = false) String Detail, @RequestParam(value = "VendorId") int VendorId,
                               @RequestParam(value = "imageList", required = false) List<String> imageList, HttpServletResponse response) throws Exception {
        VendorInfo objvendorDateilInfo = vendorInfoService.vendorDetail(VendorId);
        if (objvendorDateilInfo == null) {
            return ApiResult.error("该商家信息不存在");
        }
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = dateFormat.format(now);
        ScoreInfo objInfo = new ScoreInfo();
        objInfo.setProductId(0);
        objInfo.setScore(new BigDecimal(jifen));
        objInfo.setDetail(URLDecoder.decode(Detail));
        objInfo.setCreateBy(this.getUserName());
        objInfo.setCreateDate(date);
        objInfo.setUpdateDate(date);
        objInfo.setUpdateBy(this.getUserName());
        objInfo.setType(Constants.COMMENTVENDOR_CODE_VENDOR);
        objInfo.setUserId(this.getCurrentUserID());
        objInfo.setOrderNo(orderNo);
        objInfo.setVendorId(VendorId);
        UserInfo objUser = userInfoService.getCityUserInfo(this.getCurrentUserID());
        objInfo.setUserName(objUser == null ? "" : objUser.getNickname());
        scoreInfoService.insertComment(objInfo);
        for (int j = 0; j < imageList.size(); j++) {
            ImageInfo objImageInfo = new ImageInfo();
            objImageInfo.setImage(imageList.get(j).replace("[", "").replace("\"", "").replace("]", ""));
            objImageInfo.setUrl("");
            objImageInfo.setTitle("");
            objImageInfo.setStatus(1);
            objImageInfo.setDetail("");
            objImageInfo.setForeignKey(String.valueOf(objInfo.getUserId()));
            objImageInfo.setType(Constants.COMMENT_CODE_VENDOR);
            objImageInfo.setSortNumber(0);
            objImageInfo.setCreateBy(objUser.getUsername());
            objImageInfo.setUpdateBy(objUser.getUsername());
            objImageInfo.setCreateDate(date);
            objImageInfo.setUpdateDate(date);
            objImageInfo.setExtendId(objInfo.getId());
            imageInfoService.insertImage(objImageInfo);
        }
        ApiResult objResult = new ApiResult();
        objResult.setReturnId(String.valueOf(objInfo.getId()));
        objResult.setSuccess(true);
        objResult.setMessage("评论成功");
        objResult.setStatus("200");
        return objResult;
    }


    /**
     * 查询订单评论列表
     *
     * @param orderNo
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "cmOrderList", method = RequestMethod.GET)
    @ApiLogin
    public @ResponseBody
    BaseResult commentOrder(@RequestParam(value = "orderNo") String orderNo, HttpServletResponse response) throws Exception {
        if (orderNo.isEmpty()) return ApiResult.error("传入参数不正确");
        List<ScoreInfo> lstScoreInfo = scoreInfoService.CommonOrderList(orderNo);
        int counts = lstScoreInfo.size();
        for (int i = 0; i < (lstScoreInfo.size() >= 3 ? 3 : lstScoreInfo.size()); i++) {
            List<ImageInfo> lstImage = imageService.ImageInfoList(Constants.COMMENT_CODE_VENDOR, lstScoreInfo.get(i).getId());
            if (lstImage.size() > 0) {
                lstScoreInfo.get(i).setLstImage(lstImage);
            }
        }
        return ApiResult.success(lstScoreInfo.subList(0, (lstScoreInfo.size() >= 3 ? 3 : lstScoreInfo.size())), counts);
    }
}
