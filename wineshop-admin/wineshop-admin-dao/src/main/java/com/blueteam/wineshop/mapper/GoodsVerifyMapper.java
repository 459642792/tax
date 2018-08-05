package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.GoodsVerifySearchDTO;
import com.blueteam.entity.dto.VendorVerifySearchDTO;
import com.blueteam.entity.po.GoodsVerifyInfoPO;
import com.blueteam.entity.vo.AdminVendorVerifyVO;
import com.blueteam.entity.vo.AdminVerifyVO;

import java.util.List;

/**
 * Created by  NastyNas on 2017/11/8.
 */
public interface GoodsVerifyMapper {


    Integer countVerifyInfo(GoodsVerifySearchDTO goodsVerifySearchDTO);

    List<AdminVerifyVO> listVerifyInfo(GoodsVerifySearchDTO goodsVerifySearchDTO);


    Integer countVendorVerifyInfo(VendorVerifySearchDTO vendorVerifySearchDTO);

    List<AdminVendorVerifyVO> listVendorInfo(VendorVerifySearchDTO vendorVerifySearchDTO);

    Integer updateVerifyInfo(GoodsVerifyInfoPO verifyPO);

    List<GoodsVerifyInfoPO> listVerifyInfoByBarCode(String barCode);
}
