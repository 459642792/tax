package com.blueteam.wineshop.service;


import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.TradedGoods;

/**
 * 平台商品
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
public interface TradedGoodsService {
    /**
     * 获取商品列表
     *
     * @param pageSize
     * @param pageIndex
     * @param goodsName
     * @param brandName
     * @param goodsStatus
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    BaseResult listTradedGoods(Integer pageSize, Integer pageIndex, String goodsName, String brandName, Integer goodsStatus);

    /**
     * 保存/修改 商品
     *
     * @param imageUrl
     * @param tradedGoods
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    BaseResult saveEditTradedGoods(Integer tradedGoodsId, String imageUrl, TradedGoods tradedGoods);

    /**
     * 修改状态
     *
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    BaseResult updateStatusTradedGoods(TradedGoods tradedGoods);

    /**
     * 获取详情
     *
     * @param tradedGoodsId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    BaseResult getTradedGoods(Integer tradedGoodsId);
}
