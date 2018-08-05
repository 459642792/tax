package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.GoodsInfoPO;
import com.blueteam.entity.po.PromotionCatagoryPO;
import com.blueteam.entity.po.PromotionInfoPO;
import com.blueteam.entity.vo.PromotionGoodsVO;

import java.util.List;
import java.util.Map;

/**
 * Created by huangqijun on 18/1/13.
 */
public interface PromotionMapper {
    /**
     *查询首页促销活动分类列表
     */
    List<PromotionCatagoryPO> listPromotionCatagory();
    /**
     *根据ID查询首页促销活动分类
     */
    PromotionCatagoryPO getPromotionCatagoryById(Integer promotionCatagoryId);
    /**
     *添加首页促销活动分类
     */
    int addPromotionCatagory(PromotionCatagoryPO promotionCatagory);
    /**
     *更新首页促销活动分类
     */
    int updatePromotionCatagory(PromotionCatagoryPO promotionCatagory);
    /**
     *删除首页促销活动分类
     */
    int deletePromotionCatagory(Integer promotionCatagoryId);
    /**
     *查询首页促销活动分类下商品信息
     */
    List<PromotionInfoPO> listPromotionInfoByCatagory(Map map);
    /**
     *添加活动店铺商品信息
     */
    int addPromotionInfo(PromotionInfoPO promotionInfo);
    /**
     *更新活动店铺商品信息
     */
    int updatePromotionInfo(PromotionInfoPO promotionInfo);
    /**
     *更新活动店铺商品信息状态
     */
    int updatePromotionInfoStatus(PromotionInfoPO promotionInfo);
    /**
     *删除活动店铺商品信息
     */
    int deletePromotionInfo(Integer promotionInfoId);
    /*
    * 检查大于0的权重在同一区域是否重复
    * */
    int checkPromotionWeight(PromotionInfoPO promotionInfo);
    /**
     *查询店铺在售商品
     */
    List<GoodsInfoPO> selectGoodsByVendorId(Long vendorId);

    /*
    * 查询促销商品详情
    * */
    List<PromotionGoodsVO> listPromotionGoods(Map map);

    /*
    * 查询商家促销商品上下架状态
    * */
    Integer getVendorGoodsState(Map map);
    /*
    * 查询商品本身上下架状态
    * */
    Integer getGoodsState(Long goodsId);

}
