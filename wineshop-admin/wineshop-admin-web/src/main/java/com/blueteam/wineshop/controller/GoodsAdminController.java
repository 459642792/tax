package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.RList;
import com.blueteam.base.lang.RMap;
import com.blueteam.base.lang.RStr;
import com.blueteam.base.lang.RDbTrans;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.base.util.FieldValidateUtil;
import com.blueteam.base.util.HttpRequestUtil;
import com.blueteam.entity.bo.BaseGoodsBO;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.*;
import com.blueteam.entity.vo.*;
import com.blueteam.wineshop.service.GoodsAdminService;
import com.blueteam.wineshop.service.GoodsAttrService;
import com.blueteam.wineshop.service.GoodsBrandService;
import com.blueteam.wineshop.service.GoodsVerifyService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.blueteam.base.constant.FieldValidateConstants.*;
import static com.blueteam.base.constant.GoodsAttrConstant.*;
import static com.blueteam.entity.po.BrandSubInfoPO.STATE_TAG_INVALID;
import static com.blueteam.entity.po.GoodsInfoPO.GOODS_STATE_DELISTING;
import static com.blueteam.entity.po.GoodsInfoPO.GOODS_STATE_LISTING;
import static com.blueteam.entity.po.GoodsVendorInfoPO.VENDOR_GOODS_DELISTING;
import static com.blueteam.entity.po.GoodsVerifyInfoPO.VERIFY_STATE_SUCCESS;

/**
 * 商品信息管理
 * Created by  NastyNas on 2017/10/24.
 */
