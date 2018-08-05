package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.ReVendor;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.wineshop.mapper.ReVendorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReVendorServiceImpl implements ReVendorService {

    @Autowired
    ReVendorMapper revendorMapper;

    @Autowired
    VendorInfoService vendorInfoService;

    /**
     * 新增操作
     *
     * @param reVendor
     * @return
     */
    public int insert(ReVendor reVendor) {
        return revendorMapper.insert(reVendor);
    }

    /**
     * 更新操作
     *
     * @param reVendor
     * @return
     */
    public int update(ReVendor reVendor) {
        return revendorMapper.update(reVendor);
    }

    /**
     * 查询推荐商家的数据
     *
     * @param VendorName
     * @param TradingArea
     * @param pageSize
     * @param pageIndex
     * @return
     */
    public List<ReVendor> listReVendor(String VendorName, String TradingArea, Integer pageSize, Integer pageIndex) {
        return revendorMapper.listReVendor(VendorName, TradingArea, pageSize, pageIndex);
    }

    /**
     * 查询推荐商家的数量
     *
     * @param VendorName
     * @param TradingArea
     * @return
     */
    public int ReVendorCount(String VendorName, String TradingArea) {
        return revendorMapper.ReVendorCount(VendorName, TradingArea);
    }

    /**
     * 查询最大的数据Id
     *
     * @param AreaAddr
     * @return
     */
    public int MaxOrderField(String AreaAddr) {
        return revendorMapper.MaxOrderField(AreaAddr);
    }

    /**
     * 删除推荐店铺
     *
     * @param Id
     * @return
     */
    @Override
    public int DeleteReVendor(Integer Id) {
        return revendorMapper.DeleteReVendor(Id);
    }

    /**
     * 推荐店铺（按序号进行默认排序）
     *
     * @param AreaAddr
     * @return
     */
    public List<ReVendor> listReVendor2(String AreaAddr) {
        return revendorMapper.listReVendor2(AreaAddr);
    }

    /**
     * 按区域查询推荐商家的信息
     *
     * @param Id
     * @return
     */
    public ReVendor revendorDetail(Integer Id) {
        return revendorMapper.revendorDetail(Id);
    }

    /**
     * 修改点击数量
     *
     * @param reVendor
     * @return
     */
    public int updateClick(ReVendor reVendor) {
        return revendorMapper.updateClick(reVendor);
    }


    /**
     * 推荐 修改GYS方法
     */
    @Override
    public MessageRecipient getMessageRecipient(Integer vendorId) {
        MessageRecipient objRe = new MessageRecipient();
        objRe.setVendorId(vendorId);
        //获取用户
        VendorInfo vendorInfo = vendorInfoService.getVendorById(vendorId);
        if (vendorInfo != null)
            objRe.setUserId(vendorInfo.getUserId());

        return objRe;
    }
}
