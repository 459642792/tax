package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.CarriersInfoMapper;
import com.blueteam.wineshop.mapper.SettlementRecordsMapper;
import com.blueteam.wineshop.mapper.VendorInfoMapper;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.SettlementRecords;
import com.blueteam.entity.po.VendorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SettlementRecordsImpl implements SettlementRecordsService {

    @Autowired
    SettlementRecordsMapper settlementRecordsMapper;
    @Autowired
    CarriersInfoMapper dao;
    @Autowired
    private VendorInfoMapper vendorInfoMapper;

    /**
     * 保存结算信息
     *
     * @param
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @Override
    public Map<String, Object> insertSettlementRecords(Integer vendorInfoId, BigDecimal amounts, Date startDate, Date endDate) {
        // TODO Auto-generated method stub
        Map<String, Object> map = new HashMap<>();
        map.put("result", true);
        map.put("message", "成功");
        Map<String, Object> maps = settlementRecordsMapper.listCountVendorInfoForTheSettlement(vendorInfoId, startDate, endDate);
        if (null != maps) {
            BigDecimal moneys = (BigDecimal) maps.get("moneys");
            if (moneys.subtract(amounts).intValue() != 0) {
                map.put("result", false);
                map.put("message", "结算金额与当前时间内不相等！");
            } else {
                SettlementRecords se = new SettlementRecords();
                se.setUpdateBy("system");
                se.setUpdateDate(new Date());
                se.setCreateBy("system");
                se.setCreateDate(new Date());
                se.setAmounts(amounts);
                se.setVendorInfoId(vendorInfoId);
                se.setSettlement_start(startDate);
                se.setSettlement_end(endDate);
                settlementRecordsMapper.insertSettlementRecords(se);
                map.put("returnId", se.getId());
            }
        }
        return map;
    }

    /**
     * 获取所有商家结算记录
     *
     * @param pageSize
     * @param pageIndex
     * @param tradingArea
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @Override
    public List<Map<String, Object>> listLimitVendorSettlementRecords(Integer pageSize, Integer pageIndex,
                                                                      String tradingArea, String vendorName, Date beginTime, Date endTime) {
        if (tradingArea == null || tradingArea.equals("")) {
            tradingArea = null;
        }
        if (vendorName == null || vendorName.equals("")) {
            vendorName = null;
        }
        if (beginTime == null || beginTime.equals("")) {
            beginTime = null;
        }
        if (endTime == null || endTime.equals("")) {
            endTime = null;
        }
        return settlementRecordsMapper.listLimitVendorSettlementRecords(pageSize, pageIndex, tradingArea, vendorName, beginTime, endTime);
    }

    /**
     * 获取所有商家结算条数
     *
     * @param tradingArea
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @Override
    public int countVendorSettlementRecords(String tradingArea, String vendorName, Date beginTime,
                                            Date endTime) {
        if (tradingArea == null || tradingArea.equals("")) {
            tradingArea = null;
        }
        if (vendorName == null || vendorName.equals("")) {
            vendorName = null;
        }
        if (beginTime == null || beginTime.equals("")) {
            beginTime = null;
        }
        if (endTime == null || endTime.equals("")) {
            endTime = null;
        }
        return settlementRecordsMapper.countVendorSettlementRecords(tradingArea, vendorName, beginTime, endTime);
    }

    /**
     * 获取某个商家结算详情
     *
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    @Override
    public List<Map<String, Object>> listSettlementRecords(Integer pageSize, Integer pageIndex, Integer vendorInfoId) {
        return settlementRecordsMapper.listSettlementRecords(pageSize, pageIndex, vendorInfoId);
    }

    /**
     * 方法的功能描述:TODO 获取商家详情（名称，交易数据）
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 10:19
     * @since 1.3.0
     */
    public BaseResult getVendorInfoSettlement(Integer vendorIndoId) {
        List<Map<String, Object>> lists = settlementRecordsMapper.getVendorInfoSettlement(vendorIndoId);
        if (lists != null && lists.size() != 0) {
            String name = dao.findManagerArea(lists.get(0).get("cityCode").toString());
            lists.get(0).put("managerArea", name);
        }
        return ApiResult.success(lists);
    }

    /**
     * 方法的功能描述:TODO 获取商家待结算数据
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 11:55
     * @since 1.3.0
     */
    public BaseResult listVendorInfoForTheSettlement(Integer pageSize, Integer pageIndex, Integer vendorInfoId, Date startDate, Date endDate) {
        List<Map<String, Object>> list = settlementRecordsMapper.listVendorInfoForTheSettlement(pageSize, pageIndex, vendorInfoId, startDate, endDate);
        Map<String, Object> map = settlementRecordsMapper.listCountVendorInfoForTheSettlement(vendorInfoId, startDate, endDate);
        return ApiResult.success(list, Integer.valueOf(map.get("counts").toString()), map.get("moneys").toString());
    }

    /**
     * 方法的功能描述:TODO获取商家待结算日期
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 15:03
     * @since 1.3.0
     */
    @Override
    public BaseResult getSettlementDate(Integer vendorIndoId) {
        Map<String, Object> map = settlementRecordsMapper.getSettlementDate(vendorIndoId);
        return ApiResult.success(map);
    }

    @Override
    public BaseResult getSettlementRecords(Integer pageSize, Integer pageInde, Integer vendorIndoId) {
        List<Map<String, Object>> list = settlementRecordsMapper.getSettlementRecords(pageSize, pageInde, vendorIndoId);
        int counts = settlementRecordsMapper.getCountSettlementRecords(vendorIndoId);
        return ApiResult.success(list, counts);
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
    public MessageRecipient getMessageRecipient(Integer settlementRecordsId) {
        Map<String, Object> map = settlementRecordsMapper.getSettlementRecord(settlementRecordsId);
        MessageRecipient m = new MessageRecipient();
        if (null != map) {
            m.setUserId(0);
            if (null != map.get("vendorInfoId") && !"".equals(map.get("vendorInfoId"))) {
                m.setVendorId(Integer.valueOf(map.get("vendorInfoId").toString()));
            } else {
                m.setVendorId(0);
            }

            //根据商家ID获取商家用户ID
            VendorInfo vendorInfo = vendorInfoMapper.getVendorById(m.getVendorId());
            if (vendorInfo != null)
                m.setUserId(vendorInfo.getUserId());
        } else {
            m.setVendorId(0);
            m.setUserId(0);
            m.setCarriersId(0);
        }
        return m;
    }


    /**
     * 根据结算记录ID获取结算记录
     *
     * @param id
     * @return
     */
    @Override
    public SettlementRecords getSettlementRecordById(Integer id) {
        return settlementRecordsMapper.getSettlementRecordById(id);
    }
}
