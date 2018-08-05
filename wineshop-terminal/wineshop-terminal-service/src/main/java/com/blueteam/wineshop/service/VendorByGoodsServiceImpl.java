package com.blueteam.wineshop.service;

import com.blueteam.base.lang.RMap;
import com.blueteam.base.lang.RStr;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.GoodsVendorDTO;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.po.GoodsInfoPO;
import com.blueteam.entity.po.GoodsVendorInfoPO;
import com.blueteam.entity.po.GoodsVerifyInfoPO;
import com.blueteam.entity.po.VendorBrandInfoPO;
import com.blueteam.entity.vo.GoodsDetailVO;
import com.blueteam.entity.vo.GoodsVO;
import com.blueteam.wineshop.mapper.VendorByGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家商品相关
 *
 * @author xiaojiang
 * @create 2017-10-28  15:13
 */
@Service
public class VendorByGoodsServiceImpl implements VendorByGoodsService {
    @Autowired
    VendorByGoodsMapper vendorByGoodsMapper;

    /**
     * 方法的功能描述:TODO 根据相关参数获取商家商品列表
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName listValidTypeByGoods
     * @param: vendorId
     * @param: brandGoodsType 类型id
     * @param: order
     * @param: orderType
     * @param: source 来源
     * @param: type 状态
     * @param: pageIndex
     * @param: pageSize
     * @author xiaojiang 2017/10/30 18:53
     * @modifier
     * @since 1.4.0
     */
    @Override
    public BaseResult listGoodsByType(Integer vendorId, Integer brandGoodsType, String order, String orderType, Integer source, Integer type, Integer pageIndex, Integer pageSize) {
        PageResult<List<GoodsVO>> result = vendorByGoodsMapper.listGoodsByType(vendorId, brandGoodsType, order, orderType, source, type, pageIndex, pageSize);
        return null != result ? ApiResult.success(result.getList(), result.getCount()) : ApiResult.success("");
    }

    /**
     * 方法的功能描述:TODO 新增审核数据
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/30 19:26
     * @modifier
     * @since 1.4.0
     */
    @Override
    public BaseResult insertGoodsVerifyInfo(GoodsVerifyInfoPO goodsVerifyInf) {
        Map<String, Object> map = vendorByGoodsMapper.getGoodsByBrandId(goodsVerifyInf.getVerifyBarCode(), goodsVerifyInf.getVendorId());
        if (null != map && !map.isEmpty()) {
            if (null != map.get("vendorId")) {
                return ApiResult.error("该商品" + goodsVerifyInf.getVerifyBarCode() + "条码已存在，请返回查询后直接查看。");
            } else {
                return ApiResult.error("该商品" + goodsVerifyInf.getVerifyBarCode() + "条码已存在，请返回查询后直接添加！");
            }
        } else {
            goodsVerifyInf.setCreateTime(new Date());
            goodsVerifyInf.setUpdateTime(new Date());
            goodsVerifyInf.setVerifyState(GoodsVerifyInfoPO.VERIFY_STATE_TODO);
            int counts = vendorByGoodsMapper.updateGoodsVerifyByBarCode(goodsVerifyInf);
            if (counts == 1) {
                return ApiResult.success("");
            } else {
                try {
                    int count = vendorByGoodsMapper.insertGoodsVerifyInfo(goodsVerifyInf);
                    return count == 1 ? ApiResult.success("") : ApiResult.error("该商品" + goodsVerifyInf.getVerifyBarCode() + "条码已发起申请，请勿重复提交。");
                } catch (Exception e) {
                    return ApiResult.error("服务器错误！");
                }
            }
        }
    }

    /**
     * 方法的功能描述:TODO 根据商家id查询所有的审核商品列表
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/31 16:07
     * @modifier
     * @since 1.4.0
     */
    @Override
    public BaseResult listGoodsByVerify(Integer vendorId) {
        List<GoodsVO> result = vendorByGoodsMapper.listGoodsByVerify(vendorId);
        return ApiResult.success(result);
    }

