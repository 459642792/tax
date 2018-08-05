package com.blueteam.wineshop.service;

import com.blueteam.entity.po.VendorBankPO;
import com.blueteam.wineshop.mapper.VendorBankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("vendorBankService")
public class VendorBankServiceImpl implements VendorBankService{

    @Autowired
    private VendorBankMapper vendorBankMapper;

    @Override
    public int add(VendorBankPO po) {
        VendorBankPO po1=vendorBankMapper.selectByVendorId(po.getVendorId());
        if (po1!=null){return -1;}
        return vendorBankMapper.insert(po);
    }

    @Override
    public VendorBankPO getInfoByVendorId(int vendorId) {
        return vendorBankMapper.selectByVendorId(vendorId);
    }

    @Override
    public int update(VendorBankPO po) {
        return vendorBankMapper.updateById(po);
    }

    @Override
    public int del(VendorBankPO po) {
        return vendorBankMapper.delById(po);
    }
}
