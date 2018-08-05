package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.RMap;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.base.util.FieldValidateUtil;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.AttrValInfoPO;
import com.blueteam.wineshop.service.GoodsAttrService;
import com.blueteam.wineshop.service.UserService;
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


/**
 * 商品属性管理
 * <p>
 * Created by  NastyNas on 2017/10/16.
 */
@Controller
@RequestMapping("/goodsAttr")
@AdminApiLogin
public class GoodsAttrController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsAttrController.class);

    @Autowired
    GoodsAttrService goodsAttrService;
    @Autowired
    UserService userService;

    /**
     * 跳转页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listGoodsAttr", method = RequestMethod.GET)
    public ModelAndView listGoodsAttr() throws Exception {
        ModelAndView mav = new ModelAndView("goods/attr/goods_attr_list");
        return mav;
    }

    /**
     * 获取属性类型列表：通用、白酒、红酒等
     *
     * @return
     */
    @RequestMapping(value = "/attrTypeList", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult attrTypeList() {
        List attrTypeList = goodsAttrService.listAttrType();
        return ApiResult.success(attrTypeList);
    }

    /**
     * 根据商品类型获取该类型下的展示属性，如，白酒类型下的属性：香型、省份、酿造工艺等
     *
     * @param attrTypeId
     * @return
     */
    @RequestMapping(value = "/attrList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult attrList(@RequestParam(value = "attrTypeId") Integer attrTypeId) {
        List attrCodeList = goodsAttrService.listAttrCode(attrTypeId);
        return ApiResult.success(attrCodeList);
    }

    /**
     * 根据属性编码获取该属性下的所有属性值，如：香型属性下的属性值:酱香、浓香、清香等
     * 前台获取list长度为1并且ATTR_TYPE=1时表示该属性为input属性，需要按照要求自定义展示
     *
     * @param attrValueSearchDTO
     * @return
     */
    @RequestMapping(value = "/attrValueList", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult attrValueList(AttrValueSearchDTO attrValueSearchDTO) {
        FieldValidateUtil.validate(attrValueSearchDTO);
        PageResult<List<Map>> attrValueList = goodsAttrService.listAttrValue(attrValueSearchDTO);
        return ApiResult.success(attrValueList.getList(), attrValueList.getCount());
    }

    /**
     * 为指定属性新增属性值
     *
     * @return
     */
    @RequestMapping(value = "/attrAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult attrAdd(AttrValueDTO attrValueDTO) {
        //参数完整性校验
        attrValueDTOValidate(attrValueDTO, ATTR_ADD_DTO);
        //获取属性信息
        Map attrInfoMap = goodsAttrService.getAttrInfo(attrValueDTO.getAttrCode());
        if (RMap.isEmpty(attrInfoMap) || RMap.getInt(attrInfoMap, "operateTag") == 0) {
            return BaseResult.error("该属性不可添加");
        }
        //校验属性值是否存在
        Integer valueCount = goodsAttrService.countAttrValueName(attrValueDTO.getAttrCode(), attrValueDTO.getAttrValueName().trim());
        if (valueCount >= 1) {
            return BaseResult.error("属性值已经存在");
        }
        //获取当前属性的最大valueIndex以及attrValueShowOrder，新增属性值的valueIndex和attrValueShowOrder在原有最大值得基础依次加1和加5
        Map maxValueMap = goodsAttrService.getExistentMaxValue(attrValueDTO.getAttrCode());
        String newAttrCode = attrValueDTO.getAttrCode();
        Integer newValueIndex = RMap.isEmpty(maxValueMap) ? 100 : RMap.getInt(maxValueMap, "maxValueIndex") + 1;
        //新增的属性值编码由属性编码拼接valueIndex，确保属性值编码的唯一性
        String newAttrValueCode = newAttrCode + newValueIndex;
        Integer newOperateTag = RMap.getInt(attrInfoMap, "operateTag");
        Integer newShowOrder = RMap.isEmpty(maxValueMap) ? 5 : RMap.getInt(maxValueMap, "maxShowOrder") + 5;
        AttrValInfoPO attrValInfoPO = new AttrValInfoPO();
        attrValInfoPO.setAttrCode(newAttrCode);
        attrValInfoPO.setValueIndex(newValueIndex);
        attrValInfoPO.setAttrValueCode(newAttrValueCode);
        attrValInfoPO.setOperateTag(newOperateTag);
        attrValInfoPO.setAttrValueShowOrder(newShowOrder);
        attrValInfoPO.setAttrValueName(attrValueDTO.getAttrValueName().trim());
        attrValInfoPO.setCreateStaffId(getCurrentUserID());
        attrValInfoPO.setUpdateStaffId(getCurrentUserID());
        try {
            //数据入库
            Integer addResult = goodsAttrService.saveAttrValue(attrValInfoPO);
            if (addResult != 1) {
                return BaseResult.error("属性值添加失败");
            }
        } catch (Exception e) {
            logger.error("属性值添加失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), attrValueDTO, attrValInfoPO);
            throw new BusinessException("属性值添加失败");
        }
        return BaseResult.success();
    }

    /**
     * 编辑属性值
     *
     * @param attrValueDTO
     * @return
     */
    @RequestMapping(value = "/attrValueUpdate", method = RequestMethod.POST)
    @ResponseBody
    @AdminApiLogin
    public BaseResult attrValueUpdate(AttrValueDTO attrValueDTO) {
        //参数完整性校验
        attrValueDTOValidate(attrValueDTO, ATTR_EDIT_DTO);
        //密码校验
        BaseResult user = userService.pwdLogin(getUserName(), attrValueDTO.getPassword());
        if (!user.isSuccess()) {
            return user;
        }
        //校验属性值是否存在
        Integer valueCount = goodsAttrService.countAttrValueName(attrValueDTO.getAttrCode(), attrValueDTO.getAttrValueName().trim());
        if (valueCount >= 1) {
            return BaseResult.error("属性值已经存在");
        }
        //商品校验
        Integer goodsCount = goodsAttrService.countGoodsAttrValueCode(attrValueDTO.getAttrValueCode());
        if (goodsCount > 0) {
            return BaseResult.error("该属性值下有" + goodsCount + "款商品，不可编辑/删除");
        }
        //属性值校验
        attrValueDTO.setAttrValueName(attrValueDTO.getAttrValueName().trim());
        Map attrValueInfo = goodsAttrService.getAttrValueInfo(attrValueDTO.getAttrCode(), attrValueDTO.getAttrValueCode(), AttrValInfoPO.OPERATE_TAG_VALID);
        if (RMap.isEmpty(attrValueInfo) || RMap.getStr(attrValueInfo, "attrValueName").trim().equals(attrValueDTO.getAttrValueName())) {
            return BaseResult.error("新属性值不可与原属性值相同");
        }
        //准备更新入库
        AttrValInfoPO attrValInfoPO = new AttrValInfoPO();
        attrValInfoPO.setAttrCode(attrValueDTO.getAttrCode());
        attrValInfoPO.setAttrValueCode(attrValueDTO.getAttrValueCode());
        attrValInfoPO.setAttrValueName(attrValueDTO.getAttrValueName());
        attrValInfoPO.setUpdateStaffId(getCurrentUserID());
        attrValInfoPO.setOperateTag(AttrValInfoPO.OPERATE_TAG_VALID);
        try {
            //数据更新
            Integer updateResult = goodsAttrService.updateAttrValueInfo(attrValInfoPO);
            if (updateResult != 1) {
                return BaseResult.error("属性值编辑失败");
            }
        } catch (Exception e) {
            logger.error("属性值编辑失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), attrValueDTO, attrValInfoPO);
            throw new BusinessException("属性值编辑失败");
        }
        return BaseResult.success();
    }

    /**
     * 删除属性值
     *
     * @param attrValueDTO
     * @return
     */
    @RequestMapping(value = "/attrValueRemove", method = RequestMethod.POST)
    @ResponseBody
    @AdminApiLogin
    public BaseResult attrValueRemove(AttrValueDTO attrValueDTO) {
        //参数完整性校验
        attrValueDTOValidate(attrValueDTO, ATTR_REMOVE_DTO);
        //密码校验
        BaseResult user = userService.pwdLogin(getUserName(), attrValueDTO.getPassword());
        if (!user.isSuccess()) {
            return user;
        }
        //商品校验
        Integer goodsCount = goodsAttrService.countGoodsAttrValueCode(attrValueDTO.getAttrValueCode());
        if (goodsCount > 0) {
            return BaseResult.error("该属性值下有" + goodsCount + "款商品，不可编辑/删除");
        }
        //属性值校验
        Map attrValueInfo = goodsAttrService.getAttrValueInfo(attrValueDTO.getAttrCode(), attrValueDTO.getAttrValueCode(), AttrValInfoPO.OPERATE_TAG_VALID);
        if (RMap.isEmpty(attrValueInfo)) {
            return BaseResult.error("属性值不存在");
        }
        //删除属性值
        AttrValInfoPO attrValInfoPO = new AttrValInfoPO();
        attrValInfoPO.setAttrCode(attrValueDTO.getAttrCode());
        attrValInfoPO.setAttrValueCode(attrValueDTO.getAttrValueCode());
        attrValInfoPO.setOperateTag(AttrValInfoPO.OPERATE_TAG_VALID);
        try {
            Integer removeResult = goodsAttrService.removeAttrValueInfo(attrValInfoPO);
            if (removeResult != 1) {
                return BaseResult.error("属性值删除失败");
            }
        } catch (Exception e) {
            logger.error("属性值删除失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), attrValueDTO, attrValInfoPO);
            throw new BusinessException("属性值删除失败");
        }
        return BaseResult.success();
    }


    /**
     * AttrValueDTO类型dto校验
     *
     * @param attrValueDTO
     * @param validateType
     */
    private void attrValueDTOValidate(AttrValueDTO attrValueDTO, String validateType) {
        if (attrValueDTO == null) {
            throw new BusinessException(ValidateTypeEnum.valueOf(validateType).getValidateTypeName() + "请求参数为空");
        }
        //设置校验类型
        attrValueDTO.setValidateType(validateType);
        //字段非空校验
        FieldValidateUtil.validate(attrValueDTO);
    }
}
