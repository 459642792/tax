package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.SettlementQueryDTO;
import com.blueteam.entity.po.SettlementPO;
import com.blueteam.entity.vo.OrderListVO;
import com.blueteam.entity.vo.PageUtil;
import com.blueteam.entity.vo.SettlementListSumInfoVO;
import com.blueteam.entity.vo.SettlementListSumVO;
import com.blueteam.entity.vo.SettlementListVO;
import com.blueteam.wineshop.mapper.SettlementMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class SettlementServiceImpl implements SettlementService {
private static Logger log=Logger.getLogger(SettlementServiceImpl.class);
	@Autowired
	private SettlementMapper settlementMapper;
	@Autowired
	private PlatformTransactionManager txManager;

	@Override
	public PageUtil pageQuerySettlementSum(SettlementQueryDTO settlementQueryDTO) {
		PageUtil page = null;
		int count = settlementMapper.pageQuerySettlementCount(settlementQueryDTO);
		if (count == 0 || (settlementQueryDTO.getPageIndex() - 1) * settlementQueryDTO.getPageSize() >= count) {
			return PageUtil.emptyData();
		}
		page = PageUtil.getPageByCount(count);
		List<Integer> vendorIds=new ArrayList<Integer>();
		List<SettlementListSumVO> vo = settlementMapper.pageQuerySettlementList(settlementQueryDTO);
		 for (SettlementListSumVO settlementListSumVO : vo) {
			 vendorIds.add(settlementListSumVO.getVendorId());
		}
		Map<Integer,SettlementListSumVO>   sumMap=settlementMapper.getSettlementListSumVO(vendorIds);
		 for (SettlementListSumVO settlementListSumVO : vo) {
			 SettlementListSumVO voTemp = sumMap.get(settlementListSumVO.getVendorId());
			 BigDecimal allAmounts =null;
			 if(voTemp!=null) {
				 allAmounts = voTemp.getAllAmounts();
			 }
			 if(allAmounts==null) {
				 allAmounts=new BigDecimal(0);
			 }
			 settlementListSumVO.setAllAmounts(allAmounts);
			 BigDecimal allUnbalancedAmounts = allAmounts.subtract(settlementListSumVO.getAllBalancedAmounts());
			 settlementListSumVO.setAllUnbalancedAmounts(allUnbalancedAmounts);
		}
		// 待订单统计数据填充
		page.setArr(vo);
		return page;
	}

	@Override
	public SettlementListSumInfoVO getSettlementSumInfo(Integer vendorId) {
		SettlementListSumInfoVO vo = settlementMapper.findVendorSimpleInfoByVendorId(vendorId);
		return vo;
	}

	@Override
	public PageUtil pageVendorSettlementHistory(SettlementQueryDTO settlementQueryDTO) {
		PageUtil page = null;
		int count = settlementMapper.pageVendorSettlementHistoryCount(settlementQueryDTO);
		if (count == 0 || (settlementQueryDTO.getPageIndex() - 1) * settlementQueryDTO.getPageSize() >= count) {
			return PageUtil.emptyData();
		}
		page = PageUtil.getPageByCount(count);
		List<SettlementListVO> vo = settlementMapper.pageVendorSettlementHistoryList(settlementQueryDTO);
		page.setArr(vo);
		return page;
	}

	@Override
	public PageUtil pageNotSettlementOrder(SettlementQueryDTO settlementQueryDTO) {
		PageUtil page = null;
		int count = settlementMapper.pageNotSettlementOrderCount(settlementQueryDTO);
		if (count == 0 || (settlementQueryDTO.getPageIndex() - 1) * settlementQueryDTO.getPageSize() >= count) {
			return PageUtil.emptyData();
		}
		page = PageUtil.getPageByCount(count);
		List<OrderListVO> vo = settlementMapper.pageNotSettlementOrderList(settlementQueryDTO);
		page.setArr(vo);
		return page;
	}

	@Override
	public PageUtil pageSettlementedOrder(SettlementQueryDTO settlementQueryDTO) {
		PageUtil page = null;
		int count = settlementMapper.pageSettlementedOrderCount(settlementQueryDTO);
		if (count == 0 || (settlementQueryDTO.getPageIndex() - 1) * settlementQueryDTO.getPageSize() >= count) {
			return PageUtil.emptyData();
		}
		page = PageUtil.getPageByCount(count);
		List<OrderListVO> vo = settlementMapper.pageSettlementedOrderList(settlementQueryDTO);
		page.setArr(vo);
		return page;
	}

	@Override
	public Map<String, BigDecimal> getOrderAmounts(SettlementQueryDTO settlementQueryDTO) {
		return settlementMapper.getUnbalenceOrderAmounts(settlementQueryDTO);
	}
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public String settleOrder(SettlementPO settlement) {
		//TODO事务处理
		TransactionDefinition def=new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus txStatus = txManager.getTransaction(def);
		try {
			settlementMapper.insert(settlement);
			settlementMapper.updateOrderStateToSettled(settlement);
			txManager.commit(txStatus);
		} catch (Exception e) {
			 e.printStackTrace();
			 txManager.rollback(txStatus);
			 log.error("添加结算记录失败!"+"结算账户:"+settlement.getVendorId()
			 +"结算开始时间:"+settlement.getStartTime()+"结算截止时间:"+settlement.getEndTime()
			 +"结算金额"+settlement.getBalancedAmounts()+"错误消息提示:"+e.getMessage());
			 return "发生未知异常添加结算信息记录失败!";
		}
		log.info("添加结算记录成功,结算记录id="+settlement.getId());
		return null;
	}

	@Override
	public ApiResult pageSettlementInfo(SettlementQueryDTO settlementQueryDTO) {
		settlementQueryDTO.setBeginIndex((settlementQueryDTO.getPageIndex()-1)*settlementQueryDTO.getPageSize());
		List<SettlementPO> pos=settlementMapper.pageSettlementInfo(settlementQueryDTO);
		int count=settlementMapper.getCount(settlementQueryDTO);
		return ApiResult.success(pos,count);
	}
}
