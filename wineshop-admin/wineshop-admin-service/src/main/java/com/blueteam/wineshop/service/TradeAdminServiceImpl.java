package com.blueteam.wineshop.service;

import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RDbTrans;
import com.blueteam.base.lang.RList;
import com.blueteam.entity.bo.AdminTradeBO;
import com.blueteam.entity.bo.OrderStateBO;
import com.blueteam.entity.dto.TradeListSearchDTO;
import com.blueteam.entity.vo.AdminTradeListVO;
import com.blueteam.entity.vo.AdminTradeVO;
import com.blueteam.wineshop.mapper.TradeAdminMapper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.blueteam.base.constant.Constants.CREATE_COUPON_CODE_VENDOR;
import static com.blueteam.base.constant.Constants.CREATE_COUPON_CODE_VENDOR_ADMIN;

/**
 * Created by  NastyNas on 2018/1/15.
 */
@Service
public class TradeAdminServiceImpl implements TradeAdminService {
    private static final Logger logger = LoggerFactory.getLogger(TradeAdminServiceImpl.class);

    @Autowired
    TradeAdminMapper tradeAdminMapper;

    @Override
    public AdminTradeVO getTradeInfo(TradeListSearchDTO tradeListSearchDTO) {
        //封装查询信息
        tradeListSearchDTO = wrapSearchDTO(tradeListSearchDTO);
        //查询交易信息
        List<AdminTradeBO> tradeBOList = tradeAdminMapper.getTradeInfo(tradeListSearchDTO);
        //查询分页交易信息
        List<AdminTradeBO> tradeBOPageList = tradeAdminMapper.getTradeListInfo(tradeListSearchDTO);
        //封装返回VO
        return wrapTradeVO(tradeBOList, tradeBOPageList);
    }

    /**
     * 封装返回VO
     *
     * @param tradeBOList
     * @param tradeBOPageList
     * @return
     */
    private AdminTradeVO wrapTradeVO(List<AdminTradeBO> tradeBOList, List<AdminTradeBO> tradeBOPageList) {
        //查询结果为空
        if (tradeBOList == null || tradeBOList.size() == 0) {
            return null;
        }
        //交易次数
        Integer count = tradeBOList.size();
        //平台优惠券金额(元)
        Double platformFee = 0D;
        //商家优惠券金额(元)
        Double vendorFee = 0D;
        //退款金额(分)
        Long refundFee = 0L;
        //交易总额(分)
        Long totalFee = 0L;
        //流水金额(分)
        Long statementFee = 0L;
        /*统计金额*/
        for (AdminTradeBO adminTradeBO : tradeBOList) {
            //平台优惠券
            if (CREATE_COUPON_CODE_VENDOR_ADMIN.equals(adminTradeBO.getCouponType())) {
                platformFee += adminTradeBO.getCouponFee();
            }
            //商家优惠券
            if (CREATE_COUPON_CODE_VENDOR.equals(adminTradeBO.getCouponType())) {
                vendorFee += adminTradeBO.getCouponFee();
            }
            //退款金额
            if (adminTradeBO.getRefundFee() != null) {
                refundFee += adminTradeBO.getRefundFee();
            }
            //交易总额
            if (adminTradeBO.getPayPrice() != null) {
                totalFee += adminTradeBO.getPayPrice();
            }
            //流水金额
            if (adminTradeBO.getOriginalPrice() != null) {
                statementFee += adminTradeBO.getOriginalPrice();
            }
        }
        /*分页交易信息封装*/
        List<AdminTradeListVO> tradeListVOS = Lists.newArrayList();
        for (AdminTradeBO adminTradeBO : tradeBOPageList) {
            AdminTradeListVO tradeListVO = new AdminTradeListVO();
            tradeListVO.setOrderNo(adminTradeBO.getOrderNo());
            tradeListVO.setTradeTime(RDbTrans.asShowDate(adminTradeBO.getTradeTime()));
            tradeListVO.setShopName(adminTradeBO.getShopName());
            tradeListVO.setTradeArea(adminTradeBO.getTradeArea());
            tradeListVO.setUserId(adminTradeBO.getUserId());
            tradeListVO.setOrderSource(adminTradeBO.getOrderSource());
            tradeListVO.setOriginalPrice(RDbTrans.asShowPrice(adminTradeBO.getOriginalPrice()));
            tradeListVO.setCouponType(wrapCouponType(adminTradeBO.getCouponType()));
            tradeListVO.setCouponFee(wrapCouponFee(adminTradeBO.getCouponFee()));
            tradeListVO.setPayPrice(RDbTrans.asShowPrice(adminTradeBO.getPayPrice()));
            tradeListVO.setOrderBusinessState(wrapOrderBusiness(adminTradeBO.getOrderStateBO()).getState());
            tradeListVO.setOrderBusinessStateName(wrapOrderBusiness(adminTradeBO.getOrderStateBO()).getDescription());
            tradeListVOS.add(tradeListVO);
        }
        //封装返回VO
        AdminTradeVO tradeVO = new AdminTradeVO();
        tradeVO.setCount(count);
        tradeVO.setPlatformFee(RDbTrans.asShowPrice(platformFee));
        tradeVO.setVendorFee(RDbTrans.asShowPrice(vendorFee));
        tradeVO.setRefundFee(RDbTrans.asShowPrice(refundFee));
        tradeVO.setTotalFee(RDbTrans.asShowPrice(totalFee));
        tradeVO.setStatementFee(RDbTrans.asShowPrice(statementFee));
        tradeVO.setAdminTradeListVOList(tradeListVOS);
        return tradeVO;
    }


