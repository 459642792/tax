package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.TradeListSearchDTO;
import com.blueteam.entity.vo.AdminTradeVO;

/**
 * 交易数据管理
 * Created by  NastyNas on 2018/1/15.
 */
public interface TradeAdminService {
    //查询列表页详情
    AdminTradeVO getTradeInfo(TradeListSearchDTO tradeListSearchDTO);
}
