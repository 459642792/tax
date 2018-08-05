package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.OrderFeePO;

public interface OrderFeePOMapper {
    int insert(OrderFeePO record);

    int insertSelective(OrderFeePO record);
}