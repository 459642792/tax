package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.TradeRecordPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 功能描述: 交易记录描述
 *@since 1.4.0
 *@author xiaojiang
 */
@Repository
public interface TradeRecordMapper {
    int saveTradeRecord(TradeRecordPO record);
    Integer getTradeRecord(@Param("orderId") Long orderId);
    int updateTradeRecord(TradeRecordPO record);
}

