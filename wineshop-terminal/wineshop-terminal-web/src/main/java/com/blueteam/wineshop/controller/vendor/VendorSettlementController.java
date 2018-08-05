package com.blueteam.wineshop.controller.vendor;


import com.blueteam.base.constant.VendorApiLogin;
import com.blueteam.base.util.JsonUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.SettlementQueryDTO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.wineshop.controller.BaseController;
import com.blueteam.wineshop.service.SettlementService;
import com.blueteam.wineshop.service.wechatapplet.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/vendor/settlement")
@VendorApiLogin
public class VendorSettlementController extends BaseController{

    @Resource
    private SettlementService settlementService;
    @Resource
    private OrderService orderService;
    /**
     * 待结算记录
     * @return
     */
    @RequestMapping(value = "/getWaitSettlementInfo",method = RequestMethod.POST)
    public BaseResult getWaitSettlementInfo(){


        return BaseResult.success();
    }


    /**
     * 已结算记录
     * @return
     * /vendor/settlement/getSettlementInfo?pageIndex=1&pageSize=10
     */
    @RequestMapping("/getSettlementInfo")
    public BaseResult getSettlementInfo(Integer pageIndex,Integer pageSize){
        SettlementQueryDTO dto=new SettlementQueryDTO();
        dto.setVendorId(getIdentify().getExtendId());
        dto.setPageIndex(pageIndex);
        dto.setPageSize(pageSize);
        return settlementService.pageSettlementInfo(dto);
    }

    /**
     * 结算记录的订单列表
     * @return
     * /vendor/settlement/getSettlementInfo?settlementId=1
     */
    @RequestMapping("/getSettlementDetail")
    public BaseResult getSettlementDetail(Integer settlementId){
        List<OrderPO> list=orderService.getSettlementOrder(settlementId);
        return ApiResult.success(list);
    }
}
