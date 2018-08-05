package com.blueteam.entity.dto;

import com.blueteam.entity.po.GoodsInfoPO;
import com.blueteam.entity.po.GoodsVendorInfoPO;
import com.blueteam.entity.po.VendorBrandInfoPO;

import java.util.List;

/**
 * 管理员上下架商品dto
 * Created by  NastyNas on 2017/11/6.
 */
public class GoodsShelvesDTO {

    GoodsInfoPO goodsPO;
    List<VendorBrandInfoPO> vendorBrandPOList;
    List<GoodsVendorInfoPO> goodsVendorPOList;

    public GoodsInfoPO getGoodsPO() {
        return goodsPO;
    }

    public void setGoodsPO(GoodsInfoPO goodsPO) {
        this.goodsPO = goodsPO;
    }

    public List<VendorBrandInfoPO> getVendorBrandPOList() {
        return vendorBrandPOList;
    }

    public void setVendorBrandPOList(List<VendorBrandInfoPO> vendorBrandPOList) {
        this.vendorBrandPOList = vendorBrandPOList;
    }

    public List<GoodsVendorInfoPO> getGoodsVendorPOList() {
        return goodsVendorPOList;
    }

    public void setGoodsVendorPOList(List<GoodsVendorInfoPO> goodsVendorPOList) {
        this.goodsVendorPOList = goodsVendorPOList;
    }
}
