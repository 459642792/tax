package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.AttrDTO;
import com.blueteam.entity.dto.AttrValueSearchDTO;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.po.AttrInfoPO;
import com.blueteam.entity.po.AttrValInfoPO;

import java.util.List;
import java.util.Map;

/**
 * Created by  NastyNas on 2017/10/16.
 */
public interface GoodsAttrService {

    /**
     * 获取属性商品类别列表
     *
     * @return
     */
    List listAttrType();

    /**
     * 根据商品类别获取属性信息
     *
     * @param attrTypeId
     * @return
     */
    List listAttrCode(Integer attrTypeId);

    /**
     * 根据属性编码分页获取属性值信息
     *
     * @param attrCode
     * @return
     */
    PageResult<List<Map>> listAttrValue(AttrValueSearchDTO attrCode);

    /**
     * 根据属性编码获取属性信息
     *
     * @param attrCode
     * @return
     */
    Map getAttrInfo(String attrCode);

    /**
     * 根据属性编码和属性值名称 获取当前该属性值的数量
     *
     * @param attrCode
     * @param attrValueName
     * @return
     */
    Integer countAttrValueName(String attrCode, String attrValueName);

    /**
     * 获取当前属性的最大序号信息
     *
     * @param attrCode
     * @return
     */
    Map getExistentMaxValue(String attrCode);

    /**
     * 新增属性值
     *
     * @param attrValInfoPO
     * @return
     */
    Integer saveAttrValue(AttrValInfoPO attrValInfoPO);

    /**
     * 查询属性值绑定的商品数量
     *
     * @param attrValueCode
     * @return
     */
    Integer countGoodsAttrValueCode(String attrValueCode);

    /**
     * 获取属性值详情
     *
     * @param attrCode
     * @param attrValueCode
     * @param operateTag
     * @return
     */
    Map getAttrValueInfo(String attrCode, String attrValueCode, Integer operateTag);

    /**
     * 更新属性值
     *
     * @param attrValInfoPO
     * @return
     */
    Integer updateAttrValueInfo(AttrValInfoPO attrValInfoPO);

    /**
     * 删除属性值
     *
     * @param attrValInfoPO
     * @return
     */
    Integer removeAttrValueInfo(AttrValInfoPO attrValInfoPO);

    /**
     * 通过属性类别（基础/特有）和 父类属性列表 获取详细属性信息
     *
     * @param goodsTypeId
     * @param searchAttrList
     * @return
     */
    List<Map> listAttrInfoByParentCode(Integer goodsTypeId, List<String> searchAttrList);

    /**
     * 根据商品类别和属性类型获取属性信息列表
     *
     * @param goodsTypeId
     * @param attrListType
     * @return
     */
    List<Map> listAttrInfo(Integer goodsTypeId, Integer attrListType);

    /**
     * 根据商品类别和属性类别（基础/特有）查询属性数量
     *
     * @param goodsType
     * @param attrListType
     * @return
     */
    Integer countAttr(Integer goodsType, Integer attrListType);

    /**
     * 根据属性dto信息查询详细属性信息
     *
     * @param baseAttrList
     * @param goodsType
     * @param attrListType
     * @return
     */
    List<AttrInfoPO> listAttrInfoByDTO(List<AttrDTO> baseAttrList, Integer goodsType, Integer attrListType);

    /**
     * 根据属性dto列表查询详细属性值信息
     *
     * @param selectBaseAttrList
     * @return
     */
    List<AttrValInfoPO> listAttrValueInfoByDTO(List<AttrDTO> selectBaseAttrList);


}
