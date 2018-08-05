package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.RefundResultPO;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundResultMapper {

    int saveRefundResult(RefundResultPO record);


}