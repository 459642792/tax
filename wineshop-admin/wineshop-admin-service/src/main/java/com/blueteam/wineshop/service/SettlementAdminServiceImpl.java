package com.blueteam.wineshop.service;

import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RList;
import com.blueteam.entity.bo.OrderStateBO;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.SettlementListSearchDTO;
import com.blueteam.entity.vo.AdminSettlementListVO;
import com.blueteam.wineshop.mapper.SettlementAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by  NastyNas on 2018/1/18.
 */
@Service
public class SettlementAdminServiceImpl implements SettlementAdminService {

    @Autowired
    SettlementAdminMapper settlementAdminMapper;


    @Override
    public PageResult<List<AdminSettlementListVO>> listSettlement(SettlementListSearchDTO settlementListSearchDTO) {
//        //封装查询DTO
//        wrapSearchDTO(settlementListSearchDTO);
//        //未分页结算总数
//        Integer count = settlementAdminMapper.countSettlementList(settlementListSearchDTO);
//        //分页结算列表
//        List<AdminSettlementListVO> adminSettlementVOList = settlementAdminMapper.listSettlement(settlementListSearchDTO);


        return null;
    }

    private void wrapSearchDTO(SettlementListSearchDTO settlementListSearchDTO) {

    }
}
