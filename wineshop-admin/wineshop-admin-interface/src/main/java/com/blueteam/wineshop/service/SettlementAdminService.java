package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.SettlementListSearchDTO;
import com.blueteam.entity.vo.AdminSettlementListVO;

import java.util.List;

/**
 * 交易信息-结算
 * Created by  NastyNas on 2018/1/18.
 */
public interface SettlementAdminService {
    //分页查询结算列表页
    PageResult<List<AdminSettlementListVO>> listSettlement(SettlementListSearchDTO settlementListSearchDTO);
}
