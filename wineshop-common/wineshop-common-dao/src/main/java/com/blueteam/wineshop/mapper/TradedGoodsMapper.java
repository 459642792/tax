package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.TradedGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 平台 商品表
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
public interface TradedGoodsMapper {

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
    List<Map<String, Object>> listTradedGoods(@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex,
                                              @Param("goodsName") String goodsName, @Param("brandName") String brandName, @Param("goodsStatus") Integer goodsStatus);

    /**
     * 获取商品列表总数
     *
     * @param goodsName
     * @param brandName
     * @param goodsStatus
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    int listCountTradedGoods(@Param("goodsName") String goodsName, @Param("brandName") String brandName, @Param("goodsStatus") Integer goodsStatus);

    /**
     * 获取商品
     *
     * @param id
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    Map<String, Object> getTradedGoods(@Param("id") Integer id);

    /**
     * 保存商品
     *
     * @param tradedGoods
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    Integer saveTradedGoods(TradedGoods tradedGoods);

    /**
     * 编辑商品
     *
     * @param tradedGoods
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    Integer updateTradedGoods(TradedGoods tradedGoods);

    /**
     * 修改状态
     *
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    Integer updateStatusTradedGoods(TradedGoods tradedGoods);
}
