package com.blueteam.wineshop.service;

import com.blueteam.base.lang.RDbTrans;
import com.blueteam.entity.dto.GoodsVerifySearchDTO;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.VendorVerifySearchDTO;
import com.blueteam.entity.po.GoodsVerifyInfoPO;
import com.blueteam.entity.vo.AdminVendorVerifyVO;
import com.blueteam.entity.vo.AdminVerifyVO;
import com.blueteam.wineshop.mapper.GoodsVerifyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by  NastyNas on 2017/11/8.
 */
@Service
public class GoodsVerifyServiceImpl implements GoodsVerifyService {
    @Autowired
    GoodsVerifyMapper goodsVerifyMapper;

    @Override
    public PageResult<List<Map>> listVerifyInfo(GoodsVerifySearchDTO goodsVerifySearchDTO) {
        Integer count = goodsVerifyMapper.countVerifyInfo(goodsVerifySearchDTO);
        List<AdminVerifyVO> verifyVOList = goodsVerifyMapper.listVerifyInfo(goodsVerifySearchDTO);
        PageResult pageResult = new PageResult();
        pageResult.setCount(count);
        pageResult.setList(verifyVOList);
        return pageResult;
    }

    @Override
    public PageResult<List<Map>> listVendorVerifyInfo(VendorVerifySearchDTO vendorVerifySearchDTO) {
        Integer count = goodsVerifyMapper.countVendorVerifyInfo(vendorVerifySearchDTO);
        List<AdminVendorVerifyVO> verifyVOList = goodsVerifyMapper.listVendorInfo(vendorVerifySearchDTO);
        //处理展示vo信息
        wrapShowVOList(verifyVOList);
        PageResult pageResult = new PageResult();
        pageResult.setCount(count);
        pageResult.setList(verifyVOList);
        return pageResult;
    }

    @Override
    public Integer updateVerifyInfo(GoodsVerifyInfoPO verifyPO) {
        return goodsVerifyMapper.updateVerifyInfo(verifyPO);
    }


    private void wrapShowVOList(List<AdminVendorVerifyVO> verifyVOList) {
        for (AdminVendorVerifyVO vendorVO : verifyVOList) {
            //设置提交时间
            vendorVO.setSubmitTime(RDbTrans.asShowDate(vendorVO.getSubmitTime()));
            //设置展示价格
            vendorVO.setVerifySalePrice(RDbTrans.asShowPrice(vendorVO.getVerifySalePrice()));
            //设置照片列表
            vendorVO.setVerifyPhotoList(Arrays.asList(vendorVO.getVerifyGoodsPhoto().split("\\^")));
        }
    }
}
