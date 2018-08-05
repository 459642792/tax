package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.GoodsTableImage;

/**
 * 商品图片中间表
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
public interface GoodsTableImageMapper {
    /**
     * 保存
     *
     * @param goodsTableImage
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    Integer saveGoodsTableImage(GoodsTableImage goodsTableImage);

    /**
     * 修改
     *
     * @param goodsTableImage
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    void updateGoodsTableImage(GoodsTableImage goodsTableImage);
}
