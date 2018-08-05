package com.blueteam.wineshop.service.vendor;

import com.blueteam.base.cache.redis.Redis;
import com.blueteam.base.util.Common;
import com.blueteam.base.util.DateUtil;
import com.blueteam.base.util.StringUtil;
import com.blueteam.base.util.aliyun.SmsUtil;
import com.blueteam.entity.bo.SMSMessageBO;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.VendorOrderDTO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.entity.vo.OrderStatisticsVO;
import com.blueteam.entity.vo.VendorOrderVO;
import com.blueteam.wineshop.mapper.OrderMapper;
import com.blueteam.wineshop.service.MessageSubService;
import com.blueteam.wineshop.service.VendorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;
import java.util.*;

@Service("vendorOrderService")
public class VendorOrderServiceImpl implements VendorOrderService{
    @Autowired
    private OrderMapper orderMapper;

    @Resource
    private VendorInfoService vendorInfoService;

    @Resource
    private MessageSubService messageSubService;

    @Override
    public ApiResult<List<VendorOrderVO>> getOrderList(VendorOrderDTO dto) {
        List<VendorOrderVO> list=orderMapper.getOrderList(dto);
        dto.setKeyword(null);
        int count=orderMapper.getCount(dto);
        return ApiResult.success(list, count);
    }


    @Override
    public int receiveOrder(VendorOrderDTO dto) {
//        OrderPO orderPO=orderPOMapper.getByOrderId(dto.getOrderId());
//        if (orderPO.getOrderState()==2 && orderPO.getRefoundState()==0){//必须是待接单未申请退款状态
//            return orderPOMapper.receiveOrder(dto);
//        }
//        return 0;
        //发送消息
        OrderPO po=orderMapper.getByOrderId(dto.getOrderId());
        messageSubService.senUserMessage(3,po);
        return orderMapper.receiveOrder(dto);
    }


    @Override
    @Transactional
    public int confirmOrder(VendorOrderDTO dto) {
        OrderPO po=orderMapper.getByOrderId(dto.getOrderId());
        dto.setVersion(po.getVersion());
        int res=orderMapper.confirmOrder(dto);
        //优化时下面的部分应该放线程里执行
        //添加总销量和总销售额
        if (res>0) {
            try {
                List<Map> list = orderMapper.getOrderGoodsByOrderId(dto.getOrderId());
                Calendar cal = Calendar.getInstance();
                int month = cal.get(Calendar.MONTH) + 1;
                JedisCommands jedis = Redis.getJedis();
                if (list != null && list.size() > 0) {
                    for (Map map : list) {
                        //月份_商家id_商品id_monthly
                        String key = month + "_" + po.getVendorId() + "_" + map.get("goods_id") + "_monthly";
                        if (jedis.exists(key)) {
                            jedis.incrBy(key, Long.parseLong(map.get("goods_num").toString()));
                        } else {
                            jedis.incrBy(key, Long.parseLong(map.get("goods_num").toString()));
                            jedis.expire(key, 32 * 24 * 60 * 60);
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            vendorInfoService.increaseOrderStatistics(po.getVendorId(), 1, 0, po.getPayPrice().intValue());
            //发送消息
            messageSubService.senUserMessage(2, po);
        }
        return res;
    }

    @Override
    public VendorOrderVO orderDetail(Long orderId) {
        return orderMapper.orderDetail(orderId);
    }

    @Override
    public VendorOrderVO orderDetail2(String orderNo,Long orderId) {
        return orderMapper.orderDetail2(orderNo,orderId);
    }

    @Override
    @Transactional
    public int commentOrderFinish(Long orderId,Integer score) {
        return orderMapper.commentOrderFinish(orderId,score);
    }

    @Override
    public OrderStatisticsVO orderStatistics(Integer vendorId) {
        return new OrderStatisticsVO(orderMapper.getWaitReceiveOrder(vendorId),orderMapper.getLowerOrder(vendorId));
    }

    @Override
    public Long getOrderIdByNo(String orderNo) {
        return orderMapper.getOrderIdByNo(orderNo);
    }

    @Override
    @Transactional
    public int updateScoreAndCommentStatus(Long orderId) {
        return orderMapper.updateScoreAndCommentStatus(orderId);
    }

    @Override
    public OrderPO getByOrderNo(String orderNo) {
        return orderMapper.getByOrderNo(orderNo);
    }

    @Override
    public OrderPO getByOrderId(Long orderId) {
        return orderMapper.getByOrderId(orderId);
    }

    @Override
    public int getCount(VendorOrderDTO dto) {
        return orderMapper.getCount(dto);
    }

    /**
     * 首页统计
     * @param vendorId
     * @return
     */
    @Override
    public OrderStatisticsVO indexStatistics(Integer vendorId) {
        OrderStatisticsVO vo=new OrderStatisticsVO(orderMapper.getWaitReceiveOrder(vendorId),
                orderMapper.getLowerOrder(vendorId));
        VendorInfo info=vendorInfoService.getNewVendorById(vendorId);
        vo.setVolume(info.getVolume());//成交单数
        vo.setSalesAmount(info.getSalesAmount());//总销量
        //当日销量
        String today= DateUtil.format(new Date(),"yyyy-MM-dd");
        String beginTime=today+" 00:00:00";
        String endTime=today+" 23:59:59";
        Integer todaySalesAmount=orderMapper.getTodaySalesAmount(beginTime,endTime,vendorId);
        vo.setTodaySalesAmount(todaySalesAmount);
        //日访问量
        String key= Common.getRedisKeyOfPageView(new Date(),vendorId);
        Integer pageView=0;
        if (Redis.getJedis().exists(key))
                pageView=Integer.parseInt(Redis.getJedis().get(key));
        vo.setPageViews(pageView);
        return vo;
    }


}
