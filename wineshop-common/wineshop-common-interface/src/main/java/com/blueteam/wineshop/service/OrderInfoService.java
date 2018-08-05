package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.BasePageSearch;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.CouponInfo;
import com.blueteam.entity.po.OrderInfo;
import com.blueteam.entity.dto.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Marx
 * <p>
 * OrderInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface OrderInfoService {

    /**
     * @param
     * @return
     */
    List<OrderInfo> OrderInfoList(int userid);

    /**
     * @param orderInfo
     * @return int
     * @Description: 生产订单
     * @author xiaojiang
     * @date 2017年2月23日 下午5:27:01
     */
    BaseResult saveOrderInfo(OrderInfo orderInfo, String spbillCreateIp, String body);

    /**
     * 微信回调函数
     *
     * @param request
     * @return
     * @author xiaojiang 2017年3月1日
     * @version 1.0
     * @since 1.0 2017年3月1日
     */
    String payWXSyntony(HttpServletRequest request);

    /**
     * 判断优惠卷是否可用
     *
     * @param num
     * @param
     * @return
     * @author xiaojiang 2017年2月27日
     * @version 1.0
     * @since 1.0 2017年2月27日
     */
    boolean decideCoupon(Double num, CouponInfo couponInfo);

    /**
     * 查询优惠券订单状态
     *
     * @author xiaojiang 2017年3月7日
     * @version 1.0
     * @since 1.0 2017年3月7日
     */
    void queryCouponsOrderStatus();

    /**
     * 根据商户订单号查询微信订单状态
     *
     * @param outTradeNo
     * @return
     * @author xiaojiang 2017年3月7日
     * @version 1.0
     * @since 1.0 2017年3月7日
     */
    Map<String, Object> listWXOrderInfo(String outTradeNo);

    /**
     * 关闭未支付的订单
     *
     * @param outTradeNo
     * @return
     * @author xiaojiang 2017年3月8日
     * @version 1.0
     * @since 1.0 2017年3月8日
     */
    BaseResult ckiseWXOrderInfo(String outTradeNo);

    /**
     * 根据订单编号查询状态
     *
     * @param outTradeNo
     * @return
     * @author xiaojiang 2017年3月8日
     * @version 1.0
     * @since 1.0 2017年3月8日
     */
    BaseResult getOrderInfo(String outTradeNo);

    /**
     * 用户积分统计
     *
     * @param
     * @return
     */
    Map<String, Object> sumjifen(int UserId);

    /**
     * 根据条件查询总交易次数 交易额
     *
     * @param status
     * @param tradingArea
     * @param type
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    Map<String, Object> countPrices(int status, String tradingArea, int type, String vendorName, Date beginTime, Date endTime);

    /**
     * 交易数据
     * 返回交易地区 商家名称等
     *
     * @param pageSize
     * @param pageIndex
     * @param tradingArea
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @param type
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    BaseResult listLimitOrderInfo(Integer pageSize, Integer pageIndex, String tradingArea, String vendorName, Date beginTime, Date endTime, int type);

    /**
     * 根据id查询交易金额
     *
     * @param ids
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    Map<String, Object> countPrice(String ids);


    /**
     * 获取用户的消费记录
     *
     * @param search 用户ID
     * @return
     */
    PageResult<List<OrderInfo>> selectUserOrderRecord(BasePageSearch search);

    /**
     * 获取订单详情信息(专属C端使用)
     *
     * @param
     * @return
     */
    OrderInfo getOrderInfoC(String orderno);

    /**
     * 方法的功能描述:TODO 获取 消息接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    MessageRecipient getMessageRecipient(Integer orderInfoId);


    /**
     * 根据ID获取支付用户昵称和支付款信息
     *
     * @param id
     * @return
     */
    OrderInfo selectPayUserInfo(Integer id);

    /**
     * 根据订单ID获取普通用户接收者信息
     *
     * @param id
     * @return
     */
    MessageRecipient getEveryMessageRecipient(Integer id);

    /**
     * 根据订单ID获取商家用户接收者信息
     *
     * @param id
     * @return
     */
    MessageRecipient getVendorMessageRecipient(Integer id);

    BaseResult getWXOpenid(String code);
    BaseResult  getWXAccessToken();
}
