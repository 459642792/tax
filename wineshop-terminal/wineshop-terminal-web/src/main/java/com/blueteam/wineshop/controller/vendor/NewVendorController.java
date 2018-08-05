package com.blueteam.wineshop.controller.vendor;


import com.blueteam.base.cache.redis.Redis;
import com.blueteam.base.constant.*;
import com.blueteam.base.util.JsonUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.VendorImageDTO;
import com.blueteam.entity.po.AdvertiseInfo;
import com.blueteam.entity.po.Log;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.wineshop.controller.BaseController;
import com.blueteam.wineshop.service.AdvertiseInfoService;
import com.blueteam.wineshop.service.VendorInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/newVendor")
public class NewVendorController extends BaseController{
    private static final Logger logger= LoggerFactory.getLogger(NewVendorController.class);
    @Resource
    private VendorInfoService vendorInfoService;
    @Resource
    private AdvertiseInfoService advertiseInfoService;


    /**
     * 商家资质认证
     * @return
     *
     */
    @ApiLogin
    @RequestMapping(value = "/authentication",method = RequestMethod.POST)
    public BaseResult authentication(@RequestBody VendorInfo vendorInfo) {
        String key = "code-authentication-" + this.getIdentify().getTelephone();
        if (!vendorInfo.getCode().equals(Redis.getJedis().get(key))) {
            return BaseResult.error("验证码错误");
        }
        vendorInfo.setUserId(this.getIdentify().getUserId());//商家关联的用户id
        vendorInfo.setAuditStatus(EnabledOrDisabled.DISABLED);
        vendorInfo.setStatus(Enums.VendorStatus.CheckAccess.getValue());
        vendorInfo.setTelephone(this.getIdentify().getTelephone());
        logger.info("params of /newVendor/authentication:{}", JsonUtil.serialize(vendorInfo));
        int res = vendorInfoService.saveAuthentication(vendorInfo);
        if (res > 0){
            vendorInfoService.bindPayInfo(vendorInfoService.getNewVendorByUserId(getIdentify().getUserId()).getId());
            return BaseResult.success();
        }
        return BaseResult.error("保存失败");
    }


    /**
     * 商家基础信息
     * @return
     */
    @VendorApiLogin
    @RequestMapping(value = "/updateVendor",method = RequestMethod.POST)
    public BaseResult updateVendor(@RequestBody VendorInfo vendorInfo){
        vendorInfo.setId(this.getIdentify().getExtendId());
        //vendorInfo.setId(1);
        logger.info("params of /newVendor/updateVendor:{}", JsonUtil.serialize(vendorInfo));
        int res=vendorInfoService.updateVendorInfo(vendorInfo);
        if (res>0)
            return BaseResult.success();
        return BaseResult.error("操作失败");
    }


    /**
     * 获取商家基础信息
     * @return
     */
    @VendorApiLogin
    @RequestMapping(value = "/getVendor")
    public BaseResult getVendor(){
        VendorInfo info=vendorInfoService.getNewVendorById(this.getIdentify().getExtendId());
        return ApiResult.success(info);
    }

    /**
     * 上传图片（门脸图和环境图）
     * @return
     */
    @VendorApiLogin
    @RequestMapping(value = "/uploadVendorImage",method = RequestMethod.POST)
    public BaseResult uploadVendorImage(@RequestBody VendorImageDTO dto){
        logger.info("params of /newVendor/uploadVendorImage:{}",JsonUtil.serialize(dto));
        Integer vendorId = getIdentify().getExtendId();
        String type;
        if (dto.getImageType()==1){
            type= Constants.CREATE_VENDOR_DETAIL_FACADE;
        }else if (dto.getImageType()==2){
            type= Constants.CREATE_VENDOR_DETAIL_AMBIENT;
        }else if (dto.getImageType()==3){
            type=Constants.CREATE_VENDOR_GENERALVIEW;
        }else {
            return BaseResult.error("图片类型错误！");
        }
        int newSortNumber = advertiseInfoService.findMaxSortNumberOfHeadImg(vendorId) + 1;
        AdvertiseInfo model =null;
        for (String image:dto.getImages()) {
             model=new AdvertiseInfo();
            model.setImg(image);
            model.setTypeCode(type);
            model.setForeignKey(vendorId + "");
            model.setEnableFlag(EnabledOrDisabled.ENABLED);
            model.setSortNumber(newSortNumber);
            model.setCreateBy(getUserName());
            model.setCreateDate(getCurrentDateString());
            advertiseInfoService.save(model);
            newSortNumber++;
        }
        return ApiResult.success();
    }

    @VendorApiLogin
    @RequestMapping(value = "/uploadSingleImage",method = RequestMethod.POST)
    public BaseResult uploadSingleImage(@RequestBody VendorImageDTO dto){
        logger.info("params of /newVendor/uploadSingleImage:{}",JsonUtil.serialize(dto));
        Integer vendorId = getIdentify().getExtendId();
        String type;
        if (dto.getImageType()==1){
            type= Constants.CREATE_VENDOR_DETAIL_FACADE;
        }else if (dto.getImageType()==2){
            type= Constants.CREATE_VENDOR_DETAIL_AMBIENT;
        }else if (dto.getImageType()==3){
            type=Constants.CREATE_VENDOR_GENERALVIEW;
        }else {
            return BaseResult.error("图片类型错误！");
        }
        int newSortNumber = advertiseInfoService.findMaxSortNumberOfHeadImg(vendorId) + 1;
        AdvertiseInfo model =new AdvertiseInfo();
            model.setImg(dto.getImage());
            model.setTypeCode(type);
            model.setForeignKey(vendorId + "");
            model.setEnableFlag(EnabledOrDisabled.ENABLED);
            model.setSortNumber(newSortNumber);
            model.setCreateBy(getUserName());
            model.setCreateDate(getCurrentDateString());
            advertiseInfoService.save(model);
        return ApiResult.success(model.getId());
    }


    /**
     * 删除图片
     * @param dto
     * @return
     */
    @VendorApiLogin
    @RequestMapping(value = "/delVendorImage",method = RequestMethod.POST)
    public BaseResult delVendorImage(@RequestBody VendorImageDTO dto){
        if (advertiseInfoService.removeHeadImage(dto.getId())>0){
          return BaseResult.success();
        }else {
            return BaseResult.error("删除失败");
        }
    }


    /**
     * 获取图片列表
     * @return
     */
    @VendorApiLogin
    @RequestMapping(value = "/getVendorImage")
    public BaseResult getVendorImage(){

        return advertiseInfoService.getVendorImagesByForeignKey(getIdentify().getExtendId()+"");
    }


    /**
     * 申请寄送二维码
     * /newVendor/sendVendorCode
     */
    @VendorApiLogin
    @RequestMapping("/sendVendorCode")
    public BaseResult sendVendorCode(){
        int res=vendorInfoService.sendQRCode(getIdentify().getExtendId());
        if (res>0)
        return BaseResult.success();
        return BaseResult.error("申请失败");
    }

}
