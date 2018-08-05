package com.blueteam.wineshop.service.wechatapplet;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.VendorOrderDTO;
import com.blueteam.entity.po.OrderCommentPO;

import java.util.List;

public interface OrderCommentService {

    int save(OrderCommentPO po);

    OrderCommentPO getByOrderId(Long orderId);

    ApiResult getCommentList(VendorOrderDTO dto);

    /**
     * 删除评价
     * @param commentId
     * @param userId
     * @return
     */
    BaseResult delComment(long commentId,int userId,Long orderId);

    List<OrderCommentPO> getCommentListByOrderId(int userId, Long orderId);

    /**
     * 店铺评分
     * @param vendorId
     * @return
     */
    Double averageScore(Integer vendorId);
}
