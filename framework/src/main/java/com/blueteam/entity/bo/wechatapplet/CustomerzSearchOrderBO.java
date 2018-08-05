package com.blueteam.entity.bo.wechatapplet;

import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RStr;
import com.blueteam.entity.bo.OrderStateBO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户收索订单列表
 *
 * @author xiaojiang
 * @create 2018-02-05  13:47
 */
public class CustomerzSearchOrderBO{
    /**  用户id */
    private Integer userId;
    /**  分页参数 */
    private Integer pageIndex;
    /**  分页参数 */
    private Integer pageSize;
    /**  条件判断 */
    private Map<String,Integer> map;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public CustomerzSearchOrderBO() {
    }

    public CustomerzSearchOrderBO(Integer userId, Integer pageIndex, Integer pageSize, Integer state) {
        this.userId = userId;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        OrderStateBO orderState =  OrderStateHelp.transOrderStateBO(state);
        Map<String,Integer> map = new HashMap<>();
        if (RStr.isNotEmpty(orderState.getCommentState())){
            map.put("comment_state",orderState.getCommentState());
        }
        if (RStr.isNotEmpty(orderState.getCompleteState())){
            map.put("complete_state",orderState.getCompleteState());
        }
        if (RStr.isNotEmpty(orderState.getOrderState())){
            map.put("order_state",orderState.getOrderState());
        }
        if (RStr.isNotEmpty(orderState.getRefundState())){
            map.put("refund_state",orderState.getRefundState());
        }
        this.map = map;
    }
}
