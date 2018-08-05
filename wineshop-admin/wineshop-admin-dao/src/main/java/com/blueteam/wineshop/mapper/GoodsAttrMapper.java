package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.AttrDTO;
import com.blueteam.entity.dto.AttrValueSearchDTO;
import com.blueteam.entity.po.AttrInfoPO;
import com.blueteam.entity.po.AttrValInfoPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by  NastyNas on 2017/10/16.
 */
public interface GoodsAttrMapper {
    List<Map> listAttrType();


    List<Map> listAttrCode(Integer attrTypeId);

    List<Map> listAttrValue(AttrValueSearchDTO attrCode);

    Integer countAttrValue(AttrValueSearchDTO attrCode);

    Map getAttrInfo(String attrCode);

    Integer countAttrValueName(@Param("attrCode") String attrCode, @Param("attrValueName") String attrValueName);

    Map getExistentMaxValue(String attrCode);

    Integer saveAttrValue(AttrValInfoPO attrValInfoPO);

    Integer countGoodsAttrValueCode(String attrValueCode);

    Map getAttrValueInfo(@Param("attrCode") String attrCode, @Param("attrValueCode") String attrValueCode, @Param("operateTag") Integer operateTag);

    Integer updateAttrValueInfo(AttrValInfoPO attrValInfoPO);

    Integer removeAttrValueInfo(AttrValInfoPO attrValInfoPO);

    List<Map> listAttrInfoByParentCode(@Param("goodsTypeId") Integer goodsTypeId, @Param("searchAttrList") List<String> searchAttrList);

    List<Map> listAttrInfo(@Param("goodsTypeId") Integer goodsTypeId, @Param("attrListType") Integer attrListType);

    Integer countAttr(@Param("goodsTypeId") Integer goodsTypeId, @Param("attrListType") Integer attrListType);

    List<AttrInfoPO> listAttrInfoByDTO(@Param("attrList") List attrList, @Param("goodsTypeId") Integer goodsTypeId, @Param("attrListType") Integer attrListType);

    List<AttrValInfoPO> listAttrValueInfoByDTO(@Param("attrList") List<AttrDTO> selectBaseAttrList);
}
