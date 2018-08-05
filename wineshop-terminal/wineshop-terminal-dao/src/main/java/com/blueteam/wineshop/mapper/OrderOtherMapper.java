package com.blueteam.wineshop.mapper;

import com.blueteam.entity.bo.wechatapplet.CouponBO;
import com.blueteam.entity.bo.wechatapplet.OrderGoodsItemBO;
import com.blueteam.entity.vo.VendorPayTypeVO;
import com.blueteam.entity.vo.VendorPayWayVO;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 订单相关类型集合
 *
 * @author xiaojiang
 * @create 2018-01-08  15:03
 */
@Repository
public interface OrderOtherMapper {

    /**
     * 方法的功能描述: 获取商家支持的支付类型（如：在线支付等）
     *@methodName
      * @param: channelId 渠道id 请查看渠道表  微信 1
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/8 15:13
     *@modifier
     */
    List<VendorPayTypeVO> listVendorPayType(@Param("vendorId") Integer vendorId,@Param("channelId")Integer channelId);
    /**
     * 方法的功能描述: 获取商家支持的支付方式（如：微信 支付宝等）
     *@methodName
     * @param: null  payTypeId  支付方式id
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/8 15:13
     *@modifier
     */
    List<VendorPayWayVO> listVendorPayWay(@Param("vendorId") Integer vendorId,@Param("payTypeId")Integer payTypeId);

    /**
     * 方法的功能描述: 获取商品基础数据
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/8 16:47
     *@modifier
     */
    List<OrderGoodsItemBO> getOrderGoodsItem(@Param("vendorId") Integer vendorId,@Param("goodsList")List<Long> goodsList);
    /**
     * 方法的功能描述: 商家配送信息
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 14:55
     *@modifier
     */
    Map<String,Object> getVendorDispatch(@Param("vendorId") Integer vendorId);
    /**
     * 方法的功能描述:  获取用户在该商家未使用未过期的优惠券
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 14:56
     *@modifier
     */
    List<CouponBO> listCouponByVendorAndUser(@Param("vendorId")Integer vendorId, @Param("userId")Integer userId);
    /**
     * 方法的功能描述: 通过订单id获取优惠id
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 14:59
     *@modifier
     */
    Integer getByCouponId(@Param("orderNo")Long orderNo);
}