@Controller
@RequestMapping("/goodsAdmin")
@AdminApiLogin
public class GoodsAdminController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsAdminController.class);
    //select属性类型
    private static final int SELECT_ATTR_TYPE = 0;
    //input属性类型
    private static final int INPUT_ATTR_TYPE = 1;
    //编辑商品默认属性值标志-1：选中
    private static final int SELECTED_FLAG_TRUE = 1;
    //编辑商品默认属性值标志-0：未选中
    private static final int SELECTED_FLAG_FALSE = 0;
    //默认瓶数
    private static final String BOTTLE_NUM_DEFAULT = "1";
    //瓶数
    private String bottleNum = BOTTLE_NUM_DEFAULT;
    //规格
    private String content;
    @Autowired
    GoodsAdminService goodsAdminService;
    @Autowired
    GoodsBrandService goodsBrandService;
    @Autowired
    GoodsAttrService goodsAttrService;
    @Autowired
    GoodsVerifyService goodsVerifyService;

    /**
     * 跳转页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listGoodsAdmin", method = RequestMethod.GET)
    public ModelAndView listGoodsAdmin() throws Exception {
        ModelAndView mav = new ModelAndView("goods/goods/goods_admin_list");
        return mav;
    }

    /**
     * 商品新增填写页中,获取所有酒类商品类别信息作为 "品类选择"
     *
     * @return
     */
    @RequestMapping(value = "/listGoodsType", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listGoodsType() {
        List<Map> goodsTypeLists = goodsAdminService.listGoodsType();
        return ApiResult.success(goodsTypeLists);
    }

    /**
     * 商品新增填写页中，根据商品类型获取商品基本属性
     *
     * @param goodsTypeId
     * @return
     */
    @RequestMapping(value = "/listBaseAttr", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listBaseAttr(@RequestParam("goodsTypeId") Integer goodsTypeId) {
        //校验商品类型编码是否合法
        Integer countResult = goodsAdminService.countGoodsTypeById(goodsTypeId);
        if (countResult == 0) {
            return BaseResult.error("商品新增填写页，品类选择信息错误");
        }
        //根据商品类型查询所有基本属性
        List<Map> baseAttrLists = goodsAttrService.listAttrInfo(goodsTypeId, BASE_LIST_TYPE);
        //封装必选属性列表VO
        List<AdminGoodsAttrVO> attrInputVOLists = wrapAttrInputVOList(baseAttrLists);
        return ApiResult.success(attrInputVOLists);
    }

    /**
     * 商品新增填写页中，根据商品类型获取商品特有属性
     *
     * @param goodsTypeId
     * @return
     */
    @RequestMapping(value = "/listSpecialAttr", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listSpecialAttr(@RequestParam("goodsTypeId") Integer goodsTypeId) {
        //校验商品类型编码是否合法
        Integer countResult = goodsAdminService.countGoodsTypeById(goodsTypeId);
        if (countResult == 0) {
            return BaseResult.error("商品新增填写页，品类选择信息错误");
        }
        //根据商品类型查询所有基本属性
        List<Map> specialAttrLists = goodsAttrService.listAttrInfo(goodsTypeId, SPECIAL_LIST_TYPE);
        //封装必选属性列表VO
        List<AdminGoodsAttrVO> attrInputVOLists = wrapAttrInputVOList(specialAttrLists);
        return ApiResult.success(attrInputVOLists);
    }


    /**
     * 模糊查询主品牌列表
     *
     * @param mainBrandName
     * @return
     */
    @RequestMapping(value = "/mainBrandSearch", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult mainBrandSearch(@RequestParam("mainBrandName") String mainBrandName) {
        //查询主品牌列表
        List<Map> mainBrandList = goodsBrandService.listMainBrand(mainBrandName);
        return ApiResult.success(mainBrandList);
    }

    /**
     * 查询子品牌列表
     *
     * @param mainBrandId
     * @return
     */
    @RequestMapping(value = "/subBrandSearch", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult subBrandSearch(@RequestParam("mainBrandId") Integer mainBrandId, @RequestParam("goodsType") Integer goodsType) {
        //查询子品牌列表
        List<AdminBrandVO> mainBrandList = goodsBrandService.listSubBrandTypeLimited(mainBrandId, goodsType);
        return ApiResult.success(mainBrandList);
    }

    /**
     * 商品新增
     *
     * @param goodsDTO
     * @return
     */
    @RequestMapping(value = "/goodsAdd", method = RequestMethod.POST)
    @ResponseBody
    public synchronized BaseResult goodsAdd(@RequestBody GoodsDTO goodsDTO) {
        //校验参数完整性
        goodsDTOValidate(goodsDTO, GOODS_ADD_DTO);
        //校验数据合法性
        checkDataValidity(goodsDTO);
        //准备入库信息
        GoodsInfoPO goodsPO = preGoodsInfo(goodsDTO);
        List<GoodsAttrInfoPO> goodsAttrPOList = preGoodsAttrInfo(goodsDTO);
        List<GoodsPhotoInfoPO> goodsPhotoPOList = preGoodsPhotoInfo(goodsDTO);
        GoodsDetailInfoPO goodsDetailPO = preGoodsDetailInfo(goodsDTO);
        List<GoodsVendorInfoPO> goodsVendorPOList = preGoodsVendorInfo(goodsDTO);
        List<VendorBrandInfoPO> vendorBrandPO = preVendorBrandInfo(goodsDTO);
        GoodsVerifyInfoPO goodsVerifyPO = preGoodsVerifyInfo(goodsDTO);

        //设置商品入库DTO信息
        GoodsAddDTO goodsAddDTO = new GoodsAddDTO();
        goodsAddDTO.setGoodsPO(goodsPO);
        goodsAddDTO.setGoodsAttrPOList(goodsAttrPOList);
        goodsAddDTO.setGoodsPhotoPOList(goodsPhotoPOList);
        goodsAddDTO.setGoodsDetailPO(goodsDetailPO);
        goodsAddDTO.setGoodsVendorPOList(goodsVendorPOList);
        goodsAddDTO.setVendorBrandPOList(vendorBrandPO);
        goodsAddDTO.setGoodsVerifyPO(goodsVerifyPO);
        try {
            //商品相关表落值
            goodsAdminService.saveGoodsRelated(goodsAddDTO);
        } catch (Exception e) {
            logger.error("商品入库失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), goodsDTO, goodsAddDTO);
            throw new BusinessException("商品入库失败");
        }
        return BaseResult.success();
    }

    /**
     * 准备商品审核更新信息
     *
     * @param goodsDTO
     * @return
     */
    private GoodsVerifyInfoPO preGoodsVerifyInfo(GoodsDTO goodsDTO) {
        GoodsVerifyInfoPO goodsVerifyInfoPO = new GoodsVerifyInfoPO();
        goodsVerifyInfoPO.setVerifyBarCode(goodsDTO.getBarCode());
        goodsVerifyInfoPO.setVerifyState(VERIFY_STATE_SUCCESS);
        return goodsVerifyInfoPO;
    }

    /**
     * 商品编辑提交
     *
     * @param goodsDTO
     * @return
     */
    @RequestMapping(value = "/goodsEdit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult goodsEdit(@RequestBody GoodsDTO goodsDTO) {
        //查询商品基本信息
        obtainBaseGoodsInfo(goodsDTO);
        //参数完整性校验
        goodsDTOValidate(goodsDTO, goodsDTO.getValidateType());
        //校验数据合法性
        checkEditDataValidity(goodsDTO);
        //准备编辑入库信息
        GoodsInfoPO goodsPO = preEditGoodsInfo(goodsDTO);
        List<GoodsAttrInfoPO> goodsAttrPOList = preEditGoodsAttrInfo(goodsDTO);
        List<GoodsPhotoInfoPO> goodsPhotoPOList = preGoodsPhotoInfo(goodsDTO);
        GoodsDetailInfoPO goodsDetailPO = preGoodsDetailInfo(goodsDTO);
        List<VendorBrandInfoPO> oldVendorBrandPOList = Lists.newArrayList();
        List<VendorBrandInfoPO> newVendorBrandPOList = Lists.newArrayList();
        preEditVendorBrandInfo(goodsDTO, oldVendorBrandPOList, newVendorBrandPOList);
        //设置商品入库DTO信息
        GoodsAddDTO goodsAddDTO = new GoodsAddDTO();
        goodsAddDTO.setGoodsPO(goodsPO);
        goodsAddDTO.setGoodsAttrPOList(goodsAttrPOList);
        goodsAddDTO.setGoodsPhotoPOList(goodsPhotoPOList);
        goodsAddDTO.setGoodsDetailPO(goodsDetailPO);
        goodsAddDTO.setOldVendorBrandPOList(oldVendorBrandPOList);
        goodsAddDTO.setVendorBrandPOList(newVendorBrandPOList);
        try {
            //商品相关表更新
            goodsAdminService.updateGoodsRelated(goodsAddDTO);
        } catch (Exception e) {
            logger.error("商品编辑失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), goodsDTO, goodsAddDTO);
            throw new BusinessException("商品编辑失败");
        }
        return BaseResult.success();
    }

    /**
     * 商品编辑页展示
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/goodsEditShow", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult goodsEditShow(@RequestParam("goodsId") Long goodsId) {
        //查询商品信息
        BaseGoodsBO baseGoodsBO = goodsAdminService.getGoodsInfo(goodsId);
        //查询该类商品基础属性
        List<Map> baseAttrList = goodsAttrService.listAttrInfo(baseGoodsBO.getGoodsType(), BASE_LIST_TYPE);
        //查询该类商品特有属性
        List<Map> specialAttrList = goodsAttrService.listAttrInfo(baseGoodsBO.getGoodsType(), SPECIAL_LIST_TYPE);
        //封装并填充基础属性列表VO
        List<AdminGoodsAttrVO> baseAttrShowVOList = fillSelectedAttr(goodsId, wrapAttrInputVOList(baseAttrList), BASE_LIST_TYPE);
        //封装并填充特有属性列表VO
        List<AdminGoodsAttrVO> specialAttrShowVOList = fillSelectedAttr(goodsId, wrapAttrInputVOList(specialAttrList), SPECIAL_LIST_TYPE);
        //封装并填充品牌列表VO
        List<AdminBrandVO> brandList = fillSelectedBrand(baseGoodsBO.getMainBrandId(), baseGoodsBO.getBrandId(), baseGoodsBO.getGoodsType());
        //封装图片列表VO
        List<AdminPhotoVO> photoList = goodsAdminService.listGoodsPhoto(goodsId);
        //封装商品编辑展示VO
        AdminGoodsEditShowVO editShowVO = new AdminGoodsEditShowVO();
        editShowVO.setGoodsId(baseGoodsBO.getGoodsId());
        editShowVO.setGoodsState(baseGoodsBO.getGoodsState());
        editShowVO.setGoodsName(baseGoodsBO.getGoodsName());
        editShowVO.setBarCode(baseGoodsBO.getBarCode());
        editShowVO.setMainBrandId(baseGoodsBO.getMainBrandId());
        editShowVO.setMainBrandName(baseGoodsBO.getMainBrandName());
        editShowVO.setGoodsType(baseGoodsBO.getGoodsType());
        editShowVO.setGoodsTypeName(baseGoodsBO.getGoodsTypeName());
        editShowVO.setGoodsDesc(baseGoodsBO.getGoodsDesc());
        editShowVO.setSuggestPrice(RDbTrans.asShowPrice(baseGoodsBO.getSuggestPrice()));
        editShowVO.setBaseAttrShowVOList(baseAttrShowVOList);
        editShowVO.setSpecialAttrShowVOList(specialAttrShowVOList);
        editShowVO.setBrandList(brandList);
        editShowVO.setPhotoList(photoList);
        return ApiResult.success(editShowVO);
    }


    /**
     * 商品上下架
     * 根据商品状态，当前为上架状态则执行下架操作，同理
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/goodsShelves", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult goodsShelves(Long goodsId) {
        //查询商品信息
        BaseGoodsBO baseGoodsBO = goodsAdminService.getGoodsInfo(goodsId);
        if (baseGoodsBO == null) {
            throw new BusinessException("商品信息错误");
        }
        //品牌已经被禁用的商品，不能执行上架操作
        if (onTheShelves(baseGoodsBO) && STATE_TAG_INVALID.equals(baseGoodsBO.getBrandStateTag())) {
            throw new BusinessException("商品品牌已被禁用，不能执行上架操作");
        }
        //准备编辑入库信息
        GoodsInfoPO goodsPO = new GoodsInfoPO();
        List<GoodsVendorInfoPO> goodsVendorPOList = Lists.newArrayList();
        List<VendorBrandInfoPO> vendorBrandPOList = Lists.newArrayList();
        preGoodsShelvesInfo(goodsPO, goodsVendorPOList, vendorBrandPOList, baseGoodsBO);
        //设置商品上下架dto信息
        GoodsShelvesDTO goodsShelvesDTO = new GoodsShelvesDTO();
        goodsShelvesDTO.setGoodsPO(goodsPO);
        goodsShelvesDTO.setGoodsVendorPOList(goodsVendorPOList);
        goodsShelvesDTO.setVendorBrandPOList(vendorBrandPOList);
        try {
            if (onTheShelves(baseGoodsBO)) {
                //上架
                goodsAdminService.onTheShelvesRelated(goodsShelvesDTO);
            } else {
                //下架
                goodsAdminService.takenOffShelvesRelated(goodsShelvesDTO);
            }
        } catch (Exception e) {
            logger.error("商品上下架失败!失败原因:{},请求参数:{},入库参数:{}", ExceptionUtil.stackTraceString(e), goodsId, goodsShelvesDTO);
            throw new BusinessException("商品上下架失败");
        }

        return BaseResult.success();
    }


    /**
     * 商品列表页展示/搜索
     *
     * @param goodsListSearchDTO
     * @return
     */
    @RequestMapping(value = "/listGoods", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listGoods(GoodsListSearchDTO goodsListSearchDTO) {
        //校验商品类型编码是否合法
        if (goodsListSearchDTO != null && goodsListSearchDTO.getGoodsType() != null) {
            Integer countResult = goodsAdminService.countGoodsTypeById(goodsListSearchDTO.getGoodsType());
            if (countResult == 0) {
                return BaseResult.error("商品新增填写页，品类选择信息错误");
            }
        }
        //校验搜索属性是否合法
        checkSearchAttr(goodsListSearchDTO);
        //分页获取商品列表
        PageResult<List<Map>> goodsInfoList = goodsAdminService.listGoodsInfo(goodsListSearchDTO);
        return ApiResult.success(goodsInfoList.getList(), goodsInfoList.getCount());
    }

    /**
     * 根据商品类别id获取商品搜索属性
     *
     * @param goodsTypeId 商品类别id
     * @return
     */
    @RequestMapping(value = "/listSearchAttr", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listSearchAttr(@RequestParam("goodsTypeId") Integer goodsTypeId) {
        //校验商品类型编码是否合法
        Integer countResult = goodsAdminService.countGoodsTypeById(goodsTypeId);
        if (countResult == 0) {
            return BaseResult.error("商品新增填写页，品类选择信息错误");
        }
        //根据商品类别查询搜索属性项
        List<Map> searchAttrLists = goodsAttrService.listAttrInfoByParentCode(goodsTypeId, SEARCH_ATTR_LIST);
        //封装属性列表VO
        List<AdminGoodsAttrVO> attrInputVOLists = wrapAttrInputVOList(searchAttrLists);
        return ApiResult.success(attrInputVOLists);
    }

    private void checkSearchAttr(GoodsListSearchDTO goodsListSearchDTO) {
        Integer goodsTypeId = goodsListSearchDTO.getGoodsType();
        //酒精度
        String alcoholAttrCode = goodsListSearchDTO.getAlcoholAttrCode();
        String alcoholValueNameFrom = goodsListSearchDTO.getAlcoholValueNameFrom();
        String alcoholValueNameTo = goodsListSearchDTO.getAlcoholValueNameTo();
        //包装
        String packageAttrCode = goodsListSearchDTO.getPackageAttrCode();
        String packageAttrValueCode = goodsListSearchDTO.getPackageAttrValueCode();
        /*
         * *查询属性校验原则*
         * 数据完整性：
         * 1 属性 和 属性值/属性值范围 要么都传，要么都不传
         * 2 若传入属性，必须传入商品类型
         * 数据合法性：
         * 注意：数据合法性暂不校验，数据不合法查询不到数据
         */
        if ((RStr.isNotEmpty(alcoholAttrCode) && RStr.isEmpty(alcoholValueNameFrom) && RStr.isEmpty(alcoholValueNameTo)) ||
                (RStr.isEmpty(alcoholAttrCode) && (RStr.isNotEmpty(alcoholValueNameFrom) || RStr.isNotEmpty(alcoholValueNameTo)))) {
            throw new BusinessException("酒精度查询信息错误");
        }
        if (RStr.isNotEmpty(packageAttrCode) && RStr.isEmpty(packageAttrValueCode) ||
                (RStr.isEmpty(packageAttrCode) && RStr.isNotEmpty(packageAttrValueCode))) {
            throw new BusinessException("包装查询信息错误");
        }
        if ((RStr.isNotEmpty(alcoholAttrCode) || RStr.isNotEmpty(packageAttrCode)) && goodsTypeId == null) {
            throw new BusinessException("商品类型和查询属性信息不匹配");
        }
    }

    /**
     * 准备上下架入库更新信息
     *
     * @param goodsPO
     * @param goodsVendorPOList
     * @param vendorBrandPOList
     * @param baseGoodsBO
     */
    private void preGoodsShelvesInfo(GoodsInfoPO goodsPO, List<GoodsVendorInfoPO> goodsVendorPOList, List<VendorBrandInfoPO> vendorBrandPOList, BaseGoodsBO baseGoodsBO) {
        //准备商品基本信息
        goodsPO.setGoodsState(onTheShelves(baseGoodsBO) ? GOODS_STATE_LISTING : GOODS_STATE_DELISTING);
        goodsPO.setUpdateStaffId(getCurrentUserID());
        goodsPO.setGoodsId(baseGoodsBO.getGoodsId());
        //管理员下架操作会同步下架商家商品，因此需要同步变更商家商品信息以及商家品牌信息
        if (!onTheShelves(baseGoodsBO)) {
            goodsVendorPOList.addAll(goodsAdminService.listGoodsVendor(baseGoodsBO.getGoodsId(), GoodsVendorInfoPO.VENDOR_GOODS__LISTING));
            for (GoodsVendorInfoPO goodsVendorPO : goodsVendorPOList) {
                //设置商家商品信息
                goodsVendorPO.setGoodsId(baseGoodsBO.getGoodsId());
                goodsVendorPO.setVendorGoodsState(VENDOR_GOODS_DELISTING);
                goodsVendorPO.setUpdateStaffId(getCurrentUserID());
                //设置商家品牌信息
                VendorBrandInfoPO vendorBrandPO = new VendorBrandInfoPO();
                vendorBrandPO.setVendorId(goodsVendorPO.getVendorId());
                vendorBrandPO.setBrandId(baseGoodsBO.getBrandId());
                vendorBrandPO.setUpdateStaffId(getCurrentUserID());
                vendorBrandPOList.add(vendorBrandPO);
            }
        }

    }

    /**
     * 是否执行上架操作:
     * 当前商品状态为下架状态则执行上架操作
     *
     * @param baseGoodsBO
     * @return
     */
    private boolean onTheShelves(BaseGoodsBO baseGoodsBO) {
        return baseGoodsBO.getGoodsState().equals(GOODS_STATE_DELISTING);
    }

    /**
     * 获取商品基本信息
     *
     * @param goodsDTO
     */
    private void obtainBaseGoodsInfo(GoodsDTO goodsDTO) {
        if (goodsDTO == null) {
            throw new BusinessException("商品编辑请求参数为空");
        }
        //查询商品基本信息
        BaseGoodsBO baseGoodsBO = goodsAdminService.getGoodsInfo(goodsDTO.getGoodsId());
        if (baseGoodsBO == null) {
            throw new BusinessException("商品信息错误");
        }
        //获取商品状态
        Integer goodsState = baseGoodsBO.getGoodsState();
        if ((goodsState != GOODS_STATE_LISTING && goodsState != GOODS_STATE_DELISTING)) {
            throw new BusinessException("商品信息错误");
        }
        //根据商品状态信息确定参数完整性校验类型
        if (goodsState == GOODS_STATE_LISTING) {
            goodsDTO.setValidateType(GOODS_EDIT_DTO);
            goodsDTO.setGoodsState(GOODS_STATE_LISTING);
        }
        if (goodsState == GOODS_STATE_DELISTING) {
            goodsDTO.setValidateType(GOODS_EDIT_LIMITED_DTO);
            goodsDTO.setGoodsState(GOODS_STATE_DELISTING);
        }
        goodsDTO.setBaseGoodsBO(baseGoodsBO);
        goodsDTO.setGoodsType(baseGoodsBO.getGoodsType());
    }


    /**
     * 准备商品编辑时商家品牌信息
     *
     * @param goodsDTO
     * @param oldVendorBrandPOList
     * @param newVendorBrandPOList
     */
    private void preEditVendorBrandInfo(GoodsDTO goodsDTO, List<VendorBrandInfoPO> oldVendorBrandPOList, List<VendorBrandInfoPO> newVendorBrandPOList) {
        //上架商品品牌编辑需要更新商品的商家商家品牌
        if (!isLimitedEdit(goodsDTO) && isBrandChanged(goodsDTO)) {
            Integer oldBrandId = goodsDTO.getBaseGoodsBO().getBrandId();
            Integer newBrandId = goodsDTO.getBrandId();
            //查询商品上架商家信息
            List<GoodsVendorInfoPO> goodsVendorPOList = goodsAdminService.listGoodsVendor(goodsDTO.getGoodsId(), GoodsVendorInfoPO.VENDOR_GOODS__LISTING);
            for (GoodsVendorInfoPO goodsVendorPO : goodsVendorPOList) {
                //准备原有商家品牌更新信息
                VendorBrandInfoPO oldVendorBrandPO = new VendorBrandInfoPO();
                oldVendorBrandPO.setVendorId(goodsVendorPO.getVendorId());
                oldVendorBrandPO.setBrandId(oldBrandId);
                oldVendorBrandPO.setUpdateStaffId(getCurrentUserID());
                oldVendorBrandPOList.add(oldVendorBrandPO);
                //准备当前商家品牌新增/更新信息
                VendorBrandInfoPO newVendorBrandPO = new VendorBrandInfoPO();
                newVendorBrandPO.setVendorId(goodsVendorPO.getVendorId());
                newVendorBrandPO.setBrandId(newBrandId);
                newVendorBrandPO.setBrandGoodsAmount(VendorBrandInfoPO.DEFAULT_AMOUNT);
                newVendorBrandPO.setAuthorityTag(VendorBrandInfoPO.AUTHORITY_TAG_VALID);
                newVendorBrandPO.setCreateStaffId(getCurrentUserID());
                newVendorBrandPO.setUpdateStaffId(getCurrentUserID());
                newVendorBrandPOList.add(newVendorBrandPO);
            }
        }
    }

    /**
     * 准备商品编辑时的商品属性信息
     *
     * @param goodsDTO
     * @return
     */
    private List<GoodsAttrInfoPO> preEditGoodsAttrInfo(GoodsDTO goodsDTO) {
        List<GoodsAttrInfoPO> attrPOList = Lists.newArrayList();
        List<AttrDTO> allAttrList = Lists.newArrayList();
        if (!isLimitedEdit(goodsDTO)) {
            allAttrList = goodsDTO.getBaseAttrList();
            allAttrList.addAll(goodsDTO.getSpecialAttrList());
            //获取瓶数
            bottleNum = obtainAttrValueName(allAttrList, BOTTLE_ATTR_CODE);
            //获取规格
            content = obtainAttrValueName(allAttrList, CONTENT_ATTR_CODE);
        } else {
            allAttrList = goodsDTO.getSpecialAttrList();
        }
        for (AttrDTO attrDTO : allAttrList) {
            GoodsAttrInfoPO goodsAttrInfoPO = new GoodsAttrInfoPO();
            goodsAttrInfoPO.setGoodsId(goodsDTO.getGoodsId());
            goodsAttrInfoPO.setAttrCode(attrDTO.getAttrCode());
            goodsAttrInfoPO.setAttrValueCode(attrDTO.getAttrValueCode());
            goodsAttrInfoPO.setAttrValueName(wrapAttrValueName(attrDTO));
            goodsAttrInfoPO.setAttrValueShowName(wrapAttrValueShowName(attrDTO));
            attrPOList.add(goodsAttrInfoPO);
        }
        return attrPOList;
    }

    /**
     * 准备商品编辑时的商品基础信息
     *
     * @param goodsDTO
     * @return
     */
    private GoodsInfoPO preEditGoodsInfo(GoodsDTO goodsDTO) {
        GoodsInfoPO goodsPO = new GoodsInfoPO();
        if (!isLimitedEdit(goodsDTO)) {
            goodsPO.setGoodsName(goodsDTO.getGoodsName());
            goodsPO.setGoodsLabel(goodsDTO.getGoodsName());
            goodsPO.setMainBrandId(goodsDTO.getMainBrandId());
            goodsPO.setBrandId(goodsDTO.getBrandId());
        }
        goodsPO.setGoodsId(goodsDTO.getGoodsId());
        goodsPO.setSuggestPrice(RDbTrans.asDbPrice(goodsDTO.getSuggestPrice()));
        goodsPO.setUpdateStaffId(getCurrentUserID());
        goodsPO.setOperateTag(GoodsInfoPO.OPERATE_TAG_EDITABLE);
        return goodsPO;
    }


    /**
     * 商品编辑，前台传参数据合法性校验
     *
     * @param goodsDTO
     */
    private void checkEditDataValidity(GoodsDTO goodsDTO) {
        if (!isLimitedEdit(goodsDTO)) {
            //校验品牌合法性
            Map brandInfo = goodsBrandService.getSubBrandInfo(goodsDTO.getBrandId());
            if (RMap.isEmpty(brandInfo)) {
                throw new BusinessException("商品编辑，品牌信息错误");
            }
            //校验基础属性
            checkAttrInfo(goodsDTO, BASE_LIST_TYPE);
        }
        //校验特有属性
        checkAttrInfo(goodsDTO, SPECIAL_LIST_TYPE);
    }

    /**
     * 编辑方式是否为限制编辑：
     * 商品商家状态不允许编辑基础信息，因此为限制编辑。
     *
     * @param goodsDTO
     * @return
     */
    private boolean isLimitedEdit(GoodsDTO goodsDTO) {
        return goodsDTO.getGoodsState() == GOODS_STATE_LISTING;
    }


    private List<AdminBrandVO> fillSelectedBrand(Integer mainBrandId, Integer brandId, Integer goodsTypeId) {
        //根据主品牌id和商品类别获取旗下品牌列表
        List<AdminBrandVO> brandVOList = goodsBrandService.listSubBrandTypeLimited(mainBrandId, goodsTypeId);
        for (AdminBrandVO brandVO : brandVOList) {
            if (Objects.equals(brandVO.getBrandId(), brandId)) {
                brandVO.setSelectedFlag(SELECTED_FLAG_TRUE);
            }
            brandVO.setBrandUpdateTime(RDbTrans.asShowDate(brandVO.getBrandUpdateTime()));
        }
        //若商品品牌已经无效，商品编辑页不显示默认子品牌
        return brandVOList;
    }

    /**
     * 将商品属性值填充到商品属性VO列表中作为默认选中项，作为商品编辑时的默认展示
     *
     * @param goodsId
     * @param attrInputVOList
     * @return
     */
    private List<AdminGoodsAttrVO> fillSelectedAttr(Long goodsId, List<AdminGoodsAttrVO> attrInputVOList, Integer attrListType) {
        //商品当前基本属性信息
        List<GoodsAttrInfoPO> baseAttrList = goodsAdminService.listGoodsAttr(goodsId, attrListType);
        for (AdminGoodsAttrVO goodsAttrVO : attrInputVOList) {
            for (GoodsAttrInfoPO goodsAttrPO : baseAttrList) {
                if (goodsAttrVO.getAttrCode().equals(goodsAttrPO.getAttrCode())) {
                    //select类型属性：将商品属性值作为商品编辑列表页默认选中项
                    if (SELECT_ATTR_TYPE == goodsAttrVO.getAttrType()) {
                        List<AdminGoodsAttrValVO> attrValVOList = goodsAttrVO.getAttrValVOList();
                        for (AdminGoodsAttrValVO attrValVO : attrValVOList) {
                            if (attrValVO.getAttrValueCode().equals(goodsAttrPO.getAttrValueCode())) {
                                attrValVO.setSelectedFlag(SELECTED_FLAG_TRUE);
                            }
                        }
                    }
                    //input类型属性：将商品属性值作为商品属性默认输入框内容
                    if (INPUT_ATTR_TYPE == goodsAttrVO.getAttrType()) {
                        goodsAttrVO.setAttrValueName(goodsAttrPO.getAttrValueName());
                    }
                }
            }
        }
        return attrInputVOList;
    }


    /**
     * 准备商家品牌信息
     *
     * @param goodsDTO
     * @return
     */
    private List<VendorBrandInfoPO> preVendorBrandInfo(GoodsDTO goodsDTO) {
        //条形码未关联商家审核信息
        if (RList.isEmpty(goodsDTO.getGoodsVerifyPOList())) {
            return null;
        }
        //商家审核新增
        List<VendorBrandInfoPO> vendorBrandPOList = Lists.newArrayList();
        List<GoodsVerifyInfoPO> goodsVerifyPOList = goodsDTO.getGoodsVerifyPOList();
        for (GoodsVerifyInfoPO goodsVerifyPO : goodsVerifyPOList) {
            //商家上架状态审核新增
            if (Objects.equals(goodsVerifyPO.getVerifyGoodsState(), GoodsVerifyInfoPO.VERIFY_GOODS__LISTING)) {
                VendorBrandInfoPO vendorBrandPO = new VendorBrandInfoPO();
                vendorBrandPO.setVendorId(goodsVerifyPO.getVendorId());
                vendorBrandPO.setBrandId(goodsDTO.getBrandId());
                vendorBrandPO.setBrandGoodsAmount(VendorBrandInfoPO.DEFAULT_AMOUNT);
                vendorBrandPO.setAuthorityTag(VendorBrandInfoPO.AUTHORITY_TAG_VALID);
                vendorBrandPO.setCreateStaffId(getCurrentUserID());
                vendorBrandPO.setUpdateStaffId(getCurrentUserID());
                vendorBrandPOList.add(vendorBrandPO);
            }
        }
        return vendorBrandPOList;
    }

    /**
     * 准备商品商家信息
     *
     * @param goodsDTO
     * @return
     */
    private List<GoodsVendorInfoPO> preGoodsVendorInfo(GoodsDTO goodsDTO) {
        List<GoodsVendorInfoPO> goodsVendorPOList = Lists.newArrayList();
        //通过条形码获取所有商家审核信息
        List<GoodsVerifyInfoPO> goodsVerifyPOList = goodsAdminService.getVerifyInfo(goodsDTO.getBarCode(), GoodsVerifyInfoPO.VERIFY_STATE_TODO);
        goodsDTO.setGoodsVerifyPOList(goodsVerifyPOList);
        //条形码未关联商家审核信息
        if (RList.isEmpty(goodsVerifyPOList)) {
            return null;
        }
        for (GoodsVerifyInfoPO goodsVerifyPO : goodsVerifyPOList) {
            GoodsVendorInfoPO goodsVendorPO = new GoodsVendorInfoPO();
            goodsVendorPO.setGoodsId(goodsDTO.getGoodsId());
            goodsVendorPO.setVendorId(goodsVerifyPO.getVendorId());
            goodsVendorPO.setSalePrice(goodsVerifyPO.getVerifySalePrice());
            goodsVendorPO.setVendorGoodsState(goodsVerifyPO.getVerifyGoodsState());
            goodsVendorPO.setCreateStaffId(getCurrentUserID());
            goodsVendorPO.setUpdateStaffId(getCurrentUserID());
            goodsVendorPOList.add(goodsVendorPO);
        }
        return goodsVendorPOList;
    }


    /**
     * 校验数据合法性
     *
     * @param goodsDTO
     */
    private void checkDataValidity(GoodsDTO goodsDTO) {
        //商品类别合法性
        Integer countResult = goodsAdminService.countGoodsTypeById(goodsDTO.getGoodsType());
        if (countResult == 0) {
            throw new BusinessException("商品新增，商品类别信息错误");
        }
        //品牌合法性
        Map brandInfo = goodsBrandService.getSubBrandInfo(goodsDTO.getBrandId());
        if (RMap.isEmpty(brandInfo)) {
            throw new BusinessException("商品新增，品牌信息错误");
        }
        //校验商品条形码是否已经存在
        Integer goodsCount = goodsAdminService.countGoodsByBarCode(goodsDTO.getBarCode());
        if (goodsCount != 0) {
            throw new BusinessException("该商品条码已添加了商品，请勿重复添加。");
        }
        //校验基础属性
        checkAttrInfo(goodsDTO, BASE_LIST_TYPE);
        //校验特有属性
        checkAttrInfo(goodsDTO, SPECIAL_LIST_TYPE);
    }

    /**
     * 准备商品详情
     *
     * @param goodsDTO
     * @return
     */
    private GoodsDetailInfoPO preGoodsDetailInfo(GoodsDTO goodsDTO) {
        GoodsDetailInfoPO goodsDetailPO = new GoodsDetailInfoPO();
        goodsDetailPO.setGoodsId(goodsDTO.getGoodsId());
        goodsDetailPO.setGoodsDesc(goodsDTO.getGoodsDesc());
        return goodsDetailPO;
    }

    /**
     * 准备商品图片信息
     *
     * @param goodsDTO
     * @return
     */
    private List<GoodsPhotoInfoPO> preGoodsPhotoInfo(GoodsDTO goodsDTO) {
        List<GoodsPhotoInfoPO> goodsPhotoPOList = Lists.newArrayList();
        for (String photoUrl : goodsDTO.getGoodsPhotoList()) {
            GoodsPhotoInfoPO goodsPhotoPO = new GoodsPhotoInfoPO();
            goodsPhotoPO.setGoodsId(goodsDTO.getGoodsId());
            goodsPhotoPO.setGoodsPhoto(photoUrl);
            goodsPhotoPO.setPhotoTone(getPhotoToneByCloud(photoUrl));
            goodsPhotoPO.setPhotoState(GoodsPhotoInfoPO.PHOTO_STATE_VALID);
            goodsPhotoPOList.add(goodsPhotoPO);
        }
        return goodsPhotoPOList;
    }

    /**
     * 通过阿里云图片保存地址从阿里云端获取图片色调
     *
     * @param photoUrl
     * @return
     */
    private String getPhotoToneByCloud(String photoUrl) {
        String photoTone = "";
        try {
            photoTone = HttpRequestUtil.httpRequest(photoUrl + "?x-oss-process=image/average-hue");
        } catch (Exception e) {

        }
        return photoTone;
    }

    /**
     * 准备商品属性信息
     *
     * @param goodsDTO
     * @return
     */
    private List<GoodsAttrInfoPO> preGoodsAttrInfo(GoodsDTO goodsDTO) {
        List<GoodsAttrInfoPO> attrPOList = Lists.newArrayList();
        List<AttrDTO> allAttrList = goodsDTO.getBaseAttrList();
        allAttrList.addAll(goodsDTO.getSpecialAttrList());
        //获取瓶数
        bottleNum = obtainAttrValueName(allAttrList, BOTTLE_ATTR_CODE);
        //获取规格
        content = obtainAttrValueName(allAttrList, CONTENT_ATTR_CODE);
        for (AttrDTO attrDTO : allAttrList) {
            GoodsAttrInfoPO goodsAttrInfoPO = new GoodsAttrInfoPO();
            goodsAttrInfoPO.setGoodsId(goodsDTO.getGoodsId());
            goodsAttrInfoPO.setAttrCode(attrDTO.getAttrCode());
            goodsAttrInfoPO.setAttrValueCode(attrDTO.getAttrValueCode());
            goodsAttrInfoPO.setAttrValueName(wrapAttrValueName(attrDTO));
            goodsAttrInfoPO.setAttrValueShowName(wrapAttrValueShowName(attrDTO));
            attrPOList.add(goodsAttrInfoPO);
        }
        return attrPOList;
    }

    /**
     * 封装属性AttrValueName
     *
     * @param attrDTO
     * @return
     */
    private String wrapAttrValueName(AttrDTO attrDTO) {
//        if (CONTENT_ATTR_CODE.equals(attrDTO.getParentAttrCode())) {
//            return Integer.parseInt(content) * Integer.parseInt(bottleNum) + "";
//        }
        return attrDTO.getAttrValueName();
    }

    /**
     * 根据属性dto列表获取某一属性的AttrValueName
     *
     * @param allAttrList
     * @param attrCode
     * @return
     */
    private String obtainAttrValueName(List<AttrDTO> allAttrList, String attrCode) {
        for (AttrDTO attrDTO : allAttrList) {
            if (attrDTO.getParentAttrCode().equals(attrCode)) {
                return attrDTO.getAttrValueName();
            }
        }
        return null;
    }

    /**
     * 封装属性值的描述信息
     * select类型：attrValueShowName 等于 attrValueName
     * input类型：根据具体属性编码 通过attrValueName进行拼接
     *
     * @param attrDTO
     * @return
     */
    private String wrapAttrValueShowName(AttrDTO attrDTO) {
        if (ALCOHOL_ATTR_CODE.equals(attrDTO.getParentAttrCode())) {
            return attrDTO.getAttrValueName() + "度";
        }
        if (BOTTLE_ATTR_CODE.equals(attrDTO.getParentAttrCode())) {
            bottleNum = attrDTO.getAttrValueName();
            return attrDTO.getAttrValueName() + "瓶";
        }
        if (CONTENT_ATTR_CODE.equals(attrDTO.getParentAttrCode())) {
            return attrDTO.getAttrValueName() + "ml*" + bottleNum;
        }
        if (SPIRIT_YEAR_ATTR_CODE.equals(attrDTO.getAttrCode())) {
            return RStr.isEmpty(attrDTO.getAttrValueName()) ? attrDTO.getAttrValueName() : attrDTO.getAttrValueName() + "年";
        }
        if (GRAPE_YEAR_ATTR_CODE.equals(attrDTO.getAttrCode())) {
            return RStr.isEmpty(attrDTO.getAttrValueName()) ? attrDTO.getAttrValueName() : attrDTO.getAttrValueName() + "年";
        }
        if (BEER_MALT_ATTR_CODE.equals(attrDTO.getAttrCode())) {
            return RStr.isEmpty(attrDTO.getAttrValueName()) ? attrDTO.getAttrValueName() : attrDTO.getAttrValueName() + "度";
        }
        return attrDTO.getAttrValueName();
    }

    /**
     * 准备商品基本信息
     *
     * @param goodsDTO
     * @return
     */
    private GoodsInfoPO preGoodsInfo(GoodsDTO goodsDTO) {
        //mysql自定义函数获取唯一goodsId
        Long goodsId = goodsAdminService.getGoodsId(goodsDTO.getGoodsType());
        goodsDTO.setGoodsId(goodsId);
        GoodsInfoPO goodsPO = new GoodsInfoPO();
        goodsPO.setGoodsId(goodsId);
        goodsPO.setGoodsName(goodsDTO.getGoodsName());
        goodsPO.setGoodsLabel(goodsDTO.getGoodsName());
        goodsPO.setGoodsType(goodsDTO.getGoodsType());
        goodsPO.setBarCode(goodsDTO.getBarCode());
        goodsPO.setMainBrandId(goodsDTO.getMainBrandId());
        goodsPO.setBrandId(goodsDTO.getBrandId());
        goodsPO.setSuggestPrice(RDbTrans.asDbPrice(goodsDTO.getSuggestPrice()));
        goodsPO.setGoodsState(GOODS_STATE_LISTING);
        goodsPO.setOperateTag(GoodsInfoPO.OPERATE_TAG_EDITABLE);
        goodsPO.setCreateStaffId(getCurrentUserID());
        goodsPO.setUpdateStaffId(getCurrentUserID());
        return goodsPO;
    }

    /**
     * 校验属性信息
     *
     * @param goodsDTO
     * @param attrListType
     */
    private void checkAttrInfo(GoodsDTO goodsDTO, Integer attrListType) {
        String attrTypeName = Objects.equals(attrListType, BASE_LIST_TYPE) ? "基础" : "特有";
        List<AttrDTO> attrList = Objects.equals(attrListType, BASE_LIST_TYPE) ? goodsDTO.getBaseAttrList() : goodsDTO.getSpecialAttrList();
        //校验应该传入后台的基础/特有属性的数量是否匹配
        Integer attrCount = goodsAttrService.countAttr(goodsDTO.getGoodsType(), attrListType);
        if (!Objects.equals(attrCount, attrList.size())) {
            throw new BusinessException("商品" + attrTypeName + "属性信息缺失");
        }
        //校验属性编码、属性值编码、属性类别、属性值名称是否匹配。即基础select属性attrValueCode不为空，基础input属性attrValueName不为空
        List<AttrInfoPO> attrInfoPOList = goodsAttrService.listAttrInfoByDTO(attrList, goodsDTO.getGoodsType(), attrListType);
        if (attrInfoPOList == null || attrInfoPOList.size() != attrList.size()) {
            throw new BusinessException("商品" + attrTypeName + "属性信息错误");
        }
        //筛选出为select类型同时已明确选择属性值的属性信息
        List<AttrDTO> selectTypeAttrList = Lists.newArrayList();
        for (AttrDTO attrDTO : attrList) {
            if (Objects.equals(attrDTO.getAttrType(), SELECT_ATTR_TYPE) && !UNSELECT_ATTR_VALUE_CODE.equals(attrDTO.getAttrValueCode())) {
                selectTypeAttrList.add(attrDTO);
            }
        }
        //校验select类型属性值编码attrValueCode是否合法，并设置属性值
        if (selectTypeAttrList != null && selectTypeAttrList.size() > 0) {
            List<AttrValInfoPO> attrValInfoPOList = goodsAttrService.listAttrValueInfoByDTO(selectTypeAttrList);
            if (attrValInfoPOList == null || attrValInfoPOList.size() != selectTypeAttrList.size()) {
                throw new BusinessException("商品" + attrTypeName + "属性input类型属性值错误");
            }
            //设置属性值名称attrValueName
            for (AttrValInfoPO attrValPO : attrValInfoPOList) {
                for (AttrDTO attrDTO : attrList) {
                    if (attrValPO.getAttrCode().equals(attrDTO.getAttrCode())) {
                        attrDTO.setAttrValueName(attrValPO.getAttrValueName());
                    }
                }
            }
        }
    }

    /**
     * 封装属性列表VO
     *
     * @param baseAttrLists
     * @return
     */
    private List<AdminGoodsAttrVO> wrapAttrInputVOList(List<Map> baseAttrLists) {
        List<AdminGoodsAttrVO> attrVOList = Lists.newArrayList();
        for (Map baseAttr : baseAttrLists) {
            if (checkAttrExist(attrVOList, baseAttr)) {
                AdminGoodsAttrVO attrVO = obtainAttrVO(attrVOList, baseAttr);
                List<AdminGoodsAttrValVO> attrValVOList = attrVO.getAttrValVOList();
                AdminGoodsAttrValVO attrValVO = new AdminGoodsAttrValVO();
                attrValVO.setAttrValueCode(RMap.getStr(baseAttr, "attrValueCode"));
                attrValVO.setAttrValueName(RMap.getStr(baseAttr, "attrValueName"));
                attrValVOList.add(attrValVO);
                attrVO.setAttrValVOList(attrValVOList);
            } else {
                AdminGoodsAttrVO attrVO = new AdminGoodsAttrVO();
                attrVO.setAttrCode(RMap.getStr(baseAttr, "attrCode"));
                attrVO.setParentAttrCode(RMap.getStr(baseAttr, "parentAttrCode"));
                attrVO.setAttrName(RMap.getStr(baseAttr, "attrName"));
                attrVO.setAttrType(RMap.getInt(baseAttr, "attrType"));
                attrVO.setNecessaryTag(RMap.getStr(baseAttr, "necessaryTag"));
                //select类型属性
                if (baseAttr.get("attrValueCode") != null) {
                    List<AdminGoodsAttrValVO> attrValVOList = Lists.newArrayList();
                    AdminGoodsAttrValVO attrValVO = new AdminGoodsAttrValVO();
                    attrValVO.setAttrValueCode(RMap.getStr(baseAttr, "attrValueCode"));
                    attrValVO.setAttrValueName(RMap.getStr(baseAttr, "attrValueName"));
                    attrValVOList.add(attrValVO);
                    attrVO.setAttrValVOList(attrValVOList);
                } else {
                    //input类型属性值默认展示为空
                    attrVO.setAttrValueName("");
                }
                attrVOList.add(attrVO);
            }
        }
        return attrVOList;
    }

    private AdminGoodsAttrVO obtainAttrVO(List<AdminGoodsAttrVO> attrVOList, Map baseAttr) {
        AdminGoodsAttrVO goodsAttrVO = null;
        for (AdminGoodsAttrVO attrVO : attrVOList) {
            if (attrVO.getAttrCode().equals(RMap.getStr(baseAttr, "attrCode"))) {
                goodsAttrVO = attrVO;
                break;
            }
        }
        return goodsAttrVO;
    }

    private boolean checkAttrExist(List<AdminGoodsAttrVO> attrVOList, Map baseAttr) {
        boolean flag = false;
        for (AdminGoodsAttrVO attrVO : attrVOList) {
            if (attrVO.getAttrCode().equals(RMap.getStr(baseAttr, "attrCode"))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 品牌是否被编辑
     *
     * @param goodsDTO
     * @return
     */
    public boolean isBrandChanged(GoodsDTO goodsDTO) {
        Integer oldBrandId = goodsDTO.getBaseGoodsBO().getBrandId();
        Integer newBrandId = goodsDTO.getBrandId();
        return !(Objects.equals(oldBrandId, newBrandId));
    }

    /**
     * goodsDTO参数完整性校验
     *
     * @param goodsDTO
     * @param validateType
     */
    private void goodsDTOValidate(GoodsDTO goodsDTO, String validateType) {
        if (goodsDTO == null) {
            throw new BusinessException(ValidateTypeEnum.valueOf(validateType).getValidateTypeName() + "请求参数为空");
        }
        //设置校验类型
        goodsDTO.setValidateType(validateType);
        //字段非空校验
        FieldValidateUtil.validate(goodsDTO);
    }

}
