package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.ScoreInfoMapper;
import com.blueteam.wineshop.mapper.VendorInfoMapper;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.ScoreInfo;
import com.blueteam.entity.po.VendorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 苟永山
 */
@Service
public class ScoreInfoServiceImpl implements ScoreInfoService {

    @Autowired
    ScoreInfoMapper scoreInfoMapper;
    @Autowired
    private VendorInfoMapper vendorInfoMapper;


    @Override
    public List<ScoreInfo> ScoreInfoList(int VendorId) {
        return scoreInfoMapper.ScoreInfoList(VendorId);
    }

    @Override
    public List<ScoreInfo> CommonInfoList(int VendorId, String Type) {
        return scoreInfoMapper.CommonInfoList(VendorId, Type);
    }

    /**
     * 评论订单列表
     *
     * @param OrderNo
     * @param
     * @return
     */
    @Override
    public List<ScoreInfo> CommonOrderList(String OrderNo) {
        return scoreInfoMapper.CommonOrderList(OrderNo);
    }

    @Override
    public int insertComment(ScoreInfo score) {
        return scoreInfoMapper.insertComment(score);
    }

    @Override
    public ScoreInfo ScoreInfo(String OrderNo, int UserId, String Type) {
        return scoreInfoMapper.ScoreInfo(OrderNo, UserId, Type);
    }

    /**
     * 发现条数（评论）
     *
     * @return
     */
    @Override
    public int GetScoreCount(Integer VendorId, String Type) {
        return scoreInfoMapper.GetScoreCount(VendorId, Type);
    }

    /**
     * 查询商家回复
     *
     * @return
     */
    @Override
    public List<ScoreInfo> VendorBackList(String OrderNo, String Type) {
        return scoreInfoMapper.VendorBackList(OrderNo, Type);
    }

    @Override
    public Map<String, Object> findAllVendorScore(Integer vendorId, String type, Integer pageIndex, Integer pageSize, String vendorInfoType) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
        Map<String, Object> resultMap = new HashMap<String, Object>();
//		List<ScoreInfo> list = scoreInfoDao.findAllVendorScore(vendorId, type, pageIndex, pageSize);
        List<Map<String, Object>> list = scoreInfoMapper.findNewAllVendorScore(vendorId, type, pageIndex, pageSize, vendorInfoType);
        if (list.size() == 0) {
            return resultMap;
        }

