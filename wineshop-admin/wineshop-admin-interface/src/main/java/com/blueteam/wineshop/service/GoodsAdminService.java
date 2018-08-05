package com.blueteam.wineshop.service;

import com.blueteam.entity.bo.BaseGoodsBO;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.*;
import com.blueteam.entity.vo.AdminPhotoVO;

import java.util.List;
import java.util.Map;

/**
 * Created by  NastyNas on 2017/10/24.
 */
public interface GoodsAdminService {
    /**
     * 根据商品类别id是否存在
     *
     * @param goodsTypeId
     * @return
     */
    Integer countGoodsTypeById(Integer goodsTypeId);

    /**
     * 获取商品类别列表
     *
     * @return
     */
    List<Map> listGoodsType();


    /**
     * 商品新增，相关信息入库
     *
     * @param goodsAddDTO
     */
    void saveGoodsRelated(GoodsAddDTO goodsAddDTO);

    /**
     * 获取唯一商品id
     *
     * @param goodsType
     * @return
     */
    Long getGoodsId(Integer goodsType);


    /**
     * 根据条形码和审核状态 获取商品审核信息列表
     *
     * @param barCode
     * @param verifyState
     * @return
     */
    List<GoodsVerifyInfoPO> getVerifyInfo(String barCode, Integer verifyState);

    /**
     * 根据商品id查询商品基础信息
     *
     * @param goodsId
     * @return
     */
    BaseGoodsBO getGoodsInfo(Long goodsId);


    /**
     * 根据商品id，属性类别（基础/特有）获取商品属性实例列表
     *
     * @param goodsId
     * @param baseListType
     * @return
     */
    List<GoodsAttrInfoPO> listGoodsAttr(Long goodsId, Integer baseListType);

    /**
     * 根据商品id，获取商品图片列表
     *
     * @param goodsId
     * @return
     */
    List<AdminPhotoVO> listGoodsPhoto(Long goodsId);

    /**
     * 根据商品id，商家商品状态 获取商家商品信息列表
     *
     * @param goodsId
     * @param vendorGoodsState
     * @return
     */
    List<GoodsVendorInfoPO> listGoodsVendor(Long goodsId, int vendorGoodsState);

    /**
     * 商品编辑，相关信息更新
     *
     * @param goodsAddDTO
     */
    void updateGoodsRelated(GoodsAddDTO goodsAddDTO);

    /**
     * 商品上架，相关信息更新
     *
     * @param goodsShelvesDTO
     */
    void onTheShelvesRelated(GoodsShelvesDTO goodsShelvesDTO);

    /**
     * 商品下架，相关信息更新
     *
     * @param goodsShelvesDTO
     */
    void takenOffShelvesRelated(GoodsShelvesDTO goodsShelvesDTO);

    /**
     * 分页获取商品列表信息
     *
     * @param goodsListSearchDTO
     * @return
     */
    PageResult<List<Map>> listGoodsInfo(GoodsListSearchDTO goodsListSearchDTO);

    /**
     * 根据条形码查询商品是否已经存在
     *
     * @param barCode
     * @return
     */
    Integer countGoodsByBarCode(String barCode);
}
