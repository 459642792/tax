package com.blueteam.wineshop.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.blueteam.entity.dto.SettlementQueryDTO;
import com.blueteam.entity.po.SettlementPO;
import com.blueteam.entity.vo.OrderListVO;
import com.blueteam.entity.vo.SettlementListSumInfoVO;
import com.blueteam.entity.vo.SettlementListSumVO;
import com.blueteam.entity.vo.SettlementListVO;

public interface SettlementMapper {

	int pageQuerySettlementCount(SettlementQueryDTO settlementQueryDTO);

	List<SettlementListSumVO> pageQuerySettlementList(SettlementQueryDTO settlementQueryDTO);
	
	/**
	*  以后调整到xml中
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT tab1.*,tab2.*,IFNULL(tab2.allAmounts-tab4.allBalancedAmounts,0) as allUnbalancedAmounts,tab3.*,IFNULL(tab4.allBalancedAmounts,0)as allBalancedAmounts, tab4.lastTime as lastTime " + 
			"FROM (SELECT vendor.id as vendorId,vendor.Image as image, vendor.name as vendorName ,vendor.Telephone as phone,vendor.addr as address ,vendor.TradingArea as region  FROM vendorinfo vendor where vendor.Id=#{vendorId}) tab1   " + 
			"LEFT JOIN (SELECT COUNT(1) as allCount ,IFNULL(SUM(ord.pay_price),0) as allAmounts FROM tf_b_order ord WHERE ord.complete_state=1 and  ord.order_state=4  and ord.vendor_id=#{vendorId}) tab2  ON 1=1 " + 
			"LEFT JOIN (SELECT ord.complete_time as lastOrderCompleteTime FROM tf_b_order ord WHERE ord.complete_state=1 and  ord.order_state=4 and ord.settlement_state=-1  and ord.vendor_id=#{vendorId} order by ord.complete_time desc Limit 0,1) tab3   ON 1=1 " + 
			"LEFT JOIN (SELECT settlement.end_time as lastTime, settlement.all_balanced_amounts as allBalancedAmounts FROM tf_b_order_settlement settlement where settlement.vendor_id=#{vendorId} ORDER BY settlement.id desc LIMIT 0,1) tab4  ON 1=1 ")
	SettlementListSumInfoVO findVendorSimpleInfoByVendorId(Integer vendorId);

	
	int pageVendorSettlementHistoryCount(SettlementQueryDTO settlementQueryDTO);

	List<SettlementListVO> pageVendorSettlementHistoryList(SettlementQueryDTO settlementQueryDTO);

	
	int pageNotSettlementOrderCount(SettlementQueryDTO settlementQueryDTO);

	List<OrderListVO> pageNotSettlementOrderList(SettlementQueryDTO settlementQueryDTO);

	Date findLastSettlementDate(Integer vendorId);
	
	@MapKey("vendorId")
	Map<Integer, SettlementListSumVO> getSettlementListSumVO(List<Integer> vendorIds);

	int pageSettlementedOrderCount(SettlementQueryDTO settlementQueryDTO);

	List<OrderListVO> pageSettlementedOrderList(SettlementQueryDTO settlementQueryDTO);

	Map<String,Date> findLastSettlementAllDate(SettlementQueryDTO settlementQueryDTO);
	
	/**
	*  以后调整到xml中
	 */
	@Select("select * FROM (SELECT IFNULL(SUM(ord.pay_price),0) as allAmounts FROM tf_b_order ord  where ord.vendor_id=#{vendorId} and ord.complete_state=1 and  ord.order_state=4 ) tab1, " + 
			"(SELECT IFNULL(SUM(ord.pay_price),0) as allBalencedAmounts FROM tf_b_order ord  where  ord.vendor_id=#{vendorId} and ord.complete_state=1 and  ord.order_state=4 and settlement_state>-1) tab2," + 
			"(SELECT IFNULL(SUM(ord.pay_price),0) as willBalenceAmounts FROM tf_b_order ord  where ord.complete_time>=#{startTime} and ord.complete_time<=#{endTime} and ord.vendor_id=#{vendorId} and ord.complete_state=1 and  ord.order_state=4 and settlement_state=-1) tab3 " )
	Map<String,BigDecimal> getUnbalenceOrderAmounts(SettlementQueryDTO settlementQueryDTO);
	/**
	 *  以后调整到xml中
	 */
	@Insert("insert into tf_b_order_settlement"
			+ "(region,vendor_id,vendor_name,all_balanced_amounts,balanced_amounts,create_time,create_username,create_user_id,start_time,end_time,count_type,count_no)"
			+ "values(#{region},#{vendorId},#{vendorName},#{allBalancedAmounts},#{balancedAmounts},#{createTime},#{createUsername},#{createUserId},#{startTime},#{endTime},#{countType},#{countNo})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(SettlementPO settlement);
	/**
	*  以后调整到xml中
	 */
	@Update("update tf_b_order set settlement_state=#{id} where complete_time>=#{startTime} and complete_time<=#{endTime} and vendor_id=#{vendorId} and complete_state=1 and  order_state=4 and settlement_state=-1 ")
	void updateOrderStateToSettled(SettlementPO settlement);


	/**
	 * 获取结算记录
	 * @return
	 */
	List<SettlementPO> pageSettlementInfo(SettlementQueryDTO settlementQueryDTO);

	int getCount(SettlementQueryDTO settlementQueryDTO);


	SettlementPO getLastRecordByVD(Integer vendorId);

}
