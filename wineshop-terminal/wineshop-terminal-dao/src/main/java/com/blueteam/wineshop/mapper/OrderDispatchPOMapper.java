package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.OrderDispatchPO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDispatchPOMapper {

    int saveOrderDispatch(OrderDispatchPO record);

}