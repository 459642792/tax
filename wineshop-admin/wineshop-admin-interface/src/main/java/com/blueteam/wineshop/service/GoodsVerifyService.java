package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.GoodsVerifySearchDTO;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.VendorVerifySearchDTO;
import com.blueteam.entity.po.GoodsVerifyInfoPO;

import java.util.List;
import java.util.Map;

/**
 * 商品审核服务
 * Created by  NastyNas on 2017/11/8.
 */
public interface GoodsVerifyService {
    /**
     * 待审核列表也分页展示
     *
     * @param goodsVerifySearchDTO
     * @return
     */
    PageResult<List<Map>> listVerifyInfo(GoodsVerifySearchDTO goodsVerifySearchDTO);

    /**
     * 编辑审核信息，审核条形码对应商家审核信息展示
     *
     * @param vendorVerifySearchDTO
     * @return
     */
    PageResult<List<Map>> listVendorVerifyInfo(VendorVerifySearchDTO vendorVerifySearchDTO);

    /**
     * 更新审核信息
     *
     * @param verifyPO
     */
    Integer updateVerifyInfo(GoodsVerifyInfoPO verifyPO);
}