    /**
     * 方法的功能描述:TODO 根据商家id商品id获取商品详情
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/10/24 15:57
     * @modifier
     * @since 1.4.0
     */
    @Override
    public BaseResult getVendorByGoodsDetails(Integer vendorId, Long goodsId, Integer source) {
        GoodsDetailVO result = vendorByGoodsMapper.getVendorByGoodsDetails(vendorId, goodsId, source);
        try {
            result.setMajor(vendorByGoodsMapper.getIsMajor(result.getBrandId(),vendorId));
        }catch (Exception e){
            result.setMajor(0);
        }

        return null == result ? ApiResult.error("商品以下架") : ApiResult.success(result);
    }

    /**
     * 方法的功能描述:TODO
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName listMatchingGoodsByNameAndBarcode
     * @param: barcode 条形码
     * @param: goodsName 商品名称
     * @param: source
     * @param: pageIndex
     * @param: pageSize
     * @author xiaojiang 2017/11/1 17:00
     * @modifier
     * @since 1.4.0
     */
    @Override
    public BaseResult listMatchingGoodsByNameAndBarcode(String matchingValue, Integer source, Integer pageIndex, Integer pageSize) {
        PageResult<List<GoodsVO>> result = vendorByGoodsMapper.listMatchingGoodsByNameAndBarcode(matchingValue, source, pageIndex, pageSize);
        if (null != result) {
            return ApiResult.success(result.getList(), result.getCount());
        } else {
            return ApiResult.error("没有匹配到该商品");
        }
    }

    /**
     * 方法的功能描述:TODO 商家新增商品 商家商品 修改商品  下架商品
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2017/11/2 11:36
     * @modifier
     * @since 1.4.0
     */
    @Override
    public BaseResult saveChangeVendorByGoods(GoodsVendorDTO goodsVendorDTO) {
        //获取商品状态和品牌
        Map<String, Object> map = vendorByGoodsMapper.getGoodsByStatusAndBrandId(goodsVendorDTO.getGoodsId());
        // 1保存 编辑 2上下 删除
        Integer genre = goodsVendorDTO.getGenre();
        if (RMap.isEmpty(map)) {
            return ApiResult.error("没有该商品！");
        }
        //商品状态
        Integer status = (Integer) map.get("goodsStatus");
        //商品品牌id
        Integer brandId = null != map ? (Integer) map.get("brandId") : null;
        //商家id
        Integer vendorId = goodsVendorDTO.getVendorId();
        GoodsVendorInfoPO goodsVendorInfoPO = new GoodsVendorInfoPO();
        goodsVendorInfoPO.setGoodsId(goodsVendorDTO.getGoodsId());
        goodsVendorInfoPO.setVendorId(vendorId);
        goodsVendorInfoPO.setSalePrice((int) (!RStr.isNotEmpty(goodsVendorDTO.getPrice()) ? 0 : new Double(goodsVendorDTO.getPrice()) * 100));
        goodsVendorInfoPO.setCreateStaffId(vendorId);
        goodsVendorInfoPO.setUpdateStaffId(vendorId);
        goodsVendorInfoPO.setUpdateTime(new Date());
        goodsVendorInfoPO.setCreateTime(new Date());
        goodsVendorInfoPO.setVendorGoodsState(goodsVendorDTO.getGoodsVendroStatus());
        if (goodsVendorDTO.getGenre().equals(1)) {
            return saveVendorByGoods(goodsVendorInfoPO, brandId, status,goodsVendorDTO.getMajor());
        } else {
            return changeVendorByGoods(goodsVendorInfoPO, brandId, status,goodsVendorDTO.getMajor());
        }
    }

