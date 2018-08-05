package com.blueteam.wineshop.service.wechatapplet;

import com.blueteam.entity.po.OrderComplainPO;
import com.blueteam.wineshop.mapper.OrderComplainPOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderComplainService")
public class OrderComplainServiceImpl implements OrderComplainService {

    @Autowired
    private OrderComplainPOMapper orderComplainPOMapper;

    @Override
    public int saveComplain(OrderComplainPO po) {
        return orderComplainPOMapper.insert(po);
    }

    @Override
    public OrderComplainPO getComplain(OrderComplainPO po) {
        return orderComplainPOMapper.getComplain(po);
    }

    @Override
    public int isHaveComplain(Long orderId) {
        return orderComplainPOMapper.isHaveComplain(orderId);
    }
}
