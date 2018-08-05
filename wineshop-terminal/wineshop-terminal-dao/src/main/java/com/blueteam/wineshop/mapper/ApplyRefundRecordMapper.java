package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.ApplyRefundRecordPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 功能描述: 申请退款记录表
 *@since 1.4.0
 *@author xiaojiang
 */
@Repository
public interface ApplyRefundRecordMapper {


    int saveApplyRefundRecord(ApplyRefundRecordPO record);
    /**
     * 方法的功能描述: 获取申请退款类
     *@methodName
     * @param: processStatus 传未处理
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 16:14
     *@modifier
     */
    ApplyRefundRecordPO getApplyRefundRecord(@Param("orderId") Long orderId,@Param("processStatus") Integer processStatus);
    int updateApplyRefundRecord(ApplyRefundRecordPO record);
}