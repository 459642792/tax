package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.AttrDTO;
import com.blueteam.entity.dto.AttrValueSearchDTO;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.po.AttrInfoPO;
import com.blueteam.entity.po.AttrValInfoPO;
import com.blueteam.wineshop.mapper.GoodsAttrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by  NastyNas on 2017/10/16.
 */
@Service
public class GoodsAttrServiceImpl implements GoodsAttrService {


    @Autowired
    GoodsAttrMapper goodsAttrMapper;

    @Override
    public List<Map> listAttrType() {
        List<Map> attrTypeList = goodsAttrMapper.listAttrType();
        return attrTypeList;
    }

    @Override
    public List listAttrCode(Integer attrTypeId) {
        List<Map> attrCodeList = goodsAttrMapper.listAttrCode(attrTypeId);
        return attrCodeList;
    }

    @Override
    public PageResult<List<Map>> listAttrValue(AttrValueSearchDTO attrValueSearchDTO) {
        Integer count = goodsAttrMapper.countAttrValue(attrValueSearchDTO);
        List<Map> attrValueList = goodsAttrMapper.listAttrValue(attrValueSearchDTO);
        PageResult pageResult = new PageResult<>();
        pageResult.setCount(count);
        pageResult.setList(attrValueList);
        return pageResult;
    }

    @Override
    public Map getAttrInfo(String attrCode) {
        Map attrInfoMap = goodsAttrMapper.getAttrInfo(attrCode);
        return attrInfoMap;
    }

    @Override
    public Integer countAttrValueName(String attrCode, String attrValueName) {
        Integer attrValueCount = goodsAttrMapper.countAttrValueName(attrCode, attrValueName);
        return attrValueCount;
    }

    @Override
    public Map getExistentMaxValue(String attrCode) {
        Map maxValueIndex = goodsAttrMapper.getExistentMaxValue(attrCode);
        return maxValueIndex;
    }

    @Override
    public Integer saveAttrValue(AttrValInfoPO attrValInfoPO) {
        return goodsAttrMapper.saveAttrValue(attrValInfoPO);
    }

    @Override
    public Integer countGoodsAttrValueCode(String attrValueCode) {
        return goodsAttrMapper.countGoodsAttrValueCode(attrValueCode);

    }

    @Override
    public Map getAttrValueInfo(String attrCode, String attrValueCode, Integer operateTag) {
        return goodsAttrMapper.getAttrValueInfo(attrCode, attrValueCode, operateTag);
    }

    @Override
    public Integer updateAttrValueInfo(AttrValInfoPO attrValInfoPO) {
        return goodsAttrMapper.updateAttrValueInfo(attrValInfoPO);
    }

    @Override
    public Integer removeAttrValueInfo(AttrValInfoPO attrValInfoPO) {
        return goodsAttrMapper.removeAttrValueInfo(attrValInfoPO);
    }

    @Override
    public List<Map> listAttrInfoByParentCode(Integer goodsTypeId, List<String> searchAttrList) {
        return goodsAttrMapper.listAttrInfoByParentCode(goodsTypeId, searchAttrList);
    }


    @Override
    public List<Map> listAttrInfo(Integer goodsTypeId, Integer attrListType) {
        return goodsAttrMapper.listAttrInfo(goodsTypeId, attrListType);
    }

    @Override
    public Integer countAttr(Integer goodsType, Integer attrListType) {
        return goodsAttrMapper.countAttr(goodsType, attrListType);
    }

    @Override
    public List<AttrInfoPO> listAttrInfoByDTO(List<AttrDTO> baseAttrList, Integer goodsType, Integer attrListType) {
        return goodsAttrMapper.listAttrInfoByDTO(baseAttrList, goodsType, attrListType);
    }

    @Override
    public List<AttrValInfoPO> listAttrValueInfoByDTO(List<AttrDTO> selectBaseAttrList) {
        return goodsAttrMapper.listAttrValueInfoByDTO(selectBaseAttrList);
    }
}
