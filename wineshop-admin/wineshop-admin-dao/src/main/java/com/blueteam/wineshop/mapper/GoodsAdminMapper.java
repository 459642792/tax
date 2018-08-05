package com.blueteam.wineshop.mapper;

import com.blueteam.entity.bo.BaseGoodsBO;
import com.blueteam.entity.dto.GoodsListSearchDTO;
import com.blueteam.entity.po.*;
import com.blueteam.entity.vo.AdminGoodsListVO;
import com.blueteam.entity.vo.AdminPhotoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by  NastyNas on 2017/10/24.
 */
public interface GoodsAdminMapper {
    Integer countGoodsTypeById(Integer goodsTypeId);

    List<Map> listGoodsType();

    void saveGoodsInfo(GoodsInfoPO goodsPO);

    void saveGoodsAttrInfo(List<GoodsAttrInfoPO> goodsAttrPOList);

    void saveGoodsDetailInfo(GoodsDetailInfoPO goodsDetailPO);

    void saveGoodsPhotoInfo(List<GoodsPhotoInfoPO> goodsPhotoPOList);

    List<GoodsVerifyInfoPO> getVerifyInfo(@Param("barCode") String barCode, @Param("verifyState") Integer verifyState);

    void saveGoodsVendorInfo(List<GoodsVendorInfoPO> goodsVendorPOList);

    void saveVendorBrandInfo(@Param("vendorBrandPOList") List<VendorBrandInfoPO> vendorBrandPOList, @Param("maxIndex") Integer maxIndex);

    Long getGoodsId(Integer goodsTypeId);

    Integer countGoodsByBarCode(String barCode);

    BaseGoodsBO getGoodsInfo(Long goodsId);

    List<AdminPhotoVO> listGoodsPhoto(Long goodsId);

    List<GoodsAttrInfoPO> listGoodsAttr(@Param("goodsId") Long goodsId, @Param("attrListType") Integer attrListType);

    List<GoodsVendorInfoPO> listGoodsVendor(@Param("goodsId") Long goodsId, @Param("vendorGoodsState") int vendorGoodsState);

    void updateGoodsInfo(GoodsInfoPO goodsPO);

    void updateGoodsAttrInfo(@Param("goodsAttrPOList") List<GoodsAttrInfoPO> goodsAttrPOList, @Param("goodsId") Long goodsId);

    void removeGoodsPhotoInfo(Long goodsId);

    void updateGoodsDetailInfo(GoodsDetailInfoPO goodsDetailPO);

    void updateOldVendorBrandInfo(List<VendorBrandInfoPO> oldVendorBrandPOList);

    void updateGoodsVendorInfo(List<GoodsVendorInfoPO> goodsVendorPOList);

    Integer countGoodsInfo(GoodsListSearchDTO goodsListSearchDTO);

    List<AdminGoodsListVO> listGoodsInfo(GoodsListSearchDTO goodsListSearchDTO);

    List<Map> getGoodsListShowAttr(@Param("goodsListVOS") List<AdminGoodsListVO> goodsListVOS, @Param("showAttrList") List<String> showAttrList);
}
