package com.blueteam.wineshop.controller.vendor;


import com.blueteam.base.constant.VendorApiLogin;
import com.blueteam.base.util.JsonUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.VendorOrderDTO;
import com.blueteam.entity.po.ApplyRefundRecordPO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.entity.vo.RefundRecordVO;
import com.blueteam.entity.vo.VendorOrderVO;
import com.blueteam.wineshop.controller.BaseController;
import com.blueteam.wineshop.mapper.ApplyRefundRecordMapper;
import com.blueteam.wineshop.service.VendorInfoService;
import com.blueteam.wineshop.service.vendor.VendorOrderService;
import com.blueteam.wineshop.service.wechatapplet.OrderCommentService;
import com.blueteam.wineshop.service.wechatapplet.OrderRefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * ljc 2018年1月8日 17:16:04
 */
@RestController
@RequestMapping("/vendor/order")
@VendorApiLogin
public class VendorOrderController extends BaseController{
    public Logger logger = LoggerFactory.getLogger(VendorOrderController.class);

    @Autowired
    private VendorOrderService vendorOrderService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private ApplyRefundRecordMapper applyRefundRecordMapper;

    @Resource
    private OrderCommentService orderCommentService;

    /**
     * 訂單列表
     * @param dto
     * @return
     */
    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public BaseResult getOrderList(@RequestBody VendorOrderDTO dto){
        dto.setVendorId(this.getIdentify().getExtendId());
        //dto.setVendorId(3);
        dto.setBeginIndex((dto.getPageIndex()-1)*dto.getPageSize());
        logger.info("params of /vendor/order/getList::{}",JsonUtil.serialize(dto));
        return vendorOrderService.getOrderList(dto);
    }


    /**
     * 订单详情
     */
    @RequestMapping("/orderDetail")
    public BaseResult orderDetail(@RequestParam("orderId") Long orderId){
        VendorOrderVO vo=vendorOrderService.orderDetail(orderId);
        return ApiResult.success(vo);

    }


    /**
     * 接單
     */
     @RequestMapping(value = "/receiveOrder",method = RequestMethod.POST)
     public BaseResult receiveOrder(@RequestBody VendorOrderDTO dto){
         dto.setVendorId(this.getIdentify().getExtendId());
         //dto.setVendorId(3);
         if (vendorOrderService.receiveOrder(dto)>0) {
             return BaseResult.success();
         }
         return BaseResult.error("接单失败！");
     }


    /**
     * 確認收貨
     */
    @RequestMapping(value = "/confirmOrder",method = RequestMethod.POST)
    public BaseResult confirmOrder(@RequestBody VendorOrderDTO dto){
        dto.setVendorId(this.getIdentify().getExtendId());
        //dto.setVendorId(3);
        if (vendorOrderService.confirmOrder(dto)>0) {
            return BaseResult.success();
        }
        return BaseResult.error("确认收货失败！");
    }


    /**
     * 確認退款
     */
    @RequestMapping("/refundOrder")
    public BaseResult refundOrder(@RequestParam("orderId") Long orderId){
        //dto.setVendorId(this.getIdentify().getExtendId());
        OrderPO po=vendorOrderService.getByOrderId(orderId);
        if (po==null){
            return  BaseResult.error("退款失败,未找到相关订单");
        }
        int res=orderRefundService.refundOrder(orderId,po.getUserId());
        if (res>0)return BaseResult.success();
        return  BaseResult.error("退款失败");
    }

    /**
     * 查看退款原因
     */
    @RequestMapping("/getRefundReason")
    public BaseResult getRefundReason(@RequestParam("orderId") Long orderId){
        ApplyRefundRecordPO po=applyRefundRecordMapper.getApplyRefundRecord(orderId,ApplyRefundRecordPO.PROCESS_STATUS_UNTREATED);
        RefundRecordVO vo = new RefundRecordVO();
        if (po!=null) {
            vo.setRefundReason(po.getRefundReasonDesc());
            vo.setRefundRemark(po.getRefundRemark());
        }
        return ApiResult.success(vo);
    }


    /**
     * 订单统计
     */
    @RequestMapping("/orderStatistics")
    public BaseResult orderStatistics(){
        return ApiResult.success(vendorOrderService.orderStatistics(this.getIdentify().getExtendId()));
    }

    /**
     * 查看评价
     * @param
     * @return
     */
    @RequestMapping("/getComment")
    public BaseResult getComment(@RequestParam("orderId") Long orderId){
        return ApiResult.success(orderCommentService.getByOrderId(orderId));
    }

    /**
     * 商家首页统计
     */
    @RequestMapping("/vendorIndexStatistics")
    public BaseResult vendorIndexStatistics(){
        return ApiResult.success(vendorOrderService.indexStatistics(this.getIdentify().getExtendId()));
    }





}
