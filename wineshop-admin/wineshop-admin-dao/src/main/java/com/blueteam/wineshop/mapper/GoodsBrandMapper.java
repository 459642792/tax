package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.BrandSearchDTO;
import com.blueteam.entity.po.BrandMainInfoPO;
import com.blueteam.entity.po.BrandSubInfoPO;
import com.blueteam.entity.vo.AdminBrandVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by  NastyNas on 2017/10/19.
 */
public interface GoodsBrandMapper {
    Integer countMainBrandByName(String brandName);

    Integer saveMainBrand(BrandMainInfoPO brandMainInfoPO);

    Map getMainBrandInfo(Integer mainBrandId);

    Integer updateMainBrand(BrandMainInfoPO brandMainInfoPO);

    Integer removeMainBrand(Integer mainBrandId);

    Integer countMainBrandGoods(Integer mainBrandId);

    Integer countSubBrand(Integer mainBrandId);

    Integer countSubBrandByName(@Param("mainBrandId") Integer mainBrandId, @Param("brandName") String brandName);

    Integer saveSubBrand(BrandSubInfoPO brandSubInfoPO);

    Map getSubBrandInfo(@Param("brandId") Integer brandId);

    Integer countSubBrandGoods(Integer brandId);

    Integer updateSubBrand(BrandSubInfoPO brandSubInfoPO);

    Integer removeSubBrand(@Param("brandId") Integer brandId);

    Integer updateMainBrandUpdateTime(@Param("mainBrandId") Integer mainBrandId, @Param("updateStaffId") Integer updateStaffId);

    List listBrandInfo(BrandSearchDTO brandSearchDTO);

    Integer updateGoodsState(BrandSubInfoPO brandSubInfoPO);

    Integer updateVendorGoodsState(BrandSubInfoPO brandSubInfoPO);

    Integer updateVendorBrandState(BrandSubInfoPO brandSubInfoPO);

    Integer updateSubBrandState(BrandSubInfoPO brandSubInfoPO);

    List<Map> listMainBrand(String mainBrandName);

    List<AdminBrandVO> listSubBrand(@Param("mainBrandId") Integer mainBrandId, @Param("stateTag") Integer stateTag);

    List<AdminBrandVO> listSubBrandTypeLimited(@Param("mainBrandId") Integer mainBrandId, @Param("goodsTypeId") Integer goodsTypeId, @Param("stateTag") Integer stateTag);
}
