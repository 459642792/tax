package com.blueteam.wineshop.service;

import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.RList;
import com.blueteam.base.lang.RMap;
import com.blueteam.base.lang.RDbTrans;
import com.blueteam.base.util.RandomUtils;
import com.blueteam.entity.bo.BaseGoodsBO;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.*;
import com.blueteam.entity.vo.AdminGoodsListVO;
import com.blueteam.entity.vo.AdminPhotoVO;
import com.blueteam.wineshop.mapper.GoodsAdminMapper;
import com.blueteam.wineshop.mapper.GoodsVerifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.blueteam.base.constant.GoodsAttrConstant.*;

/**
 * Created by  NastyNas on 2017/10/24.
 */
@Service
public class GoodsAdminServiceImpl implements GoodsAdminService {

    @Autowired
    GoodsAdminMapper goodsAdminMapper;

    @Autowired
    GoodsVerifyMapper goodsVerifyMapper;
    @Autowired
    private MessageSubService messageSubService;
    @Override
    public Integer countGoodsTypeById(Integer goodsTypeId) {
        return goodsAdminMapper.countGoodsTypeById(goodsTypeId);
    }

    @Override
    public List<Map> listGoodsType() {
        return goodsAdminMapper.listGoodsType();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveGoodsRelated(GoodsAddDTO goodsAddDTO) {
        if (goodsAddDTO.getGoodsPO() != null) {
            goodsAdminMapper.saveGoodsInfo(goodsAddDTO.getGoodsPO());
        }
        if (RList.isNotEmpty(goodsAddDTO.getGoodsAttrPOList())) {
            goodsAdminMapper.saveGoodsAttrInfo(goodsAddDTO.getGoodsAttrPOList());
        }
        if (RList.isNotEmpty(goodsAddDTO.getGoodsPhotoPOList())) {
            goodsAdminMapper.saveGoodsPhotoInfo(goodsAddDTO.getGoodsPhotoPOList());
        }
        if (goodsAddDTO.getGoodsDetailPO() != null) {
            goodsAdminMapper.saveGoodsDetailInfo(goodsAddDTO.getGoodsDetailPO());
        }
        if (RList.isNotEmpty(goodsAddDTO.getGoodsVendorPOList())) {
            goodsAdminMapper.saveGoodsVendorInfo(goodsAddDTO.getGoodsVendorPOList());
        }
        if (RList.isNotEmpty(goodsAddDTO.getVendorBrandPOList())) {
            goodsAdminMapper.saveVendorBrandInfo(goodsAddDTO.getVendorBrandPOList(), goodsAddDTO.getVendorBrandPOList().size() - 1);
        }
        //发送消息
        messageSubService.sendVendormessage(3,goodsAddDTO.getGoodsVerifyPO().getVerifyBarCode(),null);
        Integer result = goodsVerifyMapper.updateVerifyInfo(goodsAddDTO.getGoodsVerifyPO());
    }

    /**
     * 通过二维码校验商品是否已经添加，未添加，新增商品
     *
     * @param goodsPO
     */
    private synchronized void checkAndSaveGoodsInfo(GoodsInfoPO goodsPO) {
        Integer goodsCount = goodsAdminMapper.countGoodsByBarCode(goodsPO.getBarCode());
        if (goodsCount != 0) {
            throw new BusinessException("商品已经添加，请勿重复");
        }
        goodsAdminMapper.saveGoodsInfo(goodsPO);
    }

    @Override
    public Long getGoodsId(Integer goodsType) {
        //return goodsAdminMapper.getGoodsId(goodsType);
        String timestamp = String.valueOf(new Date().getTime());
        return Long.parseLong(timestamp.substring(timestamp.length()-8)+goodsType);
    }


    @Override
    public List<GoodsVerifyInfoPO> getVerifyInfo(String barCode, Integer verifyState) {
        return goodsAdminMapper.getVerifyInfo(barCode, verifyState);
    }

    @Override
    public BaseGoodsBO getGoodsInfo(Long goodsId) {
        return goodsAdminMapper.getGoodsInfo(goodsId);
    }

    @Override
    public List<GoodsAttrInfoPO> listGoodsAttr(Long goodsId, Integer baseListType) {
        return goodsAdminMapper.listGoodsAttr(goodsId, baseListType);
    }

    @Override
    public List<AdminPhotoVO> listGoodsPhoto(Long goodsId) {
        return goodsAdminMapper.listGoodsPhoto(goodsId);
    }

    @Override
    public List<GoodsVendorInfoPO> listGoodsVendor(Long goodsId, int vendorGoodsState) {
        return goodsAdminMapper.listGoodsVendor(goodsId, vendorGoodsState);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateGoodsRelated(GoodsAddDTO goodsAddDTO) {
        if (goodsAddDTO.getGoodsPO() != null) {
            goodsAdminMapper.updateGoodsInfo(goodsAddDTO.getGoodsPO());
        }
        if (RList.isNotEmpty(goodsAddDTO.getGoodsAttrPOList())) {
            goodsAdminMapper.updateGoodsAttrInfo(goodsAddDTO.getGoodsAttrPOList(), goodsAddDTO.getGoodsPO().getGoodsId());
        }
        if (RList.isNotEmpty(goodsAddDTO.getGoodsPhotoPOList())) {
            deleteAndSaveGoodsPhoto(goodsAddDTO);
        }
        if (goodsAddDTO.getGoodsDetailPO() != null) {
            goodsAdminMapper.updateGoodsDetailInfo(goodsAddDTO.getGoodsDetailPO());
        }
        if (RList.isNotEmpty(goodsAddDTO.getOldVendorBrandPOList())) {
            goodsAdminMapper.updateOldVendorBrandInfo(goodsAddDTO.getOldVendorBrandPOList());
        }
        if (RList.isNotEmpty(goodsAddDTO.getVendorBrandPOList())) {
            goodsAdminMapper.saveVendorBrandInfo(goodsAddDTO.getVendorBrandPOList(), goodsAddDTO.getVendorBrandPOList().size() - 1);
        }
    }

    @Override
    public void onTheShelvesRelated(GoodsShelvesDTO goodsShelvesDTO) {
        goodsAdminMapper.updateGoodsInfo(goodsShelvesDTO.getGoodsPO());
    }

    @Override
    public void takenOffShelvesRelated(GoodsShelvesDTO goodsShelvesDTO) {
        goodsAdminMapper.updateGoodsInfo(goodsShelvesDTO.getGoodsPO());
        if (RList.isNotEmpty(goodsShelvesDTO.getGoodsVendorPOList())) {
            goodsAdminMapper.updateGoodsVendorInfo(goodsShelvesDTO.getGoodsVendorPOList());
        }
        if (RList.isNotEmpty(goodsShelvesDTO.getVendorBrandPOList())) {
            goodsAdminMapper.updateOldVendorBrandInfo(goodsShelvesDTO.getVendorBrandPOList());
        }
    }

    @Override
    public PageResult<List<Map>> listGoodsInfo(GoodsListSearchDTO goodsListSearchDTO) {
        Integer count = goodsAdminMapper.countGoodsInfo(goodsListSearchDTO);
        List<AdminGoodsListVO> goodsListVOS = goodsAdminMapper.listGoodsInfo(goodsListSearchDTO);
        if (RList.isNotEmpty(goodsListVOS)) {
            List<Map> goodsListShowAttr = goodsAdminMapper.getGoodsListShowAttr(goodsListVOS, SHOW_ATTR_LIST);
            wrapShowAttrToVO(goodsListVOS, goodsListShowAttr);
        }
        PageResult pageResult = new PageResult();
        pageResult.setCount(count);
        pageResult.setList(goodsListVOS);
        return pageResult;
    }

    @Override
    public Integer countGoodsByBarCode(String barCode) {
        return goodsAdminMapper.countGoodsByBarCode(barCode);
    }

    private void wrapShowAttrToVO(List<AdminGoodsListVO> goodsListVOS, List<Map> goodsListShowAttr) {
        for (AdminGoodsListVO goodsListVO : goodsListVOS) {
            goodsListVO.setSuggestPrice(RDbTrans.asShowPrice(goodsListVO.getSuggestPrice()));
            for (Map showAttrMap : goodsListShowAttr) {
                if (goodsListVO.getGoodsId().equals(RMap.getLong(showAttrMap, "goodsId"))) {
                    if (ALCOHOL_ATTR_CODE.equals(RMap.getStr(showAttrMap, "parentAttrCode"))) {
                        goodsListVO.setAlcohol(RMap.getStr(showAttrMap, "attrValueShowName"));
                    }
                    if (PACKAGE_ATTR_CODE.equals(RMap.getStr(showAttrMap, "parentAttrCode"))) {
                        goodsListVO.setPackageType(RMap.getStr(showAttrMap, "attrValueShowName"));
                    }
                    if (CONTENT_ATTR_CODE.equals(RMap.getStr(showAttrMap, "parentAttrCode"))) {
                        goodsListVO.setContent(RMap.getStr(showAttrMap, "attrValueShowName"));
                    }
                }
            }
        }
    }

    /**
     * 删除原有照片并插入最新照片
     *
     * @param goodsAddDTO
     */
    private void deleteAndSaveGoodsPhoto(GoodsAddDTO goodsAddDTO) {
        goodsAdminMapper.removeGoodsPhotoInfo(goodsAddDTO.getGoodsPO().getGoodsId());
        goodsAdminMapper.saveGoodsPhotoInfo(goodsAddDTO.getGoodsPhotoPOList());
    }


}
