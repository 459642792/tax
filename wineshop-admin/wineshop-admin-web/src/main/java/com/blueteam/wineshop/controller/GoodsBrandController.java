package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.RMap;
import com.blueteam.base.lang.RDbTrans;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.base.util.FieldValidateUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.BrandDTO;
import com.blueteam.entity.dto.BrandSearchDTO;
import com.blueteam.entity.po.BrandMainInfoPO;
import com.blueteam.entity.po.BrandSubInfoPO;
import com.blueteam.entity.vo.AdminBrandMainVO;
import com.blueteam.entity.vo.AdminBrandVO;
import com.blueteam.wineshop.service.GoodsAdminService;
import com.blueteam.wineshop.service.GoodsBrandService;
import com.blueteam.wineshop.service.UserService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static com.blueteam.base.constant.FieldValidateConstants.*;
import static com.blueteam.entity.po.BrandSubInfoPO.STATE_TAG_INVALID;
import static com.blueteam.entity.po.BrandSubInfoPO.STATE_TAG_VALID;

/**
 * 商品品牌管理
 * <p>
 * Created by  NastyNas on 2017/10/19.
 */
@Controller
@RequestMapping("/goodsBrand")
@AdminApiLogin
public class GoodsBrandController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsBrandController.class);
    @Autowired
    GoodsAdminService goodsAdminService;
    @Autowired
    GoodsBrandService goodsBrandService;
    @Autowired
    UserService userService;

    /**
     * 跳转页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listGoodsBrand", method = RequestMethod.GET)
    public ModelAndView listGoodsBrand() throws Exception {
        ModelAndView mav = new ModelAndView("goods/brand/goods_brand_list");
        return mav;
    }

    /**
     * 密码二次验证
     *
     * @param password
     * @return
     */
    @RequestMapping(value = "/passwordConfirm", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult passwordConfirm(@RequestParam String password) {
        BaseResult user = userService.pwdLogin(getUserName(), password);
        if (!user.isSuccess()) {
            return user;
        }
        return BaseResult.success();
    }

    /**
     * 添加主品牌
     *
     * @param brandDTO
     * @return
     */
    @RequestMapping(value = "/addMainBrand", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addMainBrand(BrandDTO brandDTO) {
        //参数完整性校验
        brandDTOValidate(brandDTO, MAIN_BRAND_ADD_DTO);
        //校验品牌名称是否存在
        Integer mainBrandCount = goodsBrandService.countMainBrandByName(brandDTO.getMainBrandName());
        if (mainBrandCount != 0) {
            return BaseResult.error("该主品牌已经存在");
        }
        //主品牌入库
        BrandMainInfoPO brandMainInfoPO = new BrandMainInfoPO();
        brandMainInfoPO.setMainBrandName(brandDTO.getMainBrandName());
        brandMainInfoPO.setMainBrandPhoto(brandDTO.getMainBrandPhoto());
        brandMainInfoPO.setMainBrandDesc(brandDTO.getMainBrandDesc());
        brandMainInfoPO.setCreateStaffId(getCurrentUserID());
        brandMainInfoPO.setUpdateStaffId(getCurrentUserID());
        try {
            Integer saveResult = goodsBrandService.saveMainBrand(brandMainInfoPO);
            if (saveResult != 1) {
                return BaseResult.error("主品牌新增失败");
            }
        } catch (Exception e) {
            logger.error("主品牌添加失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), brandDTO, brandMainInfoPO);
            throw new BusinessException("主品牌添加失败");
        }
        return ApiResult.success();
    }

    /**
     * 编辑主品牌页面展示
     *
     * @param mainBrandId
     * @return
     */
    @RequestMapping(value = "/editMainBrandShow", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult editMainBrandShow(@RequestParam("mainBrandId") Integer mainBrandId) {
        //查询主品牌信息
        Map mainBrandInfo = goodsBrandService.getMainBrandInfo(mainBrandId);
        if (RMap.isEmpty(mainBrandInfo)) {
            return BaseResult.error("主品牌信息错误");
        }
        return ApiResult.success(mainBrandInfo);
    }


    /**
     * 编辑主品牌提交
     *
     * @param brandDTO
     * @return
     */
    @RequestMapping(value = "/updateMainBrand", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateMainBrand(BrandDTO brandDTO) {
        //参数完整性校验
        brandDTOValidate(brandDTO, MAIN_BRAND_EDIT_DTO);
        //查询主品牌信息
        Map mainBrandInfo = goodsBrandService.getMainBrandInfo(brandDTO.getMainBrandId());
        if (RMap.isEmpty(mainBrandInfo)) {
            return BaseResult.error("主品牌信息错误");
        }
        //校验是否和原有信息相同
        if (brandDTO.getMainBrandName().trim().equals(RMap.getStr(mainBrandInfo, "mainBrandName"))
                && brandDTO.getMainBrandPhoto().trim().equals(RMap.getStr(mainBrandInfo, "mainBrandPhoto"))
                && brandDTO.getMainBrandDesc().trim().equals(RMap.getStr(mainBrandInfo, "mainBrandDesc"))) {
            return BaseResult.error("主品牌编辑失败，编辑后主品牌信息与原信息相同");
        }
        //校验主品牌名称是否存在
        Integer mainBrandCount = goodsBrandService.countMainBrandByName(brandDTO.getMainBrandName().trim());
        if (mainBrandCount != 0) {
            return BaseResult.error("编辑失败，该主品牌已经存在");
        }
        //校验主品牌是否有绑定商品
        Integer goodsCount = goodsBrandService.countMainBrandGoods(brandDTO.getMainBrandId());
        if (goodsCount != 0) {
            return BaseResult.error("该主品牌已绑定了商品，不可编辑");
        }
        //更新
        BrandMainInfoPO brandMainInfoPO = new BrandMainInfoPO();
        brandMainInfoPO.setMainBrandId(brandDTO.getMainBrandId());
        brandMainInfoPO.setMainBrandName(brandDTO.getMainBrandName());
        brandMainInfoPO.setMainBrandPhoto(brandDTO.getMainBrandPhoto());
        brandMainInfoPO.setMainBrandDesc(brandDTO.getMainBrandDesc());
        brandMainInfoPO.setUpdateStaffId(getCurrentUserID());
        try {
            Integer updateResult = goodsBrandService.updateMainBrand(brandMainInfoPO);
            if (updateResult != 1) {
                return BaseResult.error("主品牌编辑失败");
            }
        } catch (Exception e) {
            logger.error("主品牌编辑失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), brandDTO, brandMainInfoPO);
            throw new BusinessException("主品牌编辑失败");
        }
        return BaseResult.success();
    }

    /**
     * 删除主品牌
     *
     * @param brandDTO
     * @return
     */
    @RequestMapping(value = "/removeMainBrand", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult removeMainBrand(BrandDTO brandDTO) {
        //参数完整性校验
        brandDTOValidate(brandDTO, MAIN_BRAND_REMOVE_DTO);
        //查询主品牌信息
        Map mainBrandInfo = goodsBrandService.getMainBrandInfo(brandDTO.getMainBrandId());
        if (RMap.isEmpty(mainBrandInfo)) {
            return BaseResult.error("主品牌信息错误");
        }
        //校验主品牌是否有绑定商品
        Integer goodsCount = goodsBrandService.countMainBrandGoods(brandDTO.getMainBrandId());
        if (goodsCount != 0) {
            return BaseResult.error("该主品牌已绑定了商品，不可编辑");
        }
        //校验主品牌下是否存在子品牌
        Integer brandCount = goodsBrandService.countSubBrand(brandDTO.getMainBrandId());
        if (brandCount != 0) {
            return BaseResult.error("该主品牌下存在子品牌，需先删除子品牌");
        }
        try {
            //删除主品牌
            Integer removeResult = goodsBrandService.removeMainBrand(brandDTO.getMainBrandId());
            if (removeResult != 1) {
                return BaseResult.error("主品牌删除失败");
            }
        } catch (Exception e) {
            logger.error("主品牌删除失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), brandDTO, brandDTO.getMainBrandId());
            throw new BusinessException("主品牌删除失败");
        }
        return BaseResult.success();
    }


    /**
     * 添加子品牌
     *
     * @param brandDTO
     * @return
     */
    @RequestMapping(value = "/addSubBrand", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult addSubBrand(BrandDTO brandDTO) {
        //参数完整性校验
        brandDTOValidate(brandDTO, BRAND_ADD_DTO);
        //校验子品牌名称是否存在
        Integer subBrandCount = goodsBrandService.countSubBrandByName(brandDTO.getMainBrandId(), brandDTO.getBrandName());
        if (subBrandCount != 0) {
            return BaseResult.error("该子品牌已经存在");
        }
        //校验商品类型编码是否合法
        Integer countResult = goodsAdminService.countGoodsTypeById(brandDTO.getBrandGoodsType());
        if (countResult == 0) {
            return BaseResult.error("子品牌新增，品类选择信息错误");
        }
        //子品牌入库
        BrandSubInfoPO brandSubInfoPO = new BrandSubInfoPO();
        brandSubInfoPO.setMainBrandId(brandDTO.getMainBrandId());
        brandSubInfoPO.setBrandName(brandDTO.getBrandName());
        brandSubInfoPO.setBrandGoodsType(brandDTO.getBrandGoodsType());
        brandSubInfoPO.setBrandPhoto(brandDTO.getBrandPhoto());
        brandSubInfoPO.setBrandDesc(brandDTO.getBrandDesc());
        brandSubInfoPO.setCreateStaffId(getCurrentUserID());
        brandSubInfoPO.setUpdateStaffId(getCurrentUserID());
        brandSubInfoPO.setStateTag(STATE_TAG_VALID);
        try {
            goodsBrandService.saveSubBrand(brandSubInfoPO);
        } catch (BusinessException e) {
            logger.error("子品牌新增同步更新主品牌更新时间失败，失败原因:{},入库参数", ExceptionUtil.stackTraceString(e.getCause()), brandSubInfoPO);
        } catch (Exception e) {
            logger.error("子品牌新增失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), brandDTO, brandSubInfoPO);
            throw new BusinessException("子品牌新增失败");
        }
        return ApiResult.success();
    }


    /**
     * 编辑品牌页面展示
     *
     * @param brandId
     * @return
     */
    @RequestMapping(value = "/editBrandShow", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult editBrandShow(@RequestParam("brandId") Integer brandId) {
        //查询子品牌信息
        Map subBrandInfo = goodsBrandService.getSubBrandInfo(brandId);
        if (subBrandInfo == null) {
            return BaseResult.error("子品牌信息错误");
        }
        return ApiResult.success(subBrandInfo);
    }

    /**
     * 编辑子品牌提交
     *
     * @param brandDTO
     * @return
     */
    @RequestMapping(value = "/updateSubBrand", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateSubBrand(BrandDTO brandDTO) {
        //参数完整性校验
        brandDTOValidate(brandDTO, BRAND_EDIT_DTO);
        //查询子品牌信息
        Map subBrandInfo = goodsBrandService.getSubBrandInfo(brandDTO.getBrandId());
        if (subBrandInfo == null) {
            return BaseResult.error("子品牌信息错误");
        }
        //子品牌名称变更时，校验变更后子品牌名称是否存在
        if (!RMap.getStr(subBrandInfo, "brandName").equals(brandDTO.getBrandName())) {
            Integer subBrandCount = goodsBrandService.countSubBrandByName(RMap.getInt(subBrandInfo, "mainBrandId"), brandDTO.getBrandName());
            if (subBrandCount != 0) {
                return BaseResult.error("编辑失败，该子品牌已经存在");
            }
        }
        //校验是否和原有信息相同
        if (brandDTO.getBrandName().trim().equals(RMap.getStr(subBrandInfo, "brandName"))
                && brandDTO.getBrandPhoto().trim().equals(RMap.getStr(subBrandInfo, "brandPhoto"))
                && brandDTO.getBrandDesc().trim().equals(RMap.getStr(subBrandInfo, "brandDesc"))
                && brandDTO.getBrandGoodsType().equals(RMap.getInt(subBrandInfo, "brandGoodsType"))) {
            return BaseResult.error("编辑失败，编辑后子品牌信息与原信息相同");
        }
        //校验商品类型编码是否合法
        Integer countResult = goodsAdminService.countGoodsTypeById(brandDTO.getBrandGoodsType());
        if (countResult == 0) {
            return BaseResult.error("子品牌编辑，品类选择信息错误");
        }
        //校验子品牌是否有绑定商品
        Integer goodsCount = goodsBrandService.countSubBrandGoods(brandDTO.getBrandId());
        if (goodsCount != 0) {
            return BaseResult.error("该子品牌已绑定了商品，不可编辑");
        }
        //更新
        BrandSubInfoPO brandSubInfoPO = new BrandSubInfoPO();
        brandSubInfoPO.setMainBrandId(RMap.getInt(subBrandInfo, "mainBrandId"));
        brandSubInfoPO.setBrandId(brandDTO.getBrandId());
        brandSubInfoPO.setBrandName(brandDTO.getBrandName());
        brandSubInfoPO.setBrandGoodsType(brandDTO.getBrandGoodsType());
        brandSubInfoPO.setBrandPhoto(brandDTO.getBrandPhoto());
        brandSubInfoPO.setBrandDesc(brandDTO.getBrandDesc());
        brandSubInfoPO.setUpdateStaffId(getCurrentUserID());
        try {
            goodsBrandService.updateSubBrand(brandSubInfoPO);
        } catch (BusinessException e) {
            logger.error("子品牌编辑同步更新主品牌更新时间失败，失败原因:{},入库参数", ExceptionUtil.stackTraceString(e.getCause()), brandSubInfoPO);
        } catch (Exception e) {
            logger.error("子品牌编辑失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), brandDTO, brandSubInfoPO);
            throw new BusinessException("子品牌编辑失败");
        }
        return BaseResult.success();
    }

    /**
     * 删除子品牌
     *
     * @param brandDTO
     * @return
     */
    @RequestMapping(value = "/removeSubBrand", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult removeSubBrand(BrandDTO brandDTO) {
        //参数完整性校验
        brandDTOValidate(brandDTO, BRAND_REMOVE_DTO);
        //查询子品牌信息
        Map subBrandInfo = goodsBrandService.getSubBrandInfo(brandDTO.getBrandId());
        if (subBrandInfo == null) {
            return BaseResult.error("子品牌信息错误");
        }
        //校验子品牌是否有绑定商品
        Integer goodsCount = goodsBrandService.countSubBrandGoods(brandDTO.getBrandId());
        if (goodsCount != 0) {
            return BaseResult.error("该子品牌已绑定了商品，不可编辑");
        }
        try {
            //删除子品牌
            Integer removeResult = goodsBrandService.removeSubBrand(brandDTO.getBrandId());
            if (removeResult != 1) {
                return BaseResult.error("子品牌删除失败");
            }
        } catch (Exception e) {
            logger.error("子品牌删除失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), brandDTO, brandDTO);
            throw new BusinessException("子品牌删除失败");
        }
        return BaseResult.success();
    }

    /**
     * 展示品牌列表
     *
     * @param brandSearchDTO
     * @return
     */
    @RequestMapping(value = "/listBrandInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listBrandInfo(BrandSearchDTO brandSearchDTO) {
        List<Map> brandInfoLists = goodsBrandService.listBrandInfo(brandSearchDTO);
        List<AdminBrandMainVO> mainBrandVOList = wrapViewObject(brandInfoLists);
        return ApiResult.success(mainBrandVOList);
    }


    /**
     * 子品牌禁用
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/brandDisable", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult brandDisable(BrandDTO brandDTO) {
        //参数完整性校验
        brandDTOValidate(brandDTO, BRAND_DISABLE_DTO);
        //查询子品牌信息
        Map subBrandInfo = goodsBrandService.getSubBrandInfo(brandDTO.getBrandId());
        if (RMap.isEmpty(subBrandInfo)) {
            return BaseResult.error("子品牌信息错误");
        }
        //准备数据
        BrandSubInfoPO brandSubInfoPO = new BrandSubInfoPO();
        brandSubInfoPO.setBrandId(brandDTO.getBrandId());
        brandSubInfoPO.setStateTag(STATE_TAG_INVALID);
        brandSubInfoPO.setUpdateStaffId(getCurrentUserID());
        //子品牌是否有绑定商品
        Integer goodsCount = goodsBrandService.countSubBrandGoods(brandDTO.getBrandId());
        try {
            if (goodsCount != 0) {
                //下架所有该子品牌商品并禁用品牌
                goodsBrandService.disableBrandWithGoods(brandSubInfoPO);
            } else {
                //未绑定商品，直接更新子品牌状态
                goodsBrandService.disableBrandWithoutGoods(brandSubInfoPO);
            }
        } catch (BusinessException e) {
            logger.error("子品牌禁用同步更新主品牌更新时间失败，失败原因:{},子品牌id:{}", ExceptionUtil.stackTraceString(e.getCause()), brandDTO.getBrandId());
        } catch (Exception e) {
            logger.error("子品牌禁用失败,失败原因:{},子品牌id:{}", ExceptionUtil.stackTraceString(e), brandDTO.getBrandId());
            return BaseResult.error("子品牌禁用失败");
        }
        return BaseResult.success();
    }

    /**
     * 子品牌启用
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/brandEnable", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult brandEnable(BrandDTO brandDTO) {
        //参数完整性校验
        brandDTOValidate(brandDTO, BRAND_ENABLE_DTO);
        //查询子品牌信息
        Map subBrandInfo = goodsBrandService.getSubBrandInfo(brandDTO.getBrandId());
        if (RMap.isEmpty(subBrandInfo)) {
            return BaseResult.error("子品牌信息错误");
        }
        //准备数据
        BrandSubInfoPO brandSubInfoPO = new BrandSubInfoPO();
        brandSubInfoPO.setBrandId(brandDTO.getBrandId());
        brandSubInfoPO.setStateTag(STATE_TAG_VALID);
        brandSubInfoPO.setUpdateStaffId(getCurrentUserID());
        //启用品牌
        try {
            goodsBrandService.enableBrand(brandSubInfoPO);
        } catch (BusinessException e) {
            logger.error("子品牌启用同步更新主品牌更新时间失败，失败原因:{},子品牌id:{}", ExceptionUtil.stackTraceString(e.getCause()), brandDTO.getBrandId());
        } catch (Exception e) {
            logger.error("子品牌启用失败,失败原因:{},子品牌id:{}", ExceptionUtil.stackTraceString(e), brandDTO.getBrandId());
            return BaseResult.error("子品牌启用失败");
        }
        return BaseResult.success();

    }


    /**
     * 将主品牌和子品牌左连接查询结果
     * 封装成成员为AdminBrandMainVO的list给前台展示
     *
     * @param brandInfoLists
     * @return
     */
    private List<AdminBrandMainVO> wrapViewObject(List<Map> brandInfoLists) {
        List<AdminBrandMainVO> mainBrandVOList = Lists.newArrayList();
        // 封装品牌展示信息
        for (Map brandInfo : brandInfoLists) {
            if (checkMainBrandExist(mainBrandVOList, brandInfo)) {
                AdminBrandMainVO brandMainVO = obtainMainBrandVO(mainBrandVOList, brandInfo);
                List<AdminBrandVO> brandVOList = brandMainVO.getBrandVOList();
                AdminBrandVO brandVO = new AdminBrandVO();
                brandVO.setBrandId(RMap.getInt(brandInfo, "brandId"));
                brandVO.setBrandName(RMap.getStr(brandInfo, "brandName"));
                brandVO.setBrandStateTag(RMap.getInt(brandInfo, "stateTag"));
                brandVO.setBrandUpdateTime(RDbTrans.asShowDate(RMap.getStr(brandInfo, "brandUpdateTime")));
                brandVOList.add(brandVO);
                brandMainVO.setBrandVOList(brandVOList);
            } else {
                AdminBrandMainVO mainVO = new AdminBrandMainVO();
                mainVO.setMainBrandId(RMap.getInt(brandInfo, "mainBrandId"));
                mainVO.setMainBrandName(RMap.getStr(brandInfo, "mainBrandName"));
                mainVO.setMainBrandUpdateTime(RDbTrans.asShowDate(RMap.getStr(brandInfo, "mainBrandUpdateTime")));
                if (brandInfo.get("brandId") != null) {
                    List<AdminBrandVO> brandVOList = Lists.newArrayList();
                    AdminBrandVO brandVO = new AdminBrandVO();
                    brandVO.setBrandId(RMap.getInt(brandInfo, "brandId"));
                    brandVO.setBrandName(RMap.getStr(brandInfo, "brandName"));
                    brandVO.setBrandStateTag(RMap.getInt(brandInfo, "stateTag"));
                    brandVO.setBrandUpdateTime(RDbTrans.asShowDate(RMap.getStr(brandInfo, "brandUpdateTime")));
                    brandVOList.add(brandVO);
                    mainVO.setBrandVOList(brandVOList);
                }
                mainBrandVOList.add(mainVO);
            }
        }
        return mainBrandVOList;
    }

    /**
     * 从主品牌list中获取主品牌id为当前遍历主品牌id的主品牌信息
     *
     * @param mainBrandVOList
     * @param brandInfo
     * @return
     */
    private AdminBrandMainVO obtainMainBrandVO(List<AdminBrandMainVO> mainBrandVOList, Map brandInfo) {
        Integer mainBrandId = RMap.getInt(brandInfo, "mainBrandId");
        AdminBrandMainVO brandMainVO = null;
        for (AdminBrandMainVO mainVO : mainBrandVOList) {
            if (mainVO.getMainBrandId().equals(mainBrandId)) {
                brandMainVO = mainVO;
                break;
            }
        }
        return brandMainVO;
    }

    /**
     * 校验主品牌list中是否已经添加当前mainBrandId信息
     *
     * @param mainBrandVOList
     * @param brandInfo
     * @return
     */
    private boolean checkMainBrandExist(List<AdminBrandMainVO> mainBrandVOList, Map brandInfo) {
        Integer mainBrandId = RMap.getInt(brandInfo, "mainBrandId");
        boolean flag = false;
        for (AdminBrandMainVO mainVO : mainBrandVOList) {
            if (mainVO.getMainBrandId().equals(mainBrandId)) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    /**
     * BrandDTO参数完整性校验
     *
     * @param brandDTO
     * @param validateType
     */
    private void brandDTOValidate(BrandDTO brandDTO, String validateType) {
        if (brandDTO == null) {
            throw new BusinessException(ValidateTypeEnum.valueOf(validateType).getValidateTypeName() + "请求参数为空");
        }
        //设置校验类型
        brandDTO.setValidateType(validateType);
        //字段非空校验
        FieldValidateUtil.validate(brandDTO);
    }


}
