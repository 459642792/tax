package com.blueteam.wineshop.service;

import com.blueteam.entity.po.CouponRecord;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.wineshop.mapper.CouponRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponRecordServiceImpl implements CouponRecordService {

    @Autowired
    CouponRecordMapper couponRecordMapper;

    @Override
    public List<CouponRecord> CouponRecordList(int CouponId, int UserId) {
        return couponRecordMapper.CouponRecordList(CouponId, UserId);
    }

    @Override
    public PageResult<List<CouponRecord>> vendorCouponRecordList(Integer couponId, Integer pageIndex, Integer pageSize) {
        PageResult<List<CouponRecord>> result = new PageResult<List<CouponRecord>>();
        List<CouponRecord> list = couponRecordMapper.vendorCouponRecordList(couponId, pageIndex, pageSize);
        int count = couponRecordMapper.vendorCouponRecordListCount(couponId);
        result.setList(list);
        result.setCount(count);
        return result;
    }
}
