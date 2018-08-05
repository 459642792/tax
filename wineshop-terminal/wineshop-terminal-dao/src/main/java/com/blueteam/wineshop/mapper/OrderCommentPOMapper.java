package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.VendorOrderDTO;
import com.blueteam.entity.po.OrderCommentPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderCommentPOMapper {

    int insert(OrderCommentPO record);

    OrderCommentPO getByOrderId(Long orderId);

    List<OrderCommentPO> getCommentList(VendorOrderDTO dto);

    int getCount(VendorOrderDTO dto);

    List<OrderCommentPO> getCommentListByOrderId(@Param("userId") int userId,@Param("orderId") long orderId);


    int delComment(Long id);

    Double averageScore(Integer vendorId);
}