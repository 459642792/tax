package com.blueteam.wineshop.service;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.BillingInfo;
import com.blueteam.entity.po.OrderInfo;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.wineshop.mapper.BillingInfoMapper;
import com.blueteam.wineshop.mapper.OrderInfoMapper;
import com.blueteam.wineshop.mapper.VendorInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 结算信息实现类
 *
 * @author xiaojiang 2017年3月1日
 * @version 1.0
 * @since 1.0 2017年3月1日
 */
@Service
@ApiLogin
public class BillingInfoServiceImpl implements BillingInfoService {

    @Autowired
    BillingInfoMapper billingInfoMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    VendorInfoMapper vendorInfoMapper;
    private static Logger logger = LoggerFactory.getLogger(BillingInfoServiceImpl.class);

    /**
     * 保存结算信息
     *
     * @param billingInfo
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    @Override
    public BaseResult saveBillingInfo(BillingInfo billingInfo) {
        billingInfo.setCreateDate(new Date());
        billingInfo.setUpdateDate(new Date());
        int num = billingInfoMapper.saveBillingInfo(billingInfo);
        if (num == 1) {
            return BaseResult.success();
        } else {
            return BaseResult.error("保存信息失败！");
        }
    }

    /**
     * 获取结算信息
     *
     * @param billingInfoId
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    @Override
    public BaseResult getBillingInfo(int billingInfoId) {
        BillingInfo b = billingInfoMapper.getBillingInfo(billingInfoId);
        return ApiResult.success(b);
    }

    /**
     * 修改结算信息
     *
     * @param billingInfo
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    @Override
    public BaseResult updateBillingInfo(BillingInfo billingInfo) {
        billingInfo.setUpdateDate(new Date());
        int num = billingInfoMapper.updateBillingInfo(billingInfo);
        if (num == 1) {
            return BaseResult.success();
        } else {
            return BaseResult.error("修改信息失败！");
        }
    }

    @Override
    public BaseResult listOrderInfo(int vendorId, int pageIndex, int pageSize) {
        VendorInfo vendorInfo = vendorInfoMapper.findByUserId(vendorId);
        if (null != vendorInfo) {
            vendorId = vendorInfo.getId();
            List<Map<String, Object>> list = orderInfoMapper.listOrderInfo(vendorId, OrderInfo.ORDER_STATUS_FINISH_FINISHED, pageIndex, pageSize);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Map<String, Object> map : list) {
                String updateDate = (String) map.get("updateDate");
                try {
                    map.put("updateDate", updateDate == null ? "" : sdf.format(sdf2.parse(updateDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                BigDecimal costLimitMoney = (BigDecimal) map.get("costLimitMoney");
                BigDecimal money = (BigDecimal) map.get("money");
                costLimitMoney = costLimitMoney == null ? BigDecimal.ZERO : costLimitMoney;
                money = money == null ? BigDecimal.ZERO : money;
                String desc = (String) map.get("condition");

                if ((desc == null || desc.isEmpty()) && costLimitMoney.doubleValue() != 0) {
                    desc = "满" + costLimitMoney.intValue() + "元减" + money.intValue();//优惠券描述
                }

                map.put("desc", desc == null ? "" : desc); //优惠券描述
            }
            int counts = orderInfoMapper.listOrderInfoCount(vendorId, OrderInfo.ORDER_STATUS_FINISH_FINISHED);
            return ApiResult.success(list, counts);
        } else {
            return ApiResult.error("用户没有找到！");
        }
    }

    @Override
    public BaseResult listOrderInfoSimple(int vendorId) {
        List<Map<String, Object>> list;
        try {
            VendorInfo vendorInfo = vendorInfoMapper.findByUserId(vendorId);
            if (null != vendorInfo) {
                vendorId = vendorInfo.getId();
            } else {
                return ApiResult.error("用户没有找到！");
            }
            list = orderInfoMapper.listOrderInfoSimple(vendorId, OrderInfo.ORDER_STATUS_FINISH_FINISHED);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Map<String, Object> map : list) {
                String updateDate = (String) map.get("updateDate");
                map.put("updateDate", updateDate == null ? "" : sdf.format(sdf2.parse(updateDate)));
                BigDecimal costLimitMoney = (BigDecimal) map.get("costLimitMoney");
                BigDecimal money = (BigDecimal) map.get("money");
                costLimitMoney = costLimitMoney == null ? BigDecimal.ZERO : costLimitMoney;
                money = money == null ? BigDecimal.ZERO : money;
                String desc = (String) map.get("condition");

                if ((desc == null || desc.isEmpty()) && costLimitMoney.doubleValue() != 0) {
                    desc = "满" + costLimitMoney.intValue() + "元减" + money.intValue();//优惠券描述
                }

                map.put("desc", desc == null ? "" : desc); //优惠券描述
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
        return ApiResult.success(list);
    }


}
