package com.blueteam.wineshop.service.wechatapplet;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.VendorOrderDTO;
import com.blueteam.entity.po.OrderCommentPO;
import com.blueteam.entity.po.OrderGoodsItemPO;
import com.blueteam.entity.vo.VendorOrderVO;
import com.blueteam.wineshop.mapper.OrderCommentPOMapper;
import com.blueteam.wineshop.service.MessageSubService;
import com.blueteam.wineshop.service.UserService;
import com.blueteam.wineshop.service.vendor.VendorOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service("OrderCommentService")
public class OrderCommentServiceImpl implements OrderCommentService{

    @Resource
    private OrderCommentPOMapper orderCommentPOMapper;

    @Resource
    private VendorOrderService vendorOrderService;

    @Autowired
    private UserService userService;
    @Resource
    private MessageSubService messageSubService;
    @Override
    @Transactional
    public int save(OrderCommentPO po) {
        List<OrderCommentPO> list=orderCommentPOMapper.getCommentListByOrderId(po.getUserId(),po.getOrderId());
        if (list!=null&&list.size()>=2){
            return -1;
        }
        if (list!=null&&list.size()==1){
            if (list.get(0).getDeletedFlag()==0){
                return -1;
            }
        }
        VendorOrderVO vo=vendorOrderService.orderDetail2(po.getOrderNo(),po.getOrderId());
        po.setVendorId(vo.getVendorId());
        po.setHeadImage(userService.selectByPrimaryKey(po.getUserId()).getHeadimage());
        String goodsName="";
        if (vo.getOrderSource()!=2){
            for (OrderGoodsItemPO ogip : vo.getOrderGoodsItemPOS()) {
                goodsName += ogip.getGoodsName().trim() + "^";
            }
        }
        if (goodsName.contains("^"))
            goodsName.substring(0,goodsName.lastIndexOf("^")-1);
        po.setGoodsName(goodsName);
        int res=orderCommentPOMapper.insert(po);
        if (res>0){
            res=vendorOrderService.commentOrderFinish(po.getOrderId(),po.getScore());
        }
        if (res > 0 && po.getScore() < 3){
            //差评发送消息
            messageSubService.sendVendormessage(6,vo.getVendorId(),new HashMap<String, Object>());
        }
        return res;
    }


    @Override
    public OrderCommentPO getByOrderId(Long orderId) {
        return orderCommentPOMapper.getByOrderId(orderId);
    }

    /**
     * 获取列表
     * @param dto
     * @return
     */
    @Override
    public ApiResult getCommentList(VendorOrderDTO dto) {
        dto.setBeginIndex((dto.getPageIndex()-1)*dto.getPageSize());
        return ApiResult.success(orderCommentPOMapper.getCommentList(dto),orderCommentPOMapper.getCount(dto));
    }


    @Transactional
    @Override
    public BaseResult delComment(long commentId, int userId,Long orderId) {
        List<OrderCommentPO> list=orderCommentPOMapper.getCommentListByOrderId(userId,orderId);
        if (list==null||list.size()==0)return BaseResult.error("删除失败，未找到相关评价！");
        if (list.size()>1)return BaseResult.error("该评价不允许再删除。");
        OrderCommentPO po=list.get(0);
        if (po.getId()!=commentId)return BaseResult.error("删除失败，未找到相关评价!！");
        Long timeS=System.currentTimeMillis()-po.getCreatedTime().getTime();
        if (timeS>24*60*60*1000)return BaseResult.error("该评价不允许删除！");
        if (orderCommentPOMapper.delComment(commentId)>0){
            vendorOrderService.updateScoreAndCommentStatus(orderId);
            return BaseResult.success();
        }else {
            return BaseResult.error("删除失败");
        }
    }

    @Override
    public List<OrderCommentPO> getCommentListByOrderId(int userId, Long orderId) {
        return orderCommentPOMapper.getCommentListByOrderId(userId,orderId);
    }

    @Override
    public Double averageScore(Integer vendorId) {
        return orderCommentPOMapper.averageScore(vendorId);
    }
}