    private OrderConstant.OrderBusinessStateEnum wrapOrderBusiness(OrderStateBO orderStateBO) {
        if (OrderStateHelp.isTradeSuccess(orderStateBO)) {
            return OrderConstant.OrderBusinessStateEnum.TRADE_SUCCESS;
        } else if (OrderStateHelp.isDrawbackSuccess(orderStateBO)) {
            return OrderConstant.OrderBusinessStateEnum.DRAWBACK_SUCCESS;
        } else if (OrderStateHelp.isDrawbackFail(orderStateBO)) {
            return OrderConstant.OrderBusinessStateEnum.DRAWBACK_FAIL;
        } else {
            logger.error("交易信息查询信息异常,订单状态信息={}", orderStateBO);
            throw new BusinessException("交易信息信息异常");
        }
    }

    /**
     * 封装优惠券金额
     *
     * @param couponFee
     * @return
     */
    private String wrapCouponFee(Double couponFee) {
        return couponFee == null ? "0.00" : couponFee.toString();
    }

    /**
     * 封装优惠券类型
     *
     * @param couponType
     * @return
     */
    private Integer wrapCouponType(String couponType) {
        //平台优惠券
        if (CREATE_COUPON_CODE_VENDOR_ADMIN.equals(couponType)) {
            return 1;
        }
        //商家优惠券
        if (CREATE_COUPON_CODE_VENDOR.equals(couponType)) {
            return 2;
        }
        return null;
    }

    private TradeListSearchDTO wrapSearchDTO(TradeListSearchDTO tradeListSearchDTO) {
        //校验请求
        checkSearchDTO(tradeListSearchDTO);
        //根据交易状态设置订单状态
        return matchBusinessState(tradeListSearchDTO);
    }

    private TradeListSearchDTO matchBusinessState(TradeListSearchDTO tradeListSearchDTO) {
        //默认为全部: 全部=交易成功(待评价+已完成)+退款成功+退款失败
        if (tradeListSearchDTO == null || tradeListSearchDTO.getOrderBusinessState() == null) {
            List<OrderStateBO> list = Lists.newArrayList();
            list.addAll(OrderStateHelp.transCompositeOrderStateBO(OrderConstant.OrderBusinessStateEnum.TRADE_SUCCESS.getState()));
            list.add(OrderStateHelp.transOrderStateBO(OrderConstant.OrderBusinessStateEnum.DRAWBACK_FAIL.getState()));
            list.add(OrderStateHelp.transOrderStateBO(OrderConstant.OrderBusinessStateEnum.DRAWBACK_SUCCESS.getState()));
            if (tradeListSearchDTO == null) {
                TradeListSearchDTO dto = new TradeListSearchDTO();
                dto.setOrderStateBOList(list);
                return dto;
            } else {
                tradeListSearchDTO.setOrderStateBOList(list);
                return tradeListSearchDTO;
            }
        }
        //交易成功
        else if (OrderStateHelp.isTradeSuccess(tradeListSearchDTO.getOrderBusinessState())) {
            tradeListSearchDTO.setOrderStateBOList(OrderStateHelp.transCompositeOrderStateBO(OrderConstant.OrderBusinessStateEnum.TRADE_SUCCESS.getState()));
            return tradeListSearchDTO;
        }
        //退款成功
        else if (OrderStateHelp.isDrawbackSuccess(tradeListSearchDTO.getOrderBusinessState())) {
            tradeListSearchDTO.setOrderStateBOList(RList.asList(OrderStateHelp.transOrderStateBO(OrderConstant.OrderBusinessStateEnum.DRAWBACK_SUCCESS.getState())));
            return tradeListSearchDTO;
        }
        //退款失败
        else if (OrderStateHelp.isDrawbackFail(tradeListSearchDTO.getOrderBusinessState())) {
            tradeListSearchDTO.setOrderStateBOList(RList.asList(OrderStateHelp.transOrderStateBO(OrderConstant.OrderBusinessStateEnum.DRAWBACK_FAIL.getState())));
            return tradeListSearchDTO;
        } else {
            logger.error("交易状态信息错误,交易查询参数={}", tradeListSearchDTO);
            throw new BusinessException("交易状态信息错误");
        }

    }

    private void checkSearchDTO(TradeListSearchDTO tradeListSearchDTO) {
        if (tradeListSearchDTO != null && tradeListSearchDTO.getOrderBusinessState() != null &&
                !OrderStateHelp.isDrawbackSuccess(tradeListSearchDTO.getOrderBusinessState()) &&
                !OrderStateHelp.isDrawbackFail(tradeListSearchDTO.getOrderBusinessState()) &&
                !OrderStateHelp.isTradeSuccess(tradeListSearchDTO.getOrderBusinessState())) {
            logger.error("交易状态信息错误,查询参数={}", tradeListSearchDTO);
            throw new BusinessException("交易状态信息错误");
        }
    }
}
