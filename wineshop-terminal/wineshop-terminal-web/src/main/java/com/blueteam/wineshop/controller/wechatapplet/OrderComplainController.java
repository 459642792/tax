package com.blueteam.wineshop.controller.wechatapplet;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.util.JsonUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.OrderComplainPO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.wineshop.controller.BaseController;
import com.blueteam.wineshop.service.vendor.VendorOrderService;
import com.blueteam.wineshop.service.wechatapplet.OrderComplainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/complain")
@ApiLogin
public class OrderComplainController extends BaseController{
    private final static Logger log= LoggerFactory.getLogger(OrderComplainService.class);

    @Autowired
    private OrderComplainService orderComplainService;
    @Autowired
    private VendorOrderService vendorOrderService;

    /**
     * 保存投诉
     * @return
     */
    @RequestMapping(value = "/saveComplain",method = RequestMethod.POST)
    public BaseResult saveComplain(@RequestBody OrderComplainPO po){
        po.setUserId(this.getCurrentUserID());
        OrderPO orderPO=vendorOrderService.getByOrderNo(po.getOrderNo());
        po.setOrderId(orderPO.getOrderId());
        po.setPayPrice(orderPO.getPayPrice().intValue());
        po.setVendorId(orderPO.getVendorId().longValue());
        log.info("parmas of /order/complain/saveComplain:{}", JsonUtil.serialize(po));
        Long timeS=System.currentTimeMillis()-orderPO.getCompleteTime().getTime();
        if (timeS>24*60*60*1000){
            return BaseResult.error("投诉失败,已经过了投诉时间!");
        }
        if (orderComplainService.saveComplain(po)>0)
        return BaseResult.success();
        return BaseResult.error("投诉失败!");
    }

    @RequestMapping("/getComplain")
    public BaseResult getComplain(@RequestParam("orderNo") String orderNo){
        OrderComplainPO po=new OrderComplainPO();
        po.setUserId(this.getCurrentUserID());
        po.setOrderId(vendorOrderService.getOrderIdByNo(orderNo));
        OrderComplainPO orderComplainPO=orderComplainService.getComplain(po);
        if (orderComplainPO==null)return BaseResult.error("未找到相关举报信息！");
        return ApiResult.success(orderComplainPO);
    }


}
