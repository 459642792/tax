package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.OrderAttrPO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAttrPOMapper {

    /**
     * 方法的功能描述: 保存订单属性表
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/9 15:52
     *@modifier
     */
    int saveOrderAttr(OrderAttrPO record);

}