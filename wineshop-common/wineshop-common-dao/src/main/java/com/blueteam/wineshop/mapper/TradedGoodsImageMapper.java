package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.TradedGoodsImage;

/**
 * 商品图片表
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
public interface TradedGoodsImageMapper {
    /**
     * 保存图片
     *
     * @param tradedGoodsImage
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    Integer saveTradedGoodsImage(TradedGoodsImage tradedGoodsImage);

    /**
     * 修改图片
     *
     * @param tradedGoodsImage
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    void updateTradedGoodsImage(TradedGoodsImage tradedGoodsImage);

    void deleteByPrimaryKey(Integer id);
}
