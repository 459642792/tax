package com.blueteam.wineshop.controller.wechatapplet;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.PromotionCatagoryPO;
import com.blueteam.entity.po.PromotionInfoPO;
import com.blueteam.entity.vo.PromotionGoodsVO;
import com.blueteam.wineshop.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangqijun on 18/1/26.
 */
@Controller
@RequestMapping("/promotion")
public class PromotionController  {

    @Autowired
    private PromotionService promotionService;

    /**
     * 获取首页活动信息列表
     * */
    @ResponseBody
    @RequestMapping(value = "/catagory/list", method = RequestMethod.GET)
    public BaseResult listCatagorys(){
        List<PromotionCatagoryPO> catagoryList = promotionService.listPromotionCatagory();
        return ApiResult.success(catagoryList);
    }

    /**
     * 获取指定活动具体信息列表
     * */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public BaseResult listPromotionInfoList(Integer promotionCatagoryId,String cityCode,Integer status,Double longitude,Double latitude) {
        if(cityCode != null){
            cityCode = cityCode+"%";//前缀匹配查询
        }
        Map param  = new HashMap();
        param.put("promotionCatagoryId",promotionCatagoryId);
        param.put("cityCode",cityCode);
        param.put("status",status);
        List<PromotionGoodsVO> promotionGoodsList = promotionService.listPromotionGoods(param);
        //计算店铺距离
        if(longitude != null && latitude != null){
            for (PromotionGoodsVO promotionGoods : promotionGoodsList){
                double distance = GetDistance(longitude, latitude, promotionGoods.getLongitude(), promotionGoods.getLatitude());
                promotionGoods.setDistance(distance);
            }
        }
        return ApiResult.success(promotionGoodsList,promotionGoodsList.size());
    }

    private static double EARTH_RADIUS = 6371.393;

    /**
     * 计算两个经纬度之间的距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;

    }
    /**
     * 计算连个经纬度之间的距离
     *
     * @param d
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
