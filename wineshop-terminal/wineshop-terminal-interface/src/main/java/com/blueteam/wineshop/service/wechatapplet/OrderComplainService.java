package com.blueteam.wineshop.service.wechatapplet;

import com.blueteam.entity.po.OrderComplainPO;
import com.blueteam.entity.vo.OrderComment;

public interface OrderComplainService {

    /**
     * 保存投诉
     * @param po
     * @return
     */
    int saveComplain(OrderComplainPO po);


    /**
     * 查看举报内容
     * @param po
     * @return
     */
    OrderComplainPO getComplain(OrderComplainPO po);

    /**
     * 是否有投诉
     * @param orderId
     * @return
     */
    int isHaveComplain(Long orderId);
}
