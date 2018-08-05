package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.GoodsTableImage;
import com.blueteam.entity.po.TradedGoods;
import com.blueteam.entity.po.TradedGoodsImage;
import com.blueteam.wineshop.mapper.GoodsTableImageMapper;
import com.blueteam.wineshop.mapper.TradedGoodsImageMapper;
import com.blueteam.wineshop.mapper.TradedGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 平台商品
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */

@Service
public class TradedGoodsImpl implements TradedGoodsService {

    @Autowired
    TradedGoodsMapper tradedGoodsdao;//商品表

    @Autowired
    GoodsTableImageMapper goodsTableImageMapper;//图片中间表

    @Autowired
    TradedGoodsImageMapper tradedGoodsImageMapper;//商品图片表


    /**
     * 商品列表
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
    @Override
    public BaseResult listTradedGoods(Integer pageSize, Integer pageIndex, String goodsName,
                                      String brandName, Integer goodsStatus) {
        goodsName = null == goodsName || goodsName.isEmpty() ? null : goodsName;
        brandName = null == brandName || brandName.isEmpty() ? null : brandName;
        List<Map<String, Object>> list = tradedGoodsdao.listTradedGoods(pageSize, pageIndex, goodsName, brandName, goodsStatus);
        int counts = tradedGoodsdao.listCountTradedGoods(goodsName, brandName, goodsStatus);
        return ApiResult.success(list, counts);
    }

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
    @Override
    public BaseResult saveEditTradedGoods(Integer tradedGoodsId, String imageUrl, TradedGoods tradedGoods) {
        tradedGoods.setGoodsGenre(1);
        if (null == tradedGoodsId || tradedGoodsId.equals("")) {
            tradedGoods.setGoodsStatus(TradedGoods.GOODSSTATUS_PUTAWAY);
            int i = tradedGoodsdao.saveTradedGoods(tradedGoods);
            if (i == 1) {
                //保存图片
                TradedGoodsImage tradedGoodsImage = new TradedGoodsImage();
                tradedGoodsImage.setCreateBy(tradedGoods.getCreateBy());
                tradedGoodsImage.setCreateDate(new Date());
                tradedGoodsImage.setUpdateBy(tradedGoods.getUpdateBy());
                tradedGoodsImage.setUpdateDate(new Date());
                tradedGoodsImage.setType(TradedGoodsImage.TYPE_HOME);
                tradedGoodsImage.setImageUrl(imageUrl);
                int j = tradedGoodsImageMapper.saveTradedGoodsImage(tradedGoodsImage);
                if (j == 1) {
                    GoodsTableImage goodsTableImage = new GoodsTableImage();
                    goodsTableImage.setCreateBy(tradedGoods.getCreateBy());
                    goodsTableImage.setCreateDate(new Date());
                    goodsTableImage.setUpdateBy(tradedGoods.getUpdateBy());
                    goodsTableImage.setUpdateDate(new Date());
                    goodsTableImage.setTradedGoodsId(tradedGoods.getId());
                    goodsTableImage.setTradedGoodsImageId(tradedGoodsImage.getId());
                    goodsTableImageMapper.saveGoodsTableImage(goodsTableImage);
                    return ApiResult.success();
                } else {
                    return ApiResult.error("保存失败");
                }
            } else {
                return ApiResult.error("保存失败");
            }
        } else {
            Map<String, Object> map = tradedGoodsdao.getTradedGoods(tradedGoodsId);
            tradedGoods.setId(tradedGoodsId);
            tradedGoods.setGoodsDetails(map.get("goodsDetails") == null || map.get("goodsDetails") == "" ? "" : map.get("goodsDetails").toString());
            tradedGoodsdao.updateTradedGoods(tradedGoods);
            TradedGoodsImage tradedGoodsImage = new TradedGoodsImage();
            tradedGoodsImage.setUpdateBy(tradedGoods.getUpdateBy());
            tradedGoodsImage.setUpdateDate(new Date());
            tradedGoodsImage.setImageUrl(imageUrl);
            tradedGoodsImage.setId(map.get("tradedGoodsImageId") == null || map.get("tradedGoodsImageId") == "" ? 0 : new Integer(map.get("tradedGoodsImageId").toString()));
            tradedGoodsImageMapper.updateTradedGoodsImage(tradedGoodsImage);
            return ApiResult.success();
        }
    }

    /**
     * 修改状态
     *
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    @Override
    public BaseResult updateStatusTradedGoods(TradedGoods tradedGoods) {
        tradedGoodsdao.updateStatusTradedGoods(tradedGoods);
        return ApiResult.success();
    }


    @Override
    public BaseResult getTradedGoods(Integer tradedGoodsId) {
        Map<String, Object> map = tradedGoodsdao.getTradedGoods(tradedGoodsId);
        return ApiResult.success(map);
    }
}
