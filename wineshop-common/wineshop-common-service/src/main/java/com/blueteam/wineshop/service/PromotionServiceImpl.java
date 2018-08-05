package com.blueteam.wineshop.service;

import com.blueteam.entity.po.GoodsInfoPO;
import com.blueteam.entity.po.PromotionCatagoryPO;
import com.blueteam.entity.po.PromotionInfoPO;
import com.blueteam.entity.vo.PromotionGoodsVO;
import com.blueteam.wineshop.mapper.PromotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangqijun on 18/1/13.
 */
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionMapper promotionMapper;

    @Override
    public List<PromotionCatagoryPO> listPromotionCatagory() {
        return promotionMapper.listPromotionCatagory();
    }

    @Override
    public PromotionCatagoryPO getPromotionCatagoryById(Integer promotionCatagoryId){
        return promotionMapper.getPromotionCatagoryById(promotionCatagoryId);
    }

    @Override
    public int addPromotionCatagory(PromotionCatagoryPO promotionCatagory) {
        return promotionMapper.addPromotionCatagory(promotionCatagory);
    }

    @Override
    public int updatePromotionCatagory(PromotionCatagoryPO promotionCatagory) {
        return promotionMapper.updatePromotionCatagory(promotionCatagory);
    }

    @Override
    public int deletePromotionCatagory(Integer promotionCatagoryId) {
        return promotionMapper.deletePromotionCatagory(promotionCatagoryId);
    }

    @Override
    public List<PromotionInfoPO> listPromotionInfoByCatagory(Map map) {
        return promotionMapper.listPromotionInfoByCatagory(map);
    }

    @Override
    public int addPromotionInfo(PromotionInfoPO promotionInfo) {
        return promotionMapper.addPromotionInfo(promotionInfo);
    }

    @Override
    public int updatePromotionInfo(PromotionInfoPO promotionInfo) {
        return promotionMapper.updatePromotionInfo(promotionInfo);
    }

    @Override
    public int updatePromotionInfoStatus(PromotionInfoPO promotionInfo) {
        return promotionMapper.updatePromotionInfoStatus(promotionInfo);
    }

    @Override
    public int deletePromotionInfo(Integer promotionInfoId) {
        return promotionMapper.deletePromotionInfo(promotionInfoId);
    }
    @Override
    public int checkPromotionWeight(PromotionInfoPO promotionInfo){
        return promotionMapper.checkPromotionWeight(promotionInfo);
    }

    @Override
    public List<GoodsInfoPO> selectGoodsByVendorId(Long vendorId){
        return promotionMapper.selectGoodsByVendorId(vendorId);
    }

    @Override
    public List<PromotionGoodsVO> listPromotionGoods(Map map){
        return promotionMapper.listPromotionGoods(map);
    }

    @Override
    public void executeCheckStatus(){
        List<PromotionInfoPO> promotionInfoPOList = promotionMapper.listPromotionInfoByCatagory(null);
        Date nowDate = new Date();
        for (PromotionInfoPO promotionInfoPO: promotionInfoPOList){
            try {
                boolean bupdate = false;
                Integer status =  promotionInfoPO.getStatus();
                //检查促销项目是否过期
                String endTime = promotionInfoPO.getEndTime();
                SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date endDate = sdf.parse(endTime);
                if(endDate.before(nowDate) && status == 1){
                    promotionInfoPO.setStatus(0);
                    bupdate = true;
                }
                //检查商家商品是否下架
                if(!bupdate && status == 1){
                    Map map = new HashMap();
                    map.put("vendorId",promotionInfoPO.getVendorId());
                    map.put("goodsId",promotionInfoPO.getGoodsId());
                    Integer state = promotionMapper.getVendorGoodsState(map);
                    if(state == null || state == 0){
                        promotionInfoPO.setStatus(0);
                        bupdate = true;
                    }
                }
                //检查商品在平台是否下架
                if(!bupdate && status == 1){
                    Integer state = promotionMapper.getGoodsState(promotionInfoPO.getGoodsId());
                    if(state == null || state == 0){
                        promotionInfoPO.setStatus(0);
                        bupdate = true;
                    }
                }
                if(bupdate == true){
                    promotionMapper.updatePromotionInfoStatus(promotionInfoPO);
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        System.out.println("定时检查促销商品");
    }
}
