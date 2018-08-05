package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.OrderComplainPO;

public interface OrderComplainPOMapper {

    int insert(OrderComplainPO record);


    OrderComplainPO getComplain(OrderComplainPO po);

    int isHaveComplain(Long orderId);
}