        List<Map> listsort = new ArrayList<Map>();
        if (null != list && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                String newCreateDate = sdf2.format(sdf.parse(map.get("createDate").toString()));
                Map<String, String> mapsort = new LinkedHashMap<String, String>();
                mapsort.put("vendorInfo_detail", map.get("vendorInfo_detail").toString());
                mapsort.put("orderNo", map.get("orderNo").toString());
                mapsort.put("productId", map.get("productId").toString());
                mapsort.put("nickName", map.get("nickName") != null ? map.get("nickName").toString() : "");
                mapsort.put("vendorId", map.get("vendorId").toString());
                mapsort.put("type", map.get("type").toString());
                mapsort.put("userName", map.get("userName") != null ? map.get("userName").toString() : "");
                mapsort.put("userId", map.get("userId").toString());
                mapsort.put("CreateDate", newCreateDate);
                mapsort.put("score", map.get("score").toString());
                mapsort.put("createBy", map.get("createBy").toString());
                mapsort.put("updateBy", map.get("updateBy").toString());
                mapsort.put("id", map.get("id").toString());
                mapsort.put("detail", map.get("detail").toString());
                mapsort.put("createDate", map.get("createDate").toString());
                mapsort.put("updateDate", map.get("updateDate").toString());

/*
                        "vendorInfo_detail": "武侯区",
                        "orderNo": "201709261419546710953005",
                        "productId": 0,
                        "nickName": "龚治辉_Qa2",
                        "vendorId": 3,
                        "type": "COMMENTVENDOR_CODE_VENDOR",
                        "userName": "龚治辉_Qa2",
                        "userId": 4,
                        "CreateDate": "09-26",
                        "score": 5,
                        "createBy": "13081000106",
                        "updateBy": "13081000106",
                        "id": 23,
                        "detail": "的罚款等将发动时开发",
                        "createDate": 1506412810000
                        "updateDate": 1506412810000,*/
                listsort.add(mapsort);
            }
        }
        resultMap.put("list", listsort);


        BigDecimal scores = BigDecimal.ZERO;
        if (null != list && !list.isEmpty()) {
//			for (ScoreInfo scoreInfo : list) {
//				scores = scores.add(scoreInfo.getScore());
//				String newCreateDate = sdf2.format(sdf.parse(scoreInfo.getCreateDate()));
//				scoreInfo.setCreateDate(newCreateDate);
//			}
            for (Map<String, Object> map : list) {
                String newCreateDate = sdf2.format(sdf.parse(map.get("createDate").toString()));
                map.put("CreateDate", newCreateDate);
            }
        }
        Map<String, Object> scoresMap = scoreInfoMapper.countVendorScore(vendorId, type);
        scores = new BigDecimal(scoresMap.get("scores").toString());
        long sums = (long) scoresMap.get("sums");
        BigDecimal bigSums = new BigDecimal(sums);
        scores = scores.divide(bigSums, 1, BigDecimal.ROUND_HALF_DOWN);
    /*	String str = scores.toString();
        String[] strs = str.split("\\.",-1);
		int i = Integer.parseInt(strs[1]);
		double d = 0.00;
		if( i >= 0 && i < 25 ){
			d =Double.parseDouble(strs[0]);
		}else if(i >= 25 && i < 75 ){
			d =Double.parseDouble(strs[0])+0.5;
		}else{
			d =Double.parseDouble(strs[0])+1.0;
		}*/
        resultMap.put("avgScore", scores.toString()); //总评分
        resultMap.put("people", list.size()); //评价人数
        resultMap.put("count", scoreInfoMapper.findNewAllVendorScoreCount(vendorId, type, vendorInfoType));
        return resultMap;
    }

    @Override
    public ApiResult<List<ScoreInfo>> findAllVendorScore4List(Integer vendorId, String type, Integer pageIndex,
                                                              Integer pageSize) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
        List<ScoreInfo> list = scoreInfoMapper.findAllVendorScore(vendorId, type, pageIndex, pageSize);
        for (ScoreInfo scoreInfo : list) {
            String newCreateDate = sdf2.format(sdf.parse(scoreInfo.getCreateDate()));
            scoreInfo.setCreateDate(newCreateDate);
        }
        ApiResult<List<ScoreInfo>> result = ApiResult.success(list);
        result.setCount(scoreInfoMapper.findAllVendorScoreCount(vendorId, type));
        return result;
    }

    @Override
    public ApiResult<List<Map<String, Object>>> findNewAllVendorScore(Integer vendorId, String type, Integer pageIndex, Integer pageSize, String vendorInfoType) throws Exception {
        List<Map<String, Object>> list = scoreInfoMapper.findNewAllVendorScore(vendorId, type, pageIndex, pageSize, vendorInfoType);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
        if (null != list && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                String newCreateDate = sdf2.format(sdf.parse(map.get("createDate").toString()));
                map.put("CreateDate", newCreateDate);
            }
        }
        ApiResult<List<Map<String, Object>>> result = ApiResult.success(list);
        result.setCount(scoreInfoMapper.findNewAllVendorScoreCount(vendorId, type, vendorInfoType));
        return result;
    }


    /**
     * 方法的功能描述:TODO 获取商家 消息接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    @Override
    public MessageRecipient getMessageRecipient(Integer scoreInfoId) {
        Map<String, Object> map = scoreInfoMapper.getScoreInfo(scoreInfoId);
        MessageRecipient m = new MessageRecipient();
        if (null != map) {
            if (null != map.get("vendorInfoId") && !"".equals(map.get("vendorInfoId"))) {
                m.setVendorId(Integer.valueOf(map.get("vendorInfoId").toString()));
            } else {
                m.setVendorId(0);
            }

            VendorInfo vendorInfo = vendorInfoMapper.getVendorById(m.getVendorId());
            if (vendorInfo != null)
                m.setUserId(vendorInfo.getUserId());
        } else {
            m.setVendorId(0);
            m.setUserId(0);
            m.setCarriersId(0);
        }
        return m;
    }

    /**
     * 方法的功能描述:TODO 获取 消息用户接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    @Override
    public MessageRecipient getUserMessageRecipient(Integer scoreInfoId) {
        MessageRecipient m = new MessageRecipient();
        ScoreInfo scoreInfo = scoreInfoMapper.selectBeAnsweredUserID(scoreInfoId);
        if (scoreInfo != null)
            m.setUserId(scoreInfo.getUserId());
        return m;
    }


    /**
     * 获取评价内容，type,以及商家和用户名称
     *
     * @param id 评论回复ID
     * @return
     */
    @Override
    public ScoreInfo selectScoreAndUserByID(Integer id) {
        return scoreInfoMapper.selectScoreAndUserByID(id);
    }
}
