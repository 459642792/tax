package com.blueteam.wineshop.service;

import java.math.BigDecimal;
import java.util.Map;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.SettlementQueryDTO;
import com.blueteam.entity.po.SettlementPO;
import com.blueteam.entity.vo.PageUtil;
import com.blueteam.entity.vo.SettlementListSumInfoVO;

public interface SettlementService {
	
	PageUtil pageQuerySettlementSum(SettlementQueryDTO settlementQueryDTO);

	SettlementListSumInfoVO getSettlementSumInfo(Integer vendorId);

	PageUtil pageVendorSettlementHistory(SettlementQueryDTO vendorId);

	PageUtil pageNotSettlementOrder(SettlementQueryDTO settlementQueryDTO);

	PageUtil pageSettlementedOrder(SettlementQueryDTO settlementQueryDTO);

	Map<String, BigDecimal>  getOrderAmounts(SettlementQueryDTO settlementQueryDTO);

	String settleOrder(SettlementPO settlement);

	/**
	 * 已结算记录
	 * @param settlementQueryDTO
	 * @return
	 */
	ApiResult pageSettlementInfo(SettlementQueryDTO settlementQueryDTO);

}
