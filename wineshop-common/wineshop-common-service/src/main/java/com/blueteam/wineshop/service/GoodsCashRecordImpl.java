package com.blueteam.wineshop.service;

import com.blueteam.base.util.Coder;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.CurrencyRecord;
import com.blueteam.entity.po.GoodsCashRecord;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.wineshop.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 兑换商品
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
@Service
public class GoodsCashRecordImpl implements GoodsCashRecordService {

    @Autowired
    CurrencyRecordMapper currencyRecordMapper;

    @Autowired
    GoodsCashRecordMapper goodsCashRecordMapper;

    @Autowired
    TradedGoodsMapper tradedGoodsdao;//商品表

    @Autowired
    private VendorInfoMapper vendorInfoMapper;
    @Autowired
    CarriersInfoMapper carriersInfoDao;

    /**
     * 兑换商品
     *
     * @param tradedGoodsId
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @Override
    public synchronized BaseResult cashGoods(Integer tradedGoodsId, Integer vendorInfoId) {
        //商家剩余酒币
        Integer counts = currencyRecordMapper.countVendorInfoCurrencyRecord(vendorInfoId);
        //商品信息
        Map<String, Object> goods = tradedGoodsdao.getTradedGoods(tradedGoodsId);
        if (null != goods.get("goodsPrice")) {
            if (null != counts) {
                if (new Integer(goods.get("goodsPrice").toString()) <= counts) {
                    GoodsCashRecord goodsCashRecord = new GoodsCashRecord();
                    goodsCashRecord.setTradedGoodsId(new Integer(goods.get("id").toString()));
                    String serialCode = Coder.getSerialCode20();
                    serialCode += Coder.getSerialCode4();
                    goodsCashRecord.setOrderNumber(serialCode);
                    goodsCashRecord.setGoodsName(goods.get("goodsName").toString());
                    goodsCashRecord.setAmount(1);
                    goodsCashRecord.setCashPrice(new Integer(goods.get("goodsPrice").toString()));
                    goodsCashRecord.setStatus(0);
                    goodsCashRecord.setVendorinfoId(vendorInfoId);
                    goodsCashRecord.setCreateBy("system");
                    goodsCashRecord.setCreateDate(new Date());
                    goodsCashRecord.setUpdateBy("system");
                    goodsCashRecord.setUpdateDate(new Date());
                    int i = goodsCashRecordMapper.saveGoodsCashRecord(goodsCashRecord);
                    if (i == 1) {
                        CurrencyRecord currencyRecord = new CurrencyRecord();
                        currencyRecord.setAmount(new Integer(goods.get("goodsPrice").toString()));
                        currencyRecord.setNumberId(goodsCashRecord.getId());
                        currencyRecord.setVendorinfoId(vendorInfoId);
                        currencyRecord.setCreateBy("system");
                        currencyRecord.setCreateDate(new Date());
                        currencyRecord.setUpdateBy("system");
                        currencyRecord.setUpdateDate(new Date());
                        currencyRecord.setStatus(CurrencyRecord.STATUS_REDUCE);
                        currencyRecordMapper.saveCurrencyRecord(currencyRecord);
                    }
                    BaseResult apiResult = ApiResult.success();
                    if (i == 1) {
                        apiResult.setReturnId(String.valueOf(goodsCashRecord.getId()));
                    } else {
                        apiResult.setReturnId(String.valueOf(0));
                    }
                    return apiResult;
                } else {
                    return ApiResult.error("对不起！您的酒币不足！");
                }
            } else {
                return ApiResult.error("对不起！您的酒币不足！");
            }
        } else {
            return ApiResult.error("对不起！该商品目前无法兑换请联系管理员！");
        }
    }

    /**
     * 获取商家剩余酒币
     *
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @Override
    public BaseResult countVendorInfoCurrencyRecord(Integer vendorInfoId) {
        Integer counts = currencyRecordMapper.countVendorInfoCurrencyRecord(vendorInfoId);
        return ApiResult.success(counts);
    }

    /**
     * 获取商家酒币记录
     *
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @Override
    public BaseResult listVendorInfoCurrencyRecord(Integer pageSize, Integer pageIndex, Integer vendorInfoId) {
        int counts = currencyRecordMapper.listCountCurrencyRecord(vendorInfoId);
        List<CurrencyRecord> list = currencyRecordMapper.listCurrencyRecord(pageSize, pageIndex, vendorInfoId);
        return ApiResult.success(list, counts);
    }

    /**
     * 获取兑换记录列表
     *
     * @param pageSize
     * @param pageIndex
     * @param orderNumber
     * @param vendorInfoName
     * @param cashStatus
     * @param beginTime
     * @param endTime
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @Override
    public BaseResult listGoodsCashRecord(Integer pageSize, Integer pageIndex, String orderNumber,
                                          String vendorInfoName, Integer cashStatus, Date beginTime, Date endTime, Integer vendorInfoId, Integer tradedGoodsId) {
        int counts = goodsCashRecordMapper.listCountGoodsCashRecord(pageSize, pageIndex, orderNumber, vendorInfoName, cashStatus, beginTime, endTime, vendorInfoId, tradedGoodsId);
        List<Map<String, Object>> list = goodsCashRecordMapper.listGoodsCashRecord(pageSize, pageIndex, orderNumber, vendorInfoName, cashStatus, beginTime, endTime, vendorInfoId, tradedGoodsId);
        return ApiResult.success(list, counts);
    }

    /**
     * 获取商品详情
     *
     * @param goodsCashRecordId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    @Override
    public BaseResult getGoodsCashRecord(Integer goodsCashRecordId) {
        Map<String, Object> map = goodsCashRecordMapper.getGoodsCashRecord(goodsCashRecordId);
        return ApiResult.success(map);
    }

    /**
     * 兑换商品发货
     * <p>
     * * @param userName
     *
     * @param expressCompany
     * @param expressNumbers
     * @return
     * @author xiaojiang 2017年4月22日
     * @version 1.0
     * @since 1.0 2017年4月22日
     */
    @Override
    public BaseResult expressGoods(Integer goodsCashRecordId, String userName, String expressCompany,
                                   String expressNumbers) {
        GoodsCashRecord goodsCashRecord = goodsCashRecordMapper.getGoodsCashRecords(goodsCashRecordId);
        goodsCashRecord.setExpressNumbers(expressNumbers);
        goodsCashRecord.setExpressCompany(expressCompany);
        goodsCashRecord.setUpdateBy(userName);
        goodsCashRecord.setUpdateDate(new Date());
        goodsCashRecord.setExpressDate(new Date());
        goodsCashRecord.setStatus(GoodsCashRecord.STATUS_SHIPMENTS);
        goodsCashRecordMapper.updateGoodsCashRecord(goodsCashRecord);
        BaseResult result = ApiResult.success();
        result.setReturnId(goodsCashRecordId.toString());
        return result;
    }