    /**
     * 方法的功能描述:TODO 保存商家商品
     *
     * @return com.blueteam.entity.dto.BaseResult
     * @methodName saveVendorByGoods
     * @param: goodsVendorInfoPO 商家商品
     * @param: brandId 品牌id
     * @param: status 商品状态
     * @author xiaojiang 2017/11/2 16:55
     * @modifier
     * @since 1.4.0
     */
    public BaseResult saveVendorByGoods(GoodsVendorInfoPO goodsVendorInfoPO, Integer brandId, Integer status,Integer isMajor) {
        //商家传过来的上下架状态
        Integer vendorGoodsStatus = goodsVendorInfoPO.getVendorGoodsState();
        Integer vendorId = goodsVendorInfoPO.getVendorId();
        if (status.equals(GoodsInfoPO.GOODS_STATE_DELISTING)) {
            goodsVendorInfoPO.setVendorGoodsState(GoodsVendorInfoPO.VENDOR_GOODS_STATE_DOWN);
        }
        Map<String, Object> map = vendorByGoodsMapper.getVendorGoodsByStatus(goodsVendorInfoPO.getGoodsId(), vendorId);
        Integer oldOendorGoodsStatus = RMap.isEmpty(map) ? null : new Integer(map.get("vendorGoodsStatus").toString());
        //商家商品old ID
        int saveType = vendorByGoodsMapper.saveGoodsVendorInfo(goodsVendorInfoPO);
        VendorBrandInfoPO vendorBrandInfoPO = new VendorBrandInfoPO();
        vendorBrandInfoPO.setBrandId(brandId);
        vendorBrandInfoPO.setVendorId(vendorId);
        vendorBrandInfoPO.setAuthorityTag(VendorBrandInfoPO.AUTHORITY_TAG_VALID);
        vendorBrandInfoPO.setBrandGoodsAmount(1);
        vendorBrandInfoPO.setCreateStaffId(vendorId);
        vendorBrandInfoPO.setCreateTime(new Date());
        vendorBrandInfoPO.setUpdateStaffId(vendorId);
        vendorBrandInfoPO.setUpdateTime(new Date());
        vendorBrandInfoPO.setIsMajor(isMajor);
        //vendorByGoodsMapper.saveVndorBrandInfo(vendorBrandInfoPO);
        if (saveType == 1) {//新增
            //新增商家上架商品
            if (goodsVendorInfoPO.getVendorGoodsState().equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP)) {
                //vendorBrandInfoPO.setGoodsVendorStatus(1);
                vendorByGoodsMapper.saveVndorBrandInfo(vendorBrandInfoPO);
            }
            if (status.equals(GoodsInfoPO.GOODS_STATE_DELISTING) && vendorGoodsStatus.equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP)) {
                return ApiResult.success("商品添加成功,该商品条码平台已下架，请联系平台方。");
            } else {
                return ApiResult.success("商品添加成功");
            }
        } else {//修改
            //old 上架 new 下架  数量减1
            if (oldOendorGoodsStatus.equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP) && goodsVendorInfoPO.getVendorGoodsState().equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_DOWN)) {
                //vendorBrandInfoPO.setGoodsVendorStatus(0);
                //vendorByGoodsMapper.saveVndorBrandInfo(vendorBrandInfoPO);
                vendorBrandInfoPO.setBrandGoodsAmount(-1);
                //old 下架或删除 new 上架  数量加1
            } else if (!oldOendorGoodsStatus.equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP) && goodsVendorInfoPO.getVendorGoodsState().equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP)) {
                //vendorBrandInfoPO.setGoodsVendorStatus(1);
                //vendorByGoodsMapper.saveVndorBrandInfo(vendorBrandInfoPO);
                vendorBrandInfoPO.setBrandGoodsAmount(1);
            }else {
                vendorBrandInfoPO.setBrandGoodsAmount(0);
            }
            vendorByGoodsMapper.saveVndorBrandInfo(vendorBrandInfoPO);
            if (status.equals(GoodsInfoPO.GOODS_STATE_DELISTING) && vendorGoodsStatus.equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP)) {
                return ApiResult.success("该商品条码平台已下架，请联系平台方。");
            } else {
                return ApiResult.success("商品修改成功");
            }
        }
    }

    /**
     * 方法的功能描述:TODO 修改商品状态
     *
     * @return
     * @param: null
     * @author xiaojiang 2017/11/3 9:35
     * @modifier
     * @since 1.4.0
     */
    public BaseResult changeVendorByGoods(GoodsVendorInfoPO goodsVendorInfoPO, Integer brandId, Integer status,Integer isMajor) {
        Integer vendorGoodsStatus = goodsVendorInfoPO.getVendorGoodsState();
        Integer vendorId = goodsVendorInfoPO.getVendorId();
        if (status.equals(GoodsInfoPO.GOODS_STATE_DELISTING) && vendorGoodsStatus.equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP)) {
            goodsVendorInfoPO.setVendorGoodsState(GoodsVendorInfoPO.VENDOR_GOODS_STATE_DOWN);
        }
        //商家传过来的上下架状态
        Map<String, Object> map = vendorByGoodsMapper.getVendorGoodsByStatus(goodsVendorInfoPO.getGoodsId(), vendorId);
        Integer oldOendorGoodsStatus = new Integer(map.get("vendorGoodsStatus").toString());
        //商家商品old ID
        vendorByGoodsMapper.updateVendorGoodsByStatus(goodsVendorInfoPO.getGoodsId(), goodsVendorInfoPO.getVendorId(), goodsVendorInfoPO.getVendorGoodsState());
        VendorBrandInfoPO vendorBrandInfoPO = new VendorBrandInfoPO();
        vendorBrandInfoPO.setBrandId(brandId);
        vendorBrandInfoPO.setVendorId(vendorId);
        vendorBrandInfoPO.setAuthorityTag(VendorBrandInfoPO.AUTHORITY_TAG_VALID);
        vendorBrandInfoPO.setBrandGoodsAmount(1);
        vendorBrandInfoPO.setCreateStaffId(vendorId);
        vendorBrandInfoPO.setCreateTime(new Date());
        vendorBrandInfoPO.setUpdateStaffId(vendorId);
        vendorBrandInfoPO.setUpdateTime(new Date());
        vendorBrandInfoPO.setIsMajor(isMajor);
        if (oldOendorGoodsStatus.equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP) && !goodsVendorInfoPO.getVendorGoodsState().equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP)) {
//            vendorBrandInfoPO.setGoodsVendorStatus(0);
//            vendorByGoodsMapper.saveVndorBrandInfo(vendorBrandInfoPO);
            vendorBrandInfoPO.setBrandGoodsAmount(-1);
            //old 下架或删除 new 上架  数量加1
        } else if (!oldOendorGoodsStatus.equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP) && goodsVendorInfoPO.getVendorGoodsState().equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP)) {
//            vendorBrandInfoPO.setGoodsVendorStatus(1);
//            vendorByGoodsMapper.saveVndorBrandInfo(vendorBrandInfoPO);
            vendorBrandInfoPO.setBrandGoodsAmount(1);
        }else {
            vendorBrandInfoPO.setBrandGoodsAmount(0);
        }
        vendorByGoodsMapper.saveVndorBrandInfo(vendorBrandInfoPO);
        if (status.equals(GoodsInfoPO.GOODS_STATE_DELISTING) && vendorGoodsStatus.equals(GoodsVendorInfoPO.VENDOR_GOODS_STATE_UP)) {
            return ApiResult.success("该商品条码平台已下架，请联系平台方。");
        } else {
            switch (vendorGoodsStatus) {
                case 0:
                    return ApiResult.success("商品下架成功");
                case 1:
                    return ApiResult.success("商品上架成功");
                default:
                    return ApiResult.success("商品删除成功");
            }
        }
    }


}
