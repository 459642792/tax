package com.blueteam.wineshop.controller.wechatapplet;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.util.JsonUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.VendorOrderDTO;
import com.blueteam.entity.po.OrderCommentPO;
import com.blueteam.wineshop.controller.BaseController;
import com.blueteam.wineshop.service.vendor.VendorOrderService;
import com.blueteam.wineshop.service.wechatapplet.OrderCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 订单评价
 * ljc 2018年1月13日 11:36:48
 */
@RestController
@RequestMapping("/order/comment")

public class OrderCommentController extends BaseController {
    public Logger logger = LoggerFactory.getLogger(OrderCommentController.class);

    @Resource
    private OrderCommentService orderCommentService;

    @Resource
    private VendorOrderService vendorOrderService;

    /**
     * 保存评价
     * @param po
     * @return
     *
     *
     */
    @ApiLogin
    @RequestMapping(value = "/saveComment",method = RequestMethod.POST)
    public BaseResult saveComment(@RequestBody OrderCommentPO po){
        po.setUserId(this.getCurrentUserID());
        po.setUserName(this.getUserName());
        po.setOrderId(vendorOrderService.getOrderIdByNo(po.getOrderNo()));
        int res=orderCommentService.save(po);
        if (res>0){
            return BaseResult.success();
        }
        if (res==-1) {
            return BaseResult.error("该订单已评论，不能重复评论！");
        }
        return BaseResult.error("评价失败！");
    }

    /**
     * 查看评价
     * @param
     * @return
     */
    @ApiLogin
    @RequestMapping("/getComment")
    public BaseResult getComment(@RequestParam("orderNo") String orderNo){
        Long orderId=vendorOrderService.getOrderIdByNo(orderNo);
        OrderCommentPO po=orderCommentService.getByOrderId(orderId);
        Long timeS=System.currentTimeMillis()-po.getCreatedTime().getTime();
        if (timeS<24*60*60*1000){
            if (orderCommentService.getCommentListByOrderId(po.getUserId(),orderId).size()<=1){
                po.setShowDelete(1);
            }
        }
        return ApiResult.success(po);
    }


    /**
     * 商家评价列表
     * @param dto
     * @return
     */
    @RequestMapping(value = "/getCommentList",method = RequestMethod.POST)
    public BaseResult getCommentList(@RequestBody VendorOrderDTO dto){
        logger.info("params of /order/comment/getCommentList::{}", JsonUtil.serialize(dto));
        return orderCommentService.getCommentList(dto);
    }

    /**
     * 删除评价
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/delComment")
    public BaseResult delComment(@RequestParam("orderNo") String orderNo,@RequestParam("commentId") long commentId){
        Long orderId=vendorOrderService.getOrderIdByNo(orderNo);
        return orderCommentService.delComment(commentId,this.getCurrentUserID(),orderId);
    }
}