    /**
     * 方法的功能描述:TODO 获取 消息接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    @Override
    public MessageRecipient getMessageRecipient(Integer goodsCashRecordId) {
        GoodsCashRecord goodsCashRecord = goodsCashRecordMapper.getGoodsCashRecords(goodsCashRecordId);
        MessageRecipient m = new MessageRecipient();

        if (null != goodsCashRecord) {
            if (null != goodsCashRecord.getVendorinfoId()) {
                m.setVendorId(goodsCashRecord.getVendorinfoId());
                VendorInfo vendorInfo = vendorInfoMapper.getVendorById(goodsCashRecord.getVendorinfoId());
                if (vendorInfo == null)
                    return new MessageRecipient();
                m.setUserId(vendorInfo.getUserId());
            } else {
                m.setVendorId(0);
            }
        }
        return m;
    }

    /**
     * 获取单个对象详情
     *
     * @param goodsCashRecordId
     * @return
     * @author xiaojiang 2017年4月22日
     * @version 1.0
     * @since 1.0 2017年4月22日
     */
    @Override
    public GoodsCashRecord getGoodsCashRecords(Integer goodsCashRecordId) {
        return goodsCashRecordMapper.getGoodsCashRecords(goodsCashRecordId);
    }

    /**
     * 根据兑换记录获取兑换信息 包括使用酒币，剩余酒币，商品名称等
     *
     * @param id
     * @return
     */
//    @Override
//    public GoodsCashRecord selectCashByID(Integer id) {
//        return goodsCashRecordMapper.selectCashByID(id);
//    }
}
